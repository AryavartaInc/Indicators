package com.fin.technical;

import java.io.IOException;
import java.util.*;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */

public class TechnicalIndicators extends Thread {

    public Vector<Double> m_objListTypicalPrice = null;

    public Vector<Double> m_objListStandardDeviation = null;

    public ArrayList<StockPriceInfo> m_objListBollingerBand = new ArrayList<StockPriceInfo>();

    public ArrayList<StockPriceInfo> m_objListADX = new ArrayList<StockPriceInfo>();

    private Util m_objUtil = null;

    private ElapsedTime m_objElapsedTime = null;

    private DataSource m_objDataSource = null;

    private int[] m_nEMADurationValue;

    public void initialize() {
        EMACalculator.initialize();
        Vector<StockPrice> lstHistoricalPrice = null;
        lstHistoricalPrice = m_objDataSource.getStockPriceList();
        if (lstHistoricalPrice != null && !lstHistoricalPrice.isEmpty()) {
            lstHistoricalPrice.removeAllElements();
        }
    }

    public TechnicalIndicators() {
        if (m_objUtil == null) {
            m_objUtil = Util.getInstance();
        }
        m_nEMADurationValue = new int[2]; // m_nEMADurationValue
        m_nEMADurationValue[0] = 3;// default value
        m_nEMADurationValue[1] = 15;// default value
        if (m_objUtil == null) {
            m_objUtil = Util.getInstance();
        }
        if (m_objElapsedTime == null) {
            m_objElapsedTime = ElapsedTime.getInstance();
        }
    }

    public TechnicalIndicators(String strThreadName) {
        super(strThreadName);
        if (m_objUtil == null) {
            m_objUtil = Util.getInstance();
        }
    }

    public TechnicalIndicators(ThreadGroup objTG, String strThreadName) {
        super(objTG, strThreadName);
        if (m_objUtil == null) {
            m_objUtil = Util.getInstance();
        }
    }

    public int calculateSMA(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice, int nPeriod) {
        double dblSMA = 0.0;
        try {
            int nNumOfElem = lstHistoricalPrice.size();
            int nSubListStartIdx = nNumOfElem - nPeriod;
            StockPrice objStockPrice = null;
            for (int nIdx = nNumOfElem; nIdx >= nPeriod; nIdx--) {
                objStockPrice = (StockPrice) lstHistoricalPrice
                        .elementAt(nSubListStartIdx);
                List<StockPrice> lstStockPriceForSMA = lstHistoricalPrice
                        .subList(nSubListStartIdx, nIdx);
                // Calculate SMA of Stock Closing Price
                dblSMA = calculateSMA(strStockCode, lstStockPriceForSMA, 0);
                objStockPrice.setSMA(dblSMA, nPeriod);
                if (nPeriod == 20) {
                    // Calculate SMA of Typical Price
                    if (Util.getInstance().getGenCCIReportValue() == 1) {
                        double dbl20DaysSMAOfTypicalPrice = calculateSMA(
                                strStockCode, lstStockPriceForSMA, 1);
                        objStockPrice
                                .set20DaysSMAOfTypicalPrice(dbl20DaysSMAOfTypicalPrice);
                        calculateCCI(strStockCode, lstStockPriceForSMA,
                                objStockPrice);
                    }
                    // For Bollinger Band, intermediate calculations.
                    double dblDeviation = objStockPrice.getClosePrice()
                            - dblSMA;
                    objStockPrice
                            .setDeviationSquared(Math.pow(dblDeviation, 2));
                }
                nSubListStartIdx--;
            }
        } catch (ArrayIndexOutOfBoundsException aiobeExcep) {
            aiobeExcep.printStackTrace();
        }
        return Constants.NO_ERROR; // Success
    }

    public double calculateSMA(String strStockCode,
            List<StockPrice> lstHistoricalPrice, int nTheField) {
        double dblSMA = 0.0;
        try {
            int nNumOfElem = lstHistoricalPrice.size();
            if (nNumOfElem == 0) {
                return dblSMA;
            }
            int nIdx = 0;
            StockPrice objStockPrice = null;
            while (nIdx < nNumOfElem) {
                objStockPrice = (StockPrice) lstHistoricalPrice.get(nIdx);
                if (objStockPrice != null) {
                    switch (nTheField) {
                    case 0:
                        dblSMA += objStockPrice.getClosePrice();
                        break;
                    case 1:
                        dblSMA += objStockPrice.getTypicalPrice();
                        break;
                    case 2:
                        dblSMA += objStockPrice.getDeviationSquared();
                        break;
                    default:
                        break;
                    }
                    nIdx++;
                }
            }
            dblSMA = dblSMA / nNumOfElem;
        } catch (ArrayIndexOutOfBoundsException aiobeExcep) {
            aiobeExcep.printStackTrace();
        }
        return dblSMA;
    }

