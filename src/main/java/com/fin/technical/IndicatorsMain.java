
package com.fin.technical;

import java.io.IOException;
import java.util.*;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.fin.persistence.HibernateUtil;

import jxl.write.*;
import jxl.write.biff.*;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */
// http://www.jguru.com/forums/view.jsp?EID=1364499
// http://www.marketoracle.co.uk/Article23727.html
// http://trading-stocks.netfirms.com/tradingsignals.htm

// TODO ::: Top Triangle The price seems to have reached a top,
// showing signs of reversal as it has broken downward after a period of
// uncertainty or consolidation. A Top Triangle shows two converging trendlines
// as prices reach lower highs and higher (or stable) lows. Volume diminishes
// as the price swings back and forth between an increasingly narrow range
// reflecting uncertainty in the market direction. Then well before the triangle
// reaches its apex, the price breaks down below the lower trendline with a
// noticeable increase in volume, confirming this bearish pattern as a reversal
// of the prior uptrend.
// TODO ::: Bottom Triangle
// TODO ::: Diamond Top
// TODO ::: Flag (Bearish)
// TODO ::: Continuation Wedge (Bearish)
// TODO ::: Symmetrical Continuation Triangle (Bearish)
// TODO ::: Megaphone Top
// TODO ::: Head and Shoulders Top
// TODO :::

public class IndicatorsMain {
    private Util m_objUtil = null;

    private ElapsedTime m_objElapsedTime = null;

    private int[] m_nEMADurationValue;

    private DataSource m_objDataSource = null;

    private static StringBuffer m_strBufLogOutput = new StringBuffer();
    
    private ApplicationContext m_objSpringAppContext = null;

    /**
	 *
	 */

    public IndicatorsMain() {
        m_nEMADurationValue = new int[2]; // m_nEMADurationValue
        m_nEMADurationValue[0] = 3;// default value
        m_nEMADurationValue[1] = 15;// default value
        if (m_objUtil == null) {
            m_objUtil = Util.getInstance();
        	//m_objUtil = (Util)m_objSpringAppContext.getBean("util");
        }

        //m_objSpringAppContext = new ClassPathXmlApplicationContext("Beans.xml");
        String strFileSeparator = System.getProperty(Constants.STR_FILE_SEPARATOR);
        StringBuffer strBufPath = m_objUtil.getParentOfWorkingDir();
        String strFIleSystemXMLAppCtx = String.format(Constants.STR_FILE_SYSTEM_XML_APPLICATION_CONTEXT, 
        		strFileSeparator, strFileSeparator, strFileSeparator, strFileSeparator, 
        		strFileSeparator, strFileSeparator, strFileSeparator);
        strBufPath.append(strFIleSystemXMLAppCtx);
        m_objSpringAppContext = new FileSystemXmlApplicationContext(strBufPath.toString());
        m_objUtil.setSpringFXApplicationContext(m_objSpringAppContext);

        if (m_objElapsedTime == null) {
            m_objElapsedTime = ElapsedTime.getInstance();
        }
        ElapsedTime.getInstance().BeginStopWatch();
        m_objUtil.readProgramInput();
        m_strBufLogOutput.append(m_objElapsedTime.checkStopWatch(
                "Reading program input", "from file 'ema.ini'"));
        // Data Source is determined after reading program input.
        if (m_objDataSource == null) {
            //m_objDataSource = DataSource.getInstance();
            m_objDataSource = (DataSource)m_objSpringAppContext.getBean("dataSource");
        }
        EMACalculator.initialize();
        if (m_objUtil.getSaveIntoDatabase() == 1) {
            m_objUtil.generateSchemaFile();
            m_objUtil.generatePojo();
            m_objUtil.generateHibernateMapping();
        }
        //ApplicationContext objSpringAppContext = new ClassPathXmlApplicationContext("Beans.xml");
        DataSource.getListOfUnderlyings();
        if (Util.getArchiveStockQuote().equalsIgnoreCase("1")) {        	
            StockQuoteArchiver objSQA = (StockQuoteArchiver)
            		this.m_objSpringAppContext.getBean("stockQuoteArchiver");
            objSQA.archiveStockQuote();
        }
    }

