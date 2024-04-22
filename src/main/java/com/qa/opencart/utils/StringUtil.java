package com.qa.opencart.utils;

public class StringUtil {

	public static String getRandomEmailId() {
		String emailId = "testautomation" + System.currentTimeMillis() + "@opencart.com";
		return emailId;
	}

}