    public int calculateSMAForADX(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice, int nPeriod) {
        try {

            int nNumOfElem = lstHistoricalPrice.size();
            if (nNumOfElem == 0) {
                return Constants.ERROR_EMPTY_LIST;
            }
            int nIdx = 0;
            double dblSMAOfTrueRange = 0.0;
            double dblSMAOfDMPlus = 0.0;
            double dblSMAOfDMMinus = 0.0;
            StockPrice objStockPrice = null;
            while (nIdx < nPeriod) {
                objStockPrice = (StockPrice) lstHistoricalPrice
                        .get(nNumOfElem - 2);
                if (objStockPrice != null) {
                    dblSMAOfTrueRange += objStockPrice.getTrueRange();
                    dblSMAOfDMPlus += objStockPrice.getDMPlus();
                    dblSMAOfDMMinus += objStockPrice.getDMMinus();
                    nNumOfElem--;
                    nIdx++;
                }
            }
            if (nPeriod != 0) {
                dblSMAOfTrueRange = dblSMAOfTrueRange / nPeriod;
                dblSMAOfDMPlus = dblSMAOfDMPlus / nPeriod;
                dblSMAOfDMMinus = dblSMAOfDMMinus / nPeriod;
            }
            objStockPrice.setEMAOfTrueRange(dblSMAOfTrueRange);
            objStockPrice.setEMAOfDMPlus(dblSMAOfDMPlus);
            objStockPrice.setEMAOfDMMinus(dblSMAOfDMMinus);

            double dblDIPlus = 0.00;
            double dblDIMinus = 0.00;
            if (dblSMAOfTrueRange > 0.00) {
                dblDIPlus = 100 * (dblSMAOfDMPlus / dblSMAOfTrueRange);
                dblDIMinus = 100 * (dblSMAOfDMMinus / dblSMAOfTrueRange);
            }
            double dblDIDiff = Math.abs(dblDIPlus - dblDIMinus);
            double dblDISum = dblDIPlus + dblDIMinus;

            objStockPrice.setDIPlus(dblDIPlus);
            objStockPrice.setDIMinus(dblDIMinus);
            objStockPrice.setDIDiff(dblDIDiff);
            objStockPrice.setDISum(dblDISum);
            double dblDX = 0.00;
            if (dblDISum > 0.00) {
                dblDX = 100 * (dblDIDiff / dblDISum);
            }
            objStockPrice.setDX(dblDX);
        } catch (ArrayIndexOutOfBoundsException aiobeExcep) {
            aiobeExcep.printStackTrace();
        }
        return Constants.NO_ERROR; // Success
    }

    public int calculateSMAOfDX(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice, int nPeriod) {
        try {
            int nNumOfElem = lstHistoricalPrice.size();
            int nSubListStartIdx = nNumOfElem - nPeriod;
            StockPrice objStockPrice = null;
            for (int nIdx = nNumOfElem; nIdx >= nPeriod; nIdx--) {
                objStockPrice = (StockPrice) lstHistoricalPrice
                        .elementAt(nSubListStartIdx);
                List<StockPrice> lstStockPriceForDXSMA = lstHistoricalPrice
                        .subList(nSubListStartIdx, nIdx);

                double dblSMAOfDX = 0.0;
                try {
                    int nSize = lstStockPriceForDXSMA.size();
                    int nSubIdx = 0;
                    StockPrice objStockPriceOfDX = null;
                    while (nSubIdx < nSize) {
                        objStockPriceOfDX = (StockPrice) lstStockPriceForDXSMA
                                .get(nSubIdx);
                        if (objStockPriceOfDX != null) {
                            dblSMAOfDX += objStockPriceOfDX.getDX();
                            nSubIdx++;
                        }
                    }
                    if (nSize != 0) {
                        dblSMAOfDX = dblSMAOfDX / nSize;
                    }
                } catch (ArrayIndexOutOfBoundsException aiobeExcep) {
                    aiobeExcep.printStackTrace();
                }
                try {
                    objStockPrice.setADX(dblSMAOfDX);
                } catch (Exception excep) {
                    objStockPrice.setADX(0.00);
                }
                // objStockPrice.setADX(objBigDecADX.doubleValue());
                nSubListStartIdx--;
            }
        } catch (ArrayIndexOutOfBoundsException aiobeExcep) {
            aiobeExcep.printStackTrace();
        }
        return Constants.NO_ERROR; // Success
    }

