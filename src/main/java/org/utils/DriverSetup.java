package org.utils;

import org.cognizant.base.CognizantBase;
import org.openqa.selenium.WebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverSetup extends CognizantBase {


	public static WebDriver driver = null;
		//Launch the Specified WebDriver (Chrome/Edge/FireFox)
		public static WebDriver getBrowser(String browser) {

			switch (browser.toUpperCase()) {
			case "CHROME":
				driver = WebDriverManager.chromedriver().create();
				System.out.println("Test Automation Started On 'Chrome Browser'\n");
				break;
			case "FIREFOX":
				driver = WebDriverManager.chromedriver().create();
				System.out.println("Test Automation Started On 'FireFox Browser'\n");
				break;
			case "EDGE":
				driver = WebDriverManager.edgedriver().create();
				System.out.println("Test Automation Started On 'Edge Browser'\n");
				break;
			default:
				logger.info("Driver is not defined: Pass a Valid Browser in Parameter");
				System.out.println("Driver is not defined: Pass a Valid Browser in Parameter");
				break;
			}
			return driver;
		}
	}



