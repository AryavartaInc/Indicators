// A working URL that provides data in JSON format.
// http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote?format=json

package com.fin.technical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.hibernate.Session;

import com.fin.persistence.HibernateUtil;
import com.fin.persistence.StockQuote;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public class DSYahooFinance extends DataSource {
    private static StringBuffer m_strBufEndDateParam = null;

    private static StringBuffer m_strBufBeginDateParam = null;

    static {
        Calendar currDateTime = Calendar.getInstance();
        m_strBufEndDateParam = new StringBuffer();
        m_strBufEndDateParam.append("&d=").append(
                currDateTime.get(Calendar.MONTH));
        m_strBufEndDateParam.append("&e=").append(
                currDateTime.get(Calendar.DAY_OF_MONTH));
        m_strBufEndDateParam.append("&f=").append(
                currDateTime.get(Calendar.YEAR));

        currDateTime.set(
                Calendar.DAY_OF_MONTH,
                currDateTime.get(Calendar.DAY_OF_MONTH)
                        - m_objUtil.getHistDataDurationValue());
        // System.out.println("Start Date and time is: " +
        // currDateTime.getTime().toString());
        m_strBufBeginDateParam = new StringBuffer();
        m_strBufBeginDateParam.append("&a=").append(
                currDateTime.get(Calendar.MONTH));
        m_strBufBeginDateParam.append("&b=").append(
                currDateTime.get(Calendar.DAY_OF_MONTH));
        m_strBufBeginDateParam.append("&c=").append(
                currDateTime.get(Calendar.YEAR));
    }

    protected DSYahooFinance() {

    }

    // Let us build URL like following:
    // http://www.nseindia.com/content/historical/EQUITIES/2011/JUN/cm24JUN2011bhav.csv.zip
    // Security wise price volume data
    // http://www.nseindia.com/content/equities/scripvol/datafiles/28-09-2010-TO-27-09-2011RELIANCEEQN.csv

    // Security-wise Deliverable Positions Data
    // http://www.nseindia.com/content/equities/scripvol/datafiles/28-09-2010-TO-27-09-2011RELIANCEEQN.csv

    // Security-wise Price volume & Deliverable position data
    // http://www.nseindia.com/content/equities/scripvol/datafiles/28-09-2010-TO-27-09-2011RELIANCEEQN.csv
    // from Google finance
    // http://www.google.com/finance/historical?cid=207437&startdate=May+14+2011&enddate=Jun+14+2011&num=30

    // for Current Day's NIFTY price
    // http://finance.yahoo.com/d/quotes.csv?s=^NSEI&f=d1ohgl1vl1
    public Vector<StockPrice> getCurrentPriceOfStockOrIndex(String strCode) {
        if (m_lstHistoricalPrice == null) {
            return m_lstHistoricalPrice;
        }
        URL url = null;
        BufferedReader brContent = null;
        StringBuffer strBufParam = null;
        try {
            // Source: http://cliffngan.net/a/13
            // The complete URL is:
            // "http://finance.yahoo.com/d/quotes.csv?s=RELIANCE.NS&f=d1ohgl1vl1"
            // d1 : Last Trade Date
            // o : Open Price
            // h : Day’s High Price
            // g : Day’s Low
            // l1 : Last Trade (Price Only)
            // v : Volume
            // l1 : Last Trade (Price Only)
            String strEncodedStockCode = URLEncoder.encode(strCode,
                    Constants.STR_UTF8_CHARACTER_ENCODING);
            strBufParam = new StringBuffer(
                    "http://finance.yahoo.com/d/quotes.csv?s=");
            strBufParam.append(strEncodedStockCode).append("&f=d1ohgl1vl1");
            System.out.println(strBufParam.toString());
            url = new URL(strBufParam.toString());
            brContent = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            String strLine = null;
            while ((strLine = brContent.readLine()) != null
                    && !strLine.isEmpty()) {
                // the delimiter or regex for splitting is ","
                String[] strValues = strLine
                        .split(Constants.STR_SPILT_ON_COMMA);
                // To convert Date format from MM/dd/yyyy to yyyy-MM-dd
                strValues[0] = strValues[0].trim().substring(1,
                        strValues[0].length() - 1);
                try {
                    String[] strDateValues = strValues[0].split("/");
                    strValues[0] = String.format(
                            Constants.STR_ISO_DATE_FORMAT_FROM_STRING,
                            strDateValues[2], strDateValues[0],
                            strDateValues[1]);
                } catch (Exception excep) {
                    System.out.println(excep.getLocalizedMessage());
                }
                YahooFinanceStockPrice objStockPrice = new YahooFinanceStockPrice(strValues, 0);
                m_lstHistoricalPrice.add(0, objStockPrice);
                strValues = null;
                strLine = null;
            }
        } catch (MalformedURLException mueExcep) {
            strBufParam.delete(0, strBufParam.length());
            mueExcep.printStackTrace(System.out);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace(System.out);
        } catch (Exception excep) {
            excep.printStackTrace(System.out);
        } finally {
            url = null;
            brContent = null;
            strBufParam = null;
        }
        return m_lstHistoricalPrice;
    }

    // retrieve Stock quote for multiple Stocks
    public Vector<StockPrice> getCurrentPriceOfStockOrIndex(
            String[] strArrayStockCode) {
        URL url = null;
        BufferedReader brContent = null;
        StringBuffer strBufParam = new StringBuffer(
                "http://finance.yahoo.com/d/quotes.csv?s=");
        try {
            StringBuffer strBufStockCode = new StringBuffer();
            int nLength = strArrayStockCode.length;
            // for (String strStockCode : strArrayStockCode)
            for (int nIdx = 0; nIdx < nLength; nIdx++) {
                strBufStockCode.append(strArrayStockCode[nIdx].trim());
                if (nIdx < nLength - 1) {
                    strBufStockCode.append("+");
                }
            }
            // Source: http://cliffngan.net/a/13 &
            // http://www.gummy-stuff.org/Yahoo-data.htm
            // The complete URL is:
            // "http://finance.yahoo.com/d/quotes.csv?s=RELIANCE.NS&f=d1ohgl1vl1"
            // d1 : Last Trade Date
            // d2 : Trade Date
            // o : Open Price
            // h : Day’s High Price
            // g : Day’s Low Price
            // l1 : Last Trade (Price Only)
            // v : Volume
            // l1 : Last Trade (Price Only)
            strBufParam.append(strBufStockCode).append("&f=d1ohgl1vl1");

            url = new URL(strBufParam.toString());
            brContent = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            if (m_lstHistoricalPrice == null) {
                m_lstHistoricalPrice = new Vector<StockPrice>();
            }
            String strLine = null;
            while ((strLine = brContent.readLine()) != null
                    && !strLine.isEmpty()) {
                // the delimiter or regex for splitting is ","
                String[] strValues = strLine
                        .split(Constants.STR_SPILT_ON_COMMA);
                YahooFinanceStockPrice objStockPrice = new YahooFinanceStockPrice(strValues, 0);
                m_lstHistoricalPrice.add(0, objStockPrice);
                strLine = null;
                strValues = null;
            }
            strBufStockCode = null;
        } catch (MalformedURLException mueExcep) {
            mueExcep.printStackTrace(System.out);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace(System.out);
        } catch (Exception excep) {
            excep.printStackTrace(System.out);
        } finally {
            url = null;
            brContent = null;
            strBufParam = null;
        }
        return m_lstHistoricalPrice;
    }

    // Let us build URL like following:

    // from Yahoo finance
    // http://ichart.finance.yahoo.com/table.csv?s=^NSEI&a=4&b=15&c=2011&d=5&e=14&f=2011&g=d&ignore=.csv

    // In the above URL
    // Fixed URL is "http://ichart.finance.yahoo.com/table.csv?s=^NSEI"
    public Vector<StockPrice> getHistoricalPriceOfStockOrIndex(String strCode,
            int nPeriod) {
        URL url = null;
        BufferedReader brContent = null;
        StringBuffer strBufURL = null;
        try {
            String strEncodedStockCode = URLEncoder.encode(strCode,
                    Constants.STR_UTF8_CHARACTER_ENCODING);

            strBufURL = new StringBuffer(
                    "http://ichart.finance.yahoo.com/table.csv?s=").append(strEncodedStockCode);
            strBufURL.append(m_strBufBeginDateParam).append(m_strBufEndDateParam);
            strBufURL.append("&g=d").append("ignore=.csv");

            url = new URL(strBufURL.toString());
            brContent = new BufferedReader(new InputStreamReader(
                    url.openStream()));

            String strLine = null;
            while ((strLine = brContent.readLine()) != null
                    && !strLine.isEmpty()) {
                strLine = strLine.trim();
                // the delimiter or regex for splitting is ","
                try {
                    String[] strValues = strLine
                            .split(Constants.STR_SPILT_ON_COMMA);
                    YahooFinanceStockPrice objStockPrice = new YahooFinanceStockPrice(strValues, 0);
                    m_lstHistoricalPrice.addElement(objStockPrice);
                } catch (NumberFormatException nfeExcep) {
                    // It may happen that strLine will contain following String:
                    // Date, Open Price, High Price, Low Price, Last Traded
                    // Price , Close Price, Total Traded Quantity, Turnover (in
                    // Lakhs)
                    // When this string is parsed based on "," delimiter, and
                    // attempted to create StockPrice Object
                    // It will throw "NumberFormatException". Nothing to worry
                    // about. Hence voiding the Exception.
                    nfeExcep.printStackTrace(System.out);
                } catch (Exception excep) {
                    excep.printStackTrace(System.out);
                }
                strLine = null;
            }
        } catch (MalformedURLException mueExcep) {
            mueExcep.printStackTrace(System.out);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace(System.out);
        } catch (Exception excep) {
            excep.printStackTrace(System.out);
        } finally {
            url = null;
            brContent = null;
            strBufURL = null;
        }
        return m_lstHistoricalPrice;
    }

    public void archiveStockQuote(String strCode) {

        String strHistoricalURL = m_objUtil.getYahooFinanceHistoricalSQURL();
        StringBuffer strBufURL = new StringBuffer(String.format(strHistoricalURL,
                strCode, m_strBufBeginDateParam, m_strBufEndDateParam));
        URL url = null;
        BufferedReader brContent = null;
        try {
            String strCurrentPrice = null;
            int nDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            if (nDayOfWeek != Calendar.SATURDAY
                    && nDayOfWeek != Calendar.SUNDAY) {

                String strCSQURL = m_objUtil.getYahooFinanceCSQURL();
                // get current Stock Quote from yahoo finance
                String strEncodedStockCode = URLEncoder.encode(strCode,
                        Constants.STR_UTF8_CHARACTER_ENCODING);
                StringBuffer strBufParam = new StringBuffer(String.format(strCSQURL, strEncodedStockCode));

                url = new URL(strBufParam.toString());
                brContent = new BufferedReader(new InputStreamReader(
                        url.openStream()));
                strCurrentPrice = brContent.readLine();
            }
            url = new URL(strBufURL.toString());
            brContent = new BufferedReader(new InputStreamReader(
                    url.openStream()));
            String strHistoricalPrice = null;
            StringBuffer strBufStockQuote = new StringBuffer();
            while ((strHistoricalPrice = brContent.readLine()) != null
                    && !strHistoricalPrice.isEmpty()) {
                strHistoricalPrice = strHistoricalPrice.trim();
                strBufStockQuote.append(strHistoricalPrice).append(
                        System.getProperty("line.separator"));
                if(strCurrentPrice != null) {
                    strBufStockQuote.append(strCurrentPrice).append(
                            System.getProperty("line.separator"));
                    strCurrentPrice = null;
                }
                strHistoricalPrice = null;
            }
            writeStockQuote(strCode, strBufStockQuote.toString());
        } catch (MalformedURLException mueExcep) {
            mueExcep.printStackTrace(System.out);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace(System.out);
        } catch (Exception excep) {
            excep.printStackTrace(System.out);
        } finally {
            url = null;
            brContent = null;
            strBufURL = null;
        }
        return;
    }

    public Vector<StockPrice> getHistoricalPrice(String strCode) {
        FileReader objFileReader = null;
        BufferedReader brContent = null;
        String strStockQuoteDir = Util.getStockQuotePath();

        StringBuffer strBufStockQuoteFile = new StringBuffer(strStockQuoteDir)
                .append(Util.m_strFileSeparator);
        strBufStockQuoteFile.append(strCode).append(".csv");
        try {
            String strLine = null;
            objFileReader = new FileReader(strBufStockQuoteFile.toString());
            brContent = new BufferedReader(objFileReader);
            while ((strLine = brContent.readLine()) != null
                    && !strLine.isEmpty()) {
                strLine = strLine.trim();
                // the delimiter or regex for splitting is ","
                try {
                    String[] strValues = strLine
                            .split(Constants.STR_SPILT_ON_COMMA);
                    YahooFinanceStockPrice objStockPrice = new YahooFinanceStockPrice(strValues, 0);
                    m_lstHistoricalPrice.addElement(objStockPrice);
                } catch (NumberFormatException nfeExcep) {
                    // It may happen that strLine will contain following String:
                    // Date, Open Price, High Price, Low Price, Last Traded
                    // Price , Close Price, Total Traded Quantity, Turnover (in
                    // Lakhs)
                    // When this string is parsed based on "," delimiter, and
                    // attempted to create StockPrice Object
                    // It will throw "NumberFormatException". Nothing to worry
                    // about. Hence voiding the Exception.
                } catch (Exception excep) {

                }
                strLine = null;
            }
        } catch (MalformedURLException mueExcep) {
            mueExcep.printStackTrace(System.out);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace(System.out);
        } catch (Exception excep) {
            excep.printStackTrace(System.out);
        } finally {
            try {
                objFileReader.close();
            } catch (FileNotFoundException fnfeExcep) {
                fnfeExcep.printStackTrace(System.out);
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace(System.out);
            }
            brContent = null;
        }
        return m_lstHistoricalPrice;
    }

    // https://forum.hibernate.org/viewtopic.php?f=1&t=954229
    // http://coderoony.blogspot.in/2008/12/mapping-class-multiple-times-in.html
    // https://hibernate.onjira.com/browse/HHH-4967
    @SuppressWarnings("unchecked")
    public void saveStockQuote(String strCode, String[] strValues) {
        Session objHORMSession = HibernateUtil.getSessionFactory().openSession();
        try {
            objHORMSession.beginTransaction();
            Map collObjStockQuote = new HashMap();

            SimpleDateFormat sdfDateFormat = new SimpleDateFormat(
                    Constants.STR_DATE_FORMAT_PATTERN);
            Date TradeDate = sdfDateFormat.parse(strValues[0].trim(),
                    new ParsePosition(0));

            collObjStockQuote.put("TradeDate", TradeDate);
            collObjStockQuote.put("OpenPrice",
                    new BigDecimal(strValues[1].trim()).doubleValue());
            collObjStockQuote.put("HighPrice",
                    new BigDecimal(strValues[2].trim()).doubleValue());
            collObjStockQuote.put("LowPrice",
                    new BigDecimal(strValues[3].trim()).doubleValue());
            collObjStockQuote.put("LastTradedPrice", new BigDecimal(
                    strValues[4].trim()).doubleValue());
            collObjStockQuote.put("ClosePrice",
                    new BigDecimal(strValues[4].trim()).doubleValue());
            collObjStockQuote.put("TotalTradedQuantity", new BigDecimal(
                    strValues[5].trim()).intValue());
            collObjStockQuote.put("Turnover", 0.0);

            objHORMSession.save(strCode, collObjStockQuote);
            objHORMSession.getTransaction().commit();
        } catch (RuntimeException rteExcep) {
            objHORMSession.getTransaction().rollback();
            rteExcep.printStackTrace(System.out);
        }
        /*
         * String strFullyQualifiedClassName =
         * String.format(Constants.STR_COM_FIN_PRESISTENCE_CLASS, strCode); //
         * http
         * ://docs.oracle.com/javase/tutorial/java/javaOO/objectcreation.html //
         * http
         * ://crashingnhanging.blogspot.in/2009/07/using-dynamic-model-in-hibernate
         * .html try{ Class cStockQuote =
         * Class.forName(strFullyQualifiedClassName); StockQuote objStockQuote =
         * (com.fin.persistence.StockQuote)cStockQuote.newInstance();
         * objHORMSession.save(objStockQuote);
         * objHORMSession.getTransaction().commit(); }
         * catch(ClassNotFoundException cnfeExcep) {
         * cnfeExcep.printStackTrace(System.out); } catch(IllegalAccessException
         * iaeExcep) { iaeExcep.printStackTrace(System.out); }
         * catch(InstantiationException ieExcep) {
         * ieExcep.printStackTrace(System.out); }
         */
        /*
         * if(strCode.equalsIgnoreCase("ABAN")) { //Class.forName
         * ("com.fin.persistence.Driver").newInstance (); ABAN objStockQuote =
         * new ABAN(strValues, 1); objHORMSession.save(objStockQuote); }
         * else if(strCode.equalsIgnoreCase("ABB")) { ABB objStockQuote = new
         * ABB(strValues, 1); objHORMSession.save(objStockQuote); } else
         * if(strCode.equalsIgnoreCase("ABGSHIP")) { ABGSHIP objStockQuote = new
         * ABGSHIP(strValues, 1); objHORMSession.save(objStockQuote); }
         * else if(strCode.equalsIgnoreCase("ABIRLANUVO")) { ABIRLANUVO
         * objStockQuote = new ABIRLANUVO(strValues, 1);
         * objHORMSession.save(objStockQuote); } else
         * if(strCode.equalsIgnoreCase("ACC")) { ACC objStockQuote = new
         * ACC(strValues, 1); objHORMSession.save(objStockQuote); } else
         * if(strCode.equalsIgnoreCase("ADANIENT")) { ADANIENT objStockQuote =
         * new ADANIENT(strValues, 1); objHORMSession.save(objStockQuote);
         * }
         */
    }

    public void saveStockQuote(String strCode,
            ArrayList<StockQuote> lstStockQuote) {

    }
}
