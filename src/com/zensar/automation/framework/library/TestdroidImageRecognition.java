package com.zensar.automation.framework.library;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.opencv.core.Point;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.testng.AssertJUnit;

import com.zensar.automation.framework.utils.Constant;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.TouchAction;

/**
 * Created by testdroid on 22/07/16.
 * This class is used for image recognition 
 */
public class TestdroidImageRecognition extends AbstractAppiumTest {
	 
	 public TestdroidImageRecognition(AppiumDriver<WebElement> driver1) throws IOException {
		super(driver1);
	}

	static   final  int DEFAULT_RETRIES = 2;
	static final int DEFAULT_RETRY_WAIT = 0;
	static final double DEFAULT_TOLERANCE = 0.6;
	static final boolean DEFAULT_WITH_ASSERT = true;
	static final boolean DEFAULT_TAKE_SCREENSHOT = true;
	static final boolean DEFAULT_CROP = false;
	    AkazeImageFinder imageFinder = new AkazeImageFinder();

	    String queryimageFolder = "";
	    long timeDifferenceStartTest1;
	    boolean found = false;
	   
	    
	    //If this method is called inside a test the script will check if the device has a resolution lower than 500x500 and if so will use
	    // a different set of images when trying to find a image. These images are located in /queryimages/low_res
	    public void setQueryImageFolder() {
	        Dimension size = driver.manage().window().getSize();
	       log.debug("Screen size: " + size.toString());
	        if ((size.getHeight() <= 500) || (size.getWidth() <= 500)) {
	            queryimageFolder = "low_res/";
	        }
	    }

	    /**
	     * ======================================================================================
	     * FINDING AN IMAGE IN ANOTHER IMAGE
	     * ======================================================================================
	     */

	    public Point[] findImage(String image, String scene) {
	        return findImage(image, scene, DEFAULT_TOLERANCE);
	    }

	    //This method calls on the Akaze scripts to find the coordinates of a given image in another image.
	    //The "image" parameter is the image that you are searching for
	    //The "scene" parameter is the image in which we are looking for "image"
	    // "tolerance" sets the required accuracy for the image recognition algorithm.
	    public Point[] findImage(String image, String scene, double tolerance)  {
	        Point[] imgRect = new Point[0];
	        Point[] imgRectScaled;

	        // queryImageFolder is "", unless set by setQueryImageFolder()
	        String queryImageFile = "queryimages/" + queryimageFolder + image;
	       log.debug("Searching for " + queryImageFile);
	       log.debug("Searching in " + searchedImage);
	        try {
	            imgRect = imageFinder.findImage(queryImageFile, searchedImage, tolerance);
	        } catch (Exception e) {
	            log.debug(e.getStackTrace());
	        }

	        if (imgRect != null) {
	        	driver.context(Constant.NATIVE_APP);
	        	Dimension size =driver.manage().window().getSize();
		        driver.context(Constant.CHROMIUM);
	            if (platformName.equalsIgnoreCase("iOS")) {
	                //for retina devices we need to recalculate coordinates
	                double sceneHeight = imageFinder.getSceneHeight();
	                double sceneWidth = imageFinder.getSceneWidth();

	                int screenHeight = size.getHeight();
	                int screenWidth = size.getWidth();

	                // Make sure screenshot size values are "landscape" for comparison
	                if (sceneHeight > sceneWidth) {
	                    double temp = sceneHeight;
	                    sceneHeight = sceneWidth;
	                    sceneWidth = temp;
	                }

	                // Make sure screen size values are "landscape" for comparison
	                if (screenHeight > screenWidth) {
	                    int temp = screenHeight;
	                    screenHeight = screenWidth;
	                    screenWidth = temp;
	                }

	                if ((screenHeight<sceneHeight) && (screenWidth<sceneWidth)) {
	                    if ((screenHeight<sceneHeight/2)&&(screenWidth<sceneWidth/2)) {
	                        imgRectScaled = new Point[]{new Point(imgRect[0].x / 3, imgRect[0].y / 3), new Point(imgRect[1].x / 3, imgRect[1].y / 3), new Point(imgRect[2].x / 3, imgRect[2].y / 3), new Point(imgRect[3].x / 3, imgRect[3].y / 3), new Point(imgRect[4].x / 3, imgRect[4].y / 3)};
	                       log.debug("Device with Retina display rendered at x3 => coordinates have been recalculated");
	                        imgRect = imgRectScaled;
	                    }
	                    else {
	                        imgRectScaled = new Point[]{new Point(imgRect[0].x / 2, imgRect[0].y / 2), new Point(imgRect[1].x / 2, imgRect[1].y / 2), new Point(imgRect[2].x / 2, imgRect[2].y / 2), new Point(imgRect[3].x / 2, imgRect[3].y / 2), new Point(imgRect[4].x / 2, imgRect[4].y / 2)};
	                       log.debug("Device with Retina display rendered at x2 => coordinates have been recalculated");
	                        imgRect = imgRectScaled;
	                    }
	                }
	            }

	            Point center = imgRect[4];

	            // Check that found center coordinate isn't out of screen bounds
	            if ((center.x >= size.width) || (center.x < 0) || (center.y >= size.height) || (center.y < 0)) {
	               log.debug("Screen size is (width, height): " + size.getWidth() + ", " + size.getHeight());
	               log.debug("WARNING: Coordinates found do not match the screen --> image not found.");
	                
	            } else {
	              return imgRect;
	            }
	        }
		      return imgRect;
	    }

	    /**
	     * ======================================================================================
	     * TAPS ON SCREEN - MIDDLE OR AT RELATIVE COORDINATES
	     * ======================================================================================
	     */

