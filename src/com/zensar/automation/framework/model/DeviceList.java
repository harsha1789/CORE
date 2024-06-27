package com.zensar.automation.framework.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Model class for list of devices.
 * @author ak47374
 *
 */
@JsonIgnoreProperties
public class DeviceList implements Serializable {

	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("devices")
	List<DeviceInfo> devices=null;
	
	@JsonProperty("success")
	String success=null;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<DeviceInfo> getDevices() {
		return devices;
	}

	public void setDevices(List<DeviceInfo> devices) {
		this.devices = devices;
	}

	
	
	

	
	
	
}
