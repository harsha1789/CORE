package Modules.Regression.FunctionLibrary;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
import org.sikuli.script.Screen;

import com.zensar.automation.framework.report.Desktop_HTML_Report;
import com.zensar.automation.framework.utils.Constant;
import com.zensar.automation.framework.utils.Util;

//import bsh.util.Util;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.core.har.HarEntry;

public class CFNLibrary_Desktop_CS extends CFNLibrary_Desktop {
	long Avgquickspinduration = 0;
	long Avgnormalspinduration = 0;
	public Logger log = Logger.getLogger(CFNLibrary_Desktop_CS.class.getName());

	public BrowserMobProxyServer proxy;
	public String LobbyBalance;
	public double lobbybals;
	public String bet = null;
	double totalWinNew = 0;
	WebDriverWait Wait;

	Util clickAt = new Util();

	public CFNLibrary_Desktop_CS(WebDriver webdriver, BrowserMobProxyServer browserproxy1, Desktop_HTML_Report report,
			String gameName) throws IOException {
		super(webdriver, browserproxy1, report, gameName);
		this.GameName = gameName.trim();

	}

	/**
	 * Date: 13/11/2017 Author: Ashish Kshatriya Description: This function is
	 * used for get Webelement text Parameter: By locator
	 */
	public String func_GetText(String locator) {
		try {
			WebElement ele = webdriver.findElement(By.xpath(locator));
			return ele.getText();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	/**
	 * Date: 13/11/2017 Author: Ashish Kshatriya Description: This function is
	 * used for get Webelement text Parameter: By locator
	 */
	public String func_GetText_BYID(String locator) {
		try {
			WebElement ele = webdriver.findElement(By.id(locator));
			return ele.getText();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public boolean ISAutoplayAvailable() {
		boolean autoplay;
		try {
			autoplay = webdriver.findElement(By.id(XpathMap.get("AutoplayID"))).isDisplayed();
		} catch (Exception e) {
			log.error("Autoplay id button is not visible", e);
			autoplay = false;
		}
		return autoplay;
	}

	/* Sneha Jawarkar: Wait for Spin button */
	public boolean waitForSpinButtonstop() {
		try {
			System.out.println("Waiting for spinbutton active to come after completion of FreeSpin");
			while (true) {
				/*
				 * elementWait(
				 * "return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('SpinButtonComponent').Buttons.spinButton.currentState"
				 * , "active");
				 */
				return true;
			}
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * * Date: 14/05/2019 Author: Sneha Jawarkar. Description: This function is
	 * used in GTR_freegame Parameter: play letter
	 */
	public void ClickBaseSceneDiscardButton() {
		try {
			/*
			 * clickAtButton(
			 * "return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesRemainingComponent').onButtonClicked('deleteButton')"
			 * ); System.out.println("Clicked on basescene Discard Button");
			 */ } catch (Exception e) {
			System.out.println("Can not Clicked on Basescene Discard Button");
		}
	}

	/**
	 * * Date: 17/05/2019 Author: Sneha Jawarkar. Description: This function is
	 * used in GTR_freegame Parameter: resume play button
	 */
	public boolean ClickFreegameResumePlayButton() {
		boolean b = false;
		try {
			/*
			 * JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			 * Coordinates coordinateObj = new Coordinates(); String align =
			 * "return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesComponent').views.freeGamesResumeView.Buttons.resumeButton.label.currentStyle.alignment"
			 * ; typeCasting(
			 * "return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesResumeView.resumeButton').x"
			 * , coordinateObj); coordinateObj.setX(coordinateObj.getSx());
			 * typeCasting(
			 * "return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesResumeView.resumeButton').y"
			 * , coordinateObj); coordinateObj.setY(coordinateObj.getSx());
			 * typeCasting(
			 * "return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesResumeView.resumeButton').height"
			 * , coordinateObj); coordinateObj.setHeight(coordinateObj.getSx());
			 * typeCasting(
			 * "return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesResumeView.resumeButton').width"
			 * , coordinateObj); coordinateObj.setWidth(coordinateObj.getSx());
			 * coordinateObj.setAlign((js.executeScript(align)).toString());
			 * getComponentCenterCoordinates(coordinateObj);
			 * clickAtCoordinates(coordinateObj.getCenterX(),
			 * coordinateObj.getCenterY()); Thread.sleep(2000); b = true;
			 */ } catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	/**
	 * This method is used to wait till FS scene loads Author: Sneha Jawarkar
	 */
	public boolean FreeSpinEntryScene() {
		try {
			log.debug("After refreshing,waiting for free spin's scene to come");
			Thread.sleep(10000);
			/*
			 * String currentScene =
			 * GetConsoleText("return mgs.mobile.casino.slotbuilder.v1.automation.currentScene"
			 * ); if (currentScene.equalsIgnoreCase("FREESPINS"))
			 */ return true;
			// System.out.println("free spins are started.");

		} catch (Exception e) {
			log.error("error while waiting for free spin scene", e);
			return false;
		}
	}

	/*
	 * Date: 16/05/2019 Author:Sneha Jawarkar Description: Freegame_GTR
	 * Parameter: NA
	 */
	public boolean CompleteFreeGameOffer_freespin(int freegamescount) {
		try {
			for (int i = 0; i < freegamescount; i++) {
				spinclick();
				Thread.sleep(10000);
				System.out.println("click on spin");
				if (FreeSpinEntryScene()) {
					// spinclick();
					Thread.sleep(10000);
					webdriver.navigate().refresh();
					Thread.sleep(10000);
					ClickFreegameResumePlayButton();
					FS_continue();
					Thread.sleep(10000);

				}
				waitForSpinButtonstop();
			}
		} catch (Exception e) {
			log.error("Can Not Clicked on spin Button");

		}
		return false;
	}

	/*
	 * Date: 16/05/2019 Author:Sneha Jawarkar Description: Freegame_GTR
	 * Parameter: NA
	 */
	public boolean CompleteFreeGameOffer(int freegamescount) {
		try {
			for (int i = 0; i < freegamescount; i++) {
				spinclick();
				// write for spin button to stop
				waitForSpinButtonstop();
			}
			log.debug("Clicked on spin button");
			return true;
		} catch (Exception e) {
			log.debug("Can not complete the freegame offer", e);
			return false;
		}

	}

	/**
	 * * Date: 14/05/2019 Author: Sneha Jawarkar. Description: This function is
	 * used in GTR_freegame Parameter: play letter
	 */
	public boolean clickOnPlayLater() {
		Wait = new WebDriverWait(webdriver, 500);
		boolean b = false;
		/*
		 * try { JavascriptExecutor js = ((JavascriptExecutor) webdriver);
		 * Coordinates coordinateObj = new Coordinates(); String align =
		 * "return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesComponent').views.freeGamesOfferView.Buttons.playLaterButton.buttonData.text.layoutStyles.desktop.alignment"
		 * ;
		 * typeCasting("return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesOffersView.playLaterButton').x"
		 * ,coordinateObj); coordinateObj.setX(coordinateObj.getSx());
		 * typeCasting("return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesOffersView.playLaterButton').y"
		 * ,coordinateObj); coordinateObj.setY(coordinateObj.getSx());
		 * typeCasting("return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesOffersView.playLaterButton').height"
		 * ,coordinateObj); coordinateObj.setHeight(coordinateObj.getSx());
		 * typeCasting("return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesOffersView.playLaterButton').width"
		 * ,coordinateObj); coordinateObj.setWidth(coordinateObj.getSx());
		 * coordinateObj.setAlign((js.executeScript(align)).toString());
		 * getComponentCenterCoordinates(coordinateObj);
		 * clickAtCoordinates(coordinateObj.getX(), coordinateObj.getY());
		 * Thread.sleep(100); b = true; } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		return b;

	}

	/**
	 * Date:15/5/19 Author:Sneha Jawarkar GTR_Freegame purpose
	 */

	public void Backtogame_centerclick() {
		try {
			/*
			 * clickAtButton(
			 * "return mgs.mobile.casino.slotbuilder.v1.automation.getControlById('FreeGamesCompleteView.backToGameCenterButton')"
			 * );
			 */log.debug("Clicked at back to game");
		} catch (Exception e) {
			log.error("Can not clicked on Back to Button", e);
		}
	}

	/**
	 * Date: 21/6/2019 Author: Sneha Jawarkar Description: GTR Reelspin
	 * 
	 * @throws InterruptedException
	 */
	public long Reel_Spin_speed_Duration() throws InterruptedException {
		long Avgspinduration = 0;
		long loadingTime = 0;
		long sum = 0;
		for (int i = 0; i < 5; i++) {
			long start = System.currentTimeMillis();
			spinclick();
			waitForSpinButton();
			long finish = System.currentTimeMillis();
			long totalTime = finish - start;
			loadingTime = totalTime / 1000;
			sum = sum + loadingTime;
			log.debug("Calculation for the spin duration is running properly");
			System.out.println(i + "taken" + loadingTime);
			// newFeature();
			// summaryScreen_Wait();
		}
		log.debug("error while spin duration");
		System.out.println("Total spin duraion for the 5 spins = " + sum);
		Avgspinduration = sum / 5;
		System.out.println("Average time between 5 spins is = " + Avgspinduration);
		return Avgspinduration;
	}

	/*
	 * Date: 22/04/2019 Description:To verify Autoplay spin selection Parameter:
	 * NA
	 * 
	 * @return boolean
	 */

	public boolean autoplay_spin_selection() {
		boolean spin_autoplay;
		try {
			webdriver.findElement(By.id(XpathMap.get("AutoplayID"))).click();
			WebElement SizeSlider = webdriver.findElement(By.id(XpathMap.get("SpinSizeSlider_ID")));
			String value1 = webdriver.findElement(By.id(XpathMap.get("SpinCount_ID"))).getText();
			Actions action = new Actions(webdriver);
			action.dragAndDropBy(SizeSlider, 200, 0).build().perform();
			log.debug("drag and drop performed");

			String value2 = webdriver.findElement(By.id(XpathMap.get("SpinCount_ID"))).getText();

			if (value1.equals(value2)) {
				return spin_autoplay = false;
			} else {
				return spin_autoplay = true;
			}

		} catch (Exception e) {
			log.error("Spin count not getting change.", e);
			return spin_autoplay = true;

		}

	}

	/*
	 * Date: 25/04/2019 Description:To verify autoplay must stop when focus
	 * being removed. Parameter: NA
	 * 
	 * @return boolean
	 */
	public boolean Autoplay_focus_removed() {
		boolean focus;
		try {
			webdriver.findElement(By.id(XpathMap.get("AutoplayID"))).click();
			webdriver.findElement(By.xpath(XpathMap.get("Start_Autoplay"))).click();

			Thread.sleep(3000);

			String windowhandle = webdriver.getWindowHandle();
			webdriver.switchTo().window(windowhandle);
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_T);
			robot.keyRelease(KeyEvent.VK_CONTROL);

			// To check open windows
			ArrayList<String> tabs = new ArrayList<String>(webdriver.getWindowHandles());
			System.out.println(tabs.size());
			String window = tabs.get(1);
			webdriver.switchTo().window(window);
			webdriver.get("https://outlook.office.com");
			Thread.sleep(10000);
			webdriver.switchTo().window(tabs.get(0));
			Thread.sleep(3000);
			// closeOverlay();
			log.debug("Switch to default tab");
			// driver.switchTo().defaultContent();

		} catch (Exception e) {
			log.error("Focus not get changed");
			log.error(e.getMessage());
			return focus = false;
		}
		// webdriver.getWindowHandle();
		return focus = true;

	}

	public boolean Autoplay_focus_removed_UK() {
		boolean focus;
		try {
			clickAtButton("return " + XpathMap.get("ClickAutoPlayMoreOptionsBtn"));
			Thread.sleep(2000);
			webdriver.findElement(By.id(XpathMap.get("Start_Autoplay"))).click();
			Thread.sleep(3000);
			newTabOpen();
			focus = true;
			/*
			 * String windowhandle=webdriver.getWindowHandle();
			 * webdriver.switchTo().window(windowhandle); Robot robot= new
			 * Robot(); robot.keyPress(KeyEvent.VK_CONTROL);
			 * robot.keyPress(KeyEvent.VK_T); robot.keyRelease(KeyEvent.VK_T);
			 * robot.keyRelease(KeyEvent.VK_CONTROL);
			 * 
			 * //To check open windows ArrayList<String> tabs=new
			 * ArrayList<String>(webdriver.getWindowHandles());
			 * System.out.println(tabs.size()); String window=tabs.get(1);
			 * webdriver.switchTo().window(window);
			 * webdriver.get("https://outlook.office.com"); Thread.sleep(10000);
			 * webdriver.switchTo().window(tabs.get(0)); Thread.sleep(5000);
			 * //closeOverlay(); log.debug("Switch to default tab");
			 */

			// driver.switchTo().defaultContent();

		} catch (Exception e) {
			log.error("Error on opening new tab", e);
			focus = false;
		}
		return focus;
	}

	/*
	 * Date: 5/05/2019 Description:To verify Autoplay after free spin Parameter:
	 * NA
	 * 
	 * @return boolean
	 */

	public boolean is_autoplay_with_freespin() {
		boolean freeSpin = false;
		try {
			// webdriver.findElement(By.id(XpathMap.get("AutoplayID"))).click();
			webdriver.findElement(By.xpath(XpathMap.get("Start_Autoplay"))).click();
			Thread.sleep(5000);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spin_button"))));
			log.debug("Free spin over");
			freeSpin = true;
		} catch (Exception e) {
			log.error("Free Spins are not completed");
		}
		return freeSpin;
	}

	/*
	 * Date: 24/04/2019 Description:To verify Autoplay spin session stop
	 * Parameter: NA
	 * 
	 * @return boolean
	 */

	public boolean is_autoplay_session_end() {

		boolean spin_session;

		try {

			WebElement SizeSlider = webdriver.findElement(By.id(XpathMap.get("SpinSizeSlider_ID")));
			// String value1
			// =webdriver.findElement(By.id(XpathMap.get("SpinCount_ID"))).getText();
			Actions action = new Actions(webdriver);
			action.dragAndDropBy(SizeSlider, -500, 0).build().perform();

			webdriver.findElement(By.xpath(XpathMap.get("Start_Autoplay"))).click();

			log.debug("Clicked Auto paly..");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(XpathMap.get("Autoplay_active"))));
			log.debug("Autoplay is Active Now");

		} catch (Exception e) {
			log.error("Session not over after autoplay spin", e);
			return spin_session = false;
		}
		return spin_session = true;
	}
	/*
	 * Date: 24/04/2019 Description:To verify maximum count of spin. *Parameter:
	 * NA
	 * 
	 * @return boolean
	 */

	public boolean max_spin_chk() {
		boolean max_spin;
		try {
			Thread.sleep(5000);
			webdriver.findElement(By.id(XpathMap.get("AutoplayID"))).click();
			WebElement SizeSlider = webdriver.findElement(By.id(XpathMap.get("SpinSizeSlider_ID")));
			// String value1
			// =webdriver.findElement(By.id(XpathMap.get("SpinCount_ID"))).getText();
			Actions action = new Actions(webdriver);
			action.dragAndDropBy(SizeSlider, 600, 0).build().perform();
			log.debug("drag and drop performed");

			String value2 = webdriver.findElement(By.id(XpathMap.get("SpinCount_ID"))).getText();

			if (value2.equals("100")) {
				return max_spin = true;
			} else {
				return max_spin = false;
			}

		} catch (Exception e) {
			log.error("Session not over after autoplay spin", e);
			return max_spin = false;
		}

	}

	/*
	 * Date: 24/04/2019 Description:To verify Auto play setting window
	 * Parameter: NA
	 * 
	 * @return boolean
	 */
	public boolean is_autoplay_window() {
		boolean isautoplaywin;
		try {
			// webdriver.findElement(By.id(XpathMap.get("AutoplayID"))).click();
			Thread.sleep(3000);
			if (webdriver.findElement(By.xpath(XpathMap.get("AutoplayHeader"))).isDisplayed()) {
				log.debug("Auto play setting window is displayed");
			}
		} catch (Exception e) {
			log.error("Autoplay setting window not Displayed");
			return isautoplaywin = false;
		}
		return isautoplaywin = true;

	}

	/*
	 * Date: 25/04/2019 Description:To verify Auto play on refreshing Parameter:
	 * NA
	 * 
	 * @return boolean
	 */
	public boolean isAutoplayOnAfterRefresh() {
		boolean onrefresh;
		try {
			webdriver.findElement(By.xpath(XpathMap.get("Start_Autoplay"))).click();
			// details_append(" verify On refresh, the previous AutoPlay session
			// must not resume."," On refresh, the previous AutoPlay session
			// must not resume.","The previous AutoPlay session has not resume",
			// "pass");

			Thread.sleep(3000);
			webdriver.navigate().refresh();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spin_button"))));
			log.debug("On refresh previous autoplay session has not resume");
		} catch (Exception e) {
			log.error("On refresh previous autoplay session has resume");
			return onrefresh = false;
		}

		return onrefresh = true;
	}

	public void wait_Bonus() {
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Bonus"))));
	}

	/**
	 * Date: 13/11/2017 Author: Ashish Kshatriya Description: This function is
	 * used for click on element Parameter: By locator
	 */
	public boolean func_Click(String locator) {
		Wait = new WebDriverWait(webdriver, 300);
		boolean present;
		try {
			//Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locator)));
			WebElement ele = webdriver.findElement(By.xpath(locator));
			if (ele != null) {
				ele.click();
			}
			present = true;
		} catch (NoSuchElementException e) {
			present = false;
			e.printStackTrace();
		}
		return present;
	}

	/**
	 * 
	 * @param locator
	 * @return
	 * @author rk61073
	 */
	public boolean func_Click_BYID(String locator) 
	{
		Wait = new WebDriverWait(webdriver, 60);
		boolean present;
		try 
		{
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get(locator))));
		WebElement ele = webdriver.findElement(By.id(XpathMap.get(locator)));
		if (ele != null) 
		{
			ele.click();present = true;
		}
		else
		{
			System.out.println("Unable to Click");log.debug("Unable to Click");
			present = false;
		}
			
		}
		catch (Exception e) 
		{
			present = false;
			log.debug(e.getMessage());
		}
		return present;
}

	public String Func_navigate_Denmark(String applicationName, String urlNavigate) {
		Wait = new WebDriverWait(webdriver, 500);
		try {
			String appurl = XpathMap.get("ApplicationURL");
			webdriver.navigate().to(appurl);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("five_Reel_slot"))));
			webdriver.findElement(By.xpath(XpathMap.get("five_Reel_slot"))).click();
			// webdriver.findElement(By.xpath(XpathMap.get("applicationName")));
			Thread.sleep(4000);
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Spin_Button"))));
			GameDesktopName = webdriver.findElement(By.id(GameName)).getText();
			// JavascriptExecutor js = ((JavascriptExecutor)webdriver);
			// js.executeScript("arguments[0].scrollIntoView(true);",webdriver.findElement(By.id(applicationName)));
			webdriver.findElement(By.id(GameName)).click();
			// WebElement ele=webdriver.findElement(By.id(applicationName));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Login"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GameDesktopName;
	}

	/**
	 * Date: 15/12/2017 Autohr: Laxmikanth Kodam Description: This function used
	 * to Login to the spain lobby
	 * 
	 * @return Title
	 */
	public String Func_Login_RegulatoryMarket_Spain(String username, String password) {
		Wait = new WebDriverWait(webdriver, 500);
		String Title = null;
		try {
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).clear();
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).sendKeys(username);
			webdriver.findElement(By.xpath(XpathMap.get("password"))).clear();
			webdriver.findElement(By.xpath(XpathMap.get("password"))).sendKeys(password);

			webdriver.findElement((By.xpath(XpathMap.get("Login")))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_LobbyOK"))));
			webdriver.findElement((By.xpath(XpathMap.get("spain_LobbyOK")))).click();

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_SlotGameOverlay"))));
			Title = webdriver.getTitle();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return Title;

	}

