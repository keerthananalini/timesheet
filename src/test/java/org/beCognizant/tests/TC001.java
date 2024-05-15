package org.beCognizant.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import org.cognizant.base.CognizantBase;
import org.becognizant.pages.BeCognizantHome;
import org.oneCognizant.pages.OneCognizantHome;
import org.oneCognizant.pages.OneCognizantTimeSheet;
import org.utils.Utilities;

import com.aventstack.extentreports.ExtentTest;

public class TC001 extends CognizantBase{

	@Test(priority = 0)
	public void page1() {
		BeCognizantHome cogzHome = new BeCognizantHome(driver);

		
		ExtentTest test01 = createTest("TC001 - page1Test");


		logger.info("Test Case: TC001 Starting.....");
		logger.info("Page1test Started.....");
		Assert.assertEquals(cogzHome.checkUserLabel(), true, "UserInfo Label not found");
		cogzHome.clickUserInfo();
		Utilities utils = new Utilities(driver);
		utils.takeScreenShot("BeCognizantPage");

		Assert.assertEquals(cogzHome.checkUserInfo(), true,"User-Details not displayed");
		cogzHome.getUserInfo();
		utils.takeScreenShot(cogzHome.getUserInfoEle(),"User-Info");
		logger.info("User-Info Received....");

		Assert.assertEquals(cogzHome.checkOneCogzBtn(), true,"One Cognizant Button not found");
		cogzHome.clickOneCogzBtn();

		logger.info("Page1test Ended.....");

		stepReport(test01,"pass", "User-Information Retrived","User-Info.png");
		

	}
	@Test(priority = 1, dependsOnMethods = {"page1"})
	public void page2() {

		Utilities util = new Utilities(driver);
		util.switchToWindow(2);

		ExtentTest test02 = createTest("TC001 - page2Test");


		logger.info("Page2test Started.....");
		OneCognizantHome oneHome = new OneCognizantHome(driver);
		oneHome.sendSearchText("Timesheet");
		logger.info("Searched for - 'Timesheet' ");
		util.takeScreenShot("oneCogz");
		oneHome.clickTimeSheetBtn();
		util.switchToWindow(3);
		logger.info("Page2test Ended.....");
		stepReport(test02, "pass", "TimeSheet Searched","oneCogz.png");
		
	}
	@Test(priority = 2, dependsOnMethods = {"page2"})
	public void page3() {
		OneCognizantTimeSheet TimeSheet_Page = new OneCognizantTimeSheet(driver);


		TimeSheet_Page.setWeekDetails();
		TimeSheet_Page.getCurDateDetails();
		TimeSheet_Page.getStatusDetails();
		TimeSheet_Page.StatusValidation();

		
	}


}