	    // Taps on the center of the screen.
	    public void tapMiddle() throws Exception {
	        Dimension size = driver.manage().window().getSize();
	        Point middle = new Point(size.getWidth() / 2, size.getHeight() / 2);

	        tapAtCoordinates((int) middle.x, (int) middle.y);
	    }

	    // Taps at given coordinates given in pixels
	    public void tapAtCoordinates(int x, int y) throws Exception {
	        if (automationName.equalsIgnoreCase("selendroid")) {
	            selendroidTapAtCoordinate(x, y, 1);
	        } else if (platformName.equalsIgnoreCase("Android")){
	            Dimension size = driver.manage().window().getSize();
	        	if(y > size.getHeight() || x > size.getWidth()){
	    			log.debug("using adb tap at " + x + ", " + y);
	    			try{
	                    //run eclipse from commandline to get path variable correct and find adb
	            		Process p = Runtime.getRuntime().exec("adb -s " + udid + " shell input tap " + x + " " + y);
	            		p.waitFor();
	    			} catch (Exception e) {
	    				log.error(e.toString());
	    			}
	        	} else {
	        	//((AppiumDriver) driver).tap(1, x, y, 1);
	        	}
	        } else {
	        //	((AppiumDriver) driver).tap(1, x, y, 1);
	        }
	    }

	    // Selendroid specific taps at given coordinates in pixels
	    public void selendroidTapAtCoordinate(int x, int y, int secs) throws Exception {
	        TouchActions actions = new TouchActions(driver);
	        actions.move(x, y).click().perform();
	        
	        
	        sleep(secs);
	        actions.up(x, y).perform();
	       actions.up(x, y).click().perform();
	    }

	    //Taps at relative coordinates on the screen.
	    // "xOffset" and "yOffset" set the X and Y coordinates.
	    // "taps" sets the number of taps that are performed.
	    // "frequency" sets the frequency of the taps.
	    public void tapAtRelativeCoordinates(double xOffset, double yOffset, int taps, double frequency) throws Exception {
	        Dimension size = driver.manage().window().getSize();
	        Point middle = new Point(size.getWidth(), size.getHeight());
	        Point middleWithOffset = new Point(middle.x * xOffset, middle.y * yOffset);

	       log.debug("Tapping at coordinates: " + middleWithOffset.toString() + "  when size of the screen is: " + size.toString());
	        for (int i = 0; i < taps; i++) {
	            if (automationName.equalsIgnoreCase("selendroid")) {
	                selendroidTapAtCoordinate((int) middleWithOffset.x, (int) middleWithOffset.y, 1);
	            } else {
	              //  ((AppiumDriver) driver).tap(1, (int) middleWithOffset.x, (int) middleWithOffset.y, 1);
	            }
	            sleep(frequency);
	        }
	    }

	    public void tapAtRelativeCoordinates(double xOffset, double yOffset, int taps) throws Exception {
	        tapAtRelativeCoordinates(xOffset, yOffset, taps, 1);
	    }

	    public void tapAtRelativeCoordinates(double xOffset, double yOffset) throws Exception {
	        tapAtRelativeCoordinates(xOffset, yOffset, 1, 0.9);
	    }

	    /**
	     * ======================================================================================
	     * FINDING AN IMAGE ON SCREEN
	     * ======================================================================================
	     * @throws InterruptedException 
	     * @throws IOException 
	     */

	    //Checks if an image is visible on the current screen view.
	    // "image" is the searched image name
	    // "retries" is the number of times the method will try to find the searched image. If not set, default is 5.
	    // "tolerance" sets the required accuracy for the image recognition algorithm.
	    // "with_assert" specifies if the method will return a fail or not if the searched image is not found on the screen. Use findImageOnScreenNoAssert() to have this set by default to FALSE
	    public Point[] findImageOnScreen(String image, int retries, int retryWait, double tolerance, boolean withAssert, boolean takeScreenshot, boolean crop) throws InterruptedException, IOException  {
	        Point[] imgRect = null;
	        boolean newStep = true;
	        long starTime = System.nanoTime();
	        int originalRetries = retries;
	        while ((retries > 0) && (imgRect == null)) {
	            if (retries < originalRetries) {
	                if (retryWait > 0) {
	                   log.debug("retryWait given, sleeping " + retryWait + " seconds.");
	                    try {
							sleep(retryWait);
						} catch (Exception e) {
							log.debug(e.getStackTrace());
						}
	                }
	                newStep = false;
	            }

	           log.debug("Find image started, retries left: " + retries);
	           log.debug("Image ="+image);
	            if (takeScreenshot)
	                takeScreenshot(image + "_screenshot", newStep);
	            imgRect = findImage(image, image + "_screenshot" + getRetryCounter() + "_" + timeDifferenceStartTest1, tolerance);
	            retries = retries - 1;
	        }

	        long endTime = System.nanoTime();
	        int difference = (int) ((endTime - starTime) / 1e6 / 1000);
	       log.debug("==> Find image took: " + difference + " secs.");

	        if (imgRect!=null) {
	        }
	        else
	        {
	        	return imgRect;
	        }

	        if (crop) {
	            Point topLeft = imgRect[0];
	            Point topRight = imgRect[1];
	            Point bottomLeft = imgRect[2];
	            imageFinder.cropImage(screenshotsFolder + getScreenshotsCounter() + "_" + image + "_screenshot" + getRetryCounter() + "_" + timeDifferenceStartTest1 + "sec", topLeft.x, topLeft.y, topRight.x - topLeft.x, bottomLeft.y - topLeft.y);
	        }
	        return imgRect;
	    }

	    public Point[] findImageOnScreen(String image, int retries, int retryWait, double tolerance, boolean withAssert, boolean takeScreenshot) throws InterruptedException, IOException  {
	        return findImageOnScreen(image, retries, retryWait, tolerance, withAssert, takeScreenshot, DEFAULT_CROP);
	    }