	/* Havish Jain: Method is used to wait till Stop button */
	public void verifyStopLanguage(Desktop_HTML_Report language, String languageCode) {
		Wait = new WebDriverWait(webdriver, 50);
		try {
			func_Click(XpathMap.get("spinButtonBox"));
			log.debug("Clicked on spin button");
			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("spinButtonBox"))));
			language.detailsAppendFolder("verify the Stop button translation",
					"Stop button should translate as per respective language", "Stop button is displaying", "Pass",
					languageCode);
			log.debug("Waiting reels to stop");

			Thread.sleep(100);
		} catch (Exception e) {
			log.error("error in verifyStopLanguage method", e);
		}
	}

	/**
	 * Date: 14/11/2017 Author: Laxmikanth Kodam Description: This function is
	 * used to resize the window
	 * 
	 */

	public void resizeBrowser(int a, int b) {
		Dimension d = new Dimension(a, b);
		// Resize current window to the set dimension
		webdriver.manage().window().setSize(d);

	}

	/**
	 * Date: 14/11/2017 Author: Laxmikanth Kodam Description: This function is
	 * used to resize the window
	 * 
	 */
	public void maxiMizeBrowser() {
		// Maximize the window
		webdriver.manage().window().maximize();
	}

	/**
	 * Author: Laxmikanth Kodam Description: This function is used to login the
	 * application by entering username and password Parameter:String
	 * username,String password
	 */
	public String Func_LoginBaseScene(String username, String password) {
		Wait = new WebDriverWait(webdriver, 500);
		String Title = null;

		try {
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).clear();
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).sendKeys(username);
			webdriver.findElement(By.xpath(XpathMap.get("password"))).clear();
			webdriver.findElement(By.xpath(XpathMap.get("password"))).sendKeys(password);

			webdriver.findElement((By.xpath(XpathMap.get("Login")))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSpin")));
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			Title = GameName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Title;
	}

	/*
	 * public long Random_LoginBaseScene(String userName){ Wait=new
	 * WebDriverWait(webdriver,500); long loadingTime = 0; try {
	 * webdriver.findElement(By.xpath(XpathMap.get("userName"))).clear();
	 * 
	 * String password=XpathMap.get("Password");
	 * webdriver.findElement(By.xpath(XpathMap.get("userName"))).sendKeys(
	 * userName);
	 * webdriver.findElement(By.xpath(XpathMap.get("password"))).clear();
	 * webdriver.findElement(By.xpath(XpathMap.get("password"))).sendKeys(
	 * password);
	 * webdriver.findElement((By.xpath(XpathMap.get("Login")))).click();
	 * 
	 * long start = System.currentTimeMillis();
	 * Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
	 * XpathMap.get("clock")))); long finish = System.currentTimeMillis(); long
	 * totalTime = finish - start; loadingTime = totalTime/1000;
	 * System.out.println("Total Time for Game load in Seconds is: "+loadingTime
	 * ); //newFeature(); //summaryScreen_Wait(); } catch (Exception e) {
	 * e.printStackTrace(); } return loadingTime; }
	 */

	/**
	 * Date: 29/05/2018 Author: Havish Jain Description: This function is used
	 * to refresh the page and will take screenshot of splash screen before
	 * navigating to Base Scene. Parameter:
	 */

	public String splashScreen(Desktop_HTML_Report report, String language)
	{
		try {
			webdriver.navigate().refresh();
			if (language.equalsIgnoreCase("en"))
				acceptAlert();
			Wait = new WebDriverWait(webdriver, 40);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("preLoaderBackground"))));
			report.detailsAppendFolder("verify the Splash Screen", "Splash Screen should display",
					"Splash screen is displaying", "pass", language);
			Thread.sleep(2000);

			log.debug("Refreshed the game and taken screen shot of splsh Screen");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			Thread.sleep(2000);
			// newFeature();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void acceptAlert() {
		Wait = new WebDriverWait(webdriver, 15);
		try {
			Capabilities caps = ((RemoteWebDriver) webdriver).getCapabilities();
			String browserName = caps.getBrowserName();
			if (browserName.equalsIgnoreCase("internet explorer") || browserName.equalsIgnoreCase("firefox")) {
				Wait.until(ExpectedConditions.alertIsPresent());
				Alert alert = webdriver.switchTo().alert();
				alert.accept();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Date: 31/05/2018 Author: Havish Jain Description: This function is used
	 * to click on PLay For Real link in menu Parameter:
	 */

	public void clickPlayForReal() {
		try {
			webdriver.findElement(By.xpath(XpathMap.get("PlayForReal"))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Login"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Date: 14/11/2017 Author: Laxmikanth Kodam Description: This function is
	 * used to login the application by entering username and password
	 * Parameter:String username,String password
	 */
	public String Func_LoginBaseScene_Spain(String username, String password) {
		Wait = new WebDriverWait(webdriver, 500);
		String Title = null;
		try {
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).clear();
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).sendKeys(username);
			webdriver.findElement(By.xpath(XpathMap.get("password"))).clear();
			webdriver.findElement(By.xpath(XpathMap.get("password"))).sendKeys(password);

			webdriver.findElement((By.xpath(XpathMap.get("Login")))).click();

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_LobbyOK"))));
			Thread.sleep(1000);
			func_Click(XpathMap.get("spain_LobbyOK"));
			LobbyBalance = func_GetText(XpathMap.get("lobbyBalance"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_BackToLobby"))));
			Title = webdriver.getTitle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Title;
	}

	/**
	 * Date: 07-01-2018 Author:Laxmikanth Kodam Description: verify New Feature
	 * Dialogue is appearing on the screen
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public void newFeature() {
		try {
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_NewFeature_ClickToContinue"))));
			// boolean
			// test=webdriver.findElements(By.xpath(XpathMap.get("OneDesign_NewFeature_ClickToContinue"))).size()>0;
			boolean test = webdriver.findElement(By.xpath(XpathMap.get("OneDesign_NewFeature_ClickToContinue")))
					.isDisplayed();
			if (test) {
				Wait.until(ExpectedConditions
						.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_NewFeature_ClickToContinue"))));
				func_Click(XpathMap.get("OneDesign_NewFeature_ClickToContinue"));
				Thread.sleep(2000);
			} else {
				System.out.println("New Feature Screen is not displaying");
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Date: 17-11-2017 Author:Laxmikanth Kodam Description: Verify Credit text
	 * on the console for all the languages and validate with excel
	 */
	public String verifyCreditText() {

		String creditText = func_GetText(XpathMap.get("creditText"));
		String CreditValue = func_String_Operation(creditText);
		return CreditValue;
	}

	public String verifyCredit() {
		String CreditVal = null;
		try {
			String creditValue = func_GetText(XpathMap.get("creditBalance"));
			CreditVal = func_String_Operation(creditValue);
		} catch (Exception e) {
			e.getMessage();
		}
		return CreditVal;
	}

	/**
	 * Date:20/11/2017 Author: Laxmikanth Kodam This method used to verify the
	 * balance before spin
	 * 
	 * @return balance
	 * @throws InterruptedException
	 */
	public String verifySpinBeforeClick() throws InterruptedException {
		String defaultBalance = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("gameLogo"))));
			defaultBalance = func_GetText(XpathMap.get("creditBalanace"));
			System.out.println("The outputData is " + defaultBalance);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return defaultBalance;
	}

	/**
	 * Date:22/11/2017 Author:Laxmikanth Kodam This method used to check the
	 * currency multiplier inside settings
	 * 
	 * @return amount
	 * @throws InterruptedException
	 */
	public double currencymultiplier(String nodeValue, String attribute1, String attribute2, String attribute3)
			throws InterruptedException {
		func_Click(XpathMap.get("leftArrow"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("settings"))));
		func_Click(XpathMap.get("coinSize_Arrow"));
		func_Click(XpathMap.get("minCoinSize_Amount"));
		String minAmount = func_GetText(XpathMap.get("coinSize_Amount"));
		String value1 = func_String_Operation(minAmount);
		Double amount = Double.parseDouble(value1);
		return amount;
	}

	/**
	 * Author:Laxmikanth Kodam This method is used to calculate the respective
	 * multiplier value with coin size and min coin size
	 * 
	 * @param multipllier
	 * @return
	 */

	public double multiplierCalculation(double valu) {
		double minbal = 0.01;
		double Multiplier = valu / minbal;
		int multiplier = (int) Multiplier;
		System.out.println(multiplier);
		return multiplier;
	}

	/**
	 * Author:Premlata Mishra This method is used for verifying the status of
	 * the quick spin
	 * 
	 * @return true
	 */
	public String quickSpinStatus() {
		String opacityvalue = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("quickSpin_Toggle"))));
			WebElement elementObj = webdriver.findElement(By.xpath(XpathMap.get("quickSpin_Toggle")));
			opacityvalue = elementObj.getCssValue("opacity");
			System.out.println(opacityvalue);
		} catch (Exception e) {
			e.getMessage();
		}
		return opacityvalue;
	}

	/**
	 * Date:07/12/2017 Author:Premlata Mishra This method is used to open the
	 * settings
	 * 
	 * @return true
	 * @throws InterruptedException
	 */
	public boolean settingsOpen() throws InterruptedException {
		Wait = new WebDriverWait(webdriver, 50);
		boolean test = false;
		try {
			// in case of video poker game putlocator of deal button
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			// Clicking on hamburger menu
			func_Click(XpathMap.get("OneDesign_Hamburger"));
			log.debug("Clicked on hemburger menu button ");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_Settings"))));
			// Clicking on settings link
			func_Click(XpathMap.get("OneDesign_Settings"));
			log.debug("Clicked on settings button to open");
			Thread.sleep(2000);
			test = true;
		} catch (Exception e) {
			log.error("Error in opening setting button", e);
		}
		return test;
	}

	public String gamelogo() {
		try {
			boolean test = webdriver.findElements(By.xpath(XpathMap.get("OneDesignLogo"))).size() > 0;
			if (test) {
				String gamelogo = func_GetText(XpathMap.get("OneDesignLogo"));
				Thread.sleep(2000);
				return gamelogo;
			} else {
				System.out.println("yes button is not in the game");
			}
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * Date:07/12/2017 Author:Premlata Mishra This method is usedto open the
	 * settings
	 * 
	 * @return true
	 * @throws InterruptedException
	 */
	public void SettingsToBasescen() throws InterruptedException {
		Wait = new WebDriverWait(webdriver, 5000);
		try {
			func_Click(XpathMap.get("OneDesignSettingclose"));
			Thread.sleep(3000);
			// boolean
			// wait=webdriver.findElement(By.xpath(XpathMap.get("OneDesignMenuClose"))).isDisplayed();
			/*
			 * if(!GameName.equals("reelGemsDesktop")&&!GameName.equals(
			 * "reelGems")) {
			 * webdriver.findElement(By.xpath(XpathMap.get("OneDesignMenuClose")
			 * )).click(); }
			 */
			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("OneDesign_Spin_Button"))));
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Date:07/12/2017 Author:Laxmikanth Kodam This method is to change the bet
	 * 
	 * @return true
	 * @throws InterruptedException
	 */
	/*
	 * public boolean betDecrease() throws InterruptedException {
	 * Thread.sleep(2000); func_Click(XpathMap.get("OneDesignBetDecrement"));
	 * Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get(
	 * "OneDesignBetbtnyes")))); func_Click(XpathMap.get("OneDesignBetbtnyes"));
	 * return true; }
	 */
	public boolean betDecrease() {
		try {
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			func_Click(XpathMap.get("OneDesignBetDecrement"));
			// func_Click("//*[@id='btnDecrement']");
			/* need to uncomment for breakDaBank */
			/*
			 * boolean test1=webdriver.findElements(By.xpath(XpathMap.get(
			 * "OneDesignBetDecrement"))).size()>0; if(test1) {
			 * func_Click(XpathMap.get("OneDesignBetDecrement")); } else {
			 * System.out.println("- button is not here"); }
			 */
			/* need to uncomment for Reel gems */
			/*
			 * boolean test=webdriver.findElements(By.xpath(XpathMap.get(
			 * "OneDesignBetbtnyes"))).size()>0; if(test){
			 * Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
			 * XpathMap.get("OneDesignBetbtnyes"))));
			 * func_Click(XpathMap.get("OneDesignBetbtnyes"));
			 * Thread.sleep(2000); } else{
			 * 
			 * System.out.println("yes button is not in the game"); }
			 */ } catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Date:07/12/2017 Author:Premlata Mishra This method is to increase the bet
	 * 
	 * @return true
	 */
	/*
	 * public boolean betIncrease() {
	 * func_Click(XpathMap.get("OneDesignBetIncreament"));
	 * Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get(
	 * "OneDesignBetbtnyes")))); func_Click(XpathMap.get("OneDesignBetbtnyes"));
	 * 
	 * return true; }
	 */
	public boolean betIncrease() {

		try {
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			boolean test1 = webdriver.findElements(By.xpath(XpathMap.get("OneDesignBetIncreament"))).size() > 0;
			if (test1) {
				func_Click(XpathMap.get("OneDesignBetIncreament"));
			} else {
				System.out.println("bet button is not displaying");
			}
			boolean test = webdriver.findElements(By.xpath(XpathMap.get("OneDesignBetbtnyes"))).size() > 0;

			if (test) {
				Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("OneDesignBetbtnyes"))));
				func_Click(XpathMap.get("OneDesignBetbtnyes"));
				Thread.sleep(2000);
			} else {
				System.out.println("yes button is not in the game");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * Date:07/12/2017 Author:premlata Mishra This method is to wait till the
	 * game will load after refresh,and the it will click on new feature screen
	 * 
	 * @return true
	 */
	public String refreshWait() {
		String wait = null;
		try {
			Wait = new WebDriverWait(webdriver, 5000);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_Spin_Text"))));
			newFeature();
			wait = "wait";
		} catch (Exception e) {
			e.getMessage();
		}
		return wait;
	}

	/**
	 * Date:07/12/2017 Author:premlata Mishra This method is to get the paytable
	 * symbol amount
	 * 
	 * @return true
	 */
	public String symbol_Value(String locator) {
		String value = Get_Amount(locator);
		return value;
	}

	/**
	 * Date:07/12/2017 Author:premlata Mishra This method is to get any element
	 * value
	 * 
	 * @return true
	 */
	public String Get_Amount(String locator) {
		String Value1 = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
			String Value = func_GetText(locator);
			Value1 = func_String_Operation(Value);
		} catch (Exception e) {
			e.getMessage();
		}
		return Value1;
	}

	/**
	 * Date:07/12/2017 Author:premlata Mishra This method is to change the bet
	 * 
	 * @return true
	 */
	public double GetBetAmt() {
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_Spin_Button"))));
		String betVal = func_GetText(XpathMap.get("OneDesign_BetValue"));
		String CreditVal = func_String_Operation(betVal);
		double CreditVal1 = Double.parseDouble(CreditVal);
		return CreditVal1;
	}

	/**
	 * Date:07/12/2017 Author:premlata mishra This method is to get win amount
	 * 
	 * @return true
	 */
	public String GetWinAmt() {
		String Winamt1 = null;
		try {
			String Winamt = func_GetText_BYID(XpathMap.get("OneDesignWinTextID"));
			Thread.sleep(1000);

			if (!Winamt.isEmpty())
				Winamt1 = func_String_Operation(Winamt);
			else
				Winamt1 = "0.0";

			System.out.println("win amount " + Winamt1);
		} catch (InterruptedException e) {
			log.error("Error in getting win amount", e);
			Thread.currentThread().interrupt();
		}
		return Winamt1;
	}

	/**
	 * Date:07/12/2017 Author:premlata mishra This method is to get total bet
	 * amount
	 * 
	 * @return true
	 */
	public String Slider_TotalBetamnt() {
		String totalbet = null;
		try {
			Wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesignCoinSizeSlider"))));
			totalbet = func_GetText(XpathMap.get("OneDesigntotalbet"));
			// totalbet1=func_String_Operation(totalbet);
			System.out.println("Total Bet amount" + totalbet);
		} catch (Exception e) {
			e.getMessage();
		}
		return totalbet;
	}

	/**
	 * Date:07/12/2017 Author:premlata mishra This method is to get total bet
	 * amount
	 * 
	 * @return true
	 *//*
		 * public String BaseGame_TotalBetmnt() {
		 * Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
		 * XpathMap.get("OneDesignbetbutton")))); String
		 * totalbet=func_GetText(XpathMap.get("OneDesignbetbutton")); String
		 * totalbet1=func_String_Operation(totalbet);
		 * System.out.println("basegame totalbet"+totalbet1); return totalbet1;
		 * }
		 */
	/**
	 * Date:07/12/2017 Author:Laxmikanth Kodam This method is actually not
	 * necessery in component store just declaration needed
	 * 
	 * @return true
	 */
	public boolean settingsBack() {
		boolean test = false;
		try {
			Thread.sleep(500);
			webdriver.findElement(By.xpath(XpathMap.get("OneDesignSettingclose"))).click();
			log.debug("Clicked settings back button to close settings overlay");
			Thread.sleep(1000);
			if (webdriver.findElement(By.id(XpathMap.get("OneDesign_SettingsID"))).isDisplayed()) {
				webdriver.findElement(By.xpath(XpathMap.get("OneDesignMenuClose"))).click();
				log.debug("clicked on hemburger menu to close menu overlay");
				Thread.sleep(1000);
			}
			Thread.sleep(1000);
			test = true;
		} catch (Exception e) {
			log.error("error in closing settings", e);
		}
		return test;
	}

	/**
	 * * Date:07/12/2017 Author:premlata Mishra This method is waiting till the
	 * reel gets stop after click on spin
	 * 
	 * @return true
	 * @throws InterruptedException
	 */
	/*
	 * public String waitTillStop() throws InterruptedException {
	 * 
	 * boolean
	 * test=webdriver.findElements(By.xpath("//*[@id='respin-footer-4']")).size(
	 * )>0; if(test){
	 * while(webdriver.findElement(By.xpath("//*[@id='respin-footer-4']")).
	 * getText().isEmpty()) { Thread.sleep(500); } } else {
	 * 
	 * Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get(
	 * "spinButtonBox")))); }
	 * 
	 * Thread.sleep(500);
	 * webdriver.findElement(By.xpath("//*[@id='txtWin']")).click();
	 * Thread.sleep(1000);
	 * 
	 * return null; }
	 */
	public void waitTillStop() {
		try {
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			boolean test = webdriver.findElements(By.xpath("//*[@id='respin-footer-4']")).size() > 0;
			if (test) {
				while (webdriver.findElement(By.xpath("//*[@id='respin-footer-4']")).getText().isEmpty()) {
					Thread.sleep(500);
				}
			} else {
				Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("spinButtonBox"))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ate:22/11/2017 Author:Laxmikanth Kodam This method used to remove the $
	 * symbol from credits balance
	 * 
	 * @param value
	 * @return
	 */
	public String func_String_Operation(String value) {
		String str = value;
		String str1 = str.substring(1);
		return str1;
	}

	/**
	 * Date:22/11/2017 Author:Laxmikanth Kodam This method is used swipe the
	 * coins size from min to max coins sizes
	 * 
	 * @return true
	 * @throws Exception
	 */
	public boolean swipeMinCoinSize(double minCoinSize) throws Exception {
		try {
			List<WebElement> forms = webdriver.findElements(By.className("coinSize-cell"));
			int count = forms.size();
			func_Click(XpathMap.get("coinSize_Arrow"));
			for (int i = 3; i < count; i++) {
				WebElement current = webdriver.findElement(By.xpath("//*[@id='coinSize-content']/div[" + i + "]"));
				current.click();
				Thread.sleep(2000);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Date 26/11/2017 Author:Laxmikanth Kodam This method used to verify the
	 * Big/Super/Mega win amounts
	 * 
	 * @return total win value
	 */
	public double verify_Win_Amount(String nodeValue, String attribute1, String attribute2, String attribute3)
			throws InterruptedException {
		Wait = new WebDriverWait(webdriver, 180);
		double Bigwin = 0;
		try {
			func_Click(XpathMap.get("spinButtonBox"));
			String bigWin = func_GetText(XpathMap.get("winValue"));
			String BigWin = func_String_Operation(bigWin);
			Bigwin = Double.parseDouble(BigWin);
			System.out.println(Bigwin);
			/*
			 * String DataFromHar = clickAt.getData(nodeValue, attribute1,
			 * attribute2, attribute3,proxy); balance =
			 * DataFromHar.split(",")[0]; loyaltyBalance =
			 * DataFromHar.split(",")[1]; totalWin = DataFromHar.split(",")[2];
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		Thread.sleep(2000);
		return Bigwin;
	}

	/**
	 * Date 26/11/2017 Laxmikanth Kodam This method used to verify the Bet
	 * amount
	 * 
	 * @return the value to the win amount
	 */
	public double verify_Bet_Amount() {
		double betAmount = 0;
		try {
			String betValue = func_GetText(XpathMap.get("betValue"));
			String BetValue = func_String_Operation(betValue);
			System.out.println(BetValue);
			betAmount = Double.parseDouble(BetValue);
			System.out.println(betAmount);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return betAmount;
	}

	/**
	 * Date: 25/10/2017 Author: Kamal Kumar Vishwakarma Description: This
	 * function is used for get Data from the Common Language File provided by
	 * Derivco
	 */
	public void getCommonLanguageFile(String language) throws IOException {

		File commonLanguage = new File("languageFiles/" + language + "/language.json");
		commonLanguageContent = clickAt.removeUTF8BOM(FileUtils.readFileToString(commonLanguage, "UTF-8"));
	}

	/**
	 * Date: 04/05/2017 Author:Kamal Kumar Vishwakarma Description:
	 * verifyLanguageResponseFile use to verify Language.json Parameter:
	 * language
	 */
	/*
	 * public JSONCompareResult verifyLanguageResponseFile(String language,
	 * BrowserMobProxyServer proxy) throws InterruptedException {
	 * JSONCompareResult result = null; try { waitForPageToBeReady();
	 * //getCommonLanguageFile(language); responseLanguageContent =
	 * getResponseLanguageFile(proxy); result = compareLanguage();
	 * Thread.sleep(100); } catch (Exception e) { e.printStackTrace(); } return
	 * result; }
	 */

	public String getResponseLanguageFile(BrowserMobProxyServer proxy) {
		nhar = proxy.getHar();

		hardata = nhar.getLog();
		entries = hardata.getEntries();
		itr = entries.iterator();

		while (itr.hasNext()) {
			HarEntry entry = itr.next();
			String requestUrl = entry.getRequest().getUrl().toString();
			System.out.println(requestUrl);

			if (requestUrl.matches("(.*)resources/language(.*).json") || requestUrl.matches("(.*).eu/XMan/x.x(.*)")) {
				System.out.println("Matched**");
				responseLanguageContent = removeUTF8BOM(entry.getResponse().getContent().getText());
			}
		}
		return responseLanguageContent;
	}

	public String removeUTF8BOM(String s) {
		if (s.startsWith(UTF8_BOM)) {
			s = s.substring(1);
		}
		return s;
	}

	/**
	 * Date: 14/11/2017 Author: Laxmikanth Kodam Description: This function is
	 * used navigate the url of the application Parameter:String
	 * applicationName,String urlNavigate
	 */

	public String Func_navigate_reg(String applicationName, String urlNavigate) {
		Wait = new WebDriverWait(webdriver, 500);
		try {
			webdriver.navigate().to(urlNavigate);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("five_Reel_slot"))));

			webdriver.findElement(By.xpath(XpathMap.get("five_Reel_slot"))).click();
			Thread.sleep(4000);
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Spin_Button"))));
			GameDesktopName = webdriver.findElement(By.id(applicationName)).getText();
			webdriver.findElement(By.id(applicationName)).click();
			// func_Click(XpathMap.get("practice_Play"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GameDesktopName;
	}

	/**
	 * Date: 10/1/2018 Autohr: Laxmikanth Kodam Description: This function used
	 * for navigating to Gibraltar url
	 * 
	 * @return true
	 */
	public String func_navigate_DirectURL(String urlNavigate) {
		Wait = new WebDriverWait(webdriver, 500);
		String title = null;
		try {
			// urlNavigate=XpathMap.get("app_url_Gibraltar_Desktop");
			webdriver.navigate().to(urlNavigate);
			newFeature();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			title = webdriver.getTitle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return title;
	}

	/**
	 * Date: 10/1/2018 Autohr: Laxmikanth Kodam Description: This function used
	 * to close the popup
	 */
	public void checkPopUp() {
		try {
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			boolean test = webdriver.findElements(By.xpath(XpathMap.get("Gibraltar_CrossMark"))).size() > 0;
			if (test) {
				func_Click(XpathMap.get("Gibraltar_CrossMark"));
			} else {
				System.out.println("POP UP not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Date: 10/1/2018 Autohr: Laxmikanth Kodam Description: This function used
	 * for verifying the help icon
	 * 
	 * @return
	 */
	public boolean verifyHelp() {
		boolean ret = false;
		try {
			boolean test = webdriver.findElement(By.xpath(XpathMap.get("Gibraltar_HelpLink"))).isDisplayed();
			if (test) {
				ret = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * Date: 10/1/2018 Autohr: Laxmikanth Kodam Description: This function used
	 * for clicking help icon
	 * 
	 * @return
	 */
	public String clickHelp() {
		String GoogleTitle = null;
		try {
			func_Click(XpathMap.get("Gibraltar_HelpLink"));
			Wait.until(ExpectedConditions.titleContains("Google"));
			GoogleTitle = webdriver.getTitle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return GoogleTitle;
	}

	/**
	 * Date: 13/12/2017 Autohr: Laxmikanth Kodam Description: This function used
	 * for clicking left arrow & navigate to settings page
	 * 
	 * @return true
	 */
	public boolean navigateSettings(String OD) throws Exception {
		Wait = new WebDriverWait(webdriver, 500);
		if (OD.equalsIgnoreCase("onedesign")) {
			settingsOpen();
		} else {
			webdriver.switchTo().defaultContent();
			func_Click(XpathMap.get("leftArrow"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("settings"))));
			Thread.sleep(3000);
		}
		return true;
	}

	/**
	 * Author:Premlata Mishra This method is used to open the settings
	 * 
	 * @return true
	 * @throws InterruptedException
	 */
	public boolean verifyQuickSpin(String imagepath) {
		boolean test = false;
		try {
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			// test=webdriver.getPageSource().contains("Quick Spin");
			test = webdriver.findElement(By.xpath(XpathMap.get("QuickSpin"))).isDisplayed();
			if (test) {
				return test = true;
			}
		} catch (Exception e) {
			return test = false;
		}
		return test;
	}

	/**
	 * This method is used to check stop is avalable or not Author: Premlata
	 * Mishra
	 * 
	 * @return true
	 */
	public boolean VerifyStop(String imagepath) {
		boolean test = false;
		try {
			webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Spin_Button"))));
			func_Click(XpathMap.get("Spin_Button"));
			test = webdriver.findElement(By.xpath(XpathMap.get("Stop_Text"))).isDisplayed();
			// test=webdriver.getPageSource().contains("stop text");
			if (test) {
				return test = true;
			}
		} catch (Exception e) {
			return test = false;
		}
		return test;
	}

	/**
	 * This method is used to check flag is available or not Author: Premlata
	 * Mishra
	 * 
	 * @return true
	 */
	public String verifyFlag() {
		boolean test = false;
		String flag = null;
		try {
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			test = webdriver.findElement(By.id(XpathMap.get("Denmark_FlagID"))).isDisplayed();
			if (test) {
				webdriver.findElement(By.id(XpathMap.get("Denmark_FlagID"))).click();
				flag = webdriver.findElement(By.id(XpathMap.get("Denmark_FlagID"))).getText();
			}
		} catch (Exception e) {
			flag = null;
		}
		return flag;
	}

	/**
	 * Author:Havish Jain This method is used validate Time limit reached
	 * scenario. Click continue in Reminder popup and wait till time limit is
	 * reached
	 * 
	 * @return true
	 * @throws InterruptedException
	 */
	public void waitUntilTimeLimitSession(Desktop_HTML_Report tc10) {
		try {
			for (int i = 0; i <= 5; i++) {
				spainContinueSession();
				boolean b = webdriver.findElements(By.xpath(XpathMap.get("spain_CloseBtn"))).size() > 0;
				if (b) {
					break;
				}
			}
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_CloseBtn"))));
			tc10.detailsAppend("To check if Time Limit Summary overlay appear when Time limit is reached",
					"Time Limit Summary overlay should appear", "Time Limit summary overlay appears", "Pass");
			func_Click(XpathMap.get("spain_CloseBtn"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("five_Reel_slotID"))));
			tc10.detailsAppend(
					"To verify user navigates to lobby after clicking on close button when Time limit is reached",
					"User should redirect to lobby after clicking on close button when Time limit is reached",
					"User is redirected to lobby", "Pass");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Author: Laxmikanth Kodam Description: This function used to verify the
	 * spin button
	 * 
	 * @return true
	 */
	public boolean verifySpin(String image) {
		func_Click(XpathMap.get("Spin_Button"));
		return true;
	}

	/**
	 * Date: 13/12/2017 Autohr: Laxmikanth Kodam Description: This function used
	 * to verify the responsible gaming text
	 * 
	 * @return text
	 */
	public boolean verifyResponsibleLink(String imagepath) {
		String text = func_GetText(XpathMap.get("responsibleGaming_Text"));
		System.out.println(text);
		boolean ret = false;
		if (text != null) {
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

	/**
	 * Date: 13/12/2017 Autohr: Laxmikanth Kodam Description: This function used
	 * to verify the responsible gaming text
	 * 
	 * @return text
	 */
	public boolean verifyResponsibleLink_working(String imagepath, Desktop_HTML_Report report) {

		boolean ret = false;
		try {
			func_Click(XpathMap.get("responsibleGaming_Text"));
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
			ret = false;
		}
		return ret;
	}
	/**
	 * @throws InterruptedException
	 */
	/*
	 * public boolean verifyGameFlag() throws InterruptedException{
	 * func_Click(XpathMap.get("responsible_Image")); return true; }
	 */

	/**
	 * Autohr: Laxmikanth Kodam Description: This function used to login the
	 * application
	 * 
	 * @return Title
	 */

	public boolean Func_LoginBaseScene_Italy(String username, String password) {
		Wait = new WebDriverWait(webdriver, 500);
		String Title = null;
		boolean present = false;
		try {
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).clear();
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).sendKeys(username);
			webdriver.findElement(By.xpath(XpathMap.get("password"))).clear();
			webdriver.findElement(By.xpath(XpathMap.get("password"))).sendKeys(password);

			webdriver.findElement((By.xpath(XpathMap.get("Login")))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("italyHeader"))));
			/*
			 * Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
			 * (XpathMap.get("preLoaderBackground"))));
			 * Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath
			 * (XpathMap.get("spinButtonBox"))));
			 */
			Title = webdriver.getTitle();
			if (Title != null) {
				present = true;
			} else {
				present = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return present;
	}

	/**
	 * Date: 30/05/2018 Autohr: Havish Jain Description: This function used to
	 * click on Practice Play when Login Popup is open
	 * 
	 * @return Title
	 */

	public String func_LoginPracticePlay() {
		Wait = new WebDriverWait(webdriver, 500);
		String Title = null;
		try {
			func_Click(XpathMap.get("practice_Play"));
			long start = System.currentTimeMillis();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			long finish = System.currentTimeMillis();
			long totalTime = finish - start;
			System.out.println("Total Time for Game load in Seconds is - " + totalTime);
			Title = webdriver.getTitle();
			newFeature();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Title;
	}

	/**
	 * Date: 14/12/2017 Autohr: Laxmikanth Kodam Description: This function used
	 * to verify the game dialog
	 * 
	 * @return header
	 */
	public String incomplete_GameName(String gamename) {
		String incomplete_GameName = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("five_Reel_slot"))));
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			js.executeScript("arguments[0].scrollIntoView(true);", webdriver.findElement(By.id(gamename)));

			js.executeScript("arguments[0].click();", webdriver.findElement(By.id(gamename)));
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			webdriver.findElement(By.id(XpathMap.get("General_Error_ID"))).isDisplayed();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("General_Error_ID"))));
			incomplete_GameName = webdriver.findElement(By.id(XpathMap.get("General_Error_ID"))).getText();
		} catch (Exception e) {
			incomplete_GameName = null;
		}
		System.out.println("incompletegamegame" + incomplete_GameName);
		return incomplete_GameName;
	}

	public String isGamePlay() {
		Wait = new WebDriverWait(webdriver, 500);
		String header = null;
		try {
			header = func_GetText(XpathMap.get("italyHeader"));
			System.out.println(header);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return header;
	}

	/*
	 * Date: 03/04/2017 Author: Author: Dhairaya Gautam Description: This
	 * function used for page load
	 */
	public void clickamount(Desktop_HTML_Report italy, String amount) {
		try {
			Thread.sleep(1000);
			webdriver.findElement(By.id("userInput")).sendKeys(amount);
			Thread.sleep(1000);
			webdriver.findElement(By.id(XpathMap.get("italySubmit_ID"))).click();
			Thread.sleep(3000);
			italy.detailsAppend(" Verify that Take to Game screen appears ", " Take to Game screen should appear ",
					" Take to Game screen is appearing ", "Pass");
			webdriver.findElement(By.id(XpathMap.get("italySubmit_ID"))).click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Date: 14/12/2017 Autohr: Laxmikanth Kodam Description: This function used
	 * to take to the game
	 * 
	 * @return null
	 */
	public String taketoGame() {
		// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSpin")));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
		return null;
	}

	/**
	 * Date: 14/12/2017 Autohr: Laxmikanth Kodam Description: This function used
	 * to take to the game
	 * 
	 * @return null
	 */
	public String verifyCreditsValue() {
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("clock_ID"))));
		String aval = func_GetText(XpathMap.get("OneDesign_Credit_Balance"));
		String val = func_String_Operation(aval).replace(",", "");
		// double val1=Double.parseDouble(val);
		return val;
	}

	/**
	 * Date: 15/12/2017 Autohr: Laxmikanth Kodam Description: This function used
	 * to Login to the spain lobby
	 * 
	 * @return Title
	 */
	public String Func_Login_RegulatoryMarket_Spain(String username, String password, String appName) {
		Wait = new WebDriverWait(webdriver, 500);
		String Title = null;
		try {
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).clear();
			webdriver.findElement(By.xpath(XpathMap.get("userName"))).sendKeys(username);
			webdriver.findElement(By.xpath(XpathMap.get("password"))).clear();
			webdriver.findElement(By.xpath(XpathMap.get("password"))).sendKeys(password);

			webdriver.findElement((By.xpath(XpathMap.get("Login")))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_LobbyOK"))));
			webdriver.findElement((By.xpath(XpathMap.get("spain_LobbyOK")))).click();

			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_SlotGameOverlay"))));
			Title = webdriver.getTitle();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Title;
	}

	/*
	 * 
	 * Author: Havish Jain Description:Overlay popup for Set Session Limit for
	 * regulatory market Spain Parameter: N/A
	 */
	public boolean overLay() throws InterruptedException {
		try {
			boolean SetSessionLimits = webdriver.findElements(By.xpath(XpathMap.get("spain_SlotGameOverlay")))
					.size() > 0;
			if (SetSessionLimits)
				return true;
			else
				return false;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Date: 15/12/2017 Autohr: Laxmikanth Kodam Description: This function used
	 * to Set limits on the spain lobby overlay
	 * 
	 * @return Title
	 */
	public String setLimit(String losslimit) throws InterruptedException {
		Wait = new WebDriverWait(webdriver, 120);
		try {
			// Setting TimeLimit
			func_Click(XpathMap.get("spain_TimeLimit"));
			Select TimeLimit = new Select(webdriver.findElement(By.xpath(XpathMap.get("spain_TimeLimit"))));
			TimeLimit.selectByVisibleText("10 m");
			Thread.sleep(1000);

			// Setting ReminderPeriod
			func_Click(XpathMap.get("spain_RemainderPeriod"));
			Select ReminderPeriod = new Select(webdriver.findElement(By.xpath(XpathMap.get("spain_RemainderPeriod"))));
			ReminderPeriod.selectByVisibleText("5 m");
			Thread.sleep(1000);

			// Setting LossLimit
			func_Click(XpathMap.get("spain_LossLimit"));
			webdriver.findElement(By.xpath(XpathMap.get("spain_LossLimit"))).sendKeys("" + losslimit);
			// webdriver.findElement(By.className(XpathMap.get("dialog-button
			// bttn-color-primary")));

			// Setting PreventFuture
			func_Click(XpathMap.get("spain_PreventFutureSlot"));
			Select PreventFuture = new Select(webdriver.findElement(By.xpath(XpathMap.get("spain_PreventFutureSlot"))));
			PreventFuture.selectByVisibleText("1 Hour(s)");
			Thread.sleep(1000);

			// Click on SetLimits button
			webdriver.findElement(By.xpath(XpathMap.get("spain_SetLimits"))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_SetLimitsOK"))));

			func_Click(XpathMap.get("spain_SetLimitsOK"));
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		String Title = webdriver.getTitle();
		return Title;
	}

	/**
	 * Date: 19/12/2017 Autohr: Laxmikanth Kodam Description:Verify the UK url
	 * 
	 * @return Title
	 */
	public void openUrl(String url) {
		Wait = new WebDriverWait(webdriver, 500);
		try {

			webdriver.navigate().to(url);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			Thread.sleep(5000);
			/*
			 * func_Click(XpathMap.get("UK_TopBar")); //Thread.sleep(20000);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Date: 19/12/2017 Autohr: Laxmikanth Kodam Description:Verify the UK
	 * topbar
	 * 
	 * @return Title
	 * @throws InterruptedException
	 */
	public String verifyToggleTopbar() {
		webdriver.switchTo().frame("commonUIIFrame");
		try {
			func_Click(XpathMap.get("UK_TopBar"));
			Thread.sleep(2000);
			String stake = func_GetText(XpathMap.get("UK_StakeVal")) + "!";
			String paid = func_GetText(XpathMap.get("UK_PaidVal")) + "!";
			// String
			// balancedemo=webdriver.findElement(By.id("balance")).getText();
			String balance = func_GetText(XpathMap.get("UK_BalanceVal")) + "!";
			String freebets = func_GetText(XpathMap.get("UK_FreeBetsVal")) + "!";
			System.out.println("stake= " + stake);
			System.out.println("paid= " + paid);
			System.out.println("balance= " + balance);
			System.out.println("freebets= " + freebets);
			topbarData = stake + paid + balance + freebets;
			System.out.println(topbarData);
		} catch (InterruptedException e) {
			log.error("Toggle Bar issue", e);
			Thread.currentThread().interrupt();
		}
		return topbarData;
	}

	/**
	 * Author: Laxmikanth Kodam Description:Verify the UK topbar stake by
	 * changing the coin size in settings
	 * 
	 * @return Title
	 * @throws InterruptedException
	 */
	public String[] verifyStakeWithTopBar() {
		webdriver.switchTo().frame("commonUIIFrame");
		String[] betvalues = new String[4];
		try {
			/*
			 * func_Click(XpathMap.get("UK_TopBar")); Thread.sleep(4000);
			 */
			String intialStakeVal = func_GetText(XpathMap.get("UK_StakeVal"));
			betvalues[0] = intialStakeVal;
			webdriver.switchTo().defaultContent();
			Thread.sleep(4000);
			String intialCoinSizeVal = func_GetText(XpathMap.get("coinSize_Amount"));
			betvalues[1] = intialCoinSizeVal;
			List<WebElement> forms = webdriver.findElements(By.xpath("//div[@class='coinSize-cell']"));
			int count = forms.size();
			func_Click(XpathMap.get("coinSize_Arrow"));
			Thread.sleep(4000);
			for (int i = 3; i < count; i++) {
				WebElement current = webdriver.findElement(By.xpath("//*[@id='coinSize-content']/div[" + i + "]"));
				current.click();
				func_Click(XpathMap.get("coinSize_Arrow"));
				func_Click(XpathMap.get("coinSize_Arrow"));
				Thread.sleep(2000);
			}
			func_Click(XpathMap.get("coinSizeDropDownMax"));
			String finalCoinSizeVal = func_GetText(XpathMap.get("coinSize_Amount"));
			betvalues[2] = finalCoinSizeVal;
			webdriver.switchTo().frame("commonUIIFrame");
			String finalStakeVal = func_GetText(XpathMap.get("UK_StakeVal"));
			betvalues[3] = finalStakeVal;
		} catch (InterruptedException e) {
			log.error("Error in verifying stake in top bar", e);
		}
		return betvalues;
	}

	public String PreventFuture() throws Exception {
		String PreventFuture = null;
		PreventFuture = func_GetText(XpathMap.get("spain_PreventFutureSlot_Text"));
		return PreventFuture;
	}

	/**
	 * Author:Laxmikanth Kodam Description: Parameter:
	 */
	public double[] getAttributes(String nodeValue, String attribute1, String attribute2, String attribute3) {
		double[] data = new double[4];
		try {
			String totalWin1 = null;
			String balance1 = func_GetText(XpathMap.get("creditBalance"));
			balance = func_String_Operation(balance1);

			String bet1 = func_GetText(XpathMap.get("betValue"));
			bet = func_String_Operation(bet1);

			totalWin1 = func_GetText(XpathMap.get("winValue"));
			if (!totalWin1.isEmpty()) {
				totalWin = func_String_Operation(totalWin1);
				String y = totalWin.replaceAll(",", "");
				data[2] = Double.parseDouble(y);
			} else {
				totalWin1 = "0";
				System.out.println(totalWin1);
				totalWinNew = Double.parseDouble(totalWin1);
			}
			String x = balance.replaceAll(",", "");
			data[0] = Double.parseDouble(x);
			data[1] = Double.parseDouble(bet);
			data[3] = totalWinNew;
			System.out.println(data[0]);
			// System.out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Author:Laxmikanth Kodam Description: Parameter:
	 */
	public double[] getAttributesSpin(String nodeValue, String attribute1, String attribute2, String attribute3) {

		double[] data1 = new double[4];
		Wait = new WebDriverWait(webdriver, 500);
		try {
			func_Click(XpathMap.get("spinButtonBox"));
			Thread.sleep(4000);
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			String totalWin1 = null;
			String balance1 = func_GetText(XpathMap.get("creditBalance"));
			balance = func_String_Operation(balance1);

			String bet1 = func_GetText(XpathMap.get("betValue"));
			bet = func_String_Operation(bet1);

			totalWin1 = func_GetText(XpathMap.get("winValue"));
			if (!totalWin1.isEmpty()) {
				totalWin = func_String_Operation(totalWin1);
				String y = totalWin.replaceAll(",", "");
				data1[2] = Double.parseDouble(y);
			} else {
				totalWin1 = "0";
				System.out.println(totalWin1);
				totalWinNew = Double.parseDouble(totalWin1);
			}
			String x = balance.replaceAll(",", "");
			data1[0] = Double.parseDouble(x);
			data1[1] = Double.parseDouble(bet);
			data1[3] = totalWinNew;
			System.out.println(data1[0]);
			// System.out.println(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data1;
	}

	/* code for verifying slottitle */
	public String verifyslottitle() throws Exception {
		String slotgametitle1 = null;
		slotgametitle1 = func_GetText(XpathMap.get("spain_SlotGameLimits"));
		return slotgametitle1;
	}

	public String verifysetlimitbutton() throws Exception {
		String setlimit = null;
		setlimit = func_GetText(XpathMap.get("spain_SetLimits"));
		return setlimit;
	}

	public boolean verifyhyperlink() throws Exception {
		boolean ret = false;
		if (webdriver.findElement(By.xpath(XpathMap.get("spain_BackToLobby"))).isDisplayed()) {
			System.out.println("enabled");
			ret = true;
		}
		return ret;
	}

	/* verify time limit blank */
	public boolean verifytimelimitblank() throws Exception {
		boolean ret = false;
		func_Click(XpathMap.get("spain_TimeLimit"));
		Thread.sleep(2000);
		ret = true;
		return ret;
	}

	/* verify reminder period blank */
	public boolean verifyreminderperiodblank() throws Exception {
		boolean ret = false;
		func_Click(XpathMap.get("spain_RemainderPeriod"));
		Thread.sleep(500);
		ret = true;
		return ret;
	}

	/* verify time loss limit blank */
	public boolean losslimitblank() throws Exception {
		boolean ret = true;
		func_Click(XpathMap.get("spain_LossLimit"));
		String textInsideInputBox = func_GetText(XpathMap.get("spain_LossLimit"));
		// Check whether input field is blank
		if (textInsideInputBox.isEmpty()) {
			System.out.println("Input field is empty");
			ret = true;
		}
		return ret;
	}

	/* verify time limit blank */
	public boolean verifyfutureprventblank() throws Exception {
		boolean ret = false;
		String selectoption = null;
		func_Click(XpathMap.get("spain_PreventFutureSlot"));
		Select preventblank = new Select(webdriver.findElement(By.xpath(XpathMap.get("spain_PreventFutureSlot"))));
		// preventblank.selectByIndex(1);
		selectoption = preventblank.getFirstSelectedOption().getText();
		System.out.println(selectoption);
		// Check whether input field is blank
		if (selectoption.isEmpty()) {
			System.out.println("Input field is empty");
			ret = true;
		}
		return ret;
	}

	// Selectable options :Ensure dropdown box appear for Time limit box
	public boolean verifytimedropdown() throws Exception {
		boolean ret = false;
		String selectoption = null;
		func_Click(XpathMap.get("spain_TimeLimit"));
		Select TimeLimit = new Select(webdriver.findElement(By.xpath(XpathMap.get("spain_TimeLimit"))));
		// TimeLimit.selectByIndex(1);
		selectoption = TimeLimit.getFirstSelectedOption().getText();
		System.out.println(selectoption);
		// Check whether input field is blank
		if (selectoption.isEmpty()) {
			System.out.println("Input field is empty");
			ret = true;
		}
		return ret;
	}

	// Selectable options :Ensure dropdown box appear for reminder limit box
	public boolean verifyreminderdropdown() throws Exception {
		boolean ret = false;
		// String textInsideInputBox
		// =func_GetText(XpathMap.get("spain_RemainderPeriod"));
		String selectoption = null;
		func_Click(XpathMap.get("spain_RemainderPeriod"));
		Select RemainderPeriod = new Select(webdriver.findElement(By.xpath(XpathMap.get("spain_RemainderPeriod"))));
		// RemainderPeriod.selectByIndex(1);
		selectoption = RemainderPeriod.getFirstSelectedOption().getText();
		System.out.println(selectoption);
		// Check whether input field is blank
		if (selectoption.isEmpty()) {
			System.out.println("Input field is empty");
			ret = true;
		}
		return ret;
	}

	// Selectable option Ensure slotreminder dropdown appear
	public boolean verifypreventfutureslotreminder() throws Exception {
		boolean ret = false;
		func_Click(XpathMap.get("spain_PreventFutureSlot"));
		ret = true;
		return ret;
	}

	// Ensure there are time duration options available for selection in the
	// different time units (eg, 1 hour, 1 minute).
	public String verifytimedurationoption(String dropdownValue) throws Exception {
		String selectoption = null;
		try {
			func_Click(XpathMap.get("spain_TimeLimit"));
			Select TimeLimit = new Select(webdriver.findElement(By.xpath(XpathMap.get("spain_TimeLimit"))));
			TimeLimit.selectByIndex(1);
			selectoption = func_GetText(XpathMap.get("spain_TimeLimit"));
			Thread.sleep(1000);
		} catch (Exception e) {
			e.getMessage();
		}
		return selectoption;
	}

	// Ensure there are time duration options available for selection in the
	// different time units (eg,5 mins,10 mins).
	public String verifytimedurationoption() throws Exception {
		String selectoption = null;
		func_Click(XpathMap.get("spain_TimeLimit"));
		Select TimeLimit = new Select(webdriver.findElement(By.xpath(XpathMap.get("spain_TimeLimit"))));
		TimeLimit.selectByIndex(3);
		selectoption = TimeLimit.getFirstSelectedOption().getText();
		System.out.println(selectoption);
		Thread.sleep(1000);
		return selectoption;
	}

	// Ensure that send the loss limit value should be less than player balance
	public String sendLossLimitData() throws Exception {
		String lobbybalance = LobbyBalance.replaceAll("[�$,]", "");
		String lobbybal = lobbybalance.substring(0, lobbybalance.length() - 3);
		lobbybals = Double.parseDouble(lobbybal);
		double lobbyBal = lobbybals - 1;
		func_Click(XpathMap.get("spain_LossLimit"));
		webdriver.findElement(By.xpath(XpathMap.get("spain_LossLimit"))).clear();
		webdriver.findElement(By.xpath(XpathMap.get("spain_LossLimit"))).sendKeys(String.valueOf(lobbyBal));
		return LobbyBalance;
	}

	/*
	 * Author: Ashish Kshatriya Description: This function is used for take
	 * screenshots in application.
	 */
	public String createScreenshot(WebDriver webdriver, String deviceName) throws InterruptedException {
		// UUID uuid = UUID.randomUUID();
		/*
		 * screenshotsFolder = "ImageScreenshot//Mobile//"; File dir = new
		 * File(screenshotsFolder); dir.mkdirs();
		 */
		String imageLocation = Constant.OUTPUTIMAGEFOLDER;
		File scrFile = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
		Thread.sleep(1000);
		try {
			FileUtils.copyFile(scrFile, new File(imageLocation + deviceName + "_Actual" + ".jpg"));
		} catch (IOException e) {
			System.out.println("Error while generating screenshot:\n" + e.toString());
		}
		return imageLocation + deviceName + "_Actual" + ".jpg";
	}

	/**
	 * Author : Laxmikanth Kodam Description: To fill Spain Start Session Form
	 * Param: Time Limit, Reminder Period, Loss Limit and prevent Future Slot
	 * Game for Play Return: Boolean value
	 */
	public boolean fillStartSessionLossForm(String LossLimit, Desktop_HTML_Report tc10) {
		boolean ret = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_TimeLimit"))));
			Select sel = new Select(webdriver.findElement(By.xpath(XpathMap.get("spain_TimeLimit"))));
			sel.selectByIndex(1);
			sel = new Select(webdriver.findElement(By.xpath(XpathMap.get("spain_ReminderPeriod"))));
			sel.selectByIndex(1);
			webdriver.findElement(By.xpath(XpathMap.get("spain_LossLimit"))).sendKeys(LossLimit);
			sel = new Select(webdriver.findElement(By.xpath(XpathMap.get("spain_PreventFutureSlot"))));
			sel.selectByIndex(1);
			webdriver.findElement(By.xpath(XpathMap.get("spain_SetLimits"))).click();
			Wait.until(
					ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(XpathMap.get("spain_SetLimitsOK"))));
			webdriver.findElement(By.xpath(XpathMap.get("spain_SetLimitsOK"))).click();
			Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(XpathMap.get("Spin_Button"))));
			ret = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/*
	 * Author : Havish Jain Description: To wait until session reminder popup
	 * appears Return: String value
	 */
	public String waitUntilSessionReminder(Desktop_HTML_Report tc10) {
		String header = null;
		try {
			Wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_SessionReminderContinue"))));
			header = webdriver.findElement(By.xpath(XpathMap.get("spain_SessionReminderContinue"))).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return header;
	}

	/**
	 * Author : Havish Jain Description: To click on continue button in session
	 * reminder popup
	 */
	public void spainContinueSession() {
		try {
			Wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_SessionReminderContinue"))));
			webdriver.findElement(By.xpath(XpathMap.get("spain_SessionReminderContinue"))).click();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/*
	 * Author: Anand Description: To test element is present or not Param: Key
	 * of element's xpath from OR Return: Boolean value
	 */
	public boolean isElementExist(String keyName, int timeInSeconds) {
		boolean ret = false;
		try {
			webdriver.manage().timeouts().implicitlyWait(timeInSeconds, TimeUnit.SECONDS);
			if (webdriver.findElements(By.xpath(XpathMap.get(keyName))).size() > 0) {
				ret = true;
			}
		} catch (Exception e) {
			webdriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		}
		return ret;
	}

	/**
	 * Author : Havish Jain Description: To wait until loss limit is reached
	 * Param: Null Return: String value
	 */
	public String waitUntilSessionLoss() {
		String title = null;
		try {
			for (int i = 0; i <= 10; i++) {
				Wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(XpathMap.get("Spin_Button_ID"))));
				func_Click(XpathMap.get("OneDesign_Spin_Text"));
				Thread.sleep(3000);
				boolean b = webdriver.findElements(By.xpath(XpathMap.get("spain_lossLimitDialogueOK"))).size() > 0;
				if (b) {
					System.out.println("Loss Limit is reached");
					title = webdriver.findElement(By.xpath(XpathMap.get("spain_lossLimitDialogueOK"))).getText();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return title;
	}

	/**
	 * Author : Havish Jain Description: To close popup of session loss and
	 * capture screenshot Param: Desktop_HTML_Report
	 */
	public void closeSessionLossPopup(Desktop_HTML_Report tc10) {
		try {
			func_Click(XpathMap.get("spain_lossLimitDialogueOK"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_CloseBtn"))));
			tc10.detailsAppend("To check if Loss Limit Summary overlay appear",
					"Loss Limit Summary overlay should appear", "Loss Limit summary overlay appears", "Pass");
			func_Click(XpathMap.get("spain_CloseBtn"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("five_Reel_slotID"))));
			tc10.detailsAppend(
					"To verify user navigates to lobby after clicking on close button when loss limit is reached",
					"User should redirect to lobby after clicking on close button when loss limit is reached",
					"User is redirected to lobby", "Pass");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Author : Havish Jain Description: To vefiry that game can't be played in
	 * cooling off, loss limit is already reached and user is in lobby again
	 * Param: Desktop_HTML_Report Return:
	 */
	public void coolingOffPeriod(Desktop_HTML_Report tc10) {
		try {
			func_Click(XpathMap.get("five_Reel_slot"));
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			js.executeScript("arguments[0].scrollIntoView(true);", webdriver.findElement(By.id(GameName)));
			js.executeScript("arguments[0].click();", webdriver.findElement(By.id(GameName)));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_CloseBtn"))));
			tc10.detailsAppend("To check if Cooling Off Period Overlay appear on launching same game",
					"Cooling Off Period Overlay should appear on launching same game",
					"Cooling Off Period Overlay appears on launching same game", "Pass");
			func_Click(XpathMap.get("spain_CloseBtn"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("five_Reel_slotID"))));
			webdriver.findElement(By.id(XpathMap.get("five_Reel_slotID"))).click();
			;
			tc10.detailsAppend(
					"To verify user navigates to lobby after clicking on close button when cooling off period is running",
					"User should redirect to lobby after clicking on close button when cooling off period is running",
					"User is redirected to lobby", "Pass");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Author : Havish Jain Description: To verify that another game can't be
	 * played in cooling off, loss limit is already reached and user is in lobby
	 * again Param: Mobile_HTML_Report Return:
	 */

	public void coolingOffPeriodNewGame(String gamename, Desktop_HTML_Report tc10) {
		try {
			JavascriptExecutor js = ((JavascriptExecutor) webdriver);
			js.executeScript("arguments[0].scrollIntoView(true);", webdriver.findElement(By.id(gamename)));
			js.executeScript("arguments[0].click();", webdriver.findElement(By.id(gamename)));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_CloseBtn"))));
			tc10.detailsAppend("To check if Cooling Off Period Overlay appear on launching another game",
					"Cooling Off Period Overlay should appear on launching another game",
					"Cooling Off Period Overlay appears on launching another game", "Pass");
			func_Click(XpathMap.get("spain_CloseBtn"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("five_Reel_slotID"))));
			tc10.detailsAppend(
					"To verify user navigates to lobby after clicking on close button when cooling off period is running",
					"User should redirect to lobby after clicking on close button when cooling off period is running",
					"User is redirected to lobby", "Pass");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Author: Havish Jain Description:Launch the game again when spain lobby is
	 * open Parameter: N/A
	 */
	public void relaunchGame() {
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("five_Reel_slotID"))));
		func_Click(XpathMap.get("five_Reel_slot"));
		JavascriptExecutor js = ((JavascriptExecutor) webdriver);
		js.executeScript("arguments[0].scrollIntoView(true);", webdriver.findElement(By.id(GameName)));
		js.executeScript("arguments[0].click();", webdriver.findElement(By.id(GameName)));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spain_SlotGameOverlay"))));
	}

	/**
	 */
	public String clickForScreen(String Ok, String Close) {
		try {
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			boolean OK = webdriver.findElements(By.xpath(XpathMap.get(Ok))).size() > 0;
			if (OK) {
				func_Click(XpathMap.get(Ok));
			} else {
				System.out.println("Element not found");
			}
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 */
	public String clickOnClose() {
		func_Click(XpathMap.get("spain_CloseBtn"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("navigation_MyAccount"))));
		String lobbyAccount = webdriver.getTitle();
		return lobbyAccount;
	}

	public void checkPage(Desktop_HTML_Report Tc10) {
		try {
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			boolean test = webdriver.findElements(By.xpath(XpathMap.get("spain_StartNewSession"))).size() > 0;
			if (test) {
				Tc10.detailsAppend("Verify that Start New Sessiosn Overlay appears before the game loads ",
						"Start New Sessiosn Overlay should appear before the game loads",
						"Start New Sessiosn Overlay is appearing before the game loads", "Pass");
				func_Click(XpathMap.get("spain_StartNewSession"));
			} else {
				System.out.println("Slot Game limit overlay appears");
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public String getText(String elementKeyName) {
		return func_GetText(XpathMap.get(elementKeyName));// webdriver.findElement(By.xpath(XpathMap.get(elementKeyName))).getText();
	}

	/**
	 * Name:Premlata Mishra Description: this function is used to click on icon
	 * n switch to previous window
	 * 
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void iconclick(WebElement ele, Desktop_HTML_Report report) throws Exception {
		String winHandleBefore = webdriver.getWindowHandle();
		System.out.println("Before content : " + winHandleBefore);
		ele.click();
		for (String winHandle : webdriver.getWindowHandles()) {
			if (!winHandle.equals(winHandleBefore))
				webdriver.switchTo().window(winHandle);
		}
		Thread.sleep(3000);
		report.detailsAppend("Verify that icon is clicking and nevigating to new page",
				" Clicking on icon ,it must nevigate to new page", "Clicking on icon ,it is nevigating to new page",
				"pass");
		webdriver.close();
		for (String winHandle : webdriver.getWindowHandles()) {
			if (winHandle.equals(winHandleBefore))
				webdriver.switchTo().window(winHandle);
		}
		String content = webdriver.getWindowHandle();
		System.out.println("After perform content : " + content);
	}

	public void clickIcon(Desktop_HTML_Report report) throws Exception {
		iconclick(webdriver.findElement(By.xpath(XpathMap.get("OneDesignHelp"))), report);
		// iconclick(webdriver.findElement(By.xpath(XpathMap.get("OneDesignnull"))),report);
		iconclick(webdriver.findElement(By.xpath(XpathMap.get("OneDesignPlayCheck"))), report);
		iconclick(webdriver.findElement(By.xpath(XpathMap.get("OneDesignCashCheck"))), report);
		func_Click(XpathMap.get("OneDesignClosePaytable"));
	}

	/**
	 * Date:10-1-2018 Name:Premlata Mishra Description: this function is used to
	 * Scroll the page * @throws Exception
	 */
	public void scrollBar(WebElement ele, Desktop_HTML_Report report) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) webdriver;
		js.executeScript("arguments[0].scrollIntoView(true);", ele);
	}

	/**
	 * Date:10-1-2018 Name:Premlata Mishra Description: this function is used to
	 * Scroll the page and to take the screenshot
	 * 
	 * @throws Exception
	 */
	public String paytableOpen(Desktop_HTML_Report report, String classname, String languageCode) {
		Wait = new WebDriverWait(webdriver, 50);
		String paytable = null;
		try {
			if (classname.equals("Desktop_Regression_Suit")) {
				Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("OneDesign_Spin_Button"))));

				func_Click(XpathMap.get("OneDesign_Hamburger"));
				Wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("OneDesign_PaytableID"))));
				func_Click_BYID(XpathMap.get("OneDesign_PaytableID"));
				log.debug("Clicked on paytable icon to open ");
				Thread.sleep(3000);
				JavascriptExecutor js = (JavascriptExecutor) webdriver;
				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("5_saphire_Symbol_")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(1000);
				paytable = func_GetText(XpathMap.get("5_saphire_Symbol_"));
				// report.details_append("verify the paytable screen shot", "
				// paytable first page screen shot", "paytable first page
				// screenshot ", "pass");
			} else {
				Thread.sleep(1000);
				func_Click_BYID(XpathMap.get("OneDesign_HamburgerID"));
				Thread.sleep(1000);
				Wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("OneDesign_PaytableID"))));
				func_Click_BYID(XpathMap.get("OneDesign_PaytableID"));
				log.debug("Clicked on paytable icon to open ");
				Thread.sleep(3000);
				report.detailsAppendFolder("verify the paytable screen shot", " paytable first page screen shot",
						"paytable first page screenshot ", "pass", languageCode);
				paytable = "paytable";
				JavascriptExecutor js = (JavascriptExecutor) webdriver;

				WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get("OneDesignpaytable1")));
				js.executeScript("arguments[0].scrollIntoView(true);", ele1);
				Thread.sleep(1000);
				report.detailsAppendFolder("verify the paytable screen shot", " paytable Second page screen shot",
						"paytable Second page screenshot ", "pass", languageCode);

				boolean test = webdriver.findElements(By.xpath(XpathMap.get("OneDesignpaytable2"))).size() > 0;
				if (test) {
					WebElement ele3 = webdriver.findElement(By.xpath(XpathMap.get("OneDesignpaytable2")));
					js.executeScript("arguments[0].scrollIntoView(true);", ele3);
					Thread.sleep(1000);
					report.detailsAppendFolder("verify the paytable screen shot", " paytable Third page screen shot",
							"paytable Third page screenshot ", "pass", languageCode);
					boolean test1 = webdriver.findElements(By.xpath(XpathMap.get("OneDesignpaytable3"))).size() > 0;
					if (test1) {
						WebElement ele4 = webdriver.findElement(By.xpath(XpathMap.get("OneDesignpaytable3")));
						js.executeScript("arguments[0].scrollIntoView(true);", ele4);
						Thread.sleep(1000);
						report.detailsAppendFolder("verify the paytable screen shot",
								" paytable fourth page screen shot", "paytable fourth page screenshot ", "pass",
								languageCode);
						/*
						 * boolean
						 * test2=webdriver.findElements(By.xpath(XpathMap.get(
						 * "OneDesignpaytable4"))).size()>0; if(test2) {
						 * WebElement
						 * ele5=webdriver.findElement(By.xpath(XpathMap.get(
						 * "OneDesignpaytable4"))); js.executeScript(
						 * "arguments[0].scrollIntoView(true);", ele5);
						 * Thread.sleep(1000); report.
						 * details_append_folder("verify the paytable screen shot"
						 * , " paytable Fifth page screen shot",
						 * "paytable Fifth page screenshot ", "pass",
						 * languageCode); }
						 */
					}
				}
			}
		} catch (Exception e) {
			log.error("error in opening paytable", e);
		}
		return paytable;
	}

	public void paytableClose() {
		Wait = new WebDriverWait(webdriver, 500);
		try {
			func_Click_BYID(XpathMap.get("PaytableBackID"));
			Thread.sleep(2000);
			log.debug("Closed the paytable page");
			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("spinButtonBox"))));
		} catch (Exception e) {
			log.debug("error in closing paytable");
		}
	}

	/*
	 * Author:Havish Jain Description: To Open menu container
	 */
	public boolean menuOpen() {
		boolean ret = false;
		try {
			// Clicking on hamburger menu
			func_Click_BYID(XpathMap.get("OneDesign_HamburgerID"));
			Thread.sleep(2000);
			boolean test = webdriver.findElements(By.id(XpathMap.get("OneDesign_HamburgerID"))).size() > 0;
			if (test) {
				ret = true;
				log.debug("Clicked on menu button  to open");
			} else {
				log.debug("Hamburger Menu Links are not displaying");
			}
		} catch (InterruptedException e) {
			log.error("Error in opening menu", e);
		}
		return ret;
	}

	/*
	 * Author:Havish Jain Description: To Close menu container
	 */
	public boolean menuClose() {
		try {
			menuOpen();
			log.debug("Clicked on screen to close menu overlay");
		} catch (Exception e) {
			log.error("Error in closing menu", e);
		}
		return true;
	}

	/**
	 * Name:Laxmikanth Kodam Description: this function is used to navigate back
	 * to lobby
	 * 
	 * @return lobbytitle
	 */
	public String verifybacktolobby() throws Exception {
		String backtolobbytitle = null;
		try {
			// click on hyperlink
			func_Click(XpathMap.get("spain_BackToLobby"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("navigation_MyAccount"))));
			// Back To Lobby
			backtolobbytitle = webdriver.getTitle();
		} catch (Exception e) {
			evalException(e);
		}
		return backtolobbytitle;
	}

	/**
	 * Author Lamxikanth Kodam Common added for logout() This method is common
	 * logout function for the component store
	 * 
	 * @return
	 */
	public String Func_logout(String onedesign) {
		String loginTitle = null;
		if (onedesign.equalsIgnoreCase("onedesign")) {
			Func_logout_OD();
		} else {
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("gameLogo"))));
			func_Click(XpathMap.get("leftArrow"));// Clicking on left arrow
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("backToLobby"))));
			func_Click(XpathMap.get("backToLobby"));// Clicking on back to lobby
													// button
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("three_Reel_Slot"))));
			func_Click(XpathMap.get("navigation_MyAccount"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("logout"))));
			loginTitle = func_GetText(XpathMap.get("logout"));
			func_Click(XpathMap.get("logout"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Login"))));
			func_Click(XpathMap.get("closeButtonLogin"));
		}
		return loginTitle;
	}

	/**
	 * Author Lamxikanth Kodam Common added for logout() This method is common
	 * logout function for the component store
	 * 
	 * @return
	 */
	public String Func_logout_OD() {
		String loginTitle = null;
		func_Click(XpathMap.get("OneDesign_HomeIcon"));// Clicking on Home Icon
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("five_Reel_slot"))));
		func_Click(XpathMap.get("navigation_MyAccount"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("logout"))));
		loginTitle = func_GetText(XpathMap.get("logout"));
		func_Click(XpathMap.get("logout"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("Login"))));
		func_Click(XpathMap.get("closeButtonLogin"));
		return loginTitle;
	}

	// ********************************Exception
	// Handler********************************************************//
	public void evalException() throws Exception {
		// System.out.println("*************In Exception Handling
		// Class*************"+ex.getClass());
		// ex.printStackTrace();
		// repo1.details_append( "Execution Interrupted because of exception" ,
		// "" , "", "Interrupted");

		String exClass = ex.getClass().toString();
		// ex.printStackTrace();
		if (exClass.contains("StaleElementReferenceException")) {
			// System.out.println("Identified specific exception "+exClass);
			// System.out.println();
			repo1.detailsAppend("Execution Interrupted because of StaleElementReferenceException", "", "",
					"Interrupted");
		} else if (exClass.contains("NoSuchElementException")) {
			repo1.detailsAppend("Execution Interrupted because of NoSuchElementException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("InvalidElementStateException")) {
			repo1.detailsAppend("Execution Interrupted because of InvalidElementStateException", "", "",
					"Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("ElementNotVisibleException")) {
			repo1.detailsAppend("Execution Interrupted because of ElementNotVisibleException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("ErrorInResponseException")) {
			repo1.detailsAppend("Execution Interrupted because of ErrorInResponseException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("InvalidSwitchToTargetException")) {
			repo1.detailsAppend("Execution Interrupted because of InvalidSwitchToTargetException", "", "",
					"Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("NoSuchFrameException")) {
			repo1.detailsAppend("Execution Interrupted because of NoSuchFrameException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("NoSuchWindowException")) {
			repo1.detailsAppend("Execution Interrupted because of NoSuchWindowException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("NoSuchAttributeException")) {
			repo1.detailsAppend("Execution Interrupted because of NoSuchAttributeException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("NoAlertPresentException")) {
			repo1.detailsAppend("Execution Interrupted because of NoAlertPresentException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("ElementNotSelectableException")) {
			repo1.detailsAppend("Execution Interrupted because of ElementNotSelectableException", "", "",
					"Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("InvalidCookieDomainException")) {
			repo1.detailsAppend("Execution Interrupted because of InvalidCookieDomainException", "", "",
					"Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("UnableToSetCookieException")) {
			repo1.detailsAppend("Execution Interrupted because of UnableToSetCookieException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("RemoteDriverServerException")) {
			repo1.detailsAppend("Execution Interrupted because of RemoteDriverServerException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("TimeoutException")) {

			repo1.detailsAppend("Execution Interrupted because of TimeoutException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("MoveTargetOutOfBoundsException")) {
			repo1.detailsAppend("Execution Interrupted because of MoveTargetOutOfBoundsException", "", "",
					"Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("UnexpectedTagNameException")) {
			repo1.detailsAppend("Execution Interrupted because of UnexpectedTagNameException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("InvalidSelectorException")) {
			repo1.detailsAppend("Execution Interrupted because of InvalidSelectorException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("ImeNotAvailableException")) {
			repo1.detailsAppend("Execution Interrupted because of ImeNotAvailableException", "", "", "Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("ImeActivationFailedException")) {
			repo1.detailsAppend("Execution Interrupted because of ImeActivationFailedException", "", "",
					"Interrupted");
			// System.out.println("Identified specific exception "+exClass);
		} else if (exClass.contains("UnhandledAlertException")) {
			repo1.detailsAppend("Execution Interrupted because of Unhandled message ALERT", "", "", "Interrupted");
			Thread.sleep(3000);
			// System.out.println("Identified specific exception "+exClass);

			// Alert alert = null;
			// try {
			// //System.out.println("Alert found"+((TargetLocator)
			// driver).alert().getText());
			// alert = driver.switchTo().alert();
			// } catch (Exception e) {
			// e.printStackTrace();
			// System.out.println("Alert found"+alert.getText());
			//
			//
			// }
			// // Get the text of the alert or prompt
			//
			// //System.out.println("Alert found");
			//
			//
			// // And acknowledge the alert (equivalent to clicking "OK")
			// alert.accept();
		}
	}

	public boolean verifyImage(String path) {
		boolean isMatch = false;
		Wait.until(ExpectedConditions.visibilityOf(webdriver.findElement(By.id("inGameClock"))));// wait
																									// untill
																									// the
																									// base
																									// game
																									// screen
																									// won't
																									// come
		Screen S = new Screen();
		// Match match=null;
		Pattern image = new Pattern(path);
		System.out.println("get image : " + image);
		try {
			// S.click(S.wait(image, 10));
			// Region r=S.find(image);
			Region r = S.exists(image, 100);
			if (r != null) {
				System.out.println("image found");
				isMatch = true;
			} else {
				System.out.println("image not found");
				isMatch = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return isMatch;
	}

	/**
	 * This method is used to verify the spin visibility
	 * 
	 * @param imagepath
	 * @return true
	 * @throws InterruptedException
	 */
	public boolean customeverifyimage(String button) throws InterruptedException {
		Wait = new WebDriverWait(webdriver, 500);
		String stop = new String("Stop");
		String spin = new String("spin");
		boolean isMatch = false;
		if (spin.equalsIgnoreCase(button)) {
			String spinText = verifySpin();
			if (spin.equalsIgnoreCase(spinText))
				isMatch = true;
		}
		if (stop.equalsIgnoreCase(button)) {
			String stopButton = null;
			stopButton = verifyStop();
			log.debug("stopButton:" + stopButton);
			if (stop.equalsIgnoreCase(stopButton))
				isMatch = true;
		}
		return isMatch;
	}

	/**
	 * This method is used to verify the Base Game Logo
	 * 
	 * @param imagepath
	 * @return
	 * @throws InterruptedException
	 */
	public String verifySpin() throws InterruptedException {
		// if(button){
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_Spin_Text"))));
		func_Click(XpathMap.get("OneDesign_Spin_Button"));
		String SpinText = func_GetText(XpathMap.get("OneDesign_Spin_Text"));
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_Spin_Text"))));
		// Thread.sleep(6000);
		return SpinText;
	}

	public String verifyStop() throws InterruptedException {
		// if(button){
		// Wait=new WebDriverWait(webdriver,120);
		func_Click(XpathMap.get("OneDesign_Spin_Button"));
		String StopText = func_GetText(XpathMap.get("OneDesign_Stop_Text"));
		// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_Spin_Text"))));
		// Thread.sleep(60000);
		return StopText;
	}

	public void winClick() {
		Wait = new WebDriverWait(webdriver, 500);
		try {
			func_Click(XpathMap.get("OneDesignWinText"));
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_Spin_Text"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean spinclick() throws InterruptedException {
		Wait = new WebDriverWait(webdriver, 50);
		try {
			func_Click(XpathMap.get("spinButtonBox"));
			log.debug("Clicked on spin button");
		} catch (Exception e) {
			log.error("error while clicking on spin button", e);
		}
		return true;
	}

	/**
	 * This method is used to wait till FS scene loads Author: Havish Jain
	 * 
	 * @return true
	 */
	public boolean FSSceneLoading() {
		Wait = new WebDriverWait(webdriver, 50);
		try {
			// wait for Free spin scene to refresh
			log.debug("After refreshing,waiting for free spin's scene to come");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FSMultiplierCountID"))));
		} catch (Exception e) {
			log.error("error while waiting for free spin scene", e);
		}
		return true;
	}

	/**
	 * *Author:Premlata This method is used to wait till the free spin entry
	 * screen won't come
	 * 
	 * @throws InterruptedException
	 */
	public String entryScreen_Wait(String entry_Screen) {
		Wait = new WebDriverWait(webdriver, 50);
		String wait = null;
		if (entry_Screen.equalsIgnoreCase("yes")) {
			log.debug("Waiting for free spin entry screen to come");
			Wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("M_FreeSpin_ClickToContinueID"))));
			wait = "freeSpin";
		} else {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FSMultiplierCountID"))));
			wait = "NoEntryScreen";
		}
		return wait;
	}

	/**
	 * *Author:Premlata This method is used to click on free spin enrtry screen
	 * 
	 * @throws InterruptedException
	 */
	public String clickToContinue() {
		String FS_Credits = null;
		Wait = new WebDriverWait(webdriver, 500);
		try {
			webdriver.findElement(By.id(XpathMap.get("M_FreeSpin_ClickToContinueID"))).click();
			// func_Click(XpathMap.get("M_FreeSpin_ClickToContinueID"));
			FS_Credits = func_GetText(XpathMap.get("FreeSpin_Credits_ID"));
		} catch (Exception e) {
			e.getMessage();
		}
		return FS_Credits;
	}

	/**
	 * *Author:Havish This method is used to wait till the free spin summary
	 * screen won't come
	 * 
	 * @throws InterruptedException
	 */
	public void waitSummaryScreen() throws InterruptedException {
		Wait = new WebDriverWait(webdriver, 100);
		try {
			log.debug("Waiting for summary screen to come");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("CongratsFSSummaryID"))));
			Thread.sleep(1500);
		} catch (Exception e) {
			log.error("error while waiting for summary screen");
		}
	}

	/**
	 * *Author:Havish This method is used to swipe for in paytable pages when
	 * arrows displays on the screen
	 * 
	 * @throws InterruptedException
	 */
	public String paytableSwipe(Desktop_HTML_Report report) {
		String paytable = "";
		try {
			// func_Click(XpathMap.get("OneDesign_Hamburger"));
			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("OneDesign_Paytable"))));
			func_Click(XpathMap.get("OneDesign_Paytable"));
			Thread.sleep(1500);
			func_Click(XpathMap.get("OneDesign_Paytable"));
			report.detailsAppend("verify paytable screen shot", " paytable first page screen shot",
					"paytable first page screenshot ", "pass");
			paytable = "paytable";

			func_Click(XpathMap.get("OneDesign_Paytable"));
			Thread.sleep(1500);
			report.detailsAppend("verify the paytable screen shot", " paytable Second page screen shot",
					"paytable Second page screenshot ", "pass");

			func_Click(XpathMap.get("OneDesign_Paytable"));
			Thread.sleep(1500);
			report.detailsAppend("verify the paytable screen shot", " paytable Third page screen shot",
					"paytable Third page screenshot ", "pass");

			func_Click(XpathMap.get("OneDesign_Paytable"));
			Thread.sleep(1500);
			report.detailsAppend("verify the paytable screen shot", " paytable fourth page screen shot",
					"paytable fourth page screenshot ", "pass");

			/*
			 * func_Click(XpathMap.get("OneDesign_Paytable"));
			 * Thread.sleep(1500);
			 * report.details_append("verify the paytable screen shot",
			 * " paytable Fifth page screen shot",
			 * "paytable Fifth page screenshot ", "pass");
			 */
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paytable;
	}

	/**
	 * *Author:Havish Jain This method is used to get currency symbol from
	 * credit balance
	 * 
	 * @throws InterruptedException
	 */
	public String getCurrencySymbol() {
		String currencySymbol = null;
		try {
			// String balance = func_GetText(XpathMap.get("creditBalance"));
			String balance = func_GetText("//*[@id='info_credit_val']");
			currencySymbol = balance.replaceAll("[[0-9],.\\s]", "");
			log.debug("Fetching currency symbol from game" + "Currency symbol is :" + currencySymbol);
		} catch (Exception e) {
			log.error("error in getting currency symbol", e);
		}
		return currencySymbol;
	}

	/**
	 * *Author:Havish Jain This method is used to get bet from the game without
	 * currency symbol, comma and dot.
	 * 
	 * @throws InterruptedException
	 */
	public String getCurrentBet() {
		String betValue = func_GetText(XpathMap.get("betValue"));
		String bet = betValue.replaceAll("[^0-9]", "");
		log.debug("Fetching multiplier from game" + "/nMultiplier is :" + bet);
		return bet;
	}

	/**
	 * *Author:Havish Jain This method is used to verify currency symbol in bet
	 * 
	 * @throws InterruptedException
	 */
	public boolean betCurrencySymbol(String currency) {
		String bet = func_GetText(XpathMap.get("betValue"));
		if (bet.indexOf(currency) >= 0) {
			return true;
		}
		if (currency == null || currency.equals("")) {
			return true;

		}
		log.debug("Fetching bet currency symbol" + "/n bet currency symbol is::" + bet);
		return false;
	}

	/**
	 * *Author:Havish Jain This method is used to verify currency symbol in bet
	 * setting screen
	 * 
	 * @throws InterruptedException
	 */
	public boolean betSettingCurrencySymbol(String currency, Desktop_HTML_Report report) {
		String totalBet = func_GetText(XpathMap.get("OneDesigntotalbet"));
		if (totalBet.indexOf(currency) < 0) {
			return true;
		}
		if (currency == null || currency.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * *Author:Havish Jain This method is used to wait till win amount occur in
	 * free spin
	 * 
	 * @throws InterruptedException
	 */
	public boolean waitforWinAmount(Desktop_HTML_Report currency, String currencyName) {
		Wait = new WebDriverWait(webdriver, 180);
		boolean b = false;
		String win = "";
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("FSWinOverlay"))));
			currency.detailsAppendFolder("Verify win overlay in Free Spins",
					"Win Overlay should display above the reel container", "Win Overlay is displaying", "Pass",
					currencyName);
			while (true) {
				win = func_GetText(XpathMap.get("FSWinValue"));
				if (!win.isEmpty()) {
					b = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean Helpclick() {
		webdriver.findElement(By.xpath(XpathMap.get("Help_Icon"))).click();
		return true;
	}// Close_Popup

	/* Click on quick spin toggle button */
	public boolean QuickSpinclick() {
		Wait = new WebDriverWait(webdriver, 5000);
		try {
			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("OneDesignQuickSpin"))));
			func_Click(XpathMap.get("OneDesignQuickSpin"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * *Author:Premlata This method is used to slide the coin size slider
	 * 
	 * @throws InterruptedException
	 *//*
		 * public void Coinselectorclose() throws InterruptedException {
		 * func_Click(XpathMap.get("OneDesignCoinSliderClose")); //
		 * Thread.sleep(1000);
		 * Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.
		 * get("OneDesignBetbtnyes"))));
		 * func_Click(XpathMap.get("OneDesignBetbtnyes")); }
		 */
	public void Coinselectorclose() {

		func_Click(XpathMap.get("OneDesignCoinSliderClose"));
		try {
			webdriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			boolean test = webdriver.findElements(By.xpath(XpathMap.get("OneDesignBetbtnyes"))).size() > 0;
			if (test) {
				Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("OneDesignBetbtnyes"))));
				func_Click(XpathMap.get("OneDesignBetbtnyes"));
				Thread.sleep(2000);
			} else {
				System.out.println("yes button is not in the game");
			}
		} catch (Exception e) {
		}
	}

	/**
	 * *Author:Premlata This method is used to slide the coin size slider
	 * 
	 * @throws InterruptedException
	 */
	public void moveCoinSizeSlider() throws InterruptedException {
		Wait = new WebDriverWait(webdriver, 5000);
		try {
			func_Click(XpathMap.get("OneDesignbetbutton"));
			WebElement coinSizeSlider = webdriver.findElement(By.xpath(XpathMap.get("OneDesignCoinSizeSlider_ID")));
			Thread.sleep(3000);
			Actions action = new Actions(webdriver);
			action.dragAndDropBy(coinSizeSlider, 127, 0).build().perform();

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	/**
	 * *Author:Premlata This method is used to wait till the free spin entry
	 * screen won't come
	 * 
	 * @throws InterruptedException
	 */
	public String entryScreen_Wait() {
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("FreeSpin_ClickToContinue"))));
		String wait = func_GetText(XpathMap.get("FreeSpin_ClickToContinue"));
		return wait;
	}

	/**
	 * *Author:Premlata This method is used to wait till the free spin summary
	 * screen won't come
	 * 
	 * @throws InterruptedException
	 */
	public String summaryScreen_Wait() {
		Wait = new WebDriverWait(webdriver, 500);
		String wait = null;
		try {
			boolean test = webdriver.findElement(By.id(XpathMap.get("FSSummaryCountinue_ID"))).isDisplayed();
			if (test) {
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FSSummaryCountinue_ID"))));
				wait = webdriver.findElement(By.id(XpathMap.get("FSSummaryCountinue_ID"))).getText();
			} else {
				Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FSSummaryCountinue_ID"))));
				wait = "summaryScreen";
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return wait;
	}

	/**
	 * *Author:Premlata This method is used to click on free spin enrtry screen
	 * 
	 * @throws InterruptedException
	 */
	public String clickToCntinue() {
		func_Click(XpathMap.get("FreeSpin_ClickToContinue"));
		String FS_Credits = func_GetText(XpathMap.get("FreeSpin_Credits"));
		return FS_Credits;
	}

	public String FS_Credits() {
		String FS_Credits1 = null;
		try {
			// String
			// FS_Credits=func_GetText(XpathMap.get("FreeSpin_Credits_ID"));
			String FS_Credits = webdriver.findElement(By.id(XpathMap.get("FreeSpin_Credits_ID"))).getText();
			FS_Credits1 = func_String_Operation(FS_Credits);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FS_Credits1;
	}

	public String FS_summaryScreenClick(Desktop_HTML_Report report) {
		String fs_credits = null;
		try {
			fs_credits = FS_Credits();
			if (XpathMap.get("FreeSpinEntryScreen").equalsIgnoreCase("yes")) {
				webdriver.navigate().refresh();
				error_Handler(report);
				Wait.until(
						ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("FSSummaryCountinue_ID"))));
				func_Click_BYID(XpathMap.get("FSSummaryCountinue_ID"));
			} else {
				fs_credits = FS_Credits();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fs_credits;

	}

	/**
	 * *Author:Premlata This method is used to wait to come free spin screen
	 * ,after refreshing the free spin
	 * 
	 * @throws InterruptedException
	 */
	public String FS_RefreshWait() {
		String balance = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeSpin_Credits_ID"))));
			balance = webdriver.findElement(By.id(XpathMap.get("FreeSpin_Credits_ID"))).getText();
		} catch (Exception e) {
			e.getMessage();
		}
		return balance;
	}
	/*
	 * This method is to click on free spin's "click to continue"
	 */
	/*
	 * public void FS_continue() {
	 * 
	 * webdriver.findElement(By.className("labelFS")).click(); }
	 */

	/* Havish Jain: Wait for Spin button */
	public void waitForSpinButton() {
		Wait = new WebDriverWait(webdriver, 600);
		try {
			log.debug("Waiting for base scene to come after completion of FreeSpin");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("spinButtonBox"))));
		} catch (Exception e) {
			log.error("error while waiting for base scene to come", e);
		}
	}

	public boolean winHistoryClick() throws Exception {
		return false;
	}

	/* Havish Jain: Click on Start Free Spin button in FS */
	public void FS_Start() {
		try {
			log.debug("Clicked on start button");
			webdriver.findElement(By.id(XpathMap.get("StartFSID"))).click();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/* Havish Jain: Click on Continue Free Spin button in FS */
	public void FS_continue() {
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("ContinueFSID"))));
			webdriver.findElement(By.id(XpathMap.get("ContinueFSID"))).click();
			log.debug("Clicked on free spin continue button");
		} catch (Exception e) {
			log.error("error while clicking on free spin continue button", e);
		}
	}

	/**
	 * *Author:Premlata This method is used to wait to come BIg win overlay
	 */
	public void bigWin_Wait() {
		Wait = new WebDriverWait(webdriver, 500);
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("OD_BIgWin_ID"))));
	}

	public boolean open_TotalBet() {/*
		boolean b = false;
		try {
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			webdriver.findElement(By.xpath(XpathMap.get("OneDesignbetbutton"))).click();
			// func_Click_BYID(XpathMap.get("OneDesignbetbutton"));
			log.debug("clicked on total bet button");
			Thread.sleep(2000);
			b = true;
		} catch (Exception e) {
			log.error("error in open_TotalBet method", e);
		}
		return b;*/
		

		Wait = new WebDriverWait(webdriver, 5000);
		try {
			func_Click(XpathMap.get("OneDesignbetbutton"));
			WebElement coinSizeSlider = webdriver.findElement(By.xpath(XpathMap.get("OneDesignCoinSizeSlider_ID")));
			Thread.sleep(3000);
			Actions action = new Actions(webdriver);
			action.dragAndDropBy(coinSizeSlider, 500, 0).build().perform();

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return false;
	}

	public void closeOverlay() {
		try {
			Actions act = new Actions(webdriver);
			/*act.moveByOffset(100, 100).click().build().perform();
			act.moveByOffset(-180, -180).build().perform();*/
			act.moveByOffset(200,200).click().build().perform();
			act.moveByOffset(-200, -200).build().perform();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void close_TotalBet() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(webdriver, 60);
		try {
			System.out.println(XpathMap.get("TotalBet_OvelayID"));
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("TotalBet_OvelayID"))));
			// webdriver.findElement(By.xpath("//div[@id='inGameClock']")).click(); 
			//open_TotalBet();
			closeOverlay();
			//func_Click(XpathMap.get("OneDesignbetbutton"));
			//webdriver.findElement(By.id(XpathMap.get("OneDesignbetbuttonID"))).click();
			//webdriver.findElement(By.id(XpathMap.get("moduleContent"))).click();
			
			log.debug("Clicked to close totalBet overlay");
		} catch (Exception e) {
			log.error("error while closing bet", e);
		}
	}
	/*
	 * Date: 22/04/2019 Description:To verify Autoplay with quick spin on
	 * Parameter: NA
	 * 
	 * @return boolean
	 */

	public void autoPlay_with_QS_On(Desktop_HTML_Report report) {

		boolean qS_Test = false;
		try {
			Thread.sleep(5000);

			WebElement qsoffele = webdriver.findElement(By.xpath(XpathMap.get("QuickSpin_Off")));
			boolean qsoff = qsoffele.isDisplayed();

			if (qsoff) {
				qsoffele.click();

				boolean qson = webdriver.findElement(By.xpath(XpathMap.get("QuickSpin_On"))).isDisplayed();
				if (qson) {
					webdriver.findElement(By.id(XpathMap.get("AutoplayID"))).click();
					qS_Test = true;
					log.debug("Clicked on autoplay");
				} else {
					qS_Test = false;
				}

			}
		} catch (Exception e)

		{
			log.error("Autoplay is  not clickable with Quick Spin on", e);
		}

	}

	public boolean open_Autoplay() {
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSpin")));
			// Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			func_Click_BYID(XpathMap.get("AutoplayID"));
			log.debug("Clicked on autoplay button");
			Thread.sleep(2000);
			b = true;
		} catch (Exception e) {
			log.error("error while opening the autoplay", e);
		}
		return b;
	}

	public void close_Autoplay() throws InterruptedException {
		try {
			Thread.sleep(500);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("Autoplay_OverlayID"))));
			// webdriver.findElement(By.xpath("//div[@id='inGameClock']")).click();
			// open_Autoplay();
			closeOverlay();
			log.debug("clicked on screen to close overlay");
			Thread.sleep(500);
		} catch (Exception e) {
			log.error("Close Autoplay Error", e);
		}
	}

	/*
	 * Author: Havish Jain Description:This function is used to take screenshot
	 * of jackpot summary screen and click on back to game button return true
	 */
	public void jackpotSummary(Desktop_HTML_Report language, String languageCode) {
		Wait = new WebDriverWait(webdriver, 500);
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("winStrip"))));
			language.detailsAppendFolder("Verify language on Win Box which diplays above the reel container",
					"Win Box should display above the reel container", "Win Box displays", "Pass", languageCode);
			Thread.sleep(2500);
			spinclick();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("JackpotSummaryID"))));
			Thread.sleep(1000);
			language.detailsAppendFolder("Verify language on Jackpot Summary Screen",
					"Jackpot Summary Screen should display", "Jackpot Summary Screen displays", "Pass", languageCode);
			Thread.sleep(1000);
			webdriver.findElement(By.id(XpathMap.get("BackToGameID"))).click();
			Thread.sleep(1500);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean freeGamesEntryScreen() {
		Wait = new WebDriverWait(webdriver, 500);
		boolean str = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesPlayNow"))));
			Thread.sleep(1000);
			String text = webdriver.findElement(By.id(XpathMap.get("FreeGamesPlayNow"))).getText();
			if (text != null)
				str = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public boolean freeGameEntryInfo() {
		Wait = new WebDriverWait(webdriver, 500);
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesPlayNow"))));
			webdriver.findElement(By.id(XpathMap.get("FreeGamesEntryInfoIcon"))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesInfoDetails"))));
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean clickPlayNow() {
		Wait = new WebDriverWait(webdriver, 500);
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesPlayNow"))));
			webdriver.findElement(By.id(XpathMap.get("FreeGamesPlayNow"))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesBaseSceneDiscard"))));
			b = true;
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public String freeGamesResumescreen() {
		Wait = new WebDriverWait(webdriver, 500);
		String str = null;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesResumeButton"))));
			Thread.sleep(1000);
			str = webdriver.findElement(By.id(XpathMap.get("FreeGamesResumeButton"))).getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public boolean freeGameResumeInfo() {
		Wait = new WebDriverWait(webdriver, 500);
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesResumeButton"))));
			webdriver.findElement(By.id(XpathMap.get("FreeGamesResumeInfoIcon"))).click();
			Wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesResumeInfoDetails"))));
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean resumeScreenDiscardClick() {
		Wait = new WebDriverWait(webdriver, 500);
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesResumeButton"))));
			webdriver.findElement(By.id(XpathMap.get("FreeGamesResumeDiscard"))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesDiscardButton"))));
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean confirmDiscardOffer() {
		Wait = new WebDriverWait(webdriver, 500);
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesDiscardButton"))));
			webdriver.findElement(By.id(XpathMap.get("FreeGamesDiscardButton"))).click();
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesBackToGames"))));
			Thread.sleep(1500);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public boolean freeGamesExpriyScreen() {
		Wait = new WebDriverWait(webdriver, 500);
		boolean b = false;
		try {
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("FreeGamesExpiredContinue"))));
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	public String freeGamesDiscardExistingOffer() {
		Wait = new WebDriverWait(webdriver, 100);
		String currentScene = null;
		try {
			webdriver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			if (webdriver.findElements(By.id(XpathMap.get("FreeGamesResumeButton"))).size() > 0) {
				currentScene = "FreeGameResume";
				resumeScreenDiscardClick();
				confirmDiscardOffer();
				clickNextOffer();
			} else {
				currentScene = "freeGamesExpiredView";
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return currentScene;
	}

	/*
	 * Author:Havish This method is used to click on Next Offer button
	 * 
	 * @throws InterruptedException
	 */
	public void clickNextOffer() {
		try {
			if (webdriver.findElements(By.id(XpathMap.get("FreeGamesNextOffer"))).size() > 0) {
				webdriver.findElement(By.id(XpathMap.get("FreeGamesNextOffer"))).click();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	/*
	 * Author:Havish This method is used click on Discard button in base scene
	 * during Free Games
	 */
	public void clickBaseSceneDiscard() {
		try {
			if (webdriver.findElements(By.id(XpathMap.get("FreeGamesBaseSceneDiscard"))).size() > 0) {
				webdriver.findElement(By.id(XpathMap.get("FreeGamesBaseSceneDiscard"))).click();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public String freeGamesContinueExpiry() {
		Wait = new WebDriverWait(webdriver, 100);
		try {
			webdriver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
			if (webdriver.findElements(By.id(XpathMap.get("FreeGamesExpiredContinue"))).size() > 0) {
				webdriver.findElement(By.id(XpathMap.get("FreeGamesExpiredContinue"))).click();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return null;
	}

	/**
	 * * Date: 29/05/2018 Author: Premlata Mishra Description: This function is
	 * used to set the bet to minimum Parameter:
	 */
	public void setMinBet() {
		try {
			for (int i = 0; i < 5; i++) {
				WebElement CoinSizeSlider = webdriver.findElement(By.id(XpathMap.get("OneDesignCoinSizeSlider_ID")));
				Actions action = new Actions(webdriver);
				action.dragAndDropBy(CoinSizeSlider, 30, 0).build().perform();
			}
			webdriver.findElement(By.xpath(XpathMap.get("OneDesignBetDecrement")));
			for (int i = 0; i < 12; i++) {
				betDecrease();
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * Date:10-1-2018 Name:Premlata Mishra Description: this function is open
	 * paytable page
	 * 
	 * @throws Exception
	 */
	public void openPaytable() {
		try {
			func_Click(XpathMap.get("OneDesign_Hamburger"));
			Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("OneDesign_Paytable"))));
			func_Click(XpathMap.get("OneDesign_Paytable"));
			Thread.sleep(2000);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * * Date: 29/05/2018 Author: Premlata Mishra Description: This function is
	 * used to fetch payout from paytable
	 * 
	 * Parameter:
	 */
	public double gamepayout(String xpath) {
		double gamepayout_Double = 0.0;
		try {
			String gamePayout = func_GetText(xpath);
			String gamePayoutnew = func_String_Operation(gamePayout).replaceAll(",", "");
			gamepayout_Double = Double.parseDouble((gamePayoutnew));
		} catch (Exception e) {
			e.getMessage();
		}
		return gamepayout_Double;
	}

	/**
	 * * Date: 29/05/2018 Author: Premlata Mishra Description: This function is
	 * used to verify paytable and it's values Parameter:
	 */
	public double verifyPaytable_Payouts(String xmlpayout, double payline, double bet, String scatter) {
		double verifypayout = 0.0, intPayout, verifypayoutnew = 0.0;
		try {
			// double gamepayout_Double=gamepayout(xpath);
			intPayout = Integer.parseInt(xmlpayout);// xmlpayout
			if (scatter.equalsIgnoreCase("yes")) {
				verifypayout = (bet * intPayout);
				verifypayoutnew = round(verifypayout);
				return verifypayoutnew;
			} else {
				verifypayout = (bet * intPayout) / payline;
				verifypayoutnew = round(verifypayout);
				return verifypayoutnew;
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return verifypayout;
	}

	/**
	 * Date:10-1-2018 Name:Premlata Mishra Description: this function is used to
	 * Scroll the page and to take the screenshot
	 * 
	 * @throws Exception
	 */
	public void paytableScroll(String ele) {
		JavascriptExecutor js = (JavascriptExecutor) webdriver;
		WebElement ele1 = webdriver.findElement(By.xpath(ele));
		js.executeScript("arguments[0].scrollIntoView(true);", ele1);
	}

	public double round(double number) {
		/*
		 * DecimalFormat dnf = new DecimalFormat( "#,###,###,##0.00" ); double
		 * roundednumber = new Double(dnf.format(number)).doubleValue();
		 */
		DecimalFormat dnf = new DecimalFormat("#.##");
		double roundednumber = new Double(dnf.format(number));
		return roundednumber;
	}

	/**
	 * * Date: 29/05/2018 Author: Premlata Mishra Description: This function is
	 * used to verify credit bubble and it's values Parameter:
	 */
	public String verifycreditBubble(String locator) {
		String bet = null;
		try {
			String betValue = func_GetText(locator);
			bet = betValue.replaceAll("[^0-9]", "");
		} catch (Exception e) {
			e.getMessage();
		}
		return bet;
	}

	/**
	 * * Date: 29/05/2018 Author: Premlata Mishra Description: This function is
	 * used to verify credit bubble and it's values
	 * 
	 * Parameter:
	 */
	public Map<String, Integer> creditBubble() throws Exception {
		Map<String, Integer> userValue = new HashMap<>();
		int creditbubbleBalstr;
		int bonusInBubbleValdouble;
		int totalcreditValdouble = 0;
		try {
			// Thread.sleep(5000);
			// func_Click(XpathMap.get("//*[@id='btnCoinsCredits']"));
			webdriver.findElement(By.id("btnCoinsCredits")).click();
			Thread.sleep(3000);
			if (webdriver.findElement(By.xpath(XpathMap.get("Creditbubble"))).isDisplayed()) {
				creditbubbleBalstr = Integer.parseInt(verifycreditBubble(XpathMap.get("creditBubble_Balance")));
				bonusInBubbleValdouble = Integer.parseInt(verifycreditBubble(XpathMap.get("creditBubble_Bonus")));
				totalcreditValdouble = Integer.parseInt(verifycreditBubble(XpathMap.get("CreditBubble_TB")));
				userValue.put("Balance", creditbubbleBalstr);
				userValue.put("Bonus", bonusInBubbleValdouble);
				userValue.put("TotalCredit", totalcreditValdouble);
				return userValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	public String GetBetAmtString() {
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("OneDesign_Spin_Button"))));
		String betVal = func_GetText(XpathMap.get("OneDesign_BetValue"));
		String CreditVal = func_String_Operation(betVal);
		// double CreditVal1=Double.parseDouble(CreditVal);
		return CreditVal;
	}

	public String getAttributeXpath(String xpath, String attr) {
		String style = webdriver.findElement(By.xpath(xpath)).getAttribute(attr);
		return style;
	}

	public boolean quickSpinOff() {
		boolean flag = false;
		try {
			spinclick();
			Thread.sleep(2000);
			if (webdriver.findElement(By.xpath(XpathMap.get("OneDesign_Stop_Button"))).isDisplayed())
				flag = true;
			else
				flag = false;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public boolean quickSpinOn() {
		boolean flag = false;
		try {
			spinclick();
			Thread.sleep(2000);
			if (!webdriver.findElement(By.xpath(XpathMap.get("OneDesign_Stop_Button"))).isDisplayed())
				flag = true;
			else
				flag = false;

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public void quickSpinClick() {
		WebElement quickSpinButton = webdriver.findElement(By.xpath(XpathMap.get("quickSpinDiv")));

		new WebDriverWait(webdriver, 120).until(ExpectedConditions.elementToBeClickable(quickSpinButton));
		quickSpinButton.click();
	}

	public boolean verifyPaytablePresent() {
		WebDriverWait wait;
		boolean flag = false;

		try {
			wait = new WebDriverWait(webdriver, 20);
			wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("deal"))));
			if (webdriver.findElement(By.id(XpathMap.get("paytableContainer"))).isDisplayed())
				flag = true;
			else
				flag = false;
			System.out.println(flag);
		} catch (Exception e) {
			log.error("Error in paytable display");
		}
		return flag;
	}

	@Override
	public boolean dealClick() throws InterruptedException
	{
		WebDriverWait wait;
		boolean flag = false;
		try {
			wait = new WebDriverWait(webdriver, 30);
			// *********Clicking on Deal and Draw button
			log.debug("Finding Deal element on the page");
			wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("deal"))));
			webdriver.findElement(By.id(XpathMap.get("deal"))).click();
			log.debug("Successfully clicked on Deal button");
			log.debug("Finding draw button on the page");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("draw"))));
			flag = true;
		} catch (Exception e) {
			log.error("Error while clicking on Draw button", e);
		}
		return flag;
	}
	
	
	
	@Override
	public boolean drawClick() throws InterruptedException {
		WebDriverWait wait;
		boolean flag = false;
		try {
			wait = new WebDriverWait(webdriver, 600);

			// **********Clicking on Draw button
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("draw"))));
			webdriver.findElement(By.id(XpathMap.get("draw"))).click();
			Thread.sleep(1000);
			log.debug("Successfully clicked on draw button");
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("collectBtn"))));

			// **********Clicking on module content to stop win counting
			webdriver.findElement(By.id("moduleContent")).click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("collectBtn"))));
			Thread.sleep(3000);
			flag = true;

		} catch (Exception e) {
			log.error("Error while clicking on Draw button", e);
		}
		return flag;
	}

	@Override
	public boolean drawCollectBaseGame(Desktop_HTML_Report report, String languageCode) throws Exception {
		double creditBalBeforeWin = 0.0;
		double creditBalWin = 0.0;
		double creditBalAfterWin = 0.0;
		WebDriverWait wait;
		WebElement winValue;
		double win = 0.0;
		double bet;
		boolean flag = false;

		try {
			wait = new WebDriverWait(webdriver, 200);

			// ********Collecting credit balance before win
			wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("collectBtn"))));
			creditBalBeforeWin = getCreditAmtDouble();

			// *********Collecting win value
			win = getWinAmtDouble(XpathMap.get("winValue"));
			if (win != 0.0) 
			{
			// ***********Clicking on collect button on double to screen
			webdriver.findElement(By.id(XpathMap.get("collectBtn"))).click();
			Thread.sleep(3000);

			// *************Capturing credit balance after collecting win
			creditBalAfterWin = getCreditAmtDouble();
			creditBalWin = (creditBalBeforeWin + win);
			if (Double.compare(creditBalWin, creditBalAfterWin) == 0) {
				if (languageCode != null) {
					report.detailsAppendFolder("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button",
							"Credit balance should get updated"
									+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin,
							"Pass", languageCode);
					resizeBrowser(600, 800);
					Thread.sleep(1000);
					report.detailsAppendFolder("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button",
							"Credit balance should get updated"
									+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin,
							"Pass", languageCode);
					resizeBrowser(400, 600);
					Thread.sleep(1000);
					report.detailsAppendFolder("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button",
							"Credit balance should get updated"
									+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin,
							"Pass", languageCode);
					webdriver.manage().window().maximize();
				}

				else {
					report.detailsAppend("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button",
							"Credit balance should get updated"
									+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin,
							"Pass");

				}
			

			} else {
				if (languageCode != null) {
					report.detailsAppendFolder("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button ,credit balance should get updated",
							"Credit balance is not updated"+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin, "Fail", languageCode);
				} else {
					report.detailsAppend("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button ,credit balance should get updated",
							"Credit balance is not updated"+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin, "Fail");
				}
			}
			
		}
		else
		{
			report.detailsAppend("Verify Deal, Draw and Collect functionality",
					"On Clicking deal and draw button ,credit balance should get updated",
					"Win has not been triggered", "Fail");
		}

		} catch (Exception e) {
			log.error("Error in collecting win on base game");
			report.detailsAppend("Verify Deal, Draw and Collect functionality",
					"On Clicking deal and draw button ,credit balance should get updated",
					"Credit balance is not updated" + "Credit balance is updated successfully :- Credit balance before win - "
							+ creditBalWin + " Win amount - " + win + " Credit balance after win - "
							+ creditBalAfterWin, "Fail");
		}
		return flag;
	}
	
	
	
	
	/*@Override
	public boolean drawCollectBaseGameFunctionality(Desktop_HTML_Report report, String languageCode) throws Exception {
		double creditBalBeforeWin = 0.0;
		double creditBalAfterWin = 0.0;
		WebDriverWait wait;
		WebElement winValue;
		double win = 0.0;
		double bet;
		boolean flag = false;

		try {
			wait = new WebDriverWait(webdriver, 200);

			// ********Collecting credit balance before win
			wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("collectBtn"))));
			creditBalBeforeWin = getCreditAmtDouble();

			// *********Collecting win value
			win = getWinAmtDouble(XpathMap.get("winValue"));
			if (win != 0.0) 
			{
			// ***********Clicking on collect button on double to screen
			webdriver.findElement(By.id(XpathMap.get("collectBtn"))).click();
			Thread.sleep(3000);

			// *************Capturing credit balance after collecting win
			creditBalAfterWin = getCreditAmtDouble();
			creditBalBeforeWin = (creditBalBeforeWin + win);
			if (Double.compare(creditBalBeforeWin, creditBalAfterWin) == 0) {
				if (languageCode != null) {
					report.details_append_folder("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button",
							"Credit balance should get updated"
									+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin,
							"Pass", languageCode);
					resizeBrowser(600, 800);
					Thread.sleep(1000);
					report.details_append_folder("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button",
							"Credit balance should get updated"
									+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin,
							"Pass", languageCode);
					resizeBrowser(400, 600);
					Thread.sleep(1000);
					report.details_append_folder("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button",
							"Credit balance should get updated"
									+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin,
							"Pass", languageCode);
					webdriver.manage().window().maximize();
				}

				else {
					report.details_append("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button",
							"Credit balance should get updated"
									+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin,
							"Pass");

				}
			

			} else {
				if (languageCode != null) {
					report.details_append_folder("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button ,credit balance should get updated",
							"Credit balance is not updated"+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin, "Fail", languageCode);
				} else {
					report.details_append("Verify Deal, Draw and Collect functionality",
							"On Clicking deal and draw button ,credit balance should get updated",
							"Credit balance is not updated"+ "Credit balance is updated successfully :- Credit balance before win - "
									+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
									+ creditBalAfterWin, "Fail");
				}
			}
			
		}
		else
		{
			report.details_append("Verify Deal, Draw and Collect functionality",
					"On Clicking deal and draw button ,credit balance should get updated",
					"Win has not been triggered", "Fail");
		}

		} catch (Exception e) {
			log.error("Error in collecting win on base game");
			report.details_append("Verify Deal, Draw and Collect functionality",
					"On Clicking deal and draw button ,credit balance should get updated",
					"Credit balance is not updated" + "Credit balance is updated successfully :- Credit balance before win - "
							+ creditBalBeforeWin + " Win amount - " + win + " Credit balance after win - "
							+ creditBalAfterWin, "Fail");
		}
		return flag;
	}*/
	

	@Override
	public boolean doubleToCollect(Desktop_HTML_Report report) throws Exception {
		WebDriverWait wait;
		WebElement winValue;
		double win = 0.0;
		double creditBalBeforeWin = 0.0;
		double creditBalWin = 0.0;
		double creditBalAfterWin = 0.0;
		boolean flag = false;

		try {
			wait = new WebDriverWait(webdriver, 600);

			// *********Clicking on Deal and Draw button
			wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("deal"))));

			if (dealClick()) {
				if (drawClick()) {
					
					Thread.sleep(3000);
					
					//********For 50Play Paytable close functionality 
					/*if(webdriver.findElements(By.id(XpathMap.get("PaytableClose"))).size()!=0)
					{
						webdriver.findElement(By.id(XpathMap.get("PaytableClose"))).click();
					}*/
					
					// ********Collecting credit balance before win
					creditBalBeforeWin = getCreditAmtDouble();
					log.debug("Credit balance before win - " + creditBalBeforeWin);
					System.out.println("Credit balance before win - " + creditBalBeforeWin);
					Thread.sleep(5000);

					// *******Clicking on double to button
					wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("double"))));
					webdriver.findElement(By.id(XpathMap.get("double"))).click();
					System.out.println("After Double button");
					Thread.sleep(2000);

					// **************Waiting for navigation on double to screen
					wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("doubleSection"))));
					Thread.sleep(2000);
					wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("doubleCard"))));
					report.detailsAppend("Verify double and collect functionality",
							"On Clicking double button ,double to screen should be open ", "Double to screen is open",
							"Pass");
					webdriver.findElement(By.id(XpathMap.get("doubleCard"))).click();
					Thread.sleep(1000);
					
					webdriver.findElement(By.id(XpathMap.get("moduleContent"))).click();
					Thread.sleep(5000);

					// ************Collecting win amount
					win = getWinAmtDouble(XpathMap.get("doubleWinAmt"));
					if (win != 0.0) {
						System.out.println("Win amount " + win);
						report.detailsAppend("Verify double and collect functionality",
								"Win should displayed after selecting card on double to screen",
								"Win amount should displayed", "Pass");

						// ***********Clicking on collect button on double to
						// screen
						/*webdriver.findElement(By.id(XpathMap.get("collectBtn"))).click();
						wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("deal"))));*/

						//Clicking on double button on double to
						webdriver.findElement(By.id(XpathMap.get("doublebutton"))).click();
						Thread.sleep(5000);
						
						wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("doubleCard"))));
						webdriver.findElement(By.id(XpathMap.get("doubleCard"))).click();
						Thread.sleep(5000);
						
						// *************Capturing credit balance after
						// collecting win
						creditBalAfterWin = getCreditAmtDouble();
						log.debug("Credit balance after win - " + creditBalAfterWin);
						creditBalWin = creditBalBeforeWin + win;
						log.debug("Balance after adding win " + creditBalWin);
						System.out.println("Balance after adding win " + creditBalWin);

						// *************Comparing Credit balance before win and
						// after win
						if (Double.compare(creditBalWin, creditBalAfterWin) == 0) {

							report.detailsAppend("Verify double and collect functionality",
									"On Clicking double button ,double to screen open and collecting win amount and balace should get updated",
									"Credit balance before win - " + creditBalBeforeWin + " Win amount - " + win
											+ " Credit balance after win - " + creditBalAfterWin,
									"Pass");
						} else {

							report.detailsAppend("Verify double and collect functionality",
									"On Clicking double button ,double to screen open and collecting win amount and balace should get updated",
									"Credit balance before win - " + creditBalBeforeWin + " Win amount - " + win
											+ " Credit balance after win - " + creditBalAfterWin,
									"Fail");
						}
						log.debug("Credit balance after win is correctly updated. Win amount  " + win);
						System.out.println("Flag status - " + flag);
					} else {
						report.detailsAppend("Verify double and collect functionality",
								"On Clicking double button ,double to screen open and collecting win amount and balace should get updated",
								"Win not triggered", "Fail");

					}
					
					Thread.sleep(5000);
					//Clicking on back button 
					wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("backToGame"))));
					webdriver.findElement(By.id(XpathMap.get("backToGame"))).click();
				}

				else {
					log.error("Error in Draw functionality ");
					report.detailsAppend("Verify double and collect functionality",
							"On Clicking double button ,double to screen open and collecting win amount and balace should get updated",
							"Error in Draw functionality ", "Fail");
				}
			} else {
				log.error("Error in Deal functionality ");
				report.detailsAppend("Verify double and collect functionality",
						"On Clicking double button ,double to screen open and collecting win amount and balace should get updated",
						"Error in Deal functionality ", "Fail");
			}
		}

		catch (Exception e) {
			log.error("Error while collecting win amount on double to screen");
			report.detailsAppend("Verify double and collect functionality",
					"On Clicking double button ,double to screen open and collecting win amount and balace should get updated",
					"Credit balance before win - " + creditBalBeforeWin + " Win amount - " + win
							+ " Credit balance after win - " + creditBalAfterWin,
					"Fail");

		}
		return flag;

	}

	public double getCreditAmtDouble() {
		String creditBal;
		double creditBalance = 0.0;
		try {
			creditBal = webdriver.findElement(By.id(XpathMap.get("creditBal"))).getText();
			creditBalance = Double.parseDouble(creditBal.replaceAll("[^.0-9]", ""));
			
		} catch (Exception e) {
			log.error("Error in fetching credit balance - " + e);
			return creditBalance;
		}
		return creditBalance;
	}

	public double getBetAmtDouble() {

		String betVal;
		double betBal = 0.0;
		try {
			betVal = webdriver.findElement(By.id(XpathMap.get("betBal"))).getText();
			betBal = Double.parseDouble(betVal.replaceAll("[^0-9]", ""));
		} catch (Exception e) {
			log.error("Error in fetching bet - " + e);
			return betBal;
		}
		return betBal;

	}

	public double getWinAmtDouble(String xpathVal) {
		String winValue;
		double win = 0.0;

		try {
			winValue = webdriver.findElement(By.xpath(xpathVal)).getText();
			win = Double.parseDouble(winValue.replaceAll("[^.0-9]", ""));
		} catch (Exception e) {
			log.error("Error in fetching bet - " + e);

		}
		return win;
	}

	public void doubleToGambleReachedFunctionality() throws InterruptedException {
		WebDriverWait wait;
		double win = 0.0;
		double creditBalBeforeWin;
		double creditBalAfterWin;
		boolean flag = false;
		try {

			wait = new WebDriverWait(webdriver, 200);
			wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("deal"))));

			// *********Clicking on Deal and Draw button
			if (dealClick()) {
				if (drawClick()) {
					Thread.sleep(2000);
					creditBalBeforeWin = getCreditAmtDouble();
					log.debug("Credit balance before win - " + creditBalBeforeWin);
					do {
						// *********Clicking on Double button
						wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("double"))));
						webdriver.findElement(By.id(XpathMap.get("double"))).click();
						Thread.sleep(5000);
						wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("doubleSection"))));
						Thread.sleep(2000);

						// ***************Click on card
						webdriver.findElement(By.id(XpathMap.get("doubleCard"))).click();
						Thread.sleep(1000);
						webdriver.findElement(By.id(XpathMap.get("moduleContent"))).click();
						Thread.sleep(5000);
					} while (!webdriver.findElement(By.xpath(XpathMap.get("gambleLimit"))).isDisplayed());

					wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("backToGame"))));
					Thread.sleep(2000);
					win = getWinAmtDouble(XpathMap.get("doubleWinAmt"));
					log.debug("Successfully captured win amount " + win);
					creditBalAfterWin = getCreditAmtDouble();
					log.debug("Credit balance after win - " + creditBalAfterWin);
					creditBalBeforeWin = creditBalBeforeWin + win;
					log.debug("Balance after adding win " + creditBalBeforeWin);
					if (Double.compare(creditBalBeforeWin, creditBalAfterWin) == 0)
						flag = true;
					else
						flag = false;
					webdriver.findElement(By.id(XpathMap.get("backToGame"))).click();
				} else {
					log.error("Error in Draw functionality");
				}

			} else {

			}

		} catch (Exception e) {
			log.error("Error in double to feature - " + e);
		}

	}

	@Override
	public void doubleToGambleReached(Desktop_HTML_Report report, String languageCode) throws Exception {

		WebDriverWait wait;
		WebElement winValue;
		double win;

		try {

			wait = new WebDriverWait(webdriver, 200);
			wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("deal"))));

			// *********Clicking on Deal and Draw button
			if (dealClick()) {
				if (drawClick()) {
					Thread.sleep(1000);
					
					//Paytable Close functionality for 50 play
					/*if(webdriver.findElements(By.id(XpathMap.get("PaytableClose"))).size()!=0)
					{
						webdriver.findElement(By.id(XpathMap.get("PaytableClose"))).click();
					}
					Thread.sleep(1000);*/

					do {
						// *********Clicking on Double button
						wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("double"))));
						webdriver.findElement(By.id(XpathMap.get("double"))).click();
						wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("doubleSection"))));
						Thread.sleep(2000);

						// ***************Click on card
						wait.until(ExpectedConditions.attributeContains(
								webdriver.findElement(By.xpath(XpathMap.get("doubleCardFirst"))), "class",
								"vp card quick-transition"));
						
						webdriver.findElement(By.id(XpathMap.get("doubleCard"))).click();
						wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("winBox"))));
						closeOverlay();
						
						webdriver.findElement(By.id(XpathMap.get("moduleContent"))).click();
						Thread.sleep(5000);
						report.detailsAppendFolder("Verify double to functionality", "Double to functionality",
								"Double to functionality", "Pass", languageCode);
						resizeBrowser(600, 800);
						Thread.sleep(1000);
						report.detailsAppendFolder("Verify double to functionality", "Double to functionality",
								"Double to functionality", "Pass", languageCode);
						resizeBrowser(400, 600);
						Thread.sleep(1000);
						report.detailsAppendFolder("Verify double to functionality", "Double to functionality",
								"Double to functionality", "Pass", languageCode);
						webdriver.manage().window().maximize();
					} while (!webdriver.findElement(By.xpath(XpathMap.get("gambleLimit"))).isDisplayed());

					wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("backToGame"))));
					Thread.sleep(2000);
					if (webdriver.findElement(By.id(XpathMap.get("backToGame"))).isDisplayed()) {

						report.detailsAppendFolder("Verify Gamble reached functionality",
								"Gamble Reached functionality", "Gamble Reached text should be displayed", "Pass",
								languageCode);
						resizeBrowser(600, 800);
						Thread.sleep(1000);
						report.detailsAppendFolder("Verify Gamble reached functionality",
								"Gamble Reached functionality", "Gamble Reached text should be displayed", "Pass",
								languageCode);
						resizeBrowser(400, 600);
						Thread.sleep(1000);
						report.detailsAppendFolder("Verify Gamble reached functionality",
								"Gamble Reached functionality", "Gamble Reached text should be displayed", "Pass",
								languageCode);
						webdriver.manage().window().maximize();

					} else {
						report.detailsAppendFolder("Verify Gamble reached functionality",
								"Gamble Reached functionality", "Gamble Reached text is not displayed", "Fail",
								languageCode);
					}

					webdriver.findElement(By.id(XpathMap.get("backToGame"))).click();
					
				} else {
					log.error("Problem in clicking Draw button in Gamble reached functionality");
					report.detailsAppendFolder("Verify Gamble reached functionality",
							"Gamble Reached functionality", "Error in Draw functionality", "Fail",
							languageCode);
				}
			}

			else {
				log.error("Problem in clicking Deal button in Gamble reached functionality");
				report.detailsAppendFolder("Verify Gamble reached functionality",
						"Gamble Reached functionality", "Error in Deal functionality", "Fail",
						languageCode);
			}
		} catch (Exception e) {
			log.error("Error in double to feature - " + e);
			report.detailsAppendFolder("Verify Gamble reached functionality",
					"Gamble Reached functionality", "Gamble Reached text is not displayed", "Fail",
					languageCode);
		}

	}

	@Override
	public void paytableClickVideoPoker(Desktop_HTML_Report report, String languageCode) throws Exception {
		WebDriverWait wait;
		int size = 0;

		try {

			wait = new WebDriverWait(webdriver, 200);
			wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("paytableContainer"))));
			size = Integer.parseInt(XpathMap.get("paytableCoinsSize").replaceAll(".0", ""));

			for (int i = 1; i <= size; i++) {
				webdriver.findElement(By.id(XpathMap.get("paytableCoinsDisplay"))).click();
				report.detailsAppendFolder("Verify paytable functionality", "paytable functionality",
						"paytable functionality page" + i, "Pass", languageCode);
				resizeBrowser(600, 800);
				Thread.sleep(1000);
				report.detailsAppendFolder("Verify paytable functionality", "paytable functionality",
						"paytable functionality page" + i, "Pass", languageCode);
				resizeBrowser(400, 600);
				Thread.sleep(1000);
				report.detailsAppendFolder("Verify paytable functionality", "paytable functionality",
						"paytable functionality page" + i, "Pass", languageCode);
				webdriver.manage().window().maximize();
			}

		} catch (Exception e) {
			log.error("Error in paytable feature - " + e);
			report.detailsAppendFolder("Verify paytable functionality", "paytable functionality",
					"paytable functionality page", "Fail", languageCode);
		}
	}
	
	

	/*
	 * This method will wait for element to be visible
	 * 
	 * @param
	 * 
	 * @return
	 * 
	 * @throws
	 */
	public void waitFor(String locator) {
		Wait = new WebDriverWait(webdriver, 60);
		try {
			log.debug("Waiting For Drwa button to visible");
			Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
		} catch (Exception e) {
			log.error("error while waiting for element :" + locator, e);
		}
	}

	public boolean waitForWin() {
		boolean flag = false;
		Wait = new WebDriverWait(webdriver, 500);
		try {
			spinclick();
			webdriver.findElement(By.xpath(XpathMap.get("reelGrid"))).click();
			Thread.sleep(2000);
			if (webdriver.findElement(By.xpath(XpathMap.get("txtInfoBarWin"))).isDisplayed())
				flag = true;
			else
				flag = false;
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			flag = false;
		}

		return flag;

	}
	
	public boolean quickSpinDisabledForDenmark()
	{
		boolean flag = false;
		try
		{
			
			String classQuickAttr = XpathMap.get("classQuickAttr");
			WebElement we = webdriver.findElement(By.id("quickSpin"));
			String attr = we.getAttribute("class");
			
			if(attr.equals(classQuickAttr))
				flag = true;
			else
				flag = false;
			
		}
		catch(Exception e)
		{
			log.error("Error occured in quick spin check" + e);
		}
		
		return flag;
	}
	
	public boolean clockDisplayForDenmark()
	{
		boolean flag = true;
		try
		{
			if(webdriver.findElement(By.id(XpathMap.get("clock"))).isDisplayed())
				flag = true;
			else
				flag = false;
		}
		catch(Exception e)
		{
			log.error("Clock not displyed in the game" + e);
		}
		return flag;
	}
	
	public boolean helpLinkDisplayForDenmark()
	{
		boolean flag = false;
		WebDriverWait  wait = new WebDriverWait(webdriver, 60) ;
		try
		{
			webdriver.findElement(By.xpath(XpathMap.get("helpLinkMenu"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(webdriver.findElement(By.xpath(XpathMap.get("helpLink")))));
			if(webdriver.findElement(By.xpath(XpathMap.get("helpLink"))).isDisplayed())
				flag = true;
			else
				flag = false;
			closeOverlay();
			
		}
		
		catch(Exception e)
		{
			log.error("Error in help link display"+e);
		}
		return flag;
	}
	
	public boolean linkToPlayerProtection()
	{
		boolean flag = false;
		WebDriverWait  wait = new WebDriverWait(webdriver, 60) ;
		try
		{
			webdriver.findElement(By.xpath(XpathMap.get("helpLinkMenu"))).click();
			wait.until(ExpectedConditions.elementToBeClickable(webdriver.findElement(By.xpath(XpathMap.get("playerProtectionLink")))));
			if(webdriver.findElement(By.xpath(XpathMap.get("playerProtectionLink"))).isDisplayed())
				flag = true;
			else
				flag = false;
			closeOverlay();
		}
		catch(Exception e)
		{
			log.error("Error in player protection link display"+e);
			
		}
		return false;
		
	}
	/**
	 * This methode Set conSizeSliderTo Max bet
	 * @return 
	 */
	public boolean moveCoinSizeSliderToMaxBet() {
		boolean isSetToMaxBet=false;
		Wait = new WebDriverWait(webdriver, 5000);
		try {
			func_Click(XpathMap.get("OneDesignbetbutton"));
			WebElement coinSizeSlider = webdriver.findElement(By.xpath(XpathMap.get("OneDesignCoinSizeSlider_ID")));
			Thread.sleep(2000);
			Actions action = new Actions(webdriver);
			action.dragAndDropBy(coinSizeSlider, 500, 0).build().perform();
			Thread.sleep(2000);
			isSetToMaxBet=true;

		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		return isSetToMaxBet;
	}
/**
 * method is to check the availability if an element 
 * @param string
 * @return
 * @author rk61073
 */
public boolean checkAvilabilityofElement(String element) 
{
	boolean isComponentAvilable = true;
	Wait = new WebDriverWait(webdriver, 10);
	try 
	{
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get(element))));	
		WebElement ele = webdriver.findElement(By.xpath(XpathMap.get(element)));
		ele.isDisplayed();
		if(ele != null)
		{
			isComponentAvilable = true;
		}
	}
	catch (Exception e) 
	{
		// if component in the game not avilable in it while give an exception
		isComponentAvilable = false;
	}
	return isComponentAvilable;
}
/**
 * method is to check the availability if an element 
 * @param string
 * @return
 * @author rk61073
 */
