package com.zensar.automation.framework.utils;

import java.io.File;
/*
 *Class Name : Constatnt.java
 *Description: This final class contains the all constant variables required y other classes. 
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
	public static final String LOCALHUBURL="http://localhost:4444/wd/hub";
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
	public static final String PAYTABLE_XL_SHEET_NAME="Paytable";//Added by Harsha on 13th dec 2022
	
	
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
	
	
	public static final String Home = "Home";
	public static final String Banking = "Banking";
	public static final String Settings = "Settings";
	public static final String Sounds = "Sounds";
	public static final String Double = "Double";
	public static final String QuickDeal = "QuickDeal";
	public static final String PoweredByMicrogaming = "PoweredByMicrogaming";
	public static final String CoinsInPaytable = "CoinsInPaytable";	
	public static final String Credits = "Credits";
	public static final String BetPlus1 = "BetPlus1";
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
	public static final String Statistics = "Statistics";
	public static final String General = "General";
	public static final String HandResults = "HandResults";
	public static final String HandsPlayed = "HandsPlayed";
	public static final String SessionTime = "SessionTime";
	public static final String HandsPerHour = "HandsPerHour";
	public static final String HighestWins = "HighestWins";
	public static final String First = "First";
	public static final String Second = "Second";
	public static final String Third = "Third";
	public static final String StatsDisclaimer = "StatsDisclaimer";
	public static final String Reset = "Reset";
	public static final String Ok = "Ok";
	public static final String WinSummary = "WinSummary";
	public static final String HandNames = "HandNames";
	public static final String NoOfHands = "NoOfHands";
	public static final String Percentage = "Percentage";
	
	//Added By Harsha new sheet paytable for lang translations
	public static final String PayOut1 = "PayOut1";
	public static final String PayOut2 = "PayOut2";
	public static final String PayOut3 = "PayOut3";
	public static final String PayOut4 = "PayOut4";
	public static final String PayOut5 = "PayOut5";
	public static final String PayOut6 = "PayOut6";
	public static final String PayOut7 = "PayOut7";
	public static final String PayOut8 = "PayOut8";
	public static final String PayOut9 = "PayOut9";
	public static final String PayOut10 = "PayOut10";
	public static final String PayOut11 = "PayOut11";
	
	
	//Added by Harsha for play.next language translation	
	public static final String LanguageCode = "LanguageCode";
	public static final String Language = "Language";
	//public static final String Credits1 = "Credits1";
	public static final String Bet1 = "Bet1";
	public static final String TotalBet1 = "TotalBet1";
	public static final String Banking1 = "Banking1";
	public static final String Lobby = "Lobby";
	public static final String Settings1 = "Settings1";
	public static final String newPay = "newPay";
	public static final String CoinSize1 = "CoinSize1";
	public static final String CoinsPerLine = "CoinsPerLine";
	public static final String Sounds1 = "Sounds1";
	public static final String QuickDeal1 = "QuickDeal1";
	public static final String Spin = "Spin";
	public static final String newbigwin = "newbigwin";
	public static final String AMAZING = "AMAZING";
	public static final String Scatter = "Scatter";
	public static final String TOTALWIN = "TOTALWIN";
	public static final String FREESPINS = "FREESPINS";
	public static final String BaseMaxbet = "BaseMaxbet";//updated on 19th Jan 2023 HT67091
	public static final String QuickBet = "QuickBet";
	public static final String QuickSpin = "QuickSpin";
	public static final String Autoplay = "Autoplay";
	public static final String BaseNewCoinsPerLine = "BaseNewCoinsPerLine";//updated on 19th Jan 2023 HT67091
	public static final String YouHaveWon = "YouHaveWon";
	public static final String FreeGames = "FreeGames";
	public static final String FreeSpins = "FreeSpins";
	public static final String Help = "Help";
	
	//Added on 16th Jan 2023
	public static final String Freegames="Freegames";    
	public static final String PlayNow="PlayNow";   
	public static final String PlayLater="PlayLater";    
	public static final String KeepIt="KeepIt";
	public static final String DiscardOffer="DiscardOffer";    
	public static final String Discard="Discard";
	public static final String FGCongratulations = "FGCongratulations";//updated on 19th Jan 2023 HT67091
	

	
	public static final String AGamesGlobalStudio = "AGamesGlobalStudio";
	
	public static final String TotalWin = "TotalWin";
	
	
	public static final String PAYOUT1 = "PayOut1";
	public static final String PAYOUT2 = "PayOut2";
	public static final String PAYOUT3 = "PayOut3";
	public static final String PAYOUT4 = "PayOut4";
	public static final String PAYOUT5 = "PayOut5";
	public static final String PAYOUT6 = "PayOut6";
	public static final String PAYOUT7 = "PayOut7";
	public static final String PAYOUT8 = "PayOut8";
	public static final String PAYOUT9 = "PayOut9";
	public static final String PAYOUT10 = "PayOut10";
	public static final String PAYOUT11 = "PayOut11";
	public static final String PAYOUT12 = "PayOut12";
	public static final String PAYOUT13 = "PayOut13";
	

	public static final String REG_MARKET_NAME = "RegMarketName";
	public static final String PRODUCT_ID ="ProductId";
	public static final String CURRENCY_ISOCODE = "CurrencyISOCode";
	public static final String MARKET_TYPEID = "MarketTypeID";
	public static final String BALANCE = "Balance";
	public static final String PRODUCT_ID_MOBILE ="ProductId_Mobile";
	
	public static final String LANG_XL_SHEET_NAME_For_ITALY = "ItalySupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_MALTA= "MaltaSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_DENMARK = "DenmarkSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_SWEDEN = "SwedenSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_UK = "UKSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_SPAIN = "SpainSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_GERMANY = "GermanySupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_DOTCOM = "DotComSupportedLangs";
	
	public static final String LANG_XL_SHEET_NAME_For_ONTARIO = "OntarioSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_ALDERNEY = "AlderneySupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_BELGUIM = "BelguimSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_BULGARIA= "BulgariaSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_GILBRALTRA = "GibraltarSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_NETHERLAND= "NetherlandSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_ISLEOFMAN = "IsleOfManSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_CROATIA = "CroatiaSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_ASIAs= "AsiaSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_ROMANIA= "RomaniaSupportedLangs";
	public static final String LANG_XL_SHEET_NAME_For_SWITZERLAND = "SwitzerlandSupportedLangs";
	public static final String RUNFLAG = "RunFlag";
	public static final String MARKET_XL_SHEET_NAME = "SupportedMarketList";
	public static final String LANG_STRINGS_XL_SHEET_NAME="LanguageStrings";
	
	
	public static final String HELPFILE_XL_SHEET_NAME="HelpFile";
	
	
	
	/*
	 * Defining private constructor as make the class singleton
	 * */
	private Constant() {
	}




}