package com.fin.technical;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author Manoj Kumar Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public class Util {

    private String[] m_strStockCodeValue;

    private String m_strDataSource = "";

    private ArrayList<Integer> m_nEMADurationValue;

    private ArrayList<String> m_strEMADurationValue = null;

    private String m_strPreferredEMAComboValue = "3,15";

    private int m_nHistDataDurationValue = 365;

    private int m_nNumberOfStockArchiverThread = 2;

    private int m_nRSIDurationValue = 14;// Default value

    private int m_nGenMasterEMAReportValue = 1;

    private int m_nGenPivotPointReportValue = 1;

    private int m_nGenStockEMAReportValue = 1;

    private int m_nSMA20Days = 1;

    private int m_nSMA50Days = 0;

    private int m_nSMA200Days = 0;

    private int m_nGenRSIReportValue = 1;

    private int m_nGenRSIDebugInfoValue = 0;

    private int m_nGenADLReportValue = 1;

    private int m_nGenCCIReportValue = 1;

    private int m_nGenADXReportValue = 1;

    private int m_nADXDurationValue = 14;

    private int m_nGenBollDebugInfoValue = 1;

    private int m_nGenBollReportValue = 1;

    private int m_nBollingerDurationValue = 20;// Default value

    private int m_nGenADXDebugInfoValue = 0;

    private static String m_strWorkingDirectory = null;

    public static String m_strFileSeparator = null;

    private String m_strReportLocation = "";

    private String m_strSchemaTemplateLocation = "";

    private String m_strPojoTemplateLocation = "";

    private String m_strHbmTemplateLocation = "";

    private String m_strReportFileType = "xls";

    private static String m_strReportsPath = null;

    private static String m_strHibernateMappingPath = null;

    private static String m_strStockQuotePath = null;

    private static String m_strUserEmailAddress = null;

    private static String m_strUserEmailPasscode = null;

    private static String m_strUserEmailOAuthToken = null;

    private static String m_strUserEmailOAuthTokenSecret = null;

    private static String m_strArchiveStockQuote = null;

    private static String[] m_strRecipientEmailAddress;

    private String m_strEmailSubject = null;

    private String m_strEmailBody = null;

    private int m_nSaveIntoDatabase = 0;

    private int m_nGenSchemaFile = 0;

    private int m_nGenPojoFile = 0;

    private int m_nGenHibernateMapping = 0;

    private String m_strPojoDestinationDir = null;

    private String m_strPojoDestinationFile = null;

    private String m_strHbmDestinationDir = null;

    private String m_strHbmDestinationFile = null;

    private String m_strDatabaseName = null;

    private String m_strSchemaTemplate = null;

    private String m_strSchemaDestinationFile = null;

    private String m_strHibernateMappingTemplate = null;

    private String m_strXMLHeaderTemplate = null;

    private String m_strClassEntityTemplate = null;

    public static final String m_strCRLF = System.getProperty("line.separator");

    private int m_nROCDurationValue = 14;// Default value

    private int m_nGenROCReportValue = 1;

    private String m_strGoogleFinanceMainURL = null;

    private String m_strGoogleFinanceParamURL = null;

    private String m_strYahooFinanceCSQURL = null;

    private String m_strYahooFinanceHistoricalSQURL = null;
    
    ApplicationContext m_objSpringAppContext = null;

    // Private constructor prevents instantiation from other classes
    private Util() {
        m_nEMADurationValue = new ArrayList<Integer>(); // m_nEMADurationValue
        m_nEMADurationValue.add(new Integer(3)); // default value
        m_nEMADurationValue.add(new Integer(15)); // default value
        m_nEMADurationValue.trimToSize();
        m_strEMADurationValue = new ArrayList<String>();
        m_strWorkingDirectory = System
                .getProperty(Constants.STR_CURRENT_WORKING_DIR);
        m_strFileSeparator = System.getProperty(Constants.STR_FILE_SEPARATOR);        
    }

    /**
     * SingletonHolder is loaded on the first execution of
     * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
     * not before.
     */
    private static class SingletonHolder {
        // Later, when I implement Factory pattern, below code needs to be
        // re-written.
        public static final Util objUtil = new Util();
        
        // https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#clone()
        public Object clone() throws CloneNotSupportedException {
        	   throw new CloneNotSupportedException();
        }
    }

    public static Util getInstance() {
        return SingletonHolder.objUtil;
    }

    public String[] getStockCodeValue() {
        return m_strStockCodeValue;
    }

    public void setStockCodeValue(String[] strStockCodeValue) {
        m_strStockCodeValue = strStockCodeValue;
    }

    public String getDataSourceSettings() {
        return m_strDataSource;
    }

    public void setDataSourceSettings(String strDataSource) {
        m_strDataSource = strDataSource;
    }

    public int[] getEMADurationIntValue() {
        int[] nEMADurationValue = new int[m_nEMADurationValue.size()];
        for (int nIdx = 0; nIdx < m_nEMADurationValue.size(); nIdx++) {
            nEMADurationValue[nIdx] = m_nEMADurationValue.get(nIdx).intValue();
        }
        return nEMADurationValue;
    }

    public void setEMADurationIntValue(int[] nEMADurationValue) {
        for (int nIdx = 0; nIdx < nEMADurationValue.length; nIdx++) {
            m_nEMADurationValue.set(nIdx, new Integer(nEMADurationValue[nIdx]));
        }
    }

    // public ArrayList<String> getEMADurationValue()
    // {
    // return m_strEMADurationValue;
    // }

    public String[] getEMADurationValue() {
        String[] strEMADurationValue = new String[m_strEMADurationValue.size()];
        m_strEMADurationValue.toArray(strEMADurationValue);
        return strEMADurationValue;
    }

    public void setEMADurationValue(String[] strEMADurationValue) {
        for (int nIdx = 0; nIdx < strEMADurationValue.length; nIdx++) {
            m_strEMADurationValue.set(nIdx, strEMADurationValue[nIdx]);
        }
    }

    public String getPreferredEMAComboValue() {
        return m_strPreferredEMAComboValue;
    }

    public void setPreferredEMAComboValue(String strPreferredEMAComboValue) {
        m_strPreferredEMAComboValue = strPreferredEMAComboValue;
    }

    public int getHistDataDurationValue() {
        return m_nHistDataDurationValue;
    }

    public void setHistDataDurationValue(int nHistDataDurationValue) {
        m_nHistDataDurationValue = nHistDataDurationValue;
    }

    public int getNumberOfStockArchiverThread() {
        return m_nNumberOfStockArchiverThread;
    }

    public void setNumberOfStockArchiverThread(int nNumberOfStockArchiverThread) {
        m_nNumberOfStockArchiverThread = nNumberOfStockArchiverThread;
    }

    public int getRSIDurationValue() {
        return m_nRSIDurationValue;
    }

    public void setRSIDurationValue(int nRSIDurationValue) {
        m_nRSIDurationValue = nRSIDurationValue;
    }

    public int getGenStockEMAReportValue() {
        return m_nGenStockEMAReportValue;
    }

    public void setGenStockEMAReportValue(int nGenStockEMAReportValue) {
        m_nGenStockEMAReportValue = nGenStockEMAReportValue;
    }

    public int getGenMasterEMAReportValue() {
        return m_nGenMasterEMAReportValue;
    }

    public void setGenMasterEMAReportValue(int nGenMasterEMAReportValue) {
        m_nGenMasterEMAReportValue = nGenMasterEMAReportValue;
    }

    public int getGenPivotPointReportValue() {
        return m_nGenPivotPointReportValue;
    }

    public void setGenPivotPointReportValue(int nGenPivotPointReportValue) {
        m_nGenPivotPointReportValue = nGenPivotPointReportValue;
    }

    public int getGenRSIReportValue() {
        return m_nGenRSIReportValue;
    }

    public void setGenRSIReportValue(int nGenRSIReportValue) {
        m_nGenRSIReportValue = nGenRSIReportValue;
    }

    public int getGenRSIDebugInfo() {
        return m_nGenRSIDebugInfoValue;
    }

    public void setGenRSIDebugInfo(int nGenRSIDebugInfoValue) {
        m_nGenRSIDebugInfoValue = nGenRSIDebugInfoValue;
    }

    public int getGenADLReportValue() {
        return m_nGenADLReportValue;
    }

    public void setGenADLReportValue(int nGenADLReportValue) {
        m_nGenADLReportValue = nGenADLReportValue;
    }

    public int getGenCCIReportValue() {
        return m_nGenCCIReportValue;
    }

    public void setGenCCIReportValue(int nGenCCIReportValue) {
        m_nGenCCIReportValue = nGenCCIReportValue;
    }

    public int getGenADXReportValue() {
        return m_nGenADXReportValue;
    }

    public void setGenADXReportValue(int nGenADXReportValue) {
        m_nGenADXReportValue = nGenADXReportValue;
    }

    public int getADXDurationValue() {
        return m_nADXDurationValue;
    }

    public void setADXDurationValue(int nADXDurationValue) {
        m_nADXDurationValue = nADXDurationValue;
    }

    public int getGenBollDebugInfoValue() {
        return m_nGenBollDebugInfoValue;
    }

    public void setGenBollDebugInfoValue(int nGenBollDebugInfoValue) {
        m_nGenBollDebugInfoValue = nGenBollDebugInfoValue;
    }

    public int getGenBollReportValue() {
        return m_nGenBollReportValue;
    }

    public void setGenBollReportValue(int nGenBollReportValue) {
        m_nGenBollReportValue = nGenBollReportValue;
    }

    public int getGenADXDebugInfo() {
        return m_nGenADXDebugInfoValue;
    }

    public void setADXGenDebugInfo(int nGenADXDebugInfoValue) {
        m_nGenADXDebugInfoValue = nGenADXDebugInfoValue;
    }

    public String getReportLocation() {
        return m_strReportLocation;
    }

    public void setReportLocation(String strReportLocation) {
        m_strReportLocation = strReportLocation;
    }

    public String getSchemaTemplateLocation() {
        return m_strSchemaTemplateLocation;
    }

    public void setSchemaTemplateLocation(String strSchemaTemplateLocation) {
        m_strSchemaTemplateLocation = strSchemaTemplateLocation;
    }

    public String getPojoTemplateLocation() {
        return m_strPojoTemplateLocation;
    }

    public void setPojoTemplateLocation(String strPojoTemplateLocation) {
        m_strPojoTemplateLocation = strPojoTemplateLocation;
    }

    public String getHbmTemplateLocation() {
        return m_strHbmTemplateLocation;
    }

    public void setHbmTemplateLocation(String strHbmTemplateLocation) {
        m_strHbmTemplateLocation = strHbmTemplateLocation;
    }

    public String getReportFileType() {
        return m_strReportFileType;
    }

    public void setReportFileType(String strReportFileType) {
        m_strReportFileType = strReportFileType;
    }

    public String getUserEmailAddress() {
        return m_strUserEmailAddress;
    }

    public void setUserEmailAddress(String strUserEmailAddress) {
        m_strUserEmailAddress = strUserEmailAddress;
    }

    public String getUserEmailPasscode() {
        return m_strUserEmailPasscode;
    }

    public void setUserEmailPasscode(String strUserEmailPasscode) {
        m_strUserEmailPasscode = strUserEmailPasscode;
    }

    public String getUserEmailOAuthToken() {
        return m_strUserEmailOAuthToken;
    }

    public void setUserEmailOAuthToken(String strUserEmailOAuthToken) {
        m_strUserEmailOAuthToken = strUserEmailOAuthToken;
    }

    public String getUserEmailOAuthTokenSecret() {
        return m_strUserEmailOAuthTokenSecret;
    }

    public void setUserEmailOAuthTokenSecret(String strUserEmailOAuthTokenSecret) {
        m_strUserEmailOAuthTokenSecret = strUserEmailOAuthTokenSecret;
    }

    public int getGenSMA20DaysReportValue() {
        return m_nSMA20Days;
    }

    public void setGenSMA20DaysReportValue(int nSMA20Days) {
        m_nSMA20Days = nSMA20Days;
    }

    public int getGenSMA50DaysReportValue() {
        return m_nSMA50Days;
    }

    public void setGenSMA50DaysReportValue(int nSMA50Days) {
        m_nSMA50Days = nSMA50Days;
    }

    public int getGenSMA200DaysReportValue() {
        return m_nSMA200Days;
    }

    public void setGenSMA200DaysReportValue(int nSMA200Days) {
        m_nSMA200Days = nSMA200Days;
    }

    public void readProgramInput() {
        File objFile = null;
        FileInputStream fisObj = null;
        try {
            String strValue = null;
            System.out.println(String.format(
                    Constants.STR_MSG_CURRENT_WORKING_DIR,
                    m_strWorkingDirectory));
            String strProgramInitFile = String.format(
                    Constants.STR_PROGRAM_INIT_FILE, m_strFileSeparator,
                    m_strFileSeparator);
            objFile = new File(strProgramInitFile);
            if (objFile != null) {
                if (!objFile.canRead()) {
                    objFile.setReadable(true, true);
                }
                fisObj = new FileInputStream(objFile);
            }

            Properties propProgInput = new Properties();
            propProgInput.load(fisObj);

            if (propProgInput.containsKey(Constants.STR_STOCK_CODE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_STOCK_CODE_KEY_NAME)).trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strStockCodeValue = strValue
                            .split(Constants.STR_SPILT_ON_COMMA);
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_STOCK_CODE_DATA_SOURCE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_STOCK_CODE_DATA_SOURCE_KEY_NAME))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strDataSource = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_PREFERRED_EMA_COMBO_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_PREFERRED_EMA_COMBO_KEY_NAME))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    String[] strEMADurations = strValue
                            .split(Constants.STR_SPILT_ON_COMMA);
                    boolean bLegalInput = true;
                    if (strEMADurations.length >= 2) {
                        for (int nIdx = 0; nIdx < strEMADurations.length; nIdx++) {
                            try {
                                int nTemp = (new Integer(
                                        strEMADurations[nIdx].trim()))
                                        .intValue();
                                if (nTemp <= 0) {
                                    bLegalInput = false;
                                    break;
                                }
                            } catch (NumberFormatException nfeExcep) {
                                bLegalInput = false;
                                break;
                            }
                        }
                        if (bLegalInput) {
                            m_strPreferredEMAComboValue = strValue;
                        }
                    }
                }
            }
            if (propProgInput.containsKey(Constants.STR_EMA_DURATION_KEY_NAME)) {
                try {
                    strValue = ((String) propProgInput
                            .get(Constants.STR_EMA_DURATION_KEY_NAME)).trim();
                    String[] strEMADurationValue = null;
                    if (strValue != null && strValue.length() > 0) {
                        strEMADurationValue = strValue
                                .split(Constants.STR_SPILT_ON_COLON);
                    }
                    if (strEMADurationValue.length == 1) {
                        m_strPreferredEMAComboValue = strEMADurationValue[0];
                    }

                    for (int nEMACombo = 0; nEMACombo < strEMADurationValue.length; nEMACombo++) {
                        String strEMACombo = strEMADurationValue[nEMACombo]
                                .trim();
                        if (strEMACombo != null && strEMACombo.length() > 0) {
                            try {
                                StringTokenizer strToken = new StringTokenizer(
                                        strEMACombo,
                                        Constants.STR_SPILT_ON_COMMA);
                                while (strToken.hasMoreTokens()) {
                                    String strTemp = strToken.nextToken()
                                            .trim();
                                    if (strTemp != null && !strTemp.isEmpty()) {
                                        // we are interested in Storing and/or
                                        // using the int value.
                                        // In case a non-numeric value is
                                        // specified, NumberFormatException will
                                        // be thrown
                                        // and control will be transferred to
                                        // catch block, and the statement
                                        // following the while
                                        // block shouldn't get executed.
                                        int nTemp = (new Integer(strTemp))
                                                .intValue();
                                        if (nTemp <= 0) {
                                            // a new IllegalInputException
                                            // should have been thrown.
                                            throw (new IllegalInputException(
                                                    Constants.STR_ILLEGAL_INPUT_FOR_EMA_CALC));
                                        }
                                    }
                                }
                                m_strEMADurationValue.add(nEMACombo,
                                        strEMADurationValue[nEMACombo]);
                            } catch (NumberFormatException nfeExcep) {
                                nfeExcep.printStackTrace();
                            } catch (IllegalInputException iieExcep) {
                                iieExcep.printStackTrace();
                            }

                        }
                    }
                    m_strEMADurationValue.trimToSize();
                } catch (NumberFormatException nfeExcep) {
                    nfeExcep.printStackTrace();
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_HIST_DATA_DURATION_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_HIST_DATA_DURATION_KEY_NAME)).trim();
                int nHistDataDurationValue = 0;
                if (strValue != null && !strValue.isEmpty()) {
                    boolean bLegalInput = true;
                    try {
                        nHistDataDurationValue = (new Integer(strValue))
                                .intValue();
                        if (nHistDataDurationValue <= 30) {
                            throw (new IllegalInputException(
                                    Constants.STR_ILLEGAL_INPUT_FOR_EMA_CALC));
                        }
                    } catch (NumberFormatException nfeExcep) {
                        bLegalInput = false;
                        nfeExcep.printStackTrace();
                    } catch (IllegalInputException iieExcep) {
                        bLegalInput = false;
                        iieExcep.printStackTrace();
                    }
                    if (bLegalInput) {
                        m_nHistDataDurationValue = nHistDataDurationValue;
                    }
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_NUMBER_OF_STOCK_ARCHIVER_THREAD_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_NUMBER_OF_STOCK_ARCHIVER_THREAD_KEY_NAME))
                        .trim();
                int nNumberOfStockArchiverThread = 0;
                if (strValue != null && !strValue.isEmpty()) {
                    boolean bLegalInput = true;
                    try {
                        nNumberOfStockArchiverThread = (new Integer(strValue))
                                .intValue();
                        if (nNumberOfStockArchiverThread <= 0) {
                            throw (new IllegalInputException(
                                    Constants.STR_ILLEGAL_INPUT_FOR_NUMBER_OF_STOCK_ARCHIVER_THREAD));
                        }
                    } catch (NumberFormatException nfeExcep) {
                        bLegalInput = false;
                        nfeExcep.printStackTrace();
                    } catch (IllegalInputException iieExcep) {
                        bLegalInput = false;
                        iieExcep.printStackTrace();
                    }
                    if (bLegalInput) {
                        m_nNumberOfStockArchiverThread = nNumberOfStockArchiverThread;
                    }
                }
            }
            if (propProgInput.containsKey(Constants.STR_RSI_DURATION_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_RSI_DURATION_KEY_NAME)).trim();
                int nRSIDurationValue = 0;
                if (strValue != null && !strValue.isEmpty()) {
                    boolean bLegalInput = true;
                    try {
                        nRSIDurationValue = (new Integer(strValue)).intValue();
                        if (nRSIDurationValue <= 0) {
                            throw (new IllegalInputException(
                                    Constants.STR_ILLEGAL_INPUT_FOR_RSI_CALC));
                        }

                    } catch (NumberFormatException nfeExcep) {
                        bLegalInput = false;
                        nfeExcep.printStackTrace();
                    } catch (IllegalInputException iieExcep) {
                        bLegalInput = false;
                        iieExcep.printStackTrace();
                    }
                    if (bLegalInput) {
                        m_nRSIDurationValue = nRSIDurationValue;
                    }
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GEN_STOCK_EMA_REPORT_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_STOCK_EMA_REPORT_KEY_NAME))
                        .trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenStockEMAReportValue = (new Integer(strValue))
                            .intValue();
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GEN_EMA_CROSOVER_REPORT_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_EMA_CROSOVER_REPORT_KEY_NAME))
                        .trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenMasterEMAReportValue = (new Integer(strValue))
                            .intValue();
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GEN_PIVOT_POINT_REPORT_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_PIVOT_POINT_REPORT_KEY_NAME))
                        .trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenPivotPointReportValue = (new Integer(strValue))
                            .intValue();
                }
            }
            // if(propProgInput.containsKey(Constants.SIMPLE_MOVING_AVERAGE_20_DAYS_KEY_NAME))
            // {
            // strValue =
            // ((String)propProgInput.get(Constants.SIMPLE_MOVING_AVERAGE_20_DAYS_KEY_NAME)).trim();
            // if(strValue != null && !strValue.isEmpty())
            // {
            // m_nSMA20Days = (new Integer(strValue)).intValue();
            // }
            // }
            if (propProgInput
                    .containsKey(Constants.SIMPLE_MOVING_AVERAGE_50_DAYS_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.SIMPLE_MOVING_AVERAGE_50_DAYS_KEY_NAME))
                        .trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nSMA50Days = (new Integer(strValue)).intValue();
                }
            }
            if (propProgInput
                    .containsKey(Constants.SIMPLE_MOVING_AVERAGE_200_DAYS_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.SIMPLE_MOVING_AVERAGE_200_DAYS_KEY_NAME))
                        .trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nSMA200Days = (new Integer(strValue)).intValue();
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GEN_RSI_REPORT_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_RSI_REPORT_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenRSIReportValue = (new Integer(strValue)).intValue();
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GEN_RSI_DEBUG_INFO_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_RSI_DEBUG_INFO_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenRSIDebugInfoValue = (new Integer(strValue))
                            .intValue();
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GEN_ADL_REPORT_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_ADL_REPORT_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenADLReportValue = (new Integer(strValue)).intValue();
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GEN_CCI_REPORT_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_CCI_REPORT_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenCCIReportValue = (new Integer(strValue)).intValue();
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GEN_ADX_REPORT_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_ADX_REPORT_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenADXReportValue = (new Integer(strValue)).intValue();
                }
            }
            if (propProgInput.containsKey(Constants.STR_ADX_DURATION_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_ADX_DURATION_KEY_NAME)).trim();
                int nADXDurationValue = 0;
                if (strValue != null && !strValue.isEmpty()) {
                    boolean bLegalInput = true;
                    try {
                        nADXDurationValue = (new Integer(strValue)).intValue();
                        if (nADXDurationValue <= 0) {
                            throw (new IllegalInputException(
                                    Constants.STR_ILLEGAL_INPUT_FOR_ADX_CALC));
                        }

                    } catch (NumberFormatException nfeExcep) {
                        bLegalInput = false;
                        nfeExcep.printStackTrace();
                    } catch (IllegalInputException iieExcep) {
                        bLegalInput = false;
                        iieExcep.printStackTrace();
                    }
                    if (bLegalInput) {
                        m_nADXDurationValue = nADXDurationValue;
                    }
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GEN_ADX_DEBUG_INFO_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_ADX_DEBUG_INFO_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenADXDebugInfoValue = (new Integer(strValue))
                            .intValue();
                }
            }

            if (propProgInput
                    .containsKey(Constants.STR_GEN_BOLL_REPORT_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_BOLL_REPORT_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenBollReportValue = (new Integer(strValue)).intValue();
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GEN_BOLL_DEBUG_INFO_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_BOLL_DEBUG_INFO_KEY_NAME))
                        .trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenBollDebugInfoValue = (new Integer(strValue))
                            .intValue();
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_BOLLINGER_DURATION_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_BOLLINGER_DURATION_KEY_NAME)).trim();
                int nBollingerDurationValue = 0;
                if (strValue != null && !strValue.isEmpty()) {
                    boolean bLegalInput = true;
                    try {
                        nBollingerDurationValue = (new Integer(strValue))
                                .intValue();
                        if (nBollingerDurationValue <= 0) {
                            throw (new IllegalInputException(
                                    Constants.STR_ILLEGAL_INPUT_FOR_BOLLINGER_CALC));
                        }

                    } catch (NumberFormatException nfeExcep) {
                        bLegalInput = false;
                        nfeExcep.printStackTrace();
                    } catch (IllegalInputException iieExcep) {
                        bLegalInput = false;
                        iieExcep.printStackTrace();
                    }
                    if (bLegalInput) {
                        m_nBollingerDurationValue = nBollingerDurationValue;
                    }
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_REPORT_LOCATION_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_REPORT_LOCATION_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_strReportLocation = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_POJO_TEMPLATE_FILE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_POJO_TEMPLATE_FILE_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_strPojoTemplateLocation = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_HBM_TEMPLATE_FILE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_HBM_TEMPLATE_FILE_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_strHbmTemplateLocation = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_SCHEMA_TEMPLATE_FILE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_SCHEMA_TEMPLATE_FILE_KEY_NAME))
                        .trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_strSchemaTemplateLocation = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_REPORT_FILE_TYPE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_REPORT_FILE_TYPE_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_strReportFileType = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_USER_EMAIL_ADDRESS_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_USER_EMAIL_ADDRESS_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_strUserEmailAddress = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_USER_EMAIL_PASSCODE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_USER_EMAIL_PASSCODE_KEY_NAME))
                        .trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_strUserEmailPasscode = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_USER_EMAIL_OAUTH_TOKEN_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_USER_EMAIL_OAUTH_TOKEN_KEY_NAME))
                        .trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_strUserEmailOAuthToken = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_USER_EMAIL_OAUTH_TOKEN_SECRET_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_USER_EMAIL_OAUTH_TOKEN_SECRET_NAME))
                        .trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_strUserEmailOAuthTokenSecret = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_ARCHIVE_STOCK_QUOTE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_ARCHIVE_STOCK_QUOTE_KEY_NAME))
                        .trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_strArchiveStockQuote = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_RECIPIENT_EMAIL_ADDRESS_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_RECIPIENT_EMAIL_ADDRESS_KEY_NAME))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strRecipientEmailAddress = strValue
                            .split(Constants.STR_SPILT_ON_COMMA);
                }
            }
            if (propProgInput.containsKey(Constants.STR_EMAIL_SUBJECT_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_EMAIL_SUBJECT_KEY_NAME)).trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strEmailSubject = strValue;
                }
            }
            if (propProgInput.containsKey(Constants.STR_EMAIL_BODY_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_EMAIL_BODY_KEY_NAME)).trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strEmailBody = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GENERATE_SCHEMA_FILE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GENERATE_SCHEMA_FILE_KEY_NAME))
                        .trim();
                int nGenSchemaFile = 0;
                if (strValue != null && !strValue.isEmpty()) {
                    boolean bLegalInput = true;
                    try {
                        nGenSchemaFile = (new Integer(strValue)).intValue();
                        if (nGenSchemaFile < 0 || nGenSchemaFile > 1) {
                            String strErrorMessage = String
                                    .format(Constants.STR_ILLEGAL_INPUT_FOR_BOOLEAN_TYPE,
                                            Constants.STR_GENERATE_SCHEMA_FILE_KEY_NAME);
                            throw (new IllegalInputException(strErrorMessage));
                        }
                    } catch (NumberFormatException nfeExcep) {
                        bLegalInput = false;
                        nfeExcep.printStackTrace();
                    } catch (IllegalInputException iieExcep) {
                        bLegalInput = false;
                        iieExcep.printStackTrace();
                    }
                    if (bLegalInput) {
                        m_nGenSchemaFile = nGenSchemaFile;
                    }
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_ENABLE_HIBERNATE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_ENABLE_HIBERNATE_KEY_NAME)).trim();
                int nSaveIntoDatabase = 0;
                if (strValue != null && !strValue.isEmpty()) {
                    boolean bLegalInput = true;
                    try {
                        nSaveIntoDatabase = (new Integer(strValue)).intValue();
                        if (nSaveIntoDatabase < 0 || nSaveIntoDatabase > 1) {
                            String strErrorMessage = String
                                    .format(Constants.STR_ILLEGAL_INPUT_FOR_BOOLEAN_TYPE,
                                            Constants.STR_ENABLE_HIBERNATE_KEY_NAME);
                            throw (new IllegalInputException(strErrorMessage));
                        }
                    } catch (NumberFormatException nfeExcep) {
                        bLegalInput = false;
                        nfeExcep.printStackTrace();
                    } catch (IllegalInputException iieExcep) {
                        bLegalInput = false;
                        iieExcep.printStackTrace();
                    }
                    if (bLegalInput) {
                        m_nSaveIntoDatabase = nSaveIntoDatabase;
                    }
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GENERATE_POJO_FILE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GENERATE_POJO_FILE_KEY_NAME)).trim();
                int nGenPojoFile = 0;
                if (strValue != null && !strValue.isEmpty()) {
                    boolean bLegalInput = true;
                    try {
                        nGenPojoFile = (new Integer(strValue)).intValue();
                        if (nGenPojoFile < 0 || nGenPojoFile > 1) {
                            String strErrorMessage = String
                                    .format(Constants.STR_ILLEGAL_INPUT_FOR_BOOLEAN_TYPE,
                                            Constants.STR_GENERATE_POJO_FILE_KEY_NAME);
                            throw (new IllegalInputException(strErrorMessage));
                        }
                    } catch (NumberFormatException nfeExcep) {
                        bLegalInput = false;
                        nfeExcep.printStackTrace();
                    } catch (IllegalInputException iieExcep) {
                        bLegalInput = false;
                        iieExcep.printStackTrace();
                    }
                    if (bLegalInput) {
                        m_nGenPojoFile = nGenPojoFile;
                    }
                }
            }
            if (propProgInput.containsKey(Constants.STR_DATABASE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_DATABASE_KEY_NAME)).trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strDatabaseName = strValue;
                }
            }
            if (propProgInput.containsKey(Constants.STR_DATABASE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_DATABASE_KEY_NAME)).trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strDatabaseName = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_POJO_DESTINATION_DIR_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_POJO_DESTINATION_DIR_KEY_NAME))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strPojoDestinationDir = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_POJO_DESTINATION_FILE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_POJO_DESTINATION_FILE_KEY_NAME))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strPojoDestinationFile = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_HBM_DESTINATION_DIR_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_HBM_DESTINATION_DIR_KEY_NAME))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strHbmDestinationDir = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_HBM_DESTINATION_FILE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_HBM_DESTINATION_FILE_KEY_NAME))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strHbmDestinationFile = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_SCHEMA_DESTINATION_FILE_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_SCHEMA_DESTINATION_FILE_NAME))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strSchemaDestinationFile = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GENERATE_HIBERNATE_MAPPING_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GENERATE_HIBERNATE_MAPPING_KEY_NAME))
                        .trim();
                int nTemp = 0;
                if (strValue != null && !strValue.isEmpty()) {
                    boolean bLegalInput = true;
                    try {
                        nTemp = (new Integer(strValue)).intValue();
                        if (nTemp < 0 || nTemp > 1) {
                            String strErrorMessage = String
                                    .format(Constants.STR_ILLEGAL_INPUT_FOR_BOOLEAN_TYPE,
                                            Constants.STR_GENERATE_HIBERNATE_MAPPING_KEY_NAME);
                            throw (new IllegalInputException(strErrorMessage));
                        }

                    } catch (NumberFormatException nfeExcep) {
                        bLegalInput = false;
                        nfeExcep.printStackTrace();
                    } catch (IllegalInputException iieExcep) {
                        bLegalInput = false;
                        iieExcep.printStackTrace();
                    }
                    if (bLegalInput) {
                        m_nGenHibernateMapping = nTemp;
                    }
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_HIBERNATE_MAPPING_TEMPLATE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_HIBERNATE_MAPPING_TEMPLATE_KEY_NAME))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strHibernateMappingTemplate = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_XML_HEADER_TEMPLATE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_XML_HEADER_TEMPLATE_KEY_NAME))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strXMLHeaderTemplate = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_CLASS_ENTITY_TEMPLATE_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_CLASS_ENTITY_TEMPLATE_KEY_NAME))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strClassEntityTemplate = strValue;
                }
            }
            if (propProgInput.containsKey(Constants.STR_ROC_DURATION_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_ROC_DURATION_KEY_NAME)).trim();
                int nROCDurationValue = 0;
                if (strValue != null && !strValue.isEmpty()) {
                    boolean bLegalInput = true;
                    try {
                        nROCDurationValue = (new Integer(strValue)).intValue();
                        if (nROCDurationValue <= 0) {
                            throw (new IllegalInputException(
                                    Constants.STR_ILLEGAL_INPUT_FOR_ROC_CALC));
                        }

                    } catch (NumberFormatException nfeExcep) {
                        bLegalInput = false;
                        nfeExcep.printStackTrace();
                    } catch (IllegalInputException iieExcep) {
                        bLegalInput = false;
                        iieExcep.printStackTrace();
                    }
                    if (bLegalInput) {
                        m_nROCDurationValue = nROCDurationValue;
                    }
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GEN_ROC_REPORT_KEY_NAME)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GEN_ROC_REPORT_KEY_NAME)).trim();
                if (strValue != null && !strValue.isEmpty()) {
                    m_nGenROCReportValue = (new Integer(strValue)).intValue();
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GOOGLE_FINANCE_MAIN_URL_KEY)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GOOGLE_FINANCE_MAIN_URL_KEY))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strGoogleFinanceMainURL = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_GOOGLE_FINANCE_PARAM_URL_KEY)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_GOOGLE_FINANCE_PARAM_URL_KEY))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strGoogleFinanceParamURL = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_YAHOO_FINANCE_CURRENCT_STOCK_QUOTE_URL_KEY)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_YAHOO_FINANCE_CURRENCT_STOCK_QUOTE_URL_KEY))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strYahooFinanceCSQURL = strValue;
                }
            }
            if (propProgInput
                    .containsKey(Constants.STR_YAHOO_FINANCE_HISTORICAL_STOCK_QUOTE_URL_KEY)) {
                strValue = ((String) propProgInput
                        .get(Constants.STR_YAHOO_FINANCE_HISTORICAL_STOCK_QUOTE_URL_KEY))
                        .trim();
                if (strValue != null && strValue.length() > 0) {
                    m_strYahooFinanceHistoricalSQURL = strValue;
                }
            }
        } catch (FileNotFoundException fnfeExcep) {
            fnfeExcep.printStackTrace();
            System.out.println(Constants.STR_MISSING_PROGRAM_INI_FILE);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {
            try {
                if (fisObj != null) {
                    fisObj.close();
                }
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace();
            }
        }
    }

    public StringBuffer getParentOfWorkingDir() {
        StringBuffer strBufPath = null;
        File objFile = new File(m_strWorkingDirectory);
        String strParent = objFile.getParent();
        objFile = null;

        objFile = new File(strParent);
        if (objFile.isDirectory()) {
            strBufPath = new StringBuffer(strParent);
        }
        objFile = null;
        return strBufPath;
    }

    public String createOutputDir() {
        StringBuffer strBufPath = getParentOfWorkingDir();

        strBufPath.append(m_strFileSeparator).append(m_strReportLocation)
                .append(m_strFileSeparator);
        strBufPath.append(m_strReportFileType).append(m_strFileSeparator)
                .append(m_strDataSource);
        m_strReportsPath = strBufPath.toString();
        File oFile = new File(m_strReportsPath);
        boolean bExists = oFile.exists();
        if (!bExists) {
            boolean bCanWrite = (new File(m_strWorkingDirectory)).canWrite();
            if (bCanWrite) {
                System.out.println(String.format(
                        Constants.STR_MSG_DIR_NOT_FOUND, m_strReportsPath));
                oFile.mkdirs();
                oFile = null;
                System.out.println(String.format(
                        Constants.STR_MSG_SPECIFIED_DIR_CREATED,
                        m_strReportsPath));
            } else {
                System.out.println(String.format(
                        Constants.STR_MSG_GRANT_WRITE_PERMISSION_DIR,
                        m_strWorkingDirectory));
                return null;
            }
        }
        return m_strReportsPath;
    }

    public String getStockQuoteDir() {
        StringBuffer strBufPath = getParentOfWorkingDir();
        strBufPath.append(m_strFileSeparator).append(
                Constants.STR_DATA_SOURCE_DIR_NAME);
        strBufPath.append(m_strFileSeparator).append(m_strDataSource);
        return strBufPath.toString();
    }

    public String createStockQuoteDir() {
        StringBuffer strBufPath = getParentOfWorkingDir();
        strBufPath.append(m_strFileSeparator).append(
                Constants.STR_DATA_SOURCE_DIR_NAME);
        strBufPath.append(m_strFileSeparator).append(m_strDataSource);
        m_strStockQuotePath = strBufPath.toString();
        File oFile = new File(m_strStockQuotePath);
        boolean bExists = oFile.exists();
        if (!bExists) {
            boolean bCanWrite = (new File(m_strWorkingDirectory)).canWrite();
            if (bCanWrite) {
                System.out.println(String.format(
                        Constants.STR_MSG_DIR_NOT_FOUND, m_strStockQuotePath));
                oFile.mkdirs();
                oFile = null;
                System.out.println(String.format(
                        Constants.STR_MSG_SPECIFIED_DIR_CREATED,
                        m_strStockQuotePath));
            } else {
                System.out.println(String.format(
                        Constants.STR_MSG_GRANT_WRITE_PERMISSION_DIR,
                        m_strWorkingDirectory));
                return null;
            }
        }
        return m_strStockQuotePath;
    }

    public String createHibernateMappingDir() {
        StringBuffer strBufPath = null;
        File objFile = new File(m_strWorkingDirectory);
        if (objFile.isDirectory()) {
            strBufPath = new StringBuffer(m_strWorkingDirectory);
        }

        strBufPath.append(m_strFileSeparator).append("com")
                .append(m_strFileSeparator);
        strBufPath.append("fin").append(m_strFileSeparator)
                .append("persistence");
        m_strHibernateMappingPath = strBufPath.toString();
        File oFile = new File(m_strHibernateMappingPath);
        boolean bExists = oFile.exists();
        if (!bExists) {
            boolean bCanWrite = (new File(m_strWorkingDirectory)).canWrite();
            if (bCanWrite) {
                System.out.println(String.format(
                        Constants.STR_MSG_DIR_NOT_FOUND, m_strReportsPath));
                oFile.mkdirs();
                oFile = null;
                System.out.println(String.format(
                        Constants.STR_MSG_SPECIFIED_DIR_CREATED,
                        m_strReportsPath));
            } else {
                System.out.println(String.format(
                        Constants.STR_MSG_GRANT_WRITE_PERMISSION_DIR,
                        m_strWorkingDirectory));
                return null;
            }
        }
        return m_strHibernateMappingPath;
    }

    public static String checkMemoryStatus() {
        Runtime theJRE = Runtime.getRuntime();
        theJRE.gc();
        long lTotalHeapMemory = theJRE.totalMemory() / 1024;
        long lfreeHeapMemory = theJRE.freeMemory() / 1024;
        String strCRLF = System.getProperty("line.separator");
        return String.format(Constants.STR_CHECK_MEMORY_STATUS, strCRLF,
                lTotalHeapMemory, lfreeHeapMemory);
    }

    public static void logMemoryStatus() {
        System.out.println(checkMemoryStatus());
    }

    public static String getReportsPath() {
        return m_strReportsPath;
    }

    public void setReportsPath(String strReportsPath) {
        m_strReportsPath = strReportsPath;
    }

    public static String getStockQuotePath() {
        if (m_strStockQuotePath == null) {
            m_strStockQuotePath = Util.getInstance().getStockQuoteDir();
        }
        return m_strStockQuotePath;
    }

    public void setStockQuotePath(String strStockQuotePath) {
        m_strStockQuotePath = strStockQuotePath;
    }

    public static String getArchiveStockQuote() {
        return m_strArchiveStockQuote;
    }

    public void setArchiveStockQuote(String strArchiveStockQuote) {
        m_strArchiveStockQuote = strArchiveStockQuote;
    }

    public String[] getRecipientEmailAddress() {
        return m_strRecipientEmailAddress;
    }

    public void setRecipientEmailAddress(String[] strRecipientEmailAddress) {
        m_strRecipientEmailAddress = strRecipientEmailAddress;
    }

    public String getEmailSubject() {
        return m_strEmailSubject;
    }

    public void setEmailSubject(String strEmailSubject) {
        m_strEmailSubject = strEmailSubject;
    }

    public String getEmailBody() {
        return m_strEmailBody;
    }

    public void setEmailBody(String strEmailBody) {
        m_strEmailBody = strEmailBody;
    }

    public int getGenSchemaFile() {
        return m_nGenSchemaFile;
    }

    public void setGenSchemaFile(int nGenSchemaFile) {
        m_nGenSchemaFile = nGenSchemaFile;
    }

    public int getSaveIntoDatabase() {
        return m_nSaveIntoDatabase;
    }

    public void setSaveIntoDatabase(int nSaveIntoDatabase) {
        m_nSaveIntoDatabase = nSaveIntoDatabase;
    }

    public int getGenPojoFile() {
        return m_nGenPojoFile;
    }

    public void setGenPojoFile(int nGenPojoFile) {
        m_nGenPojoFile = nGenPojoFile;
    }

    public String getDatabaseName() {
        return m_strDatabaseName;
    }

    public void setDatabaseName(String strDatabaseName) {
        m_strDatabaseName = strDatabaseName;
    }

    public String getPojoDestinationDir() {
        return m_strPojoDestinationDir;
    }

    public void setPojoDestinationDir(String strPojoDestinationDir) {
        m_strPojoDestinationDir = strPojoDestinationDir;
    }

    public String getPojoDestinationFile() {
        return m_strPojoDestinationFile;
    }

    public void setPojoDestinationFile(String strPojoDestinationFile) {
        m_strPojoDestinationFile = strPojoDestinationFile;
    }

    public String getHbmDestinationDir() {
        return m_strHbmDestinationDir;
    }

    public void setHbmDestinationDir(String strHbmDestinationDir) {
        m_strHbmDestinationDir = strHbmDestinationDir;
    }

    public String getHbmDestinationFile() {
        return m_strHbmDestinationFile;
    }

    public void setHbmDestinationFile(String strHbmDestinationFile) {
        m_strHbmDestinationFile = strHbmDestinationFile;
    }

    public String getSchemaDestinationFile() {
        return m_strSchemaDestinationFile;
    }

    public void setSchemaDestinationFile(String strSchemaDestinationFile) {
        m_strSchemaDestinationFile = strSchemaDestinationFile;
    }

    public String getSchemaTemplate() {
        return m_strSchemaTemplate;
    }

    public void setSchemaTemplate(String strSchemaTemplate) {
        m_strSchemaTemplate = strSchemaTemplate;
    }

    /**
     * @return the m_nGenHibernateMapping
     */
    public int getGenHibernateMapping() {
        return m_nGenHibernateMapping;
    }

    /**
     * @param nGenHibernateMapping
     *            the m_nGenHibernateMapping to set
     */
    public void setM_nGenHibernateMapping(int nGenHibernateMapping) {
        this.m_nGenHibernateMapping = nGenHibernateMapping;
    }

    /**
     * @return the m_strHibernateMappingTemplate
     */
    public String getHibernateMappingTemplate() {
        return m_strHibernateMappingTemplate;
    }

    /**
     * @param strHibernateMappingTemplate
     *            the m_strHibernateMappingTemplate to set
     */
    public void setHibernateMappingTemplate(String strHibernateMappingTemplate) {
        this.m_strHibernateMappingTemplate = strHibernateMappingTemplate;
    }

    public int getBollingerDurationValue() {
        return m_nBollingerDurationValue;
    }

    public void setBollingerDurationValue(int nBollingerDurationValue) {
        m_nBollingerDurationValue = nBollingerDurationValue;
    }

    /**
     * @return the m_strXMLHeaderTemplate
     */
    public String getXMLHeaderTemplate() {
        return m_strXMLHeaderTemplate;
    }

    /**
     * @param m_strXMLHeaderTemplate
     *            the m_strXMLHeaderTemplate to set
     */
    public void setXMLHeaderTemplate(String strXMLHeaderTemplate) {
        m_strXMLHeaderTemplate = strXMLHeaderTemplate;
    }

    /**
     * @return the m_strClassEntityTemplate
     */
    public String getClassEntityTemplate() {
        return m_strClassEntityTemplate;
    }

    /**
     * @param m_strClassEntityTemplate
     *            the m_strClassEntityTemplate to set
     */
    public void setClassEntityTemplate(String strClassEntityTemplate) {
        m_strClassEntityTemplate = strClassEntityTemplate;
    }

    /**
     * @return the m_strWorkingDirectory
     */
    public static String getWorkingDirectory() {
        return m_strWorkingDirectory;
    }

    public int getROCDurationValue() {
        return m_nROCDurationValue;
    }

    public void setROCDurationValue(int nROCDurationValue) {
        m_nROCDurationValue = nROCDurationValue;
    }

    public int getGenROCReportValue() {
        return m_nGenROCReportValue;
    }

    public void setGenROCReportValue(int nGenROCReportValue) {
        m_nGenROCReportValue = nGenROCReportValue;
    }

    private StringBuffer getFileContent(String strSourceFileName) {
        // Read MYSql Schema/Pojo template file from "Template.schema"
        // or "Template.pojo" respectively
        // and store it into a non-mutable String Object
        // template Directory is
        FileReader objFileReader = null;
        BufferedReader brContent = null;
        StringBuffer strBufTemplateFileContent = new StringBuffer();
        try {
            String strLine = null;
            objFileReader = new FileReader(strSourceFileName);
            brContent = new BufferedReader(objFileReader);
            while ((strLine = brContent.readLine()) != null) {
                strBufTemplateFileContent.append(strLine).append(m_strCRLF);
            }
        } catch (Exception excep) {
            excep.printStackTrace(System.out);
        } finally {
            try {
                if (objFileReader != null) {
                    objFileReader.close();
                }
                if (brContent != null) {
                    brContent.close();
                }
            } catch (FileNotFoundException fnfeExcep) {
                fnfeExcep.printStackTrace(System.out);
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace(System.out);
            }
            brContent = null;
        }
        return strBufTemplateFileContent;
    }

    public void generateSchemaFile() {
        if (m_nGenSchemaFile != 1) {
            return;
        }
        // prepare absolute file name. From Current Working Directory, we need
        // to go one level up and then into "schema" folder.
        // In there "Template.sql" needs to be read for generation of
        // "schema.sql"
        String strParentOfWorkingDir = getParentOfWorkingDir().toString();
        StringBuffer strBufTemplateFileName = new StringBuffer(
                strParentOfWorkingDir);
        strBufTemplateFileName.append(String.format(
                m_strSchemaTemplateLocation, m_strFileSeparator,
                m_strFileSeparator));

        StringBuffer strBufTemplateFileContent = getFileContent(strBufTemplateFileName
                .toString());
        // Now use the content of the file read above
        // to generate schema for Different stocks.
        // Just to remind: there is a one-one mapping between
        // each stock and its underlying database table.
        try {
            final String strTemplateFileContent = strBufTemplateFileContent
                    .toString();
            strBufTemplateFileContent.delete(0,
                    strBufTemplateFileContent.length());
            int nNumberOfStock = m_strStockCodeValue.length;
            for (int nIdx = 0; nIdx < nNumberOfStock; nIdx++) {
                String strFinalContent = strTemplateFileContent.replace("#",
                        m_strStockCodeValue[nIdx]);
                strFinalContent = strFinalContent.replace("$",
                        m_strDatabaseName);
                strBufTemplateFileContent.append(strFinalContent);
                strBufTemplateFileContent.append(m_strCRLF);
            }

            StringBuffer strBufSchemaTemplateFile = new StringBuffer(
                    strParentOfWorkingDir);
            strBufSchemaTemplateFile.append(String.format(
                    m_strSchemaDestinationFile, m_strFileSeparator,
                    m_strFileSeparator));

            Charset charset = Charset.forName("US-ASCII");
            File objFile = new File(strBufSchemaTemplateFile.toString());
            if (objFile.isFile() /* && !objFile.exists() */) {
                Path file = Paths.get(strBufSchemaTemplateFile.toString());
                BufferedWriter bwWriter = Files
                        .newBufferedWriter(file, charset);

                try {
                    bwWriter.write(strBufTemplateFileContent.toString(), 0,
                            strBufTemplateFileContent.length());
                    bwWriter.flush();
                    bwWriter.close();
                } catch (IOException ioExcep) {
                    System.err.format("IOException: %s%n", ioExcep);
                }
                strBufTemplateFileContent.delete(0,
                        strBufTemplateFileContent.length());
                strBufTemplateFileContent = null;
            }
            objFile = null;
        } catch (MalformedURLException mueExcep) {
            mueExcep.printStackTrace(System.out);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace(System.out);
        } catch (Exception excep) {
            excep.printStackTrace(System.out);
        } finally {

        }
    }

    public void generatePojo() {
        if (m_nGenPojoFile != 1) {
            return;
        }
        // prepare absolute file name. From Current Working Directory, we need
        // to go one level up and then into "schema" folder.
        // In there "Template.pojo" needs to be read for generation of
        // "ABB.java" etc.
        String strParentOfWorkingDir = getParentOfWorkingDir().toString();
        StringBuffer strBufTemplateFileName = new StringBuffer(
                strParentOfWorkingDir);
        strBufTemplateFileName.append(String.format(m_strPojoTemplateLocation,
                m_strFileSeparator, m_strFileSeparator));

        StringBuffer strBufTemplateFileContent = getFileContent(strBufTemplateFileName
                .toString());
        // Now use the content of the file read above
        // to generate pojo for Different stocks.
        // Just to remind: there is a one-one mapping between
        // each stock and its underlying database table.
        StringBuffer strBufPojoTemplateDir = new StringBuffer(
                strParentOfWorkingDir);
        strBufPojoTemplateDir.append(String.format(m_strPojoDestinationDir,
                m_strFileSeparator, m_strFileSeparator, m_strFileSeparator,
                m_strFileSeparator, m_strFileSeparator, m_strFileSeparator,
                m_strFileSeparator));
        try {
            final String strTemplateFileContent = strBufTemplateFileContent
                    .toString();
            int nNumberOfStock = m_strStockCodeValue.length;
            for (int nIdx = 0; nIdx < nNumberOfStock; nIdx++) {
                StringBuffer strBufFileName = new StringBuffer(
                        strBufPojoTemplateDir);
                strBufFileName.append(
                        String.format(m_strPojoDestinationFile,
                                m_strStockCodeValue[nIdx])).toString();
                String strFinalContent = strTemplateFileContent.replace("$",
                        m_strStockCodeValue[nIdx]);
                /*
                 * File fProgOutput = new File(strBufFileName.toString()); fwObj
                 * = new FileWriter(fProgOutput); fwObj.write(strContent);
                 */
                Charset charset = Charset.forName("US-ASCII");
                File objFile = new File(strBufFileName.toString());
                if (objFile.isFile() /* && !objFile.exists() */) {
                    Path file = Paths.get(strBufFileName.toString());
                    BufferedWriter bwWriter = Files.newBufferedWriter(file,
                            charset);

                    try {
                        bwWriter.write(strFinalContent, 0,
                                strFinalContent.length());
                        bwWriter.flush();
                        bwWriter.close();
                    } catch (IOException ioExcep) {
                        System.err.format("IOException: %s%n", ioExcep);
                    }
                    strBufFileName.delete(0, strBufFileName.length());
                    strBufFileName = null;
                }
                objFile = null;
            }
        } catch (MalformedURLException mueExcep) {
            mueExcep.printStackTrace(System.out);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace(System.out);
        } catch (Exception excep) {
            excep.printStackTrace(System.out);
        } finally {

        }
    }

    public void generateHibernateMapping() {
        if (m_nGenHibernateMapping != 1) {
            return;
        }
        // prepare absolute file name. From Current Working Directory, we need
        // to go one level up and then into "schema" folder.
        // In there "Template.hbm.xml" needs to be read for generation of
        // "ABB.hbm.xml" etc.
        String strParentOfWorkingDir = getParentOfWorkingDir().toString();
        StringBuffer strBufTemplateFileName = new StringBuffer(
                strParentOfWorkingDir);
        strBufTemplateFileName.append(String.format(m_strHbmTemplateLocation,
                m_strFileSeparator, m_strFileSeparator));

        StringBuffer strBufTemplateFileContent = getFileContent(strBufTemplateFileName
                .toString());
        // Now use the content of the file read above
        // to generate pojo for Different stocks.
        // Just to remind: there is a one-one mapping between
        // each stock and its underlying database table.
        StringBuffer strBufPojoTemplateDir = new StringBuffer(
                m_strWorkingDirectory);
        strBufPojoTemplateDir.append(String.format(m_strHbmDestinationDir,
                m_strFileSeparator, m_strFileSeparator, m_strFileSeparator,
                m_strFileSeparator));
        try {
            final String strTemplateFileContent = strBufTemplateFileContent
                    .toString();
            int nNumberOfStock = m_strStockCodeValue.length;
            for (int nIdx = 0; nIdx < nNumberOfStock; nIdx++) {
                StringBuffer strBufFileName = new StringBuffer(
                        strBufPojoTemplateDir);
                strBufFileName.append(
                        String.format(m_strHbmDestinationFile,
                                m_strStockCodeValue[nIdx])).toString();
                String strFinalContent = strTemplateFileContent.replace("$",
                        m_strStockCodeValue[nIdx]);
                /*
                 * File fProgOutput = new File(strBufFileName.toString()); fwObj
                 * = new FileWriter(fProgOutput); fwObj.write(strContent);
                 */
                Charset charset = Charset.forName("US-ASCII");
                File objFile = new File(strBufFileName.toString());
                if (objFile.isFile() || !objFile.exists()) {
                    Path file = Paths.get(strBufFileName.toString());
                    BufferedWriter bwWriter = Files.newBufferedWriter(file,
                            charset);

                    try {
                        bwWriter.write(strFinalContent, 0,
                                strFinalContent.length());
                        bwWriter.flush();
                        bwWriter.close();
                    } catch (IOException ioExcep) {
                        System.err.format("IOException: %s%n", ioExcep);
                    }
                    strBufFileName.delete(0, strBufFileName.length());
                    strBufFileName = null;
                }
                objFile = null;
            }
        } catch (MalformedURLException mueExcep) {
            mueExcep.printStackTrace(System.out);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace(System.out);
        } catch (Exception excep) {
            excep.printStackTrace(System.out);
        } finally {

        }
    }

    public boolean isFileObject(String strAbsoluteFileName)
    {
    	Path file = Paths.get(strAbsoluteFileName.toString());
        if (Files.exists(file, LinkOption.NOFOLLOW_LINKS))
        	return true;

        return false;
    }

    /**
     * @return the m_strGoogleFinanceMainURL
     */
    public String getGoogleFinanceMainURL() {
        return m_strGoogleFinanceMainURL;
    }

    /**
     * @param m_strGoogleFinanceMainURL the m_strGoogleFinanceMainURL to set
     */
    public void setGoogleFinanceMainURL(String m_strGoogleFinanceMainURL) {
        this.m_strGoogleFinanceMainURL = m_strGoogleFinanceMainURL;
    }

    /**
     * @return the m_strGoogleFinanceParamURL
     */
    public String getGoogleFinanceParamURL() {
        return m_strGoogleFinanceParamURL;
    }

    /**
     * @param m_strGoogleFinanceParamURL the m_strGoogleFinanceParamURL to set
     */
    public void setGoogleFinanceParamURL(String m_strGoogleFinanceParamURL) {
        this.m_strGoogleFinanceParamURL = m_strGoogleFinanceParamURL;
    }

    /**
     * @return the m_strYahooFinanceCSQURL
     */
    public String getYahooFinanceCSQURL() {
        return m_strYahooFinanceCSQURL;
    }

    /**
     * @param m_strYahooFinanceCSQURL the m_strYahooFinanceCSQURL to set
     */
    public void setYahooFinanceCSQURL(String m_strYahooFinanceCSQURL) {
        this.m_strYahooFinanceCSQURL = m_strYahooFinanceCSQURL;
    }

    /**
     * @return the m_strYahooFinanceHistoricalSQURL
     */
    public String getYahooFinanceHistoricalSQURL() {
        return m_strYahooFinanceHistoricalSQURL;
    }

    /**
     * @param m_strYahooFinanceHistoricalSQURL the m_strYahooFinanceHistoricalSQURL to set
     */
    public void setYahooFinanceHistoricalSQURL(
            String m_strYahooFinanceHistoricalSQURL) {
        this.m_strYahooFinanceHistoricalSQURL = m_strYahooFinanceHistoricalSQURL;
    }

    public ApplicationContext getSpringFXApplicationContext() {
    	return m_objSpringAppContext;
    }

    public void setSpringFXApplicationContext(ApplicationContext objSpringAppContext) {
    	m_objSpringAppContext = objSpringAppContext;
    }

}
