package com.zensar.automation.framework.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Owner implements Serializable {
	
	/**
	 * Default serial version
	 */
	private static final long serialVersionUID = 1L;


	@JsonProperty("email")
	String email;
	

	@JsonProperty("group")
	String group;
	
	@JsonProperty("name")
	String name;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
