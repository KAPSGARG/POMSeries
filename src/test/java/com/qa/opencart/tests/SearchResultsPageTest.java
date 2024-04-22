package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;

public class SearchResultsPageTest extends BaseTest {

	@BeforeClass
	public void searchResultsSetup() {

		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getProductCountData() {
		return new Object[][] { { "macbook", 3 }, { "imac", 1 }, { "samsung", 2 } };
	}

	@Test(dataProvider = "getProductCountData")
	public void searchResultsCountTest(String searchKey, int productCount) {
		searchResultsPage = accPage.doSearch(searchKey);

		// To validate
		Assert.assertEquals(searchResultsPage.getSearchProductCount(), productCount);
	}
}
