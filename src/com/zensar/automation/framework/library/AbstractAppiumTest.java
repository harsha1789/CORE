package com.zensar.automation.framework.library;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumDriver;
/* Appium test  class
 * Implemented appium operations  for diffrent scenarios 
 * */
public class AbstractAppiumTest {
	Logger log = Logger.getLogger(AbstractAppiumTest.class.getName());
	public AbstractAppiumTest(AppiumDriver<WebElement> driver1)
	{
		driver=driver1;
	}
	AppiumDriver<WebElement> driver;
	static   final int SHORTSLEEP = 1;
	static  final int MEDIUM_SLEEP = 5;
	static  final int LONG_SLEEP = 10;
	long timeDifferenceStartTest;
	long startTime;
	int defaultWaitTime = 60;

	int counter = 0;
	int retryCounter = 1;
	String searchedImage;
	File userDir = new File(System.getProperty("user.dir"));

	File[] matches = userDir.listFiles((dir, name) -> name.startsWith("application"));

	String screenshotsFolder = "";
	String platformName = "Android";
	String automationName = "Appium";
	String deviceName = "83af383147464c4a"; 
	String udid = "83af383147464c4a";
	String platformVersion =  "5.1.1";
	// Set to false to autoDismiss
	boolean autoAccept = true;
	boolean idevicescreenshotExists = false;


	public  void takeScreenshot(String screenshotName) throws IOException, InterruptedException  {
		takeScreenshot(screenshotName, true);
	}

	public void takeScreenshot(String screenshotName, boolean newStep) throws IOException, InterruptedException {
		if (idevicescreenshotExists) {
			// Keep Appium session alive between multiple non-driver screenshots
			driver.manage().window().getSize();
		}
		timeDifferenceStartTest = (int) ((System.nanoTime() - startTime) / 1e6 / 1000);
		long startTimeNew = System.nanoTime();

		if (newStep) {
			counter = counter + 1;
			retryCounter = 1;
		} else {
			retryCounter = retryCounter + 1;
		}

		screenshotsFolder = "ImageScreenshot//Mobile//";
		File dir = new File(screenshotsFolder);
		dir.mkdirs();
		searchedImage = screenshotsFolder + getScreenshotsCounter() + "_" + screenshotName + getRetryCounter() + "_" + timeDifferenceStartTest + "sec";
		String fullFileName = System.getProperty("user.dir") + File.separator + searchedImage + ".png";

		if (platformName.equalsIgnoreCase("iOS") && idevicescreenshotExists) {
			String[] cmd = new String[]{"idevicescreenshot", "-u", udid, fullFileName};
			Process p = Runtime.getRuntime().exec(cmd);
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = in.readLine()) != null)
				log.debug(line);

