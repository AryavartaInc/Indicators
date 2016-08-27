package com.fin.technical;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public class ProgramProperties {

    private StringBuffer m_strProgramSettings = new StringBuffer();

    private ProgramProperties() {
        m_strProgramSettings
                .append("#Multiple STOCK CODEs can be provided. They need to be separated by commas \n");
        m_strProgramSettings
                .append("#This entry is for testing purpose only. \n");
        m_strProgramSettings
                .append("#STOCK_CODE=RELIANCE.NS \n STOCK_CODE=^NSEI, ACC.BO, ACL.BO, ABIRLANUV.BO, ALLBANKSL.BO, ALOKTEXT.NS,ASHOKLEY.BO,AXISBANK.NS,ANDHRABAN.NS,ARVIND.NS, AUROPHARM.NS, BAJAJAUT.BO,BHARTIART.NS,BATAINDIA.NS,BHEL.NS,BPCL.NS, CAIRN.NS,CHAMBAL.BO,CIPLA.BO,COALINDIA.NS, DABUR.NS,DLF.NS,DISHTV.NS,DRREDDY.NS, EXIDEIND.BO, GAIL.NS,GTLINFRA.BO,GTL.NS,GRASIM.NS, HCLTECH.NS,HDFC.BO,HDFCBANK.NS,HEROHONDA.NS,HINDALCO.NS,HINDUNILV.NS,ICICIBANK.NS,IDFC.NS,INFY.BO,ITC.NS,IDEA.BO,INDHOTELS.BO, JETAIRWAYS.BO,JINDALSTE.NS,JPASSOCIA.NS, KOTAKBANK.NS, LITL.BO,LT.NS, MNM.BO,MARUTI.NS, NHPC.NS, NTPC.NS, ONGC.NS,ONMOBILE.NS,OPTOCIRCU.NS, PANTALOON.NS,PFC.NS, PIPAVAVYD.NS,PNB.NS,POWERGRID.NS,POLARIS.NS, RANBAXY.NS,RCOM.BO,RELCAPITA.NS,RELIANCE.NS,RELINFRA.NS,RENUKA.BO,RPOWER.NS, SAIL.NS,SBIN.NS,SESAGOA.NS,SIEMENS.BO,SOBHA.NS,SUZLON.NS,SUNPHARMA.NS, TATAMOTOR.NS,TATAPOWER.NS,TATASTEEL.NS,TCS.NS,TULIP.NS,TVSMOTOR.BO,UNITECH.NS,WIPRO.NS ");
        m_strProgramSettings
                .append("#Duration for which EMAs needs to be calculated, and can also be provided by comma separated, when missing it ssumes 3 and 15 as default.\n");
        m_strProgramSettings.append("EMA_DURATION=12,20 \n");
        m_strProgramSettings
                .append("#To fetch historical data from yahoo finance for EMA calculation, duration is the number of days.\n");
        m_strProgramSettings.append("HISTORICAL_DATA_DURATION=90\n");
        m_strProgramSettings
                .append("#Although, the primary gaol of the program is to find the EMA Crossovers, we will use the data available to\n");
        m_strProgramSettings
                .append("#calculate EMA also for the stocks specified above.\n");
        m_strProgramSettings.append("RSI_DATA_DURATION=14\n");
        m_strProgramSettings
                .append("#to turn on/off (report generation) use 1 or 0 respectively\n");
        m_strProgramSettings.append("GENERATE_STOCK_EMA_REPORT=1\n");
        m_strProgramSettings
                .append("#to turn on/off (report generation) use 1 or 0 respectively\n");
        m_strProgramSettings.append("GENERATE_EMA_CROSSOVER_REPORT=1\n");
        m_strProgramSettings
                .append("#to turn on/off (report generation) use 1 or 0 respectively\n");
        m_strProgramSettings.append("GENERATE_PIVOT_POINT_REPORT=1\n");
        m_strProgramSettings
                .append("#to turn on/off (report generation) use 1 or 0 respectively\n");
        m_strProgramSettings.append("GENERATE_RSI_REPORT=1\n");
        m_strProgramSettings
                .append("#to turn on/off (EMA Debug Info in the generated reports, which are nothing but columns like \"Gain\", \"Loss\", \"Avg. Gain\", \"Avg. Loss\" and \"RS\") use 1 or 0 respectively\n");
        m_strProgramSettings.append("GENERATE_RSI_DEBUG_INFO=0\n");
        m_strProgramSettings
                .append("#try not to use file separator here for this input param. Otherwise it will become OS dependent. this is relative to Current Working Directory\n");
        m_strProgramSettings.append("EMA_REPORT_LOCATION=reports\n");
        m_strProgramSettings.append(" \n");
        m_strProgramSettings.append(" \n");
    }

    /**
     * SingletonHolder is loaded on the first execution of
     * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
     * not before.
     */
    private static class SingletonHolder {
        // Later, when I implement Factory pattern, below code needs to be
        // re-written.
        public static final ProgramProperties m_objProgProp = new ProgramProperties();
    }

    public static ProgramProperties getInstance() {
        return SingletonHolder.m_objProgProp;
    }

    /*
#Multiple STOCK CODES can be provided. They need to be separated by comma.

#Stock code for Yahoo Finance
#This entry is for testing purpose only, with few stock codes.
#STOCK_CODE=ACC.BO,BOSCHL.BO,RELIANCE.NS,CIPLA.BO

#STOCK_CODE=^GSPC,^IXIC,^NSEI,ABIRLANUV.BO,ACC.BO,ACL.BO,ADANIPOWE.NS,ALLBANKSL.BO,ALOKTEXT.NS,ANDHRABAN.NS,ARVIND.NS,ASHOKLEY.BO,AXISBANK.NS,AUROPHARM.NS,BAJAJAUT.BO,BAJAJHIND.NS,BHARTIART.NS,BATAINDIA.NS,BPCL.NS,BOB.BO,BOSCHL.BO,BANKINDIA.NS,BFUTI.BO,BRFL.BO,CAIRN.NS,CHAMBAL.BO,CIPLA.BO,COALINDIA.NS,CORPBANK.NS,DABUR.NS,DENABANK.BO,DLF.NS,DISHTV.NS,DRREDDY.NS,EXIDEIND.BO,GAIL.NS,GLAXOCON.BO,GTLINFRA.BO,GTL.NS,GRASIM.NS,HCLTECH.NS,HDFC.BO,HDFCBANK.NS,HINDALCO.NS,HINDUNILV.NS,HPCL.BO,ICICIBANK.NS,IDFC.NS,IFCI.NS,INFY.BO,ITC.NS,IDEA.BO,IDBI.NS,INDHOTELS.BO,IGL.NS,IOC.NS,JBFIND.NS,JETAIRWAY.NS,JINDALSTE.NS,JPASSOCIA.NS,JPPOWER.NS,KOTAKBANK.NS,LITL.BO,LT.NS,MNM.BO,MARUTI.NS,NEYVELILI.NS,NHPC.NS,NTPC.NS,ONGC.NS,ONMOBILE.NS,OPTOCIRCU.NS,PANTALOON.NS,PFC.NS,PHILIPCAR.NS,PIDILITIN.NS,PNB.NS,POWERGRID.NS,POLARIS.NS,PTC.BO,RANBAXY.NS,RCOM.BO,RECLTD.NS,RELCAPITA.NS,RELIANCE.NS,RELINFRA.NS,RENUKA.BO,RPOWER.NS,SAIL.NS,SBIN.NS,SCI.NS,SESAGOA.NS,SIEMENS.BO,SOBHA.NS,SUZLON.NS,SUNPHARMA.NS,TATAMOTOR.NS,TATAPOWER.NS,TATASTEEL.NS,TCS.NS,TULIP.NS,TVSMOTOR.BO,UCO.BO,ULTRACEMC.NS,UNITECH.NS,VIDEOCON.BO,VIJAYABAN.NS,VIPIND.BO,VOLTAS.NS,WIPRO.NS,WELCORP.NS,YESBANK.NS,ZEEL.NS,

#Stock code for Google Finance
#STOCK_CODE=NSE:NIFTY,BOM:500112,BOM:500124,BOM:532628,BOM:532977,BOM:500114,BOM:500830,BOM:500410,BOM:532454,BOM:500103,BOM:500087,BOM:532868,BOM:500300,BOM:500180,BOM:500010,BOM:500440,BOM:532174,NSE:INFY,BOM:500875,BOM:500510,BOM:500520,BOM:532500,BOM:532555,BOM:500359,BOM:532712,BOM:500325,NSE:SUNPHARMA,BOM:500112,BOM:532540,BOM:500570,BOM:507685,NSE:ONGC,

#Stock code for NSE India
#DJIA,S&P500,NIFTY,MINIFTY,CNXIT,BANKNIFTY,NFTYMCAP50,CNXPSE,CNXINFRA,ABAN,ABB,ABGSHIP,ABIRLANUVO,ACC,ADANIENT,ADANIPOWER,ALBK,ALOKTEXT,AMBUJACEM,ANDHRABANK,APIL,APOLLOTYRE,AREVAT&D,ARVIND,ASHOKLEY,ASIANPAINT,AUROPHARMA,AXISBANK,BAJAJHIND,BAJAJHLDNG,BALRAMCHIN,BANKBARODA,BANKINDIA,BATAINDIA,BEL,BEML,BFUTILITIE,BGRENERGY,BHARATFORG,BHARTIARTL,BHEL,BHUSANSTL,BIOCON,BOMDYEING,BOSCHLTD,BPCL,BRFL,CAIRN,CANBK,CENTRALBK,CENTURYTEX,CESC,CHAMBLFERT,CIPLA,COALINDIA,COLPAL,COREEDUTEC,CROMPGREAV,CUMMINSIND,DABUR,DCB,DCHL,DELTACORP,DENABANK,DHANBANK,DISHTV,DIVISLAB,DLF,DRREDDY,EDUCOMP,EKC,ESCORTS,ESSAROIL,EXIDEIND,FEDERALBNK,FINANTECH,FORTIS,FSL,GAIL,GESHIP,GITANJALI,GLAXO,GMDCLTD,GMRINFRA,GODREJIND,GRASIM,GSPL,GTOFFSHORE,GUJFLUORO,GVKPIL,HAVELLS,HCC,HCLTECH,HDFC,HDFCBANK,HDIL,HEROMOTOCO,HEXAWARE,HINDALCO,HINDOILEXP,HINDPETRO,HINDUNILVR,HINDZINC,HOTELEELA,IBREALEST,ICICIBANK,IDBI,IDEA,IDFC,IFCI,IGL,INDHOTEL,INDIACEM,INDIAINFO,INDIANB,INDUSINDBK,INFY,IOB,IOC,IRB,ITC,IVRCLINFRA,JETAIRWAYS,JINDALSAW,JINDALSTEL,JINDALSWHL,JISLJALEQS,JPASSOCIAT,JPPOWER,JSWENERGY,JSWISPAT,JSWSTEEL,JUBLFOOD,KFA,KOTAKBANK,KSOILS,KTKBANK,LICHSGFIN,LITL,LT,LUPIN,M&M,MARUTI,MAX,MCLEODRUSS,MERCATOR,MPHASIS,MRF,MRPL,MTNL,MUNDRAPORT,NATIONALUM,NCC,NEYVELILIG,NHPC,NMDC,NTPC,OFSS,OIL,ONGC,ONMOBILE,OPTOCIRCUI,ORCHIDCHEM,ORIENTBANK,PANTALOONR,PATELENG,PATNI,PETRONET,PFC,PIRHEALTH,PNB,POLARIS,POWERGRID,PRAJIND,PTC,PUNJLLOYD,RANBAXY,RAYMOND,RCOM,RECLTD,RELCAPITAL,RELIANCE,RELINFRA,RENUKA,ROLTA,RPOWER,RUCHISOYA,SAIL,SBIN,SCI,SESAGOA,SIEMENS,SINTEX,SKUMARSYNF,SOBHA,SOUTHBANK,SREINFRA,SRTRANSFIN,STER,STRTECH,SUNPHARMA,SUNTV,SUZLON,SYNDIBANK,TATACHEM,TATACOMM,TATAGLOBAL,TATAMOTORS,TATAMTRDVR,TATAPOWER,TATASTEEL,TCS,TECHM,TITAN,TRIVENI,TTKPRESTIG,TTML,TULIP,TVSMOTOR,UCOBANK,ULTRACEMCO,UNIONBANK,UNIPHOS,UNITECH,VIDEOIND,VIJAYABANK,VIPIND,VOLTAS,WELCORP,WIPRO,YESBANK,ZEEL,
STOCK_CODE=ABAN,ABB,ABGSHIP,ABIRLANUVO,ACC,ADANIENT,ADANIPOWER,ALBK,ALOKTEXT,AMBUJACEM,ANDHRABANK,APIL,APOLLOTYRE,ARVIND,ASHOKLEY,ASIANPAINT,AUROPHARMA,AXISBANK,BAJAJHIND,BAJAJHLDNG,BALRAMCHIN,BANKBARODA,BANKINDIA,BATAINDIA,BEL,BEML,BFUTILITIE,BGRENERGY,BHARATFORG,BHARTIARTL,BHEL,BHUSANSTL,BIOCON,BOMDYEING,BOSCHLTD,BPCL,BRFL,CAIRN,CANBK,CENTRALBK,CENTURYTEX,CESC,CHAMBLFERT,CIPLA,COALINDIA,COLPAL,COREEDUTEC,CROMPGREAV,CUMMINSIND,DABUR,DCB,DCHL,DELTACORP,DENABANK,DHANBANK,DISHTV,DIVISLAB,DLF,DRREDDY,EDUCOMP,EKC,ESCORTS,ESSAROIL,EXIDEIND,FEDERALBNK,FINANTECH,FORTIS,FSL,GAIL,GESHIP,GITANJALI,GLAXO,GMDCLTD,GMRINFRA,GODREJIND,GRASIM,GSPL,GTOFFSHORE,GUJFLUORO,GVKPIL,HAVELLS,HCC,HCLTECH,HDFC,HDFCBANK,HDIL,HEROMOTOCO,HEXAWARE,HINDALCO,HINDOILEXP,HINDPETRO,HINDUNILVR,HINDZINC,HOTELEELA,IBREALEST,ICICIBANK,IDBI,IDEA,IDFC,IFCI,IGL,INDHOTEL,INDIACEM,INDIAINFO,INDIANB,INDUSINDBK,INFY,IOB,IOC,IRB,ITC,IVRCLINFRA,JETAIRWAYS,JINDALSAW,JINDALSTEL,JINDALSWHL,JISLJALEQS,JPASSOCIAT,JPPOWER,JSWENERGY,JSWISPAT,JSWSTEEL,JUBLFOOD,KFA,KOTAKBANK,KSOILS,KTKBANK,LICHSGFIN,LITL,LT,LUPIN,MARUTI,MAX,MCLEODRUSS,MERCATOR,MPHASIS,MRF,MRPL,MTNL,MUNDRAPORT,NATIONALUM,NCC,NEYVELILIG,NHPC,NMDC,NTPC,OFSS,OIL,ONGC,ONMOBILE,OPTOCIRCUI,ORCHIDCHEM,ORIENTBANK,PANTALOONR,PATELENG,PATNI,PETRONET,PFC,PIRHEALTH,PNB,POLARIS,POWERGRID,PRAJIND,PTC,PUNJLLOYD,RANBAXY,RAYMOND,RCOM,RECLTD,RELCAPITAL,RELIANCE,RELINFRA,RENUKA,ROLTA,RPOWER,RUCHISOYA,SAIL,SBIN,SCI,SESAGOA,SIEMENS,SINTEX,SKUMARSYNF,SOBHA,SOUTHBANK,SREINFRA,SRTRANSFIN,STER,STRTECH,SUNPHARMA,SUNTV,SUZLON,SYNDIBANK,TATACHEM,TATACOMM,TATAGLOBAL,TATAMOTORS,TATAMTRDVR,TATAPOWER,TATASTEEL,TCS,TECHM,TEXINFRA,TITAN,TRIVENI,TTKPRESTIG,TTML,TULIP,TVSMOTOR,UCOBANK,ULTRACEMCO,UNIONBANK,UNIPHOS,UNITECH,VIDEOIND,VIJAYABANK,VIPIND,VOLTAS,WELCORP,WIPRO,YESBANK,ZEEL,
#STOCK_CODE=ABAN,ABB,ABGSHIP,

#No historical data is available for the following Indices on NSEIndia web site.
#STOCK_CODE=BANKNIFTY,S&P500,DJIA,NIFTY,MINIFTY,CNXIT,NFTYMCAP50,CNXINFRA,CNXPSE,

#Stock code can be obtained from various sources like "yahoo finance", "google finance", "NSE India" etc.
#We will support ony two data source at this moment, "Yahoo Finance" and "Google Finance".
#For Yahoo Finance the acceptable value is "yahoo" and for "Google Finance", it is "google" and for NSE India it is "nseindia" like following:

#STOCK_CODE_DATA_SOURCE=yahoo
#STOCK_CODE_DATA_SOURCE=google
STOCK_CODE_DATA_SOURCE=nseindia

#This is more useful for the purpose of development. Say, Stock Quote for any given day has been archived,
#then turning this option off will retrieve stock quote from archived file rather than getting it from the
#specified data source and archiving it for further use. No default is assumed. Value 1 fetches the data
#from data source and archive it to a file in the directory named "datasource" where "bin" is also located.
ARCHIVE_STOCK_QUOTE=1


#Duration for which EMAs needs to be calculated, and must be be provided by comma separated, when missing it assumes 3 and 15 as default.
#Only 2 values needs to be specified e.g. 3,15 or 12,16. More than two values separated by comma will be ignored.
EMA_DURATION=3,15:5,20:12,26


#In the above, if there is only one set of EMA Duration e.g. 5,20 then PREFERRED_EMA_COMBO value will be overridden
#by value of "EMA_DURATION", for obvious reason.
PREFERRED_EMA_COMBO=5,20


#To fetch historical data from yahoo finance for EMA calculation, duration is the number of days.
HISTORICAL_DATA_DURATION=365

#Number specified below will be used to create that many number of
#threads to fetch Historical Data of Stocks specified in this file above
#having key name "STOCK_CODE". Retrieval of Historical Data from NSE is slow
#as compared to yahoo finance. Typically, 4-8 times slower. This prompted to
#have many background processes to accomplish the goal faster. Default Value is 2.
NUMBER_OF_STOCK_ARCHIVER_THREAD=30

#Although, the primary gaol of the program is to find the EMA Crossovers, we will use the data available to
#calculate RSI also for the stocks specified above. Duration for RSI Calculation is fixed and is 14.
RSI_DATA_DURATION=14

#to turn on/off (report generation) use 1 or 0 respectively
#Default is assumed to be 1, meaning for each Stock in the list above EMA for the specified duration will be calculated and included in the report.
GENERATE_STOCK_EMA_REPORT=1

#to turn on/off (report generation) use 1 or 0 respectively
#Default is assumed to be 1, meaning for each Stock in the list above EMA for the specified duration will be calculated and included in the report.
GENERATE_EMA_CROSSOVER_REPORT=1

#to turn on/off (report generation) use 1 or 0 respectively
GENERATE_PIVOT_POINT_REPORT=1

#If you turn off 20 Days SMA Calculation, Commodity Channel Index result will be incorrect.
#Hence this must be turned on all the time.
SIMPLE_MOVING_AVERAGE_20_DAYS=1

#Default is assumed to be 0, meaning no 50 Days SMA will be calculated and included in the report.
SIMPLE_MOVING_AVERAGE_50_DAYS=1

# Not supported right now.
#SIMPLE_MOVING_AVERAGE_100_DAYS=1

#Default is assumed to be 0, meaning no 200 Days SMA will be calculated and included in the report.
SIMPLE_MOVING_AVERAGE_200_DAYS=1

#to turn on/off (report generation) use 1 or 0 respectively, Default is assumed to be 1
#Default is assumed to be 1
GENERATE_RSI_REPORT=1

#to turn on/off (RSI Debug Info in the generated reports, which are nothing but columns like "Gain", "Loss", "Avg. Gain", "Avg. Loss" and "RS") use 1 or 0 respectively
#Even if this is turned on and "GENERATE_RSI_REPORT" is turned off, no RSI Debug Info will be generated.
#Default is assumed to be 0
GENERATE_RSI_DEBUG_INFO=0

#to turn on/off (ADL: Accumulation Distribution Line in the generated reports) use 1 or 0 respectively
#Default is assumed to be 1
GENERATE_ADL_REPORT=1


#to turn on/off (CCI: Commodity Channel Index in the generated reports) use 1 or 0 respectively
#Default is assumed to be 1
GENERATE_CCI_REPORT=1

#to turn on/off (ADX: Average Directional Index in the generated reports) use 1 or 0 respectively
#Default is assumed to be 1
GENERATE_ADX_REPORT=1

#In order to calculate ADX, we calculate EMAs.  EMAs need any duration like 5, 12, 14, 20, 26 etc.
ADX_DATA_DURATION=20

#to turn on/off ADX Debug Info (ADX: Average Directional Index Debug Info in the generated reports) use 1 or 0 respectively
GENERATE_ADX_DEBUG_INFO=0

BOLLINGER_DATA_DURATION=20

#to turn on/off (Bollinger Band Debug Info in the generated reports, which are nothing but columns like "Deviation Squared", "2-SMA Of Deviation Squared", "Standard Deviation") use 1 or 0 respectively
#Even if this is turned on and "GENERATE_BOLLINGER_REPORT" is turned off, no Bollinger Band Debug Info will be generated.
#Default is assumed to be 0
GENERATE_BOLLINGER_DEBUG_INFO=0


#to turn on/off (BOLL: Bollinger Bandwidth in the generated reports) use 1 or 0 respectively
#Default is assumed to be 1
GENERATE_BOLLINGER_REPORT=1


#try not to use file separator here for this input param. Otherwise it will become OS dependent. this is relative to parent of Current Working Directory
REPORT_LOCATION=reports

#Currently supported formats are "pdf", "xls" and "csv" file types. Default is "xls".
#i.e. if the following value is missing, "xls" format becomes default.
REPORT_FILE_TYPE=pdf

#For google mail as web service

USER_EMAIL_OAUTH_TOKEN=

USER_EMAIL_OAUTH_TOKEN_SECRET=

#For sending reports in email using Java mail API and google mail SMTP Server.
USER_EMAIL_ADDRESS=the.greatest.magadh@gmail.com

USER_EMAIL_PASSCODE=

#Comma separated recipient's e-mail address.
#RECIPIENT_EMAIL_ADDRESS=
RECIPIENT_EMAIL_ADDRESS=the.greatest.magadh@gmail.com

EMAIL_SUBJECT=Reports generated from EMACalculator program [DataSource - %s]

EMAIL_BODY=Please find: %s1. EMA Cross-over, %s2. Bollinger-Band, %s3. Pivot Point, %s4. Fibonacci Pivot Point and %s5. RSI %s6. ADX DI Crossover (Average Directional Index) %s reports generated and attached in the e-mail. %s Please also find India VIX report below: %s DI Crossover System http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:average_directional_ %sWilder put forth a simple system for trading with these directional movement indicators. The first requirement is for ADX to be trading above 25. This insures that prices are trending. A buy signal occurs when +DI crosses above - DI. Wilder based the initial stop on the low of the signal day. The signal remains in force as long as this low holds, even if +DI crosses back below - DI. Wait for this low to be penetrated before abandoning the signal. This bullish signal is reinforced if/when ADX turns up and the trend strengthens. Once the trend develops and becomes profitable, traders will have to incorporate a stop-loss and trailing stop should the trend continue. A sell signal triggers when - DI crosses above +DI. The high on the day of the sell signal becomes the initial stop-loss.

#Default is assumed to be 0 meaning No, otherwise Yes.
GENERATE_SCHEMA_FILE=1

#try not to use file separator here for this input param. Otherwise it will become OS dependent. this is relative to parent of Current Working Directory
SCHEMA_TEMPLATE_FILE=%stemplate%sTemplate.sql

SCHEMA_DESTINATION_FILE=%sschema%sschema.sql

#try not to use file separator here for this input param. Otherwise it will become OS dependent. this is relative to parent of Current Working Directory
POJO_TEMPLATE_FILE=%stemplate%sTemplate.pojo

#try not to use file separator here for this input param. Otherwise it will become OS dependent. this is relative to parent of Current Working Directory
HBM_TEMPLATE_FILE=%stemplate%sTemplate.hbm.xml

DATABASE_NAME=StockQuote

ENABLE_HIBERNATE=0

SCHEMA_TEMPLATE=DROP TABLE IF EXISTS `%s`.`%s`;%s CREATE TABLE IF NOT EXISTS `%s`.`%s` (%s `ID` bigint(20) NOT NULL AUTO_INCREMENT, %s TradeDate datetime NOT NULL, %s OpenPrice FLOAT(9,2) NOT NULL, %s HighPrice FLOAT(9,2) NOT NULL, %s LowPrice FLOAT(9,2) NOT NULL, %s LastTradedPrice FLOAT(9,2) NOT NULL, %s ClosePrice FLOAT(9,2) NOT NULL, %s TotalTradedQuantity int NOT NULL, %s Turnover FLOAT(9,2) NOT NULL, %s PRIMARY KEY (`ID`) %s) ENGINE\=InnoDB AUTO_INCREMENT\=3 DEFAULT CHARSET\=latin1;

#SCHEMA_TEMPLATE=DROP TABLE IF EXISTS `%s`.`%s`;%s CREATE TABLE IF NOT EXISTS `%s`.`%s` (%s `ID` bigint(20) NOT NULL AUTO_INCREMENT, %s TradeDate datetime NOT NULL, %s OpenPrice FLOAT(9,2) NOT NULL, %s HighPrice FLOAT(9,2) NOT NULL, %s LowPrice FLOAT(9,2) NOT NULL, %s LastTradedPrice FLOAT(9,2) NOT NULL, %s ClosePrice FLOAT(9,2) NOT NULL, %s TotalTradedQuantity int NOT NULL, %s Turnover FLOAT(9,2) NOT NULL, %s PRIMARY KEY (`ID`) %s) ENGINE\=InnoDB AUTO_INCREMENT\=3 DEFAULT CHARSET\=latin1;

#Default is assumed to be 0 meaning No, otherwise Yes.
GENERATE_POJO_FILE=1

POJO_DESTINATION_DIR=%ssrc%smain%sjava%scom%sfin%spersistence%s

POJO_DESTINATION_FILE=%s.java

HBM_DESTINATION_DIR=%scom%sfin%spersistence%s

HBM_DESTINATION_FILE=%s.hbm.xml

GENERATE_HIBERNATE_MAPPING=1

#HIBERNATE_MAPPING_TEMPLATE=<?xml version="1.0"?>%s<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN" "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">%s<!-- http://www.tutorialspoint.com/hibernate/hibernate_mapping_types.htm -->%s<hibernate-mapping package="com.fin.persistence">%s<class name="%s" table="%s">  %s <id name="id" type="long" column="ID">  %s      <generator class="sequence"/>  %s </id>  %s <property name="TradeDate" type="timestamp" column="TradeDate"/>  %s <property name="OpenPrice" type="double" column="OpenPrice"/>  %s <property name="HighPrice" type="double" column="HighPrice"/>  %s <property name="LowPrice" type="double" column="LowPrice"/> %s <property name="LastTradedPrice" type="double" column="LastTradedPrice"/> %s <property name="ClosePrice" type="double" column="ClosePrice"/> %s <property name="TotalTradedQuantity" type="integer" column="TotalTradedQuantity"/> %s <property name="Turnover" type="double" column="Turnover"/> %s </class>  %s</hibernate-mapping>

HIBERNATE_MAPPING_TEMPLATE=<?xml version="1.0"?>%s<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN" "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">%s<!-- http://www.tutorialspoint.com/hibernate/hibernate_mapping_types.htm -->%s<hibernate-mapping package="com.fin.persistence">%s<class name="%s" entity-name="%s" table="%s">%s <id name="id" type="long" column="ID">%s  <generator class="native"/>%s </id>%s <property name="TradeDate" type="timestamp" column="TradeDate"/>%s <property name="OpenPrice" type="double" column="OpenPrice"/>%s <property name="HighPrice" type="double" column="HighPrice"/>%s <property name="LowPrice" type="double" column="LowPrice"/>%s <property name="LastTradedPrice" type="double" column="LastTradedPrice"/>%s <property name="ClosePrice" type="double" column="ClosePrice"/>%s <property name="TotalTradedQuantity" type="integer" column="TotalTradedQuantity"/>%s <property name="Turnover" type="double" column="Turnover"/>%s</class>%s</hibernate-mapping>

HIBERNATE_MAPPING_DYNAMIC_MODEL_TEMPLATE=<?xml version="1.0"?>%s<!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN" "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">%s<!-- http://www.tutorialspoint.com/hibernate/hibernate_mapping_types.htm -->%s<hibernate-mapping package="com.fin.persistence">%s<class name="%s" table="%s">  %s<id name="id" type="long" column="ID">%s    <generator class="sequence"/>  %s</id>  %s<property name="TradeDate" type="timestamp" column="TradeDate"/>  %s<property name="OpenPrice" type="double" column="OpenPrice"/>  %s<property name="HighPrice" type="double" column="HighPrice"/>  %s<property name="LowPrice" type="double" column="LowPrice"/> %s<property name="LastTradedPrice" type="double" column="LastTradedPrice"/> %s<property name="ClosePrice" type="double" column="ClosePrice"/> %s<property name="TotalTradedQuantity" type="integer" column="TotalTradedQuantity"/> %s<property name="Turnover" type="double" column="Turnover"/> %s</class>  %s</hibernate-mapping>

XML_HEADER_TEMPLATE=<?xml version="1.0"?>%s <!DOCTYPE hibernate-mapping PUBLIC "-//Hibernate/Hibernate Mapping DTD 3.0//EN" "http://www.hibernate.org/dtd/hibernate-mapping-3.0.dtd">  %s <!-- http://www.tutorialspoint.com/hibernate/hibernate_mapping_types.htm -->  %s <hibernate-mapping package="com.fin.persistence"> %s

CLASS_ENTITY_TEMPLATE=<class name="%s" table="%s">%s <id name="id" type="long" column="ID">%s   <generator class="sequence"/>%s </id>%s <property name="TradeDate" type="timestamp" column="TradeDate"/>%s <property name="OpenPrice" type="double" column="OpenPrice"/>%s <property name="HighPrice" type="double" column="HighPrice"/>%s <property name="LowPrice" type="double" column="LowPrice"/>%s <property name="LastTradedPrice" type="double" column="LastTradedPrice"/>%s <property name="ClosePrice" type="double" column="ClosePrice"/>%s <property name="TotalTradedQuantity" type="integer" column="TotalTradedQuantity"/>%s <property name="Turnover" type="double" column="Turnover"/>%s</class>
     */
}
