package com.zensar.automation.framework.library;



import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class LanguageReader {

	public HashMap<String,String>  getLanguageDetails(String language) {
		
		HashMap <String,String> langMap= new HashMap<String,String>();

		String jsonFilesPath =PropertyReader.getInstance().getProperty("jsonFilesPath");
		
		try {
			JsonParser parser = new JsonParser();
			Object obj = parser.parse(new FileReader(jsonFilesPath+language+"\\language.json"));
			JsonObject jsonObject = (JsonObject) obj;
			Set<Entry<String, JsonElement>> jsonEntry = jsonObject.entrySet();
			

			for (Entry<String, JsonElement> valueEntry : jsonEntry) {
				JsonElement element = valueEntry.getValue();
				String key = valueEntry.getKey();
				String value = element.getAsJsonObject().get("StringValue").getAsString();
				langMap.put(key, value);
			}

		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
		return langMap;
	}

}
