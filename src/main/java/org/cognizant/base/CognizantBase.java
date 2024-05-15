package org.cognizant.base;


import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.utils.DriverSetup;
import org.utils.PropertiesHandler;
import org.utils.Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class CognizantBase implements Base{
	long maxWaitTime = 30;

	public  WebDriver driver = null;
	public Properties p;
	String url = null;
	static public Logger logger;
	public static ExtentSparkReporter reporter = new ExtentSparkReporter("./TestReport01.html");
	public static ExtentReports extent = new ExtentReports();
	{extent.attachReporter(reporter);}
	String author ="Phantom Troupe"; String category = "Functional Testing";
	protected static String[] weeks = new String[3];

	@BeforeTest
	@Parameters({"browser"}) // Get the Parameters From XML 
	
	//Setup method to launch Chrome
	public void startApp(String browser) throws IOException 
	{
		System.out.println("Opening Browser");
		driver = DriverSetup.getBrowser(browser);
		setURL();
		logger = LogManager.getLogger(this.getClass());
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(maxWaitTime));
	}
	//Explicit Wait
	public WebDriverWait getWait(WebDriver driver, long dur_sec) {
		return new WebDriverWait(driver, Duration.ofSeconds(dur_sec));
	}
	
	//Close the driver
	public void close() {
		System.out.println("Browser Closed");
		driver.quit();
	}
	//Switch the WindowsHandles to Focus on Window
	public void switchToWindow(int i) {
		getWait(driver, 30).until(ExpectedConditions.numberOfWindowsToBe(i));
		ArrayList<String> list = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(list.get(i-1));
	}
	
	// Function to check if the dropdown is selected ?
	public void selectValue(WebElement ele, String value) {
		WebElement element = isElementVisible(ele);
		new Select(element).selectByValue(value);
	}
	// Function to check if the dropdownText is selected ?
	public void selectText(WebElement ele, String text) {
		WebElement element = isElementVisible(ele);
		new Select(element).selectByVisibleText(text);
	}
	//Function to check if the dropdownIndex is selected ?
	public void selectIndex(WebElement ele, int position) {
		WebElement element = isElementVisible(ele);
		new Select(element).selectByIndex(position);
	}
	//Wait untiL the Element to Be Clickable
	public void click(WebElement ele) {
		new WebDriverWait(driver, Duration.ofSeconds(maxWaitTime)).until(ExpectedConditions.elementToBeClickable(ele)).click();
	}
	//Clear and SendValue
	public void type(WebElement ele, String testData) {
		try {
			WebElement element = isElementVisible(ele);
			element.clear();
			element.sendKeys(testData);
		} catch (NullPointerException e) {
			logger.info("Element might be null => " +e.getMessage());
		}
	}
	//Wait until the Element is Visible
	private WebElement isElementVisible(WebElement ele) {
		WebElement element = getWait(driver, maxWaitTime).
				until(ExpectedConditions.visibilityOf(ele));
		return element;
	}
	//Over loading the method "type"
	public void type(WebElement ele, String testData, Keys keys) {
		WebElement element = isElementVisible(ele);
		element.clear();
		element.sendKeys(testData, keys);
	}
	//AppendText
	public void appendText(WebElement ele, String testData) {
		WebElement element = isElementVisible(ele);
		element.sendKeys(testData);
	}
	//Get window Title
	public String getTitle() {
		return driver.getTitle();
	}
	//Get Current URL
	public String getURL() {
		return driver.getCurrentUrl();
	}
	//Check Wheather the Element is Displayed 
	public boolean isDisplayed(WebElement ele) {

		return ele.isDisplayed();
	}
	//get the url and laucnh the browser to Website
	public void setURL() throws IOException {
		url = PropertiesHandler.getURL();
		driver.get(url);
	}
	// Return the WebElement 
	public WebElement element(String type, String value) {
		try {
			switch (type) {
			case "id":
				return driver.findElement(By.id(value));
			case "name":
				return driver.findElement(By.name(value));
			case "xpath":
				return driver.findElement(By.xpath(value));
			case "link":
				return driver.findElement(By.linkText(value));
			case "className":
				return driver.findElement(By.className(value));
			case "css":
				return driver.findElement(By.cssSelector(value));
			default:
				break;
			}
		}catch (NoSuchElementException e) {
			logger.info("Element not found => "+e.getMessage());
			throw new NoSuchElementException("Element not found");
		}catch(WebDriverException e) {
			logger.info(e.getMessage());
			throw new WebDriverException("Some unknown webdriver error");
		}catch(Exception e) {
			logger.info(e.getMessage());
		}
		return null;
	}
	//Actions Class
	public Actions getHandler(WebDriver driver) {
		return new Actions(driver);
	}

	
	//Get Ulitities Class
	public Utilities getUtil(WebDriver driver) {
		return new Utilities(driver);
	}
	
	
	
	@AfterSuite
	public void stopReport() {
		extent.flush();
	}
	
	public ExtentTest createTest(String desc) {
		ExtentTest test  = extent.createTest(desc);
		test.assignAuthor(author);
		test.assignCategory(category);
		return test;
	}

	public void stepReport(ExtentTest test, String status, String desc, String fileName) {

		switch(status.toLowerCase()) {

		case "pass":
			test.pass(desc, MediaEntityBuilder.createScreenCaptureFromPath("./snaps/"+fileName).build());
			break;

		case "fail":
			test.fail(desc, MediaEntityBuilder.createScreenCaptureFromPath("./snaps/"+fileName).build());
			break;
		case "warning":
			test.pass(desc, MediaEntityBuilder.createScreenCaptureFromPath("./snaps/"+fileName).build());
			break;
		default:
			System.err.println("Status is not defined");
			break;
		}
		

	}
}


