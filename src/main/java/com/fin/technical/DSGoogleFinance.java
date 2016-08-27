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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import com.fin.persistence.StockQuote;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

//https://developers.google.com/apps-script/reference/finance/finance-app?hl=hi

//10 ways to download historical stock quotes data for free
//Read more: http://www.quantshare.com/sa-43-10-ways-to-download-historical-stock-quotes-data-for-free#ixzz2dHGd0Rck
//Follow us: @quantshare on Twitter
//http://www.quantshare.com/sa-43-10-ways-to-download-historical-stock-quotes-data-for-free


//from Google Finance Historical Stock Data Downloader by Alejandro Arrizabalaga
//gathers historical stock information from Google Finance.
//http://www.mathworks.com/matlabcentral/fileexchange/25433-google-finance-historical-stock-data-downloader/content/get_gf_histdata.m

//A working URL that provides data in XML format.
//http://www.google.com/ig/api?stock=MSFT [ Stock Quote ]
//http://www.google.com/ig/api?stock=DJI [ Index Quote ]
//https://www.google.com/ig/api?stock=GOOG&stock=AAPL   [ For Multiple Stocks ]
//http://www.google.com/ig/api?stock=AXISBANK [NSE] or http://www.google.com/ig/api?stock=500010 [BSE]

//https://www.google.com/finance/historical?output=csv&q=MSFT // works fine
//https://www.google.com/finance/historical?output=csv&q=BOM:500124 // Doesn't work

//http://www.jarloo.com/google-stock-api/

public class DSGoogleFinance extends DataSource {

    private static StringBuffer m_strBufEndDateParam = null;

    private static StringBuffer m_strBufBeginDateParam = null;

    private static String m_strParamURL = null;

    // http://www.google.com/finance/historical?q=MSFT&startdate=Jan+1+2013&enddate=Sep+5+2013&output=csv
    static {
        Calendar currDateTime = Calendar.getInstance();
        Calendar startDateTime = currDateTime;

        currDateTime.set(
                Calendar.DAY_OF_MONTH,
                currDateTime.get(Calendar.DAY_OF_MONTH)
                        - m_objUtil.getHistDataDurationValue());
        Calendar endDateTime = currDateTime;

        m_strParamURL = String.format(Util.getInstance().getGoogleFinanceParamURL(), startDateTime.get(Calendar.MONTH),
                startDateTime.get(Calendar.DAY_OF_MONTH), startDateTime.get(Calendar.YEAR),
                endDateTime.get(Calendar.MONTH), endDateTime.get(Calendar.DAY_OF_MONTH),
                endDateTime.get(Calendar.YEAR));
    }

    /**
	 *
	 */
    protected DSGoogleFinance() {

    }

    // http://www.nseindia.com/content/fo/fo_underlyinglist.htm
    public Vector<StockPrice> getCurrentPriceOfStockOrIndex(String strCode) {
        StringBuffer strBufParam = new StringBuffer();
        try {
            //http://www.google.com/ig/api?stock=MSFT
            strBufParam.append("http://www.google.com/ig/api?stock=").append(
                    strCode);
            System.out.print("URL is: ");
            System.out.println(strBufParam.toString());

            URL url = new URL(strBufParam.toString());
            URLConnection urlConn = url.openConnection();
            urlConn.connect();
            // process contents
            BufferedReader brContent = new BufferedReader(
                    new InputStreamReader(urlConn.getInputStream()));
            String strLine = null;
            StockPrice objStockIndexPrice = null;
            while ((strLine = brContent.readLine()) != null
                    && !strLine.isEmpty()) {
                if (strLine.contains("data-snapfield=")) {
                    System.out.println("Hurray:::::::::::::::::::::::::::::::"
                            + strLine);
                }
                System.out.println(strLine);
                String[] strValues = strLine
                        .split(Constants.STR_SPILT_ON_COMMA);// the
                                                             // delimiter
                                                             // or regex
                                                             // for
                                                             // splitting
                                                             // is ,
                objStockIndexPrice = new GoogleFinanceStockPrice(strValues, 0);
                m_lstHistoricalPrice.add(0, objStockIndexPrice);
            }
        } catch (MalformedURLException mueExcep) {
            mueExcep.printStackTrace(System.out);
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace(System.out);
        } catch (Exception excep) {
            excep.printStackTrace();
        } finally {

        }
        return m_lstHistoricalPrice;
    }

    // https://developers.google.com/apps-script/reference/finance/finance-app?hl=hi

    // Let us build URL like following:
    // from Google finance

    // http://www.google.com/finance/historical?cid=207437&startdate=Oct+10+2010&enddate=Nov+11+2011&num=200
    // http://www.google.com/finance/historical?cid=207437&startdate=Oct%2010%202010&enddate=Nov%2011%202011&num=200&start=200