public boolean checkAvilabilityOfElement(String element) 
{
	boolean isComponentAvilable = true;
	WebDriverWait wait = new WebDriverWait(webdriver, 1);
	try 
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get(element))));	
		WebElement ele = webdriver.findElement(By.xpath(XpathMap.get(element)));
		ele.isDisplayed();
		if(ele != null)
		{
			isComponentAvilable = true;
		}
	}
	catch (Exception e) 
	{
		// if component in the game not avilable in it while give an exception
		isComponentAvilable = false;
	}
	return isComponentAvilable;
}
/**
 * method is used to perform click action 
 * @param locator
 * @return
 * @author rk61073
 */
public boolean fun_Click(String locator) 
{
	Wait = new WebDriverWait(webdriver, 60);
	boolean present;
	try 
	{
	Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get(locator))));
	WebElement ele = webdriver.findElement(By.xpath(XpathMap.get(locator)));
	if (ele != null) 
	{
		ele.click();
	}
	else
	{
		System.out.println("Unable to Click");log.debug("Unable to Click");
	}
		present = true;
	}
	catch (Exception e) 
	{
		present = false;
		log.debug(e.getMessage());
	}
	return present;
}
/**
 * method is used to perform click action 
 * @param locator
 * @return
 * @author rk61073
 */
