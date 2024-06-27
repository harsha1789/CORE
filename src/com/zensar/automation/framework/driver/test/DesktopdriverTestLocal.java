package com.zensar.automation.framework.driver.test;

import java.io.IOException;

import com.zensar.automation.framework.driver.DesktopDriver;
import com.zensar.automation.framework.library.PropertyReader;
import com.zensar.automation.framework.library.UnableToLoadPropertiesException;

public class DesktopdriverTestLocal {

	public static void main(String[] args) {
 
		DriverTest();
	}

	public static void DriverTest() {

		DesktopDriver dDriver = new DesktopDriver();
		String username = "player901";
		String browser = "Chrome";
		String osPlatform = "Windows";
		String gameName = "GoldFactory";
		
		
		System.setProperty("logfilename",gameName+"/Selenium");
		
		try {
				PropertyReader.getInstance().loadDefaultProperties();
				PropertyReader.getInstance().loadGameProperties("GoldFactory");
				dDriver.test(browser, username   ,osPlatform, gameName);
		} catch (UnableToLoadPropertiesException e) {
				e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	

}
