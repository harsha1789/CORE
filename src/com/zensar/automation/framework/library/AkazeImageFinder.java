package com.zensar.automation.framework.library;

import static org.opencv.imgproc.Imgproc.resize;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.openqa.selenium.WebElement;

import com.zensar.automation.framework.utils.Constant;

import io.appium.java_client.AppiumDriver;

/**
 * Created by testdroid on 22/07/16.
 * This class used to find the Image
 */
public class AkazeImageFinder{
	Logger log = Logger.getLogger(AbstractAppiumTest.class.getName());
	AppiumDriver<WebElement> webdriver;
	public AkazeImageFinder(AppiumDriver<WebElement> webdriver1) 
	{
		webdriver=webdriver1;
	}
	public AkazeImageFinder() {
	}
	double sceneHeight;
	double sceneWidth;
	double lastResizeFactor;
	public double getSceneHeight() {
		return sceneHeight;
	}

	public double getSceneWidth() {
		return sceneWidth;
	}

	public Point[] findImage(String objectFilenameNopng, String
			sceneFilenameNopng, double tolerance) {

		long startTime = System.nanoTime();

		String objectFilename = objectFilenameNopng + ".png";
		String sceneFilename = sceneFilenameNopng + ".png";

	//	Mat imgObject = Imgcodecs.imread(objectFilename, Imgcodecs.CV_LOAD_IMAGE_UNCHANGED);
		Mat imgObject = Imgcodecs.imread(objectFilename, Imgcodecs.IMREAD_UNCHANGED);//HT67091 updaded pom file version to 4.5.5-0 .
		Mat imgScene = Imgcodecs.imread(sceneFilename, Imgcodecs.IMREAD_UNCHANGED);

		Mat resizedImgScene = new Mat();
		sceneHeight = imgScene.rows();
		sceneWidth = imgScene.cols();

		double resizeFactor = 1;
		if (sceneWidth < sceneHeight)
			resizeFactor = sceneWidth / 750;
		else
			resizeFactor = sceneHeight / 750;

		if (resizeFactor > 1) {
			Size size = new Size(sceneWidth / resizeFactor, sceneHeight / resizeFactor);
			resize(imgScene, resizedImgScene, size);
			Imgcodecs.imwrite(sceneFilename, resizedImgScene);
			imgScene = Imgcodecs.imread(sceneFilename, Imgcodecs.IMREAD_UNCHANGED);
			log.debug("Image was resized, resize factor is: " + resizeFactor);
			lastResizeFactor = resizeFactor;
		} else
			resizeFactor = 1;

		String jsonResults = null;
		try {
			jsonResults = runAkazeMatch(objectFilename, sceneFilename);
		} catch (InterruptedException e) {
			log.error("AkazeImageFinder Error", e);
			Thread.currentThread().interrupt();
			return new Point[0];
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return new Point[0];
		}
		if (jsonResults == null) {
			return new Point[0];
		}

		double initialHeight = imgObject.size().height;
		double initialWidth = imgObject.size().width;

		Imgcodecs.imwrite(sceneFilename, imgScene);

		//finding homography
		LinkedList<Point> objList = new LinkedList<>();
		LinkedList<Point> sceneList = new LinkedList<>();
		JSONObject jsonObject = getJsonObject(jsonResults);
		if (jsonObject == null) {
			log.debug("ERROR: Json file couldn't be processed. ");
			return new Point[0];
		}
		JSONArray keypointsPairs = null;
		try {
			keypointsPairs = jsonObject.getJSONArray("keypoint-pairs");
		} catch (JSONException e) {
			log.error( e.getStackTrace());
			return new Point[0];
		}
		Point[] objPoints = new Point[keypointsPairs.length()];
		Point[] scenePoints = new Point[keypointsPairs.length()];
		int j = 0;
		for (int i = 0; i < keypointsPairs.length(); i++) {
			try {
				objPoints[j] = new Point(Integer.parseInt(keypointsPairs.getJSONObject(i).optString("x1")), Integer.parseInt(keypointsPairs.getJSONObject(i).optString("y1")));
				scenePoints[j] = new Point(Integer.parseInt(keypointsPairs.getJSONObject(i).optString("x2")), Integer.parseInt(keypointsPairs.getJSONObject(i).optString("y2")));
				j++;
			} catch (JSONException e) {
				log.error( e.getStackTrace());
				return new Point[0];
			}
		}

		for (int i = 0; i < objPoints.length; i++) {
			Point objectPoint = new Point(objPoints[i].x, objPoints[i].y);
			objList.addLast(objectPoint);
			Point scenePoint = new Point(scenePoints[i].x - initialWidth, scenePoints[i].y);
			sceneList.addLast(scenePoint);
		}

		if ((objList.size() < 4) || (sceneList.size() < 4)) {
			log.debug("Not enough matches found. ");

			return new Point[0];
		}

		MatOfPoint2f obj = new MatOfPoint2f();
		obj.fromList(objList);
		MatOfPoint2f scene = new MatOfPoint2f();
		scene.fromList(sceneList);

		Mat h = Calib3d.findHomography(obj, scene);

		Mat sceneCorners = drawFoundHomography(sceneFilenameNopng, imgObject, sceneFilename, h);
		Point topLeft = new Point(sceneCorners.get(0, 0));
		Point topRight = new Point(sceneCorners.get(1, 0));
		Point bottomLeft = new Point(sceneCorners.get(3, 0));
		Point bottomRight = new Point(sceneCorners.get(2, 0));


		double rotationAngle = round(getComponents(h) * 57.3 / 90, 0);

		Point[] objectOnScene = new Point[5];

		if (rotationAngle == 1.0) {
			objectOnScene[0] = topRight;
			objectOnScene[1] = bottomRight;
			objectOnScene[2] = bottomLeft;
			objectOnScene[3] = topLeft;
		} else if (rotationAngle == -1.0) {
			objectOnScene[0] = bottomLeft;
			objectOnScene[1] = topLeft;
			objectOnScene[2] = topRight;
			objectOnScene[3] = bottomRight;

		} else if (rotationAngle == 2.0) {
			objectOnScene[0] = bottomRight;
			objectOnScene[1] = bottomLeft;
			objectOnScene[2] = topLeft;
			objectOnScene[3] = topRight;
		} else {
			objectOnScene[0] = topLeft;
			objectOnScene[1] = topRight;
			objectOnScene[2] = bottomRight;
			objectOnScene[3] = bottomLeft;
		}


		topLeft = objectOnScene[0];
		topRight = objectOnScene[1];
		bottomRight = objectOnScene[2];
		bottomLeft = objectOnScene[3];

		double initialRatio;
		if ((rotationAngle == 1.0) || (rotationAngle == -1.0)) {
			initialRatio = initialWidth / initialHeight;
		} else {
			initialRatio = initialHeight / initialWidth;
		}
		double foundRatio1 = (bottomLeft.y - topLeft.y) / (topRight.x - topLeft.x);
		double foundRatio2 = (bottomRight.y - topRight.y) / (bottomRight.x - bottomLeft.x);

		long endTime = System.nanoTime();
		int difference = (int) ((endTime - startTime) / 1e6 / 1000);
		log.debug("==> Image finder took: " + difference + " secs.");

		if (checkFoundImageDimensions(topLeft, topRight, bottomLeft, bottomRight, tolerance))
			return new Point[0];
		if (checkFoundImageSizeRatio(initialHeight, initialWidth, topLeft, topRight, bottomLeft, bottomRight, initialRatio, foundRatio1, foundRatio2, tolerance))
			return new Point[0];

		//calculate points in original orientation
		Point[] points = new Point[5];

		if (rotationAngle == 1.0) {
			points[0] = new Point(sceneHeight / resizeFactor - bottomLeft.y, bottomLeft.x);
			points[1] = new Point(sceneHeight / resizeFactor - topLeft.y, topLeft.x);
			points[2] = new Point(sceneHeight / resizeFactor - topRight.y, topRight.x);
			points[3] = new Point(sceneHeight / resizeFactor - bottomRight.y, bottomRight.x);
		} else if (rotationAngle == -1.0) {
			points[0] = new Point(topRight.y, sceneWidth / resizeFactor - topRight.x);
			points[1] = new Point(bottomRight.y, sceneWidth / resizeFactor - bottomRight.x);
			points[2] = new Point(bottomLeft.y, sceneWidth / resizeFactor - bottomLeft.x);
			points[3] = new Point(topLeft.y, sceneWidth / resizeFactor - topLeft.x);
		} else if (rotationAngle == 2.0) {
			points[0] = new Point(sceneWidth / resizeFactor - bottomRight.x, sceneHeight / resizeFactor - bottomRight.y);
			points[1] = new Point(sceneWidth / resizeFactor - bottomLeft.x, sceneHeight / resizeFactor - bottomLeft.y);
			points[2] = new Point(sceneWidth / resizeFactor - topLeft.x, sceneHeight / resizeFactor - topLeft.y);
			points[3] = new Point(sceneWidth / resizeFactor - topRight.x, sceneHeight / resizeFactor - topRight.y);
		} else {
			points[0] = topLeft;
			points[1] = topRight;
			points[2] = bottomRight;
			points[3] = bottomLeft;
		}

		Point centerOriginal = new Point((points[0].x + (points[1].x - points[0].x) / 2) * resizeFactor, (points[0].y + (points[3].y - points[0].y) / 2) * resizeFactor);

		points[4] = centerOriginal;

		log.debug("Image found at coordinates: " + (int) points[4].x + ", " + (int) points[4].y + " on screen.");
		log.debug("All corners: " + points[0].toString() + " " + points[1].toString() + " " + points[2].toString() + " " + points[4].toString());

		points[0] = new Point(points[0].x * resizeFactor, points[0].y * resizeFactor);
		points[1] = new Point(points[1].x * resizeFactor, points[1].y * resizeFactor);
		points[2] = new Point(points[2].x * resizeFactor, points[2].y * resizeFactor);
		points[3] = new Point(points[3].x * resizeFactor, points[3].y * resizeFactor);

		return points;
	}