public boolean fun_ClickByID(String locator) 
{
	Wait = new WebDriverWait(webdriver, 60);
	boolean present;
	try 
	{
	Wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get(locator))));
	WebElement ele = webdriver.findElement(By.id(XpathMap.get(locator)));
	if (ele != null) 
	{
		ele.click();
		log.debug("clicked on element using id ");
	}
	else
	{
		System.out.println("Unable to Click");log.debug("Unable to Click");
	}
		present = true;
	}
	catch (Exception e) 
	{
		present = false;
		log.debug(e.getMessage());
	}
	return present;
}	

/**
 * method is used to navigate 
 * @param report
 * @param gameurl
 * @author rk61073
 */
public void checkpagenavigation(Desktop_HTML_Report report, String gameurl,String CurrencyName) 
{
	try 
	{
		String mainwindow = webdriver.getWindowHandle();
		Set<String> s1 = webdriver.getWindowHandles();
		if (s1.size() > 1) 
		{
		  Iterator<String> i1 = s1.iterator();
		  while (i1.hasNext()) {
		  String ChildWindow = i1.next();
		  if (mainwindow.equalsIgnoreCase(ChildWindow))
     {
		//report.detailsAppend("verify the Navigation screen shot", " Navigation page screen shot", "Navigation page screenshot ", "PASS");
		ChildWindow = i1.next();
		webdriver.switchTo().window(ChildWindow);
		String url = webdriver.getCurrentUrl();
		log.debug("Navigation URL is :: " + url);
		if (!url.equalsIgnoreCase(gameurl))
		{
		// pass condition for navigation	
		//report.detailsAppendFolder("verify the Navigation screen shot", " Navigation page screen shot","Navigation page screenshot ", "PASS",CurrencyName);
		log.debug("Page navigated succesfully");
		webdriver.close();
		} 
		else
		{
			//System.out.println("Now On game page");
			log.debug("Now On game page");
		}
	}
}
		webdriver.switchTo().window(mainwindow);
	} 
		else
	{
		String url = webdriver.getCurrentUrl();
	//System.out.println("Navigation URL is ::  " + url);
	log.debug("Navigation URL is ::  " + url);
	if (!url.equalsIgnoreCase(gameurl)) {
		// pass condition for navigation
		//report.detailsAppendFolder("verify the Navigation screen shot", " Navigation page screen shot","Navigation page screenshot ", "PASS",CurrencyName);
		log.debug("Page navigated succesfully");
		webdriver.navigate().to(gameurl);
		waitForSpinButton();
		//newFeature();
	}
	else 
	{
		webdriver.navigate().to(gameurl);
		waitForSpinButton();
		//System.out.println("Now On game page");
		log.debug("Now On game page");
       }
		}
	} catch (Exception e)
	{
		log.error("error in navigation of page");
	}
	}
/**
 * method is to click and navigate from Menu>home 
 * @param report
 * @param regExpr
 * @param currencyName
 * @return
 * @author rk61073
 */
public boolean homeFromMenu(Desktop_HTML_Report report, String currencyName)
{
boolean homeFromMenu= false;

String gameurl = webdriver.getCurrentUrl();
WebDriverWait Wait = new WebDriverWait(webdriver, 60);
try 
{
	
if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("HamburgerMenu")))) != null)
{
fun_Click("HamburgerMenu");
System.out.println("Clicked Hamburger Menu");log.debug("Clicked Hamburger Menu");
report.detailsAppendFolder("Hamburger","Hamburger menu clicked","Hamburger menu clicked","PASS",""+currencyName);
Thread.sleep(2000);


if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("HomeIconfromHamburgerMenu")))) != null)
{
fun_Click("HomeIconfromHamburgerMenu");Thread.sleep(6000);
System.out.println("Clicked Home button from Hamburger");log.debug("Clicked Home button from Hamburger");
report.detailsAppendFolder("Home-Icon","Home-Icon from Hamburger menu clicked","Home-Icon from Hamburger menu clicked","PASS",""+currencyName);
checkpagenavigation(report,gameurl,currencyName);
homeFromMenu = true;
}
else
{
System.out.println("Unable to click on Home");log.debug("Unable to click on Home");
report.detailsAppendFolder("Home-Icon","Home-Icon from Hamburger Menu clicked","Home-Icon from Hamburger Menu clicked","FAIL",""+currencyName);
homeFromMenu = false;
}
}
else
{
System.out.println("Unable to click on Hamburger Menu");log.debug("Unable to click on Hamburger Menu");
report.detailsAppendFolder("Hamburger","Hamburger menu clicked","Hamburger menu clicked","FAIL",""+currencyName);
homeFromMenu = false;
}}
catch (Exception e) 
{
	log.debug(e.getMessage());
}	
return homeFromMenu;

}
/**
 * method is to click and navigate from Menu>banking 
 * @param report
 * @param regExpr
 * @param currencyName
 * @return
 * @author rk61073
 */

public boolean bankingFromMenu(Desktop_HTML_Report report, String currencyName)
{
boolean bankingfrommenu= false;

String gameurl = webdriver.getCurrentUrl();
WebDriverWait Wait = new WebDriverWait(webdriver, 60);
try 
{
	
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("HamburgerMenu")))) != null)
	{
	fun_Click("HamburgerMenu");Thread.sleep(2000);
	System.out.println("Clicked on HamburgerMenu");log.debug("Clicked on HamburgerMenu");
	report.detailsAppendFolder("Hamburger","Hamburger menu clicked","Hamburger menu clicked","PASS",""+currencyName);
	bankingfrommenu = true;
	}
	else
	{
	System.out.println("Unable to click on Hamburger Menu");log.debug("Unable to click on Hamburger Menu");
	report.detailsAppendFolder("Hamburger","Hamburger menu clicked","Hamburger menu clicked","FAIL",""+currencyName);
	bankingfrommenu = false;
	}Thread.sleep(3000);
	
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("BankingIconfromHamburgerMenu")))) != null)
	{
	fun_Click("BankingIconfromHamburgerMenu");Thread.sleep(6000);
	System.out.println("Clicked on Banking from Hamburger Menu");log.debug("Clicked on Banking from Hamburger Menu");
	report.detailsAppendFolder("Banking","Banking from Hamburger menu clicked","Banking from Hamburger menu clicked","PASS",""+currencyName);
	checkpagenavigation(report,gameurl,currencyName);
	bankingfrommenu = true;
	}
	else
	{
	System.out.println("Unable to click on Banking ");log.debug("Unable to click on Banking ");
	report.detailsAppendFolder("Banking","Banking from Hamburger Menu clicked","Banking from Hamburger Menu clicked","FAIL",""+currencyName);
	bankingfrommenu = false;
	}Thread.sleep(2000);

}
catch (Exception e) 
{
	log.debug(e.getMessage());
}	
return bankingfrommenu;

}
/**
 * method is to click and navigate from Menu>settings
 * @param report
 * @param regExpr
 * @param currencyName
 * @return
 * @author rk61073
 */

