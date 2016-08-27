/**
 *
 */
package com.fin.technical;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */
public class EMACalculator {

    public static ArrayList<EMACrossover> m_objListEMACrossover = new ArrayList<EMACrossover>();

    public static double m_dblLongDurationSMA = 0.0;
    public static double m_dblLongDurationEMA = 0.0;
    public static double m_dblLongDurationEMAMultiplier = 0.0;

    public static double m_dblShortDurationSMA = 0.0;
    public static double m_dblShortDurationEMA = 0.0;
    public static double m_dblShortDurationEMAMultiplier = 0.0;
    private static Util m_objUtil = null;
    public static Hashtable<String, Double> m_objListEMAMultiplier = null;

    static {
        if (m_objListEMAMultiplier == null) {
            m_objListEMAMultiplier = new Hashtable<String, Double>();
        }
        if (m_objUtil == null) {
            m_objUtil = Util.getInstance();
        }
        String[] strEMADurationValues = m_objUtil.getEMADurationValue();
        for (int nIdx = 0; nIdx < strEMADurationValues.length; nIdx++) {
            String strEMAValueSet = strEMADurationValues[nIdx];
            String[] strEMAValues = strEMAValueSet
                    .split(Constants.STR_SPILT_ON_COMMA);
            try {
                for (int nInnerIdx = 0; nInnerIdx < strEMAValues.length; nInnerIdx++) {
                    int nEMADurationValue = (new Integer(
                            strEMAValues[nInnerIdx].trim())).intValue();
                    if (nEMADurationValue <= 0) {
                        // a new IllegalInputException should have been thrown.
                        throw (new IllegalInputException(
                                Constants.STR_ILLEGAL_INPUT_FOR_EMA_CALC));
                    }
                }
                // after successful iteration of above for loop, we are entering
                // next for loop
                // this indicates that input supplied in ema.ini is correct.
                // e.g. 3,15:12,26 is correct, however 3,15:,12 is incorrect
                // in such scenario ,12 will be discarded.
                for (int nInnerIdx = 0; nInnerIdx < strEMAValues.length; nInnerIdx++) {
                    int nEMADurationValue = (new Integer(
                            strEMAValues[nInnerIdx].trim())).intValue();
                    Double objDblEMAMultiplier = new Double(
                            2.0 / (nEMADurationValue + 1));
                    m_objListEMAMultiplier.put(strEMAValues[nInnerIdx].trim(),
                            objDblEMAMultiplier);
                }
            } catch (NumberFormatException nfeExcep) {
                nfeExcep.printStackTrace(System.out);
            } catch(Exception excep) {
                excep.printStackTrace(System.out);
            }
        }
    }

    public EMACalculator() {
        initialize();
    }

    public static void initialize() {
        if (m_objUtil == null) {
            m_objUtil = Util.getInstance();
        }
        m_dblLongDurationSMA = 0.0;
        m_dblLongDurationEMA = 0.0;
        m_dblShortDurationSMA = 0.0;
        m_dblShortDurationEMA = 0.0;
    }