	    public Point[] findImageOnScreen(String image, int retries, int retryWait, double tolerance, boolean withAssert) throws InterruptedException, IOException  {
	        return findImageOnScreen(image, retries, retryWait, tolerance, withAssert, DEFAULT_TAKE_SCREENSHOT, DEFAULT_CROP);
	    }

	    public Point[] findImageOnScreen(String image, int retries, int retryWait, double tolerance) throws InterruptedException, IOException  {
	        return findImageOnScreen(image, retries, retryWait, tolerance, DEFAULT_WITH_ASSERT, DEFAULT_TAKE_SCREENSHOT, DEFAULT_CROP);
	    }

	    public Point[] findImageOnScreen(String image, int retries, int retryWait) throws InterruptedException, IOException  {
	        return findImageOnScreen(image, retries, retryWait, DEFAULT_TOLERANCE, DEFAULT_WITH_ASSERT, DEFAULT_TAKE_SCREENSHOT, DEFAULT_CROP);
	    }

	    public Point[] findImageOnScreen(String image, int retries) throws InterruptedException, IOException  {
	        return findImageOnScreen(image, retries, DEFAULT_RETRY_WAIT, DEFAULT_TOLERANCE, DEFAULT_WITH_ASSERT, DEFAULT_TAKE_SCREENSHOT, DEFAULT_CROP);
	    }

	    public Point[] findImageOnScreen(String image) throws InterruptedException, IOException {
	        return findImageOnScreen(image, DEFAULT_RETRIES);
	    }

	    public Point[] findImageOnScreenNoAssert(String image, int retries, int retryWait, double tolerance) throws InterruptedException, IOException  {
	        return findImageOnScreen(image, retries, retryWait, tolerance, false, DEFAULT_TAKE_SCREENSHOT, DEFAULT_CROP);
	    }

	    public Point[] findImageOnScreenNoAssert(String image, int retries, int retryWait) throws InterruptedException, IOException  {
	        return findImageOnScreenNoAssert(image, retries, retryWait, DEFAULT_TOLERANCE);
	    }

	    public Point[] findImageOnScreenNoAssert(String image, int retries) throws InterruptedException, IOException  {
	        return findImageOnScreenNoAssert(image, retries, DEFAULT_RETRY_WAIT, DEFAULT_TOLERANCE);
	    }

	    public Point[] findImageOnScreenNoAssert(String image) throws InterruptedException, IOException {
	        return findImageOnScreenNoAssert(image, DEFAULT_RETRIES, DEFAULT_RETRY_WAIT, DEFAULT_TOLERANCE);
	    }

	    public Point[] findImageOnScreen(String image, boolean takeScreenshot) throws InterruptedException, IOException  {
	        return findImageOnScreen(image, DEFAULT_RETRIES, DEFAULT_RETRY_WAIT, DEFAULT_TOLERANCE, DEFAULT_WITH_ASSERT, takeScreenshot, DEFAULT_CROP);
	    }

	    //Searches for an image until it disappears from the current view. Good for checking if a loading screen has disappeared.
	    public void waitForImageToDisappearFromScreen(String image) throws Exception {
	        boolean firstTime = true;
	        boolean check = true;
	        long start;
	        long present;
	        start = System.nanoTime();
	        present = start;

	       log.debug("==> Trying to find image: " + image);

	        while ((check) && ((present - start) / 1e6 / 1000 < 300)) {

	            if (firstTime) {
	                firstTime = false;
	                takeScreenshot(image + "_screenshot", true);
	                if ((findImage(image, image + "_screenshot" + getRetryCounter())) == null) {
	                   log.debug("Loading screen not found. Moving on");
	                    check = false;
	                } else {
	                    sleep(3);
	                }
	            } else {
	                takeScreenshot(image + "_screenshot", false);
	                if ((findImage(image, image + "_screenshot" + getRetryCounter())) == null) {
	                   log.debug("Loading screen not found. Moving on");
	                    check = false;
	                } else {
	                    sleep(3);
	                }
	            }

	            present = System.nanoTime();

	            if ((present - start) / 1e6 / 1000 >= 300) {
	                AssertJUnit.fail("Application takes too long to load: Stopping tests.....");
	               log.debug("Application takes too long to load: Stopping tests.....");
	                check = false;
	            }
	        }
	    }

	    /**
	     * ======================================================================================
	     * TAPPING AN IMAGE ON SCREEN
	     * ======================================================================================
	     */

	    //Finds an image on screen and taps on it. Method will cause a FAIL if the image is not found.
	    // "image" is the searched image name
	    // "retries" is the number of times the method will try to find the searched image. If not set, default is 5.
	    // "tolerance" sets the required accuracy for the image recognition algorithm.
	    // "xOffset" and "yOffset" change the location on the found image where the tap is performed. If not used, the defaults are (0.5, 0.5) which represent the middle of the image.
	    public void tapImageOnScreen(String image, double xOffset, double yOffset, int retries, int retryWait, double tolerance) throws Exception {
	        Point[] imgRect = findImageOnScreen(image, retries, retryWait, tolerance);
	        Point topLeft = imgRect[0];
	        Point topRight = imgRect[1];
	        Point bottomLeft = imgRect[2];
	        Point center = imgRect[4];

	        if ((xOffset == 0.5) && (yOffset == 0.5)) {
	           log.debug("Coordinates are : " + center.x + "," + center.y);
	            tapAtCoordinates((int) center.x, (int) center.y);
	        } else {
	            //adding the offset to each coordinate; if offset = 0.5, middle will be returned
	            double newX = topLeft.x + (topRight.x - topLeft.x) * xOffset;
	            double newY = topLeft.y + (bottomLeft.y - topLeft.y) * yOffset;

	           log.debug("Coordinates with  offset are : " + newX + ", " + newY);
	            tapAtCoordinates((int) newX, (int) newY);
	        }
	    }