	public void cropImage(String sceneFilenameNopng, double x, double y, double width, double height) {

		String sceneFilename = sceneFilenameNopng + ".png";
		Mat imgObject = Imgcodecs.imread(sceneFilename);

		Rect croppedRect = new Rect((int) (x / lastResizeFactor), (int) (y / lastResizeFactor), (int) (width / lastResizeFactor), (int) (height / lastResizeFactor));
		Mat croppedImage = new Mat(imgObject, croppedRect);
		Imgcodecs.imwrite(sceneFilename, croppedImage);
	}

	private Mat drawFoundHomography(String sceneFilenameNopng, Mat imgObject, String filename, Mat h) {
		Mat objCorners = new Mat(4, 1, CvType.CV_32FC2);
		Mat sceneCorners = new Mat(4, 1, CvType.CV_32FC2);

		objCorners.put(0, 0, 0, 0);
		objCorners.put(1, 0,imgObject.cols(), 0);
		objCorners.put(2, 0, imgObject.cols(), imgObject.rows());
		objCorners.put(3, 0, 0, imgObject.rows());

		Core.perspectiveTransform(objCorners, sceneCorners, h);

		//Mat img = Imgcodecs.imread(filename, Imgcodecs.CV_LOAD_IMAGE_COLOR);
		Mat img = Imgcodecs.imread(filename, Imgcodecs.IMREAD_COLOR);//updated by HT67091 as upgrade open cv version to 4.5.5-0


		filename = sceneFilenameNopng + ".png";
		Imgcodecs.imwrite(filename, img);

		return sceneCorners;
	}