public boolean settingsFromMenu(Desktop_HTML_Report report, String currencyName)
{
boolean isSettingsFromMenu= false;
WebDriverWait Wait = new WebDriverWait(webdriver, 60);
try 
{
if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("HamburgerMenu")))) != null)
{
fun_Click("HamburgerMenu");Thread.sleep(2000);
System.out.println("Clicked on Hamburger Menu");log.debug("Clicked on Hamburger Menu");
report.detailsAppendFolder("Hamburger","Hamburger menu clicked","Hamburger menu clicked","PASS",""+currencyName);
isSettingsFromMenu = true;
}
else
{
System.out.println("Unable to click on Hamburger Menu");log.debug("Unable to click on Hamburger Menu");
report.detailsAppendFolder("Hamburger","Hamburger menu clicked","Hamburger menu clicked","FAIL",""+currencyName);
isSettingsFromMenu = false;
}Thread.sleep(3000);

if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("SettingsIconfromHamburgerMenu")))) != null)
{
fun_Click("SettingsIconfromHamburgerMenu");Thread.sleep(4000);
System.out.println("Clicked on settings from Hamburger Menu");log.debug("Clicked on settings from Hamburger Menu");
report.detailsAppendFolder("Settings","Settings from menu clicked","Settings from menu clicked","PASS",""+currencyName);
isSettingsFromMenu = true;
}
else
{
System.out.println("Unable to click on Settings ");log.debug("Unable to click on Settings ");
report.detailsAppendFolder("Settings","Settings from menu clicked","Settings from menu clicked","FAIL",""+currencyName);
isSettingsFromMenu = false;
}Thread.sleep(2000);}
catch (Exception e) 
{
	log.debug(e.getMessage());
}	
return isSettingsFromMenu;
}
/**
 * method is to close the settings
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public boolean closeSettings(Desktop_HTML_Report report,String CurrencyName)
{
	boolean closetheSettings= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("CloseSettings")))) != null)
		{
		fun_Click("CloseSettings");log.debug("CloseSettings : PASS");
		//report.detailsAppendFolder("Close Settings","Closing settings from hamburger menu","Closing settings from hamburger menu","PASS",""+CurrencyName);
		closetheSettings = true;
		}
		else
		{
		System.out.println("CloseSettings : FAIL");log.debug("CloseSettings : FAIL");
		//report.detailsAppendFolder("Close Settings","Closing settings from hamburger menu","Closing settings from hamburger menu","FAIL",""+CurrencyName);
		closetheSettings = false;
		}	Thread.sleep(2000);		
}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
	return closetheSettings;
}
/**
 * method is to close the settings
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public boolean closeSettings(Desktop_HTML_Report report)
{
	boolean closetheSettings= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("CloseSettings")))) != null)
		{
		fun_Click("CloseSettings");
		System.out.println("Clicked on Close settings button");log.debug("Clicked on Close settings button");
		report.detailsAppend("Close Settings","Closing settings from hamburger menu","Closing settings from hamburger menu","PASS");
		closetheSettings = true;
		}
		else
		{
		System.out.println("Unable to click on close Settings button ");log.debug("Unable to click on close Settings button ");
		report.detailsAppend("Close Settings","Closing settings from hamburger menu","Closing settings from hamburger menu","FAIL");
		closetheSettings = false;
		}	Thread.sleep(2000);		
}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
	return closetheSettings;
}
	/**
	 * validated the hambugermenu options 
	 * @author rk61073
	 */
	public void hamburgerMenu(Desktop_HTML_Report report,String currencyName)
	{
		String gameurl = webdriver.getCurrentUrl();
		WebDriverWait Wait = new WebDriverWait(webdriver, 60);
		try 
		{
			for(int i=1;i<=3;i++)
			{
			 Thread.sleep(3000);
			boolean hamburgermenu = fun_Click("HamburgerMenu");
		
			if(hamburgermenu)
			{
			System.out.println("Hamburgermenu : PASS");log.debug("Hamburgermenu : PASS");
			if(i==1) 
			{
			report.detailsAppendFolder("Verify Hamburger Menu","Click on Hamburger Menu","Hamburger Menu : " +hamburgermenu,"PASS",""+currencyName);
			}
			Thread.sleep(3000);
			
			if(i==1)
			{
				boolean homeIconFromMenu = fun_Click("HomeIconfromHamburgerMenu");
				if(homeIconFromMenu)
				{Thread.sleep(6000);
				System.out.println("HomeIconFromMenu : PASS");log.debug("HomeIconFromMenu : PASS");
				report.detailsAppendFolder("Verify Home Icon from Menu","Click on Home Icon from Menu","Home Icon from Menu : " +homeIconFromMenu,"PASS",""+currencyName);
				checkpagenavigation(report,gameurl,currencyName);
				}
				else
				{
				System.out.println("HomeIconFromMenu : FAIL");log.debug("HomeIconFromMenu : FAIL");
				report.detailsAppendFolder("Verify Home Icon from Menu","Click on Home Icon from Menu","Home Icon from Menu : " +homeIconFromMenu,"FAIL",""+currencyName);
				}
				
			}
			else if(i==2)
			{
				boolean bankingIconFromMenu = fun_Click("BankingIconfromHamburgerMenu");
				if(bankingIconFromMenu)
				{Thread.sleep(6000);
				System.out.println("BankingIconFromMenu : PASS");log.debug("BankingIconFromMenu : PASS");
				report.detailsAppendFolder("Verify Banking Icon from Menu","Click on Banking Icon from Menu","Banking Icon from Menu : " +bankingIconFromMenu,"PASS",""+currencyName);
				checkpagenavigation(report,gameurl,currencyName);	
				}
				else
				{
				System.out.println("BankingIconFromMenu : FAIL");log.debug("BankingIconFromMenu : FAIL");
				report.detailsAppendFolder("Verify Banking Icon from Menu","Click on Banking Icon from Menu","Banking Icon from Menu : " +bankingIconFromMenu,"FAIL",""+currencyName);
				}
				
			}
			else
			{
				boolean settingsIconFromMenu = fun_Click("SettingsIconfromHamburgerMenu");
				if(settingsIconFromMenu)
				{Thread.sleep(4000);
				System.out.println("SettingsIconFromMenu : PASS");log.debug("SettingsIconFromMenu : PASS");
				report.detailsAppendFolder("Verify Settings Icon from Menu","Click on Settings Icon from Menu","Settings Icon from Menu : " +settingsIconFromMenu,"PASS",""+currencyName);
				}
				else
				{
				System.out.println("SettingsIconFromMenu : FAIL");log.debug("SettingsIconFromMenu : FAIL");
				report.detailsAppendFolder("Verify Settings Icon from Menu","Click on Settings Icon from Menu","Settings Icon from Menu : " +settingsIconFromMenu,"FAIL",""+currencyName);
				}
			}}
			else
			{
			System.out.println("Hamburger Menu : FAIL ");log.debug("Hamburger Menu : FAIL ");
			report.detailsAppendFolder("Verify Hamburger Menu","Click on Hamburger Menu","Hamburger Menu : " +hamburgermenu,"FAIL",""+currencyName);
			}Thread.sleep(2000);
			}closeSettings(report,currencyName);}
		catch (Exception e) 
		{
			log.debug(e.getMessage());
		}}
	/**
	 * validated the hambugermenu options 
	 * @author rk61073
	 */
	public void hamburgerMenuWithResize(Desktop_HTML_Report report,String currencyName)
	{
		String gameurl = webdriver.getCurrentUrl();
		try 
		{
			for(int i=1;i<=3;i++)
			{
			 Thread.sleep(3000);
			boolean hamburgermenu = fun_Click("HamburgerMenu");
			if(hamburgermenu)
			{
			log.debug("Clicked on hamburger menu button : PASS");
			if(i==1) 
			{
				report.detailsAppendFolder("Verify hamburger menu ", "Clicked on hamburger menu button ", "Clicked on hamburger menu button", "PASS", currencyName);
				resizeBrowser(600, 800);
				Thread.sleep(2000);
				report.detailsAppendFolder("Verify hamburger menu ", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", currencyName);
				resizeBrowser(400,600);
				Thread.sleep(2000);
				report.detailsAppendFolder("Verify hamburger menu ", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", currencyName);
				webdriver.manage().window().maximize();Thread.sleep(2000);
			}
			
			if(i==1)
			{
				boolean homeIconFromMenu = fun_Click("HomeIconfromHamburgerMenu");
				if(homeIconFromMenu)
				{Thread.sleep(6000);log.debug("Clicked on home button from menu : PASS");
				report.detailsAppendFolder("Verify home from hamburger menu","Clicked on home button from menu ","Clicked on home button from menu ","PASS",""+currencyName);
				resizeBrowser(600, 800);
				Thread.sleep(2000);
				report.detailsAppendFolder("Verify home from hamburger menu", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", currencyName);
				resizeBrowser(400,600);
				Thread.sleep(2000);
				report.detailsAppendFolder("Verify home from hamburger menu", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", currencyName);
				webdriver.manage().window().maximize();Thread.sleep(2000);
				checkpagenavigation(report,gameurl,currencyName);
				}
				else
				{
				System.out.println("Clicked on home button from menu : FAIL");log.debug("Clicked on home button from menu : FAIL");
				report.detailsAppendFolder("Verify home form hamburger menu","Clicked on home button from menu ","Clicked on home button from menu ","FAIL",""+currencyName);
				}
				
			}
			else if(i==2)
			{
				boolean bankingIconFromMenu = fun_Click("BankingIconfromHamburgerMenu");
				if(bankingIconFromMenu)
				{Thread.sleep(6000);log.debug("Clicked on banking button from menu : PASS");
				report.detailsAppendFolder("Verify banking from hamburger menu","Clicked on banking button from menu ","Clicked on banking button from menu ","PASS",""+currencyName);
				resizeBrowser(600, 800);
				Thread.sleep(2000);
				report.detailsAppendFolder("Verify banking from hamburger menu", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", currencyName);
				resizeBrowser(400,600);
				Thread.sleep(2000);
				report.detailsAppendFolder("Verify banking from hamburger menu", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", currencyName);
				webdriver.manage().window().maximize();Thread.sleep(2000);
				checkpagenavigation(report,gameurl,currencyName);	
				}
				else
				{
				System.out.println("Clicked on banking icon from menu : FAIL");log.debug("Clicked on banking icon from menu : FAIL");
				report.detailsAppendFolder("Verify banking form hamburger menu","Clicked on banking icon from menu ","Clicked on banking icon from menu ","FAIL",""+currencyName);
				}
				
			}
			else
			{
				boolean settingsIconFromMenu = fun_Click("SettingsIconfromHamburgerMenu");
				if(settingsIconFromMenu)
				{Thread.sleep(4000);log.debug("Clicked on settings button from menu : PASS");
				report.detailsAppendFolder("Verify settings from hamburger menu","Clicked on settings button from menu","Clicked on settings button from menu","PASS",""+currencyName);
				resizeBrowser(600, 800);
				Thread.sleep(2000);
				report.detailsAppendFolder("Verify settings from hamburger menu", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", currencyName);
				resizeBrowser(400,600);
				Thread.sleep(2000);
				report.detailsAppendFolder("Verify settings from hamburger menu", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", currencyName);
				webdriver.manage().window().maximize();Thread.sleep(3000);
				boolean close  =closeSettings(report,currencyName);
				if(close)
				{
					log.debug("Clicked on close settings button  : PASS");
				}
				else
				{
					System.out.println("Clicked on close settings button : FAIL");log.debug("Clicked on close settings button : FAIL");
					report.detailsAppendFolder("Verify settings close form hamburger menu","Clicked on settings close button from menu","Clicked on settings close button from menu","FAIL",""+currencyName);
				}
				}//closing if loop of close
				else
				{
				System.out.println("Clicked on settings button from menu : FAIL");log.debug("Clicked on settings button from menu  : FAIL");
				report.detailsAppendFolder("Verify settings form hamburger menu","Clicked on settings button from menu","Clicked on settings button from menu","FAIL",""+currencyName);
				}
			}//closing else block
			}//closing if blosck of hambirger menu 
			else
			{
			System.out.println("Clicked on hamburger menu button: PASS : FAIL ");log.debug("Clicked on hamburger menu button: PASS : FAIL ");
			report.detailsAppendFolder("Verify hamburger menu ", "Clicked on hamburger menu button: PASS", "Clicked on hamburger menu button: PASS", "FAIL", currencyName);
			}Thread.sleep(2000);
			}//closing for loop 
			}//closing try block 
		catch (Exception e) 
		{
			log.debug(e.getMessage());
		}}
	/**
	 * validated the hambugermenu options 
	 * @author rk61073
	 */
	public void hamburgerMenuOptions(Desktop_HTML_Report report,String currencyName)
	{
		try
		{
		  settingsFromHamburgerMenu(report,currencyName);
		  bankingFromHamburgerMenu(report,currencyName);
		  homeFromHamburgerMenu(report,currencyName);
	    }
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}}
/**
 * 
 * @param report
 * @param CurrencyName
 * @return
 */
public boolean homeFromHamburgerMenu(Desktop_HTML_Report report,String CurrencyName)
{
	boolean home = false;
	String gameurl = webdriver.getCurrentUrl();
	try 
	{
boolean hamburgermenu = fun_Click("HamburgerMenu");
boolean homeIconFromMenu = fun_Click("HomeIconfromHamburgerMenu");

if(hamburgermenu)
{
	System.out.println("Hamburgermenu : PASS");log.debug("Hamburgermenu : PASS");
	report.detailsAppendFolder("Verify Hamburger Menu","Click on Hamburger Menu","Hamburger Menu : " +hamburgermenu,"PASS",""+CurrencyName);
	Thread.sleep(3000);
	
	if(homeIconFromMenu)
	{Thread.sleep(6000);
	System.out.println("HomeIconFromMenu : PASS");log.debug("HomeIconFromMenu : PASS");
	report.detailsAppendFolder("Verify Home Icon from Menu","Click on Home Icon from Menu","Home Icon : " +homeIconFromMenu,"PASS",""+CurrencyName);
	checkpagenavigation(report,gameurl,CurrencyName);
	}
	else
	{
	System.out.println("HomeIconFromMenu : FAIL");log.debug("HomeIconFromMenu : FAIL");
	report.detailsAppendFolder("Verify Home Icon from Menu","Click on Home Icon from Menu","Home Icon : " +homeIconFromMenu,"FAIL",""+CurrencyName);
	home = false;
	}
}
else
{
report.detailsAppendFolder("Verify Hamburger Menu","Click on Hamburger Menu","Hamburger Menu : " +hamburgermenu,"PASS",""+CurrencyName);
}	
	}
catch (Exception e) 
{
	log.debug(e.getMessage());
	
}
	return home;
}
/**
 * 
 * @param report
 * @param CurrencyName
 * @return
 */
public boolean bankingFromHamburgerMenu(Desktop_HTML_Report report,String CurrencyName)
{
	boolean banking = false;
	String gameurl = webdriver.getCurrentUrl();
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
boolean hamburgermenu = fun_Click("HamburgerMenu");
boolean bankingIconFromMenu = fun_Click("BankingIconfromHamburgerMenu");

if(hamburgermenu)
{
	System.out.println("Hamburgermenu : PASS");log.debug("Hamburgermenu : PASS");
	//report.detailsAppendFolder("Verify Hamburger Menu","Click on Hamburger Menu","Hamburger Menu : " +hamburgermenu,"PASS",""+CurrencyName);
	Thread.sleep(3000);
		
	if(bankingIconFromMenu)
	{Thread.sleep(6000);
	System.out.println("BankingIconFromMenu : PASS");log.debug("BankingIconFromMenu : PASS");
	report.detailsAppendFolder("Verify Banking Icon from Menu","Click on Banking Icon from Menu","Banking Icon from Menu : " +bankingIconFromMenu,"PASS",""+CurrencyName);
	checkpagenavigation(report,gameurl,CurrencyName);	banking = true;
	}
	else
	{
	System.out.println("BankingIconFromMenu : FAIL");log.debug("BankingIconFromMenu : FAIL");
	report.detailsAppendFolder("Verify Banking Icon from Menu","Click on Banking Icon from Menu","Banking Icon from Menu : " +bankingIconFromMenu,"FAIL",""+CurrencyName);
	banking = false;}
}
else
{
report.detailsAppendFolder("Verify Hamburger Menu","Click on Hamburger Menu","Hamburger Menu : " +hamburgermenu,"PASS",""+CurrencyName);
}	
	}
catch (Exception e) 
{
	log.debug(e.getMessage());
	
}
	return banking;}
/**
 * 
 * @param report
 * @param CurrencyName
 * @return
 */
public boolean settingsFromHamburgerMenu(Desktop_HTML_Report report,String CurrencyName)
{
	boolean setting = false;
	String gameurl = webdriver.getCurrentUrl();
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
boolean hamburgermenu = fun_Click("HamburgerMenu");
boolean settingsIconFromMenu = fun_Click("SettingsIconfromHamburgerMenu");
boolean soundfromsettings = fun_Click("SettingsIconfromHamburgerMenu");
boolean doublefromsettings = fun_Click("SettingsIconfromHamburgerMenu");
boolean quickdealfromsettings = fun_Click("SettingsIconfromHamburgerMenu");

String settings = "Sound Button from settings : "+soundfromsettings+" Double Button From Settings : " +doublefromsettings+"Quick Deal button from settings :  "+quickdealfromsettings;

if(hamburgermenu)
{
	System.out.println("Hamburgermenu : PASS");log.debug("Hamburgermenu : PASS");
	//report.detailsAppendFolder("Verify Hamburger Menu","Click on Hamburger Menu","Hamburger Menu : " +hamburgermenu,"PASS",""+CurrencyName);
	Thread.sleep(3000);
	
	if(settingsIconFromMenu)
	{Thread.sleep(4000);
	System.out.println("SettingsIconFromMenu : PASS");log.debug("SettingsIconFromMenu : PASS");
	report.detailsAppendFolder("Verify Settings Icon from Menu","Click on Settings Icon from Menu","" +settings,"PASS",""+CurrencyName);
	closeSettings(report,CurrencyName);setting = true;}
	else
	{
	System.out.println("SettingsIconFromMenu : FAIL");log.debug("SettingsIconFromMenu : FAIL");
	report.detailsAppendFolder("Verify Settings Icon from Menu","Click on Settings Icon from Menu","" +settings,"FAIL",""+CurrencyName);
	setting = false;}
}
else
{
report.detailsAppendFolder("Verify Hamburger Menu","Click on Hamburger Menu","Hamburger Menu : " +hamburgermenu,"PASS",""+CurrencyName);
}	
	}
catch (Exception e) 
{
	log.debug(e.getMessage());
	
}
	return setting;}
	/**
	 * method is for paytable 
	 * @author rk61073
	 */
	public boolean paytable(Desktop_HTML_Report report,String CurrencyName)
	{
		boolean isPaytableVisible= false;
		WebDriverWait Wait = new WebDriverWait(webdriver, 60);
		try 
		{
		boolean paytable =	fun_Click("PaytableIcon");
			if(paytable)
			{
			Thread.sleep(3000);
			log.debug("Clicked on PaytableIcon");
			//report.detailsAppendFolder("Verify Paytable payouts ","Paytable Opened","Paytable Opened : "+paytable,"PASS",""+CurrencyName);
			isPaytableVisible = true;
			}
			else
			{
			System.out.println("Unable to click on PaytableIcon");log.debug("Unable to click on PaytableIcon");
			report.detailsAppendFolder("Verify Paytable payouts ","Paytable Opened","Paytable Opened : "+paytable,"FAIL",""+CurrencyName);
			isPaytableVisible = false;
			}
		}
		catch (Exception e) 
		{
			log.debug(e.getMessage());
		}	
		
		return isPaytableVisible;		
	}	

/**
 * 
 * @param locator
 * @return
 * @author rk61073
 */
public String fun_GetText(String locator) 
{
	String text = null;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
	 if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get(locator)))) != null )
	 {
		WebElement ele = webdriver.findElement(By.xpath(XpathMap.get(locator)));
		if(ele!=null)
		{
		text = ele.getText();
		System.out.println(text);log.debug(text);
		}
		else
		{
		 System.out.println("Text is null");log.debug("Text is null");
		}
	 }	
	 else
	 {
		 System.out.println("Unable to get the text");log.debug("Unable to get the text");
	 }} 
	catch (NoSuchElementException e)
	{
		log.debug(e.getMessage());
	}
	return text;}

/**
 * method is for clock 
 * @author rk61073
 */
public String s(Desktop_HTML_Report lvcReport, String currencyName) 
{
	String clockText = null;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("ClockfromTopBar")))) != null )
	{
	clockText = fun_GetText("ClockfromTopBar");
	}
	else
	{
		System.out.println("Clock is not visible");log.debug("Clock is not visible");
	}
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}
	return clockText;
}
/**
 * method is for click & get text 
 * @param report
 * @param menuclick
 * @param locator
 * @return
 * @author rk61073
 */
public String clickAndGetText(Desktop_HTML_Report report, String menuclick ,String locator) 
{
	String getText = null;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get(menuclick)))) != null); 
	{
	webdriver.findElement(By.xpath(XpathMap.get(menuclick))).click();
	boolean isloactorVisible = webdriver.findElement(By.xpath(XpathMap.get(locator))).isDisplayed();
	if (isloactorVisible) 
	{
		getText = webdriver.findElement(By.xpath(XpathMap.get(locator))).getText(); 
		log.debug(getText);		
		System.out.println(getText);	
    }
	else 
	{
		System.out.println("Unable to locate an element");log.debug("Unable to locate an element");
	}}}
	catch (Exception e)
	{
		log.error(e.getMessage(), e);
	}
	return getText ;
}
/**method is for click and navigate 
 * @param report
 * @param locator
 * @return
 * @author rk61073
 */
public boolean clickAndNavigate(Desktop_HTML_Report report , String locator,String currencyName) 
{
	boolean ele =false;
	String gameurl = webdriver.getCurrentUrl();
try
{		
	boolean isElementVisisble = webdriver.findElement(By.xpath(XpathMap.get(locator))).isDisplayed();	
    if(isElementVisisble)
	{
	webdriver.findElement(By.xpath(XpathMap.get(locator))).click();
	Thread.sleep(3000);
	checkpagenavigation(report, gameurl,currencyName); 
	Thread.sleep(1000);
	ele = true;
	}
    else
    {
    	System.out.println("Unable to click & navigation");log.debug("Unable to click & navigation");
    	report.detailsAppendFolder("Click and Navigation","Click and Navigation","Click and Navigation","FAIL",""+currencyName);
    	ele = false;
    }
}
catch(Exception e)
{
	log.error(e.getMessage(), e);
}
return ele;
}
/**
 * method is to validate help from top bar & navigates to help
 * @param report
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean helpFromTopBarMenu(Desktop_HTML_Report report,String currencyName) throws InterruptedException
{
String menuclick = "MenufromTopBar";
String locator = "HelpfromTopBarMenu";
String Text = clickAndGetText(report,menuclick,locator);
boolean text= false;
try
{
if (Text.equals("Help")) 
{
System.out.println("Help Text : PASS");log.debug("Help Text : PASS");
report.detailsAppendFolder("Verify Help Text ","Help Text from top is same","Help Text from top is : "+ Text,"PASS",""+currencyName);
text = true;
} 
else
{
System.out.println("Help Text : FAIL");log.debug("Help Text : FAIL");
report.detailsAppendFolder("Verify Help Text ","Help Text from top is same","Help Text from top is : "+ Text,"FAIL",""+currencyName);
text = false;
}
clickAndNavigate(report,locator,currencyName);
Thread.sleep(2000);
}
catch (Exception e) 
{
log.error(e.getMessage(), e);
}
return text ;
}
/**
 * Verifies the Currency Format -  using String method
 * @param isoCode
 * @param regExp
 * @param method
 * @return
 * @author rk61073
 */
public boolean verifyRegularExpression(Desktop_HTML_Report report, String regExp, String method)
{
	String Text = method;
	boolean regexp= false;
	try
	{
		Thread.sleep(2000);
	if (Text.matches(regExp)) 
	{
		log.debug("Compared with Reg Expression.Currency is same");
		regexp = true; 
	}
	else
	{
		log.debug("Compared with Reg Expression.Currency is different");
	}
	Thread.sleep(2000);
	}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}	
	return regexp ;		
}
/**
 * Verifies the Currency Format using arrays
 * @param report
 * @param regExp
 * @param method
 * @return
 * @author rk61073
 */
public boolean verifyRegularExpressionUsingArrays(Desktop_HTML_Report report, String regExp, String[] method)
{
	String[] Text = method;
	boolean regexp= false;
	try
	{
		Thread.sleep(2000);
	for(int i=0;i<Text.length;i++)
	{
	if (Text[i].matches(regExp))
	{
		log.debug("Compared with Reg Expression .Currency is same");
		regexp = true; 
	}
	else
	{
		log.debug("Compared with Reg Expression .Currency is different");
		regexp = false; 
	}Thread.sleep(2000);}}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}	
	return regexp ;		
}
/**
 * method is to enable & disable the quick deal button 
 * @param report
 * @param method
 * @return
 *  @author rk61073
 */
public boolean isQuickDealButtonAvailable(Desktop_HTML_Report report, String currencyName)
{
	boolean isQuickDealButtonAvailable= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{	
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("QuickDealButton")))) != null)
		{
		for(int i=1;i<=2;i++)
	    {
		boolean quickdeal = fun_Click("QuickDealButton");//to enable the button 	
		if(i==1 && quickdeal )
		{
		System.out.println("Quickdeal button is enabled");log.debug("Quickdeal button is enabled");
		report.detailsAppendFolder("Verify Quick Deal by enabling","Quickdeal button is enabled","Quickdeal button is enabled : "+quickdeal,"PASS",""+currencyName);
		Thread.sleep(3000);isQuickDealButtonAvailable = true;
		}
		else
		{ 
		System.out.println("Quickdeal button is disabled");log.debug("Quickdeal button is disabled");
		report.detailsAppendFolder("Verify Quick Deal by disabled","Quickdeal button is disabled","Quickdeal button is disabled : "+quickdeal,"PASS",""+currencyName);
		Thread.sleep(3000);isQuickDealButtonAvailable = true;
		}
		}}//closing implicit wait for quick deal button
		else
		{
		System.out.println("check Quickdeal");log.debug("check Quickdeal");
		report.detailsAppendFolder("Verify Quick Deal by enabling","Quickdeal button is enabled","Quickdeal button is enabled : false","FALE",""+currencyName);
		isQuickDealButtonAvailable = false;	
		}}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
	
	return isQuickDealButtonAvailable;		
}
/**method is to close the overlay
 * @author rk61073
 */
public void closetheOverlay(int x ,int y) 
{
  try 
{
	Actions act = new Actions(webdriver);
	act.moveByOffset(x, y).click().build().perform();
	act.moveByOffset(-200, -200).build().perform();
}
  catch (Exception e)
  {
		e.getMessage();
   }
}


public boolean slideTheCoinsToMin(Desktop_HTML_Report report, String currencyName)
{
	boolean minbet= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("coinslider")))) != null)
		{
			WebElement coinsizeslider = webdriver.findElement(By.xpath(XpathMap.get("coinslider")));
			Actions action = new Actions(webdriver);
			action.dragAndDropBy(coinsizeslider, -550,0).build().perform();
		    System.out.println("Coins Slided to Min Bet ");log.debug("Coins Slided to Min Bet ");
		    minbet = true;
		 }
		else
		{
			System.out.println("Unable to slide the coin slider to min ");log.debug("Unable to slide the coin slider to min");
			 minbet = true;
		}Thread.sleep(2000);
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
	
	return minbet ;		
}

public boolean slideTheCoinsizeToMin(Desktop_HTML_Report report, String currencyName)
{
	boolean minbet= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("coinsizeslider")))) != null)
		{
			WebElement coinsizeslider = webdriver.findElement(By.xpath(XpathMap.get("coinsizeslider")));
			Actions action = new Actions(webdriver);
			action.dragAndDropBy(coinsizeslider, -550,0).build().perform();
		    System.out.println("Coinsize slider slided to min");log.debug("Coinsize slider slided  to min");
		    minbet = true;
		}
		else
		{
			System.out.println("Unable to slide the coin size slider  to min");log.debug("Unable to slide the coin size slider  to min");
			minbet = false;
		}
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
	
	return minbet ;		
}
/**
 * method is to slide to  MinBet
 * @param report
 * @param currencyName
 * @return
 * @author rk61073
 */
public boolean setMinBet(Desktop_HTML_Report report, String currencyName)
{
	boolean slideToMinBet= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
   
		if(fun_Click("BetButton"))
		{
		Thread.sleep(2000);
		log.debug("Bet Button Opened");
		boolean coins = slideTheCoinsToMin(report,currencyName);
		boolean coinsize =slideTheCoinsizeToMin(report,currencyName);
		
		String maxbet = "CoinSize slided to min : " + coinsize  + "  Coins slided to min : " + coins  ;
		
		if(coinsize && coins)
		{
			report.detailsAppendFolder("Verify Minimum Bet ","Slide the coins and coin size to minimum bet ",""+maxbet,"PASS",""+currencyName);
			slideToMinBet = true;
		}
		else 
		{
			report.detailsAppendFolder("Verify Minimum Bet ","Slide the coins and coin size to minimum bet ",""+maxbet,"FAIL",""+currencyName);
			slideToMinBet = false;
		}closeOverlay();Thread.sleep(2000);
			
		}
		else
		{
			log.debug("Unbale to open Bet Button ");slideToMinBet = false;
			report.detailsAppendFolder("Click  Bet button ","unable to click on bet button"," bet button : fail","FAIL",""+currencyName);
		}
		}//closing try block
		catch (Exception e) 
		{
			log.debug(e.getMessage());
		}		
		return slideToMinBet ;		
	}


/**
 * method is to slide to  MinBet
 * @param report
 * @param currencyName
 * @return
 * @author rk61073
 */
public boolean setMinBetAndValidate(Desktop_HTML_Report report, String currencyName)
{
	boolean slideToMinBet= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
   
		if(fun_Click("BetButton"))
		{
		Thread.sleep(2000);
		log.debug("Bet Button Opened");
		boolean coins = slideTheCoinsToMin(report,currencyName);
		boolean coinsize =slideTheCoinsizeToMin(report,currencyName);
		
		String maxbet = "CoinSize slided to min : " + coinsize  + "  Coins slided to min : " + coins  ;
		
		if(coinsize && coins)
		{
			report.detailsAppendFolder("Verify Minimum Bet ","Slide the coins and coin size to minimum bet ",""+maxbet,"PASS",""+currencyName);
			slideToMinBet = true;
		}
		else 
		{
			report.detailsAppendFolder("Verify Minimum Bet ","Slide the coins and coin size to minimum bet ",""+maxbet,"FAIL",""+currencyName);
			slideToMinBet = false;
		}closeOverlay();Thread.sleep(2000);
		
		boolean totalbet = totalBetInBaseAndInBetPanel();
		boolean coinsText = coinsUpdationInBaseAndInBetPanel();
		boolean coinSizeText = coinSizeUpdationInBaseAndInBetPanel();
		
        String maxbetText = "TotalBet text from bet panel and bet amount from base is same : "+totalbet + " Coins text from base and bet panel are same :  "+coinsText  +"  Coinsize text from base and bet panel are same :  "+coinSizeText ;
		
		if(totalbet && coinsText && coinSizeText)
		{
			report.detailsAppendFolder("Verify Minimum Bet Coins,Coinsize,Totalbet  ","verify total bet ,coins ,coinsize text in base and in bet panel",""+maxbetText,"PASS",""+currencyName);
			slideToMinBet = true;
		}
		else 
		{
			report.detailsAppendFolder("Verify Minimum Bet Coins,Coinsize,Totalbet  ","verify total bet ,coins ,coinsize text in base and in bet panel",""+maxbetText,"FAIL",""+currencyName);
			slideToMinBet = false;
		}
		
		}
		else
		{
			log.debug("Unbale to open Bet Button ");slideToMinBet = false;
		}
		}//closing try block
		catch (Exception e) 
		{
			log.debug(e.getMessage());
		}		
		return slideToMinBet ;		
	}

/**
 * method is to slide to  max bet
 * @param report
 * @param regExpr
 * @param currencyName
 * @return
 * @author rk61073
 */
public boolean slideTheCoinSliderToMax()
{
	boolean slideToMaxBet= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
		{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("coinslider")))) != null)
		{
		  tapOnCoordinates("coinslider",946,297);
		  log.debug("Coins Slided");
		  slideToMaxBet = true;
		}
		else
		{
			log.debug("Unable to slide the coin slider");
			slideToMaxBet = false;
		}	
		}
		catch (Exception e) 
		{
			log.debug(e.getMessage());
		}	
	
	return slideToMaxBet;
}
/**
 * method is to slide to  max bet
 * @param report
 * @param regExpr
 * @param currencyName
 * @return
 * @author rk61073
 */
public boolean slideTheCoinsToMax()
{
	boolean slideToMaxBet= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
		{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("coinsizeslider")))) != null)
		{
			WebElement coinsizeslider = webdriver.findElement(By.xpath(XpathMap.get("coinsizeslider")));
			Actions action = new Actions(webdriver);
			action.dragAndDropBy(coinsizeslider,400,0).build().perform();//this is for, from default to max bet 
			log.debug("CoinSize Slided");
		    slideToMaxBet = true;
		}
		else
		{
			log.debug("Unable to slide the coin size slider");
			slideToMaxBet = false;
		}	
		}
		catch (Exception e) 
		{
			log.debug(e.getMessage());
		}	
	
	return slideToMaxBet;
}
/**
 * method is to slide to  max bet
 * @param report
 * @param regExpr
 * @param currencyName
 * @return
 * @author rk61073
 */
public boolean setMaxBetAndValidate(Desktop_HTML_Report report, String currencyName)
{
	boolean slideToMaxBet= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
     {
		if(fun_Click("BetButton"))
		{
		Thread.sleep(2000);
		log.debug("Bet Button Opened");
		boolean coinsize = slideTheCoinSliderToMax();
		boolean coins =slideTheCoinsToMax();
		
		String maxbet = "CoinSize slided to max : " + coinsize  + "  Coins slided to max : " + coins ;
		if(coinsize && coins )
		{
			report.detailsAppendFolder("Verify Maximum Bet ","Slide the coins and coin size to maximum bet ",""+maxbet,"PASS",""+currencyName);
			slideToMaxBet = true;
		}
		else 
		{
			report.detailsAppendFolder("Verify Maximum Bet ","Slide the coins and coin size to maximum bet ",""+maxbet,"FAIL",""+currencyName);
			slideToMaxBet = false;
		}closeOverlay();Thread.sleep(2000);
		
		boolean totalbet = totalBetInBaseAndInBetPanel();
		boolean coinsText = coinsUpdationInBaseAndInBetPanel();
		boolean coinSizeText = coinSizeUpdationInBaseAndInBetPanel();
        String maxbetText = " TotalBet text from bet panel and bet amount from base is same : "+totalbet + " Coins text from base and bet panel are same :  "+coinsText  +"  Coinsize text from base and bet panel are same :  "+coinSizeText ;
		
		if(totalbet && coinsText && coinSizeText )
		{
			report.detailsAppendFolder("Verify Maximum Bet Coins,Coinsize,Totalbet  ","verify total bet ,coins ,coinsize text in base and in bet panel",""+maxbetText,"PASS",""+currencyName);
			slideToMaxBet = true;
		}
		else 
		{
			report.detailsAppendFolder("Verify maximum bet ","verify total bet ,coins ,coinsize text in base and in bet panel",""+maxbetText,"FAIL",""+currencyName);
			slideToMaxBet = false;
		}
		}
		else
		{
			log.debug("Unbale to open Bet Button ");slideToMaxBet = false;
		}	
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
	
	return slideToMaxBet;
}
/**
 * method is to slide to  max bet
 * @param report
 * @param regExpr
 * @param currencyName
 * @return
 * @author rk61073
 */
public boolean setMaxBet(Desktop_HTML_Report report, String currencyName)
{
	boolean slideToMaxBet= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
     {
		if(fun_Click("BetButton"))
		{
		Thread.sleep(2000);
		log.debug("Bet Button Opened");
		boolean coinsize = slideTheCoinSliderToMax();
		boolean coins =slideTheCoinsToMax();
		
		String maxbet = "CoinSize slided to max : " + coinsize  + "  Coins slided to max : " + coins ;
		if(coinsize && coins )
		{
			report.detailsAppendFolder("Verify Maximum Bet ","Slide the coins and coin size to maximum bet ",""+maxbet,"PASS",""+currencyName);
			slideToMaxBet = true;
		}
		else 
		{
			report.detailsAppendFolder("Verify Maximum Bet ","Slide the coins and coin size to maximum bet ",""+maxbet,"FAIL",""+currencyName);
			slideToMaxBet = false;
		}closeOverlay();Thread.sleep(2000);		
		}
		else
		{
			log.debug("Unbale to open Bet Button ");
			report.detailsAppendFolder("Click  Bet button ","unable to click on bet button"," bet button : fail","FAIL",""+currencyName);slideToMaxBet = false;
			
		}	
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}	
	
	return slideToMaxBet;
}
/**
 * method is used to click  the x ele& y co-ordinates using actions class
 * @param x
 * @param y
 * @author rk61073
 */
public void tapOnCoordinates(String locator, int x, int y)
{
	try
	{
	Actions act = new Actions(webdriver);
	WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get(locator))); 
    //act.moveByOffset(x,y).doubleClick();
	act.moveByOffset(x,y).click();
    act.build().perform(); 
    }
    catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
}
/**
 * method is used to click  the x ele& y co-ordinates using actions class
 * @param x
 * @param y
 * @author rk61073
 */
public boolean tapOnTheCoordinates(String locator, int x, int y)
{
	boolean tap = false;
	try
	{
	Actions act = new Actions(webdriver);
	WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get(locator))); 
    act.moveByOffset(x,y).doubleClick();
    act.build().perform(); 
    tap = true;
    }
    catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}return tap;
}
/**
 * method is used to click  the x ele& y co-ordinates using actions class
 * @param x
 * @param y
 * @author rk61073
 */