    /*
     * Commodity Channel Index (CCI) Introduction
     *
     * Developed by Donald Lambert and featured in Commodities magazine in 1980,
     * the Commodity Channel Index (CCI) is a versatile indicator that can be
     * used to identify a new trend or warn of extreme conditions. Lambert
     * originally developed CCI to identify cyclical turns in commodities, but
     * the indicator can successfully applied to indices, ETFs, stocks and other
     * securities. In general, CCI measures the current price level relative to
     * an average price level over a given period of time. CCI is relatively
     * high when prices are far above their average. CCI is relatively low when
     * prices are far below their average.
     *
     * In this manner, CCI can be used to identify overbought and oversold
     * levels.
     *
     * // Refer most accurate formula for calculating commodity channel Index //
     * Source: http://support2.dundas.com/OnlineDocumentation/WebChart2005/
     * CommodityChannelIndex.html
     *
     *
     * S1: Go short - Commodity Channel Index turns down above the overbought
     * line. This trade is stopped out at the rally before S2. S2: Go short -
     * bearish divergence. This trade is stopped out during the rally before S3.
     * S3: Go short - bearish triple divergence. L1: Go long - Commodity Channel
     * Index turns up from below the oversold line. The next day closes below
     * the low of the signal day, causing the trade to be stopped out. A
     * trailing buy-stop would stop us back in two days later. S4: Go short -
     * Commodity Channel Index turns down above the overbought line. L2: Go long
     * - Commodity Channel Index turns up from below the oversold line. S5: Go
     * short - Commodity Channel Index turns down above the overbought line and
     * bearish divergence occurs. L3: Go long - Commodity Channel Index turns up
     * from below the oversold line and bullish divergence occurs. ?: The market
     * is now trending (evidenced by the break above the previous high). Do not
     * go short when Commodity Channel Index turns down above the overbought
     * line - wait for a bearish divergence. S6: Go short - bearish divergence.
     * S7: Even stronger signal - bearish triple divergence.
     *
     * Commodity Channel Index - CCI
     *
     * The Commodity Channel Index (CCI) is an oscillator that can be used to
     * identify a new trend or warn of extreme conditions. CCI is high when
     * prices are much above their average. CCI is relatively low when prices
     * much far below their average. In this manner, CCI can be used to identify
     * overbought and oversold levels.Today's traders often use the indicator to
     * determine cyclical trends in not only commodities, but also equities and
     * currencies.
     *
     * For instance, a security would be considered oversold when the CCI dips
     * below -100 and overbought when it exceeds +100.
     *
     * From oversold levels, a buy signal might be given when the CCI moves back
     * above -100.
     *
     * From overbought levels, a sell signal might be given when the CCI moved
     * back below +100.
     */
    public int calculateCCI(String strStockCode,
            List<StockPrice> lstHistoricalPrice, StockPrice objStockPrice) {

        try {
            int nSize = lstHistoricalPrice.size();
            double dblTypicalPrice = objStockPrice.getTypicalPrice();
            double dblMeanDeviation = 0.0;
            for (int nIdx = 0; nIdx < nSize; nIdx++) {
                dblMeanDeviation += Math.abs(objStockPrice
                        .get20DaysSMAOfTypicalPrice()
                        - lstHistoricalPrice.get(nIdx).getTypicalPrice());
            }
            if (nSize > 0) {
                dblMeanDeviation = dblMeanDeviation / nSize;
            }
            double dblCommodityChannelIndex = 0.00;
            if (dblMeanDeviation > 0.00) {
                dblCommodityChannelIndex = (dblTypicalPrice - objStockPrice
                        .get20DaysSMAOfTypicalPrice())
                        / (0.015 * dblMeanDeviation);
            }
            objStockPrice.setCommodityChannelIndex(dblCommodityChannelIndex);
        } catch (ArrayIndexOutOfBoundsException aiobeExcep) {
            aiobeExcep.printStackTrace();
        }
        return Constants.NO_ERROR; // Success
    }