    public void initialize() {
        EMACalculator.initialize();

        Vector<StockPrice> lstHistoricalPrice = null;
        lstHistoricalPrice = m_objDataSource.getStockPriceList();

        if (lstHistoricalPrice != null && !lstHistoricalPrice.isEmpty()) {
            lstHistoricalPrice.removeAllElements();
        }

    }

    /**
     * @param args
     * @throws WriteException
     * @throws RowsExceededException
     * @throws IOException
     */
    public static void main(String[] args) throws RowsExceededException,
            WriteException, IOException, Exception {
        Calendar currDateTime = Calendar.getInstance();

        System.out.println("Current Date and time is: "
                + currDateTime.getTime().toString());
        m_strBufLogOutput.append(System.getProperty("line.separator"));
        m_strBufLogOutput.append("Current Date and time is: "
                + currDateTime.getTime().toString());
        m_strBufLogOutput.append(Util.checkMemoryStatus());
        IndicatorsMain objTechnicalIndicatorsMain = new IndicatorsMain();
        if (Util.getArchiveStockQuote().equalsIgnoreCase("1")) {
            while (StockQuoteArchiver.m_objTGSQAManager != null
                    && StockQuoteArchiver.m_objTGSQAManager.activeCount() >= 1) {
                Thread.sleep(5000);
                m_strBufLogOutput.append("Thread Group Name: "
                        + StockQuoteArchiver.m_objTGSQAManager.getName()
                        + ". Active number of threads in this group: "
                        + StockQuoteArchiver.m_objTGSQAManager.activeCount());
            }
        }
        // objTechnicalIndicatorsMain.m_objDataSource.archiveIndiaVIX();
        // ReportWriter objReports = ReportWriter.getInstance();
        ReportWriter objReports = (ReportWriter)objTechnicalIndicatorsMain.m_objSpringAppContext.getBean("reportWriter");
        int nArrIdx = 0;
        Vector<StockPrice> lstHistoricalPrice = null;
        // TechnicalIndicators objTI = new TechnicalIndicators();
        TechnicalIndicators objTI = (TechnicalIndicators)objTechnicalIndicatorsMain.m_objSpringAppContext.getBean("technicalIndicators");
        // Invoke Method to generate schema file.
        // Check whether user wants to generate schema file.
        while (objTechnicalIndicatorsMain.m_objUtil.getStockCodeValue() != null
                && nArrIdx < objTechnicalIndicatorsMain.m_objUtil.getStockCodeValue().length) {
            try {
                objTechnicalIndicatorsMain.initialize();
                String strStockCode = objTechnicalIndicatorsMain.m_objUtil.getStockCodeValue()[nArrIdx]
                        .trim();

                nArrIdx++;
                objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                // lstHistoricalPrice =
                // objTechnicalIndicatorsMain.m_objDataSource.getHistoricalPriceOfStockOrIndex(strStockCode,
                // objTechnicalIndicatorsMain.m_objUtil.getHistDataDurationValue());
                lstHistoricalPrice = objTechnicalIndicatorsMain.m_objDataSource
                        .getHistoricalPrice(strStockCode);

                m_strBufLogOutput.append(objTechnicalIndicatorsMain.m_objElapsedTime
                        .checkStopWatch("Historical price retrieval of",
                                strStockCode));

                if (objTechnicalIndicatorsMain.m_objUtil.getDataSourceSettings()
                        .equalsIgnoreCase(
                                Constants.STR_STOCK_CODE_DATA_SOURCE_YAHOO) == true
                        || objTechnicalIndicatorsMain.m_objUtil
                                .getDataSourceSettings()
                                .equalsIgnoreCase(
                                        Constants.STR_STOCK_CODE_DATA_SOURCE_GOOGLE) == true
                        || objTechnicalIndicatorsMain.m_objUtil
                                .getDataSourceSettings()
                                .equalsIgnoreCase(
                                        Constants.STR_STOCK_CODE_DATA_SOURCE_NSEINDIA) == true) {
                    // Trim the capacity of this vector to be the vector's
                    // current size.
                    lstHistoricalPrice.trimToSize();
                    if (lstHistoricalPrice != null) {
                        if (objTechnicalIndicatorsMain.m_objUtil.getGenSMA20DaysReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            objTI.calculateSMA(strStockCode,
                                    lstHistoricalPrice, 20);
                            if (Util.getInstance().getGenCCIReportValue() == 1) {
                                m_strBufLogOutput
                                        .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                                .checkStopWatch(
                                                        "20 Days SMA (Simple Moving Average) and CCI (Commodity Channel Index) Calculation of",
                                                        strStockCode));
                            } else {
                                m_strBufLogOutput
                                        .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                                .checkStopWatch(
                                                        "20 Days SMA (Simple Moving Average) Calculation of",
                                                        strStockCode));
                            }
                        }
                        if (objTechnicalIndicatorsMain.m_objUtil.getGenSMA50DaysReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            objTI.calculateSMA(strStockCode,
                                    lstHistoricalPrice, 50);
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "50 Days SMA (Simple Moving Average) Calculation of",
                                                    strStockCode));
                        }
                        if (objTechnicalIndicatorsMain.m_objUtil.getGenSMA200DaysReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            objTI.calculateSMA(strStockCode,
                                    lstHistoricalPrice, 200);
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "200 Days SMA (Simple Moving Average) Calculation of",
                                                    strStockCode));
                        }
                        // Begin EMA Calculation
                        objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                        for (int nEMACombo = 0; nEMACombo < objTechnicalIndicatorsMain.m_objUtil
                                .getEMADurationValue().length; nEMACombo++) {
                            String strEMACombo = objTechnicalIndicatorsMain.m_objUtil
                                    .getEMADurationValue()[nEMACombo].trim();
                            if (strEMACombo != null && strEMACombo.length() > 0) {
                                try {
                                    StringTokenizer strToken = new StringTokenizer(
                                            strEMACombo,
                                            Constants.STR_SPILT_ON_COMMA);
                                    int nIdx = 0;
                                    while (strToken.hasMoreTokens()) {
                                        String strTemp = strToken.nextToken()
                                                .trim();
                                        if (strTemp != null
                                                && !strTemp.isEmpty()) {
                                            objTechnicalIndicatorsMain.m_nEMADurationValue[nIdx++] = (new Integer(
                                                    strTemp)).intValue();
                                        }
                                    }
                                    EMACalculator.initialize();
                                    objTechnicalIndicatorsMain.m_objUtil
                                            .setEMADurationIntValue(objTechnicalIndicatorsMain.m_nEMADurationValue);
                                } catch (NumberFormatException nfeExcep) {
                                    nfeExcep.printStackTrace();
                                }
                                EMACalculator.calculateSMA(strStockCode,
                                        lstHistoricalPrice,
                                        objTechnicalIndicatorsMain.m_nEMADurationValue[0],
                                        strEMACombo);
                                EMACalculator.calculateSMA(strStockCode,
                                        lstHistoricalPrice,
                                        objTechnicalIndicatorsMain.m_nEMADurationValue[1],
                                        strEMACombo);
                                EMACalculator.calculateEMA(strStockCode,
                                        lstHistoricalPrice, strEMACombo);
                            }
                        }
                        m_strBufLogOutput
                                .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                        .checkStopWatch(
                                                "EMA (Exponential Moving Average) Calculation of",
                                                strStockCode));
                        // End EMA Calculation
                        if (objTechnicalIndicatorsMain.m_objUtil.getGenBollReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            objTI.calculateBollingerBand(strStockCode,
                                    lstHistoricalPrice, objTechnicalIndicatorsMain.m_objUtil
                                            .getBollingerDurationValue());
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "Bollinger Band calculation of",
                                                    strStockCode));
                        }
                        if (objTechnicalIndicatorsMain.m_objUtil.getGenRSIReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            RSICalculator.calculateRSI(lstHistoricalPrice);
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "RSI (Relative Strength Index) Calculation of",
                                                    strStockCode));
                        }
                        if (objTechnicalIndicatorsMain.m_objUtil.getGenADLReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            ADLCalculator.calculateADL(strStockCode,
                                    lstHistoricalPrice);
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "ADL (Accumulation Distribution Line) calculation of",
                                                    strStockCode));
                        }
                        if (objTechnicalIndicatorsMain.m_objUtil.getGenADXReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            objTI.calculateADX(strStockCode,
                                    lstHistoricalPrice,
                                    objTechnicalIndicatorsMain.m_objUtil.getADXDurationValue());
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "ADX (Average Directional Movement Index) calculation of",
                                                    strStockCode));
                        }

                        // http://stockcharts.com/school/doku.php?id=chart_school:trading_strategies:arthur_hill_on_movin
                        // A popular use for moving averages is to develop
                        // simple trading systems based on moving average
                        // crossovers.
                        // A trading system using two moving averages would give
                        // a buy signal
                        // when the shorter (faster) moving average advances
                        // above the longer (slower) moving average.
                        // A sell signal would be given when the shorter moving
                        // average crosses below the longer moving average.
                        StockPrice objCurrentDayStockPrice = (StockPrice) lstHistoricalPrice
                                .get(0);
                        StockPrice objPreviousDayStockPrice = (StockPrice) lstHistoricalPrice
                                .get(1);
                        if (objTechnicalIndicatorsMain.m_objUtil.getGenMasterEMAReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            EMACalculator.findEMACrossovers(strStockCode,
                                    lstHistoricalPrice, objTechnicalIndicatorsMain.m_objUtil
                                            .getPreferredEMAComboValue());
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "EMA Crossover determination of",
                                                    strStockCode));
                        }

                        // RSI based report. Stocks that are Overbought and
                        // Oversold on RSI.
                        if (objTechnicalIndicatorsMain.m_objUtil.getGenRSIReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            RSICalculator.findRSIObOs(objCurrentDayStockPrice,
                                    strStockCode);
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "Determination of Overbought and Oversold stocks by RSI for",
                                                    strStockCode));
                        }

                        if (objTechnicalIndicatorsMain.m_objUtil.getGenADXReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            objTI.findTrendingStockOnADX(
                                    objCurrentDayStockPrice,
                                    objPreviousDayStockPrice, strStockCode);
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "Determination of Trending/Non-Trending stock on ADX for",
                                                    strStockCode));
                        }
                        if (objTechnicalIndicatorsMain.m_objUtil.getGenROCReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            ROCCalculator.calculateROC(strStockCode,
                                    lstHistoricalPrice);
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "ROC (Rate Of Change) calculation of",
                                                    strStockCode));
                        }
                        if (objTechnicalIndicatorsMain.m_objUtil.getGenStockEMAReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            objReports.generateEMAReport(strStockCode,
                                    lstHistoricalPrice);
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "EMA Report generation of",
                                                    strStockCode));
                        }
                        // Calculate Pivot Points and Fibonacci Pivot Points.
                        if (objPreviousDayStockPrice != null
                                && objTechnicalIndicatorsMain.m_objUtil
                                        .getGenPivotPointReportValue() == 1) {
                            objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                            PivotPoint objPP = PivotPointCalculator
                                    .calculatePivotPoints(strStockCode,
                                            objPreviousDayStockPrice,
                                            objCurrentDayStockPrice);
                            PivotPointCalculator.m_objListPivotPoint.add(objPP);
                            PivotPoint objFibonacciPivotPoint = (PivotPoint) PivotPointCalculator
                                    .calculateFibonacciPivotPoints(
                                            strStockCode,
                                            objPreviousDayStockPrice,
                                            objCurrentDayStockPrice);
                            PivotPointCalculator.m_objListFibonacciPivotPoint
                                    .add(objFibonacciPivotPoint);
                            m_strBufLogOutput
                                    .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                            .checkStopWatch(
                                                    "Pivot point calculation of",
                                                    strStockCode));
                        }
                    }
                }
            } catch (Exception excep) {
                excep.printStackTrace();
            }
            m_strBufLogOutput.append(Util.checkMemoryStatus());
            System.out.println(m_strBufLogOutput.toString());
            m_strBufLogOutput.delete(0, m_strBufLogOutput.length());
        }
        if (objTechnicalIndicatorsMain.m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                Constants.STR_STOCK_CODE_DATA_SOURCE_YAHOO) == true
                || objTechnicalIndicatorsMain.m_objUtil.getDataSourceSettings()
                        .equalsIgnoreCase(
                                Constants.STR_STOCK_CODE_DATA_SOURCE_GOOGLE) == true
                || objTechnicalIndicatorsMain.m_objUtil.getDataSourceSettings()
                        .equalsIgnoreCase(
                                Constants.STR_STOCK_CODE_DATA_SOURCE_NSEINDIA) == true) {

            try {
                if (objTechnicalIndicatorsMain.m_objUtil.getGenMasterEMAReportValue() == 1) {
                    objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                    ReportWriter.getInstance().generateEMACrossoverReport();
                    m_strBufLogOutput.append(objTechnicalIndicatorsMain.m_objElapsedTime
                            .checkStopWatch("EMA Crossover report generation",
                                    "for the specified stocks"));
                }
                m_strBufLogOutput.append(Util.checkMemoryStatus());
                if (objTechnicalIndicatorsMain.m_objUtil.getGenPivotPointReportValue() == 1) {
                    objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                    objReports.generatePivotPointReport(
                            PivotPointCalculator.m_objListPivotPoint, false);
                    m_strBufLogOutput.append(objTechnicalIndicatorsMain.m_objElapsedTime
                            .checkStopWatch("Pivot point report generation",
                                    "for the specified stocks"));

                    objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                    objReports.generatePivotPointReport(
                            PivotPointCalculator.m_objListFibonacciPivotPoint,
                            true);
                    m_strBufLogOutput.append(objTechnicalIndicatorsMain.m_objElapsedTime
                            .checkStopWatch(
                                    "Fibonacci Pivot point report generation",
                                    "for the specified stocks"));
                }
                m_strBufLogOutput.append(Util.checkMemoryStatus());
                // RSI based report. Stocks that are Over Bought and Over Sold
                // on RSI.
                if (objTechnicalIndicatorsMain.m_objUtil.getGenRSIReportValue() == 1) {
                    objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                    ReportWriter.getInstance().generateRSIObOsReport();
                    m_strBufLogOutput
                            .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                    .checkStopWatch(
                                            "Report generation of Overbought and Oversold stocks by RSI",
                                            ""));
                }
                if (objTechnicalIndicatorsMain.m_objUtil.getGenBollReportValue() == 1) {
                    objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                    ReportWriter.getInstance().generateBollingerBandReport(
                            objTI);
                    m_strBufLogOutput.append(objTechnicalIndicatorsMain.m_objElapsedTime
                            .checkStopWatch(
                                    "Report generation of Bollinger Band", ""));
                }
                if (objTechnicalIndicatorsMain.m_objUtil.getGenADXReportValue() == 1) {
                    objTechnicalIndicatorsMain.m_objElapsedTime.BeginStopWatch();
                    ReportWriter.getInstance().generateADXReport(objTI);
                    m_strBufLogOutput
                            .append(objTechnicalIndicatorsMain.m_objElapsedTime
                                    .checkStopWatch(
                                            "Report generation of Average Directional Index",
                                            ""));
                }
                m_strBufLogOutput.append(Util.checkMemoryStatus());
                System.out
                        .println("Sending generated reports in e-mail. This may take several minutes. Please wait... ");
                TransportReport objTransportReport = new TransportReport();
                objTransportReport.sendMail();
                currDateTime = Calendar.getInstance();
                System.out
                        .println("Generated reports sent in e-mail. Current Date and time is: "
                                + currDateTime.getTime().toString());

                System.out.println();
                System.out.println(m_strBufLogOutput.toString());
                System.out.flush();
            } catch (Exception excep) {
                excep.printStackTrace();
                m_strBufLogOutput
                        .append("An error occurred in main method of IndicatorsMain: "
                                + excep.getMessage());
            }
        }
        if (objTechnicalIndicatorsMain.m_objUtil.getSaveIntoDatabase() == 1) {
            HibernateUtil.getSessionFactory().close();
        }
        System.exit(0); // calling the method is a must
    }
}
