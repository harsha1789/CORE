package com.zensar.automation.framework.model;



public class Image {
	
	String id;
	String name;
	int xMinus;
	int yMinus;
	int width;
	int height;
	
	int subImageX;
	int subImageY;
	
	int subImageWidth;
	int subImageHeight;
	
	
	String imageFullPath;
	public String getImageFullPath() {
		return imageFullPath;
	}
	public void setImageFullPath(String imageFullPath) {
		this.imageFullPath = imageFullPath;
	}
	String imageAsEncodedString;
	
	
	public String getImageAsEncodedString() {
		return imageAsEncodedString;
	}
	public void setImageAsEncodedString(String imageAsEncodedString) {
		this.imageAsEncodedString = imageAsEncodedString;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getxMinus() {
		return xMinus;
	}
	public void setxMinus(int xMinus) {
		this.xMinus = xMinus;
	}
	public int getyMinus() {
		return yMinus;
	}
	public void setyMinus(int yMinus) {
		this.yMinus = yMinus;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getSubImageX() {
		return subImageX;
	}
	public void setSubImageX(int subImageX) {
		this.subImageX = subImageX;
	}
	public int getSubImageY() {
		return subImageY;
	}
	public void setSubImageY(int subImageY) {
		this.subImageY = subImageY;
	}
	public int getSubImageWidth() {
		return subImageWidth;
	}
	public void setSubImageWidth(int subImageWidth) {
		this.subImageWidth = subImageWidth;
	}
	public int getSubImageHeight() {
		return subImageHeight;
	}
	public void setSubImageHeight(int subImageHeight) {
		this.subImageHeight = subImageHeight;
	}
	 
	

}