    /*
     * EMA Calculation Exponential moving averages reduce the lag by applying
     * more weight to recent prices. The weighting applied to the most recent
     * price depends on the number of periods in the moving average. There are
     * three steps to calculating an exponential moving average. First,
     * calculate the simple moving average. An exponential moving average (EMA)
     * has to start somewhere so a simple moving average is used as the previous
     * period's EMA in the first calculation. Second, calculate the weighting
     * multiplier. Third, calculate the exponential moving average. The formula
     * below is for a 10-day EMA.
     *
     * SMA: 10 period sum / 10 Multiplier: (2 / (Time periods + 1) ) = (2 / (10
     * + 1) ) = 0.1818 (18.18%) EMA: {Close - EMA(previous day)} x multiplier +
     * EMA(previous day).
     */
    public int calculateEMAForADX(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice, int nPeriod) {
        int nNumOfElem = lstHistoricalPrice.size();
        if (nNumOfElem == 0) {
            return Constants.ERROR_EMPTY_LIST;
        }
        double dblEMAOfTrueRange = 0.0;
        double dblEMAOfDMPlus = 0.0;
        double dblEMAOfDMMinus = 0.0;
        StockPrice objCurrEMA = null;
        StockPrice objNextEMA = null;
        // Formula to calculate EMA
        // EMA = {ClosngPrice - EMA(previous day)} x multiplier + EMA(previous
        // day)
        while ((objCurrEMA = (StockPrice) lstHistoricalPrice
                .elementAt(nNumOfElem - 1)) != null) {
            nNumOfElem--;
            if (nNumOfElem == 0) {
                return Constants.NO_ERROR;
            }
            objNextEMA = (StockPrice) lstHistoricalPrice
                    .elementAt(nNumOfElem - 1);
            if (objCurrEMA.getEMAOfTrueRange() > 0.0) {
                dblEMAOfTrueRange = objCurrEMA.getEMAOfTrueRange()
                        - (objCurrEMA.getEMAOfTrueRange() / nPeriod)
                        + objNextEMA.getTrueRange();
                objNextEMA.setEMAOfTrueRange(dblEMAOfTrueRange);
            }
            if (objCurrEMA.getEMAOfDMPlus() > 0.0) {
                dblEMAOfDMPlus = objCurrEMA.getEMAOfDMPlus()
                        - (objCurrEMA.getEMAOfDMPlus() / nPeriod)
                        + objNextEMA.getDMPlus();
                objNextEMA.setEMAOfDMPlus(dblEMAOfDMPlus);
            }
            if (objCurrEMA.getEMAOfDMMinus() > 0.0) {
                dblEMAOfDMMinus = objCurrEMA.getEMAOfDMMinus()
                        - (objCurrEMA.getEMAOfDMMinus() / nPeriod)
                        + objNextEMA.getDMMinus();
                objNextEMA.setEMAOfDMMinus(dblEMAOfDMMinus);
            }

            double dblDIPlus = 0.00;
            double dblDIMinus = 0.00;
            if (dblEMAOfTrueRange > 0.00) {
                dblDIPlus = 100 * (dblEMAOfDMPlus / dblEMAOfTrueRange);
                dblDIMinus = 100 * (dblEMAOfDMMinus / dblEMAOfTrueRange);
            }
            double dblDIDiff = Math.abs(dblDIPlus - dblDIMinus);
            double dblDISum = dblDIPlus + dblDIMinus;
            objNextEMA.setDIPlus(dblDIPlus);
            objNextEMA.setDIMinus(dblDIMinus);
            objNextEMA.setDIDiff(dblDIDiff);
            objNextEMA.setDISum(dblDISum);
            double dblDX = 0.00;
            if (dblDISum > 0.00) {
                dblDX = 100 * (dblDIDiff / dblDISum);
            }
            objNextEMA.setDX(dblDX);
        }
        return Constants.NO_ERROR; // Success
    }

    /*
     * http://www.incrediblecharts.com/indicators/directional_movement.php
     *
     * To calculate the Directional Movement System:
     *
     * Calculate the Directional movement for today +DM = Today's High -
     * Yesterday's High (when price moves upward) -DM = Yesterday's Low -
     * Today's Low (when price moves downward) You cannot have both +DM and -DM
     * on the same day. If there is an outside day (where both calculations are
     * positive) then the larger of the two results is taken. An inside day
     * (where both calculations are negative) will always equal zero. Calculate
     * the true range for the day. True range is the largest of: Today's High -
     * Today's Low, Today's High - Yesterday's Close, and Yesterday's Close -
     * Today's Low +DM14 = exponential moving average* of +DM -DM14 =
     * exponential moving average* of -DM TR14 = exponential moving average* of
     * True Range
     *
     * Next, calculate the Directional Indicators: +DI14 = +DM14 divided by TR14
     * -DI14 = -DM14 divided by TR14
     *
     * Then, calculate the components of the Average Directional Movement Index
     * (ADX): Calculate the DI Difference: Record the difference between +DI14
     * and -DI14 as a positive number. Calculate the Directional Index (DX): DX
     * = DI Difference divided by the sum of +DI14 and -DI14 ADX = the
     * exponential moving average* of DX
     */
    // http://www.koders.com/cpp/fid544CA7C150BD3AC5ECAC72337DBC91DAB4B35E69.aspx
    // http://excelta.blogspot.com/2007/11/average-directional-index-adxmeaning.html
    // http://www.smallstocks.com.au/technical-analysis/directional-movement-index-dmi/
    public int calculateADX(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice, int nPeriod) {
        try {
            int nSize = lstHistoricalPrice.size();
            if (nSize == 0) {
                return Constants.ERROR_EMPTY_LIST;
            }

            double dblTrueRange = 0.0;
            double dblDMPlus = 0.0;
            double dblDMMinus = 0.0;

            StockPrice objCurr = null;
            StockPrice objPrev = null;
            for (int nIdx = nSize; nIdx >= 2; nIdx--) {
                objCurr = (StockPrice) lstHistoricalPrice.elementAt(nIdx - 2);
                if (objCurr != null) {
                    objPrev = (StockPrice) lstHistoricalPrice
                            .elementAt(nIdx - 1);
                    // http://en.mimi.hu/stockmarket/directional_movement_index.html
                    dblDMPlus = objCurr.getHighPrice() - objPrev.getHighPrice();
                    dblDMMinus = objPrev.getLowPrice() - objCurr.getLowPrice();
                    // If YH > = TH, and YL < = TL (i.e. if today's trading is
                    // totally within yesterday's range), then DM+ = 0; DM- = 0
                    if ((dblDMPlus > 0.0) && (dblDMMinus > 0.0)) {
                        // this is an outside day
                        // Please refer:
                        // http://www.incrediblecharts.com/technical/bar_charts.php#Uncertain
                        if (dblDMPlus > dblDMMinus) {
                            dblDMMinus = 0.0;
                        } else {
                            dblDMPlus = 0.0;
                        }
                    }
                    if (dblDMPlus < 0.0) {
                        // this is an inside day
                        // Please refer:
                        // http://www.incrediblecharts.com/technical/bar_charts.php#Uncertain
                        dblDMPlus = 0.0;
                    }
                    if (dblDMMinus < 0.0) {
                        dblDMMinus = 0.0;
                    }
                    // For True range computation
                    // Please refer:
                    // http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:average_true_range_a

                    double dblTR1 = objCurr.getHighPrice()
                            - objCurr.getLowPrice();
                    double dblTr2 = Math.abs(objCurr.getHighPrice()
                            - objPrev.getClosePrice());
                    double dblTr3 = Math.abs(objPrev.getClosePrice()
                            - objCurr.getLowPrice());
                    dblTrueRange = dblTR1 > dblTr2 ? dblTR1 : dblTr2;
                    if (dblTr3 > dblTR1) {
                        dblTrueRange = dblTr3;
                    }
                    objCurr.setTrueRange(dblTrueRange);
                    objCurr.setDMPlus(dblDMPlus);
                    objCurr.setDMMinus(dblDMMinus);
                }
            }
            calculateSMAForADX(strStockCode, lstHistoricalPrice, nPeriod);
            calculateEMAForADX(strStockCode, lstHistoricalPrice, nPeriod);
            calculateSMAOfDX(strStockCode, lstHistoricalPrice, nPeriod);
        } catch (ArrayIndexOutOfBoundsException aiobeExcep) {
            aiobeExcep.printStackTrace();
        }

        return Constants.NO_ERROR; // Success
    }

