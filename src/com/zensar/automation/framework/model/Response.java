package com.zensar.automation.framework.model;

public class Response {
	int parentStatusCode;
	String jsonResponse;

	public Response(int parentStatusCode, String jsonResponse) {
		super();
		this.parentStatusCode = parentStatusCode;
		this.jsonResponse = jsonResponse;
	}
	
	public int getparentStatusCode() {
		return parentStatusCode;
	}

	public void setparentStatusCode(int parentStatusCode) {
		this.parentStatusCode = parentStatusCode;
	}

	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}





}