package org.becognizant.pages;

import org.cognizant.base.CognizantBase;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BeCognizantHome extends CognizantBase{

	//be.cognizant Home Page
	public BeCognizantHome(WebDriver driver){
		this.driver = (RemoteWebDriver) driver;
		PageFactory.initElements(driver,this);
	}

	//Locators
	By framEle1 = By.xpath("//*[@id='o365shellwcssframe']");
	@FindBy(xpath = "//button[@id='O365_MainLink_Me']") WebElement userInfoLabel;
	@FindBy(xpath = "//div[@title='OneCognizant']") WebElement oneCogzBtn;
	@FindBy(id = "mectrl_currentAccount_primary") WebElement userName;
	@FindBy(id = "mectrl_currentAccount_secondary") WebElement email;
	@FindBy(xpath = "//div[@class='mectrl_accountInfo']") WebElement userInfoEle;
	@FindBy(xpath = "//*[@data-automation-id='contentScrollRegion']") WebElement scrollElement;

	
	//check for login status
	public void checkLogin() {
		getWait(driver, 60).until(ExpectedConditions.titleContains("Be.Cognizant - Home"));
	}
	
	//Check for userInfo Label
	public boolean checkUserLabel() {
		return isDisplayed(userInfoLabel);
	}

	//Checks for user-information
	public boolean checkUserInfo() {
		if(isDisplayed(userName) && isDisplayed(email)) {
			return true;
		}
		return false;
	} 

	//Checks oneCognizant button
	public boolean checkOneCogzBtn() {
		return isDisplayed(oneCogzBtn);
	} 

	//clicks user-info
	public void clickUserInfo() {
		try {
			//WebElement scrollElement = driver.findElement(By.xpath("//*[@data-automation-id=\"contentScrollRegion\"]"));
		//	((JavascriptExecutor) driver).executeScript("arguments[0].scrollTop = arguments[1];",scrollElement, 300);
			userInfoLabel.click();
			Thread.sleep(5000);
			userInfoLabel.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//Get User-Info
	public void getUserInfo() {
		System.out.println("****************Capturing User Information****************\n");
		String Name = userName.getText();
		String Email = email.getText();
		System.out.println("\tUserName: "+Name);
		logger.info("UserName: "+Name);
		System.out.println("\tEmail: "+Email +"\n\n****************User Information Successfully Printed*****\n");
		logger.info("Email: "+Email +"\nUser Information Successfully Printed\n");
	}
	//Get UserInfo WebELement
	public WebElement getUserInfoEle() {
		return userInfoEle;
	}
	//click on One-Cognizant button
	public void clickOneCogzBtn() {
		oneCogzBtn.click();
		logger.info("OneCogzButton clicked\n");
	}
}
