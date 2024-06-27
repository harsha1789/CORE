package com.zensar.automation.framework.report;
import java.io.FileWriter;

import org.apache.log4j.Logger;



public class CSSStyleSheet {
	
	
	Logger log = Logger.getLogger(CSSStyleSheet.class.getName());
	

	public void writeStyleSheet(String path, String gstrResultPath ){
		try(FileWriter f0=new FileWriter(path);){
		//String Style_Sheet="";
		//Style_Sheet.concat(Style_Sheet);
		

		String mstrstyleStrt="<!DOCTYPE html><head>\n\n\n<style type='text/css'> \nbody \n{font-size: 70%;color:#000000;margin:10px;background-color:#ffffff;}\nbody, p, h1, h2, h3, table, td, th, ul, ol, textarea, input \n{font-family: verdana,helvetica,arial,sans-serif;}";	
		f0.append(mstrstyleStrt);

		String mstrStyleTable="\ntable, input, textarea {font-size: 100%;}";
		f0.append(mstrStyleTable);

		String mstrReportHeader="\ntable.reportheader \n{font-size: 150%;font-weight:bold;text-align:left;color:#992233;padding:3px;border:0px none;border-collapse:collapse;}";
		f0.append(mstrReportHeader);

		String mstrTestSteps="\ntable.teststeps \n{background-color:White;color:#000000;border:1px solid #000000;border-collapse:collapse;}";
		f0.append(mstrTestSteps);

		String mstrTableSubheader="\ntable.subheader \n{font-size: 100%;font-weight:bold;color: Black;border:0px;background-color:transparent;border-collapse:collapse;}";
		f0.append(mstrTableSubheader);

		String mstrTbTime="\ntable.tbtime \n{background-color:#FFFFCC;color:#000000;border:1px solid #000000;border-collapse:collapse;left:660px;}";
		f0.append(mstrTbTime);

		String mstrReleaseSummary="\ntable.releasesummary \n{background-color:#153E7E;color:#000000;border:1px solid #000000;border-collapse:collapse;position:absolute;left:335px;}";
		f0.append(mstrReleaseSummary);

		String mstrPfSummary="\ntable.pfsummary \n{background-color:#153E7E;color:#000000;border:1px solid #000000;border-collapse:collapse;position:absolute;left:335px;}";
		f0.append(mstrPfSummary);

		String mstrTbLinks="\ntable.tblinks \n{background-color:#FFFFCC;color:#000000;border:1px solid #000000;border-collapse:collapse;}";
		f0.append(mstrTbLinks);

		String mstrTdSubheader="\ntd.subheader \n{font-size:120%;font-weight:bold;padding-top:5px;padding-bottom:1px;background-color:transparent;}";
		f0.append(mstrTdSubheader);

		String mstrTdSubcontents="\ntd.subcontents \n{font-size:120%;padding-top:1px;padding-bottom:1px;background-color:transparent;}";
		f0.append(mstrTdSubcontents);

		String mstrTsHeader="\ntd.tsheader \n{color:White;background-color:#153E7E;font-size: 120%;font-weight:bold;padding: 0px 0px 0px 0px;border:1px solid #000000;height:40px}";
		f0.append(mstrTsHeader);

		String mstrTsReleaseHeader="\ntd.releaseheader \n{color:#ffffgg;background-color:#153E7E;font-size: 120%;font-weight:bold;text-align:center;padding: 1px 0px 0px 0px;border:1px solid #000000;}";
		f0.append(mstrTsReleaseHeader);

		String mstrTdTsgen="\ntd.tsgen \n{font-size: 100%;font-weight:normal;padding-left:2px;border:1px solid #000000;}";
		f0.append(mstrTdTsgen);

		String mstrTdTsgenfail="\ntd.tsgenfail \n{font-size: 100%;font-weight:bold;color:red;padding-left:2px;border:1px solid #000000;}";
		f0.append(mstrTdTsgenfail);

		String mstrTdTsgenlevel1="\ntd.tsgenlevel1 \n{font-size: 100%;font-weight:normal;padding-left:2px;border:1px solid #000000;background-color: White;}";
		f0.append(mstrTdTsgenlevel1);

		String mstrTdTsgenfaillevel1="\ntd.tsgenfaillevel1 \n{font-size: 100%;font-weight:bold;color:red;padding-left:2px;border:1px solid #000000;background-color: White;}";
		f0.append(mstrTdTsgenfaillevel1);

		String mstrTdTsind="\ntd.tsind \n{font-size: 100%;font-weight:normal;padding-left:15px;padding-right: 2px;border:1px solid #000000;}";
		f0.append(mstrTdTsind);

		String mstrTdTsindfail="\ntd.tsindfail \n{font-size: 100%;font-weight:bold;color:red;padding-left:15px;padding-right: 2px;border:1px solid #000000;}";
		f0.append(mstrTdTsindfail);

		String mstrTdTsindlevel1="\ntd.tsindlevel1 \n{font-size: 100%;font-weight:normal;padding-left:15px;padding-right: 2px;border:1px solid #000000;background-color: White;}";
		f0.append(mstrTdTsindlevel1);

		String mstrTdTsindfaillevel1="\ntd.tsindfaillevel1 \n{font-size: 100%;font-weight:bold;color:red;padding-left:15px;padding-right: 2px;border:1px solid #000000;background-color: White;}";
		f0.append(mstrTdTsindfaillevel1);

		String mstrTdTsindlevel2="\ntd.tsindlevel2 \n{font-size: 100%;font-weight:normal;padding-left:25px;padding-right: 2px;border:1px solid #000000;}";
		f0.append(mstrTdTsindlevel2);

		String mstrTdTsindfaillevel2="\ntd.tsindfaillevel2 \n{font-size: 100%;font-weight:bold;color:red;padding-left:25px;padding-right: 2px;border:1px solid #000000;}";
		f0.append(mstrTdTsindfaillevel2);

		String mstrTdTsindlevel3="\ntd.tsindlevel3 \n{font-size: 100%;font-weight:normal;padding-left:35px;padding-right: 2px;border:1px solid #000000;}";
		f0.append(mstrTdTsindlevel3);

		String mstrTdTsindfaillevel3="\ntd.tsindfaillevel3 \n{font-size: 100%;font-weight:bold;color:red;padding-left:35px;padding-right: 2px;border:1px solid #000000;}";
		f0.append(mstrTdTsindfaillevel3);

		String mstrTdTsinfo="\ntd.tsinfo \n{font-size: 100%;font-weight:bold;color:Green;padding-left:2px;border: 1px solid #000000;border-left solid none}";
		f0.append(mstrTdTsinfo);

		String mstrTdTswarning="\ntd.tswarning \n{font-size: 100%;font-weight:bold;color:OrangeRed;padding-left:2px;border: 1px solid #000000;border-left solid none}";
		f0.append(mstrTdTswarning);

		String mstrTdTsimgalert="\ntd.tsimgalert \n{font-size: 100%;font-weight:bold;color:OrangeRed;text-align:center;border: 1px solid #000000;border-right solid none}";
		f0.append(mstrTdTsimgalert);

		String mstrTdPfhead="\ntd.pfhead \n{color:#000000;background-color:White;font-size: 120%;font-weight:bold;text-align:center;border:1px solid #000000;}";
		f0.append(mstrTdPfhead);


		String mstrTdPfind="\ntd.pfind \n{background-color:White;font-size: 100%;font-weight:normal;padding-left:20px;border:1px solid #000000;}";
		f0.append(mstrTdPfind);

		String mstrStyleHr="\nhr.divline \n{width:900px;border:1px solid DarkGreen;margin-top: 4px;}";
		f0.append(mstrStyleHr);
////left:220px
//		String mstrStyle_hr="\nhr.divline \n{width:900px;align:center;position:absolute;;border:1px solid DarkGreen;margin-top: 4px;margin-left: 10px;}";
//		f0.append(mstrStyle_hr);
		String mstrH3IndexHead="\nh3.indexhead \n{margin: 10px 2px 2px 2px;font-size: 150%;}";
		f0.append(mstrH3IndexHead);

		String mstrBHighlight="\nb.highlight \n{font-size: 125%;}";
		f0.append(mstrBHighlight);

		String mstrDiv="\ndiv {width:100%;}";
		f0.append(mstrDiv);

		String mstrAnimation="\na.anibutton:link, a.anibutton:visited \n{color: #000000;border: 0px none;text-align: center;background-color:transparent;text-decoration: none;padding: 0px 0px 0px 0px;}";
		f0.append(mstrAnimation);

		String mstrAni2="\na.anibutton:hover \n{color: #0000FF;border: 2px outset;background-color:transparent;text-decoration: none;padding: 1px 1px 1px 1px;line-height: 100%;}";
		f0.append(mstrAni2);

		String mstrImgScreen="\nimg.screen \n{width: 20px; align:center; height: 20px;border: 0px none;background-color:transparent;}";
		f0.append(mstrImgScreen);

		String mstrImgMessage="\nimg.message \n{width: 32px;height: 32px;border: 3px 3px 3px 3px;background-color:transparent;}";
		f0.append(mstrImgMessage);

		String mstrStyleEnd="\n</style>";
		f0.append(mstrStyleEnd);
		
		String mstrTopImg="\n</head><table align=center width=900px><tr><td width=900px><img src=\"file:///"+gstrResultPath+"Images/Top_Logo.GIF\"></td></tr></table>";
		
		//		style=\"margin:0px 220px\"  align=\"middle\"
		f0.append(mstrTopImg);

		//		String hr_line = "\n<hr class='divline'><br>";
		//		f0.append(hr_line);

		String mstrDocEnd="\n</html>";
		f0.append(mstrDocEnd);

	}
		catch(Exception exception){
			log.error(exception.getMessage(),exception);
		}
		
	}
	
}