public String tapOnCoordinate(String locator, int x, int y)
{
	String getText = null;
	try
	{
	Actions act = new Actions(webdriver);
	WebElement ele1 = webdriver.findElement(By.xpath(XpathMap.get(locator))); 
    act.moveByOffset(x,y).click();
    act.build().perform(); 
    }
    catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
	return getText;
}
/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public String[] paytablePayoutsbyChangingTheCoinSizes(Desktop_HTML_Report report, String CurrencyName) 
{
	String paytablePayout[] = {"payout1","payout2","payout3","payout4","payout5","payout6","payout7","payout8","payout9","payout10","payout11"};
    String demo[] = new String[11];
	try
   {
	   for(int i=1;i<=5;i++)
		{
         fun_Click("PaytableCoinSize");
		for(int j=0;j<paytablePayout.length;j++)
		{	
			 demo[j]= fun_GetText(paytablePayout[j]);
			 report.detailsAppendFolder("Verify the Paytable payouts by chainging coinsize and coins  , Iteration No : "+i,"Paytable payouts by chainging coinsize and coins","Paytable payouts by chainging coinsize and coins","PASS",""+CurrencyName);
		}}
	  fun_Click("PaytableClose");
		}
    catch (Exception e) 
    {
   	 report.detailsAppendFolder("Verify the Paytable payouts by chainging coinsize and coins","Paytable payouts by chainging coinsize and coins","Paytable payouts by chainging coinsize and coins","FAIL",""+CurrencyName);
        e.printStackTrace();
    }
    return demo;
}

/**
 * method is to get the double the amount
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public void doubleTheAmountToDouble(Desktop_HTML_Report report,String regExpr, String currencyName)
{
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
try
{
	while(!checkAvilabilityofElement("GambleLimitReached"))
    {
	   if(checkAvilabilityofElement("DefaultDoubleCard"))
		{	
		 if(func_Click_BYID("doubleToCard"))
		 {
			 System.out.println("Card Selection in Double TO : PASS");log.debug("Card Selection in Double TO : PASS");
			 report.detailsAppendFolder("Card Selection in Double TO ", "Double TO", "Double TO", "PASS", currencyName);
			 if(checkAvilabilityofElement("GambleLimitReached")) 
			 {
				break; 
			 }
		 }//closing double to card click
		 else 
		 {
			 System.out.println("Card Selection in Double TO : FAIL");log.debug("Card Selection in Double TO : FAIL");
			 report.detailsAppendFolder("Card Selection in Double TO ", "Double TO", "Double TO", "FAIL", currencyName);
		 }}//closing validating default card in double to 
	   else 
	   {
		   System.out.println("Double TO : FAIL");log.debug("Double TO : FAIL");
		   report.detailsAppendFolder("Navigate to Double TO ", "Double TO", "Double TO", "FAIL", currencyName);
	     }
	   
		  if(fun_Click("DoubleButton"))
		  {
			System.out.println("Double To button in Double To : PASS"); log.debug("Double To button in Double To : PASS");
			report.detailsAppendFolder("Double To button in Double To", "Double TO", "Double TO", "PASS", currencyName);
		  }
		  else 
		  {
			  System.out.println("Double To button in Double To : FAIL"); 
			  report.detailsAppendFolder("Double To button in Double To ", "Double TO ", "Double TO ", "FAIL", currencyName);
		  }
			}//closing while loop 
	    
			if (fun_GetText("GambleLimitReached") != null) 
			{
				System.out.println("Gamble limit text : PASS");log.debug("Gamble limit text : PASS");
				report.detailsAppendFolder("Gamble limit text", "Gamble limit text", "Gamble limit text", "PASS", currencyName);
			} else {
				System.out.println("Gamble limit text : FAIL");
				report.detailsAppendFolder("Gamble limit text", "Gamble limit text", "Gamble limit text", "FAIL", currencyName);
			}
			
			if (fun_Click("BackToBaseGame"))
			{
				if(checkAvilabilityofElement("DealButton"))
				{
					System.out.println("Back to Base Game : PASS");log.debug("Back to Base Game : PASS");
					report.detailsAppendFolder("Back to Base Game", "Back to Base Game", "Back to Base Game", "PASS", currencyName);
				}//closing deal button availability 
			}//closing back to base game 
			else 
			{
				System.out.println("Back to Base Game : FAIL");log.debug("Back to Base Game : FAIL");
				report.detailsAppendFolder("Back to Base Game", "Back to Base Game", "Back to Base Game", "FAIL", currencyName);
			}}
catch (Exception e) 
{
	log.debug(e.getMessage());
}}
/**
 * method is to get the double the amount
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public void gambleLimitReached(Desktop_HTML_Report report, String currencyName)
{
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
try
{
	while(!checkAvilabilityofElement("GambleLimitReached"))
    {
	   if(checkAvilabilityofElement("DefaultDoubleCard"))
		{	
		 if(func_Click_BYID("doubleToCard"))
		 {
			 log.debug("Card Selection in Double  : PASS");
			 report.detailsAppendFolder("Card selection in Double ", "Double TO", "Double TO", "PASS", currencyName);
			 if(checkAvilabilityofElement("GambleLimitReached")) 
			 {
				break; 
			 }
		 }//closing double to card click
		 else 
		 {
			 System.out.println("Card Selection in Double TO : FAIL");log.debug("Card Selection in Double TO : FAIL");
			 report.detailsAppendFolder("Card Selection in Double TO ", "Double TO", "Double TO", "FAIL", currencyName);
		 }}//closing validating default card in double to 
	   else 
	   {
		   System.out.println("Double TO : FAIL");log.debug("Double TO : FAIL");
		   report.detailsAppendFolder("Navigate to Double TO ", "Double TO", "Double TO", "FAIL", currencyName);
	     }
	   
		  if(fun_Click("DoubleButton"))
		  {
			System.out.println("Double To button in Double To : PASS"); log.debug("Double To button in Double To : PASS");
			report.detailsAppendFolder("Double To button in Double To", "Double TO", "Double TO", "PASS", currencyName);
		  }
		  else 
		  {
			  System.out.println("Double To button in Double To : FAIL"); 
			  report.detailsAppendFolder("Double To button in Double To ", "Double TO ", "Double TO ", "FAIL", currencyName);
		  }
			}//closing while loop 
	    
			if (fun_GetText("GambleLimitReached") != null) 
			{
				System.out.println("Gamble limit text : PASS");log.debug("Gamble limit text : PASS");
				report.detailsAppendFolder("Gamble limit text", "Gamble limit text", "Gamble limit text", "PASS", currencyName);
			} else {
				System.out.println("Gamble limit text : FAIL");
				report.detailsAppendFolder("Gamble limit text", "Gamble limit text", "Gamble limit text", "FAIL", currencyName);
			}
			
			if (fun_Click("BackToBaseGame"))
			{
				if(checkAvilabilityofElement("DealButton"))
				{
					System.out.println("Back to Base Game : PASS");log.debug("Back to Base Game : PASS");
					report.detailsAppendFolder("Back to Base Game", "Back to Base Game", "Back to Base Game", "PASS", currencyName);
				}//closing deal button availability 
			}//closing back to base game 
			else 
			{
				System.out.println("Back to Base Game : FAIL");log.debug("Back to Base Game : FAIL");
				report.detailsAppendFolder("Back to Base Game", "Back to Base Game", "Back to Base Game", "FAIL", currencyName);
			}}
catch (Exception e) 
{
	log.debug(e.getMessage());
}}
/**
 * method is to get the double the amount
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public void gambleLimitReachedForLVC(Desktop_HTML_Report report,String regExpr, String currencyName)
{
try
{
	while(!checkAvilabilityofElement("GambleLimitReached"))
    {
	   if(checkAvilabilityofElement("DefaultDoubleCard"))
		{
		   log.debug("Moved to double screen : PASS");
		 if(func_Click_BYID("doubleToCard"))
		 {
			 log.debug("Card Selection in Double  : PASS");
			 if(checkAvilabilityofElement("GambleLimitReached")) 
			 {
				break; 
			 }
		 }//closing double to card click
		 else 
		 {
			 System.out.println("Card selection in Double : FAIL");log.debug("Card selection in Double : FAIL");
			 report.detailsAppendFolder("Card Selection in Double ", "Select the card in Double", "Select the card in Double", "FAIL", currencyName);
		 }}//closing validating default card in double to 
	   else 
	   {
		   System.out.println("Moved to double screen : FAIL");log.debug("Moved to double screen : FAIL");
		   report.detailsAppendFolder("Navigate to double  ", "Moved to double to screen", "Moved to double to screen", "FAIL", currencyName);
	   }
	   
	   checkAvilabilityofElement("TotalWinAmount"); fun_ClickByID("moduleContent");
	   /************click on double button**********/
		  if(fun_Click("DoubleButton"))
		  {
			 log.debug("Clicked on double button : PASS");
		  }
		  else 
		  {
			  log.debug("Clicked on double button : FAIL");System.out.println("Clicked on double button : FAIL"); 
			  report.detailsAppendFolder("Click double button in double ", "Click on double button ", "Clicked on double button", "FAIL", currencyName);
		  }
		}//closing while loop 
	    
	/******************* Validate total win amt in double  ***********************/
	boolean totalWinAmtInDouble =verifyRegularExpression(report,regExpr,fun_GetText("DoubleToTotalwinAmt"));
	
	 if(checkAvilabilityofElement("GambleLimitReached"))
	   {
		 log.debug("Gamble limit reached : PASS");
		   if(checkAvilabilityofElement("IsBackToBaseGameButtonEnabled"))
		   {
			   log.debug("Verify  back button in double  : PASS");
			   
			   /******************* Validate congratulations amt in double  ***********************/
				boolean congratulationsAmtInDouble = verifyRegularExpression(report,regExpr,fun_GetText("CongratulationsWinAmt"));
				
				String glr = "Total win amt when gamble limit reached : "+totalWinAmtInDouble+"  Congrants amt when gamble limit reached : "+congratulationsAmtInDouble;
				
				if(congratulationsAmtInDouble && totalWinAmtInDouble) 
				{
					log.debug("Validate congratulations and win amts when gamble limit reached : PASS");
					report.detailsAppendFolder("Validate congratulations and total win amts when gamble limit reached","Congratulations and total win amts when gamble limit reached","Congratulations and total win amts when gamble limit reached","PASS",""+currencyName);
				}
				else
				{
					log.debug("Verify congratulations amt when gamble limit reached : FAIL");System.out.println("Verify congratulations amt when gamble limit reached : FAIL");
					report.detailsAppendFolder("Validate congratulations and total win amts when gamble limit reached","Congratulations and total win amts when gamble limit reached",""+glr,"FAIL",""+currencyName);	
				}
			   
			    report.detailsAppendFolder("Verify gamble limit reached", "Gamble limit reached", "Gamble limit reached", "PASS", currencyName);
				resizeBrowser(600, 800);
				Thread.sleep(2000);
				report.detailsAppendFolder("Verify gamble limit reached", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", currencyName);
				resizeBrowser(400,600);
				Thread.sleep(2000);
				report.detailsAppendFolder("Verify gamble limit reached", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", currencyName);
				webdriver.manage().window().maximize();Thread.sleep(2000);
				
				report.detailsAppendNoScreenshot("Scenario : Back to base game from double screen ", "Click on back to base game btn in double and moved to base game", "", ""); 
			  
				if (fun_GetText("GambleLimitReached") != null && fun_Click("BackToBaseGame")) 
				{
					log.debug("Back to base game : PASS");
					if(checkAvilabilityofElement("DealButton"))
					{
					report.detailsAppendFolder("Back to base from double to screen", "Clicked on back to base game button in double to screen and moved to base game", "Clicked on back to base game button in double to screen and moved to base game", "PASS", currencyName);
					resizeBrowser(600, 800);
					Thread.sleep(2000);
					report.detailsAppendFolder("Back to base from double to screen", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", currencyName);
					resizeBrowser(400,600);
					Thread.sleep(2000);
					report.detailsAppendFolder("Back to base game from double to screen", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", currencyName);
					webdriver.manage().window().maximize();Thread.sleep(2000);
					
					}
				} 
			    else
			    {
					System.out.println("Back to base game : FAIL");System.out.println("Back to base game  : FAIL");
					report.detailsAppendFolder("Verify back button", "Click on back button in double", "Click on back button in double", "FAIL", currencyName);
				} 
		   }
		   else
		   {
				System.out.println("Verify  back button in double : FAIL");System.out.println("GVerify  back button in double : FAIL");
				report.detailsAppendFolder("Verify  back button in double", "Back button in double", "Back button in double", "FAIL", currencyName);
			} 
	   }
	 else
	   {
			System.out.println("Gamble limitis reached  : FAIL");System.out.println("Gamble limitis reached  : FAIL");
			report.detailsAppendFolder("Verify gamble limit reached", "Gamble limitis reached", "Gamble limitis reached", "FAIL", currencyName);
		} 
			
			}
catch (Exception e) 
{
	log.debug(e.getMessage());
}}
/**
 * method is to validate totalwin amaount 
 * @param report
 * @param currencyName
 * @return
 * @author rk61073
 */
public boolean totalWin(Desktop_HTML_Report report, String currencyName)
{
	boolean totalwin= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 120);
	try
	{
	WebElement	coinsize = Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("isPayatbleAvailable"))));
	if(coinsize != null)
	{
		if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
		{
		Thread.sleep(3000);	fun_Click("PaytableClose");
		}
		totalwin = fun_GetText("TotalWinAmount") != null;
		totalwin= true;
	}
	else
	{
		System.out.println("Unable to get the total win ");log.debug("Unable to get the total win ");
		report.detailsAppendFolder("Base Game","Total Win ","Total Win ","FAIL",""+currencyName);
		totalwin =false;
	}
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}
	return totalwin;
}
/**
 * method is to validate totalwin amaount 
 * @param report
 * @param regExpr
 * @param currencyName
 * @return
 * @author rk61073
 */
public boolean totalWin(Desktop_HTML_Report report,String regExpr, String currencyName)
{
	boolean totalwin= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 120);
	try
	{
	WebElement	coinsize = Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("isPayatbleAvailable"))));
	if(coinsize != null)
	{
		if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
		{
		report.detailsAppendFolder("Base Game","Paytable Payout with Total Win ","Paytable Payout  with Total Win ","PASS",""+currencyName);
		Thread.sleep(3000);	fun_Click("PaytableClose");
		}
		totalwin = verifyRegularExpression(report,regExpr,fun_GetText("TotalWinAmount"));
		report.detailsAppendFolder("Base Game","Total Win ","Total Win ","PASS",""+currencyName);
		totalwin= true;
	}
	else
	{
		System.out.println("Unable to get the total win ");log.debug("Unable to get the total win ");
		report.detailsAppendFolder("Base Game","Total Win ","Total Win ","FAIL",""+currencyName);
		totalwin =false;
	}
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}
	return totalwin;
}

/**
 * method is for clock 
 * @author rk61073
 */
public String clockfromTopBar(Desktop_HTML_Report report, String currencyName) 
{
	String clockText = null;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try
	{
	if (Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("ClockfromTopBar")))) != null) 
	{
	clockText = fun_GetText("ClockfromTopBar");
	if(clockText != null)
	{
	report.detailsAppendFolder("Verify Clock", "Clock is Visisble", "Time : "+clockText, "PASS",currencyName);	
	}
	else 
	{
	report.detailsAppendFolder("Verify Clock", "Clock is Visisble", "Time : "+clockText, "FAIL",currencyName);	
	}
	}
	else
	{
	report.detailsAppendFolder("Verify Clock", "Clock is Visisble", "Time : "+clockText, "FAIL",currencyName);	
	}}
	catch (Exception e) 
	{
		log.error("Not able to verify clock", e);
	}
	return clockText;	
}

public boolean doubleToGambleLimit(Desktop_HTML_Report lvcreport, String currencyName)
{
    
    WebDriverWait wait;
    try
    {
    
    wait = new WebDriverWait(webdriver, 60);
    
    //*********Clicking on Deal and Draw button
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("DealButton"))));
    System.out.println("Deal button visible");
    fun_Click("DealButton");
    System.out.println("Deal button click");
    
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("DrawButton"))));
    System.out.println("draw button visible");
    fun_Click("DrawButton");
    System.out.println("draw button click");
    
    Thread.sleep(2000);

    fun_Click("DoubleButton");

   
    do
    {
        //*********Clicking on Double button
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("SelectDoubleCard"))));
        fun_Click("SelectDoubleCard");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("DoubleButton"))));
        Thread.sleep(2000);        
        
        //***************Click on card
        wait.until(ExpectedConditions.attributeContains(webdriver.findElement(By.xpath(XpathMap.get("DoubleButton"))), "class", "vp card quick-transition"));
        fun_Click("DoubleButton");
        Thread.sleep(1000);
        
        
    }while(!(webdriver.findElement(By.xpath(XpathMap.get("gambleLimit"))).isDisplayed()));
    
                    
    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("backToGame"))));
    Thread.sleep(2000);
    fun_Click("backToGame");
    Thread.sleep(1000);
    }
    catch(Exception e)
    {
        log.error("Error in double to feature - "+e);
        try {
            lvcreport.detailsAppendFolder("Verify Gamble reached functionality", "Gamble Reached functionality", "Gamble Reached text is not displayed", "Fail", currencyName);
        } catch (Exception e1) {
            log.error("Error in double to feature - "+e);
        }



   }
    return false;
    
}
/**
 * method is used to navigate 
 * @param report
 * @param gameurl
 * @author rk61073
 */
public void checkpagenavigation(Desktop_HTML_Report report, String gameurl) 
{
	try 
	{
		String mainwindow = webdriver.getWindowHandle();
		Set<String> s1 = webdriver.getWindowHandles();
		if (s1.size() > 1) 
		{
		  Iterator<String> i1 = s1.iterator();
		  while (i1.hasNext()) {
		  String ChildWindow = i1.next();
		  if (mainwindow.equalsIgnoreCase(ChildWindow))
     {
		//report.detailsAppend("verify the Navigation screen shot", " Navigation page screen shot", "Navigation page screenshot ", "PASS");
		ChildWindow = i1.next();
		webdriver.switchTo().window(ChildWindow);
		String url = webdriver.getCurrentUrl();
		log.debug("Navigation URL is :: " + url);
		if (!url.equalsIgnoreCase(gameurl))
		{
		// pass condition for navigation	
		report.detailsAppend("verify the Navigation screen shot", " Navigation page screen shot","Navigation page screenshot ", "PASS");
		log.debug("Page navigated succesfully");
		webdriver.close();
		} 
		else
		{
			//System.out.println("Now On game page");
			log.debug("Now On game page");
		}
	}
}
		webdriver.switchTo().window(mainwindow);
	} 
		else
	{
		String url = webdriver.getCurrentUrl();
	//System.out.println("Navigation URL is ::  " + url);
	log.debug("Navigation URL is ::  " + url);
	if (!url.equalsIgnoreCase(gameurl)) {
		// pass condition for navigation
		report.detailsAppend("verify the Navigation screen shot", " Navigation page screen shot","Navigation page screenshot ", "PASS");
		log.debug("Page navigated succesfully");
		webdriver.navigate().to(gameurl);
		waitForSpinButton();
		//newFeature();
	}
	else 
	{
		webdriver.navigate().to(gameurl);
		waitForSpinButton();
		//System.out.println("Now On game page");
		log.debug("Now On game page");
       }
		}
	} catch (Exception e)
	{
		log.error("error in navigation of page");
	}
	}
/**
 * validated the hambugermenu options 
 * @author rk61073
 */
public void menu(Desktop_HTML_Report report)
{
	String gameurl = webdriver.getCurrentUrl();
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
		for(int i=1;i<=3;i++)
		{
		if(fun_Click("HamburgerMenu"))
		{
		System.out.println("Clicked Hamburger Menu");log.debug("Clicked Hamburger Menu");
		report.detailsAppend("Hamburger","Hamburger menu clicked","Hamburger menu clicked","PASS");
		Thread.sleep(3000);
		if(i==1)
		{
			if(fun_Click("HomeIconfromHamburgerMenu"))
			{Thread.sleep(6000);
			System.out.println("Clicked Home button from Hamburger");log.debug("Clicked Home button from Hamburger");
			report.detailsAppend("Home-Icon","Home-Icon from Hamburger menu clicked","Home-Icon from Hamburger menu clicked","PASS");
			checkpagenavigation(report,gameurl);
			}
			else
			{
			System.out.println("Unable to click on Home");log.debug("Unable to click on Home");
			report.detailsAppend("Home-Icon","Home-Icon from Hamburger Menu clicked","Home-Icon from Hamburger Menu clicked","FAIL");
			}
			
		}
		else if(i==2)
		{
			if(fun_Click("BankingIconfromHamburgerMenu"))
			{Thread.sleep(6000);
			System.out.println("Clicked on Banking from Hamburger Menu");log.debug("Clicked on Banking from Hamburger Menu");
			report.detailsAppend("Banking","Banking from Hamburger menu clicked","Banking from Hamburger menu clicked","PASS");
			checkpagenavigation(report,gameurl);	
			}
			else
			{
			System.out.println("Unable to click on Banking ");log.debug("Unable to click on Banking ");
			report.detailsAppend("Banking","Banking from Hamburger Menu clicked","Banking from Hamburger Menu clicked","FAIL");
			}
			
		}
		else
		{
			if(fun_Click("SettingsIconfromHamburgerMenu"))
			{Thread.sleep(4000);
			System.out.println("Clicked on settings from Hamburger Menu");log.debug("Clicked on settings from Hamburger Menu");
			report.detailsAppend("Settings","Settings from menu clicked","Settings from menu clicked","PASS");
			}
			else
			{
			System.out.println("Unable to click on Settings ");log.debug("Unable to click on Settings ");
			report.detailsAppend("Settings","Settings from menu clicked","Settings from menu clicked","FAIL");
			}
		}}
		else
		{
		System.out.println("Unable to click on Menu ");log.debug("Unable to click on Menu ");
		report.detailsAppend("Hamburger","Hamburger menu clicked","Hamburger menu clicked","FAIL");
		}Thread.sleep(2000);
		}closeSettings(report);}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}}
/**
 * method is to close the settings
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public boolean creditValidationAndCollectTheAmt(Desktop_HTML_Report report, String languageCode)
{
	boolean creditVal = false;
	String getCurrentCredits ="";
	String getCurrentTotalWin  ="";
	double getCurrentCreditBeforeTotalWinGetsAdded  =0.0;
	String getCurrentCreditAfterTotalWinGetsAdded ="";
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	
	try
	{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("CollectButton")))) != null)
		{
			getCurrentCredits= fun_GetText("CreditAmount");
			double getCurrentCredit = Double.parseDouble(getCurrentCredits.replaceAll("[^.0-9]", ""));
			System.out.println("Credits : " +getCurrentCredit);
			
		    getCurrentTotalWin = fun_GetText("TotalWinAmount");
		    double getCurrentWin = Double.parseDouble(getCurrentTotalWin.replaceAll("[^.0-9]", ""));
			System.out.println("Total Win : " +getCurrentWin);
			
		    getCurrentCreditBeforeTotalWinGetsAdded = (getCurrentCredit + getCurrentWin);
		    System.out.println("Before Collecting the Amt "+ getCurrentCreditBeforeTotalWinGetsAdded);
		    
		    //for 100 pay and 50 play games which has paytable  button 
		    if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
			{
		    	if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("TotalWinAmount")))) != null)
		    	{
		    			if(fun_Click("PaytableClose"))
		    			{
		    				report.detailsAppendFolder("PaytableClose", "PaytableClose", "PaytableClose", "PASS", languageCode);
			    			resizeBrowser(600, 800);
			    			Thread.sleep(2000);
			    			report.detailsAppendFolder("PaytableClose", "Resizing", "Resizing", "PASS", languageCode);
			    			resizeBrowser(400,600);
			    			Thread.sleep(2000);
			    			report.detailsAppendFolder("PaytableClose", "Resizing", "Resizing", "PASS", languageCode);
			    			webdriver.manage().window().maximize();Thread.sleep(2000);
		    			}
		    			else
		    			{
		    				report.detailsAppendFolder("PaytableClose", "PaytableClose", "PaytableClose", "FAIL", languageCode);
		    			}
		    	}
		    	else
		    	{
		    		report.detailsAppendFolder("PaytableClose", "PaytableClose", "PaytableClose", "FAIL", languageCode);
		    	}	
			}
		    
		     fun_Click("CollectButton");Thread.sleep(4000);
		     report.detailsAppendFolder("Collect Button", "Collect Button", "Collect Button", "PASS", languageCode);
			
		     getCurrentCreditAfterTotalWinGetsAdded = fun_GetText("CreditAmount");
		     double getCurrentCreditAfterWinGetsAdded = Double.parseDouble(getCurrentCreditAfterTotalWinGetsAdded.replaceAll("[^.0-9]", ""));
			 System.out.println("After Collecting the Amt : "+getCurrentCreditAfterWinGetsAdded);
				
		     System.out.println("After Collecting the Amt "+ getCurrentCreditBeforeTotalWinGetsAdded);
		    if(getCurrentCreditBeforeTotalWinGetsAdded == getCurrentCreditAfterWinGetsAdded) 
		    {
		    	 System.out.println("Credit Amount is updating correctly after collecting the amount  : PASS");
		    	 report.detailsAppendFolder("Credit Amount is updating correctly after collecting the amount", "Credit Amount", "Credit Amount", "PASS", languageCode);
		    	 creditVal = true;
		    }
		    else
		    {
		    	System.out.println("Credit Amount is updating correctly after collecting the amount  : FAIL");
		    	 report.detailsAppendFolder("Credit Amount is updating correctly after collecting the amount", "Credit Amount", "Credit Amount", "FAIL", languageCode);
		    	 creditVal = false;
		    }}
		else
	    {
	    	System.out.println("Collect Amount : FAIL");log.debug("Collect Amount : FAIL");
	    }}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}
	return creditVal;}
/**
 * 
 * @param report
 * @param currencyName
 * @return
 * @author rk61073
 */
public boolean openBet(Desktop_HTML_Report report, String currencyName)
{
	boolean openbetpannel= false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
	try 
	{
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("BetButton")))) != null)
		{
		fun_Click("BetButton");
		openbetpannel = true;
		}
		else
		{
		openbetpannel = false;
		}
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}
	return openbetpannel;

}
/**
 * 
 * @param report
 * @param language
 * @return
 * @author rk61073
 */
public boolean refresh(Desktop_HTML_Report report, String language)
{
	boolean refresh = false;
	Wait = new WebDriverWait(webdriver, 40);
	try 
	{
	    webdriver.navigate().refresh();Thread.sleep(2000);
		report.detailsAppendFolder("Refresh ","Refresh the game","Refresh the game","PASS",""+language);
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("clock"))));
		refresh = true;
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}
	return refresh;
}
/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public String[] paytablePayoutsOfTen(Desktop_HTML_Report report, String CurrencyName) 
{
	String paytablePayout[] = {"payout1","payout2","payout3","payout4","payout5","payout6","payout7","payout8","payout9","payout10"};
    String demo[] = new String[10];
	try
   {
	   for(int i=1;i<=5;i++)
		{
		   fun_Click("Paytable"); 
	       String coins = fun_GetText("Coins");
		for(int j=0;j<paytablePayout.length;j++)
		{	
			 demo[j]= fun_GetText(paytablePayout[j]);	
		}
		report.detailsAppendFolder("Verify paytable payouts . Where, "+coins,"Paytable payouts of "+coins,"Paytable payouts of "+coins,"PASS",""+CurrencyName);
		resizeBrowser(600, 800);
    	Thread.sleep(2000);
    	report.detailsAppendFolder("Verify paytable payouts where, "+coins, "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", CurrencyName);
    	resizeBrowser(400,600);
    	Thread.sleep(2000);
    	report.detailsAppendFolder("Verify paytable payouts where, "+coins, "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", CurrencyName);
    	webdriver.manage().window().maximize();Thread.sleep(2000);
		}
	   if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
	   {
		   fun_Click("PaytableClose");
	   }
		}
    catch (Exception e) 
    {
    	report.detailsAppendFolder("Verify the Paytable payouts by chainging coinsize and coins ","Paytable payouts by chainging coinsize and coins","Paytable payouts by chainging coinsize and coins","FAIL",""+CurrencyName);
        e.printStackTrace();
    }
    return demo;
}
/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public String[] paytablePayoutsOfNine(Desktop_HTML_Report report, String CurrencyName) 
{
	String paytablePayout[] = {"payout1","payout2","payout3","payout4","payout5","payout6","payout7","payout8","payout9"};
    String demo[] = new String[9];
	try
   {
	   for(int i=1;i<=5;i++)
		{
         fun_Click("Paytable"); 
         String coins = fun_GetText("Coins");
		for(int j=0;j<paytablePayout.length;j++)
		{	
			 demo[j]= fun_GetText(paytablePayout[j]);
		}
		report.detailsAppendFolder("Verify paytable payouts . Where, "+coins,"Paytable payouts of "+coins,"Paytable payouts of "+coins,"PASS",""+CurrencyName);
		resizeBrowser(600, 800);
    	Thread.sleep(2000);
    	report.detailsAppendFolder("Verify paytable payouts where, "+coins, "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", CurrencyName);
    	resizeBrowser(400,600);
    	Thread.sleep(2000);
    	report.detailsAppendFolder("Verify paytable payouts where, "+coins, "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", CurrencyName);
    	webdriver.manage().window().maximize();Thread.sleep(2000);
		}
	   if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
	   {
		   fun_Click("PaytableClose");
	   }
		}
    catch (Exception e) 
    {
    	report.detailsAppendFolder("Verify the Paytable payouts by chainging coinsize and coins ","Paytable payouts by chainging coinsize and coins","Paytable payouts by chainging coinsize and coins","FAIL",""+CurrencyName);
        e.printStackTrace();
    }
    return demo;
}/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public String[] paytablePayoutsOfThirteen(Desktop_HTML_Report report, String CurrencyName) 
{
	String paytablePayout[] = {"payout1","payout2","payout3","payout4","payout5","payout6","payout7","payout8","payout9","payout10","payout11","payout12","payout13"};
	String demo[] = new String[13];
	try
   {
	   for(int i=1;i<=5;i++)
		{
		   fun_Click("Paytable"); 
	       String coins = fun_GetText("Coins");
		for(int j=0;j<paytablePayout.length;j++)
		{	
			 demo[j]= fun_GetText(paytablePayout[j]);
		}
		report.detailsAppendFolder("Verify paytable payouts . Where, "+coins,"Paytable payouts of "+coins,"Paytable payouts of "+coins,"PASS",""+CurrencyName);
		resizeBrowser(600, 800);
    	Thread.sleep(2000);
    	report.detailsAppendFolder("Verify paytable payouts where, "+coins, "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", CurrencyName);
    	resizeBrowser(400,600);
    	Thread.sleep(2000);
    	report.detailsAppendFolder("Verify paytable payouts where, "+coins, "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", CurrencyName);
    	webdriver.manage().window().maximize();Thread.sleep(2000);
		}
	   if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
	   {
		   fun_Click("PaytableClose");
	   }
		}
    catch (Exception e) 
    {
    	report.detailsAppendFolder("Verify the Paytable payouts by chainging coinsize and coins ","Paytable payouts by chainging coinsize and coins","Paytable payouts by chainging coinsize and coins","FAIL",""+CurrencyName);
       e.printStackTrace();
    }
    return demo ;
}
/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public String[] paytablePayoutsOfEleven(Desktop_HTML_Report report, String CurrencyName) 
{
	String paytablePayout[] = {"payout1","payout2","payout3","payout4","payout5","payout6","payout7","payout8","payout9","payout10","payout11"};
	String demo[] = new String[11];
	try
   {
	   for(int i=1;i<=5;i++)
		{
		   fun_Click("Paytable"); 
	       String coins = fun_GetText("Coins");
		for(int j=0;j<paytablePayout.length;j++)
		{	
			 demo[j]= fun_GetText(paytablePayout[j]);
		}
		report.detailsAppendFolder("Verify paytable payouts . Where, "+coins,"Paytable payouts of "+coins,"Paytable payouts of "+coins,"PASS",""+CurrencyName);
		resizeBrowser(600, 800);
    	Thread.sleep(2000);
    	report.detailsAppendFolder("Verify paytable payouts where, "+coins, "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", CurrencyName);
    	resizeBrowser(400,600);
    	Thread.sleep(2000);
    	report.detailsAppendFolder("Verify paytable payouts where, "+coins, "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", CurrencyName);
    	webdriver.manage().window().maximize();Thread.sleep(2000);
		}
	   if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
	   {
		   fun_Click("PaytableClose");
	   }
		}
    catch (Exception e) 
    {
    	report.detailsAppendFolder("Verify the Paytable payouts by chainging coinsize and coins ","Paytable payouts by chainging coinsize and coins","Paytable payouts by chainging coinsize and coins","FAIL",""+CurrencyName);
        e.printStackTrace();
    }
    return demo ;
}
/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public boolean paytablePayoutsByChangingTheCoinSizes(Desktop_HTML_Report report,String regExpr,String CurrencyName) 
{
	boolean payoutvalues = false;
	try
	{
		if(XpathMap.get("PaytablePayoutsofEleven").equalsIgnoreCase("yes"))
		{
		 payoutvalues =   verifyRegularExpressionUsingArrays(report,regExpr,paytablePayoutsOfEleven(report,CurrencyName)); 
		 return payoutvalues;
		}
		else if(XpathMap.get("PaytablePayoutsofThirteen").equalsIgnoreCase("yes")) 
		{
			payoutvalues =	verifyRegularExpressionUsingArrays(report,regExpr,paytablePayoutsOfThirteen(report,CurrencyName));
			return payoutvalues;
		}
		else if(XpathMap.get("PaytablePayoutsofTen").equalsIgnoreCase("yes"))
		{
			payoutvalues =	verifyRegularExpressionUsingArrays(report,regExpr,paytablePayoutsOfTen(report,CurrencyName));
			return payoutvalues;
		}
		else if(XpathMap.get("PaytablePayoutsofNine").equalsIgnoreCase("yes"))
		{
			payoutvalues =	verifyRegularExpressionUsingArrays(report,regExpr,paytablePayoutsOfNine(report,CurrencyName));
			return payoutvalues;
		}
		else
		{
			System.out.println("Verify Paytable Payouts");log.debug("Verify Paytable Payouts");
		}
		Thread.sleep(2000);
		}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
	return payoutvalues;
}
/**
 * method is to get the paytable payouts
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public boolean paytablePayoutsByChangingTheCoinSizes(Desktop_HTML_Report report,String CurrencyName) 
{
	boolean payoutvalues = false;
	try
	{
		
		if(XpathMap.get("PaytablePayoutsofThirteen").equalsIgnoreCase("yes"))
		{
		 payoutvalues =   paytablePayoutsOfThirteen(report,CurrencyName) != null; 
		 return payoutvalues;
		}
		if(XpathMap.get("PaytablePayoutsofEleven").equalsIgnoreCase("yes"))
		{
		 payoutvalues =   paytablePayoutsOfEleven(report,CurrencyName) != null; 
		 return payoutvalues;
		}
		else if(XpathMap.get("PaytablePayoutsofTen").equalsIgnoreCase("yes"))
		{
			payoutvalues =	paytablePayoutsOfTen(report,CurrencyName) != null;
			return payoutvalues;
		}
		else if(XpathMap.get("PaytablePayoutsofNine").equalsIgnoreCase("yes"))
		{
			payoutvalues =	paytablePayoutsOfNine(report,CurrencyName) != null;
			return payoutvalues;
		}
		else
		{
			System.out.println("Verify Paytable Payouts");log.debug("Verify Paytable Payouts");
		}
		Thread.sleep(2000);
		}
	catch (Exception e) 
	{
		log.error(e.getMessage(), e);
	}
	return payoutvalues;
}
/**
 * method is to get the deal ,draw & Validate Total Win  amount
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public boolean clickOnDealDrawAndValidateTotalWin(Desktop_HTML_Report report,String regExpr, String currencyName)
{
    boolean dealDrawAndToatlWin= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
        if(Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("DealButton")))) != null)
        {
        fun_Click("DealButton");
        System.out.println("Clicked on DealButton");log.debug("Clicked on DealButton");
        report.detailsAppendFolder("Base Game","Deal Button","Deal Button","PASS",""+currencyName);
        Thread.sleep(2000);
        
        if(Wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XpathMap.get("DrawButton")))) != null)
        {
        fun_Click("DrawButton");
        System.out.println("Clicked on DrawButton");log.debug("Clicked on DrawButton");
        report.detailsAppendFolder("Base Game","Draw Button","Draw Button","PASS",""+currencyName);
        dealDrawAndToatlWin = true;Thread.sleep(2000);
        }//closing if loop for Draw Button
        else
        {
            dealDrawAndToatlWin = false;
            System.out.println("Unable to click on DrawButton");log.debug("Unable to click on DrawButton");
            report.detailsAppendFolder("Base Game","Draw Button","Draw Button","FAIL",""+currencyName);    
        }
        }//closing if for Deal Button
        else
        {
            dealDrawAndToatlWin = false;
            System.out.println("Unable to click on Deal Button ");log.debug("Unable to click on Deal Button ");
            report.detailsAppendFolder("Base Game","Deal Button ","Deal Button ","FAIL",""+currencyName);    
        }
        //total win and its validation 
        dealDrawAndToatlWin = totalWin(report,regExpr,currencyName);
        if(dealDrawAndToatlWin!=false)
        {
            System.out.println("Total Win Amt : PASS");log.debug("Total Win Amt : PASS");
            dealDrawAndToatlWin = true;
        }
        else
        {
            System.out.println("Total Win Amt : FAIL");log.debug("Total Win Amt: FAIL");
            dealDrawAndToatlWin = false;
        }
        }//closing
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }    
    
    return dealDrawAndToatlWin;        
}
/**
 * this method changes the coinsize by clicking on left and right buttons & validate the text from  coinsize from total bet & coin size 
 * @return
 * @author rk61073
 */
