 package com.zensar.automation.framework.driver.test;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.PointerInput.MouseButton;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;


import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;



/**
 * Test program to test the connection with SauceLab
 * @author ak47374
 *
 */
public class SauceLabMobileDriverTest {
	
	
	static	Logger log = Logger.getLogger(SauceLabMobileDriverTest.class.getName());
	public static void main (String []args) throws MalformedURLException{
		try{
			
			//AppiumDriver<WebElement> driver=null; sel 3.141.59
			AppiumDriver driver=null; //sel 4
			
	
			DesiredCapabilities capabilities = new DesiredCapabilities();
		    capabilities.setCapability("testobject_api_key", "CA4FB67971A14D1D9A11204ABC0B00F2");
		    capabilities.setCapability("deviceName","iPhone_6_Plus_real_us");
		   // capabilities.setCapability("deviceOrientation", "PORTRAIT");
		    capabilities.setCapability("browserName", "Safari");
		    /*
		     capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "120000");
		    capabilities.setCapability("deviceName", deviceid);
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("platformVersion", osVersion);
			capabilities.setCapability("udid", deviceid);
			capabilities.setCapability("newCommandTimeout", 60000);
			capabilities.setCapability(CapabilityType.BROWSER_NAME, "safari");*/
			
		    
		  //  driver = new AppiumDriver<WebElement>(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities); 
		    driver = new AppiumDriver(new URL("https://us1.appium.testobject.com/wd/hub"), capabilities); 
			
			driver.get("https://mobilewebserver9-zensardev.installprogram.eu/MobileWebGames/game/mgs/10_2_2_7803?moduleID=10008&clientID=40303&gameName=treasurePalace&gameTitle=Treasure%20Palace&LanguageCode=en&clientTypeID=40&casinoID=5007&lobbyName=IslandParadise&loginType=InterimUPE&lobbyURL=https://mobilewebserver9-ZensarDev.installprogram.eu/Lobby/en/IslandParadise/Games/3ReelSlots&xmanEndPoints=https://mobilewebserver9-ZensarDev.installprogram.eu/XMan/x.x&bankingURL=https://mobilewebserver9-ZensarDev.installprogram.eu/Lobby/en/IslandParadise/Banking&routerEndPoints=&isPracticePlay=false&username=player987&password=test&host=Mobile&apicommsenabled=true&launchtoken=&version=&gameversion=");
			Thread.sleep(10000);
			Dimension size = driver.manage().window().getSize();
		 	int anchor = (int) (size.width * 0.5);
	        int startPoint = (int) (size.height * 0.9);
	        int endPoint = (int) (size.height * 0.1);
      //  new TouchAction(driver) .press(point(anchor, startPoint)).waitAction(waitOptions(ofMillis(1000))).moveTo(point(anchor, endPoint)).release().perform();  //Supported for selenium 3.141.59

        // Assuming 'driver' is an instance of WebDriver and 'anchor', 'startPoint', 'endPoint' are instances of WebElement representing the respective points.

     // Create a PointerInput instance for touch actions.
     PointerInput pointer = new PointerInput(PointerInput.Kind.TOUCH, "finger1");

     // Create a sequence of actions.
     Sequence sequence = new Sequence(pointer, 0);

     // Move to the anchor element.
  // Move to the anchor element.
     sequence.addAction(pointer.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), anchor.getLocation().getX(), anchor.getLocation().getY()));


     // Press on the startPoint.
     sequence.addAction(pointer.createPointerDown(MouseButton.LEFT.asArg()));

     // Wait for 1000 milliseconds.
     sequence.addAction(pointer.createPointerPause(Duration.ofMillis(1000)));

     // Move to the endPoint.
     sequence.addAction(pointer.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), endPoint.getLocation().getX(), endPoint.getLocation().getY()));

     // Release the press.
     sequence.addAction(pointer.createPointerUp(MouseButton.LEFT.asArg()));

     // Perform the action.
   //  Action action = new Actions(driver).perform();
  // Perform the actions.
     // Perform the actions.

    
  // Perform the actions.
     Action action = sequence.toAction();
     action.perform();
        
        
        Thread.sleep(5000);
        JavascriptExecutor js = ((JavascriptExecutor)driver);
		js.executeScript("return theForce.game.automation.getControlById('BetComponent').onButtonClicked('betIconButton')");
        Thread.sleep(5000);
			//driver.context("NATIVE_APP");
			//driver.rotate(ScreenOrientation.LANDSCAPE);
			/*Thread.sleep(5000);
			int height=driver.manage().window().getSize().height;
			int width=driver.manage().window().getSize().width;
			Thread.sleep(5000);
			
			System.out.println("Height="+height+"\nWidth="+width);
			
			//driver.context("CHROMIUM");
			Thread.sleep(30000);
			//Get the actual device size
			JavascriptExecutor js = ((JavascriptExecutor)driver);
			
			int webViewWidth = ((Long) js.executeScript("return window.screen.width * window.devicePixelRatio")).intValue();
			int webViewHeight = ((Long) js.executeScript("return window.screen.height * window.devicePixelRatio")).intValue();

			
			String javascript = "return arguments[0].driver.window.screen.width * driver.window.devicePixelRatio";
			
			Map<String,Object> screenWidth= (Map<String,Object>)js.executeScript(javascript);
			//("Your screen resolution is: " + window.screen.width * window.devicePixelRatio + "x" + window.screen.height * window.devicePixelRatio)

			//System.out.println(" screenWidth="+webViewWidth+"\n scrrenHeight="+webViewHeight);
		*/
		driver.close();
		
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
	}
	
}	
