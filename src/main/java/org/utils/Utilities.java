package org.utils;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.cognizant.base.CognizantBase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utilities extends CognizantBase {

	public Utilities(WebDriver driver) {
		this.driver = driver;
	}

	Function <WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
		public Boolean apply(WebDriver driver) {

			return (Boolean)((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
		}
	};

	// SwitchWindow Based On Page Title
	public  void switchWindow(String pageTitle) {
		List <String> windowList = new ArrayList<String>(driver.getWindowHandles());
		for(String window : windowList) {
			new WebDriverWait(driver, Duration.ofSeconds(30)).until(function);
			String title = driver.switchTo().window(window).getTitle();
			
			if(title.contains(pageTitle)) {
				break;
			}

		}
	}

	//full-page screenshot
	public  void takeScreenShot(String name) {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("./snaps/"+name+".png"));
		} catch (IOException e) {
			logger.info(e.getMessage());
			
		}
	}

	//element screenshot
	public  void takeScreenShot( WebElement element,String name) {
		File scrFile = element.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File("./snaps/"+name+".png"));
		} catch (IOException e) {
		logger.info(e.getMessage());
			
		}
	}
	
	
}
