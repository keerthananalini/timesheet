package org.oneCognizant.pages;

import org.cognizant.base.CognizantBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OneCognizantHome extends CognizantBase{
	
	public OneCognizantHome(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}
	
	//OneCognizant home page
	@FindBy(id = "oneC_searchAutoComplete") WebElement searchEle;
	@FindBy(xpath = "//*[contains(text(),'Submit Timesheet') and @class='quickActionsResultText']") WebElement timesheetBtn;
	 
	//Edge Locators
	@FindBy(xpath="//li[@class='searchTopBar']")
	WebElement edgeSearch;
	@FindBy(xpath="//input[@id='oneCSearchTop']")
	WebElement edgeInputBox;
	
	//locators
	public boolean checkSearchInput() {
		return isDisplayed(searchEle);
	}
	public boolean checkTimeSheetBtn() {
		return isDisplayed(timesheetBtn);
	}
	
	//actions
	// provide data provider
	public void sendSearchText(String searchText) {
		try {
			searchEle.sendKeys(searchText);
		} catch (Exception e) {
			edgeSearch.click();
			edgeInputBox.sendKeys(searchText);
		}		
	}
	//ClickTimeSheetButton
	public void clickTimeSheetBtn() {
		click(timesheetBtn);
	}

}