    public static int calculateSMA(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice, int nPeriod,
            String strEMACombo) {
        try {
            if (lstHistoricalPrice.size() == 0) {
                return Constants.ERROR_EMPTY_LIST;
            }
            if (nPeriod == 0) {
                return Constants.INT_INVALID_INPUT_TO_CALC_SMA;
            }
            int nIdx = 0;
            int nNumOfElem = lstHistoricalPrice.size();
            // double dblSMA = 0.0;
            StockPrice objStockPrice = null;
            MathContext objMathContext = new MathContext(7,
                    RoundingMode.HALF_UP);
            BigDecimal objBigDecSMA = new BigDecimal("0.00", objMathContext);
            EMA objCurrDayEMA = new EMA();
            while ((nIdx < nPeriod) && !lstHistoricalPrice.isEmpty()) {
                objStockPrice = (StockPrice) lstHistoricalPrice
                        .elementAt(nNumOfElem - 1);
                if (objStockPrice != null) {
                    BigDecimal objBigDecClosingPrice = new BigDecimal(
                            objStockPrice.getAdjClosePrice(), objMathContext);
                    objBigDecSMA = objBigDecSMA.add(objBigDecClosingPrice); // obj.getAdjClosePrice();
                    nNumOfElem--;
                    nIdx++;
                }
            }
            // objBigDecSMA = objBigDecSMA.divide(objBigDecDenominator);
            objBigDecSMA = new BigDecimal(objBigDecSMA.doubleValue() / nPeriod);
            if (nPeriod == m_objUtil.getEMADurationIntValue()[0]) {
                objCurrDayEMA.setShortDurationEMA(objBigDecSMA.doubleValue()); // This
                                                                               // will
                                                                               // be
                                                                               // the
                                                                               // starting
                                                                               // point
                                                                               // for
                                                                               // Short
                                                                               // Duration
                                                                               // e.g.
                                                                               // 3
                                                                               // Days
                                                                               // EMA,
                                                                               // which
                                                                               // is
                                                                               // nothing
                                                                               // but
                                                                               // SMA
            }
            if (nPeriod == m_objUtil.getEMADurationIntValue()[1]) {
                objCurrDayEMA.setLongDurationEMA(objBigDecSMA.doubleValue()); // This
                                                                              // will
                                                                              // be
                                                                              // the
                                                                              // starting
                                                                              // point
                                                                              // for
                                                                              // Long
                                                                              // Duration
                                                                              // e.g.
                                                                              // 15
                                                                              // Days
                                                                              // EMA,
                                                                              // which
                                                                              // is
                                                                              // nothing
                                                                              // but
                                                                              // SMA
            }
            objStockPrice.getEMAList().put(strEMACombo, objCurrDayEMA);
        } catch (ArrayIndexOutOfBoundsException aiobeExcep) {
            aiobeExcep.printStackTrace();
        }
        return Constants.NO_ERROR; // Success
    }

    // What Does Exponential Moving Average - EMA Mean?
    // A type of moving average that is similar to a simple moving average,
    // except that more weight is given to the latest data. The exponential
    // moving average is also known as "exponentially weighted moving average".

    // Investopedia explains Exponential Moving Average - EMA
    // This type of moving average reacts faster to recent price changes than
    // a simple moving average. The 12- and 26-day EMAs are the most popular
    // short-term averages, and they are used to create indicators like the
    // moving average convergence divergence (MACD) and
    // the percentage price oscillator (PPO). In general, the 50- and 200-day
    // EMAs
    // are used as signals of long-term trends.

