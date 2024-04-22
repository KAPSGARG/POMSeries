package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	// According to the Page Object Model, always remember that we must have
	// separate pages w.r.t. the web pages of an application

	// Page Class/Page Library/Page Object
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Private By Locators
	private By logoutLink = By.linkText("Logout");
	private By myAccountLink = By.linkText("My Account");
	private By headers = By.xpath("//div[@id='content']//h2");
	private By search = By.name("search");
	private By searchIcon = By.xpath("//div[@id='search']//button");

	// 2. Public Page Class Constructor
	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		this.eleUtil = new ElementUtil(driver);
	}

	// 3. Public Page Actions/Methods
	public String getAccPageTitle() {

		String title = eleUtil.waitForTitleIs(AppConstants.ACCOUNTS_PAGE_TITLE, 5);
		System.out.println("Acc Page Title : " + title);
		return title;
	}

	public String getAccPageURL() {

		String url = eleUtil.waitForURLContains(AppConstants.ACC_PAGE_URL_FRACTION, 5);
		System.out.println("Acc Page URL : " + url);
		return url;
	}

	public boolean isLogoutLinkExist() {
		return eleUtil.waitForElementVisible(logoutLink, 10).isDisplayed();
	}

	public boolean myAccountLinkExist() {

		return eleUtil.waitForElementVisible(myAccountLink, 10).isDisplayed();
	}

	public List<String> getAccountsPageHeadersList() {
		List<WebElement> headersEleList = eleUtil.getElements(headers);
		List<String> headerList = new ArrayList<String>();

		for (WebElement ele : headersEleList) {
			String text = ele.getText();

			headerList.add(text);
		}

		return headerList;
	}

	public SearchResultsPage doSearch(String searchKey) {

		eleUtil.doSendKeys(search, searchKey);
		eleUtil.doClick(searchIcon);

		return new SearchResultsPage(driver);
	}
}
