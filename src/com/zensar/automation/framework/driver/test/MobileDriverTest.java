package com.zensar.automation.framework.driver.test;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.zensar.automation.framework.api.DeviceApi;
import com.zensar.automation.framework.model.STFService;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class MobileDriverTest {
	
	static Logger log = Logger.getLogger(MobileDriverTest.class.getName());
	
	public static void main (String []args) throws MalformedURLException, URISyntaxException{
		AppiumDriver<WebElement> webDriver=null;
		DeviceApi deviceApi = null;
		String deviceSerial=null;
		try{
		String stfServiceURL = "https://devicefarm.gametechnology.io/api/v1/";
				//"https://devicefarm.gametechnology.io/api/v1/"; 
		// https://10.120.87.249// For ios device farm
		//AndroidDriver<WebElement> webDriver=null;
		
		deviceSerial ="99091FFAZ001B6";
				//"00008101-001A4588112A001E";
				//"0A201FDD4007H8";
				//"00008030-001E05462186402E";
				//"21b857b82a0c7ece"; 
				//"RF8M21RRNVF";
		//"00008030-001E05462186402E"; //input1
		//21aaa478df0c7ece
		//2672088cc90d7ece
		//4d0066494aab4125
		
		String accessToken = "93282ac5dbc44b008e2878826e33ba91fb9b8885ed9f4896aac03d909096e0c2";
				//"e7050b25effb4ab7a5fc37840706395ed54560ee386f4fcba8529872b25291fb"; .....for ios key
				//"fb6cf92990a0404c87c05a623c7b472eb21e8cb67fd146d1888696528d623398";
				//"93282ac5dbc44b008e2878826e33ba91fb9b8885ed9f4896aac03d909096e0c2";-----for Durban device farm
				//"fb6cf92990a0404c87c05a623c7b472eb21e8cb67fd146d1888696528d623398"; //input2
		
		String osVersion="11";
		STFService stfService = new STFService(stfServiceURL, accessToken);
		
		deviceApi = new DeviceApi(stfService);
		
		boolean isDeviceConnected = deviceApi.isDeviceConnected(deviceSerial);
		// Check whether it is already checked out
		if (!isDeviceConnected) {
			deviceApi.connectDevice(deviceSerial);
			//below code commented need to add api call to get remote url  from api currently 
			String providerName = deviceApi.getDeviceProvider();
			
			String appiumServiceUrl;
			//appiumServiceUrl="http://10.120.87.248:15294/wd/hub";
					//"http://devicefarm.gametechnology.io:15406/wd/hub";
			/*if (providerName.contains("pune1")) {
				//appiumServiceUrl = "https://devicefarm.gametechnology.io/provider2/wd/hub";
				appiumServiceUrl = "http://10.120.87.250:4727/wd/hub";
				System.out.println("Appium service provider:" + appiumServiceUrl);
			} else {
				appiumServiceUrl="http://10.120.87.145:4727/wd/hub";
			}*/
			/*below is for ios device farm*/
			////http://10.120.87.249/ios/appium/10.216.45.29/20076/wd/hub
					//"http://10.120.87.249/ios/appium/10.216.45.41/20056/wd/hub";
					//"http://10.216.45.41:20096/wd/hub";
			//applicationName: ce031713cd9c4c1301, browserName: Chrome, deviceName: ce031713cd9c4c1301, maxInstances: 1, platform: ANDROID, platformName: Android, seleniumProtocol: WebDriver, server:CONFIG_UUID: d49a3d29-30fc-430f-853e-e45..., version: 8.0.0}
			
			appiumServiceUrl="https://devicefarm.gametechnology.io/selenium/wd/hub";
			
			DesiredCapabilities capabilities = new DesiredCapabilities();
			/*below for durban andriod*/
			capabilities.setCapability("deviceName", deviceSerial);
			capabilities.setCapability("udid", deviceSerial);
			capabilities.setCapability("platformVersion", osVersion);
			capabilities.setCapability("platformName", "Android");
			
			capabilities.setCapability("browserName", "Chrome");
			//capabilities.setCapability("chromedriverExecutableDir", "/root/chromedrivers/");
			
			
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("'w3c'=False");
			capabilities.setCapability("deviceReadyTimeout", "10");
			
			
	
			
			//below capabilities are exesting chrome capabilities for Andriod
			/*capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("deviceName", deviceSerial);
            capabilities.setCapability("udid", deviceSerial);
            capabilities.setCapability("platformVersion", osVersion);
            capabilities.setCapability(CapabilityType.BROWSER_NAME, "Chrome");
           capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS,true);
            capabilities.setCapability("chromedriverExecutableDir","/root/chromedrivers/");
            capabilities.setCapability("newCommandTimeout", "120000");
            ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("'w3c'=False");
			chromeOptions.addArguments("--enable-automatic-password-saving");
			chromeOptions.setAcceptInsecureCerts(true);
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			*/
			/*capabilities.setCapability("deviceName",deviceSerial);
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("newCommandTimeout", 90000);
*/
			webDriver = new AndroidDriver<>(new URL(appiumServiceUrl), capabilities);
		//	webDriver = new IOSDriver<>(new URL(appiumServiceUrl), capabilities);
			webDriver.getCapabilities().getBrowserName();
			
			//driver.context("CHROMIUM");
			webDriver.navigate().to("https://google.co.in");
			//driver.navigate().to("https://mobilewebserver9-zensardev.installprogram.eu/MobileWebGames/game/mgs/10_1_0_7714?moduleID=10576&clientID=40300&gameName=wackyPanda&gameTitle=Wacky%20Panda&LanguageCode=en&clientTypeID=40&casinoID=5007&lobbyName=IslandParadise&loginType=InterimUPE&lobbyURL=https://mobilewebserver9-ZensarDev.installprogram.eu/Lobby/en/IslandParadise/Games/3ReelSlots&xmanEndPoints=https://mobilewebserver9-ZensarDev.installprogram.eu/XMan/x.x&bankingURL=https://mobilewebserver9-ZensarDev.installprogram.eu/Lobby/en/IslandParadise/Banking&routerEndPoints=&isPracticePlay=false&username=player234&password=test&host=Mobile&apicommsenabled=true&launchtoken=&version=");
			WebDriverWait wait= new WebDriverWait(webDriver, 60);
			Thread.sleep(5000);
			//System.out.println(webDriver.getPageSource());
		//	wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@title='Search']"))));
	//		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@id='logo']")));

		//	webDriver.findElementById("logo");
			
            
            
            
			
			//capabilities.acceptInsecureCerts();
		    //capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			//capabilities.setCapability("newCommandTimeout", "120000");

			/*driver.context("NATIVE_APP");
			driver.rotate(ScreenOrientation.LANDSCAPE);
			Thread.sleep(5000);
			int height=driver.manage().window().getSize().height;
			int width=driver.manage().window().getSize().width;
			Thread.sleep(5000);
			
			System.out.println("Height="+height+"\nWidth="+width);
			
			driver.context("CHROMIUM");
			Thread.sleep(3000);
			//Get the actual device size
			JavascriptExecutor js = ((JavascriptExecutor)driver);
			
			int webViewWidth = ((Long) js.executeScript("return window.screen.width * window.devicePixelRatio")).intValue();
			int webViewHeight = ((Long) js.executeScript("return window.screen.height * window.devicePixelRatio")).intValue();

			
			String javascript = "return arguments[0].driver.window.screen.width * driver.window.devicePixelRatio";
			
			Map<String,Object> screenWidth= (Map<String,Object>)js.executeScript(javascript);
			//("Your screen resolution is: " + window.screen.width * window.devicePixelRatio + "x" + window.screen.height * window.devicePixelRatio)

			System.out.println(" screenWidth="+webViewWidth+"\n scrrenHeight="+webViewHeight);*/
		}else{
			System.out.println("Device not connected");
		}
		if(webDriver != null) {
			webDriver.quit();
		}
		deviceApi.releaseDevice(deviceSerial);
		}catch(Exception e)
		{
			e.printStackTrace();
			webDriver.quit();
			deviceApi.releaseDevice(deviceSerial);
			
			
		}
		
	}
	
}	
