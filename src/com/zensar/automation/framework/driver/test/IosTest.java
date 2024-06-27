package com.zensar.automation.framework.driver.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.ios.IOSDriver;

public class IosTest {

	public static void main(String[] args) {
		try{
		//Create driver object	
			//IOSDriver<WebElement> driver=null;//Was in selenium 3.141.59
			IOSDriver driver=null; //Was in selenium 4
		String deviceid="00008027-001309861162402E";
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		String appiumServiceUrl = "http://10.216.45.41:20091/wd/hub";
		
		Thread.sleep(3000);
		System.out.println("Appium Service Address : - "+ appiumServiceUrl);
		//Set the desire capabilities for browser

		capabilities.setCapability("deviceName", "iPad12Pro_ZD3229");
		capabilities.setCapability("platformName", "iOS");
		capabilities.setCapability("platformVersion", "14.0");
		capabilities.setCapability("udid", deviceid);
		capabilities.setCapability("newCommandTimeout", 60000);
		capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");
		capabilities.setCapability("nativeWebTap",true);
		capabilities.setCapability("autoWebview",true);
	   
	    //create driver object by passing the remote/local appium url
	    driver = new IOSDriver<WebElement>(new URL(appiumServiceUrl), capabilities); 
		// driver = new IOSDriver<WebElement>(new URL("http://127.0.0.1:4723/wd/hub"),capabili
		driver.get("https://mobilewebserver9-zensarqa2.installprogram.eu/HtmlGames/2.18.2/launch/mgs/immortalRomanceWowPotDesktop?gameTitle=Immortal%20Romance%20WowPot&moduleid=15061&clientid=50300&clienttypeid=40&username=player71&password=test&brand=IslandParadise&productid=5007&languagecode=en&isPracticePlay=false&formfactor=Desktop");
		Thread.sleep(10000);
		String immortalEncodedString = getImageAsEncodedString("D:\\");
		driver.findElementByImage(immortalEncodedString);
		Thread.sleep(3000);
		
		String spinEncodedString = getImageAsEncodedString("D:\\");
		driver.findElementByImage(spinEncodedString);
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
    
	public static String getImageAsEncodedString(String path) {
	        String imgEncodeString = null;
	        File refImgFile = new File(path);


	        try {
	            if (refImgFile.exists()) {
	                imgEncodeString = Base64.getEncoder().encodeToString(Files.readAllBytes(refImgFile.toPath()));
	            }
	        }catch (IOException e) {
	            System.out.println(e.getMessage());
	            e.printStackTrace();
	        }


	        return imgEncodeString;
	    }

}