package com.zensar.automation.framework.driver.test;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * Random class to perform random tests
 * 
 */
public class RandomTest {
	static	Logger log = Logger.getLogger(RandomTest.class.getName());
	public static void main(String[] args) {
		
		try{
		File f1=new File("D://Automation//ZAF_JAR//BreakAway//Reports//Result//Regression");
		boolean rs=f1.mkdirs();
		if(rs)
		{
			System.out.println("Succcess");
		}
		}catch(Exception e)
		{
			log.error(e.getMessage(),e);
		}
		}
	}


