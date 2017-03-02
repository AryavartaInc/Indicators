package com.fin.technical;

import java.math.BigDecimal;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public interface Constants {
    public static final String STR_MSG_CURRENT_WORKING_DIR = "Current working directory is: %s";

    public static final String STR_DOCUMENT_AUTHOR_NAME = "Developed by: MANOJ KUMAR TIWARI";

    public static final String STR_MSG_GRANT_WRITE_PERMISSION_DIR = "Please grant write permission on directory: %s.";

    public static final String STR_MSG_DIR_NOT_FOUND = "Specified path %s not found. Creating...";

    public static final String STR_MSG_SPECIFIED_DIR_CREATED = "Direcotry %s created.";

    public static final String STR_MSG_WRONG_URL = "Given URL seems to be incorrect. Please check the URL and try again. ";

    public static final String STR_UTF8_CHARACTER_ENCODING = "UTF-8";

    // public static final String STR_DOCUMENT_AUTHOR_EMAIL =

    public static final String STR_DOCUMENT_KEY_WORDS = "SMA, EMA, EMA, ADL, CCI, ADX, Bollinger Band, EMA Trend, Pivot Points, Fibonacci Pivot Points, EMA Crossover";

    public static final String STR_DOCUMENT_TITLE = "Technical Indicator and Overlays for %s";

    public static final String STR_PERFORMANCE_MEASUREMENT = "%s%s %s took %d milli seconds.";

    public static final String STR_GOOGLE_FINANCE_MAIN_URL_KEY = "GOOGLE_FINANCE_MAIN_URL";

    public static final String STR_GOOGLE_FINANCE_PARAM_URL_KEY = "GOOGLE_FINANCE_PARAM_URL";

    public static final String STR_YAHOO_FINANCE_CURRENCT_STOCK_QUOTE_URL_KEY = "YAHOO_FINANCE_CURRENCT_STOCK_QUOTE_URL";

    public static final String STR_YAHOO_FINANCE_HISTORICAL_STOCK_QUOTE_URL_KEY = "YAHOO_FINANCE_HISTORICAL_STOCK_QUOTE_URL";

    // http://www.nseindia.com/content/equities/scripvol/datafiles/11-07-2014-TO-10-07-2015RELIANCEALLN.csv
    //public static final String STR_NSE_INDIA_URL = "http://www.nseindia.com/content/equities/scripvol/datafiles/%s-TO-%s%sALLN.csv";

    // https://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/GetQuote.jsp?symbol=SBIN
    // http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=%s&series=EQ&fromDate=undefined&toDate=undefined&datePeriod=12months&hiddDwnld=true
    
    // http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=RELIANCE&series=EQ&fromDate=undefined&toDate=undefined&datePeriod=3months&hiddDwnld=true
    public static final String STR_NSE_INDIA_URL = "http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=%s&series=EQ&fromDate=undefined&toDate=undefined&datePeriod=12months&hiddDwnld=true";
    //public static final String STR_NSE_INDIA_URL = "http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=%s&fromDate=%s&toDate=%s&datePeriod=unselected&hiddDwnld=true";

    //   http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=ABAN&fromDate=07-Dec-2011&toDate=06-Dec-2012&datePeriod=unselected&hiddDwnld=true
    // http://www.nseindia.com/content/vix/histdata/hist_india_vix_02-Jan-2012_24-Jan-2012.csv
    // http://www.nseindia.com/content/vix/histdata/hist_india_vix_25-Jan-2011_24-Jan-2012.csv
    public static final String STR_NSE_INDIA_VIX_URL = "http://www.nseindia.com/content/vix/histdata/hist_india_vix_%s_%s.csv";

    public static final String STR_STOCK_CODE_KEY_NAME = "STOCK_CODE";

    public static final String STR_NUMBER_OF_STOCK_ARCHIVER_THREAD_KEY_NAME = "NUMBER_OF_STOCK_ARCHIVER_THREAD";

    // http://www.kodejava.org/examples/174.html

    public static final String STR_STOCK_ARCHIVER_THREAD_GROUP_NAME = "StockQuoteArchiver";

    public static final String STR_STOCK_ARCHIVER_THREAD_NAME = "%s$%03d";

    public static final String STR_DATA_SOURCE_DIR_NAME = "datasource";

    public static final String STR_STOCK_CODE_DATA_SOURCE_KEY_NAME = "STOCK_CODE_DATA_SOURCE";

    public static final String STR_STOCK_CODE_DATA_SOURCE_YAHOO = "yahoo";

    public static final String STR_STOCK_CODE_DATA_SOURCE_GOOGLE = "google";

    public static final String STR_STOCK_CODE_DATA_SOURCE_NSEINDIA = "nseindia";

    public static final String STR_EMA_DURATION_KEY_NAME = "EMA_DURATION";

    public static final String STR_EMA_CROSSOVER_POSITIVE = "+ve";

    public static final String STR_EMA_CROSSOVER_NEGATIVE = "-ve";

    public static final String STR_PREFERRED_EMA_COMBO_KEY_NAME = "PREFERRED_EMA_COMBO";

    public static final String STR_HIST_DATA_DURATION_KEY_NAME = "HISTORICAL_DATA_DURATION";

    public static final String STR_ARCHIVE_STOCK_QUOTE_KEY_NAME = "ARCHIVE_STOCK_QUOTE";

    public static final String STR_RSI_DURATION_KEY_NAME = "RSI_DATA_DURATION";

    public static final String STR_GEN_STOCK_EMA_REPORT_KEY_NAME = "GENERATE_STOCK_EMA_REPORT";

    public static final String STR_GEN_EMA_CROSOVER_REPORT_KEY_NAME = "GENERATE_EMA_CROSSOVER_REPORT";

    public static final String STR_GEN_PIVOT_POINT_REPORT_KEY_NAME = "GENERATE_PIVOT_POINT_REPORT";

    public static final String SIMPLE_MOVING_AVERAGE_20_DAYS_KEY_NAME = "SIMPLE_MOVING_AVERAGE_20_DAYS";

    public static final String SIMPLE_MOVING_AVERAGE_50_DAYS_KEY_NAME = "SIMPLE_MOVING_AVERAGE_50_DAYS";

    public static final String SIMPLE_MOVING_AVERAGE_200_DAYS_KEY_NAME = "SIMPLE_MOVING_AVERAGE_200_DAYS";

    public static final String STR_GEN_RSI_REPORT_KEY_NAME = "GENERATE_RSI_REPORT";

    public static final String STR_GEN_RSI_DEBUG_INFO_KEY_NAME = "GENERATE_RSI_DEBUG_INFO";

    public static final String STR_GEN_ADL_REPORT_KEY_NAME = "GENERATE_ADL_REPORT";

    public static final String STR_GEN_CCI_REPORT_KEY_NAME = "GENERATE_CCI_REPORT";

    public static final String STR_GEN_CCI_DEBUG_INFO_KEY_NAME = "GENERATE_CCI_DEBUG_INFO";

    public static final String STR_GEN_ADX_REPORT_KEY_NAME = "GENERATE_ADX_REPORT";

    public static final String STR_ADX_DURATION_KEY_NAME = "ADX_DATA_DURATION";

    public static final String STR_GEN_ADX_DEBUG_INFO_KEY_NAME = "GENERATE_ADX_DEBUG_INFO";

    public static final String STR_GEN_BOLL_REPORT_KEY_NAME = "GENERATE_BOLLINGER_REPORT";

    public static final String STR_GEN_BOLL_DEBUG_INFO_KEY_NAME = "GENERATE_BOLLINGER_DEBUG_INFO";

    public static final String STR_REPORT_LOCATION_KEY_NAME = "REPORT_LOCATION";

    public static final String STR_SCHEMA_TEMPLATE_FILE_KEY_NAME = "SCHEMA_TEMPLATE_FILE";

    public static final String STR_POJO_TEMPLATE_FILE_KEY_NAME = "POJO_TEMPLATE_FILE";

    public static final String STR_HBM_TEMPLATE_FILE_KEY_NAME = "HBM_TEMPLATE_FILE";

    public static final String STR_INDEX_STOCK_TREND_BULLISH = "Bullish";

    public static final String STR_INDEX_STOCK_TREND_BEARISH = "Bearish";

    public static final String STR_DIR_NAME_FOR_REPORT_GEN = "%d&%dDays-EMA";

    public static final String STR_INCLUDE_20DAY_SMA_HEADER = ",20-SMA";

    public static final String STR_INCLUDE_50DAY_SMA_HEADER = ",50-SMA";

    public static final String STR_INCLUDE_200DAY_SMA_HEADER = ",200-SMA";

    public static final String STR_INCLUDE_RSI_HEADER = ",RSI";

    public static final String STR_INCLUDE_ADL_HEADER = ",ADL";

    public static final String STR_INCLUDE_CCI_HEADER = ",CCI";

    public static final String STR_INCLUDE_ROC_HEADER = ",ROC";

    public static final String STR_INCLUDE_RSI_DEBUG_HEADER = ",Gain,Loss,Avg. Gain,Avg. Loss,RS,RSI";

    public static final String STR_INCLUDE_ADX_HEADER = ",DI%d+,DI%d-,ADX";

    public static final String STR_INCLUDE_BOLLINGER_HEADER = ",BOLLINGER-BAND";

    public static final String STR_INCLUDE_BOLLINGER_DEBUG_HEADER = ",Dev.Sqrd.,20-SMA-Dev.Sqrd.,Std.Dev.,BOLLINGER-BAND";

    public static final String STR_INCLUDE_ACTION_HEADER = ",%s-%s EMA Trend,Recommendation";

    public static final String STR_INCLUDE_ADX_DEBUG_HEADER = ",TR,+DM 1,-DM 1,TR%d,DM%d-Plus,DM%d-Minus,DI%d-Plus,DI%d-Minus,DI-Diff,DI-Sum,DX,ADX";

    // public static final String STR_INCLUDE_ADX_DEBUG_HEADER =
    // ",TR,+DM 1,-DM 1,TR14,DM14-Plus,DM14-Minus,DI14-Plus,DI14-Minus,DI-Diff,DI-Sum,DX,ADX";

    public static final String STR_INCLUDE_EMA_HEADER = ",%sD EMA";

    public static final String STR_STOCK_EMA_REPORT_HEADER = "Date,Open,High,Low,Close,Volume%s%s%s%s%s%s%s%s%s%s%s";

    public static final String STR_EMA_CROSSOVER_REPORT_HEADER = "Stock-Code,Date,Open,High,Low,Close,Volume%s%s%s%s%s%s%s%s%s%s%s";

    public static final String STR_PIVOT_POINT_REPORT_HEADER = "Stock-Code,High,Low,Close,S1,S2,S3,Pivot,R1,R2,R3%s%s%s%s%s%s%s%s%s%s%s";

    public static final String STR_FIBONACCI_PIVOT_POINT_REPORT_HEADER = "Stock-Code,High,Low,Close,S1,S2,S3,Pivot,R1,R2,R3%s%s%s%s%s%s%s%s%s%s%s";

    public static final String STR_STOCK_QUOTE_FILE_TYPE_CSV = ".csv";

    public static final String STR_CSV_EMA_REPORT_FILE = "-EMA-REPORT.csv";

    public static final String STR_XLS_EMA_REPORT_FILE = "-EMA-REPORT.xls";

    public static final String STR_PDF_EMA_REPORT_FILE = "-EMA-REPORT.pdf";

    public static final String STR_CSV_EMA_CROSSOVER_REPORT_FILE = "EMA-CROSSOVERS-%s.csv";

    public static final String STR_XLS_EMA_CROSSOVER_REPORT_FILE = "EMA-CROSSOVERS-%s.xls";

    public static final String STR_PDF_RSI_REPORT_FILE = "RSI-REPORT-%s.pdf";

    public static final String STR_XLS_RSI_REPORT_FILE = "RSI-REPORT-%s.xls";

    public static final String STR_CSV_RSI_REPORT_FILE = "RSI-REPORT-%s.csv";

    public static final String STR_PDF_BOLL_REPORT_FILE = "BOLLINGER-BAND-REPORT-%s.pdf";

    public static final String STR_XLS_BOLL_REPORT_FILE = "BOLLINGER-BAND-REPORT-%s.xls";

    public static final String STR_CSV_BOLL_REPORT_FILE = "BOLLINGER-BAND-REPORT-%s.csv";

    public static final String STR_PDF_ADX_REPORT_FILE = "ADX-REPORT-%s.pdf";

    public static final String STR_XLS_ADX_REPORT_FILE = "ADX-REPORT-%s.xls";

    public static final String STR_CSV_ADX_REPORT_FILE = "ADX-REPORT-%s.csv";

    public static final String STR_PDF_EMA_CROSSOVER_REPORT_FILE = "EMA-CROSSOVERS-%s.pdf";

    public static final String STR_CSV_PIVOT_POINT_REPORT_FILE = "PIVOT-POINT-%s.csv";

    public static final String STR_XLS_PIVOT_POINT_REPORT_FILE = "PIVOT-POINT-%s.xls";

    public static final String STR_PDF_PIVOT_POINT_REPORT_FILE = "PIVOT-POINT-%s.pdf";

    public static final String STR_CSV_FIBONACCI_PIVOT_POINT_REPORT_FILE = "FIBONACCI-PIVOT-POINT-%s.csv";

    public static final String STR_XLS_FIBONACCI_PIVOT_POINT_REPORT_FILE = "FIBONACCI-PIVOT-POINT-%s.xls";

    public static final String STR_PDF_FIBONACCI_PIVOT_POINT_REPORT_FILE = "FIBONACCI-PIVOT-POINT-%s.pdf";

    public static final String STR_REPORT_FILE_TYPE_KEY_NAME = "REPORT_FILE_TYPE";// REPORT_FILE_TYPE=xls

    public static final String STR_REPORT_FILE_TYPE_CSV = "csv";

    public static final String STR_REPORT_FILE_TYPE_XLS = "xls";

    public static final String STR_REPORT_FILE_TYPE_PDF = "pdf";

    public static final String STR_GENERATE_POJO_FILE_KEY_NAME = "GENERATE_POJO_FILE";

    public static final String STR_GENERATE_SCHEMA_FILE_KEY_NAME = "GENERATE_SCHEMA_FILE";

    public static final String STR_DATABASE_KEY_NAME = "DATABASE_NAME";

    public static final String STR_SCHEMA_TEMPLATE_KEY_NAME = "SCHEMA_TEMPLATE";

    public static final String STR_GENERATE_HIBERNATE_MAPPING_KEY_NAME = "GENERATE_HIBERNATE_MAPPING";

    public static final String STR_HIBERNATE_MAPPING_TEMPLATE_KEY_NAME = "HIBERNATE_MAPPING_TEMPLATE";

    public static final String STR_XML_HEADER_TEMPLATE_KEY_NAME = "XML_HEADER_TEMPLATE";

    public static final String STR_CLASS_ENTITY_TEMPLATE_KEY_NAME = "CLASS_ENTITY_TEMPLATE";

    public static final String STR_SCHEMA_FILE_EXT = ".sql";

    public static final String STR_HIBERNATE_MAPPING_FILE_EXT = ".hbm.xml";

    public static final String STR_COMMA_SEPARATOR = ", ";

    public static final String STR_SPILT_ON_COMMA = ",";

    public static final String STR_SPILT_ON_COLON = ":";

    public static final String STR_SPILTTER_FOR_NSEINDIA = "\",\"";

    public static final String STR_CURRENT_WORKING_DIR = "user.dir";

    public static final String STR_FILE_SEPARATOR = "file.separator";

    public static final String STR_DATE_FORMAT = "dd-MM-yyyy";

    public static final String STR_DATE_FORMAT_PATTERN = "dd-MMM-yyyy";

    public static final String STR_VIX_DATE_FORMAT = "dd-MMM-yyyy";

    public static final String STR_INDIA_VIX = "INDIA-VIX";

    public static final String STR_ISO_DATE_FORMAT = "yyyy-MM-dd";

    public static final String STR_ISO_DATE_FORMAT_FROM_STRING = "%s-%s-%s";

    public static final String STR_ISO_DATE_FORMAT_FROM_NUMBER = "%d-%d-%d";

    public static final int NO_ERROR = 10000;

    public static final int INT_INVALID_INPUT_TO_CALC_SMA = 10001;

    public static final int AN_ERROR_OCCURED = 10002;

    public static final int ERROR_EMPTY_LIST = 10003;

    public static final BigDecimal m_objBigDecZero = new BigDecimal("0.00");

    public static final String STR_PROGRAM_INIT_FILE = "..%sconf%sema.ini";
    // Action Item
    public static final String STR_ACTION_BOOK_PROFIT = "Book Profit";

    public static final String STR_ACTION_HOLD = "Hold";

    public static final String STR_ACTION_BUY = "Buy above %.2f S/L %d";

    public static final String STR_ACTION_SELL = "Sell below %.2f S/L %d";

    public static final String STR_EMA_COMBO_ONE = "3,15";

    public static final String STR_EMA_COMBO_TWO = "5,20";

    public static final String STR_EMA_COMBO_MACD = "12,9,26";

    public static final String STR_ILLEGAL_INPUT_FOR_EMA_CALC = "Duration for EMA calculation should be greater than zero. Please check the ema.ini file for possible error. A possible reason could be the value of 'PREFERRED_EMA_COMBO'.";

    // public static final String STR_ILLEGAL_INPUT_FOR_SMA_CALC =
    // "Duration for SMA calculation should be greater than zero.";

    public static final String STR_ILLEGAL_INPUT_FOR_RSI_CALC = "Duration for RSI calculation should be greater than zero. Please check the ema.ini file for possible error. A possible reason could be the value of 'RSI_DATA_DURATION'.";

    public static final String STR_ILLEGAL_INPUT_HISTORICL_DATA_DURATION = "Duration for retrieval of historical data should be greater than 30. Please check the ema.ini file for possible error. A possible reason could be the value of 'HISTORICAL_DATA_DURATION'.";

    public static final String STR_ILLEGAL_INPUT_FOR_ADX_CALC = "Duration for ADX calculation should be greater than zero. Please check the ema.ini file for possible error. A possible reason could be the value of 'ADX_DATA_DURATION'.";

    public static final String STR_CHECK_MEMORY_STATUS = "%sTotal heap (memory) available in the JVM is %d KB and free memory is %d KB.";

    public static final String STR_USER_EMAIL_ADDRESS_KEY_NAME = "USER_EMAIL_ADDRESS";

    public static final String STR_USER_EMAIL_PASSCODE_KEY_NAME = "USER_EMAIL_PASSCODE";

    public static final String STR_USER_EMAIL_OAUTH_TOKEN_KEY_NAME = "USER_EMAIL_OAUTH_TOKEN";

    public static final String STR_USER_EMAIL_OAUTH_TOKEN_SECRET_NAME = "USER_EMAIL_OAUTH_TOKEN_SECRET";

    public static final String STR_RECIPIENT_EMAIL_ADDRESS_KEY_NAME = "RECIPIENT_EMAIL_ADDRESS";

    public static final String STR_EMAIL_SUBJECT_KEY_NAME = "EMAIL_SUBJECT";

    public static final String STR_EMAIL_BODY_KEY_NAME = "EMAIL_BODY";

    public static final String STR_DEFAULT_EMAIL_SUBJECT = "Reports generated from EMACalculator program [DataSource - %s]";

    public static final String STR_BOLLINGER_DURATION_KEY_NAME = "BOLLINGER_DATA_DURATION";

    public static final String STR_ILLEGAL_INPUT_FOR_BOLLINGER_CALC = "Duration for Bollinger Band calculation should be greater than zero. Please check the ema.ini file for possible error. A possible reason could be the value of 'BOLLINGER_DATA_DURATION'.";

    public static final String STR_ILLEGAL_INPUT_FOR_BOOLEAN_TYPE = "Illegal %s token value specified in ema.ini file. Please specify either 0 or 1.";

    public static final String STR_MISSING_PROGRAM_INI_FILE = "Program settings file not found. Default data will be used to calculate EMA.";

    public static final String STR_COM_FIN_PRESISTENCE_CLASS = "com.fin.persistence.%s";

    public static final String STR_ENABLE_HIBERNATE_KEY_NAME = "ENABLE_HIBERNATE";

    public static final String STR_POJO_DESTINATION_DIR_KEY_NAME = "POJO_DESTINATION_DIR";

    public static final String STR_POJO_DESTINATION_FILE_KEY_NAME = "POJO_DESTINATION_FILE";

    public static final String STR_SCHEMA_DESTINATION_FILE_NAME = "SCHEMA_DESTINATION_FILE";

    public static final String STR_HBM_DESTINATION_DIR_KEY_NAME = "HBM_DESTINATION_DIR";

    public static final String STR_HBM_DESTINATION_FILE_KEY_NAME = "HBM_DESTINATION_FILE";

    public static final String STR_MYSQL_GENERIC_QUERY = "SELECT * FROM StockQuote.aban a order by TradeDate DESC LIMIT 1;";

    public static final String STR_MYSQL_SELECT_TRADEDATE = "SELECT TradeDate from StockQuote.aban a order by TradeDate DESC LIMIT 1;";

    public static final String STR_ROC_DURATION_KEY_NAME = "ROC_DATA_DURATION";

    public static final String STR_GEN_ROC_REPORT_KEY_NAME = "GENERATE_ROC_REPORT";

    public static final String STR_ILLEGAL_INPUT_FOR_ROC_CALC = "Duration for ROC calculation should be greater than zero. Please check the ema.ini file for possible error. A possible reason could be the value of 'ROC_DATA_DURATION'.";

    public static final String STR_ILLEGAL_INPUT_FOR_NUMBER_OF_STOCK_ARCHIVER_THREAD = "Number of thread for stock quote archiving should be greater than zero. Please check the ema.ini file for possible error. A possible reason could be the value of 'NUMBER_OF_STOCK_ARCHIVER_THREAD'.";
    
    String STR_FILE_SYSTEM_XML_APPLICATION_CONTEXT = "%ssrc%smain%sjava%scom%sfin%stechnical%sBeans.xml";
    
    String STR_MISSING_DATA_SOURCE_SETTINGS = "Specified data source in ema.ini having name '%s' does not specify valid value.";
    
}
