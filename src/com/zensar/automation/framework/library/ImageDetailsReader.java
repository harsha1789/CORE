package com.zensar.automation.framework.library;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.openqa.selenium.ScreenOrientation;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.zensar.automation.framework.model.Image;
import com.zensar.automation.framework.model.OrientationImageDetails;

public class ImageDetailsReader {

	String assetsPath="D:\\ImageComparision\\";
	
	public Map<String, Image> readImageDetails(JsonArray jsonArray, String orientation, String gameName) {

		Map<String, Image> imageMap = new HashMap<String, Image>();
		
		

		for (JsonElement jsonElement : jsonArray) {
			Image image = new Image();
			String id = jsonElement.getAsJsonObject().get("id").getAsString();
			String name = jsonElement.getAsJsonObject().get("name").getAsString();
			int xMinus = jsonElement.getAsJsonObject().get("xMinus").getAsInt();
			int yMinus = jsonElement.getAsJsonObject().get("yMinus").getAsInt();
			int width = jsonElement.getAsJsonObject().get("width").getAsInt();
			int height = jsonElement.getAsJsonObject().get("height").getAsInt();
			String imageFullPath=assetsPath +"\\"+gameName+"\\"+orientation.toString().toLowerCase() + "\\"+name;
			
			String imageAsEncodedString = getImageAsEncodedString(imageFullPath);

			image.setId(id);
			image.setName(name);
			image.setxMinus(xMinus);
			image.setyMinus(yMinus);
			image.setHeight(height);
			image.setWidth(width);
			image.setImageAsEncodedString(imageAsEncodedString);
			image.setImageFullPath(imageFullPath);

			imageMap.put(id, image);

		}
		return imageMap;
	}

	public OrientationImageDetails readImageDetails(ScreenOrientation orientation, String gameName) {

		return readImageDetails(orientation.toString().toLowerCase(),gameName);

	}
	