public boolean betPlusButtonValidation( Desktop_HTML_Report  report, String CurrencyName )
{
    boolean coinSize= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	boolean betAmt = validateBeforeAndAfterClick("BetAmount","BetPlusButton");
    	boolean payouts =validateBeforeAndAfterClick("payout1","BetPlusButton");
    	boolean coinsizeUpdationInBaseAndInBetPanel =coinSizeUpdationInBaseAndInBetPanel();
    	
        String coinsMinus = "Bet Amount : "+betAmt + "  Payouts from Paytable : "+payouts+ "  Coinsize text from Base game and in BetPanel  : "+coinsizeUpdationInBaseAndInBetPanel;
    	
    	if(betAmt && payouts && coinsizeUpdationInBaseAndInBetPanel)
    	{
    		report.detailsAppendFolder("Verify the Coinsize Forward Button","Verify the bet amt , payouts, coinsize from bet panel by clicking on coinsize forward button",""+coinsMinus,"PASS",""+CurrencyName);
    		coinSize = true;
    	}
    	else
    	{
    		report.detailsAppendFolder("Verify the Coinsize Forward Button","Verify the bet amt , payouts, coinsize from bet panel by clicking on coinsize forward button",""+coinsMinus,"FAIL",""+CurrencyName);
    		coinSize = true;
    	}
    
        
     }//closing try block 
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }    
    
    return coinSize;        
}
/**
 * this method changes the coinsize by clicking on decrement button on bet  & validate the text from  coinsize from total bet & coin size 
 * @return
 * @author rk61073
 */
public boolean betMinusButtonValidation( Desktop_HTML_Report  report, String CurrencyName )
{
    boolean coinSize= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	boolean betAmt = validateBeforeAndAfterClick("BetAmount","BetMinusButton");
    	boolean payouts =validateBeforeAndAfterClick("payout1","BetMinusButton");
    	boolean coinsizeUpdationInBaseAndInBetPanel =coinSizeUpdationInBaseAndInBetPanel();
    	
        String coinsMinus = "Bet Amount : "+betAmt + "  Payouts from Paytable : "+payouts+ "  Coinsize text from Base game and in BetPanel  : "+coinsizeUpdationInBaseAndInBetPanel;
    	
    	if(betAmt && payouts && coinsizeUpdationInBaseAndInBetPanel)
    	{
    		report.detailsAppendFolder("Verify the Coinsize Backword Button","Verify the bet amt , payouts, coinsize from bet panel by clicking on coinsize backword button",""+coinsMinus,"PASS",""+CurrencyName);
    		coinSize = true;
    	}
    	else
    	{
    		report.detailsAppendFolder("Verify the Coinsize Backword Button","Verify the bet amt , payouts, coinsize from bet panel by clicking on coinsize backword  button",""+coinsMinus,"FAIL",""+CurrencyName);
    		coinSize = false;
    	}
    
        
     }//closing try block 
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }    
    
    return coinSize;        
}
/**
 * 
 * @return
 * @author rk61073
 */
public boolean validateBeforeAndAfterClick(String ele1 , String ele2 )
{							
    boolean clickAndValidate= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
    	{
    		 if(fun_Click("PaytableIcon"));
    		 {
    			  String before = fun_GetText(ele1);
    			  fun_Click(ele2);
    		      String after = fun_GetText(ele1);
			    	if(!before.equalsIgnoreCase(after))
					 {
			    		if(fun_Click("PaytableClose"))
			    		{
			    		  log.debug("Text before and after click action is same");
			    		  clickAndValidate = true;
			    		}
					 }
    		    	else
    		    	{
    		    		log.debug("Text before and after click action is different");
    		    		clickAndValidate = false;
    		    	} 
    		 }
    	}
    	else
    	{
    	String before = fun_GetText(ele1);
    	fun_Click(ele2);
    	String after = fun_GetText(ele1);
    	if(!before.equalsIgnoreCase(after))
		 {
    		log.debug("Text before and after click action is same");
    		clickAndValidate = true;
		 }
    	else
    	{
    		log.debug("Text before and after click action is different");
    		clickAndValidate = false;
    	}}
    }
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }    
    return clickAndValidate;        
}
/**
 * 
 * @return
 * @author rk61073
 */
public boolean validateBeforeAndAfterClickAction(String ele1 , String ele2 )
{							
    boolean clickAndValidate= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	String before = fun_GetText(ele1);
    	fun_Click(ele2);
    	String after = fun_GetText(ele1);
    	if(before.equalsIgnoreCase(after))
		 {
    		log.debug("Text before and after click action is same");
    		clickAndValidate = true;
		 }
    	else
    	{
    		log.debug("Text before and after click action is different");
    		clickAndValidate = false;
    	}
    }
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }    
    return clickAndValidate;        
}
/**
 * this method changes the coinsize by clicking on left buttons & validate the text from coinsfromtotalbet & coins 
 * @return
 * @author rk61073
 */
public boolean coinsMinusButtonValidation(Desktop_HTML_Report  report, String CurrencyName )
{							
    boolean coinsMinusBtn= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	boolean betAmt = validateBeforeAndAfterClick("BetAmount","CoinsMinusButton");
    	boolean payouts =validateBeforeAndAfterClick("payout1","CoinsMinusButton");
    	boolean coinsText =validateBeforeAndAfterClick("CoinsText","CoinsMinusButton");
    	boolean coinsUpdationInBaseAndInBetPanel =coinsUpdationInBaseAndInBetPanel();
    	
    	String coinsMinus = "Bet Amount : "+betAmt + "  Payouts from Paytable : "+payouts+"  Coins Text :  "+coinsText + "  Coins text from Base game and in BetPanel  : "+coinsUpdationInBaseAndInBetPanel;
    	
    	if(betAmt && payouts && coinsText && coinsUpdationInBaseAndInBetPanel )
    	{
    		report.detailsAppendFolder("Verify the Coins Minus Button","Verify the bet amt , payouts, coins from bet panel by clicking on coins minus button",""+coinsMinus,"PASS",""+CurrencyName);
    		coinsMinusBtn = true;
    	}
    	else
    	{
    		report.detailsAppendFolder("Verify the Coins Minus Button","Verify the bet amt , payouts, coins from bet panel by clicking on coins minus button",""+coinsMinus,"FAIL",""+CurrencyName);
    		coinsMinusBtn = true;
    	}
      	  
    }//closing try block 
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }    
    return coinsMinusBtn;        
}
/**
 * this method changes the coinsize by clicking on  right buttons & validate the text from coinsfromtotalbet & coins 
 * @return
 * @author rk61073
 */
public boolean coinsPlusButtonValidation(Desktop_HTML_Report  report, String CurrencyName )
{							
    boolean coinsMinusBtn= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	boolean betAmt = validateBeforeAndAfterClick("BetAmount","CoinsPlusButton");
    	boolean payouts =validateBeforeAndAfterClick("payout1","CoinsPlusButton");
    	boolean coinsText =validateBeforeAndAfterClick("CoinsText","CoinsPlusButton");
    	boolean coinsUpdationInBaseAndInBetPanel =coinsUpdationInBaseAndInBetPanel();
    	
    	String coinsMinus = "Bet Amount : "+betAmt + "  Payouts from Paytable : "+payouts+"  Coins Text :  "+coinsText + "  Coins Updation in Base game and in BetPanel : "+coinsUpdationInBaseAndInBetPanel;
    	
    	if(betAmt && payouts && coinsText && coinsUpdationInBaseAndInBetPanel )
    	{
    		report.detailsAppendFolder("Verify the Coins Plus Button","Verify the bet amt , payouts, coins from bet panel by clicking on coins Plus button",""+coinsMinus,"PASS",""+CurrencyName);
    		coinsMinusBtn = true;
    	}
    	else
    	{
    		report.detailsAppendFolder("Verify the Coins Plus Button","Verify the bet amt , payouts, coins from bet panel by clicking on coins Plus button",""+coinsMinus,"FAIL",""+CurrencyName);
    		coinsMinusBtn = true;
    	}
      	  
    }//closing try block 
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }    
    return coinsMinusBtn;        
}
/**
 * this method clicks on betplusone button & validate the text from  CoinText & CoinTextFromTotalBet
 * @return
 * @author rk61073
 */
public boolean betPlusOneButton()
{
    boolean betPlusOne= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	for(int i=1 ; i<=5; i++)
    	{
    	 String coinstextbeforeclicking = fun_GetText("CoinsText"); 
        if(fun_Click("BetPlusOneButton"))
        {  
        	 String coinstextafterclicking = fun_GetText("CoinsText"); 
        	 if(!coinstextbeforeclicking.equalsIgnoreCase(coinstextafterclicking))
        	 {
        	    if(coinsUpdationInBaseAndInBetPanel())
        	    {
        		System.out.println("BetPlusOneButton clicked and the text of Coins in base and in bet panel are same  : PASS");
        	 	betPlusOne = true;
        	    }
        	    else 
        	    {
        		System.out.println("BetPlusOneButton clicked and the text of Coins in base and in bet panel are same  : FAIL");
        		betPlusOne = false;
        	   }
        	 }//closing if comparison
        	 else
             {
             	System.out.println("BetPlusOneButton updation :FAIL");betPlusOne = false;
             }
        }//closing if condition
        
        else
        {
        	System.out.println("BetPlusOneButton Click :FAIL");betPlusOne = false;
        }
    	}
        }//closing for block								

    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return betPlusOne;        
}

/**
 * validates if the bet increment & decrement buttons available or not 
 * @return
 * @author rk61073
 */
public boolean isBetPlusAndMinusButtonsAvailableAndEnabled(Desktop_HTML_Report report,String CurrencyName)
{
    boolean betbuttons= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
	if(checkAvilabilityofElement("BetButton"))
    {  
		log.debug("Bet Button is Visible");
		if(checkAvilabilityofElement("BetMinusButton") && checkAvilabilityofElement("BetPlusButton"))
	   {
			log.debug("Bet plus and minus Button are Visible");
			if(checkAvilabilityofElement("IsBetMinusButtonEnabled") &&checkAvilabilityofElement("IsBetButtonEnabled") && checkAvilabilityofElement("IsBetPlusButtonEnabled"))
			   {
			      log.debug("Bet , Bet plus & Bet Minus buttons are available & enabled : PASS ");
			     betbuttons = true;
			   }
			else
			{
				log.debug("Bet , Bet plus & Bet Minus buttons are available & disabled : FAIL ");
				betbuttons = false;
			}
	   }
	   else
	   {
		   log.debug("Bet plus and minus Button are not Visible");
		   betbuttons = false;
	   }	
    }
    else
    {
    	log.debug("Bet Button is not Visible");
 	   
    }	   
    }//closing for block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return betbuttons;        
}
/**
 * validates if the coins increment & decrement buttons available & enabled or not 
 * @return
 * @author rk61073
 */
public boolean isCoinsPlusAndMinusButtonsAvailableAndEnabled(Desktop_HTML_Report report,String CurrencyName)
{
    boolean betbuttons= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 10);
    try
    {
	if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
	  {
		if(fun_Click("PaytableIcon"))
	    {	log.debug("clicked on paytable");
			if(checkAvilabilityofElement("CoinsMinusButton") && checkAvilabilityofElement("CoinsPlusButton"))
		   {
				log.debug("Coins minus & plus Buttons are Visible");
				if(checkAvilabilityofElement("IsCoinsMinusButtonEnabled") &&checkAvilabilityofElement("IsCoinsPlusButtonEnabled"))
				   {
				     if(fun_Click("PaytableClose"))
				     {
				     betbuttons = true;
				     log.debug("Coins , Coins plus & Coins Minus buttons are available & enabled : PASS ");
				     }
				   }
				else
				{
					log.debug("Coins , Coins plus & Coins Minus buttons are available & disabled : FAIL ");
					betbuttons = false;
				}
		   }
		   else
		   {
			   log.debug("Bet Increment and decrement are not Visible");
			   betbuttons = false;
		   }	
	    }
	    else
	    {
	    	log.debug("unable to click on paytable");betbuttons = false;
	 	   
	    }	}
	else
	{
	if(checkAvilabilityofElement("Coins"))
    {  
		log.debug("Coins Button is Visible");
		if(checkAvilabilityofElement("CoinsMinusButton") && checkAvilabilityofElement("CoinsPlusButton"))
	   {
			log.debug("Coins minus & plus Buttons are Visible");
			if(checkAvilabilityofElement("IsCoinsMinusButtonEnabled") &&checkAvilabilityofElement("IsCoinsPlusButtonEnabled") && checkAvilabilityofElement("IsBetPlusButtonEnabled"))
			   {
			     log.debug("Coins , Coins plus & Coins Minus buttons are available & enabled : PASS ");
			     betbuttons = true;
			   }
			else
			{
				log.debug("Coins , Coins plus & Coins Minus buttons are available & disabled : FAIL ");
				betbuttons = false;
			}
	   }
	   else
	   {
		   log.debug("Bet Increment and decrement are not Visible");
		   betbuttons = false;
	   }	
    }
    else
    {
    	log.debug("Bet Button is not Visible");
 	   
    }	}//closing else block   
    }//closing for block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return betbuttons;        
}
/**
 * validates if the bet plus button  available &  enabled or not 
 * @return
 * @author rk61073
 */
public boolean isBetPlusOneButtonAvailableAndEnabled(Desktop_HTML_Report report,String CurrencyName)
{
    boolean betplus= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	if(checkAvilabilityofElement("BetPlusOneButton"))
    	{
    		log.debug("Bet plus one button is displayed");
    		if(checkAvilabilityofElement("IsBetPlusOneButtonEnabled"))
    		{
    			log.debug("BetplusOne button is displayed & enabled : PASS");
    			betplus = true;
    		}
    		else
    		{
    			log.debug("BetplusOne button is displayed & enabled : FAIL");
    			betplus = false;
    		}
    	}
    	else
    	{
    		log.debug("Bet plus one button is not displayed");
    		betplus = false;
    	}
        
    }//closing for block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return betplus;        
}
/**
 * validates if the bet max button  available & enabled or not 
 * @return
 * @author rk61073
 */
public boolean isBetMaxButtonAvailableAndEnabled(Desktop_HTML_Report report,String CurrencyName)
{
    boolean betmax= false;
   // WebDriverWait Wait = new WebDriverWait(webdriver, 10);
    try
    {
    	if(checkAvilabilityofElement("BetMaxButton"))
    	{
    		log.debug("BetMax button is displayed");
    		
    		if(checkAvilabilityofElement("IsBetMaxButtonEnabled"))
    		{
    			log.debug("BetMax button is displayed & enabled : PASS");
    			betmax = true;
    		}
    		else
    		{
    			log.debug("BetMax button is displayed & enabled : FAIL");
    			betmax = false;
    		}
    	}
    	else
    	{
    		betmax = false;
    		log.debug("BetMax button is not displayed");
    	}
        
    }//closing for block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return betmax;        
}

/*
/**
 * method validate the payouts and coins updation from the base & bet panel by clicking on BetPlusOneButton
 * @return
 * @author rk61073
 */
public boolean betPlusOneButtonValidation(Desktop_HTML_Report  report, String CurrencyName)
{
    boolean betplusone= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	for(int i=0 ; i<=5;i++)
    	{
        	boolean betAmt = validateBeforeAndAfterClick("BetAmount","BetPlusOneButton");
        	boolean payouts =validateBeforeAndAfterClick("payout1","BetPlusOneButton");
        	boolean coinsUpdationInBaseAndInBetPanel =coinsUpdationInBaseAndInBetPanel();
        	
        	String coinsMinus = "Bet Amount : "+betAmt + "  Payouts from Paytable : "+payouts+ "  Coins Updation in Base game and in BetPanel : "+coinsUpdationInBaseAndInBetPanel;
        	
        	if(betAmt && payouts && coinsUpdationInBaseAndInBetPanel )
        	{
        		report.detailsAppendFolder("Verify the BetPlusOne Button , Iteration No : "+i,"Verify the bet amt , payouts, coins from bet panel by clicking on BetPlusOne button in loop",""+coinsMinus,"PASS",""+CurrencyName);
        		betplusone = true;
        	}
        	else
        	{
        		report.detailsAppendFolder("Verify the BetPlusOne Button , Iteration No : "+i,"Verify the bet amt , payouts, coins from bet panel by clicking on BetPlusOne button in loop",""+coinsMinus,"FAIL",""+CurrencyName);
        		betplusone = true;
        	}
          	  
        }//closing for loop
    	 
    }//closing try block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return betplusone;        
}

/**
 * method validate the change in the Coins but not in coinsize by clicking on paytable for 5 times in loop 
 * @return
 * @author rk61073
 */
public boolean payoutsValidationByChangingCoins( Desktop_HTML_Report  report, String CurrencyName)
{
    boolean paytable= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	for(int i = 1; i<= 5 ; i++)
    	{
		boolean betAmt = validateBeforeAndAfterClick("BetAmount","Paytable");
    	boolean payouts =validateBeforeAndAfterClick("payout1","Paytable");
    	boolean coinsText =validateBeforeAndAfterClick("CoinsText","Paytable");
    	boolean coinsUpdationInBaseAndInBetPanel =coinsUpdationInBaseAndInBetPanel();
    	
    	
    	 fun_Click("BetButton");
    	 String coinstextfromtotalbet = fun_GetText("CoinsTextFromTotalBet");
   	     String coinsizetextfromtotalbet = fun_GetText("CoinSizeFromTotalBetText");
   	     closeOverlay();//Thread.sleep(2000);
   	     
   	  if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
  	   {
         fun_Click("PaytableIcon"); Thread.sleep(2000);
    	 fun_Click("Paytable");  Thread.sleep(2000);
    	 fun_Click("PaytableClose"); Thread.sleep(2000);
  	   }
   	  else
   	  {
   	     fun_Click("Paytable"); 
   	  }
   	  
   	     fun_Click("BetButton");
    	 String coinstextvaluefromtotalbet = fun_GetText("CoinsTextFromTotalBet");
   	     String coinsizetextvaluefromtotalbet = fun_GetText("CoinSizeFromTotalBetText");
   	     closeOverlay();//Thread.sleep(2000);
   	     
    boolean coinsAndcoinSizefromBetAndBase =  !(coinstextfromtotalbet.equalsIgnoreCase(coinstextvaluefromtotalbet)) && coinsizetextfromtotalbet.equals(coinsizetextvaluefromtotalbet);
  
   	String coinsMinus = "Bet Amount : "+betAmt + "  Payouts from Paytable : "+payouts+"  Coins Text :  "+coinsText + "In bet pannel coins text is different , but the coin size text is same : " +coinsAndcoinSizefromBetAndBase + "  Coins Updation in Base game and in BetPanel : "+coinsUpdationInBaseAndInBetPanel;
  	
   	  if(betAmt && payouts && coinsText && coinsUpdationInBaseAndInBetPanel)
  	{
   		if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
   	   {
          fun_Click("PaytableIcon");
   		 report.detailsAppendFolder("Verify the Paytable payouts by chainging only coinsize  , Iteration No : "+i,"Verify the bet amt , payouts, coins from bet panel by clicking on coins Plus button",""+coinsMinus,"PASS",""+CurrencyName);
   		  fun_Click("PaytableClose");
   	   }
   		else
   		{
   		 report.detailsAppendFolder("Verify the Paytable payouts by chainging only coinsize  , Iteration No : "+i,"Verify the bet amt , payouts, coins from bet panel by clicking on coins Plus button",""+coinsMinus,"PASS",""+CurrencyName);	
   		}
   		  paytable = true;
  	}
  	else
  	{
  		report.detailsAppendFolder("Verify the Paytable payouts by chainging only coinsize  , Iteration No : "+i,"Verify the bet amt , payouts, coins from bet panel by clicking on coins Plus button",""+coinsMinus,"FAIL",""+CurrencyName);
  		paytable = false;
  	} 	  
     	}
    }//closing try block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return paytable;        
}
/**
 * method validate the change in Coins in Base and in BetPanel by Clicking on BetMax Button 
 * @return
 * @author rk61073
 */
public void betMaxButtonValidation(Desktop_HTML_Report  report, String CurrencyName)
{
   
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    
           boolean minbet = setMinBet(report,CurrencyName);
           Thread.sleep(2000);
           
           if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
             {
        	   fun_Click("PaytableIcon");
             String payatbelbeforeclikingonbetmaxbutton  = fun_GetText("payout1");
             fun_Click("PaytableClose");
       		 String betbeforeclikingonbetmaxbutton  = fun_GetText("BetAmount");
               if(fun_Click("BetMaxButton"))
               {
               String betafterclikingonbetmaxbutton  = fun_GetText("BetAmount");
               fun_Click("PaytableIcon");   String payatbelafterclikingonbetmaxbutton  = fun_GetText("payout1"); fun_Click("PaytableClose");
                 
              if(!(betbeforeclikingonbetmaxbutton.equalsIgnoreCase(betafterclikingonbetmaxbutton) && !payatbelbeforeclikingonbetmaxbutton.equalsIgnoreCase(payatbelafterclikingonbetmaxbutton) ))
              {
           	   report.detailsAppendFolder("Verify payouts and bet after clicking on Bet Max Btn","Verify payouts and bet after clicking on Bet Max Btn","Payouts : true bet : true","PASS",""+CurrencyName);
              }
              else
              {
           	   report.detailsAppendFolder("Verify payouts and bet after clicking on Bet Max Btn","Verify payouts and bet after clicking on Bet Max Btn","Payouts : true bet : true","PASS",""+CurrencyName);
              }
              }
              
           }
           else
           {
			String payatbelbeforeclikingonbetmaxbutton  = fun_GetText("payout1");
    		String betbeforeclikingonbetmaxbutton  = fun_GetText("BetAmount");
            if(fun_Click("BetMaxButton"))
            {
            String betafterclikingonbetmaxbutton  = fun_GetText("BetAmount");
            String payatbelafterclikingonbetmaxbutton  = fun_GetText("payout1");
              
           if(!(betbeforeclikingonbetmaxbutton.equalsIgnoreCase(betafterclikingonbetmaxbutton) && !payatbelbeforeclikingonbetmaxbutton.equalsIgnoreCase(payatbelafterclikingonbetmaxbutton) ))
           {
        	   report.detailsAppendFolder("Verify payouts and bet after clicking on Bet Max Btn","Verify payouts and bet after clicking on Bet Max Btn","Payouts : true bet : true","PASS",""+CurrencyName);
           }
           else
           {
        	   report.detailsAppendFolder("Verify payouts and bet after clicking on Bet Max Btn","Verify payouts and bet after clicking on Bet Max Btn","Payouts : true bet : true","PASS",""+CurrencyName);
           }
           }
           }
           
       	boolean  deal =  fun_Click("DealButton");
           if(deal)
           {
        	   report.detailsAppendFolder("Verify betmax functionality by clicking on deal button","Verify betmax functionality by clicking on deal button",""+deal,"PASS",""+CurrencyName);
           }
           else
           {
        	   report.detailsAppendFolder("Verify betmax functionality by clicking on deal button","Verify betmax functionality by clicking on deal button",""+deal,"FAIL",""+CurrencyName);
           }
           
	        if(newConsoleButtonsAreDisabled(report,CurrencyName))
			{
				System.out.println("newConsoleButtonsAreDisabled : PASS");log.debug("newConsoleButtonsAreDisabled : PASS");
			}
			else 
			{
				System.out.println("newConsoleButtonsAreDisabled : FAIL");log.debug("newConsoleButtonsAreDisabled : FAIL");
			} 
          
        	boolean drawclick = fun_Click("DrawButton");
        	Thread.sleep(8000);         
        	boolean doubleto = checkAvilabilityofElement("IsDoubleTOButtonEnabled");
			boolean collect = checkAvilabilityofElement("IsCollectButtonEnabled");
			boolean coins =	isCoinsPlusAndMinusButtonsAvailableAndDisabled(report,CurrencyName)	;
			boolean quickdeal =	 isQuickDealButtonAvailableAndEnabled(report,CurrencyName);	   
			boolean betBtn =  isBetPlusAndMinusButtonsAvailableAndDisabled(report,CurrencyName);
			boolean betplusOneBtn =  isBetPlusOneButtonAvailableAndDisabled(report,CurrencyName);
			//boolean betMax = checkAvilabilityofElement("IsBetMaxButtonReplacedWithDoubleButton");
		
        	String draw = "Draw Click : "+drawclick + " Collect Btn is enabled : "+collect + " Coins Btn is disabled : "+coins + " Quick Deal Btn is enabed : "+quickdeal +" is Bet Button Disabled : "+betBtn +  " is BetPlusone Button Disabled : "+betplusOneBtn;
        	
        	if(drawclick && doubleto && collect && coins && quickdeal  && betBtn  && betplusOneBtn  )
        	{
        		report.detailsAppendFolder("Verify betmax functionality by clicking on draw button","Verify betmax functionality by clicking on draw button",""+draw,"PASS",""+CurrencyName);
        	}
        	else
        	{	
        		report.detailsAppendFolder("Verify betmax functionality by clicking on draw button","Verify betmax functionality by clicking on draw button",""+draw,"FAIL",""+CurrencyName);
        	}
     			
        	boolean totalwin = checkAvilabilityofElement("TotalWinAmount");
			boolean iscollectBtn = checkAvilabilityofElement("IsCollectButtonEnabled");
			boolean collectBtn = fun_Click("CollectButton") ;  Thread.sleep(3000);
        	boolean coinS = coinsUpdationInBaseAndInBetPanel()	;   
        	    
        	String collectButton = "TotalWin Button : "+totalwin + " Collect Btn Available: " +iscollectBtn +" Click on Collect Button :  "+collectBtn +"  Coins Updation in base and in bet panel : "+coinS;
        	if(totalwin && iscollectBtn && collectBtn && coinS)
        	{
        		report.detailsAppendFolder("Verify betmax functionality by clicking on collect button","Verify betmax functionality by clicking on collect button",""+collectButton,"PASS",""+CurrencyName);
        	}
        	else
        	{
        		report.detailsAppendFolder("Verify betmax functionality by clicking on collect button","Verify betmax functionality by clicking on collect button",""+collectButton,"FAIL",""+CurrencyName);
        	}
        	
        	if(newConsoleButtonsAreEnabled(report,CurrencyName))
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : PASS");log.debug("newConsoleButtonsAreEnabled : PASS");
	    	}
	    	else 
	    	{
	    		System.out.println("newConsoleButtonsAreEnabled : FAIL");log.debug("newConsoleButtonsAreEnabled : FAIL");
	    	} 
        	   	
    	
    }//closing try block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }         
}
/**
 * method validates the coins text in base and in bet panel 
 * @return
 */
public boolean coinsUpdationInBaseAndInBetPanel()
{
    boolean coins= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
try
{
	
	
	if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
	  {
		fun_Click("PaytableIcon");
    	String coinstext = fun_GetText("CoinsText");
    	String coinText= coinstext.replaceAll("[a-z,A-Z,]", "").replace(" :   ", "");
    	log.debug(coinText);
        System.out.println(coinText);fun_Click("PaytableClose");
        if(fun_Click("BetButton"))
        {
        String coinstextfromtotalbet = fun_GetText("CoinsTextFromTotalBet");
        closeOverlay();
        if(coinstextfromtotalbet.equalsIgnoreCase(coinText))
        {
       	 System.out.println("Coins Text in Base and in Bet Panel are same : PASS"); coins = true; 
       	 coins = true; 
        }
        else 
        {
       	 System.out.println("Coins Text in Base and in Bet Panel are same : FAIL");
       	 coins = false; 
        }}
        else 
        {
       	 System.out.println("Click on Bet Button : FAIL");
       	 coins = false; 
        }
	}
    	else
	    {  
	    String coinstext = fun_GetText("CoinsText");
	    if(coinstext.contains("COINS: "))
	    {
	    	String coinText= coinstext.replaceAll("[a-z,A-Z,]", "").replace(":  ", "");
	        System.out.println(coinText);log.debug(coinText);
	        if(fun_Click("BetButton"))
	        {
	        String coinstextfromtotalbet = fun_GetText("CoinsTextFromTotalBet");
	        closeOverlay();
	        if(coinstextfromtotalbet.equalsIgnoreCase(coinText))
	        {
	       	 System.out.println("Coins Text in Base and in Bet Panel are same : PASS"); coins = true; 
	       	 coins = true; 
	        }
	        else 
	        {
	       	 System.out.println("Coins Text in Base and in Bet Panel are same : FAIL");
	       	 coins = false; 
	        }}
	        else 
	        {
	       	 System.out.println("Click on Bet Button : FAIL");
	       	 coins = false;	
	    }}
	    else if(coinstext.contains("COINS :   "))
	    {

	    	String coinText= coinstext.replaceAll("[a-z,A-Z,]", "").replace(" :   ", "");
	        System.out.println(coinText);log.debug(coinText);
	        if(fun_Click("BetButton"))
	        {
	        String coinstextfromtotalbet = fun_GetText("CoinsTextFromTotalBet");
	        closeOverlay();
	        if(coinstextfromtotalbet.equalsIgnoreCase(coinText))
	        {
	       	 System.out.println("Coins Text in Base and in Bet Panel are same : PASS"); coins = true; 
	       	 coins = true; 
	        }
	        else 
	        {
	       	 System.out.println("Coins Text in Base and in Bet Panel are same : FAIL");
	       	 coins = false; 
	        }}
	        else 
	        {
	       	 System.out.println("Click on Bet Button : FAIL");
	       	 coins = false;	
	    }
	    }
	    else if(coinstext.contains("COINS :  "))
	    {

	    	String coinText= coinstext.replaceAll("[a-z,A-Z,]", "").replace(" :  ", "");
	        System.out.println(coinText);log.debug(coinText);
	        if(fun_Click("BetButton"))
	        {
	        String coinstextfromtotalbet = fun_GetText("CoinsTextFromTotalBet");
	        closeOverlay();
	        if(coinstextfromtotalbet.equalsIgnoreCase(coinText))
	        {
	       	 System.out.println("Coins Text in Base and in Bet Panel are same : PASS"); coins = true; 
	       	 coins = true; 
	        }
	        else 
	        {
	       	 System.out.println("Coins Text in Base and in Bet Panel are same : FAIL");
	       	 coins = false; 
	        }}
	        else 
	        {
	       	 System.out.println("Click on Bet Button : FAIL");
	       	 coins = false;	
	    }
	    }
	    else
	    {
		String coinText= coinstext.replaceAll("[a-z,A-Z,]", "").replace(" :   ", "");
        System.out.println(coinText);log.debug(coinText);
        if(fun_Click("BetButton"))
        {
        String coinstextfromtotalbet = fun_GetText("CoinsTextFromTotalBet");
        closeOverlay();
        if(coinstextfromtotalbet.equalsIgnoreCase(coinText))
        {
       	 System.out.println("Coins Text in Base and in Bet Panel are same : PASS"); coins = true; 
       	 coins = true; 
        }
        else 
        {
       	 System.out.println("Coins Text in Base and in Bet Panel are same : FAIL");
       	 coins = false; 
        }}
        else 
        {
       	 System.out.println("Click on Bet Button : FAIL");
       	 coins = false; 
        }}
	    }//closing else block 	
        
}//closing try block

catch (Exception e)
{
    log.debug(e.getMessage());
}      
return coins;        
}
/**
 * method validates the coins text in base and in bet panel 
 * @return
 */
public boolean totalBetInBaseAndInBetPanel()
{
    boolean coins= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	String betAmt = fun_GetText("BetAmount");
         if(fun_Click("BetButton"))
         {
         String totalBetFromBetPanel = fun_GetText("TotalBet");
         closeOverlay();
         
         if(betAmt.equalsIgnoreCase(totalBetFromBetPanel))
         {
        	 System.out.println(" Bet Amt Text in Base and total bet from the Bet Panel are same : PASS"); coins = true; 
        	 coins = true; 
         }
         else 
         {
        	 System.out.println(" Bet Amt Text in Base and total bet from the Bet Panel are same : FAIL");
        	 coins = false; 
         }}
         else 
         {
        	 System.out.println("Click on Bet Button : FAIL");
        	 coins = false; 
         }
}//closing try block

catch (Exception e)
{
    log.debug(e.getMessage());
}      
return coins;        
}
/**
 * method validates the coinsize text in base and in bet panel 
 * @return
 */
public boolean coinSizeUpdationInBaseAndInBetPanel()
{
    boolean coinsize= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
 {
//get text from  CoinSize
String coinsizeText = fun_GetText("CoinSizeText");

//click on bet button 
if( fun_Click("BetButton"))
{
//get text from  CoinSizeInTotalBet
String coinsizeTextFromTotalBet = fun_GetText("CoinSizeFromTotalBetText"); 
String coinSizeTextFromTotalBet= coinsizeTextFromTotalBet.replaceAll("[^.0-9]", "");
System.out.println(coinSizeTextFromTotalBet);

//compare the text values
if(coinSizeTextFromTotalBet.equalsIgnoreCase(coinsizeText))
{
	 System.out.println("Text from CoinSizeInTotalBet & CoinSize are same and Bet is updated : PASS");
	 coinsize = true;
}
else 
{
	 System.out.println("Text from CoinSizeInTotalBet & CoinSize are same and Bet is updated : FAIL");
	 coinsize = false;
}closeOverlay();
}
}//closing try block

catch (Exception e)
{
    log.debug(e.getMessage());
}      
return coinsize;        
}
/**
 * method is for no win 
 * @return
 */
public boolean noWin(Desktop_HTML_Report  report, String CurrencyName)
{
    boolean nowin= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
 {
    if(fun_Click("DealButton"))
    {
    	System.out.println("Clicked on Deal : PASS");log.debug("Clicked on Deal : PASS");
    	report.detailsAppendFolder("Deal","Clicked on Deal","Bet Amount","PASS",""+CurrencyName);
    	if(fun_Click("DrawButton"))
    	{
    		System.out.println("Clicked on Draw : PASS");log.debug("Clicked on Draw : PASS");
    		report.detailsAppendFolder("Draw","Clicked on Draw","Clicked on Draw","PASS",""+CurrencyName);
    		nowin = true;
    	}
    	else
    	{
    		System.out.println("Clicked on Draw : FAIL");log.debug("Clicked on Draw : FAIL");
    		report.detailsAppendFolder("Deal","Clicked on Deal","Bet Amount","FAIL",""+CurrencyName);
    		nowin = false;
    	}
    }
    else
    {
    	System.out.println("Clicked on Deal : FAIL");log.debug("Clicked on Deal : FAIL");
    	report.detailsAppendFolder("Draw","Clicked on Draw","Clicked on Draw","FAIL",""+CurrencyName);
    	nowin = false;
    }
    	
 }//closing try block

catch (Exception e)
{
    log.debug(e.getMessage());
}      
return nowin;        
}

/**
 * 
 * @return
 */
public boolean isBetMaxButtonAvailableAndDisabled(Desktop_HTML_Report  report, String CurrencyName)
{
    boolean betmax= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	if(checkAvilabilityofElement("BetMaxButton"))
    	{
    		log.debug("bet max is displayed");
    		if(checkAvilabilityofElement("IsBetMaxButtonDisabled"))
    		{
    			log.debug("BetMax  button is displayed & disabled : PASS");
    			betmax = true;
    		}
    		else
    		{
    			log.debug("BetMax  button is displayed & disabled : FAIL");
    			betmax = false;
    		}
    	}
    	else
    	{
    		log.debug("bet max is not displayed");
    		betmax = false;
    	}
        
    }//closing for block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return betmax;        
}
/**
 * 
 * @return
 */
public boolean isBetPlusOneButtonAvailableAndDisabled(Desktop_HTML_Report  report, String CurrencyName)
{
    boolean betmax= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	if(checkAvilabilityofElement("BetPlusOneButton"))
    	{
    		log.debug("Betplusone max is displayed");
    		if(checkAvilabilityofElement("IsBetPlusOneButtonDisabled"))
    		{
    			log.debug("BetplusOne button is displayed & disabled : PASS");	
    			betmax = true;
    		}
    		else
    		{
    			log.debug("BetplusOne button is displayed & disabled : FAIL");
    			betmax = false;
    		}
    	}
    	else
    	{
    		betmax = false;
    		log.debug("Betplusone max is not displayed");
    	}
        
    }//closing for block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return betmax;        
}
/**
 * method is for single hand
 * @return
 */
public boolean isCoinsPlusAndMinusButtonsAvailableAndDisabled(Desktop_HTML_Report  report, String CurrencyName)
{
    boolean coins= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 10);
    try
    {
  if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
  {
	  if(fun_Click("PaytableIcon"))
	    {
	     log.debug("Clicked on paytable");
	       if(checkAvilabilityofElement("CoinsPlusButton") && checkAvilabilityofElement("CoinsMinusButton"))
	       {
	    	   log.debug("Coins plus & minus buttons are displayed");
	    	   if(checkAvilabilityofElement("IsCoinsMinusButtonDisabled") && checkAvilabilityofElement("IsCoinsPlusButtonDisabled"))
	           {
	    		    if(fun_Click("PaytableClose"))
	    		    {
	    		    	 log.debug("Coins , Coins plus & Coins Minus buttons are available & disabled : PASS ");
	    		    	 coins = true;
	    		    }
	           }
	    	   else
	    	   {
	    		  log.debug("Coins , Coins plus & Coins Minus buttons are available & disabled : FAIL ");coins = true;
	    	   }
		    }
		    else
		    {
		    	log.debug("unable validate coins buttons");
		    	coins = false;
		    }
	   }
	   else
	   {
		   log.debug("unable to open paytable");coins = true;
	   } 
  }
  else
  {
	if(checkAvilabilityofElement("Coins"))
    {
     log.debug("Coins is Visible");
       if(checkAvilabilityofElement("CoinsPlusButton") && checkAvilabilityofElement("CoinsMinusButton"))
       {
    	   log.debug("Coins plus & minus buttons are displayed");
    	   if(checkAvilabilityofElement("IsCoinsMinusButtonDisabled") && checkAvilabilityofElement("IsCoinsPlusButtonDisabled"))
           {
    		    log.debug("Coins , Coins plus & Coins Minus buttons are available & disabled : PASS ");
			   coins = true;
           }
    	   else
    	   {
    		  log.debug("Coins , Coins plus & Coins Minus buttons are available & disabled : FAIL ");
    	   }
	    }
	    else
	    {
	    	log.debug("Coins plus & minus buttons are not displayed");
	    	coins = false;
	    }
   }
   else
   {
	   log.debug("Coins is not Visible");
   }
  }//closing else block
    }//closing for block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return coins;        
}
/**
 * 
 * @return
 */