			int exitVal = p.waitFor();
			if (exitVal != 0) {
				log.debug("idevicescreenshot process exited with value: " + exitVal);
			}
			cmd = new String[]{"sips", "-s", "format", "png", fullFileName, "--out", fullFileName};
			p = Runtime.getRuntime().exec(cmd);
			exitVal = p.waitFor();
			if (exitVal != 0) {
				log.debug("sips process exited with value: " + exitVal);
			}
		} else {
			// idevicescreenshot not available, using driver.getScreenshotAs()
			File scrFile =  driver.getScreenshotAs(OutputType.FILE);
			try {
				File testScreenshot = new File(fullFileName);
				FileUtils.copyFile(scrFile, testScreenshot);
				log.debug("Screenshot stored to {}"+ testScreenshot.getAbsolutePath());
				return;
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
		}

		long endTime = System.nanoTime();
		int difference = (int) ((endTime - startTimeNew) / 1e6 / 1000);
		log.debug("==> Taking a screenshot took " + difference + " secs.");
	}

	//On a test run on the local machine this method will save the Reports folder in different folders on every test run.
	public  void savePreviousRunReports() {
		long millis = System.currentTimeMillis();
		File dir = new File("./target/reports");
		File newName = new File("./target/reports" + millis);
		if (dir.isDirectory()) {
			if(dir.renameTo(newName))
				log.info("Directory renamed");
		} else {
			dir.mkdir();
			if(dir.renameTo(newName))
				log.info("Directory renamed");
		}
	}

	public  boolean idevicescreenshotCheck() throws IOException, InterruptedException {
		String[] cmd = new String[]{"idevicescreenshot", "--help"};
		int exitVal = -1;
		try {
			Process p = Runtime.getRuntime().exec(cmd);
			exitVal = p.waitFor();
		} catch (IOException e) {
			log.debug(e.toString());
		}
		if (exitVal == 0) {
			log.debug("idevicescreenshot exited with value: " + exitVal + ". Using it for screenshots.");
			idevicescreenshotExists = true;
		} else {
			log.debug("idevicescreenshot process exited with value: " + exitVal + ". Won't be using it for screenshots.");
			idevicescreenshotExists = false;
		}
		return idevicescreenshotExists;
	}

	public  String ideviceinfoCheck(String key) throws IOException, InterruptedException {
		String[] cmd = new String[]{"ideviceinfo", "--key", key};
		int exitVal = -1;
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		String output = "";
		while ((line = in.readLine()) != null) {
			log.debug(line);
			output = line;
		}
		exitVal = p.waitFor();
		if (exitVal != 0) {
			log.debug("ideviceinfo process exited with value: " + exitVal);
		}
		return output;
	}

	public  void ideviceinstall(String appPath) throws IOException, InterruptedException {
		String[] cmd = new String[]{"ideviceinstaller", "-i", appPath};
		int exitVal = -1;
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			log.debug(line);
		}
		exitVal = p.waitFor();
		if (exitVal != 0) {
			log.debug("ideviceinstaller process exited with value: " + exitVal);
		}
	}

	public  void ideviceuninstall(String bundleId) throws IOException, InterruptedException {
		String[] cmd = new String[]{"ideviceinstaller", "-U", bundleId};
		int exitVal = -1;
		Process p = Runtime.getRuntime().exec(cmd);
		BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = in.readLine()) != null) {
			log.debug(line);
		}
		exitVal = p.waitFor();
		if (exitVal != 0) {
			log.debug("ideviceinstaller process exited with value: " + exitVal);
		}
	}

	//Will count the screenshots taken for separate stages of the test.
	public  String getScreenshotsCounter() {
		if (counter < 100) {
			if (counter < 10) {
				return "00" + counter;
			} else {
				return "0" + counter;
			}
		} else {
			return Integer.toString(counter);
		}
	}

	//Will count the number of times we tried to find the same image. When this counter goes up, the screenshot counter remains the same.
	String getRetryCounter() {
		if (retryCounter < 10) {
			return "_0" + retryCounter;
		} else {
			return "_" + Integer.toString(retryCounter);
		}
	}

	//Stops the script for the given amount of seconds.
	public  void sleep(int seconds) throws InterruptedException {
		Thread.sleep(seconds*1000L);
	}

	//Stops the script for the given amount of seconds.
	public  void sleep(double seconds) throws InterruptedException  {
		log.debug("Waiting for " + seconds + " sec");
		seconds = seconds * 1000;
		Thread.sleep((int) seconds);
	}

	public  void log(String message) {
		log.debug(message);
	}

	protected void swipeUp() {
		swipeUp(0.15f, 0.15f);
	}

	protected void swipeUp(float topPad, float bottomPad) {
		Dimension size = driver.manage().window().getSize();
		log.debug("Window size is " + size);
		log.debug(topPad+" "+bottomPad);
	}

	protected void swipeUp(Point rootLocation, Dimension rootSize, int duration, float topPad, float bottomPad) {
        int offset = 1;
        int topOffset = Math.round(rootSize.getHeight() * topPad);
        int bottomOffset = Math.round(rootSize.getHeight() * bottomPad);
        Point center = new Point(rootLocation.x + rootSize.getWidth() / 2, rootLocation.y + rootSize.getHeight() / 2);
        log.debug("Swiping up at" +
                " x1: " + center.getX() +
                " y1:" + (rootLocation.getY() + rootSize.getHeight() - bottomOffset + offset) +
                " x2:" + center.getX() +
                " y2:" + (rootLocation.getY() + topOffset));

               /* driver.swipe(center.getX(),
                rootLocation.getY() + rootSize.getHeight() - bottomOffset + offset,
               center.getX(),
                rootLocation.getY() + topOffset,
                duration);*/
    }

	protected void swipeDown() {
		swipeDown(0.15f, 0.15f);
	}

	protected void swipeDown(float topPad, float bottomPad) {
		Dimension size = driver.manage().window().getSize();
		log.debug("Window size is " + size);
		swipeDown(new Point(0, 0), size, 1000, topPad, bottomPad);
	}

	protected void swipeDown(Point rootLocation, Dimension rootSize, int duration, float topPad, float bottomPad) {
		 /*log.debug("Swiping down at" +
                " x1: " + center.getX() +
                " y1:" + (rootLocation.getY() + topOffset) +
                " x2:" + center.getX() +
                " y2:" + (rootLocation.getY() + rootSize.getHeight() - bottomOffset + offset));
                driver.swipe(center.getX(),
                (rootLocation.getY() + topOffset),
                center.getX(),
                (rootLocation.getY() + rootSize.getHeight() - bottomOffset + offset),
                duration);*/
		    }

	protected void swipeLeft() {
		swipeLeft(0.15f, 0.15f);
	}

	protected void swipeLeft(float leftPad, float rightPad) {
		Dimension size = driver.manage().window().getSize();
		log.debug("Window size " + size);
		swipeLeft(new Point(0,0), size, 1000, leftPad, rightPad);
	}

	protected void swipeLeft(Point rootLocation, Dimension rootSize, int duration, float leftPad, float rightPad) {
		/* log.debug("Swiping left at" +
                " x1: " + (rootLocation.getX() + rootSize.getWidth() - rightOffset + offset) +
                " y1:" + center.getY() +
                " x2:" + (rootLocation.getX() + leftOffset) +
                " y2:" + center.getY());
                driver.swipe((rootLocation.getX() + rootSize.getWidth() - rightOffset + offset),
                center.getY(),
                (rootLocation.getX() + leftOffset),
                center.getY(),
                duration);*/
	}

	protected void swipeRight() {
		swipeRight(0.15f, 0.15f);
	}

	protected void swipeRight(float leftPad, float rightPad) {
		Dimension size = driver.manage().window().getSize();
		swipeRight(new Point(0,0), size, 1000, leftPad, rightPad);
	}

	protected void swipeRight(Point rootLocation, Dimension rootSize, int duration, float leftPad, float rightPad) {
		/* log.debug("Swiping right at" +
                " x1: " + (rootLocation.getX() + leftOffset) +
                " y1:" + center.getY() +
                " x2:" + (rootLocation.getX() + rootSize.getWidth() - rightOffset + offset) +
                " y2:" + center.getY());
                driver.swipe((rootLocation.getX() + leftOffset),
                center.getY(),
                (rootLocation.getX() + rootSize.getWidth() - rightOffset + offset),
                center.getY(),
                duration);*/
	}

	protected void hideKeyboard() {
		try {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			driver.hideKeyboard();
		} catch (Exception e) {
			log.debug("Hiding soft keyboard failed");
			log.debug(e.toString());
		}
		driver.manage().timeouts().implicitlyWait(defaultWaitTime, TimeUnit.SECONDS);
	}
   
	
	public WebDriver setWebDriver(WebDriver driver) {
		return driver;

	}
}
