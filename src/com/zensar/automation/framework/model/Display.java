package com.zensar.automation.framework.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for device display details
 * @author ak47374
 *
 */
@JsonIgnoreProperties
public class Display implements Serializable {
	
	/**
	 * serial version
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("height")
	public int height;
	
	@JsonProperty("width")
	public int width;
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getheight() {
		return height;
	}

	public void setheight(int height) {
		this.height = height;
	}

	
	
	
	
}
