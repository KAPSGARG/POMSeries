package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegistrationPage {

	// According to the Page Object Model, always remember that we must have
	// separate pages w.r.t. the web pages of an application

	// Page Class/Page Library/Page Object
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. Private By Locators
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By email = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By confirmPassword = By.id("input-confirm");
	private By subscribeYes = By.xpath("//label[@class='radio-inline']/input[@name='newsletter' and @value='1']");
	private By subscribeNo = By.cssSelector("label.radio-inline > input[name='newsletter' ][value='0']");
	private By agreeCheckBox = By.name("agree");
	private By continueBtn = By.cssSelector("input.btn.btn-primary[value='Continue']");
	private By successMsg = By.cssSelector("div#content > h1");
	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");

	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		this.eleUtil = new ElementUtil(driver);
	}

	public boolean userRegister(String firstName, String lastName, String email, String telephone, String password,
			String subscribe) {

		eleUtil.waitForElementVisible(this.firstName, 10).sendKeys(firstName);
		eleUtil.doSendKeys(this.lastName, lastName);
		eleUtil.doSendKeys(this.email, email);
		eleUtil.doSendKeys(this.telephone, telephone);
		eleUtil.doSendKeys(this.password, password);
		eleUtil.doSendKeys(this.confirmPassword, password);

		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(this.subscribeYes);
		} else {
			eleUtil.doClick(this.subscribeNo);
		}

		eleUtil.doClick(agreeCheckBox);
		eleUtil.doClick(continueBtn);

		String regSuccessMessg = driver.findElement(successMsg).getText();
		System.out.println(regSuccessMessg);

		if (regSuccessMessg.equals(AppConstants.USER_REG_SUCCESS_MESSG)) {

			eleUtil.doClick(logoutLink);
			eleUtil.doClick(registerLink);
			return true;
		}

		return false;

	}

}