	private boolean checkFoundImageSizeRatio(double initialHeight, double initialWidth, Point topLeft, Point topRight, Point bottomLeft, Point bottomRight, double initialRatio, double foundRatio1, double foundRatio2, double tolerance) {
		//check the image size, if too small incorrect image was found

		if ((round(foundRatio1 / initialRatio, 2) > (1 + tolerance)) || (round(initialRatio / foundRatio2, 2) > (1 + tolerance))
				|| (round(foundRatio1 / initialRatio, 2) < (1 - tolerance)) || (round(initialRatio / foundRatio2, 2) < (1 - tolerance))) {
			log.debug("Size of image found is incorrect, check the ratios for more info:");
			log.debug("Initial height of query image: " + initialHeight);
			log.debug("Initial width of query image: " + initialWidth);
			log.debug("Initial ratio for query image: " + initialHeight / initialWidth);

			log.debug("Found top width: " + (topRight.x - topLeft.x));
			log.debug("Found bottom width: " + (bottomRight.x - bottomLeft.x));

			log.debug("Found left height: " + (bottomLeft.y - topLeft.y));
			log.debug("Found right height: " + (bottomRight.y - topRight.y));
			log.debug("Found ratio differences: " + round(foundRatio1 / initialRatio, 1) + " and " + round(initialRatio / foundRatio2, 1));
			return true;
		}
		return false;
	}

