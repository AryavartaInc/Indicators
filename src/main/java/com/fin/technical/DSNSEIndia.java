/**
 *
 */
package com.fin.technical;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.*;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.transform.Transformers;

import com.fin.persistence.*;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public class DSNSEIndia extends DataSource {
    private static String m_strStartDate = null;

    private static String m_strEndDate = null;

    static {
        Calendar currDateTime = Calendar.getInstance();
        currDateTime.set(
                Calendar.DAY_OF_MONTH,
                currDateTime.get(Calendar.DAY_OF_MONTH)
                        - m_objUtil.getHistDataDurationValue());
        SimpleDateFormat objSDF = new SimpleDateFormat(
                Constants.STR_DATE_FORMAT);
        m_strStartDate = objSDF.format(currDateTime.getTime());

        currDateTime = Calendar.getInstance();
        objSDF = new SimpleDateFormat(Constants.STR_DATE_FORMAT);
        currDateTime.set(Calendar.DAY_OF_MONTH,
                currDateTime.get(Calendar.DAY_OF_MONTH) - 1);
        m_strEndDate = objSDF.format(currDateTime.getTime());
    }

    /**
	 *
	 */
    protected DSNSEIndia() {

    }

    // Let us build URL like following:
    // http://www.nseindia.com/content/historical/EQUITIES/2011/JUN/cm24JUN2011bhav.csv.zip
    // Security wise price volume data
    // http://www.nseindia.com/content/equities/scripvol/datafiles/28-09-2010-TO-27-09-2011RELIANCEEQN.csv

    // Security-wise Deliverable Positions Data
    // http://www.nseindia.com/content/equities/scripvol/datafiles/28-09-2010-TO-27-09-2011RELIANCEEQN.csv

    // Security-wise Price volume & Deliverable position data
    // http://www.nseindia.com/content/equities/scripvol/datafiles/28-09-2010-TO-27-09-2011RELIANCEEQN.csv

    public Vector<StockPrice> getCurrentPriceOfStockOrIndex(String strCode) {
        return m_lstHistoricalPrice;
    }

    // Let us build URL like following:
    // http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=PFC&fromDate=27-Nov-2010&toDate=26-Nov-2011&datePeriod=unselected&hiddDwnld=true
    public Vector<StockPrice> getHistoricalPriceOfStockOrIndex(String strCode,
            int nPeriod) {
        URL url = null;
        BufferedReader brContent = null;
        try {
            String strEncodedStockCode = URLEncoder.encode(strCode,
                    Constants.STR_UTF8_CHARACTER_ENCODING);
            String strURL = String.format(Constants.STR_NSE_INDIA_URL,
                    strEncodedStockCode, m_strStartDate, m_strEndDate);
            System.out.println(strURL);
            // http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=PFC&fromDate=27-Nov-2010&toDate=26-Nov-2011&datePeriod=unselected&hiddDwnld=true
            // to be used to format String. %s will be replaced by meaningful
            // values.
            // http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=%s&fromDate=%s&toDate=%s&datePeriod=unselected&hiddDwnld=true
            url = new URL(strURL);
            URLConnection urlConn = url.openConnection();
            urlConn.connect();
            brContent = new BufferedReader(new InputStreamReader(
                    urlConn.getInputStream()));

            String strLine = null;
            while ((strLine = brContent.readLine()) != null) {
                strLine = strLine.trim();
                if (strLine != null && !strLine.isEmpty()) {
                    try {
                        String[] strValues = strLine
                                .split(Constants.STR_SPILT_ON_COMMA);// the
                                                                     // delimiter
                                                                     // or regex
                                                                     // for
                                                                     // splitting
                                                                     // is ","
                        NSEIndiaStockPrice objStockPrice = new NSEIndiaStockPrice(strValues, 1);
                        m_lstHistoricalPrice.addElement(objStockPrice);
                    } catch (NumberFormatException nfeExcep) {
                        // It may happen that strLine will contain following
                        // String:
                        // Date, Open Price, High Price, Low Price, Last Traded
                        // Price , Close Price, Total Traded Quantity, Turnover
                        // (in Lakhs)
                        // When this string is parsed based on "," delimiter,
                        // and attempted to create StockPrice Object
                        // It will throw "NumberFormatException". Nothing to
                        // worry about. Hence voiding the Exception.
                    } catch (Exception excep) {
                        excep.printStackTrace(System.out);
                    }
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
            try {
                if (brContent != null) {
                    brContent.close();
                }
            } catch (IOException ioeExcep) {

            }
            brContent = null;
        }
        return m_lstHistoricalPrice;
    }

    // SELECT * FROM stockquote.aban a order by TradeDate DESC;
    public void archiveStockQuote(String strCode) {
        URL urlObj = null;
        String strLine = null;
        BufferedReader brContent = null;
        String strStartDate = m_strStartDate;
        String strEndDate = m_strEndDate;
        try {
            Date endDate = null;
            if (m_objUtil.getSaveIntoDatabase() == 1) {
                endDate = readStockQuote(strCode);
            }
            if(endDate != null) {

                endDate.setTime(endDate.getTime() + (24* 60 * 60 * 1000));

                SimpleDateFormat objSDF = new SimpleDateFormat(
                        Constants.STR_DATE_FORMAT);
                strEndDate = objSDF.format(endDate.getTime());
            }
            ///
            String strEncodedStockCode = URLEncoder.encode(strCode,
                    Constants.STR_UTF8_CHARACTER_ENCODING);
            //String strURL = String.format(Constants.STR_NSE_INDIA_URL, strEncodedStockCode, strStartDate, strEndDate);
            String strURL = String.format(Constants.STR_NSE_INDIA_URL, strEncodedStockCode);
            urlObj = new URL(strURL);

            // http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=PFC&fromDate=27-Nov-2010&toDate=26-Nov-2011&datePeriod=unselected&hiddDwnld=true
            // to be used to format String. %s will be replaced by meaningful
            // values.
            // http://www.nseindia.com/live_market/dynaContent/live_watch/get_quote/getHistoricalData.jsp?symbol=%s&fromDate=%s&toDate=%s&datePeriod=unselected&hiddDwnld=true
            brContent = new BufferedReader(new InputStreamReader(
                    urlObj.openStream()));
            StringBuffer strBufStockQuote = new StringBuffer();
            int nLineNumber = 0;
            ArrayList<StockQuote> lstStockQUote = new ArrayList<StockQuote>();
            if(brContent != null)
            {
                while ((strLine = brContent.readLine()) != null) {
                    try {
                        strLine = strLine.trim();
                        if (strLine != null && !strLine.isEmpty()) {
                            nLineNumber++;
                            strBufStockQuote.append(strLine).append(
                                    System.getProperty("line.separator"));

                            if (m_objUtil.getSaveIntoDatabase() == 1) {
                                String[] strValues = strLine
                                        .split(Constants.STR_SPILT_ON_COMMA);

                                String strFullyQualifiedClassName = String.format(
                                        Constants.STR_COM_FIN_PRESISTENCE_CLASS,
                                        strCode);
                                Class cStockQuote = Class
                                        .forName(strFullyQualifiedClassName);
                                StockQuote objStockQuote = (com.fin.persistence.StockQuote) cStockQuote
                                        .newInstance();

                                SimpleDateFormat sdfDateFormat = new SimpleDateFormat(
                                        Constants.STR_DATE_FORMAT_PATTERN);
                                Date TradeDate = sdfDateFormat.parse(
                                        strValues[0].trim(), new ParsePosition(0));
                                objStockQuote.setId(System.nanoTime());
                                objStockQuote.setTradeDate(TradeDate);
                                objStockQuote.setOpenPrice(new BigDecimal(
                                        strValues[1].trim()).doubleValue());
                                objStockQuote.setHighPrice(new BigDecimal(
                                        strValues[2].trim()).doubleValue());
                                objStockQuote.setLowPrice(new BigDecimal(
                                        strValues[3].trim()).doubleValue());
                                objStockQuote.setLastTradedPrice(new BigDecimal(
                                        strValues[4].trim()).doubleValue());
                                objStockQuote.setClosePrice(new BigDecimal(
                                        strValues[5].trim()).doubleValue());
                                objStockQuote
                                        .setTotalTradedQuantity(new BigDecimal(
                                                strValues[6].trim()).intValue());
                                objStockQuote.setTurnover(new BigDecimal(
                                        strValues[7].trim()).doubleValue());

                                lstStockQUote.add(objStockQuote);
                                // non-batch insert to database.
                                // saveStockQuote(strCode, strValues); //Dynamic
                                // Model
                                // saveStockQuote(strCode, objStockQuote); //Pojo
                                // Model
                                if (nLineNumber % 40 == 0) {
                                    // Batch insert to reduce database round trip.
                                    saveStockQuote(strCode, lstStockQUote);
                                    lstStockQUote.clear();
                                    nLineNumber = 0;
                                }
                            }
                        }
                        strLine = null;
                    } catch (NumberFormatException nfeExcep) {

                    } catch (Throwable objThrowable) {
                        objThrowable.printStackTrace(System.out);
                    }
                }
            }
            // the last batch, where the number of records are not exactly the
            // same as desired.
            if (nLineNumber > 0 && m_objUtil.getSaveIntoDatabase() == 1) {
                // Batch insert to reduce database round trip.
                saveStockQuote(strCode, lstStockQUote);
                lstStockQUote.clear();
                nLineNumber = 0;
            }
            writeStockQuote(strCode, strBufStockQuote.toString());
        } catch (MalformedURLException mueExcep) {
            mueExcep.printStackTrace(System.out);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace(System.out);
        } catch (Exception excep) {
            excep.printStackTrace(System.out);
        } finally {
            urlObj = null;
            try {
                if (brContent != null) {
                    brContent.close();
                }
            } catch (IOException ioeExcep) {
                ioeExcep.printStackTrace(System.out);
            }
            brContent = null;
        }
    }

    // https://forum.hibernate.org/viewtopic.php?f=1&t=954229
    // http://coderoony.blogspot.in/2008/12/mapping-class-multiple-times-in.html
    // https://hibernate.onjira.com/browse/HHH-4967

    //
    // Hibernate Dynamic Model non-batch Insert
    //
    @SuppressWarnings("unchecked")
    public void saveStockQuote(String strCode, String[] strValues) {
        if (m_objUtil.getSaveIntoDatabase() == 1) {
            return;
        }
        Session objHibernateSession = HibernateUtil.getSessionFactory()
                .openSession();
        Transaction objTrans = objHibernateSession.beginTransaction();

        try {
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
                    new BigDecimal(strValues[5].trim()).doubleValue());
            collObjStockQuote.put("TotalTradedQuantity", new BigDecimal(
                    strValues[6].trim()).intValue());
            collObjStockQuote.put("Turnover",
                    new BigDecimal(strValues[7].trim()).doubleValue());

            objHibernateSession.save(strCode, collObjStockQuote);
            objTrans.commit();
        } catch (RuntimeException rteExcep) {
            objTrans.rollback();
            rteExcep.printStackTrace(System.out);
        } finally {

        }
    }

    //
    // Hibernate Pojo Model non-batch Insert
    //
    public void saveStockQuote(String strCode, StockQuote objStockQuote) {
        Session objHibernateSession = HibernateUtil.getSessionFactory()
                .openSession();
        Transaction objTrans = objHibernateSession.beginTransaction();
        // http://docs.oracle.com/javase/tutorial/java/javaOO/objectcreation.html
        // http://crashingnhanging.blogspot.in/2009/07/using-dynamic-model-in-hibernate.html
        try {
            objHibernateSession.save(objStockQuote);
            objTrans.commit();
        } catch (Exception excep) {
            objTrans.rollback();
            excep.printStackTrace(System.out);
        } finally {

        }
    }

    //
    // Hibernate Pojo batch Insert
    //
    public void saveStockQuote(String strCode,
            ArrayList<StockQuote> lstStockQuote) {
        Session objHibernateSession = null;
        try {
            int nSize = lstStockQuote.size();
            if (nSize == 0) {
                return;
            }
            objHibernateSession = HibernateUtil.getSessionFactory()
                    .openSession();
            Transaction objTrans = objHibernateSession.beginTransaction();
            for (int nIdx = 0; nIdx < nSize; nIdx++) {
                StockQuote objStockQuote = lstStockQuote.get(nIdx);
                objHibernateSession.save(strCode, objStockQuote);
            }
            objHibernateSession.flush();
            objHibernateSession.clear();
            objTrans.commit();
        } catch (RuntimeException rteExcep) {
            objHibernateSession.getTransaction().rollback();
            rteExcep.printStackTrace(System.out);
        }
    }

    //
    // Hibernate Pojo Model non-batch retrieval
    //
    // SELECT TradeDate FROM stockquote.%s a order by TradeDate DESC;
    public Date readStockQuote(String strCode) {
        StockQuote objStockQuote = null;
        List lstStock = null;
        if (m_objUtil.getSaveIntoDatabase() == 1) {
            return null;
        }
        Session objHibernateSession = HibernateUtil.getSessionFactory()
                .openSession();
//        Transaction objTrans = objHibernateSession.beginTransaction();

        try {
            String strQuery = String.format(Constants.STR_MYSQL_SELECT_TRADEDATE, strCode);
            lstStock = objHibernateSession.createSQLQuery(strQuery).setResultTransformer(Transformers.aliasToBean(com.fin.persistence.StockQuote.class)).list();
            if(lstStock.size() > 0) {
                objStockQuote = (com.fin.persistence.StockQuote)lstStock.get(0);
            }
        } catch (RuntimeException rteExcep) {
            rteExcep.printStackTrace(System.out);
        } finally {

        }
        if(objStockQuote != null) {
            return objStockQuote.getTradeDate();
        }
        return null;
    }

    public Vector<StockPrice> getHistoricalPrice(String strCode) {
        FileReader objFileReader = null;
        BufferedReader brContent = null;
        String strStockQuoteDir = Util.getStockQuotePath();

        StringBuffer strBufStockQuoteFile = new StringBuffer(strStockQuoteDir)
                .append(Util.m_strFileSeparator);
        strBufStockQuoteFile.append(strCode).append(
                Constants.STR_STOCK_QUOTE_FILE_TYPE_CSV);
        try {
            String strLine = null;
            objFileReader = new FileReader(strBufStockQuoteFile.toString());
            brContent = new BufferedReader(objFileReader);
            // This is to discard header, which doesn't contain StockQuote
            strLine = brContent.readLine();
            while ((strLine = brContent.readLine()) != null
                    && !strLine.isEmpty()) {
                strLine = strLine.trim();
                // the delimiter or regex for splitting is ","
                try {
                    String[] strValues = strLine
                            .split(Constants.STR_SPILT_ON_COMMA);
                    NSEIndiaStockPrice objStockPrice = new NSEIndiaStockPrice(strValues, 1);
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
        return m_lstHistoricalPrice;
    }
}