package org.oneCognizant.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cognizant.base.CognizantBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

public class OneCognizantTimeSheet extends CognizantBase {
	public static String[] weeks = new String[3];
	public OneCognizantTimeSheet(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	//Locators
	@FindBy(id = "CTS_TS_LAND_WRK_CTS_TS_SEARCH") WebElement selectEle;
	@FindBy(id = "CTS_TS_LAND_WRK_DATE$prompt") WebElement calenderEle;
	@FindBy(id = "curdate") WebElement curDateEle;
	@FindBy(id = "CTS_TS_LAND_WRK_SEARCH") WebElement SearchEle;
	@FindBy(css = "input#CTS_TS_LAND_WRK_DATE") WebElement dateInput;
	@FindBy(id = "CTS_TS_LAND_WRK_PB_CLEAR$IMG") WebElement resetEle;
	@FindBy(id = "CTS_TS_LAND_WRK_CTS_TS_LAND_STATUS") WebElement statusEle;

	//date_Status
	@FindBy(xpath= "//span[contains(text(),'OK')]") WebElement popup_Ok_Ele;
	@FindBy(className = "ps_modal_content") WebElement popup_Ele;
	@FindBy(xpath = "//*[@id='CTS_TS_LAND_PER_CTS_TS_STATUS_LAND$0']") WebElement statusSelected_Ele;
	
	Map<String, String> timeSheetDates = new HashMap<>();
 
	//Gets last 3 week details
	public void setWeekDetails() { 
		for(int i=0;i<3;i++) {
			weeks[i] = (driver
					.findElement(By.xpath("//div[@id='win0div$ICField44_row$"+i+"']"))
					.findElement(By.xpath("//a[@id='CTS_TS_LAND_PER_DESCR30$"+i+"']")).getText());
		}
		for(String a : weeks) {
			logger.info(a);
			System.out.println(a);
		}
	}
	//Select the Option
	public void selectedOption(String option) {
		WebElement ele = element("id", "CTS_TS_LAND_WRK_CTS_TS_SEARCH");
		Select select = new Select(ele);
		select.selectByVisibleText(option);
	}
	//Click CalenderButton
	public void selectCalenderBtn() {
		calenderEle.click();
	}
	//Click CurrenDate
	public void selectCurDate() {
		curDateEle.click();
	}
	//Click SearchButton
	public void clickSearchEle() {
		SearchEle.click();
	}
	//Call Methods to Get CurrentDate
	public void getCurDateDetails() {
		selectedOption("Date");
		selectCalenderBtn();
		selectCurDate();
		clickSearchEle();
		DateValidation();

	}
	//Select "Status" & Get Status Option
	public void getStatusDetails() {
		selectedOption("Status");
		getStatusOptions();
	}
	//Click Reset Button
	public void clickResetBtn() {
		click(resetEle);
	}

	//Status dropdown handle 
	public void getStatusOptions() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			logger.info(e.getMessage());
		}
		Select statusSelect = new Select(statusEle);
		List<WebElement> options = statusSelect.getOptions();
		for(WebElement ele : options) {
			logger.info(ele.getText());
		}
	}
	//Get timsheet 3 week Date details
		public void systemdate() {
			String[] val = new String[6];
			//int l=0;
			for(int i=0;i<3;i++) {
				String[] date = weeks[i].split("To ");
				String firPt = date[0].toLowerCase();
				String secPt = date[1].toLowerCase();

				String date1 = new StringBuffer(firPt).replace(3, 4, String.valueOf(firPt.charAt(3)).toUpperCase()).toString();
				String date2 = new StringBuffer(secPt).replace(3, 4, String.valueOf(secPt.charAt(3)).toUpperCase()).toString();		
				timeSheetDates.put(date1, date2);
			}
		List<Date> dates = new ArrayList<>();
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
		for(String a : val) {

			try {
				dates.add(formater.parse(a));
			} catch (ParseException e) {

				logger.info(e.getMessage());
			}
		}
		;
		logger.info("dates-list: "+dates);
		Calendar cur = Calendar.getInstance();
		int count = 0; 
		for(int i=0;i<3;i++) {
			int week = cur.get(Calendar.WEEK_OF_YEAR)-i;
			//System.out.println(week);
			Calendar from = Calendar.getInstance();
			Calendar to = Calendar.getInstance();
			from.setFirstDayOfWeek(Calendar.SATURDAY);
			from.setTime(dates.get(count));
			to.setFirstDayOfWeek(Calendar.SATURDAY);
			to.setTime(dates.get(count+1));
			int fromweek = from.get(Calendar.WEEK_OF_YEAR);
			int toweek = to.get(Calendar.WEEK_OF_YEAR);


			logger.info("Date: "+dates.get(i)+" Week: "+week +" targerWeek: "+fromweek);
			logger.info("week == fromweek");
			logger.info("Date: "+dates.get(i+1)+" Week: "+week +" targerWeek: "+toweek);
		
			count+=2;
		}
	}
	//Verify the Date of System with TimeSheet
	public void DateValidation() {
		logger.info("********Date Validation Started********");
		System.out.println("********Date Validation Started***************************\n");
		String current_date = dateInput.getAttribute("value");
		
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy"); 
		String system_Date = ft.format(new Date()); 

		logger.info("System_Date:  "+system_Date+"\n"+"Current_Date"+current_date);
		System.out.println("\tSystem_Date: "+system_Date+"\n\t"+"Current_Date: "+current_date);
		
		if(system_Date.equals(current_date)) {
			logger.info("Date is Correct");
			System.out.println("\t\tSystem Date And Current Date Matched\n\t\t'DATE VERIFICATIOIN SUCCESSFUL'\n");
		}
		else {
			logger.info("Date is InCorrect");
			System.err.println("Date is InCorrect\nDATE VERIFICATIOIN UNSUCCESSFUL\n");
		}
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			logger.info(e.getMessage());
		}
		logger.info("********Date Validation Completed********");
		System.out.println("********Date Validation Completed*************************\n");
		
	}
	//Verify Status Of Time Sheet 
	public void StatusValidation() {
		logger.info("********Status Validation Started********");
		System.out.println("****************Status Validation Started**************************\n");

		Select SearchBy_dropDown = new Select(selectEle);
		SearchBy_dropDown.selectByVisibleText("Status");

		Select StatusDropDown = new Select(statusEle);
		List<WebElement> options = StatusDropDown.getOptions();

		ExtentTest test03 = createTest("TC001 - page3Test");
		for (int i = 1; i < options.size(); i++) {
			StatusDropDown = new Select(statusEle);
			options = StatusDropDown.getOptions();

			WebElement SearchBtn = SearchEle;
			String texttoSelect = options.get(i).getText();
			StatusDropDown.selectByVisibleText(texttoSelect);
			SearchBtn.click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				logger.info(e1.getMessage());
			}
			try {
				boolean popupDisplayed = popup_Ele.isDisplayed();
				if(popupDisplayed) 
				{
					WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(30));
					wait.until(ExpectedConditions.elementToBeClickable(popup_Ok_Ele)).click();
					
					popup_Ok_Ele.click();
					//popup_Ok_Ele.click();

				}
			}catch(Exception e) {
				//popup_Ok_Ele.click();
				logger.info(e.getMessage());
			}
			String statusSelected = statusSelected_Ele.getText();

			if (texttoSelect.equals(statusSelected)) {
				logger.info("Successful -> Results Found for '" +texttoSelect +"'");
				System.out.println("\tSuccessful -> Results Found for '" +texttoSelect +"'");

			} 
			else if(statusSelected.equalsIgnoreCase(" ")){
				System.out.println("\tSuccessful -> No Results Found for '" +texttoSelect +"'");
				logger.info("Successful -> No Results Found for '" +texttoSelect +"'");
			}

			else {
				System.err.println("Failed to Select '"+texttoSelect +"'");
				logger.info("\tFailed to Select '"+texttoSelect +"'");
			}

			getUtil(driver).takeScreenShot(texttoSelect);
			stepReport(test03,"pass", texttoSelect + " Searched", texttoSelect+".png");
		}
		System.out.println("\n****************Status Validation Completed************************");
		logger.info("********Status Validation Completed********");
		
	}


}