    public double calculateSMAOfStandardDeviation(String strStockCode,
            List<StockPrice> lstHistoricalPrice, double dblSMA) {
        double dblSMAOfStandardDeviation = 0.0;
        try {
            int nNumOfElem = lstHistoricalPrice.size();
            if (nNumOfElem == 0) {
                return dblSMAOfStandardDeviation;
            }
            int nIdx = 0;
            StockPrice objStockPrice = null;
            if (m_objListStandardDeviation == null) {
                m_objListStandardDeviation = new Vector<Double>();
            }
            while (nIdx < nNumOfElem) {
                objStockPrice = (StockPrice) lstHistoricalPrice.get(nIdx);
                if (objStockPrice != null) {
                    double dblStandardDeviation = objStockPrice.getClosePrice()
                            - dblSMA;
                    m_objListStandardDeviation.add(new Double(
                            dblStandardDeviation));
                    dblSMAOfStandardDeviation += dblStandardDeviation;
                    nIdx++;
                }
            }
            dblSMAOfStandardDeviation = dblSMAOfStandardDeviation / nNumOfElem;
        } catch (ArrayIndexOutOfBoundsException aiobeExcep) {
            aiobeExcep.printStackTrace();
        }
        return dblSMAOfStandardDeviation;
    }

    // http://www.investopedia.com/articles/technical/102201.asp#axzz1d7MiORgR
    // http://www.investopedia.com/articles/trading/05/022205.asp#axzz1d7MiORgR
    // The Bollinger band formula consists of the following:
    // BOLU = Upper Bollinger Band
    // BOLD = Lower Bollinger Band
    // n = Smoothing Period
    // m = Number of Standard Deviations (SD)
    // SD = Standard Deviation over Last n Periods Typical Price (TP) = (HI + LO
    // + CL) / 3
    // BOLU = MA(TP, n) + m * SD[TP, n]
    // BOLD = MA(TP, n) - m * SD[TP, n]
    // Read more:
    // http://www.investopedia.com/articles/trading/05/022205.asp#ixzz1d7N9P2HA
    // http://msdn.microsoft.com/en-us/library/dd456675.aspx
    // http://msdn.microsoft.com/en-us/library/dd489253.aspx
    // http://www.incrediblecharts.com/indicators/bollinger_percentage_b_band_width.php
    // http://code.google.com/p/jbooktrader/source/browse/source/com/jbooktrader/indicator/price/BollingerBands.java?spec=svn809&name=809&r=807c9ecb153494952f8174114bc57d1f5fa58ad9
    // http://code.google.com/p/jbooktrader/
    // http://support2.dundas.com/OnlineDocumentation/WebChart2005/Bollinger.html