public boolean isBetPlusAndMinusButtonsAvailableAndDisabled(Desktop_HTML_Report  report, String CurrencyName)
{
    boolean betbuttons= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
	if(checkAvilabilityofElement("BetButton"))
    {  
		log.debug("Bet Button is Visible");
		if(checkAvilabilityofElement("BetMinusButton") && checkAvilabilityofElement("BetPlusButton"))
	   {
			log.debug("Bet plus and minus Button are Visible");
			if(checkAvilabilityofElement("IsBetMinusButtonDisabled") &&checkAvilabilityofElement("IsBetButtonDisabled") && checkAvilabilityofElement("IsBetPlusButtonDisabled"))
			   {
			      log.debug("Bet , Bet plus & Bet Minus buttons are available & disabled : PASS");
			     betbuttons = true;
			   }
			else
			{
				log.debug("Bet , Bet plus & Bet Minus buttons are available & disabled : FAIL");
				betbuttons = false;
			}
	   }
	   else
	   {
		   log.debug("bet plus & minus buttons are displayed");
		   betbuttons = false;
	   }	
    }
    else
    {
    	log.debug("bet buttons is not displayed");
 	   
    }	   
    }//closing for block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return betbuttons;        
}
/**
 * 
 * @return
 * @author rk61073
 */
public boolean isQuickDealButtonAvailableAndEnabled(Desktop_HTML_Report  report, String CurrencyName)
{
    boolean quickspin= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 10);
    try
    {
    	if(checkAvilabilityofElement("QuickDealButton"))
    	{
    		log.debug("QuickDealButton is displayed");
    		if( checkAvilabilityofElement("IsQuickDealButtonEanbled") || checkAvilabilityofElement("IsQuickDealButtonEnabled") )
    		{
    			log.debug("QuickDealButton  is displayed & enabled : PASS");
    			quickspin = true;
    		}
    		else
    		{
    			log.debug("QuickDeal button is displayed & enabled : FAIL");
    			quickspin = false;
    		}
    	}
    	else
    	{
    		quickspin = false;
    		log.debug("Betplusone max is not displayed");
    	}
        
    }//closing for block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return quickspin;        
}
/**
 * 
 * @return
 * @author rk61073
 */
public boolean isQuickDealButtonAvailableAndDisabled(Desktop_HTML_Report  report, String CurrencyName)
{
    boolean quickspin= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	if(checkAvilabilityofElement("QuickDealButton"))
    	{
    		log.debug("QuickDealButton is displayed");
    		if(checkAvilabilityofElement("IsQuickDealButtonDisabled"))
    		{
    			System.out.println("QuickDealButton is displayed & disabled : PASS");log.debug("QuickDealButton  is displayed & disabled : PASS");
    			report.detailsAppendFolder("QuickDeal","QuickDeal button is dispalyed and disabled","QuickDeal button is dispalyed and disabled","PASS",""+CurrencyName);
    			quickspin = true;
    		}
    		else
    		{
    			System.out.println("QuickDeal button is displayed & disabled : FAIL");log.debug("QuickDeal button is displayed & disabled : FAIL");
    			report.detailsAppendFolder("QuickDeal","QuickDeal button is dispalyed and disabled","QuickDeal button is dispalyed and disabled","FAIL",""+CurrencyName);
    			quickspin = false;
    		}
    	}
    	else
    	{
    		quickspin = false;
    		log.debug("Betplusone max is not displayed");
    	}
        
    }//closing for block
        
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }      
    return quickspin;        
}
/**
 * 
 * @param report
 * @param language
 * @return
 * @author rk61073
 */
public boolean refreshTheGame(Desktop_HTML_Report report, String language)
{
	 boolean refresh= false;
	Wait = new WebDriverWait(webdriver, 40);
	try 
	{
	    webdriver.navigate().refresh();Thread.sleep(1000);
		report.detailsAppendFolder("Verify the game on refresh ","On refreshing the game","On refreshing the game","PASS",""+language);
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("DealButton")))) != null)
		{
			refresh = true;
		}
		else 
		{
			refresh = false;
		}
		
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}return refresh;
}
/**
 * method is to get the deal ,draw & Validate Total Win  amount
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public boolean clickOnDealDrawAndValidateTotalWin(Desktop_HTML_Report report, String currencyName)
{
    boolean dealDrawAndToatlWin= false;
    WebDriverWait Wait = new WebDriverWait(webdriver, 60);
    try
    {
    	boolean deal = fun_Click("DealButton");Thread.sleep(3000);	
    	boolean draw = fun_Click("DrawButton");Thread.sleep(3000);	
    	boolean totalWinAmt =fun_GetText("TotalWinAmount") != null;Thread.sleep(2000);	
    	boolean iscollectbuttonavailable = checkAvilabilityofElement("IsCollectButtonEnabled");
    	if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
		{
		Thread.sleep(2000);	
		boolean paytableClose = fun_Click("PaytableClose");Thread.sleep(2000);	
		
    	String dealDrawAndTotalWin = "Deal : "+deal +"  Draw : " +draw +"  TotalWinAmt : "+totalWinAmt +"PaytableClose : "+paytableClose +"Is collect button available and enabled : "+iscollectbuttonavailable;
    	
	    	if(deal && draw && totalWinAmt && paytableClose && iscollectbuttonavailable  )
	    	{
	    		report.detailsAppendFolder("Verify Deal, Draw,TotalWin","Verifies deal , draw and totalwin ",""+dealDrawAndTotalWin,"PASS",""+currencyName);
	    		dealDrawAndToatlWin = true;
	    	}
	    	else
	    	{
	    		report.detailsAppendFolder("Verify Deal, Draw,TotalWin","Verifies deal , draw and totalwin ",""+dealDrawAndTotalWin,"FAIL",""+currencyName);
	    		dealDrawAndToatlWin = false;
	    	}
    	}
    	else 
    	{
    		String dealDrawAndTotalWin = "Deal : "+deal +"  Draw : " +draw +"  TotalWinAmt : "+totalWinAmt ;
        	
    		if(deal && draw && totalWinAmt)
	    	{
	    		report.detailsAppendFolder("Verify Deal, Draw,TotalWin","Verifies deal , draw and totalwin ","Verifies deal , draw and totalwin  : "+dealDrawAndTotalWin,"PASS",""+currencyName);
	    		dealDrawAndToatlWin = true;
	    	}
	    	else
	    	{
	    		report.detailsAppendFolder("Verify Deal, Draw,TotalWin","Verifies deal , draw and totalwin ","Verifies deal , draw and totalwin  : "+dealDrawAndTotalWin,"FAIL",""+currencyName);
	    		dealDrawAndToatlWin = false;
	    	}	
    	}
         }//closing
    catch (Exception e)
    {
        log.debug(e.getMessage());
    }    
    
    return dealDrawAndToatlWin;        
}
/**
 * method is to get the double the amount
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public void doubleTheAmountToDouble(Desktop_HTML_Report report, String currencyName)
{
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
try
{
	while(!checkAvilabilityofElement("GambleLimitReached"))
    {
	   if(checkAvilabilityofElement("DefaultDoubleCard"))
		{	
		boolean cardclick =   fun_Click("SelectDoubleCard"); //func_Click_BYID("doubleToCard");
		 if(cardclick)
		 { Thread.sleep(1000);
			 System.out.println("Card Selection in Double TO : PASS");log.debug("Card Selection in Double TO : PASS");
			report.detailsAppendFolder("Verify the card selection in Double To feature until you reach gamble limit", "card selection in Double To featureuntil you reach gamble limit", "card selection in Double To feature until you reach gamble limit : "+cardclick, "PASS", currencyName);
			 if(checkAvilabilityofElement("GambleLimitReached")) 
			 {
				break; 
			 }
		 }//closing double to card click
		 else 
		 {
			 System.out.println("Card Selection in Double TO : FAIL");log.debug("Card Selection in Double TO : FAIL");
			 report.detailsAppendFolder("Verify the card selection in Double To feature", "card selection in Double To feature", "card selection in Double To feature : "+cardclick, "FAIL", currencyName);
		 }}//closing validating default card in double to 
	   else 
	   {
		   System.out.println("Double TO : FAIL");log.debug("Double TO : FAIL");
		   report.detailsAppendFolder("Navigate to Double TO ", "Unable to navigate to double to feature", "Unable to navigate to double to feature", "FAIL", currencyName);
	     }
	   boolean doubleToBtn = fun_Click("DoubleButton");
		  if(doubleToBtn)
		  {
			System.out.println("Click on double to button .Until you reach gamble limit : PASS"); log.debug("Click on double to button .Until you reach gamble limit : PASS");
			//report.detailsAppendFolder("Verify the Gamble limt reached by clicking on double to button", "Click on double to button .Until you reach gamble limit", "Click on double to button .Until you reach gamble limit"+doubleToBtn, "PASS", currencyName);
		  }
		  else 
		  {
			  System.out.println("Click on double to button .Until you reach gamble limit : FAIL"); log.debug("Click on double to button .Until you reach gamble limit : FAIL");
			  report.detailsAppendFolder("Verify the Gamble limt reached by clicking on double to button", "Click on double to button .Until you reach gamble limit", "Click on double to button .Until you reach gamble limit"+doubleToBtn, "FAIL", currencyName);
		  }
			}//closing while loop 
	    
	     String gamblelimitText = fun_GetText("GambleLimitReached") ;
			if (gamblelimitText != null) 
			{ Thread.sleep(2000);
				System.out.println("Gamble limit text : PASS");log.debug("Gamble limit text : PASS");
				report.detailsAppendFolder("Verify Gamble limit reached text", "Gamble limit text reached", "Gamble limit text reached : "+gamblelimitText, "PASS", currencyName);
			} else {
				System.out.println("Gamble limit text : FAIL");
				report.detailsAppendFolder("Verify Gamble limit reached text", "Gamble limit text reached", "Gamble limit text reached : "+gamblelimitText, "FAIL", currencyName);
			}
		boolean isbacktobasebuttonVisible =	checkAvilabilityofElement("IsBackToBaseGameButtonEnabled");
		boolean backtobase =	fun_Click("BackToBaseGame");
			if (isbacktobasebuttonVisible && backtobase)
			{
				if(checkAvilabilityofElement("DealButton"))
				{
					System.out.println("Back to Base Game : PASS");log.debug("Back to Base Game : PASS");
					report.detailsAppendFolder("Verify Back to Base Game after gamble limit reached", "Back to Base Game after gamble limit reached", "is back button visible : "+isbacktobasebuttonVisible+"Back to Base Game   :"+backtobase, "PASS", currencyName);
				}//closing deal button availability 
			}//closing back to base game 
			else 
			{
				System.out.println("Back to Base Game : FAIL");log.debug("Back to Base Game : FAIL");
				report.detailsAppendFolder("Verify Back to Base Game after gamble limit reached", "Back to Base Game after gamble limit reached", "is back button visible : "+isbacktobasebuttonVisible+"Back to Base Game   :"+backtobase, "FAIL", currencyName);
			}}
catch (Exception e) 
{
	log.debug(e.getMessage());
}}
/**
 * method is to get the double the amount
 * @param report
 * @param CurrencyName
 * @return
 * @author rk61073
 */
public boolean isDoubleTOButtonAndCollectButtonAvailableAndEnabled(Desktop_HTML_Report report, String currencyName)
{
	boolean doubleto = false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
try
{
if(checkAvilabilityofElement("IsDoubleTOButtonEnabled") && checkAvilabilityofElement("IsCollectButtonEnabled"))
{
	  System.out.println("BetMinus,Bet,BetPlus & BetPlusOne Button : PASS");doubleto = true;
}
	else
	{
		System.out.println("BetMinus,Bet,BetPlus & BetPlusOne Button : FAIL");doubleto = false;
	}
}
catch (Exception e) 
{
	log.debug(e.getMessage());
}return doubleto;}
/**
 * validate the buttons enabled on refresh or not 
 * @param report
 * @param currencyName
 * @return
 * @author rk61073
 */

public boolean newConsoleButtonsAreEnabledOnRefresh(Desktop_HTML_Report report, String currencyName)
{
	boolean buttonsenabled = false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
try
{
	if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
	{
		boolean quickdealbtn= checkAvilabilityofElement("IsQuickButtonEnabledOnRefresh");
		boolean coinsizeminubtn =checkAvilabilityofElement("IsCoinsizeMinusButtonEnabledOnRefresh");
		boolean coinsizebtn = checkAvilabilityofElement("IsCoinsizeButtonEnabledOnRefresh");
		boolean coinsizeplusubtn  = checkAvilabilityofElement("IsCoinsizePlusButtonEnabledOnRefresh");
		boolean betplusonebtn  = checkAvilabilityofElement("IsBetPlusOneButtonEnabledOnRefresh");
		boolean betmaxbtn  = checkAvilabilityofElement("IsBetMaxButtonEnabledOnRefresh");
		boolean paytableOpen  =  fun_Click("PaytableIcon");
		boolean coinsminusbtn  =checkAvilabilityofElement("IsCoinsMinusButtonEnabledOnRefresh");
		boolean coinsplusbtn =checkAvilabilityofElement("IsCoinsPlusButtonEnabledOnRefresh") ;
		boolean paytableClose  = fun_Click("PaytableClose");

		String expected = "Quick Deal Button : "+quickdealbtn +"   CoinSize Minus Button : " +coinsizeminubtn + "  Bet Button : "+coinsizebtn + "    CoinSize Plus Button : " +coinsizeminubtn + "   Bet Plus One Button : " +betplusonebtn + "   Bet Max Button : "+" Paytable Open : "+paytableOpen+ betmaxbtn + "   Coins Minus Button : " + coinsminusbtn+"   Coins Minus Button :  " + coinsplusbtn+" Paytable Close : "+paytableClose ;
			if(quickdealbtn && coinsizeminubtn && coinsizebtn && coinsizeplusubtn && betplusonebtn && betmaxbtn && coinsminusbtn && coinsplusbtn )
			{
				report.detailsAppendFolder("Verify New Console buttons","Verify the quick deal button,coinsize plus button,coinsize minus button,coins plus button,coins minus button,bet max button ,bet plus one button are enabled ",""+expected,"PASS",""+currencyName);
				buttonsenabled = true;
			}
			else 
			{
				report.detailsAppendFolder("Verify New Console buttons","Verify the quick deal button,coinsize plus button,coinsize minus button,coins plus button,coins minus button,bet max button ,bet plus one button are enabled ",""+expected,"FAIL",""+currencyName);
				buttonsenabled = false;
			}
 }
	else
	{
		boolean quickdealbtn= checkAvilabilityofElement("IsQuickButtonEnabledOnRefresh");
		boolean coinsizeminubtn =checkAvilabilityofElement("IsCoinsizeMinusButtonEnabledOnRefresh");
		boolean coinsizebtn = checkAvilabilityofElement("IsCoinsizeButtonEnabledOnRefresh");
		boolean coinsizeplusubtn  = checkAvilabilityofElement("IsCoinsizePlusButtonEnabledOnRefresh");
		boolean betplusonebtn  = checkAvilabilityofElement("IsBetPlusOneButtonEnabledOnRefresh");
		boolean betmaxbtn  = checkAvilabilityofElement("IsBetMaxButtonEnabledOnRefresh");
		boolean coinsminusbtn  =checkAvilabilityofElement("IsCoinsMinusButtonEnabledOnRefresh");
		boolean coinsplusbtn =checkAvilabilityofElement("IsCoinsPlusButtonEnabledOnRefresh") ;

String expected = "Quick Deal Button : "+quickdealbtn +"   CoinSize Minus Button : " +coinsizeminubtn + "  Bet Button : "+coinsizebtn + "    CoinSize Plus Button : " +coinsizeminubtn + "   Bet Plus One Button : " +betplusonebtn + "   Bet Max Button : "+ betmaxbtn + "   Coins Minus Button : " + coinsminusbtn+"   Coins Minus Button :  " + coinsplusbtn ;
	if(quickdealbtn && coinsizeminubtn && coinsizebtn && coinsizeplusubtn && betplusonebtn && betmaxbtn && coinsminusbtn && coinsplusbtn )
	{
		report.detailsAppendFolder("Verify New Console buttons","Verify the quick deal button,coinsize plus button,coinsize minus button,coins plus button,coins minus button,bet max button ,bet plus one button are enabled ",""+expected,"PASS",""+currencyName);
		buttonsenabled = true;
	}
	else 
	{
		report.detailsAppendFolder("Verify New Console buttons","Verify the quick deal button,coinsize plus button,coinsize minus button,coins plus button,coins minus button,bet max button ,bet plus one button are enabled ",""+expected,"FAIL",""+currencyName);
		buttonsenabled = false;
	}}
}
catch (Exception e) 
{
	log.debug(e.getMessage());
}return buttonsenabled;	
}

/**
 * method is to validate the buttons are disabled are not 
 * @param report
 * @param currencyName
 * @return
 * @author rk61073
 */

public boolean newConsoleButtonsAreDisabled(Desktop_HTML_Report report, String currencyName)
{
	boolean buttonsDisabled = false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
try
{
boolean betPlusoneBtn= isBetPlusOneButtonAvailableAndDisabled(report,currencyName);
boolean betMaxBtn =isBetMaxButtonAvailableAndDisabled(report,currencyName);
boolean coinsBtn = isCoinsPlusAndMinusButtonsAvailableAndDisabled(report,currencyName);
boolean betBnt  = isBetPlusAndMinusButtonsAvailableAndDisabled(report,currencyName);
boolean quickDeal  = isQuickDealButtonAvailableAndEnabled(report,currencyName);//checkAvilabilityofElement("IsQuickButtonEnabledOnRefresh");

String buttons = "BetPlusOne Button : "+betPlusoneBtn +"  BetMax Button : " +betMaxBtn + "  Coins Butons : "+coinsBtn + "  Bet Buttons : " +betBnt + "  Quick Deal Button : " +quickDeal  ;
	if(betPlusoneBtn && betMaxBtn && coinsBtn && betBnt && quickDeal )
	{
		report.detailsAppendFolder("Verify New Console buttons","Verify the quick deal button is enabled and coinsize plus button,coinsize minus button,coins plus button,coins minus button,bet max button ,bet plus one button are disabled ",""+buttons,"PASS",""+currencyName);
		buttonsDisabled = true;
	}
	else 
	{
		report.detailsAppendFolder("Verify New Console buttons","Verify the quick deal button is enabled and coinsize plus button,coinsize minus button,coins plus button,coins minus button,bet max button ,bet plus one button are disabled ",""+buttons,"FAIL",""+currencyName);
		buttonsDisabled = false;
	}
}
catch (Exception e) 
{
	log.debug(e.getMessage());
}return buttonsDisabled;	
}
/**
 * method is to validate the buttons are disabled are not 
 * @param report
 * @param currencyName
 * @return
 * @author rk61073
 */

public boolean newConsoleButtonsAreEnabled(Desktop_HTML_Report report, String currencyName)
{
	boolean buttonsEnabled = false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 60);
try
{
boolean betPlusoneBtn= isBetPlusOneButtonAvailableAndEnabled(report,currencyName); 
boolean betMaxBtn =isBetMaxButtonAvailableAndEnabled(report,currencyName);
boolean coinsBtn = isCoinsPlusAndMinusButtonsAvailableAndEnabled(report,currencyName);
boolean betBnt  = isBetPlusAndMinusButtonsAvailableAndEnabled(report,currencyName);
boolean quickDeal  = isQuickDealButtonAvailableAndEnabled(report,currencyName);

String buttons = "BetPlusOne Button : "+betPlusoneBtn +"  BetMax Button : " +betMaxBtn + "  Coins Butons : "+coinsBtn + "  Bet Buttons : " +betBnt + "  Quick Deal Button : " +quickDeal  ;

	if(betPlusoneBtn && betMaxBtn && coinsBtn && betBnt && quickDeal)
	{
		report.detailsAppendFolder("Verify New Console buttons","Verify the quick deal button , coinsize plus button,coinsize minus button,coins plus button,coins minus button,bet max button ,bet plus one button are enabled ",""+buttons,"PASS",""+currencyName);
		buttonsEnabled = true;
	}
	else 
	{
		report.detailsAppendFolder("Verify New Console buttons","Verify the quick deal button , coinsize plus button,coinsize minus button,coins plus button,coins minus button,bet max button ,bet plus one button are enabled ",""+buttons,"FAIL",""+currencyName);
		buttonsEnabled = false;
	}
}
catch (Exception e) 
{
	log.debug(e.getMessage());
}return buttonsEnabled;	
}
/**
 * method is to validate the buttons are disabled are not 
 * @param report
 * @param currencyName
 * @return
 * @author rk61073
 */

public boolean newConsoleButtonsAreEnabledOnBacktoBaseGameFromDoubleToScreen(Desktop_HTML_Report report, String currencyName)
{
	boolean buttonsEnabled = false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 10);
try
{
boolean betPlusoneBtn= isBetPlusOneButtonAvailableAndEnabled(report,currencyName); 
boolean betMaxBtn =isBetMaxButtonAvailableAndEnabled(report,currencyName);
boolean coinsBtn = isCoinsPlusAndMinusButtonsAvailableAndEnabled(report,currencyName);
boolean betMinusBtn  = checkAvilabilityofElement("IsBetMinusButtonEnabledOnBacktoBaseGameFromDoubleToScreen");
boolean betBtn  = checkAvilabilityofElement("IsBetButtonEnabledOnBacktoBaseGameFromDoubleToScreen");
boolean betPlusBtn  = checkAvilabilityofElement("IsBetPlusButtonEnabledOnBacktoBaseGameFromDoubleToScreen");
boolean quickDealBtn  = checkAvilabilityofElement("IsQuickDealButtonEnabledOnBacktoBaseGameFromDoubleToScreen");

String buttons = "BetPlusOne Button : "+betPlusoneBtn +"  BetMax Button : " +betMaxBtn + "  Coins Butons : "+coinsBtn + "  Bet Minus Buttons : " +betMinusBtn  + "  Bet Buttons : " +betBtn + "  Bet Plus Buttons : " +betPlusBtn +"  Quick Deal Button : " +quickDealBtn  ;

	if(betPlusoneBtn && betMaxBtn && coinsBtn && betMinusBtn && betBtn && betPlusBtn && quickDealBtn)
	{
		report.detailsAppendFolder("Verify New Console buttons on back to base game from Double to screen","Verify the quick deal button , coinsize plus button,coinsize minus button,coins plus button,coins minus button,bet max button ,bet plus one button are enabled on back to base game from Double to screen ",""+buttons,"PASS",""+currencyName);
		buttonsEnabled = true;
	}
	else 
	{
		report.detailsAppendFolder("Verify New Console buttons on back to base game from Double to screen","Verify the quick deal button , coinsize plus button,coinsize minus button,coins plus button,coins minus button,bet max button ,bet plus one button are enabled on back to base game from Double to screen ",""+buttons,"FAIL",""+currencyName);
		buttonsEnabled = false;
	}
}
catch (Exception e) 
{
	log.debug(e.getMessage());
}return buttonsEnabled;	
}
/**
 * method is to validate the status ok button 
 * @param report
 * @param currencyName
 * @return
 * @author rk61073
 */

public boolean verifyStatsOkButton(Desktop_HTML_Report report, String currencyName)
{
	boolean stats = false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 10);
try
{
	boolean statsbutton = fun_Click("StatsButton");
	if(statsbutton)
	{
		report.detailsAppendFolder("Verify Status button - General ","Verify Status button - General "," Status button - General :  "+statsbutton,"PASS",""+currencyName);
		Thread.sleep(2000);
		log.debug("Clicked on click on stats sysmbol ");
		
		boolean winsummary  = fun_Click("WinSummaryButtonInStats");
		if(winsummary)
		{
			report.detailsAppendFolder("Verify Status button - Win Summary ","Verify Status button - Win Summary and click on OK button  ","Status button - Win Summary : "+winsummary,"PASS",""+currencyName);
		
		if(fun_Click("GenaeralButtonInStats") && fun_Click("StatsOkButton"))
		{
			log.debug("Clicked on stats sysmbol and clicked on ok ");
			stats= true;
		}
		else
		{
			log.debug("Unableto click on stats sysmbol and clicked on ok ");
			report.detailsAppendFolder("Verify Status ok button ","Unable to click on Status ok button ","Unable to click on Status ok button : fail ","FAIL",""+currencyName);
			stats= false;
		}}
		else
		{
			report.detailsAppendFolder("Verify Status button - Win Summary ","Verify Status button - Win Summary and click on OK button  ","Status button - Win Summary : "+winsummary,"FAIL",""+currencyName);stats= false;
		}
	}
	else
	{
		log.debug("Unableto click on stats sysmbol ");
		report.detailsAppendFolder("Verify Status button - General ","Verify Status button - General "," Status button - General :  "+statsbutton,"FAIL",""+currencyName);stats= false;
	}
}
catch (Exception e) 
{
	log.debug(e.getMessage());
}return stats;	
}/**
 * method is to validate the status reset button 
 * @param report
 * @param currencyName
 * @return
 * @author rk61073
 */

public boolean verifyStatsResetButton(Desktop_HTML_Report report, String currencyName)
{
	boolean stats = false;
	WebDriverWait Wait = new WebDriverWait(webdriver, 10);
try
{ 
	boolean statsButton = fun_Click("StatsButton");
	if(statsButton)
	{
		log.debug("Clicked on stats symbol: PASS");
		report.detailsAppendFolder("Verify Status button - General ","Verify Status button - General before resetting "," Status button - General :  "+statsButton,"PASS",""+currencyName);Thread.sleep(2000);
		
		boolean winsummary  = fun_Click("WinSummaryButtonInStats");
		if(winsummary)
		{
			report.detailsAppendFolder("Verify Status button - Win Summary ","Verify Status button - Win Summary before resetting  ","Status button - Win Summary : "+winsummary,"PASS",""+currencyName);
		
		if(fun_Click("StatsResetButton"))
		{
			log.debug("Clicked on stats symbol by clciking on reset button : PASS");
			report.detailsAppendFolder("Verify Status button - Win Summary on Reset ","Verify Status button - Win Summary after resetting  ","Status button - Win Summary : "+winsummary,"PASS",""+currencyName);
			
			boolean statsbutton = fun_Click("GenaeralButtonInStats");
			if(statsbutton)
			{
				report.detailsAppendFolder("Verify Status button - General  on Reset ","Verify Status button - General after resetting "," Status button - General :  "+statsbutton,"PASS",""+currencyName);
				if(fun_Click("StatsOkButton"))
				{
				stats = true;
				}
				else
				{
					stats = false;
					report.detailsAppendFolder("Verify Status ok button ","Unable to click on Status ok button ","Unable to click on Status ok button : fail ","FAIL",""+currencyName);
				}
			}
			else
			{
				report.detailsAppendFolder("Verify Status button - General on rest ","Verify Status button - General after resetting "," Status button - General :  "+statsbutton,"FAIL",""+currencyName);
				stats = false;
			}
		}
		else
		{
			log.debug("Unbale to click on stats symbol by clciking on reset button : FAIL");
			report.detailsAppendFolder("Verify Status button - Win Summary  on Reset ","Verify Status button - Win Summary after resetting  ","Status button - Win Summary : "+winsummary,"FAIL",""+currencyName);
			stats = false;
		}}
		else
		{
			report.detailsAppendFolder("Verify Status button - Win Summary  ","Verify Status button - Win Summary before restting ","Status button - Win Summary : "+winsummary,"FAIL",""+currencyName);stats = false;
		}
	}
	else
	{
		log.debug("Unbale to click on stats symbol: FAIL");
		report.detailsAppendFolder("Verify Status button - General ","Verify Status button - General before resetting "," Status button - General :  "+statsButton,"FAIL",""+currencyName);stats = false;
	}
}
catch (Exception e) 
{
	log.debug(e.getMessage());
}return stats;	
}

/**
 * method validates the held startegy 
 * @return
 * @author rk61073
 */
public void verifyHeldCardsAndClick()
{
	try
	{
		if(checkAvilabilityOfElement("CardOneheld"))
		{
			System.out.println("The card1 is held");log.debug("The card1 is held");
		}
		else
		{
			fun_Click("Card1");
		}
		
		 if(checkAvilabilityOfElement("CardTwoheld"))
		{
			 System.out.println("The card2 is Unheld");log.debug("The card2 is Unheld");
		}
		else
		{
			fun_Click("Card2");
		}
		 
		if(checkAvilabilityOfElement("CardThreeheld"))
		{
			System.out.println("The card3 is Unheld");log.debug("The card3 is Unheld");
		}
		else
		{
			fun_Click("Card3");
		}
		
		 if(checkAvilabilityOfElement("CardFourheld"))
		{
			 System.out.println("The card 4 is Unheld");log.debug("The card4 is Unheld");
		}
		else
		{
			fun_Click("Card4");
		}
		 
		if (checkAvilabilityOfElement("CardFiveheld"))
		{
			System.out.println("The card5 is Unheld");log.debug("The card5 is Unheld");
		}
		else
		{
			fun_Click("Card5");
		}
		 
}
catch (Exception e) 
{
	log.debug(e.getMessage());
}
}
/**
 * method is used to get text by using attributevalue
 */
public String func_GetTextbyAttribute(String locator,String attributeValue) 
{
	try 
	{
		WebElement ele = webdriver.findElement(By.xpath(XpathMap.get(locator)));
		System.out.println(""+ele.getAttribute(attributeValue));log.debug(ele.getAttribute(attributeValue));
		return ele.getAttribute(attributeValue);
		
	} 
	catch (NoSuchElementException e)
	{
		//log.error(e.getMessage(), e);
		return null;
	}
}
@Override
/**
 * 
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean dealBtn() throws InterruptedException
{
	WebDriverWait wait;
	boolean deal = false;
	try 
	{
	   wait = new WebDriverWait(webdriver, 30);
		log.debug("Finding deal button on the page");
		wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("deal"))));
		webdriver.findElement(By.id(XpathMap.get("deal"))).click();
		log.debug("Successfully clicked on Deal button");
		log.debug("Finding draw button on the page");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("draw"))));
		deal = true;
	} 
	catch (Exception e)
	{
		System.out.println("Error while clicking on deal button : "+e);
		log.error("Error while clicking on deal button", e);
	}
	return deal;
}
@Override
/**
 * 
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean drawBtn() throws InterruptedException 
{
	WebDriverWait wait;
	boolean deal = false;
	try {
		wait = new WebDriverWait(webdriver, 60);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("draw"))));
		webdriver.findElement(By.id(XpathMap.get("draw"))).click();
		Thread.sleep(1000);
		log.debug("Successfully clicked on draw button");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("collectBtn"))));
		webdriver.findElement(By.id("moduleContent")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("collectBtn"))));
		Thread.sleep(3000);
		deal = true;
		
		/*****************Click on paytable close button **********/
		if(XpathMap.get("isPaytableButtonAvailable").equalsIgnoreCase("yes"))
		{
		if(payTableClose())
		{
			log.debug("Click on paytable close button : PASS");deal = true;
		}
		else 
		{	
			System.out.println("Click on paytable close button : FAIL");log.debug("Click on paytable close button : FAIL");deal = false;
		}}
		

	} catch (Exception e)
	{
		System.out.println("Error while clicking on draw button : "+e);
		log.error("Error while clicking on draw button", e);
	}
	return deal;
}
@Override
/**
 * 
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean drawButton() throws InterruptedException 
{
	WebDriverWait wait;
	boolean deal = false;
	try 
	{
		wait = new WebDriverWait(webdriver, 60);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("draw"))));
		webdriver.findElement(By.id(XpathMap.get("draw"))).click();
		Thread.sleep(1000);
		log.debug("Successfully clicked on draw button");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("collectBtn"))));
		webdriver.findElement(By.id("moduleContent")).click();
		Thread.sleep(2000);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("collectBtn"))));
		Thread.sleep(3000);
		deal = true;
     }
   catch (Exception e)
	{
		System.out.println("Error while clicking on draw button : "+e);
		log.error("Error while clicking on draw button", e);
	}
	return deal;
}
@Override
/**
 * 
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean collectBtn() throws InterruptedException 
{
	WebDriverWait wait;
	boolean collect = false;
	try {
		wait = new WebDriverWait(webdriver, 60);
		webdriver.findElement(By.id("moduleContent")).click();
		Thread.sleep(2000);
    	wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("collectBtn"))));
		webdriver.findElement(By.id(XpathMap.get("collectBtn"))).click();
		Thread.sleep(1000);
		log.debug("Successfully clicked on collectBtn button");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("deal"))));
		log.debug("Finding deal button on the page");
		collect = true;
	} catch (Exception e)
	{
		System.out.println("Error while clicking on collect button : "+e);
		log.error("Error while clicking on collect button", e);
	}
	return collect;
}
/**
 * 
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean doubleBtn() throws InterruptedException 
{
	boolean doubleBtn = false;
	try 
	{
		checkAvilabilityofElement("IsCollectButtonEnabled");	
		checkAvilabilityofElement("DoubleButton");	
		if(fun_Click("DoubleButton"))
		{
			log.debug("Clicked on double button");
			doubleBtn = true;
		}
		else 
		{
			System.out.println("Unable to clicked on double button");log.debug("Unable to clicked on double button");
			doubleBtn = false;
		}
		checkAvilabilityofElement("DefaultDoubleCard");	
		
	} catch (Exception e)
	{
		System.out.println("Error while clicking on collect button : "+e);
		log.error("Error while clicking on collect button", e);
	}
	return doubleBtn;
}
@Override
/**
 * 
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean noWin() throws InterruptedException 
{
	WebDriverWait wait;
	boolean deal = false;
	try {
		wait = new WebDriverWait(webdriver, 60);
		wait.until(ExpectedConditions.elementToBeClickable(By.id(XpathMap.get("deal"))));
		log.debug("Finding deal button on the page");
		webdriver.findElement(By.id(XpathMap.get("deal"))).click();
		log.debug("Successfully clicked on Deal button");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("draw"))));
		log.debug("Finding draw button on the page");
		webdriver.findElement(By.id(XpathMap.get("draw"))).click();
		Thread.sleep(1000);
		log.debug("Successfully clicked on draw button");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(XpathMap.get("deal"))));
		Thread.sleep(2000);webdriver.findElement(By.id("moduleContent")).click();
		log.debug("Finding deal button on the page");
		deal = true;

	} catch (Exception e) 
	{
		System.out.println("Error while clicking on draw button : "+e);
		log.error("Error while clicking on draw button", e);
	}
	return deal;
}
/**
 * 
 * @return
 * @throws InterruptedException
 * @author rk61073
 */
public boolean payTableClose() throws InterruptedException 
{
	WebDriverWait wait;
	boolean paytable = false;
	try 
	{		
		//checkAvilabilityofElement("IsCollectButtonEnabled");	
		checkAvilabilityofElement("PaytableClose");
		if(fun_Click("PaytableClose"))
		{
		   log.debug("Click on paytableclose button : PASS ");
		   paytable = true;
		}
		else
		{
			System.out.println("Click on paytableclose button : FAIL ");
			log.debug("Click on paytableclose button : FAIL ");
			paytable = false;
		}
	}
	catch (Exception e) 
	{
		System.out.println("Error while clicking on paytableclose button : "+e);
		log.error("Error while clicking on paytableclose button", e);
	}
	return paytable;
}
/**wait for deal button
 * @author rk61073
 */
public void waitForDealButton() 
{
	Wait = new WebDriverWait(webdriver, 600);
	try 
	{
		log.debug("Waiting for base scene");
		Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("DealButton"))));
	} 
	catch (Exception e) 
	{
		log.error("error while waiting for base scene", e);
	}
}
/**
 * 
 * @param report
 * @param language
 * @return
 * @author rk61073
 */
public boolean gameLogo(Desktop_HTML_Report report, String language)
{
	 boolean refresh= false;
	Wait = new WebDriverWait(webdriver, 40);
	try 
	{
	    webdriver.navigate().refresh();
		report.detailsAppendFolder("Verify the game logo ","Game logo","Game logo","PASS",""+language);
		if(Wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(XpathMap.get("DealButton")))) != null)
		{
			refresh = true;
		}
		else 
		{
			refresh = false;
		}
		
	}
	catch (Exception e) 
	{
		log.debug(e.getMessage());
	}return refresh;
}
/**validate stats for lvc
 * @author rk61073
 */
public void validateStatsForLVC(Desktop_HTML_Report report,String regExprNoSymbol,  String CurrencyName) 
{
	try 
	{
		  boolean stats = fun_Click("StatsButton"); Thread.sleep(2000);
		  boolean statsFirstHightestWin = verifyRegularExpression(report,regExprNoSymbol,fun_GetText("StatsFirstHightestWin"));
		  boolean statsSecondHightestWin = verifyRegularExpression(report,regExprNoSymbol,fun_GetText("StatsSecondHightestWin"));
		  boolean statsThirdHightestWin = verifyRegularExpression(report,regExprNoSymbol,fun_GetText("StatsThirdHightestWin"));
		  String highestWinsFromStats = "First higest win from stats : "+statsFirstHightestWin +"Second higest win from stats : "+statsSecondHightestWin+"Third higest win from stats :  "+statsThirdHightestWin;
		  if(stats && statsFirstHightestWin && statsSecondHightestWin && statsThirdHightestWin )
		  {
			log.debug("Validate all three highest wins from stats    : PASS");
			report.detailsAppendFolder("Validate all three highest wins from stats", "All three highest wins from the stats", "All three highest wins from the stats", "PASS", CurrencyName);
			resizeBrowser(600, 800);
			Thread.sleep(2000);
			report.detailsAppendFolder("Validate all three highest wins from the stats", "Resizing the browser to 600 X 800", "Resizing the browser to 600 X 800", "PASS", CurrencyName);
			resizeBrowser(400,600);
			Thread.sleep(2000);
			report.detailsAppendFolder("Validate all three highest wins from the stats", "Resizing the browser to 400 X 600", "Resizing the browser to 400 X 600", "PASS", CurrencyName);
			webdriver.manage().window().maximize();Thread.sleep(2000);
			fun_Click("StatsOkButton");
		 }
		 else
		 {
			 log.debug(highestWinsFromStats);System.out.println(highestWinsFromStats);
			 report.detailsAppendFolder("Validate all three highest wins from stats", "All three highest wins from the stats", ""+highestWinsFromStats, "FAIL", CurrencyName);
		 }
	} 
	catch (Exception e) 
	{
		log.error("error while waiting for base scene", e);
	}
}
}