    // http://www.iexplain.org/ema-how-to-calculate
    // http://en.wikipedia.org/wiki/Moving_average
    // http://www.java2s.com/Code/Java/Chart/JFreeChartMovingAverageDemo.htm
    // http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:moving_averages
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
    public static int calculateEMA(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice, String strEMACombo) {
        int nNumOfElem = lstHistoricalPrice.size();
        StockPrice objPrevDayStockPrice = null;
        StockPrice objCurrDayStockPrice = null;
        // Formula to calculate EMA
        // EMA = {ClosngPrice - EMA(previous day)} x multiplier + EMA(previous
        // day)
        String[] strEMADurations = strEMACombo
                .split(Constants.STR_SPILT_ON_COMMA);
        m_dblShortDurationEMAMultiplier = m_objListEMAMultiplier.get(
                strEMADurations[0]).doubleValue();
        m_dblLongDurationEMAMultiplier = m_objListEMAMultiplier.get(
                strEMADurations[1]).doubleValue();
        while ((objPrevDayStockPrice = (StockPrice) lstHistoricalPrice
                .elementAt(nNumOfElem - 1)) != null) {
            nNumOfElem--;
            if (nNumOfElem == 0) {
                return Constants.NO_ERROR;
            }
            objCurrDayStockPrice = (StockPrice) lstHistoricalPrice
                    .elementAt(nNumOfElem - 1);
            EMA objCurrDayEMA = ((EMA) objCurrDayStockPrice.getEMAList().get(
                    strEMACombo));
            EMA objPrevDayEMA = ((EMA) objPrevDayStockPrice.getEMAList().get(
                    strEMACombo));
            double dblShortDurationEMA = 0.00;
            double dblLongDurationEMA = 0.00;
            if (objPrevDayEMA != null) {
                dblShortDurationEMA = objPrevDayEMA.getShortDurationEMA();
                dblLongDurationEMA = objPrevDayEMA.getLongDurationEMA();
            }

            if (dblShortDurationEMA > 0.0) {
                if (objCurrDayEMA == null) {
                    objCurrDayEMA = new EMA();
                    objCurrDayStockPrice.getEMAList().put(strEMACombo,
                            objCurrDayEMA);
                }
                m_dblShortDurationEMA = (objCurrDayStockPrice
                        .getAdjClosePrice() - dblShortDurationEMA)
                        * m_dblShortDurationEMAMultiplier + dblShortDurationEMA;
                objCurrDayEMA.setShortDurationEMA(m_dblShortDurationEMA);
            }
            if (dblLongDurationEMA > 0.0) {
                if (objCurrDayEMA == null) {
                    objCurrDayEMA = new EMA();
                    objCurrDayStockPrice.getEMAList().put(strEMACombo,
                            objCurrDayEMA);
                }
                m_dblLongDurationEMA = (objCurrDayStockPrice.getAdjClosePrice() - dblLongDurationEMA)
                        * m_dblLongDurationEMAMultiplier + dblLongDurationEMA;
                objCurrDayEMA.setLongDurationEMA(m_dblLongDurationEMA);
            }
            // boolean bResult =
            // strEMACombo.equalsIgnoreCase(m_objUtil.getPreferredEMAComboValue());
            if (m_dblShortDurationEMA > 0.0 && m_dblLongDurationEMA > 0.0) {
                String strPreviousDayTrend = objPrevDayEMA.getTrend();
                StringBuffer strBufAction = new StringBuffer();
                if (m_dblShortDurationEMA > m_dblLongDurationEMA) {
                    objCurrDayEMA
                            .setTrend(Constants.STR_INDEX_STOCK_TREND_BULLISH);
                    int nResult = strPreviousDayTrend
                            .compareToIgnoreCase(objCurrDayEMA.getTrend());
                    if (nResult != 0) {
                        // Action would be a "Buy Recommendation"
                        strBufAction.append(String.format(
                                Constants.STR_ACTION_BUY, m_dblLongDurationEMA,
                                (int) m_dblLongDurationEMA));
                    }
                    if ((m_dblShortDurationEMA < objCurrDayStockPrice
                            .getLowPrice())
                            && (dblShortDurationEMA < objPrevDayStockPrice
                                    .getLowPrice())) {
                        // Action would be Book Profit.
                        if (strBufAction.length() > 0) {
                            strBufAction.append(" & ");
                        }
                        strBufAction.append(Constants.STR_ACTION_BOOK_PROFIT);
                    }
                    objCurrDayEMA.setAction(strBufAction.toString());
                } else {
                    objCurrDayEMA
                            .setTrend(Constants.STR_INDEX_STOCK_TREND_BEARISH);
                    int nResult = strPreviousDayTrend
                            .compareToIgnoreCase(objCurrDayEMA.getTrend());
                    if (nResult != 0) {
                        // Action would be a "Sell Recommendation"
                        strBufAction.append(String.format(
                                Constants.STR_ACTION_SELL,
                                m_dblShortDurationEMA,
                                (int) m_dblShortDurationEMA + 1));
                    }
                    if ((m_dblShortDurationEMA < objPrevDayStockPrice
                            .getHighPrice())
                            && (dblShortDurationEMA < objPrevDayStockPrice
                                    .getHighPrice())) {
                        if (strBufAction.length() > 0) {
                            strBufAction.append(" & ");
                        }
                        // Action would be Book Profit.
                        strBufAction.append(Constants.STR_ACTION_BOOK_PROFIT);
                    }
                    objCurrDayEMA.setAction(strBufAction.toString());
                }
            }
        }
        return Constants.NO_ERROR; // Success
    }

