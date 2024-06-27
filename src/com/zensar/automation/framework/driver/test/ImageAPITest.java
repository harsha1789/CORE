package com.zensar.automation.framework.driver.test;


import java.util.ArrayList;
import java.util.HashMap;

import com.zensar.automation.framework.api.ImageMobileAPI;
import com.zensar.automation.framework.library.ImageDetailsReader;
import com.zensar.automation.framework.model.Image;

public class ImageAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Image image1= new Image();
		image1.setSubImageX(100);
		image1.setSubImageY(100);
		image1.setSubImageWidth(100);
		image1.setSubImageHeight(100);
		
		Image image2= new Image();
		image2.setSubImageX(150);
		image2.setSubImageY(150);
		image2.setSubImageWidth(20);
		image2.setSubImageHeight(20);
		
		ImageMobileAPI imageMobileAPI = new ImageMobileAPI();
		
		System.out.println("Image inside :: "+imageMobileAPI.isImageInside(image2, image1));
		System.out.println("Image Collide :: "+imageMobileAPI.isImagesCollide(image2, image1));
		
		ImageDetailsReader iDetailsReader = new ImageDetailsReader();
		HashMap<String,ArrayList<String>> overlappingCheckDetails=iDetailsReader.readOverlappingCheckDetails("wackyPanda");
		
		ArrayList abc = overlappingCheckDetails.get("BaseScene");
		
		for (int i = 0; i < abc.size(); i++) {
			System.out.println(abc.get(i));
		}
		
		HashMap<String,ArrayList<String[]>> readInsideCheckDetails = iDetailsReader.readInsideCheckDetails("wackyPanda");
		
		ArrayList<String[]> abc1 = readInsideCheckDetails.get("BaseScene");
		
		for (String[] sArray: abc1) {
			System.out.println("In main class::"+sArray[0]);
			System.out.println("In main class 1::"+sArray[1]);
		}
	}

}