	    public void tapImageOnScreen(String image, double xOffset, double yOffset, int retries, int retryWait) throws Exception {
	        tapImageOnScreen(image, xOffset, yOffset, retries, retryWait, DEFAULT_TOLERANCE);
	    }

	    public void tapImageOnScreen(String image, double xOffset, double yOffset, int retries) throws Exception {
	        tapImageOnScreen(image, xOffset, yOffset, retries, DEFAULT_RETRY_WAIT, DEFAULT_TOLERANCE);
	    }

	    public void tapImageOnScreen(String image, double xOffset, double yOffset) throws Exception {
	        tapImageOnScreen(image, xOffset, yOffset, DEFAULT_RETRIES, DEFAULT_RETRY_WAIT, DEFAULT_TOLERANCE);
	    }

	    public void tapImageOnScreen(String image, int retries, int retryWait, double tolerance) throws Exception {
	        tapImageOnScreen(image, 0.5, 0.5, retries, retryWait, tolerance);
	    }

	    public void tapImageOnScreen(String image, int retries, int retryWait) throws Exception {
	        tapImageOnScreen(image, 0.5, 0.5, retries, retryWait, DEFAULT_TOLERANCE);
	    }

	    public void tapImageOnScreen(String image, int retries) throws Exception {
	        tapImageOnScreen(image, 0.5, 0.5, retries, DEFAULT_RETRY_WAIT, DEFAULT_TOLERANCE);
	    }

	    public void tapImageOnScreen(String image) throws Exception {
	        tapImageOnScreen(image, 0.5, 0.5, DEFAULT_RETRIES, DEFAULT_RETRY_WAIT, DEFAULT_TOLERANCE);
	    }

	    //Finds an image on screen and taps on it. Method will cause a FAIL if the image is not found.
	    // "image" is the searched image name
	    // "retries" is the number of times the method will try to find the searched image. If not set, default is 5.
	    // "xOffset" and "yOffset" change the location on the found image where the tap is performed. If not used, the defaults are (0.5, 0.5) which represent the middle of the image.
	    // "taps" sets the number of taps that are performed.
	    // "frequency" sets the frequency of the taps.
	    public void multipleTapImageOnScreen(String image, int retries, int taps, double frequency, double xOffset, double yOffset) throws Exception {
	        Point[] imgRect = findImageOnScreen(image, retries);
	        Point topLeft = imgRect[0];
	        Point topRight = imgRect[1];
	        Point bottomrLeft = imgRect[2];
	        Point center = imgRect[4];
	        //imgRect[4] will have the center of the rectangle containing the image

	        if ((xOffset == 0.5) && (yOffset == 0.5)) {

	           log.debug("Coordinates are: " + center.x + "," + center.y);

	            for (int i = 0; i < taps; i++) {
	                tapAtCoordinates((int) center.x, (int) center.y);
	                sleep(frequency);
	            }
	        } else {
	            double newX = topLeft.x + (topRight.x - topLeft.x) * xOffset;
	            double newY = topLeft.y + (bottomrLeft.y - topLeft.y) * yOffset;

	            for (int i = 0; i < taps; i++) {
	               log.debug("Coordinates with offset are : " + newX + ", " + newY);
	                tapAtCoordinates((int) newX, (int) newY);
	                sleep(frequency);
	            }
	        }
	    }

	    public void multipleTapImageOnScreen(String image, int taps, double frequency, double xOffset, double yOffset) throws Exception {
	        multipleTapImageOnScreen(image, DEFAULT_RETRIES, taps, frequency, xOffset, yOffset);
	    }

	    public void multipleTapImageOnScreen(String image, int retries, int taps, double frequency) throws Exception {
	        multipleTapImageOnScreen(image, retries, taps, frequency, 0.5, 0.5);
	    }

	    public void multipleTapImageOnScreen(String image, int taps, double frequency) throws Exception {
	        multipleTapImageOnScreen(image, DEFAULT_RETRIES, taps, frequency, 0.5, 0.5);
	    }

	    public void multipleTapImageOnScreen(String image, int taps) throws Exception {
	        multipleTapImageOnScreen(image, DEFAULT_RETRIES, taps, 1, 0.5, 0.5);
	    }

	    //Finds an image on screen and taps on it. Method will NOT cause a FAIL if the image is not found.
	    // "image" is the searched image name
	    // "retries" is the number of times the method will try to find the searched image. If not set, default is 5.
	    // "xOffset" and "yOffset" change the location on the found image where the tap is performed. If not used, the defaults are (0.5, 0.5) which represent the middle of the image.
	    public boolean tryTapImageOnScreen(String image, double xOffset, double yOffset, int retries) throws Exception {
	        Point[] imgRect = findImageOnScreenNoAssert(image, retries);

	        if (imgRect == null) {
	            return false;
	        } else {
	            Point topLeft = imgRect[0];
	            Point topRight = imgRect[1];
	            Point bottomLeft = imgRect[2];
	            Point center = imgRect[4];

	            if ((xOffset == 0.5) && (yOffset == 0.5)) {
	               log.debug("Coordinates are: " + center.x + "," + center.y);
	                tapAtCoordinates((int) center.x, (int) center.y);
	            } else {
	                double newX = topLeft.x + (topRight.x - topLeft.x) * xOffset;
	                double newY = topLeft.y + (bottomLeft.y - topLeft.y) * yOffset;
	                tapAtCoordinates((int) newX, (int) newY);
	            }
	            return true;
	        }
	    }

