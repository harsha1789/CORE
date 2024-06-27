package com.zensar.automation.framework.report;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.zensar.automation.framework.driver.DesktopDriver;
import com.zensar.automation.framework.utils.Constant;

import atu.testrecorder.ATUTestRecorder;

public class Desktop_HTML_Report{
	
	Logger log = Logger.getLogger(Desktop_HTML_Report.class.getName());
	
	WebDriver webDesktopDriver;
	String mstrAppendString;
	int mintSummaryCount=0;
	String status;
	int mintDetailCount=0;
	int mintSubStepNo=0;
	int mintPassed=0;
	int mintFailed=0;
	int mintWarnings=0;
	int mintStepNo=0;
	FileWriter mObjFW0;	
	static ListMultimap<String, String> table2 = ArrayListMultimap.create();
	static HashMap<String,String> time= new HashMap<>();
	ATUTestRecorder recorder;
	int ic=0;
	DateFormat mObjDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Calendar mstrCalenderStrt = Calendar.getInstance();
	String mstrStartTime=mObjDateFormat.format(mstrCalenderStrt.getTime());
	Date mDateToday=new Date();
	String gstrCountryName="India";
	
	String strtTym;
	String mstrTCName;
	String mstrTCDesc;
	String mstrModuleName;
	int mintMasterStepNo=0;
	String detailStrtTym;
	//Calendar calStrt;
	//Calendar calEnd;
	//int mintstarttimecounter=0;
	String deviceName;
	String filePath;
	String startTime;
	String videoPath;
	String gameName;
	String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date()); 
	CSSStyleSheet styleSheet=new CSSStyleSheet();
	
	
	public  Desktop_HTML_Report(WebDriver desktopDriver,String deviceName, String filePath, String startTime,
			String mstrTCName, String mstrTCDesc, String mstrModuleName,int mintDetailCount, int mintPassed, int mintFailed,int mintWarnings,int mintStepNo, String status, String gameName)
	{
		log.info("Initiating Desktop HTML report");
		this.webDesktopDriver=desktopDriver;
		this.deviceName=deviceName;
		this.filePath=filePath;
		this.startTime=startTime;
		this.mstrTCName=mstrTCName;
		this.mstrTCDesc=mstrTCDesc;
		this.mstrModuleName=mstrModuleName;
		this.mintDetailCount=mintDetailCount;
		//mintSubStepNo=mintSubStepNo1;
		this.mintPassed=mintPassed;
		this.mintFailed=mintFailed;
		this.mintWarnings=mintWarnings;
		this.mintStepNo=mintStepNo;
		this.status=status;
		this.gameName=gameName;
		
	}
	
	//startUpProxy start=new startUpProxy(mintDetailCount,mintPassed,mintFailed,mintWarnings,mintStepNo,Status);
	public Desktop_HTML_Report(int mintDetailCount2, int mintPassed2, int mintFailed2, int mintWarnings2, int mintStepNo2,
			String status2) {
		this.mintDetailCount=mintDetailCount2;
		this.mintPassed=mintPassed2;
		this.mintFailed=mintFailed2;
		this.mintWarnings=mintWarnings2;
		this.mintStepNo=mintStepNo2;
		this.status=status2;
	}

	public void initDetailTCReport(String testCase,String tCDesc) {
		boolean flag=false;
		//With Map
		for(int count=0;count<table2.get(deviceName).size();count++)
		{
			if(table2.get(deviceName).get(count).equals(DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName))
			{
				flag=true;
			}
		}
		if(!flag)
		{
			 table2.put(deviceName, DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName);
		}
		
		Calendar calStrt = Calendar.getInstance();
		detailStrtTym=mObjDateFormat.format(calStrt.getTime());
		styleSheet.writeStyleSheet(filePath+this.mstrModuleName+"/"+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,filePath );

		try(FileWriter f1=new FileWriter(filePath+this.mstrModuleName+File.separator+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,true)){

		String detailHeader="\n<script>var txt='<title>Detail Test Case Report</title><table align=center width=900px><tr><td width=900px><h1><b>Detail Execution Log "+deviceName+"</b></h1></td></tr></table>'; document.write(txt);</script>";
		f1.append(detailHeader);

		String mstrBottomImg="\n<table align=center width=900px><tr><td width=900px><img src=\"../Images/Bottom.GIF\"></td></tr></table>";
		f1.append(mstrBottomImg);

		String detailHdTable="\n<script>var txt='<table class=subheader align=center width=900px ><tr><td class=subheader width=700px>Test Case: "+testCase+"</td></tr><tr><td class=subheader width=700px>Test Description: "+tCDesc+"</td></tr></table><hr class=divline>'; document.write(txt);</script>";
		f1.append(detailHdTable);

		mstrAppendString="\n<script>var txt1='<table align=center width=900px><tr><td width=900px><a class=tcindex font-weight:bold href=\"../"+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+"Automation_summary"+".html\"><b>Back to Automation Summary</a></tr></td></table><br>'; document.write(txt1);</script>";
		f1.append(mstrAppendString);

		mstrAppendString="<script>var txt='<br><table align=center width=900px class=teststeps><tr><td width=70px class=tsheader>Step#</td><td width=200px class=tsheader>Step Descritption</td><td width=200px class=tsheader>Expected Result</td><td width=200px class=tsheader>Actual Result</td><td width=70px align=center class=tsheader>Status</td><td width=100px class=tsheader>Screen Shot</td></tr>'; document.write(txt);</script>";
		f1.append(mstrAppendString);
		}catch(Exception exception) {
			log.error(exception.getMessage());
		}
	}	
	public String createTimeStampStr()  {
		Calendar mycalendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_hhmmss");

		return formatter.format(mycalendar.getTime());
	}
	
	public void videoRecording(String folderName)
	{
		try
		{
			recorder=new ATUTestRecorder(filePath+"/"+"Videos",DesktopDriver.repo.get(deviceName)+"_"+folderName,true);
			this.videoPath=filePath+"Videos/"+DesktopDriver.repo.get(deviceName)+"_"+folderName+".mov";
			recorder.start();
		Thread.sleep(20);
		}
		catch(Exception e)
		{
			log.error("Error in making video", e);
		}
	}
	
	public void detailsAppend(String stepDesc,String expRes,String actRes,String status)
	{
		
		try {
			File scrFile = ((TakesScreenshot)webDesktopDriver).getScreenshotAs(OutputType.FILE);
			// Now you can do whatever you need to do with it, for example copy somewhere
			String imglinkPath=DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+"/"+ createTimeStampStr() + Constant.JPEGEXTENSION;

			String screenShotPath=filePath+this.mstrModuleName+"/"+imglinkPath;

			FileUtils.copyFile(scrFile, new File(screenShotPath));
			boolean isFileDeleted = scrFile.delete();
			
			if(!isFileDeleted) {
				log.debug("Memory not cleared after taking screen shot, possible memory error.");
			}
			///***************************************************

			mintDetailCount++; 
			//Counts the call to this function from individual test case
			File file = new File(filePath+this.mstrModuleName);
			if (!file.exists()) {
			    if (file.mkdir()) {
			    	log.debug("Directory is created");
			    } else {
			    	log.debug("Failed to create directory");
			    }
			}
			
			try(FileWriter f1=new FileWriter(filePath+this.mstrModuleName+File.separator+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,true)){

			if(mintDetailCount==1){
				initDetailTCReport(this.mstrTCName,mstrTCDesc);
			}
			if(actRes.equals("")&& status.equals(""))
			{
				mintStepNo++;
				mintMasterStepNo=0;
				mintSubStepNo=0;
				mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel1>"+mintStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expRes+"</td><td width=70px class=tsgen></td><td width=70px align=center class=tsgen><b><font size = 2 color = green></td><td></td></tr>'; document.write(txt);</script>";
				f1.append("\n"+mstrAppendString);
			}
			else
			{
				mintSubStepNo++;
				if(status.equalsIgnoreCase("PASS"))
				{
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td>"
							+ "<td width=300px class=tsgen>"+stepDesc+"</td>"
									+ "<td width=70px class=tsgen>"+expRes+"</td>"
											+ "<td width=70px class=tsgen>"+actRes+"</td>"
													+ "<td width=70px align=center class=tsgen><b><font size = 2 color = green>"+status+"</td>"
															+ "<td class=tsind width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"0ges/img.png\"></a></td>"
																	//+ "<td class=tsind width=50px><a target=_blank class=anibutton </a></td>"
																	+ "</tr>'; document.write(txt);</script>";
				}
				else if(status.equalsIgnoreCase("FAIL"))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td>"
							+ "<td width=300px class=tsgen>"+stepDesc+"</td>"
									+ "<td width=70px class=tsgen>"+expRes+"</td>"
											+ "<td width=70px class=tsgen>"+actRes+"</td>"
													+ "<td width=70px align=center class=tsgen><b><font size = 2 color = red>"+status+"</td>"
															+ "<td class=tsind width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td>"
																	//+ "<td class=tsind width=50px><a target=_blank class=anibutton </a></td>"
																	+ "</tr>'; document.write(txt);</script>";
				}
				else if(status.equalsIgnoreCase("VIDEO"))
				{
					File file1 = new File(filePath+"Videos");
			        if (!file1.exists()) {
			            if (file1.mkdir()) {
			            	log.debug("Directory is created!!");
			            } else {
			            	log.debug("Failed to create directory!!");
			            }
			        }
					if(mintMasterStepNo==0){ 
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr>"
							+ "<td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td>"
							+ "<td width=300px class=tsgen>"+stepDesc+"</td>"
							+ "<td width=70px class=tsgen>"+expRes+"</td>"
							+ "<td width=70px class=tsgen>"+actRes+"</td>"
							+ "<td width=70px align=center class=tsgen><b><font size = 2 color = green>"+status+"</td>"
						    + "<td class=tsind width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td>"
						    + "<td class=tsind width=50px><a class=anibutton  href=\"file:///"+videoPath+"\" ><img class=screen src=\"file:///"+filePath+"Images/videoImage.png\"></a></td>"
						    + "</tr>'; document.write(txt);</script>";
				}
				
				else if(status.equalsIgnoreCase(Constant.INTERRUPTED))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td></td><td width=600px><b><font size = 2 color = Brown>"+stepDesc+"</td><td></td><td></td><td><b><font size = 2 color = Brown>"+status+"</td><td align=center width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				f1.append("\n"+mstrAppendString);
			}
			}
			if(mintFailed==0)
			{
				mintPassed=mintStepNo;
				summary(DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "PASS");
			}
			else
			{
				mintPassed=mintStepNo-mintFailed;
				summary(DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "FAIL");
			}
		} catch (WebDriverException webDriverException) {
			log.error(webDriverException.getMessage(),webDriverException);
			detailsAppendNoScreenshot("Webdriver Exception occur ,hence skipping the current test step", "", "", "Fail");
		} catch (IOException ioException) {
			log.error(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");

		}
	}
	
	public void detailsAppendFolder(String step_desc,String Exp_Res,String Act_Res,String Status, String folderName)
	{
		try {
			//Captures screenshot for calling step
			File scrFile = ((TakesScreenshot)webDesktopDriver).getScreenshotAs(OutputType.FILE);
			// Now you can do whatever you need to do with it, for example copy somewhere
			String imglinkPath=DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+"/"+folderName+"/"+ createTimeStampStr() + Constant.JPEGEXTENSION;
			String screenShotPath=filePath+this.mstrModuleName+File.separator+imglinkPath;
			FileUtils.copyFile(scrFile, new File(screenShotPath));
			
			boolean isFileDeleted= scrFile.delete();
			if(!isFileDeleted) {
				log.debug("Memory not cleared after taking screen shot, possible memory error");
			}
			
			///***************************************************
			mintDetailCount++; //Counts the call to this function from individual test case

			try(FileWriter f1=new FileWriter(filePath+this.mstrModuleName+File.separator+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,true)){

			if(mintDetailCount==1){
				initDetailTCReport(this.mstrTCName,mstrTCDesc);
			}

			if(Act_Res.equals("")&& Status.equals(""))
			{
				mintStepNo++;
				mintMasterStepNo=0;
				mintSubStepNo=0;
				mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel1>"+mintStepNo+"</td><td width=300px class=tsgen>"+step_desc+"</td><td width=70px class=tsgen>"+Exp_Res+"</td><td width=70px class=tsgen></td><td width=70px align=center class=tsgen><b><font size = 2 color = green></td><td></td></tr>'; document.write(txt);</script>";
				f1.append("\n"+mstrAppendString);
			}
			else
			{
				mintSubStepNo++;
				if(Status.equalsIgnoreCase("PASS"))
				{
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+step_desc+"</td><td width=70px class=tsgen>"+Exp_Res+"</td><td width=70px class=tsgen>"+Act_Res+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = green>"+Status+"</td><td class=tsind width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				else if(Status.equalsIgnoreCase("FAIL"))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+step_desc+"</td><td width=70px class=tsgen>"+Exp_Res+"</td><td width=70px class=tsgen>"+Act_Res+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = red>"+Status+"</td><td class=tsind width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				else if(Status.equalsIgnoreCase(Constant.INTERRUPTED))
				{
					if(mintMasterStepNo==0){
						mintFailed++;
						mintMasterStepNo++;
					}
					mstrAppendString="<script>var txt='<tr><td></td><td width=600px><b><font size = 2 color = Brown>"+step_desc+"</td><td></td><td></td><td><b><font size = 2 color = Brown>"+Status+"</td><td align=center width=50px><a target=_blank class=anibutton href=\"./"+imglinkPath+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				}
				f1.append("\n"+mstrAppendString);
			}
			}
			if(mintFailed==0)
			{
				mintPassed=mintStepNo;
				summary(DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "PASS");
			}
			else
			{
				mintPassed=mintStepNo-mintFailed;
				summary(DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "FAIL");
			}
		} catch (WebDriverException webDriverException) {
			log.error(webDriverException.getMessage(),webDriverException);
			detailsAppendNoScreenshot("Webdriver Exception occur ,hence skipping the current test step", "", "", "Fail");

		} catch (IOException ioException) {
			log.error(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");

		}
	}
	
	public void detailsAppendNoScreenshot(String stepDesc,String expRes,String actRes,String status)
	{
		mintDetailCount++; //Counts the call to this function frmo individual test case

		try(FileWriter f1=new FileWriter(filePath+this.mstrModuleName+File.separator+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,true)){

		if(mintDetailCount==1){
			initDetailTCReport(this.mstrTCName,mstrTCDesc);
		}

		if(actRes.equals("")&& status.equals(""))
		{
			mintStepNo++;
			mintMasterStepNo=0;
			mintSubStepNo=0;
			mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel1>"+mintStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expRes+"</td><td width=70px class=tsgen></td><td width=70px align=center class=tsgen><b><font size = 2 color = green></td><td></td></tr>'; document.write(txt);</script>";
			f1.append("\n"+mstrAppendString);
		}
		else
		{
			mintSubStepNo++;
			if(status.equalsIgnoreCase("PASS"))
			{
				mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expRes+"</td><td width=70px class=tsgen>"+actRes+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = green>"+status+"</td><td class=tsind width=50px></td></tr>'; document.write(txt);</script>";
			}
			else if(status.equalsIgnoreCase("FAIL"))
			{
				if(mintMasterStepNo==0){
					mintFailed++;
					mintMasterStepNo++;
				}
				mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expRes+"</td><td width=70px class=tsgen>"+actRes+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = red>"+status+"</td><td class=tsind width=50px></td></tr>'; document.write(txt);</script>";
			}
			else if(status.equalsIgnoreCase(Constant.INTERRUPTED))
			{
				if(mintMasterStepNo==0){
					mintFailed++;
					mintMasterStepNo++;
				}
				mstrAppendString="<script>var txt='<tr><td></td><td width=600px><b><font size = 2 color = Brown>"+stepDesc+"</td><td></td><td></td><td><b><font size = 2 color = Brown>"+status+"</td></tr>'; document.write(txt);</script>";
			}
			f1.append("\n"+mstrAppendString);
		}
		
		if(mintFailed==0)
		{
			mintPassed=mintStepNo;
			summary(DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "PASS");
		}
		else
		{
			mintPassed=mintStepNo-mintFailed;
			summary(DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "FAIL");
		}
		}catch (IOException ioException) {
			log.error(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");

		}
	}

	public void summary(String testCase,String module,String description,String steps,String passed,String failed,String warnings,String status){
		//*************************************************************
		//		File f1=new File(Global.gstrResultPath+"Dump");
		//		f1.mkdir();
		//		
		File file = new File(filePath+"Dump");
        if (!file.exists()) {
            if (file.mkdir()) {
            	log.debug("Directory is created!");
            } else {
            	log.debug("Failed to create directory!");
            }
        }
		
		String str=filePath+"Dump/"+testCase+".txt";
		try{
			 mObjFW0=new FileWriter(str);
		//**********************************************************************************************
		if(status.equalsIgnoreCase("PASS")){
			String summary="\n<script>var txt='<tr><td width=100px align=center class=tsgen><a class=tcindex href=\"./"+this.mstrModuleName+"/"+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+".html\">"+testCase+"</a></td><td width=80px class=tsgen>"+module+"</td><td width=200px class=tsgen>"+description+"</td><td width=70px align=center class=tsgen>"+steps+"</td><td width=70px align=center class=tsgen>"+passed+"</td><td width=70px align=center class=tsgen>"+failed+"</td><td width=70px align=center class=tsgen>"+warnings+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = green>"+status+"</td></tr>'; document.write(txt);</script>";
			mObjFW0.write(summary);
		}//file:/
		//+Global.gstrResultPath
		else if(status.equalsIgnoreCase("FAIL")){

			String summary="\n<script>var txt='<tr><td width=100px align=center class=tsgen><a class=tcindex href=\"./"+this.mstrModuleName+"/"+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+".html\">"+testCase+"</a></td><td width=80px class=tsgen>"+module+"</td><td width=200px class=tsgen>"+description+"</td><td width=70px align=center class=tsgen>"+steps+"</td><td width=70px align=center class=tsgen>"+passed+"</td><td width=70px align=center class=tsgen>"+failed+"</td><td width=70px align=center class=tsgen>"+warnings+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = red>"+status+"</td></tr>'; document.write(txt);</script>";
			mObjFW0.write(summary);
		}
		mObjFW0.close();
		}catch(IOException ioException) {
			log.error(ioException.getMessage(), ioException);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");

		}
		
	}//end of summary


	public void endReport() 
	{
		try {
			mObjFW0=new FileWriter(filePath+this.mstrModuleName+File.separator+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+Constant.HTMLEXTENSION,true);

			Calendar calEnd = Calendar.getInstance();
			String endTime=mObjDateFormat.format(calEnd.getTime());

			Date date1 = mObjDateFormat.parse(detailStrtTym);
			Date date2 = mObjDateFormat.parse(endTime);
			long difference = date2.getTime()-date1.getTime();

			long var1=difference%1000;
			difference=(difference/1000);
			String duraton=difference+"."+var1;
			String summary1="\n<script>var txt='</table><br><br><table width=900px align = center><tr><td width=650px></td><td colspan=2 class=tsheader>Execution Time</td></tr><tr><td width=650px></td><td class=pfhead width=120px>Start Time</td><td class=pfind width=130px>"+detailStrtTym+"</td></tr><tr><td width=650px></td><td class=pfhead width=120px>End Time</td><td class=pfind width=130px>"+endTime+"</td></tr><tr><td width=650px></td><td class=pfhead width=120px>Duration</td><td class=pfind width=130px>"+duraton+" secs</td></tr></table>'; document.write(txt);</script>";
			mObjFW0.append(summary1);
			mObjFW0.close();
		} catch (IOException | ParseException e) {
			log.error(e.getMessage(),e);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");

		}
	}

	public void buildAutomationSummary() 
	{
		//*******************************************
		// Initially writes a schema for automation summary report
		//*******************************************
		try {
			styleSheet.writeStyleSheet(filePath+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+"Automation_summary"+Constant.HTMLEXTENSION,filePath);
			mObjFW0=new FileWriter(filePath+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+"Automation_summary"+Constant.HTMLEXTENSION,true);

			mstrAppendString="\n<script>var txt='<title>Automation summary</title><table width=900px align=center class=reportheader><tr><td height=50px>Zensar Technologies<BR><td height=50px align=center>"+gstrCountryName+"</td></tr></table>'; document.write(txt);</script>";
			mObjFW0.append(mstrAppendString);

			String mstrBottomImg="\n</head><table align=center width=900px><tr><td width=900px><img src=\"./Images/Bottom.GIF\"></td></tr></table>";
			mObjFW0.append(mstrBottomImg);

			String summary="\n<script>var txt='<br><br><table align=center width=900px class=teststeps id=\"summary_table\"><tr><td align=center width=100px class=tsheader>Test_Case</td><td width=80px class=tsheader>Module</td><td width=200px class=tsheader>Description</td><td width=70px class=tsheader>Steps</td><td width=70px class=tsheader>Passed</td><td width=70px class=tsheader>Failed</td><td width=70px class=tsheader>Warnings</td><td width=70px class=tsheader>Status</td></tr>'; document.write(txt);</script>";
			mObjFW0.append(summary);

			//*****************************************
			//with Map
			
			File file = new File(filePath+File.separator+"Dump");
			if (!file.exists()) {
			    if (file.mkdir()) {
			    	log.debug("Directory is created!");
			    } else {
			    	log.debug("Failed to create directory!");
			    }
			}
			
			for(int i=0;i<table2.get(deviceName).size();i++)
			{
				String str=filePath+"/"+"Dump/"+table2.get(deviceName).get(i)+".txt";
				File f=new File(str);
				FileReader fr=new FileReader(f);
				try(BufferedReader br = new BufferedReader(fr)){

				//StringBuilder sb = new StringBuilder();
				String line = br.readLine();

				while (line != null) {
					mObjFW0.append(line);
					mObjFW0.append("\n");
					line = br.readLine();
				}
				}
				fr.close();
				f.deleteOnExit();
			}
			
			Calendar calEnd = Calendar.getInstance();
			String endtime=mObjDateFormat.format(calEnd.getTime());
			Date date3 = mObjDateFormat.parse(startTime);
			Date date4 = mObjDateFormat.parse(endtime);
			long difference1 = date4.getTime() - date3.getTime();
			long var=difference1%1000;
			difference1=(difference1/1000);
			String duration=difference1+"."+var;

			String summary2="\n<script>var txt='</table><br><br><table width=900px align=center><tr><td width=650px></td><td colspan=2 class=tsheader>Execution Time</td></tr><tr><td width=650px></td><td class=pfhead width=120px>Start Time</td><td class=pfind width=130px>"+startTime+"</td></tr><tr><td width=650px></td><td class=pfhead width=120px>End Time</td><td class=pfind width=130px>"+endtime+"</td></tr><tr><td width=650px></td><td class=pfhead width=120px>Duration</td><td class=pfind width=130px>"+duration+" secs</td></tr></table>'; document.write(txt);</script>";
			mObjFW0.append(summary2);
			
			mObjFW0.close();
		} catch (IOException | ParseException exception) {
			log.error(exception.getMessage(),exception);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");

		}
	}
	
	/**
     * Author : Kamal Kumar Vishwakarma
     * Description : This function creates the defect log for the defect.
     * @param stepDesc
     * @param expRes
     * @param actRes
     * @param report
     * @param status
     * @throws Exception
     */
    
     public void detailsAppend(String stepDesc,String expRes,String actRes,String report,String status) {

            try {
				String defectlog=filePath+"DefectLogs/"+this.mstrModuleName+"/"+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+"/"+ createTimeStampStr() + ".txt";
				FileUtils.writeStringToFile(new File(defectlog), report, StandardCharsets.UTF_8);

				/*Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(defectlog), "UTF8"));
				out.append(report).append("\r\n\n");

				out.flush();
				out.close();*/

				mintDetailCount++;

         try(FileWriter f1 = new FileWriter(filePath+this.mstrModuleName+File.separator+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+".html",true)){
				if(mintDetailCount == 1) {
				       initDetailTCReport(this.mstrTCName,mstrTCDesc);
				}
				if(actRes.equals("")&& status.equals(""))
				{
				       mintStepNo++;
				       mintMasterStepNo=0;
				       mintSubStepNo=0;
				       mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel1>"+mintStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expRes+"</td><td width=70px class=tsgen></td><td width=70px align=center class=tsgen><b><font size = 2 color = green></td><td></td></tr>'; document.write(txt);</script>";
				       f1.append("\n"+mstrAppendString);
				}
				else
				{
				       mintSubStepNo++;
				       if(status.equalsIgnoreCase("PASS"))
				       {
				             mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expRes+"</td><td width=70px class=tsgen>"+actRes+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = green>"+status+"</td><td class=tsind width=50px><a target=_blank class=anibutton href=\"file:///"+defectlog+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				       }
				       else if(status.equalsIgnoreCase("FAIL"))
				       {
				             if(mintMasterStepNo==0){
				                    mintFailed++;
				                    mintMasterStepNo++;
				             }
				             mstrAppendString="<script>var txt='<tr><td width=70px class=tsindlevel2>"+mintStepNo+"."+mintSubStepNo+"</td><td width=300px class=tsgen>"+stepDesc+"</td><td width=70px class=tsgen>"+expRes+"</td><td width=70px class=tsgen>"+actRes+"</td><td width=70px align=center class=tsgen><b><font size = 2 color = red>"+status+"</td><td class=tsind width=50px><a target=_blank class=anibutton href=\"file:///"+defectlog+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				       }
				       else if(status.equalsIgnoreCase(Constant.INTERRUPTED))
				       {
				             if(mintMasterStepNo==0){
				                    mintFailed++;
				                    mintMasterStepNo++;
				             }
				             mstrAppendString="<script>var txt='<tr><td></td><td width=600px><b><font size = 2 color = Brown>"+stepDesc+"</td><td></td><td></td><td><b><font size = 2 color = Brown>"+status+"</td><td align=center width=50px><a target=_blank class=anibutton href=\"file:///"+defectlog+"\"><img class=screen src=\"file:///"+filePath+"Images/img.png\"></a></td></tr>'; document.write(txt);</script>";
				       }
				       f1.append("\n"+mstrAppendString);
				}
         }
       
       
         
				if(mintFailed==0)
				{
				       mintPassed=mintStepNo;
				       summary(DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "PASS");
				}
				else
				{
				       mintPassed=mintStepNo-mintFailed;
				       summary(DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+mstrTCName,mstrModuleName, mstrTCDesc, String.valueOf(mintStepNo), String.valueOf(mintPassed), String.valueOf(mintFailed), String.valueOf(mintWarnings), "FAIL");
				}
			} catch (IOException e) {
			log.error(e.getMessage(),e);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");

			}
     }
     
     
     /**
      * This method capture only screen shots, not include in report
      * @param folderName
      */
     
     public void detailsAppendFolderOnlyScreenShot(String folderName)
     {
 		try {
			//Captures screenshot for calling step
			File scrFile = ((TakesScreenshot)webDesktopDriver).getScreenshotAs(OutputType.FILE);
			// Now you can do whatever you need to do with it, for example copy somewhere
			String screenShotPath=filePath+this.mstrModuleName+"/"+DesktopDriver.repo.get(deviceName)+"_"+deviceName+"_"+gameName+"_"+this.mstrTCName+"/"+folderName+"/"+ createTimeStampStr() + Constant.JPEGEXTENSION;

			FileUtils.copyFile(scrFile, new File(screenShotPath));
			boolean isFileDeleted= scrFile.delete();
			if(!isFileDeleted) {
				log.debug("Memory not cleared after taking screen shot, possible memory error");
			}
			///***************************************************
		} catch (WebDriverException webDriverException) {
			log.error(webDriverException.getMessage(),webDriverException);
			detailsAppendNoScreenshot("Webdriver Exception occur ,hence skipping the current test step", "", "", "Fail");
		} catch (IOException ioException) {
			log.error(ioException.getMessage(),ioException);
			detailsAppendNoScreenshot("IoException occur ,hence skipping the current test step", "", "", "Fail");
		}
 	}
   
 	
}
