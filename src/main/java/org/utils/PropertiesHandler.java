package org.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeSuite;

public class PropertiesHandler {
	protected static Properties property = null;
	@BeforeSuite
	//Load the Property File
	public static void setPropertiesFile() throws IOException {
		String filePath = ".//src//main//resources//config.properties";
		FileReader file = new FileReader(filePath);
		property = new Properties();
		property.load(file);
	}
	//Get the Url From Property File
	public static String getURL() throws IOException {
		PropertiesHandler.setPropertiesFile();
		return property.getProperty("URL");
	}
}