    // http://www.google.com/finance/historical?q=BOM:500112&startdate=Oct+10+2010&enddate=Nov+11+2011&num=250
    // http://www.google.com/finance/historical?q=BOM:500112&startdate=Oct+10+2010&enddate=Nov+11+2011&num=250&start=200

    // http://www.google.com/finance/historical?q=NASDAQ:MSFT
    // http://www.google.com/finance/historical?q=NASDAQ:MSFT&output=csv
    public Vector<StockPrice> getHistoricalPriceOfStockOrIndex(String strCode,
            int nPeriod) {
        int nBatchSize = 200;
        int nStart = 0;
        int nDuration = Util.getInstance().getHistDataDurationValue();
        int nTime = nDuration / nBatchSize;
        int nRemainder = nDuration % nBatchSize;
        if (nRemainder > 0) {
            nTime += 1;
        }
        for (int nIdx = 0; nIdx < nTime; nIdx++) {
            StockPrice objStockIndexPrice = null;
            // StringBuffer strBufParam = new StringBuffer();
            Calendar currDateTime = Calendar.getInstance();
            Calendar pastDateTime = Calendar.getInstance();
            pastDateTime.set(Calendar.DAY_OF_MONTH,
                    currDateTime.get(Calendar.DAY_OF_MONTH) - nPeriod);

            StringBuffer strBufURL = new StringBuffer(
                    "http://www.google.com/finance/historical?q=")
                    .append(strCode);

            // System.out.println("End Date and time is: " +
            // currDateTime.getTime().toString());
            StringBuffer strBufEndDateParam = new StringBuffer(String.format(
                    Util.getInstance().getGoogleFinanceParamURL(),
                    currDateTime.get(Calendar.MONTH),
                    currDateTime.get(Calendar.DAY_OF_MONTH),
                    currDateTime.get(Calendar.YEAR),
                    pastDateTime.get(Calendar.MONTH),
                    pastDateTime.get(Calendar.DAY_OF_MONTH),
                    pastDateTime.get(Calendar.YEAR), nBatchSize, nStart));
            strBufURL.append(strBufEndDateParam);

            System.out.println(strBufURL.toString());
            try {
                URL url = new URL(strBufURL.toString());
                URLConnection urlConn = url.openConnection();
                urlConn.connect();
                // print contents
                BufferedReader brContent = new BufferedReader(
                        new InputStreamReader(urlConn.getInputStream()));
                String strLine = brContent.readLine().trim(); // this is to
                                                              // ignore first
                                                              // line of Input
                                                              // Stream, as it
                                                              // contains header
                if (strLine != null && !strLine.isEmpty()) {
                    while ((strLine = brContent.readLine().trim()) != null
                            && !strLine.isEmpty()) {
                        if (strLine.contains("id=prices")) {
                            while ((strLine = brContent.readLine().trim()) != null
                                    && !strLine.isEmpty()) {
                                if (strLine.contains("<td")) {
                                    // <tr class=bb>
                                    // <th class="bb lm">Date
                                    // <th class="rgt bb">Open
                                    // <th class="rgt bb">High
                                    // <th class="rgt bb">Low
                                    // <th class="rgt bb">Close
                                    // <th class="rgt bb rm">Volume
                                    // <tr>

                                    String strDate = strLine.substring(strLine
                                            .indexOf('>') + 1);

                                    strLine = brContent.readLine().trim();
                                    StringBuffer strBufPrice = new StringBuffer(
                                            strLine.substring(strLine
                                                    .indexOf('>') + 1));
                                    while (strBufPrice.toString().indexOf(
                                            Constants.STR_SPILT_ON_COMMA) != -1) {
                                        strBufPrice
                                                .deleteCharAt(strBufPrice
                                                        .indexOf(Constants.STR_SPILT_ON_COMMA));
                                    }
                                    double dblOpenPrice = (new BigDecimal(
                                            strBufPrice.toString()))
                                            .doubleValue();
                                    strBufPrice.delete(0, strBufPrice.length());
                                    strLine = brContent.readLine().trim();
                                    strBufPrice
                                            .append(strLine.substring(strLine
                                                    .indexOf('>') + 1));
                                    while (strBufPrice.toString().indexOf(
                                            Constants.STR_SPILT_ON_COMMA) != -1) {
                                        strBufPrice
                                                .deleteCharAt(strBufPrice
                                                        .indexOf(Constants.STR_SPILT_ON_COMMA));
                                    }
                                    double dblHighPrice = (new BigDecimal(
                                            strBufPrice.toString()))
                                            .doubleValue();
                                    // System.out.println("High Price     " +
                                    // dblHighPrice);

                                    strBufPrice.delete(0, strBufPrice.length());
                                    strLine = brContent.readLine().trim();
                                    strBufPrice
                                            .append(strLine.substring(strLine
                                                    .indexOf('>') + 1));
                                    while (strBufPrice.toString().indexOf(
                                            Constants.STR_SPILT_ON_COMMA) != -1) {
                                        strBufPrice
                                                .deleteCharAt(strBufPrice
                                                        .indexOf(Constants.STR_SPILT_ON_COMMA));
                                    }
                                    double dblLowPrice = (new BigDecimal(
                                            strBufPrice.toString()))
                                            .doubleValue();

                                    strBufPrice.delete(0, strBufPrice.length());
                                    strLine = brContent.readLine().trim();
                                    strBufPrice
                                            .append(strLine.substring(strLine
                                                    .indexOf('>') + 1));
                                    while (strBufPrice.toString().indexOf(
                                            Constants.STR_SPILT_ON_COMMA) != -1) {
                                        strBufPrice
                                                .deleteCharAt(strBufPrice
                                                        .indexOf(Constants.STR_SPILT_ON_COMMA));
                                    }
                                    double dblClosePrice = (new BigDecimal(
                                            strBufPrice.toString()))
                                            .doubleValue();

                                    strBufPrice.delete(0, strBufPrice.length());
                                    strLine = brContent.readLine().trim();
                                    strBufPrice
                                            .append(strLine.substring(strLine
                                                    .indexOf('>') + 1));
                                    while (strBufPrice.toString().indexOf(
                                            Constants.STR_SPILT_ON_COMMA) != -1) {
                                        strBufPrice
                                                .deleteCharAt(strBufPrice
                                                        .indexOf(Constants.STR_SPILT_ON_COMMA));
                                    }
                                    int nVolume = (new BigDecimal(
                                            strBufPrice.toString())).intValue();

                                    objStockIndexPrice = new StockPrice(
                                            strDate, dblOpenPrice,
                                            dblHighPrice, dblLowPrice,
                                            dblClosePrice, nVolume,
                                            dblClosePrice);
                                    m_lstHistoricalPrice
                                            .addElement(objStockIndexPrice);
                                }
                                if (strLine.contains("</table>")) {
                                    break;
                                }
                            }
                        } else {
                            strLine = null;
                        }
                    }
                } else {
                    System.out
                            .println("No historical data is found for stock: "
                                    + strCode);
                }
            } catch (MalformedURLException mueExcep) {
                mueExcep.printStackTrace(System.out);
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace(System.out);
            } catch (Exception excep) {
                excep.printStackTrace(System.out);
            }
            nStart = nStart + nBatchSize;
        }
        return m_lstHistoricalPrice;
    }

