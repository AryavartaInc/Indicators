package com.fin.technical;

import java.io.BufferedReader;
import com.fin.persistence.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.IllegalFormatException;
import java.util.Vector;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public abstract class DataSource {
    protected Vector<StockPrice> m_lstHistoricalPrice = null;

    protected static Util m_objUtil = null;

    public abstract Vector<StockPrice> getCurrentPriceOfStockOrIndex(
            String strCode);

    public abstract Vector<StockPrice> getHistoricalPriceOfStockOrIndex(
            String strCode, int nPeriod);

    public abstract Vector<StockPrice> getHistoricalPrice(String strCode);

    public abstract void archiveStockQuote(String strCode);

    public abstract void saveStockQuote(String strCode, String[] strValues);

    public abstract void saveStockQuote(String strCode,
            ArrayList<StockQuote> lstStockQuote);

    protected DataSource() {
        // TODO Auto-generated constructor stub
        if (m_lstHistoricalPrice == null) {
            m_lstHistoricalPrice = new Vector<StockPrice>();
        }
    }

    public Vector<StockPrice> getStockPriceList() {
        return m_lstHistoricalPrice;
    }

    /**
     * SingletonHolder is loaded on the first execution of
     * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
     * not before.
     */
    private static class SingletonHolder {
        public static DataSource m_objDataSource = null;

        // Later, when I implement Factory pattern, below code needs to be
        // re-written.
    }

    // An example implementation of Factory method.
    public static DataSource getInstance() {
        if (m_objUtil == null) {
            m_objUtil = Util.getInstance();
        }

        String strDataSource = m_objUtil.getDataSourceSettings();
        if (strDataSource
                .equalsIgnoreCase(Constants.STR_STOCK_CODE_DATA_SOURCE_YAHOO) == true) {
            SingletonHolder.m_objDataSource = new DSYahooFinance();
        } else if (strDataSource
                .equalsIgnoreCase(Constants.STR_STOCK_CODE_DATA_SOURCE_GOOGLE) == true) {
            SingletonHolder.m_objDataSource = new DSGoogleFinance();
        } else if (strDataSource
                .equalsIgnoreCase(Constants.STR_STOCK_CODE_DATA_SOURCE_NSEINDIA) == true) {
            SingletonHolder.m_objDataSource = new DSNSEIndia();
        } else {
            System.out.println(String.format(Constants.STR_MISSING_DATA_SOURCE_SETTINGS, 
            		Constants.STR_STOCK_CODE_DATA_SOURCE_KEY_NAME));
            return null;
        }
        return SingletonHolder.m_objDataSource;
    }

    public static DataSource createObject() {
        DataSource objDS = null;
        String strDataSource = m_objUtil.getDataSourceSettings();
        if (strDataSource
                .equalsIgnoreCase(Constants.STR_STOCK_CODE_DATA_SOURCE_YAHOO) == true) {
            objDS = new DSYahooFinance();
        } else if (strDataSource
                .equalsIgnoreCase(Constants.STR_STOCK_CODE_DATA_SOURCE_GOOGLE) == true) {
            objDS = new DSGoogleFinance();
        } else if (strDataSource
                .equalsIgnoreCase(Constants.STR_STOCK_CODE_DATA_SOURCE_NSEINDIA) == true) {
            objDS = new DSNSEIndia();
        } else {
            System.out
                    .println("Specified data source in ema.ini having name 'STOCK_CODE_DATA_SOURCE' does not specify valid value.");
            return null;
        }
        return objDS;
    }

    public void writeStockQuote(String strStockCode, String strContent) {
        FileWriter fwObj = null;
        try {
            StringBuffer strBufFileName = new StringBuffer(
                    m_objUtil.createStockQuoteDir());
            strBufFileName.append(Util.m_strFileSeparator);
            if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                    Constants.STR_STOCK_CODE_DATA_SOURCE_YAHOO) == true) {
                strBufFileName.append(strStockCode).append(
                        Constants.STR_STOCK_QUOTE_FILE_TYPE_CSV);
            } else if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                    Constants.STR_STOCK_CODE_DATA_SOURCE_GOOGLE) == true) {
                String strStockCodeTemp = strStockCode.replace(':', '.');
                strBufFileName.append(strStockCodeTemp).append(
                        Constants.STR_STOCK_QUOTE_FILE_TYPE_CSV);
            } else if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                    Constants.STR_STOCK_CODE_DATA_SOURCE_NSEINDIA) == true) {
                strBufFileName.append(strStockCode).append(
                        Constants.STR_STOCK_QUOTE_FILE_TYPE_CSV);
            }
            File fProgOutput = new File(strBufFileName.toString());
            fwObj = new FileWriter(fProgOutput);
            fwObj.write(strContent);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } catch (SecurityException seExcep) {
            seExcep.printStackTrace();
        } finally {
            try {
                if (fwObj != null) {
                    fwObj.flush();
                    fwObj.close();
                }
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace();
            }
        }
    }

    public static String getListOfUnderlyings() {
        StringBuffer strBufParam = new StringBuffer();
        StringBuffer strBufStockCodes = new StringBuffer();
        try {
            strBufParam
                    .append("http://www.nseindia.com/content/fo/fo_underlyinglist.htm");
            System.out.print("URL is: ");
            System.out.println(strBufParam.toString());

            URL url = new URL(strBufParam.toString());
            URLConnection urlConn = url.openConnection();
            urlConn.connect();
            // process contents
            BufferedReader brContent = new BufferedReader(
                    new InputStreamReader(urlConn.getInputStream()));
            String strLine = null;

            while ((strLine = brContent.readLine()) != null
                    && !strLine.isEmpty()) {
                if (strLine.contains("/marketinfo/fo/fomwatchsymbol.jsp?key=")) {
                    int nFromIndex = 0;
                    int nToIndex = 0;
                    try {
                        nFromIndex = strLine.indexOf("key=");
                    } catch (Exception excep) {
                        excep.printStackTrace(System.out);
                    }
                    String strTemp = strLine.substring(nFromIndex,
                            strLine.length());
                    try {
                        nFromIndex = strTemp.indexOf(">");
                        nToIndex = strTemp.indexOf("<");
                    } catch (Exception excep) {
                        excep.printStackTrace(System.out);
                    }
                    String strStockCode = strTemp.substring(nFromIndex + 1,
                            nToIndex);
                    strBufStockCodes.append(strStockCode).append(",");
                }
            }
            System.out.println("Executing getListOfUnderlyings(). Retrieved NSE F&O Stock Codes. They are:"
                    + strBufStockCodes.toString());
        } catch (MalformedURLException mueExcep) {
            strBufParam.delete(0, strBufParam.length());
            strBufParam
                    .append("Given URL seems to be incorrect. \n Please check the URL and try again. \n");
            strBufParam.append(mueExcep.getLocalizedMessage());
            System.out.println(strBufParam.toString());

        } catch (IOException ioExcep) {
            System.out.println("An error occurred: "
                    + ioExcep.getLocalizedMessage());
        } catch (Exception excep) {
            excep.printStackTrace();
        } finally {

        }
        return strBufStockCodes.toString();
    }

    // VIX is volatility Index
    public String archiveIndiaVIX() {
        StringBuffer strBufStockQuote = null;
        Calendar currDateTime = Calendar.getInstance();
        currDateTime.set(
                Calendar.DAY_OF_MONTH,
                currDateTime.get(Calendar.DAY_OF_MONTH)
                        - m_objUtil.getHistDataDurationValue());
        SimpleDateFormat objSDF = new SimpleDateFormat(
                Constants.STR_VIX_DATE_FORMAT);
        String strStartDate = objSDF.format(currDateTime.getTime());

        currDateTime = Calendar.getInstance();
        objSDF = new SimpleDateFormat(Constants.STR_VIX_DATE_FORMAT);
        currDateTime.set(Calendar.DAY_OF_MONTH,
                currDateTime.get(Calendar.DAY_OF_MONTH));
        // String strEndDate = objSDF.format(currDateTime.getTime());

        URL url = null;
        BufferedReader brContent = null;
        try {
            String strURL = String.format(Constants.STR_NSE_INDIA_VIX_URL,
                    strStartDate, strStartDate);
            url = new URL(strURL);

            URLConnection urlConn = url.openConnection();
            urlConn.connect();
            brContent = new BufferedReader(new InputStreamReader(
                    urlConn.getInputStream()));

            strBufStockQuote = new StringBuffer();
            String strLine = null;
            while ((strLine = brContent.readLine()) != null) {
                strLine = strLine.trim();
                if (strLine != null && !strLine.isEmpty()) {
                    strBufStockQuote.append(strLine).append(
                            System.getProperty("line.separator"));
                }
                strLine = null;
            }
            writeStockQuote(Constants.STR_INDIA_VIX,
                    strBufStockQuote.toString());
        } catch (MalformedURLException mueExcep) {
            StringBuffer strBufParam = new StringBuffer(
                    "Given URL seems to be incorrect. \n Please check the URL and try again. \n");
            strBufParam.append(mueExcep.getLocalizedMessage());
            System.out.println(strBufParam.toString());
        } catch (IOException ioExcep) {
            System.out
                    .println("An I/O error occurred: " + ioExcep.getMessage());
        } catch (Exception excep) {
            System.out.println("An Exception occurred: " + excep.getMessage());
        } finally {
            url = null;
            try {
                if (brContent != null) {
                    brContent.close();
                }
            } catch (IOException ioeExcep) {

            }
            brContent = null;
        }
        if (strBufStockQuote != null) {
            return strBufStockQuote.toString();
        } else {
            return null;
        }
    }

    public static void generateSchemaFile() {
        FileWriter fwObj = null;
        try {
            if (m_objUtil.getGenSchemaFile() == 1) {
                String strCRLF = System.getProperty("line.separator");

                StringBuffer strBufSchemaInfo = new StringBuffer();
                // String strDatabaseName = m_objUtil.getDatabaseName();
                String strSchemaTemplate = m_objUtil.getSchemaTemplate();
                String strDatabaseName = m_objUtil.getDatabaseName();
                StringBuffer strBufFileName = new StringBuffer(
                        m_objUtil.createStockQuoteDir());
                strBufFileName.append(Util.m_strFileSeparator);
                strBufFileName.append(m_objUtil.getDataSourceSettings())
                        .append(Constants.STR_SCHEMA_FILE_EXT);
                for (int nIdx = 0; nIdx < m_objUtil.getStockCodeValue().length; nIdx++) {
                    String strTableName = m_objUtil.getStockCodeValue()[nIdx]
                            .trim();
                    String strCommandCreateTable = String.format(
                            strSchemaTemplate, strDatabaseName, strTableName,
                            strCRLF, strDatabaseName, strTableName, strCRLF,
                            strCRLF, strCRLF, strCRLF, strCRLF, strCRLF,
                            strCRLF, strCRLF, strCRLF, strCRLF, strCRLF);
                    strBufSchemaInfo.append(strCommandCreateTable)
                            .append(strCRLF).append(strCRLF);
                }
                File fProgOutput = new File(strBufFileName.toString());
                fwObj = new FileWriter(fProgOutput);
                fwObj.write(strBufSchemaInfo.toString());
            }
        } catch (IllegalFormatException ifeExcep) {
            ifeExcep.printStackTrace();
        } catch (NullPointerException npeExcep) {
            npeExcep.printStackTrace();
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } catch (SecurityException seExcep) {
            seExcep.printStackTrace();
        } finally {
            try {
                if (fwObj != null) {
                    fwObj.flush();
                    fwObj.close();
                }
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace();
            }
        }

    }

    public static void generateHibernateMapping(boolean bDynamicModel) {
        generateMasterHibernateMapping();
        if (m_objUtil.getGenHibernateMapping() == 1) {
            String strCRLF = System.getProperty("line.separator");
            final String strHibernateMappingTemplate = m_objUtil
                    .getHibernateMappingTemplate();
            final String strDirName = m_objUtil.createHibernateMappingDir();
            for (int nIdx = 0; nIdx < m_objUtil.getStockCodeValue().length; nIdx++) {
                StringBuffer strBufHibernateMappingInfo = new StringBuffer();
                String strTableName = m_objUtil.getStockCodeValue()[nIdx]
                        .trim();
                FileWriter fwObj = null;
                try {
                    StringBuffer strBufFileName = new StringBuffer(strDirName);
                    strBufFileName.append(Util.m_strFileSeparator).append(
                            strTableName);
                    strBufFileName
                            .append(Constants.STR_HIBERNATE_MAPPING_FILE_EXT);

                    String strHibernateMappingXML = String.format(
                            strHibernateMappingTemplate, strCRLF, strCRLF,
                            strCRLF, strCRLF, strTableName, strTableName,
                            strTableName, strCRLF, strCRLF, strCRLF, strCRLF,
                            strCRLF, strCRLF, strCRLF, strCRLF, strCRLF,
                            strCRLF, strCRLF, strCRLF, strCRLF);
                    strBufHibernateMappingInfo.append(strHibernateMappingXML)
                            .append(strCRLF).append(strCRLF);

                    File fProgOutput = new File(strBufFileName.toString());
                    fwObj = new FileWriter(fProgOutput);
                    fwObj.write(strBufHibernateMappingInfo.toString());
                } catch (IOException ioExcep) {
                    ioExcep.printStackTrace();
                } catch (IllegalFormatException ifeExcep) {
                    ifeExcep.printStackTrace();
                } catch (NullPointerException npeExcep) {
                    npeExcep.printStackTrace();
                } catch (SecurityException seExcep) {
                    seExcep.printStackTrace();
                } finally {
                    try {
                        if (fwObj != null) {
                            fwObj.flush();
                            fwObj.close();
                        }
                    } catch (IOException ioExcep) {
                        ioExcep.printStackTrace();
                    }
                }
            }
        }
    }

    public static void generateMasterHibernateMapping() {
        if (m_objUtil.getGenHibernateMapping() == 1) {
            File fMasterConfig = null;
            FileWriter fwMasterConfig = null;
            try {
                String strDatabaseName = m_objUtil.getDatabaseName();
                // File name with Absolute Path
                final String strDirName = m_objUtil.createHibernateMappingDir();
                StringBuffer strBufFileName = new StringBuffer(strDirName);
                strBufFileName.append(Util.m_strFileSeparator).append(
                        strDatabaseName);
                strBufFileName.append(Constants.STR_HIBERNATE_MAPPING_FILE_EXT);
                // Open File to write stuff
                fMasterConfig = new File(strBufFileName.toString());
                fwMasterConfig = new FileWriter(fMasterConfig);

                String strCRLF = System.getProperty("line.separator");
                final String strXMLHeaderTemplate = m_objUtil
                        .getXMLHeaderTemplate();
                final String strClassEntityTemplate = m_objUtil
                        .getClassEntityTemplate();
                String strText = String.format(strXMLHeaderTemplate, strCRLF,
                        strCRLF, strCRLF, strCRLF);
                StringBuffer strBufFileContent = new StringBuffer(strText);
                strBufFileContent.append(strCRLF);

                for (int nIdx = 0; nIdx < m_objUtil.getStockCodeValue().length; nIdx++) {
                    String strTableName = m_objUtil.getStockCodeValue()[nIdx]
                            .trim();
                    try {
                        String strHibernateMappingXML = String.format(
                                strClassEntityTemplate, strTableName,
                                strTableName, strCRLF, strCRLF, strCRLF,
                                strCRLF, strCRLF, strCRLF, strCRLF, strCRLF,
                                strCRLF, strCRLF, strCRLF, strCRLF, strCRLF);

                        strBufFileContent.append(strCRLF)
                                .append(strHibernateMappingXML).append(strCRLF);
                    } catch (IllegalFormatException ifeExcep) {
                        ifeExcep.printStackTrace();
                    } catch (NullPointerException npeExcep) {
                        npeExcep.printStackTrace();
                    } catch (SecurityException seExcep) {
                        seExcep.printStackTrace();
                    }
                }
                strBufFileContent.append("</hibernate-mapping>");
                fwMasterConfig.write(strBufFileContent.toString());
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace();
            } catch (IllegalFormatException ifeExcep) {
                ifeExcep.printStackTrace();
            } finally {

                try {
                    if (fwMasterConfig != null) {
                        fwMasterConfig.flush();
                        fwMasterConfig.close();
                    }
                } catch (IOException ioExcep) {
                    ioExcep.printStackTrace();
                }
            }
        }
    }
}