    // http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:bollinger_bands
    // * Middle Band = 20-day simple moving average (SMA)
    // * Upper Band = 20-day SMA + (20-day standard deviation of price x 2)
    // * Lower Band = 20-day SMA - (20-day standard deviation of price x 2)
    public int calculateBollingerBand(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice, int nPeriod) {
        // BOLL = the Upper Bandwidth of Bollinger Band
        double dblUpperBollgerBand = 0.0;

        // BOLL = the Lower Bandwidth of Bollinger Band
        double dblLowerBollgerBand = 0.0;

        try {
            int nNumOfElem = lstHistoricalPrice.size() - nPeriod + 1;

            int nSubListStartIdx = nNumOfElem - nPeriod;
            StockPrice objStockPrice = null;
            for (int nIdx = nNumOfElem; nIdx >= nPeriod; nIdx--) {
                objStockPrice = (StockPrice) lstHistoricalPrice
                        .elementAt(nSubListStartIdx);
                List<StockPrice> lstStockPriceForSMA = lstHistoricalPrice
                        .subList(nSubListStartIdx, nIdx);
                // Calculate SMA of Stock Closing Price
                double dbl20DaysSMAOfDeviationSquared = calculateSMA(
                        strStockCode, lstStockPriceForSMA, 2);
                objStockPrice
                        .set20DaysSMAOfDeviationSquared(dbl20DaysSMAOfDeviationSquared);
                objStockPrice.setStandardDeviation(Math
                        .sqrt(dbl20DaysSMAOfDeviationSquared));

                dblUpperBollgerBand = objStockPrice.get20DaysSMA()
                        + (objStockPrice.getStandardDeviation() * 2);
                objStockPrice.setUpperBollgerBand(dblUpperBollgerBand);

                dblLowerBollgerBand = objStockPrice.get20DaysSMA()
                        - (objStockPrice.getStandardDeviation() * 2);
                objStockPrice.setLowerBollgerBand(dblLowerBollgerBand);

                nSubListStartIdx--;
            }
            // objStockPrice
            double dblClosingPrice = objStockPrice.getClosePrice();
            double dblBollUpperBandDiff = Math.abs(dblUpperBollgerBand
                    - dblClosingPrice);
            double dblBollLowerBandDiff = Math.abs(dblLowerBollgerBand
                    - dblClosingPrice);

            double dblPercentRange = dblUpperBollgerBand / 100;
            if (dblBollUpperBandDiff <= dblPercentRange) {
                StockPriceInfo objStockPriceInfo = new StockPriceInfo(
                        strStockCode, objStockPrice);
                m_objListBollingerBand.add(objStockPriceInfo);
            }
            dblPercentRange = dblLowerBollgerBand / 100;
            if (dblBollLowerBandDiff <= dblPercentRange) {
                StockPriceInfo objStockPriceInfo = new StockPriceInfo(
                        strStockCode, objStockPrice);
                m_objListBollingerBand.add(objStockPriceInfo);
            }

        } catch (ArrayIndexOutOfBoundsException aiobeExcep) {
            aiobeExcep.printStackTrace();
        }
        return Constants.NO_ERROR; // Success
    }