    public static void findEMACrossovers(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice, String strEMACombo) {
        if (lstHistoricalPrice == null || lstHistoricalPrice.size() == 0
                || strEMACombo == null) {
            return;
        }
        StockPrice objPrevDayStockPrice = (StockPrice) lstHistoricalPrice
                .get(0);
        StockPrice objCurrDayStockPrice = (StockPrice) lstHistoricalPrice
                .get(1);
        double dblShortDurationEMA = ((EMA) objPrevDayStockPrice.getEMAList()
                .get(strEMACombo)).getShortDurationEMA();
        double dblLongDurationEMA = ((EMA) objPrevDayStockPrice.getEMAList()
                .get(strEMACombo)).getLongDurationEMA();
        if (dblShortDurationEMA > 0.0 && dblLongDurationEMA > 0.0) {
            if (((EMA) objCurrDayStockPrice.getEMAList().get(strEMACombo))
                    .getTrend().compareToIgnoreCase(
                            ((EMA) objPrevDayStockPrice.getEMAList().get(
                                    strEMACombo)).getTrend()) != 0) {
                EMACrossover objEMACrossover = new EMACrossover(strStockCode,
                        objPrevDayStockPrice);
                if ((((EMA) objCurrDayStockPrice.getEMAList().get(strEMACombo))
                        .getTrend())
                        .compareToIgnoreCase(Constants.STR_INDEX_STOCK_TREND_BULLISH) == 0) {
                    objEMACrossover
                            .setEMACrossover(Constants.STR_EMA_CROSSOVER_NEGATIVE);
                } else {
                    objEMACrossover
                            .setEMACrossover(Constants.STR_EMA_CROSSOVER_POSITIVE);
                }
                m_objListEMACrossover.add(objEMACrossover);
            }
        }
    }

    public static void findEMACrossovers(String strStockCode,
            String strEMACombo, StockPrice objPrevDayStockPrice,
            StockPrice objCurrDayStockPrice) {
        if (strStockCode == null || strEMACombo == null
                || objPrevDayStockPrice == null || objCurrDayStockPrice == null) {
            System.out
                    .print("Invalid Input. Returning from findEMACrossovers(String strStockCode, String strEMACombo, StockPrice objPrevDayStockPrice, StockPrice objCurrDayStockPrice)");
            return;
        }
        double dblShortDurationEMA = ((EMA) objCurrDayStockPrice.getEMAList()
                .get(strEMACombo)).getShortDurationEMA();
        double dblLongDurationEMA = ((EMA) objCurrDayStockPrice.getEMAList()
                .get(strEMACombo)).getLongDurationEMA();
        if (dblShortDurationEMA > 0.0 && dblLongDurationEMA > 0.0) {
            String strCurrDayTrend = ((EMA) objCurrDayStockPrice.getEMAList()
                    .get(strEMACombo)).getTrend();
            String strPrevDayTrend = ((EMA) objPrevDayStockPrice.getEMAList()
                    .get(strEMACombo)).getTrend();
            if (strCurrDayTrend.compareToIgnoreCase(strPrevDayTrend) != 0) {
                EMACrossover objEMACrossover = new EMACrossover(strStockCode,
                        objPrevDayStockPrice);
                if ((((EMA) objCurrDayStockPrice.getEMAList().get(strEMACombo))
                        .getTrend())
                        .compareToIgnoreCase(Constants.STR_INDEX_STOCK_TREND_BULLISH) == 0) {
                    objEMACrossover
                            .setEMACrossover(Constants.STR_EMA_CROSSOVER_NEGATIVE);
                } else {
                    objEMACrossover
                            .setEMACrossover(Constants.STR_EMA_CROSSOVER_POSITIVE);
                }
                m_objListEMACrossover.add(objEMACrossover);
            }
        }
    }

}