	    public boolean tryTapImageOnScreen(String image, double xOffset, double yOffset) throws Exception {
	        return tryTapImageOnScreen(image, xOffset, yOffset, DEFAULT_RETRIES);
	    }

	    public boolean tryTapImageOnScreen(String image, int retries) throws Exception {
	        return tryTapImageOnScreen(image, 0.5, 0.5, retries);
	    }

	    public boolean tryTapImageOnScreen(String image) throws Exception {
	        return tryTapImageOnScreen(image, 0.5, 0.5, DEFAULT_RETRIES);
	    }

	    // Finds an image on screen and taps and hold on it for a specified duration.
	    // "duration" is given in seconds
	    // "with_assert" specifies if the method will return a fail or not if the image is not found.
	    public void tapAndHoldImageOnScreen(String image, double xOffset, double yOffset, int duration, boolean withAssert) throws Exception {

	        Point[] imgRect;

	        if (withAssert) {
	            imgRect = findImageOnScreen(image);
	        } else {
	            imgRect = findImageOnScreenNoAssert(image);
	        }

	        Point topLeft = imgRect[0];
	        Point topRight = imgRect[1];
	        Point bottomLeft = imgRect[2];

	        double newX = topLeft.x + (topRight.x - topLeft.x) * xOffset;
	        double newY = topLeft.y + (bottomLeft.y - topLeft.y) * yOffset;

	       log.debug("Coordinates with offset are: " + newX + ", " + newY);

	        if (automationName.equalsIgnoreCase("selendroid")) {
	            selendroidTapAtCoordinate((int) newX, (int) newY, duration);
	        } else {
	        	//((AppiumDriver) driver).tap(1, (int) newX, (int) newY, duration);
	        }
	    }

	    public void tapAndHoldImageOnScreen(String image, double xOffset, double yOffset, int duration) throws Exception {
	        tapAndHoldImageOnScreen(image, xOffset, yOffset, duration, DEFAULT_WITH_ASSERT);
	    }

	    public void tapAndHoldImageOnScreen(String image, int duration, boolean withAssert) throws Exception {
	        tapAndHoldImageOnScreen(image, 0.5, 0.5, duration, withAssert);
	    }

	    public void tapAndHoldImageOnScreen(String image, int duration) throws Exception {
	        tapAndHoldImageOnScreen(image, 0.5, 0.5, duration, DEFAULT_WITH_ASSERT);
	    }

	    //Taps and holds on relative coordinates on the screen.
	    public void tapAndHoldOnScreen(double xOffset, double yOffset, int duration) throws Exception {

	        duration = duration * 1000;
	        Dimension size = driver.manage().window().getSize();
	        Point middle = new Point(size.getWidth(), size.getHeight());
	        Point middleWithOffset = new Point(middle.x * xOffset, middle.y * yOffset);

	       log.debug("Coordinates with offset are: " + middleWithOffset.x + ", " + middleWithOffset.y);

	        if (automationName.equalsIgnoreCase("selendroid")) {
	            selendroidTapAtCoordinate((int) middleWithOffset.x, (int) middleWithOffset.y, duration);
	        } else {
	        //	((AppiumDriver) driver).tap(1, (int) middleWithOffset.x, (int) middleWithOffset.y, duration);
	        }
	    }

	    /**
	     * =====================================================================================
	     * SWIPE GESTURES
	     * =====================================================================================
	     */

	    //Performs a vertical swipe on the screen, starting from a searched image.
	    //"distance" is given in pixels. A positive "distance" will perform a swipe down, a negative "distance" will perform a swipe up.
	    //if "xOffset" and "yOffset" are used, the swipe will start from a relative coordinate of that image. If not used, the swipe will start from the center of the image.
	    public void swipeVerticallyOnImage(String image, int distance, double xOffset, double yOffset) throws Exception {
	        Point[] imgRect = findImageOnScreen(image);

	        int startX = (int) (imgRect[0].x + (imgRect[1].x - imgRect[0].x) * xOffset);
	        int startY = (int) (imgRect[0].y + (imgRect[2].y - imgRect[0].y) * yOffset);
	        int endX = startX;
	        int endY = startY + distance;

	        if (platformName.equalsIgnoreCase("iOS")) {
	            iOSSwipe(startX, startY, endX, endY);
	        } else {
	            androidSwipe(startX, startY, endX, endY);
	        }
	    }

	    public void swipeVerticallyOnImage(String image, int distance) throws Exception {
	        swipeVerticallyOnImage(image, distance, 0.5, 0.5);
	    }

	    //Performs a horizontal swipe on the screen, starting from a searched image.
	    //"distance" is given in pixels. A positive "distance" will perform a swipe to the right, a negative "distance" will perform a swipe to the left.
	    //if "xOffset" and "yOffset" are used, the swipe will start from a relative coordinate of that image. If not used, the swipe will start from the center of the image.
	    public void swipeHorizontallyOnImage(String image, int distance, double xOffset, double yOffset) throws Exception {
	        Point[] imgRect = findImageOnScreen(image);

	        int startX = (int) (imgRect[0].x + (imgRect[1].x - imgRect[0].x) * xOffset);
	        int startY = (int) (imgRect[0].y + (imgRect[2].y - imgRect[0].y) * yOffset);
	        int endX = startX + distance;
	        int endY = startY;

	        if (platformName.equalsIgnoreCase("iOS")) {
	            iOSSwipe(startX, startY, endX, endY);
	        } else {
	            androidSwipe(startX, startY, endX, endY);
	        }
	    }

	    public void swipeHorizontallyOnImage(String image, int distance) throws Exception {
	        swipeHorizontallyOnImage(image, distance, 0.5, 0.5);
	    }