    public Vector<StockPrice> getHistoricalPrice(String strCode) {
        FileReader objFileReader = null;
        BufferedReader brContent = null;
        String strStockQuoteDir = Util.getStockQuotePath();

        //.append(Util.m_strFileSeparator)
        StringBuffer strBufStockQuoteFile = new StringBuffer(strStockQuoteDir).append(Util.m_strFileSeparator)
                .append(strCode)
                .append(Constants.STR_STOCK_QUOTE_FILE_TYPE_CSV);
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
                    GoogleFinanceStockPrice objStockPrice = new GoogleFinanceStockPrice(strValues, 0);
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

    // http://www.google.com/finance/historical?q=NASDAQ:GOOG&output=csv
    // http://www.google.com/finance/historical?cid=660479&startdate=Jan+1%2C+2013&enddate=Sep+5%2C+2013&num=30&ei=VHMoUpDHLMzMlAWIEw&output=csv

    // http://www.google.com/finance/historical?q=MSFT&startdate=Jan+1+2013&enddate=Sep+5+2013&output=csv
    public void archiveStockQuote(String strCode) {
        URL url = null;
        BufferedReader brContent = null;
        StringBuffer strBufURL = null;
        try {
            // get current Stock Quote from Google finance
            String strEncodedStockCode = URLEncoder.encode(strCode,
                    Constants.STR_UTF8_CHARACTER_ENCODING);

            strBufURL = new StringBuffer(String.format(Util.getInstance().getGoogleFinanceMainURL(), strEncodedStockCode));
            if(m_strParamURL != null) {
                strBufURL.append(m_strParamURL);
            }
            System.out.println(strBufURL.toString());
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

    public void saveStockQuote(String strCode, String[] strValues) {

    }

    public void saveStockQuote(String strCode,
            ArrayList<StockQuote> lstStockQuote) {

    }
}