	private boolean checkFoundImageDimensions(Point topLeft, Point topRight, Point bottomLeft, Point bottomRight, double tolerance) {
		//check any big differences in height and width on each side
		double leftHeight = bottomLeft.y - topLeft.y;
		double rightHeight = bottomRight.y - topRight.y;
		double heightRatio = round(leftHeight / rightHeight, 2);


		double topWidth = topRight.x - topLeft.x;
		double bottomWidth = bottomRight.x - bottomLeft.x;
		double widthRatio = round(topWidth / bottomWidth, 2);

		if ((heightRatio == 0) || (widthRatio == 0)) {
			return false;
		}

		if ((heightRatio < (1 - tolerance)) || (heightRatio > (1 + tolerance)) || (widthRatio < (1 - tolerance)) || (widthRatio > (1 + tolerance))) {
			log.debug("Height and width ratios: " + heightRatio + " and " + widthRatio);
			log.debug("Image found is not the correct shape, height or width are different on each side.");
			return true;
		} else {
			return false;
		}
	}

	private String runAkazeMatch(String objectFilename, String sceneFilename) throws InterruptedException, IOException {

		long timestamp = System.currentTimeMillis();
		String jsonFilename = "./target/keypoints/keypoints_" + timestamp + ".json";
		File file = new File(jsonFilename);
		file.getParentFile().mkdirs();
		String platformName = System.getProperty("os.name");
		String akazePath = "";
		if (platformName.toLowerCase().contains("mac")) {
			akazePath = "lib/mac/akaze/akaze_match";
		} else if (platformName.toLowerCase().contains("win")) {
			akazePath = "lib/win/akaze/akaze_match";
		} else {
			akazePath = "lib/linux/akaze/akaze_match";
		}
		String[] akazeMatchCommand = {akazePath, objectFilename, sceneFilename, "--json", jsonFilename, "--dthreshold", "0.00000000001"};

		try {
			ProcessBuilder p = new ProcessBuilder(akazeMatchCommand);
			Process proc = p.start();
			InputStream stdin = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(stdin);
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null)
				log.debug(line);
			int exitVal = proc.waitFor();
			if (exitVal != 0)
				log.debug("Akaze matching process exited with value: " + exitVal);
		} catch (IOException t) {
			t.printStackTrace();
		}

		if (!file.exists()) {
			log.debug("ERROR: Image recognition with Akaze failed. No json file created.");
			return null;
		} else {
			return jsonFilename;
		}
	}

	public  void setupOpenCVEnv() {
		String platformName = System.getProperty("os.name");
		log.debug(platformName);
		if (platformName.toLowerCase().contains("mac")) {
			System.setProperty( "java.library.path", System.getProperty("java.library.path") + File.pathSeparator + System.getProperty("user.dir") + "/lib/mac/opencv");
		} else if (platformName.toLowerCase().contains("win")) {
			if (System.getProperty("os.arch").contains("64")) {
				System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + System.getProperty("user.dir") + "/lib/win/opencv/x64");
			} else {
				System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + System.getProperty("user.dir") + "/lib/win/opencv/x86");
			}
		} else {
			System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + System.getProperty("user.dir") + "/lib/linux/opencv");
		}

		Field fieldSysPath = null;
		try {
			fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
		} catch (NoSuchFieldException e) {
			log.error("No such field", e);
		}
		try {
			fieldSysPath.setAccessible(true);

			fieldSysPath.set(null, null);
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
		}
		log.debug("java.library.path: " + System.getProperty("java.library.path"));

		String opencvpath = Constant.OPENCVPATH;
		System.load(opencvpath + Core.NATIVE_LIBRARY_NAME + ".dll");

	}

	private JSONObject getJsonObject(String filename) {
		File jsonFile = new File(filename);
		InputStream is = null;
		try {
			is = new FileInputStream(jsonFile);
			String jsonTxt = IOUtils.toString(is);
			return new JSONObject(jsonTxt);

		} catch (IOException |JSONException e) {
			log.error(e.getStackTrace());
			return null;
		} 
	}

	public double getComponents(Mat h) {

		double a = h.get(0, 0)[0];
		double b = h.get(0, 1)[0];
		return Math.atan2(b, a);
	}

	public static double round(double value, int places) {
		try {
			if (places < 0) throw new IllegalArgumentException();
			BigDecimal bd = BigDecimal.valueOf(value);
			bd = bd.setScale(places, RoundingMode.HALF_UP);
			return bd.doubleValue();
		} catch (Exception e) {
			return 0;
		}
	}


}