	    //Performs a horizontal swipe starting from a relative coordinate of the device screen.
	    //"distance" is given in pixels. A positive "distance" will perform a swipe to the right, a negative "distance" will perform a swipe to the left.
	    //"swipes" sets the number of swipes that are performed.
	    //"frequency" sets the frequency of the swipes.
	    public void swipeHorizontally(double xOffset, double yOffset, int distance, int swipes, double frequency) throws Exception {

	        Dimension size = driver.manage().window().getSize();
	        Point middle = new Point(size.getWidth(), size.getHeight());
	        Point middleWithOffset = new Point(middle.x * xOffset, middle.y * yOffset);

	        int startX = (int) middleWithOffset.x;
	        int startY = (int) middleWithOffset.y;
	        int endX = startX + distance;
	        int endY = startY;


	        for (int i = 0; i < swipes; i++) {
	            if (platformName.equalsIgnoreCase("iOS")) {
	                iOSSwipe(startX, startY, endX, endY);
	            } else {
	                androidSwipe(startX, startY, endX, endY);
	            }
	            sleep(frequency);
	        }
	       log.debug("Finished executing swipes ");
	    }

	    //Performs a horizontal swipe starting from a relative coordinate of the device screen.
	    //"distance" is relative, a number from 0 to 1. A positive "distance" will perform a swipe to the right, a negative "distance" will perform a swipe to the left.
	    //"swipes" sets the number of swipes that are performed.
	    //"frequency" sets the frequency of the swipes.
	    public void swipeHorizontally(double xOffset, double yOffset, double distance, int swipes, double frequency) throws Exception { //positive distance for swipe right, negative for swipe left.

	        Dimension size = driver.manage().window().getSize();
	        Point middle = new Point(size.getWidth(), size.getHeight());
	        Point middleWithOffset = new Point(middle.x * xOffset, middle.y * yOffset);
	        double relativeDistance = middle.x * distance;

	        int startX = (int) middleWithOffset.x;
	        int startY = (int) middleWithOffset.y;
	        int endX = startX + (int) Math.floor(relativeDistance);
	        int endY = startY;


	        for (int i = 0; i < swipes; i++) {
	            if (platformName.equalsIgnoreCase("iOS")) {
	                iOSSwipe(startX, startY, endX, endY);
	            } else {
	                androidSwipe(startX, startY, endX, endY);
	            }
	            sleep(frequency);
	        }
	       log.debug("Finished executing swipes ");
	    }


	    //Performs a vertical swipe starting from a relative coordinate of the device screen.
	    //"distance" is given in pixels. A positive "distance" will perform a swipe down, a negative "distance" will perform a swipe up.
	    //"swipes" sets the number of swipes that are performed.
	    //"frequency" sets the frequency of the swipes.
	    public void swipeVertically(double xOffset, double yOffset, int distance, int swipes, double frequency) throws Exception {

	        Dimension size = driver.manage().window().getSize();
	        Point middle = new Point(size.getWidth(), size.getHeight());
	        Point middleWithOffset = new Point(middle.x * xOffset, middle.y * yOffset);

	        int startX = (int) middleWithOffset.x;
	        int startY = (int) middleWithOffset.y;
	        int endX = startX;
	        int endY = startY + distance;


	        for (int i = 0; i < swipes; i++) {
	            if (platformName.equalsIgnoreCase("iOS")) {
	                iOSSwipe(startX, startY, endX, endY);
	            } else {
	                androidSwipe(startX, startY, endX, endY);
	            }
	            sleep(frequency);
	        }
	       log.debug("Finished executing swipes");
	    }

	    //Performs a vertical swipe starting from a relative coordinate of the device screen.
	    //"distance" is relative, a number from 0 to 1. A positive "distance" will perform a swipe down, a negative "distance" will perform a swipe up.
	    //"swipes" sets the number of swipes that are performed.
	    //"frequency" sets the frequency of the swipes.
	    public void swipeVertically(double xOffset, double yOffset, double distance, int swipes, double frequency) throws Exception {

	        Dimension size = driver.manage().window().getSize();
	        Point middle = new Point(size.getWidth(), size.getHeight());
	        Point middleWithOffset = new Point(middle.x * xOffset, middle.y * yOffset);
	        double relativeDistance = middle.y * distance;


	        int startX = (int) middleWithOffset.x;
	        int startY = (int) middleWithOffset.y;
	        int endX = startX;
	        int endY = startY + (int) Math.floor(relativeDistance);


	        for (int i = 0; i < swipes; i++) {
	            if (platformName.equalsIgnoreCase("iOS")) {
	                iOSSwipe(startX, startY, endX, endY);
	            } else {
	                androidSwipe(startX, startY, endX, endY);
	            }
	            sleep(frequency);
	        }
	       log.debug("Finished executing swipes");
	    }


	    public void androidSwipe(int startX, int startY, int endX, int endY) throws InterruptedException {
	        TouchActions actions = new TouchActions(driver);

	        actions.down(startX, startY).perform();
	        sleep(0.5);
	        actions.move(endX, endY).perform();
	        sleep(0.5);
	        actions.up(endX, endY).perform();
	        sleep(0.5);
	    }

	    public void iOSSwipe(int startX, int startY, int endX, int endY) throws InterruptedException  {
			TouchAction action = new TouchAction((MobileDriver) driver);

	       //action.press(startX, startY);
	      //  action.waitAction(1000);  //has to be >= 500 otherwise it will fail
	     //   action.moveTo(endX, endY);
	        sleep(0.5);
	        action.release();
	        sleep(0.5);
	        action.perform();
	    }


