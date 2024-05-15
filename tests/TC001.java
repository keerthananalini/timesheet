package org.beCognizant.tests;

import org.becognizant.pages.BeCognizantHome;
import org.cognizant.base.CognizantBase;
import org.oneCognizant.pages.OneCognizantHome;
import org.oneCognizant.pages.OneCognizantTimeSheet;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.utils.Utilities;

public class TC001 extends CognizantBase{

	@Test
	public void page1test() {
		BeCognizantHome cogzHome = new BeCognizantHome(driver);

		//TODO: need to verify for user info btn

		Assert.assertEquals(cogzHome.checkOneCogzBtn(), true);
		cogzHome.clickOneCogzBtn();
		logger.info("One Cognizant Button Clicked");
		
		Utilities util = new Utilities(driver);
		util.switchToWindow(2);
		
		OneCognizantHome oneHome = new OneCognizantHome(driver);
//		Assert.assertEquals(oneHome.checkSearchInput(), true);
		oneHome.sendSearchText("Timesheet");
		oneHome.clickTimeSheetBtn();
		
//		util.switchWindow("Timesheet Landing Component");
//		new WebDriverWait(driver,Duration.ofSeconds(30)).until(ExpectedConditions.numberOfWindowsToBe(3));
//		driver.switchTo().window(driver.getWindowHandles().toArray()[2].toString());
		util.switchToWindow(3);
		OneCognizantTimeSheet time = new OneCognizantTimeSheet(driver);
		time.getWeekDetails();
		time.systemdate();
		time.selectDate();
		time.selectCalBtn();
		System.out.println(getTitle());


	}


}