	public OrientationImageDetails readImageDetails(String orientation, String gamename) {

		OrientationImageDetails orientationImageDetails = new OrientationImageDetails();

		try {
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(new FileReader(assetsPath+gamename+ "\\Dimension.json"));
			JsonObject jsonObject = (JsonObject) obj;
			JsonObject portraitJson = jsonObject.getAsJsonObject(orientation);
			//System.out.println();
			JsonObject portraitDimensionJson = portraitJson.getAsJsonObject("Dimension");
			int x = portraitDimensionJson.get("x").getAsInt();
			int y = portraitDimensionJson.get("y").getAsInt();
			JsonArray imagesJsonArray = portraitJson.getAsJsonArray("Images");
			Map<String, Image> imageMap = readImageDetails(imagesJsonArray,orientation.toLowerCase(),gamename );
			orientationImageDetails.getDimension().setX(x);
			orientationImageDetails.getDimension().setY(y);

			orientationImageDetails.setImagesMap(imageMap);

		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return orientationImageDetails;

	}
	
	
	public HashMap<String,ArrayList<String>> readOverlappingCheckDetails(String gamename) {
		
		System.out.println("readOverlappingCheckDetails ");

		HashMap<String,ArrayList<String>> overlappingCheckDetails= new HashMap();
		

		try {
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(new FileReader(assetsPath+gamename+ "\\Dimension.json"));
			JsonObject jsonObject = (JsonObject) obj;
			JsonObject overlappingCheckJson = jsonObject.getAsJsonObject("overlappingCheck");
			for (Entry<String, JsonElement> valueEntry : overlappingCheckJson.entrySet()) {
				//System.out.println("Value Entry Key :: "+valueEntry.getKey());
				//System.out.println("Value Entry Value :: "+valueEntry.getValue().getAsJsonArray());
				ArrayList<String> valuesList= new ArrayList<String>();
				JsonArray jsonArray = valueEntry.getValue().getAsJsonArray();
				for (JsonElement jsonElement : jsonArray) {
					
					valuesList.add(jsonElement.getAsString());
					
				}
				overlappingCheckDetails.put(valueEntry.getKey(), valuesList);
			}
			

		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return overlappingCheckDetails;

	}
	
	
	public HashMap<String,ArrayList<String[]>> readInsideCheckDetails(String gamename) {
		
		System.out.println("readOverlappingCheckDetails ");

		HashMap<String,ArrayList<String[]>> insideCheckDetails= new HashMap<String,ArrayList<String[]>>();
		

		try {
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(new FileReader(assetsPath+gamename+ "\\Dimension.json"));
			JsonObject jsonObject = (JsonObject) obj;
			JsonObject overlappingCheckJson = jsonObject.getAsJsonObject("insideCheck");
			for (Entry<String, JsonElement> valueEntry : overlappingCheckJson.entrySet()) {
				//system.out.println("Value Entry Key :: "+valueEntry.getKey());
				//system.out.println("Value Entry Value :: "+valueEntry.getValue().getAsJsonArray());
				ArrayList<String[]> valuesList= new ArrayList<String[]>();
				JsonArray jsonArray = valueEntry.getValue().getAsJsonArray();
				for (JsonElement jsonElement : jsonArray) {
					String[] images =new String[2];
					
					JsonObject jObject=jsonElement.getAsJsonObject();
					images[0]=jObject.get("innerImage").getAsString();
					images[1]=jObject.get("outerImage").getAsString();
					//system.out.println("innerImage :: "+images[0]);
					//system.out.println("outerImage :: "+images[1]);
					valuesList.add(images);
				}
				
				insideCheckDetails.put(valueEntry.getKey(), valuesList);
				
			}
			

		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return insideCheckDetails;

	}
	
	public String getImageAsEncodedString(String path) {
		String imgEncodeString = null;
		File refImgFile = new File(path);

		try {
			if (refImgFile.exists()) {
				imgEncodeString = Base64.getEncoder().encodeToString(Files.readAllBytes(refImgFile.toPath()));
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return imgEncodeString;
	}
	
	
	public ArrayList<String> getSupportedLanguages(String gamename) {
		
		System.out.println("Reading supported languages....");

		ArrayList<String> supportedLanguages= new ArrayList<String>();
		

	 try{
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(new FileReader(assetsPath+gamename+ "\\Dimension.json"));
			JsonObject jsonObject = (JsonObject) obj;
			String supportLangStr =  jsonObject.get("supportedLanguages").getAsString();
			StringTokenizer stringTokenizer=new  StringTokenizer(supportLangStr,",");
			//system.out.println(stringTokenizer.countTokens());		
			while(stringTokenizer.hasMoreTokens()){
				supportedLanguages.add(stringTokenizer.nextToken());
			}
			

		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return supportedLanguages;

	}
	
	
	public HashMap<String,ArrayList<String[]>> readLanguageheckDetails(String gamename) { 
		
		System.out.println("readOverlappingCheckDetails ");

		HashMap<String,ArrayList<String[]>> langCheckDetails= new HashMap<String,ArrayList<String[]>>();
		

		try {
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(new FileReader(assetsPath+gamename+ "\\Dimension.json"));
			JsonObject jsonObject = (JsonObject) obj;
			JsonObject overlappingCheckJson = jsonObject.getAsJsonObject("languageCheck");
			for (Entry<String, JsonElement> valueEntry : overlappingCheckJson.entrySet()) {
				//system.out.println("Value Entry Key :: "+valueEntry.getKey());
				//system.out.println("Value Entry Value :: "+valueEntry.getValue().getAsJsonArray());
				ArrayList<String[]> valuesList= new ArrayList<String[]>();
				JsonArray jsonArray = valueEntry.getValue().getAsJsonArray();
				for (JsonElement jsonElement : jsonArray) {
					
					JsonObject langObject = jsonElement.getAsJsonObject();
					
					//System.out.println("Value Entry Value :: "+valueEntry1.getValue().getAsString());
					String[] images =new String[2];
					
					
					images[0]=langObject.get("id").getAsString();
					images[1]=langObject.get("langKey").getAsString();
					//system.out.println("id  :: "+images[0]);
					//system.out.println("langKey :: "+images[1]);
					valuesList.add(images);
				}
				
				langCheckDetails.put(valueEntry.getKey(), valuesList);
				
			}
			

		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return langCheckDetails;

	}


}
