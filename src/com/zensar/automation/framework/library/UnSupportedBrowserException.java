package com.zensar.automation.framework.library;

public class UnSupportedBrowserException extends Exception {

	/**
	 * default serial version
	 */
	private static final long serialVersionUID = 1L;
	
	public UnSupportedBrowserException(String msg)
	{
		super(msg);
	}
	public UnSupportedBrowserException(String msg,Throwable throwable)
	{
		super(msg,throwable);
	}

}