	    public void swipe(double startX, double startY, double endX, double endY) throws InterruptedException  {
	        Dimension size = driver.manage().window().getSize();

	        Point screen = new Point(size.getWidth(), size.getHeight());
	        Point swipeStart = new Point(screen.x * startX, screen.y * startY);
	        Point swipeEnd = new Point(screen.x * endX, screen.y * endY);

	        if (platformName.equalsIgnoreCase("Android")) {
	            androidSwipe((int) swipeStart.x, (int) swipeStart.y, (int) swipeEnd.x, (int) swipeEnd.y);
	        } else {
	            iOSSwipe((int) swipeStart.x, (int) swipeStart.y, (int) swipeEnd.x, (int) swipeEnd.y);
	        }
	    }

	    /**
	     * ======================================================================================
	     * DRAG AND DROP
	     * ======================================================================================
	     */

	    //Performs a drag and drop from the middle of the "object" image to the middle of the "location" image.
	    public void dragFromOneImageToAnother(String object, String location) throws Exception {

	        Point[] objectCoord = findImageOnScreen(object);
	        Point[] locationCoord = findImageOnScreen(location, false);

	        int startX = (int) objectCoord[4].x;
	        int startY = (int) objectCoord[4].y;

	        int endX = (int) locationCoord[4].x;
	        int endY = (int) locationCoord[4].y;

	        if (automationName.equalsIgnoreCase("selendroid")) {
	            androidSwipe(startX, startY, endX, endY);
	        } else {
	            iOSSwipe(startX, startY, endX, endY);
	        }
	    }


	    //Performs a drag and drop from the middle of the "object" image to the middle of the screen.
	    public void dragImageToMiddle(String object) throws Exception {

	        Dimension size = driver.manage().window().getSize();
	        Point middle = new Point(size.getWidth() / (double)2 - 20, size.getHeight() /(double) 2 + 20);

	        Point[] objectCoord = findImageOnScreen(object, 10);

	        int startX = (int) objectCoord[4].x;
	        int startY = (int) objectCoord[4].y;

	        int endX = (int) middle.x;
	        int endY = (int) middle.y;

	        if (automationName.equalsIgnoreCase("selendroid")) {
	            androidSwipe(startX, startY, endX, endY);
	        } else {
	            iOSSwipe(startX, startY, endX, endY);
	        }
	    }

	    //Performs a drag and drop from the middle of the "object" image to the specified relative coordinates of the screen.
	    public void dragImageToCoordinates(String object, double xOffset, double yOffset) throws Exception {

	        Dimension size = driver.manage().window().getSize();
	        Point screen = new Point(size.getWidth(), size.getHeight());
	        Point swipeEnd = new Point(screen.x * xOffset, screen.y * yOffset);

	        Point[] objectCoord = findImageOnScreen(object, 10);

	        int startX = (int) objectCoord[4].x;
	        int startY = (int) objectCoord[4].y;

	        int endX = (int) swipeEnd.x;
	        int endY = (int) swipeEnd.y;

	        if (automationName.equalsIgnoreCase("selendroid")) {
	            androidSwipe(startX, startY, endX, endY);
	        } else {
	            iOSSwipe(startX, startY, endX, endY);
	        }
	    }

	    /**
	     * ======================================================================================
	     * ADB UTILITIES
	     * ======================================================================================
	     */


	    //Uses adb commands to get the screen size. To be used when appium methods fail. Only works on Android devices.
	   public Dimension getScreenSizeADB() {
	       log.debug("trying to get size from adb...");
	       log.debug("------------------------------");
	        if (platformName.equalsIgnoreCase("iOS")) {
	            return driver.manage().window().getSize();
	        } else {
	            String adb = "adb";
	            String[] adbCommand = {adb, "shell", "dumpsys", "window"};
	            try {
	                ProcessBuilder p = new ProcessBuilder(adbCommand);
	                Process proc = p.start();
	                InputStream stdin = proc.getInputStream();
	                InputStreamReader isr = new InputStreamReader(stdin);
	                BufferedReader br = new BufferedReader(isr);
	                String line = null;
	                String[] size = null;
	                while ((line = br.readLine()) != null) {
	                    if (!line.contains("OriginalmUnrestrictedScreen")&& line.contains("mUnrestrictedScreen")) { //we do this check for devices with android 5.x+ The adb command returns an extra line with the values 0x0 which must be filtered out.
	                        
	                            String[] tmp = line.split("\\) ");
	                            size = tmp[1].split("x");
	                       
	                    }
	                }
	                
	                int width = Integer.parseInt((size!=null?size[0]:"0"));
	                int height = Integer.parseInt((size!=null?size[1]:"0"));
	                return new Dimension(width, height);

	            } catch (Exception exception) {
	               log.error( exception.getMessage());
	            }
	        }
	        return null;
	    }

	    public boolean findDeviceTypeADB()  {
	       log.debug("trying to find device type ...");
	       log.debug("------------------------------");
	        if (platformName.equalsIgnoreCase("iOS")) {
	            //TO Be added
	        } else {
	            String adb = "adb";
	            String[] adbCommand = {adb, "shell", "getprop", "ro.build.characteristics"};
	            try {
	                ProcessBuilder p = new ProcessBuilder(adbCommand);
	                Process proc = p.start();
	                InputStream stdin = proc.getInputStream();
	                InputStreamReader isr = new InputStreamReader(stdin);
	                BufferedReader br = new BufferedReader(isr);
	                String line = null;
	                while ((line = br.readLine()) != null) {
	                    if (line.contains("tablet")) {
	                        return true;
	                    }

	                }
	            } catch (Exception t) {
	                t.printStackTrace();
	            }
	        }
	        return false;
	    }

