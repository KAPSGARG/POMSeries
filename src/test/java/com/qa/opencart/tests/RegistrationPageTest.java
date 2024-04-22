package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.CSVUtil;
import com.qa.opencart.utils.ExcelUtil;
import com.qa.opencart.utils.StringUtil;

public class RegistrationPageTest extends BaseTest {

	@BeforeClass
	public void regSetup() {
		registrationPage = loginPage.navigateToRegisterPage();
	}

	@DataProvider
	public Object[][] getUserRegTestData() {
		return new Object[][] { { "Hardy", "Sandhu", "9898989898", "hardy@123", "yes" },
				{ "Manish", "Gupta", "1234567892", "manish@456", "no" },
				{ "Mohit", "Tyagi", "3456789123", "mohit@345", "Yes" } };
	}

	@DataProvider
	public Object[][] getUserRegTestDataFromExcel() {
		return ExcelUtil.getTestData(AppConstants.REGISTER_SHEET_NAME);
	}

	@DataProvider(name = "CSVRegData")
	public Object[][] getUserRegTestDataFromCSV() {
		return CSVUtil.csvData(AppConstants.REGISTER_SHEET_NAME);
	}

	@Test(dataProvider = "CSVRegData")
	public void userRegTest(String firstName, String lastName, String telephone, String password, String subscribe) {
		Assert.assertTrue(registrationPage.userRegister(firstName, lastName, StringUtil.getRandomEmailId(), telephone,
				password, subscribe));
	}
}
