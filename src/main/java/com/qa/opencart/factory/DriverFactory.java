package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.logger.Log;

public class DriverFactory {

	WebDriver driver;
	Properties prop;
	OptionsManager optionsManager;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	public static String highlight;

	public WebDriver initDriver(Properties prop) {

		// Here the value of browser is coming from the property file
		String browserName = prop.getProperty("browser");

		// In case, you want to pass the value of browser from the command line through
		// maven
		// String browserName = System.getProperty("browser");

		System.out.println("Browser name is : " + browserName);

		Log.info("Browser name is : " + browserName);

		highlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		switch (browserName.trim().toLowerCase()) {
		case "chrome":
			// driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			// tlDriver.set(driver);
			break;

		case "firefox":
			// driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;

		case "edge":
			// driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		case "safari":
			// driver = new SafariDriver();
			tlDriver.set(new SafariDriver());
			break;

		default:
			// System.out.println("Plz pass the right browser : " + browserName);
			Log.error("Plz pass the right browser : " + browserName);
			throw new BrowserException("NO BROWSER FOUND..." + browserName);
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));

		// Here we are returning the local copy of web driver whic is maintained by
		// local thread
		return getDriver();
	}

	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	public Properties initProp() {

		FileInputStream ip = null;
		prop = new Properties();

		// Environment name like qa,dev,stage,uat,prod
		// Command - mvn clean install -Denv="qa"
		// -D is the commnd line argument for the maven

		String envName = System.getProperty("env");
		System.out.println("Running tests on env: " + envName);

		try {
			if (envName == null) {
				System.out.println("No env is given...Hence running it on QA env...");
				ip = new FileInputStream("./src/test/resourcess/config/config.qa.properties");
			} else {

				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resourcess/config/config.qa.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resourcess/config/config.dev.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resourcess/config/config.stage.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resourcess/config/config.uat.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resourcess/config/config.properties");
					break;
				default:
					System.out.println("Please pass the right env name... " + envName);
					throw new FrameworkException(AppError.ENV_NAME_NOT_FOUND);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * take screenshot
	 */

	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp directory
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";

		File destination = new File(path);

		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
}