	    //Uses adb commands to tap at relative coordinates. To be used when appium methods fail. Only works on Android devices.
	    public void tapAtRelativeCoordinatesADB(double xOffset, double yOffset) throws Exception {
	        if (platformName.equalsIgnoreCase("iOS")) {
	            tapAtRelativeCoordinates(xOffset, yOffset);
	        } else {
	        	driver.context("NATIVE_APP");
	        	Dimension size =driver.manage().window().getSize();
		        driver.context("CHROMIUM");
	           log.debug("Size of device as seen by ADB is - width: " + size.width + " height: " + size.height);
	            String x = String.valueOf(size.width * xOffset);
	            String y = String.valueOf(size.height * yOffset);
	           log.debug("ADB: x and y: " + x + ", " + y);
	            String[] adbCommand = {"adb", "shell", "input", "tap", x, y};

	            try {
	                ProcessBuilder p = new ProcessBuilder(adbCommand);
	                Process proc = p.start();
	                InputStream stdin = proc.getInputStream();
	                InputStreamReader isr = new InputStreamReader(stdin);
	                BufferedReader br = new BufferedReader(isr);
	                String line = null;
	                while ((line = br.readLine()) != null)
	                   log.debug(line);


	                proc.waitFor();

	            } catch (Exception t) {
	                t.printStackTrace();
	            }
	        }
	    }

	    //Uses adb commands to tap at coordinates. To be used when appium methods fail. Only works on Android devices.
	    public void tapAtCoordinatesADB(double x, double y) throws Exception {
	    	String strInput="input";
	        String[] adbCommand;
	        if (platformName.equalsIgnoreCase("iOS")) {
	            tapAtCoordinates((int) x, (int) y);
	        } else {
	            int xX = (int) x;
	            int yY = (int) y;
	            String strx = String.valueOf(xX);
	            String strY = String.valueOf(yY);
	           log.debug("ADB: X: " + strx + ", Y: " + strY);

	            if (automationName.equalsIgnoreCase("selendroid")) {
	               log.debug("adb_shell_input_tap"); //works for 4.1.x. Will not work for 4.0.x
	                adbCommand = new String[]{"adb", "shell", strInput, "tap", strx, strY};
	                processBuilder(adbCommand);
	               log.debug("Tap done.");
	            } else {
	                adbCommand = new String[]{"adb", "shell",strInput, "touchscreen", "swipe", strx, strY, strx, strY, "2000"};
	                processBuilder(adbCommand);
	            }
	        }
	    }

	    public void processBuilder(String[] adbCommand) throws InterruptedException {
	        try {
	            found = true;
	            ProcessBuilder p = new ProcessBuilder(adbCommand);
	            Process proc = p.start();
	            InputStream stdin = proc.getInputStream();
	            InputStreamReader isr = new InputStreamReader(stdin);
	            BufferedReader br = new BufferedReader(isr);
	            String line = null;
	            while ((line = br.readLine()) != null)
	               log.debug(line);

	            proc.waitFor();

	        } catch ( IOException t) {
	            found = false;
	            t.printStackTrace();
	        }
	    }

	    /**
	     * ======================================================================================
	     * CROP IMAGE
	     * ======================================================================================
	     * @throws IOException 
	     * @throws InterruptedException 
	     */

	    public void findAndCropImageFromScreen(String image) throws InterruptedException, IOException{
	        findImageOnScreen(image, 3, DEFAULT_RETRY_WAIT, 0.60, true, true, true);
	    }

	    /**
	     * ======================================================================================
	     * TESSERACT GRAB TEXT FROM IMAGE
	     * ======================================================================================
	     * @throws IOException 
	     * @throws InterruptedException 
	     */

	    public String grabText(String image) throws InterruptedException, IOException  {
	        findAndCropImageFromScreen(image);
	        String imageInput = screenshotsFolder + getScreenshotsCounter() + "_" + image + "_screenshot" + getRetryCounter() + "_" + timeDifferenceStartTest1 + "sec" + ".png";
	        String[] tesseractCommand = {"tesseract", imageInput, "stdout"};
	        StringBuilder value = new StringBuilder();
	        try {
	            ProcessBuilder p = new ProcessBuilder(tesseractCommand);
	            Process proc = p.start();
	            InputStream stdin = proc.getInputStream();
	            InputStreamReader isr = new InputStreamReader(stdin);
	            BufferedReader br = new BufferedReader(isr);
	            String line;
	            while ((line = br.readLine()) != null) {
	                value.append( line);
	            }

	        } catch (Exception t) {
	            t.printStackTrace();
	        }
	        return value.toString();
	    }

	    /**
	     * ======================================================================================
	     * OTHER UTILITIES
	     * ======================================================================================
	     */

	    public Point correctAndroidCoordinates(Point appiumCoord)  {

	        Dimension appiumDimensions = driver.manage().window().getSize();
	        int appiumScreenWidth = appiumDimensions.getWidth();
	        int appiumScreenHeight = appiumDimensions.getHeight();

	        driver.context("NATIVE_APP");
        	Dimension adbDimension =driver.manage().window().getSize();
	        driver.context("CHROMIUM");
	        int adbScreenWidth = adbDimension.getWidth();
	        int adbScreenHeight = adbDimension.getHeight();

	        double xOffset = appiumCoord.x / appiumScreenWidth;
	        double yOffset = appiumCoord.y / appiumScreenHeight;
	       log.debug("xOffset is : " + xOffset);
	       log.debug("yOffset is : " + yOffset);

	        return new Point(xOffset * adbScreenWidth, yOffset * adbScreenHeight);
	    }

	  
	 
	    public void dismissFullScreenMessage() throws Exception {
	       log.debug("Trying to dismiss Full Screen notification message if it shows up...");
	        tryTapImageOnScreen("full_screen_1", 0.75, 0.75, 2);
	        tryTapImageOnScreen("full_screen_2", 0.75, 0.75, 1);
	        tryTapImageOnScreen("full_screen_3", 0.75, 0.8, 1);
	    }

		

	}
