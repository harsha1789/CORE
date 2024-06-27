package com.zensar.automation.framework.library;

public class UnableToLoadPropertiesException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UnableToLoadPropertiesException(String msg)
	{
		super(msg);
	}
	public UnableToLoadPropertiesException(String msg,Throwable throwable)
	{
		super(msg,throwable);
	}
}
