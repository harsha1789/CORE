package com.zensar.automation.framework.model;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrientationImageDetails {
	
	Dimension dimension=null;
	
	List<Image> images=null;
	
	Map<String,Image> imagesMap =new HashMap<String,Image>();

	public Dimension getDimension() {
		if(dimension==null){
			dimension=new Dimension();
		}
		return dimension;
	}

	public Map<String, Image> getImagesMap() {
		return imagesMap;
	}

	public void setImagesMap(Map<String, Image> imagesMap) {
		this.imagesMap = imagesMap;
	}

	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

}
