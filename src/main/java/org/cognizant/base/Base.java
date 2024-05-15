package org.cognizant.base;

import java.io.IOException;

import org.openqa.selenium.WebElement;

public interface Base {
	/**
	 * This method will launch the chrome browser with the given URL
	 * @author Phantom Troupe
	 * @param url
	 
	 */
	void setURL() throws IOException;
	
	/**
	 *	This function is used to close the active browser
	 * 	@author Phantom Troupe
	 *
	 */
	void close();

	
	/**
	 * This function is used to switch to specific tab
	 * @author Phantom Troupe
	 * @param i
	 */
	void switchToWindow(int i);

	/**
	 * This function is used to select the dropdown with the given value
	 * @author Phantom Troupe
	 * @param ele
	 * @param value
	 */
	void selectValue(WebElement ele, String value);


	/**
	 * This function is used to select the dropdown with the given text
	 * @author Phantom Troupe
	 * @param ele
	 * @param text
	 */
	void selectText(WebElement ele, String text);

	/**
	 * This function is used to select the dropdown with the given index
	 * @author Phantom Troupe
	 * @param ele
	 * @param position
	 */
	void selectIndex(WebElement ele, int position);
	
	/**
	 * This function will wait until the element is clickable and then click
	 * @author Phantom Troupe
	 * @param ele
	 */
	void click(WebElement ele);
	
	/**
	 * This function will wait until the element is ready and clear the existing value and type
	 * @author Phantom Troupe
	 * @param ele
	 */
	void type(WebElement ele, String testData);
	
	/**
	 * This function will wait until the element is ready and gets the input
	 * @author Phantom Troupe
	 * @param ele
	 */
	/**
	 * 
	 * @param ele
	 * @param testData
	 */
	void appendText(WebElement ele, String testData);
	
	/**	
	 * This function return the active page title
	 * @author Phantom Troupe
	 * @return String
	 */
	String getTitle();

	/**	
	 * This function return the active page URL
	 * @author Phantom Troupe
	 * @return String
	 */
	String getURL();
	
	/**	
	 * This function return the element is visible or not
	 * @author Phantom Troupe
	 * @return boolean
	 */
	boolean isDisplayed(WebElement ele);
}