    public void ProcessArchivedStock() throws RowsExceededException,
            WriteException, IOException, Exception {
        Calendar currDateTime = Calendar.getInstance();
        System.out.println();
        System.out.println("Current Date and time is: "
                + currDateTime.getTime().toString());
        Util.checkMemoryStatus();

        ReportWriter objReports = ReportWriter.getInstance();
        int nArrIdx = 0;
        Vector<StockPrice> lstHistoricalPrice = null;
        TechnicalIndicators objTI = new TechnicalIndicators();
        while (m_objUtil.getStockCodeValue() != null
                && nArrIdx < m_objUtil.getStockCodeValue().length) {
            try {
                initialize();
                System.out.println();
                String strStockCode = m_objUtil.getStockCodeValue()[nArrIdx]
                        .trim();
                // DataSource.m_strStockCode = strStockCode;
                nArrIdx++;
                int nDayOfWeek = currDateTime.get(Calendar.DAY_OF_WEEK);

                m_objElapsedTime.BeginStopWatch();
                lstHistoricalPrice = m_objDataSource
                        .getHistoricalPrice(strStockCode);
                m_objElapsedTime.logStopWatch("Historical price retrieval of",
                        strStockCode);

                if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                        Constants.STR_STOCK_CODE_DATA_SOURCE_YAHOO) == true
                        || m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                                Constants.STR_STOCK_CODE_DATA_SOURCE_GOOGLE) == true
                        || m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                                Constants.STR_STOCK_CODE_DATA_SOURCE_NSEINDIA) == true) {
                    if (nDayOfWeek != Calendar.SATURDAY
                            && nDayOfWeek != Calendar.SUNDAY) {
                        m_objDataSource
                                .getCurrentPriceOfStockOrIndex(strStockCode);
                    }
                    // Trim the capacity of this vector to be the vector's
                    // current size.
                    lstHistoricalPrice.trimToSize();
                    if (lstHistoricalPrice != null) {
                        if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                            m_objElapsedTime.BeginStopWatch();
                            calculateSMA(strStockCode, lstHistoricalPrice, 20);
                            if (Util.getInstance().getGenCCIReportValue() == 1) {
                                m_objElapsedTime
                                        .logStopWatch(
                                                "20 Days SMA (Simple Moving Average) and CCI (Commodity Channel Index) Calculation of",
                                                strStockCode);
                            } else {
                                m_objElapsedTime
                                        .logStopWatch(
                                                "20 Days SMA (Simple Moving Average) Calculation of",
                                                strStockCode);
                            }
                        }
                        if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                            m_objElapsedTime.BeginStopWatch();
                            calculateSMA(strStockCode, lstHistoricalPrice, 50);
                            m_objElapsedTime
                                    .logStopWatch(
                                            "50 Days SMA (Simple Moving Average) Calculation of",
                                            strStockCode);
                        }
                        if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                            m_objElapsedTime.BeginStopWatch();
                            calculateSMA(strStockCode, lstHistoricalPrice, 200);
                            m_objElapsedTime
                                    .logStopWatch(
                                            "200 Days SMA (Simple Moving Average) Calculation of",
                                            strStockCode);
                        }
                        // Begin EMA Calculation
                        m_objElapsedTime.BeginStopWatch();
                        for (int nEMACombo = 0; nEMACombo < m_objUtil
                                .getEMADurationValue().length; nEMACombo++) {
                            String strEMACombo = m_objUtil
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
                                            m_nEMADurationValue[nIdx++] = (new Integer(
                                                    strTemp)).intValue();
                                        }
                                    }
                                    EMACalculator.initialize();
                                    m_objUtil
                                            .setEMADurationIntValue(m_nEMADurationValue);
                                } catch (NumberFormatException nfeExcep) {
                                    nfeExcep.printStackTrace();
                                }
                                EMACalculator.calculateSMA(strStockCode,
                                        lstHistoricalPrice,
                                        m_nEMADurationValue[0], strEMACombo);
                                EMACalculator.calculateSMA(strStockCode,
                                        lstHistoricalPrice,
                                        m_nEMADurationValue[1], strEMACombo);
                                EMACalculator.calculateEMA(strStockCode,
                                        lstHistoricalPrice, strEMACombo);
                            }
                        }
                        m_objElapsedTime
                                .logStopWatch(
                                        "EMA (Exponential Moving Average) Calculation of",
                                        strStockCode);
                        // End EMA Calculation
                        if (m_objUtil.getGenBollReportValue() == 1) {
                            m_objElapsedTime.BeginStopWatch();
                            calculateBollingerBand(strStockCode,
                                    lstHistoricalPrice, 20);
                            m_objElapsedTime.logStopWatch(
                                    "Bollinger Band calculation of",
                                    strStockCode);
                        }
                        if (m_objUtil.getGenRSIReportValue() == 1) {
                            m_objElapsedTime.BeginStopWatch();
                            RSICalculator.calculateRSI(lstHistoricalPrice);
                            m_objElapsedTime
                                    .logStopWatch(
                                            "RSI (Relative Strength Index) Calculation of",
                                            strStockCode);
                        }
                        if (m_objUtil.getGenADLReportValue() == 1) {
                            m_objElapsedTime.BeginStopWatch();
                            ADLCalculator.calculateADL(strStockCode,
                                    lstHistoricalPrice);
                            m_objElapsedTime
                                    .logStopWatch(
                                            "ADL (Accumulation Distribution Line) calculation of",
                                            strStockCode);
                        }
                        if (m_objUtil.getGenADXReportValue() == 1) {
                            m_objElapsedTime.BeginStopWatch();
                            calculateADX(strStockCode, lstHistoricalPrice,
                                    m_objUtil.getADXDurationValue());
                            m_objElapsedTime
                                    .logStopWatch(
                                            "ADX (Average Directional Movement Index) calculation of",
                                            strStockCode);
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
                        if (m_objUtil.getGenMasterEMAReportValue() == 1) {
                            m_objElapsedTime.BeginStopWatch();
                            EMACalculator.findEMACrossovers(strStockCode,
                                    lstHistoricalPrice,
                                    m_objUtil.getPreferredEMAComboValue());
                            m_objElapsedTime.logStopWatch(
                                    "EMA Crossover determination of",
                                    strStockCode);
                        }

                        // RSI based report. Stocks that are Overbought and
                        // Oversold on RSI.
                        m_objElapsedTime.BeginStopWatch();
                        RSICalculator.findRSIObOs(objCurrentDayStockPrice,
                                strStockCode);
                        m_objElapsedTime
                                .logStopWatch(
                                        "Determination of Overbought and Oversold stocks by RSI for",
                                        strStockCode);

                        if (m_objUtil.getGenStockEMAReportValue() == 1) {
                            m_objElapsedTime.BeginStopWatch();
                            objReports.generateEMAReport(strStockCode,
                                    lstHistoricalPrice);
                            m_objElapsedTime.logStopWatch(
                                    "EMA Report generation of", strStockCode);
                        }

                        // Calculate Pivot Points and Fibonacci Pivot Points.
                        if (objPreviousDayStockPrice != null
                                && m_objUtil.getGenPivotPointReportValue() == 1) {
                            m_objElapsedTime.BeginStopWatch();
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
                            m_objElapsedTime.logStopWatch(
                                    "Pivot point calculation of", strStockCode);
                        }
                    }
                }
            } catch (Exception excep) {
                excep.printStackTrace(System.out);
            }
            Util.checkMemoryStatus();
        }
        if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                Constants.STR_STOCK_CODE_DATA_SOURCE_YAHOO) == true
                || m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                        Constants.STR_STOCK_CODE_DATA_SOURCE_GOOGLE) == true
                || m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                        Constants.STR_STOCK_CODE_DATA_SOURCE_NSEINDIA) == true) {

            try {
                if (m_objUtil.getGenMasterEMAReportValue() == 1) {
                    m_objElapsedTime.BeginStopWatch();
                    ReportWriter.getInstance().generateEMACrossoverReport();
                    m_objElapsedTime.logStopWatch(
                            "EMA Crossover report generation",
                            "for the specified stocks");
                }
                Util.checkMemoryStatus();
                if (m_objUtil.getGenPivotPointReportValue() == 1) {
                    m_objElapsedTime.BeginStopWatch();
                    objReports.generatePivotPointReport(
                            PivotPointCalculator.m_objListPivotPoint, false);
                    objReports.generatePivotPointReport(
                            PivotPointCalculator.m_objListFibonacciPivotPoint,
                            true);
                    m_objElapsedTime.logStopWatch(
                            "Pivot point Report generation",
                            "for the specified stocks");
                }
                Util.checkMemoryStatus();
            } catch (Exception excep) {
                excep.printStackTrace(System.out);
            }
        }
        // RSI based report. Stocks that are Overbought and Oversold on RSI.
        m_objElapsedTime.BeginStopWatch();
        ReportWriter.getInstance().generateRSIObOsReport();
        ReportWriter.getInstance().generateBollingerBandReport(objTI);
        m_objElapsedTime
                .logStopWatch(
                        "Report generation of Overbought and Oversold stocks by RSI and Bollinger Band.",
                        "");
    }

    public void run() {
        try {
            Calendar currDateTime = Calendar.getInstance();
            System.out.println();
            System.out
                    .println("Current Date and time before call to ProcessArchivedStock() is: "
                            + currDateTime.getTime().toString());
            sleep(1 * 90 * 1000);
            // ProcessArchivedStock();
            currDateTime = Calendar.getInstance();
            System.out.println();
            System.out
                    .println("Current Date and time after call to ProcessArchivedStock() is: "
                            + currDateTime.getTime().toString());
        } catch (Exception ieExcep) {
            ieExcep.printStackTrace();
        }
    }

    // Find Over Bought and Over Sold stocks as per RSI
    // Oversold stock is having RSI 30 or lower.
    // Overbought stock is having RSI 70 or above.
    public void findTrendingStockOnADX(Vector<StockPrice> lstHistoricalPrice,
            String strStockCode) {
        if (lstHistoricalPrice == null || lstHistoricalPrice.size() == 0
                || strStockCode == null) {
            return;
        }
        StockPrice objCurrDayStockPrice = (StockPrice) lstHistoricalPrice
                .get(0);
        StockPrice objPreviousDayStockPrice = (StockPrice) lstHistoricalPrice
                .get(1);
        findTrendingStockOnADX(objCurrDayStockPrice, objPreviousDayStockPrice,
                strStockCode);
    }

    public void findTrendingStockOnADX(StockPrice objCurrDayStockPrice,
            StockPrice objPreviousDayStockPrice, String strStockCode) {
        if (objCurrDayStockPrice == null || strStockCode == null) {
            return;
        }
        double dblCurrADX = objCurrDayStockPrice.getADX();
        double dblCurrDIPlus = objCurrDayStockPrice.getDIPlus();
        double dblCurrDIMinus = objCurrDayStockPrice.getDIMinus();

        double dblPrevADX = objPreviousDayStockPrice.getADX();
        double dblPrevDIPlus = objPreviousDayStockPrice.getDIPlus();
        double dblPrevDIMinus = objPreviousDayStockPrice.getDIMinus();

        boolean bCondBullish = (dblCurrADX > 25.0)
                && (dblCurrDIPlus > dblCurrDIMinus)
                && (dblPrevDIPlus <= dblPrevDIMinus);
        boolean bCondBearish = (dblCurrADX > 25.0)
                && (dblCurrDIMinus > dblCurrDIPlus)
                && (dblPrevDIMinus <= dblPrevDIPlus);
        if (bCondBullish || bCondBearish) {
            StockPriceInfo objStockPriceInfo = new StockPriceInfo(strStockCode,
                    objCurrDayStockPrice);
            m_objListADX.add(objStockPriceInfo);
        }
    }
}
