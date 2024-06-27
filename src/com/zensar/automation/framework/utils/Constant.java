package com.zensar.automation.framework.utils;

import java.io.File;
/*
 *Class Name : Constatnt.java
 *Description: This final class contains the all constatnt varibles required y other classes. 
 * */
public final class Constant {


	public static final String CONFIG="Config";
	public static final String TESTDATA="TestData";
	public static final String PRESETS="Presets";
	public static final String PATH=System.getProperty("user.dir");
	public static final String TESTDATA_EXCEL_PATH=File.separator+CONFIG+File.separator+"TestData.xls";
	public static final String SUIT_EXCEL_PATH=File.separator+CONFIG+File.separator+"SuitFile.xls";
	public static final String REGRESSION_EXCEL_PATH=File.separator+CONFIG+File.separator+"Regression.xls";
	public static final String PASSWORD="test";
	public static final String INCOMPLETEGAMENAME="thunderstruck";
	public static final String  URL="https://mobilewebserver9-zensarqa2.installprogram.eu/Lobby/en/IslandParadise/games/5reelslots";
	public  static final String LOCALHUBURL="http://localhost:4444/wd/hub";
	public static final String OUTPUTIMAGEFOLDER=System.getProperty("user.dir")+"\\ImageScreenshot\\Mobile\\";
	public static final String WINDOWS="Windows"; 
	public static final String FORCE_ULTIMATE_CONSOLE="ForceUltimateConsole";
	public static final String STANDARD_CONSOLE="StandardConsole";
	public static final String STORMCRAFT_CONSOLE="StormcraftConsole";
	public static final String TRUE="true";
	public static final String FALSE="false";
	public static final String ONEDESIGN_NEWFEATURE_CLICKTOCONTINUE="OneDesign_NewFeature_ClickToContinue";
	public static final String CLOCK="clock";
	public static final String CONFIGFILE="Config File";
	public static final int  REFRESH_RETRY_COUNT=3;
	public static final String CICD_DEFAULTPROP_FILE_PATH="."+File.separator+"AutomationBinary"+File.separator+"Default.properties";
	public static final String DEFAULTPROP_FILE_PATH="."+File.separator+"Default.properties";
	public static final String GAMETESTPROP_FILE_PATH=File.separator+CONFIG+File.separator+"TestEnv.properties";
	public static final String CURRENCY_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Desktop_Regression_CurrencySymbol.testdata";
	public static final String	FREEGAMES_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Desktop_Regression_Language_Verification_FreeGames.testdata";
	public static final	String	FREESPINS_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Desktop_Regression_Language_Verification_FreeSpin.testdata";
	public static final	String	BIGWIN_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Desktop_Regression_Language_Verification_BigWin.testdata";
	public static final	String	MOBILE_CURRENCY_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Mobile_Regression_CurrencySymbol.testdata";
	public static final	String	MOBILE_FREEGAMES_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Mobile_Regression_Language_Verification_FreeGames.testdata";
	public static final	String	MOBILE_FREESPINS_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Mobile_Regression_Language_Verification_FreeSpin.testdata";
	public static final	String	MOBILE_BIGWIN_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Mobile_Regression_Language_Verification_BigWin.testdata";
	public static final String 	PRESETSZIPPATH=File.separator+PRESETS+File.separator;
	public static final String FORCE="force";
	public static final String LANG_XL_SHEET_NAME="LanguageCodes";
	public static final String LANGUAGE="Language";
	public static final String LANG_CODE="Language Code";
	public static final String HELPFILE_SHEET_NAME="HelpFile"; 
	public static final String YES="yes";
	public static final String CURRENCY_XL_SHEET_NAME="SupportedCurrenciesList";
	public static final String ID="ID";
	public static final String ISOCODE="ISOCode";
	public static final String SANITY_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Desktop_Sanity_BaseScene.testdata";
	public static final	String MOBILE_SANITY_TESTDATA_FILE_PATH=File.separator+TESTDATA+File.separator+"Mobile_Sanity_BaseScene.testdata";
	public static final String ISONAME = "ISOName";
	public static final String LANGUAGECURRENCY = "LanguageCurrency";
	public static final String DESKTOP = "Desktop";
	public static final String MOBILE = "Mobile";
	public static final String OPENCVPATH="C:"+File.separator+"OPCV"+File.separator+"opencv"+File.separator+"build"+File.separator+"java"+File.separator+"x64"+File.separator;
	public static final String NATIVE_APP="NATIVE_APP";
	public static final String CHROMIUM="CHROMIUM";
	public static final String CHROME="chrome";
	public static final String FIREFOX="firefox";
	public static final String INTERNETEXPLORER="internet explorer";
	public static final String EDGEDRIVER="edge";
	public static final String OPERA="opera";
	
	public static final String HTMLEXTENSION=".html";
	public static final String JPEGEXTENSION=".jpeg";
	public static final String INTERRUPTED="Interrupted";
	public static final String ANDROID="Android";
	public static final String IOS="iOS";
	public static final String SAFARI="safari";
	
	public static final String DISPALYFORMAT = "DisplayFormat";
	public static final String REGEXPRESSION = "RegExpression"; //
	public static final String REGEXPRESSION_NOSYMBOL = "RegExpressionNoSymbol";
	/*
	 * Defining private constructor as make the class singlton
	 * */
	//HT67091-Harsha Toshniwal Adding parameters forlanguage translation code 
		public static final String Language_XL_SHEET_NAME="LanguageCode";
		public static final String LanguageCode = "LanguageCode";
		public static final String Language = "Language";
		public static final String Home = "Home";
		public static final String Banking = "Banking";
		public static final String Settings = "Settings";
		public static final String Sounds = "Sounds";
		public static final String Double = "Double";
		public static final String QuickDeal = "QuickDeal";
		public static final String PoweredByMicrogaming = "PoweredByMicrogaming";
		public static final String CoinsInPaytable = "CoinsInPaytable";
		public static final String Credits = "Credits";
		public static final String BETPlus1 = "BETPlus1";
		public static final String BetMax = "BetMax";
		public static final String Deal = "Deal";
		public static final String Held = "Held";
		public static final String Draw = "Draw";
		public static final String Collect = "Collect";
		public static final String Bet = "Bet";
		public static final String TotalBet = "TotalBet";
		public static final String CoinsInBet = "CoinsInBet";
		public static final String CoinSize = "CoinSize";
		public static final String DoubleTo = "DoubleTo";
		public static final String PickCard = "PickCard";
		public static final String Congratulations = "Congratulations";
		public static final String YouWin = "YouWin";
		public static final String DoubleLimitReached = "DoubleLimitReached";
		
	private Constant() {
	}




}