package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.logger.Log;
import com.qa.opencart.utils.ElementUtil;
import com.qa.opencart.utils.TimeUtil;

public class LoginPage {

	// According to the Page Object Model, always remember that we must have
	// separate pages w.r.t. the web pages of an application

	// Page Class/Page Library/Page Object
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Private By Locators
	private By emailId = By.id("input-email");
	private By password = By.id("input-password");
	private By loginButton = By.xpath("//input[@value='Login']");
	private By forgotPWDLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");

	// 2. Public Page Class Constructor
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		this.eleUtil = new ElementUtil(driver);
	}

	// 3. Public Page Actions/Methods
	public String getLoginPageTitle() {

		String title = eleUtil.waitForTitleIs(AppConstants.LOGIN_PAGE_TITLE, TimeUtil.DEFAULT_MEDIUM_TIME);
		// System.out.println("Login Page Title : " + title);
		Log.info("Login Page Title : " + title);
		return title;
	}

	public String getLoginPageURL() {

		String url = eleUtil.waitForURLContains(AppConstants.LOGIN_PAGE_URL_FRACTION, TimeUtil.DEFAULT_MEDIUM_TIME);
		System.out.println("Login Page URL : " + url);
		return url;
	}

	public boolean isForgotPwdLinkExist() {

		return eleUtil.isElementDisplayed(forgotPWDLink);
	}

	// Encapsulation : Accessing private members using public
	public AccountsPage doLogin(String userName, String pwd) {
		System.out.println("User cred : " + userName + " : " + pwd);

		eleUtil.waitForElementVisible(emailId, TimeUtil.DEFAULT_LONG_TIME).sendKeys(userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginButton);

		return new AccountsPage(driver);
	}

	public RegistrationPage navigateToRegisterPage() {
		eleUtil.waitForElementVisible(registerLink, TimeUtil.DEFAULT_LONG_TIME).click();
		return new RegistrationPage(driver);
	}

}
