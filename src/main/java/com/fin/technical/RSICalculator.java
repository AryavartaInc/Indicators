package com.fin.technical;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Vector;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public class RSICalculator {

    public static StringBuffer m_strBufProgOutput = new StringBuffer();

    private static double[] m_dblGain = new double[14];

    private static double[] m_dblLoss = new double[14];

    public static ArrayList<RSI> m_objListRSI = new ArrayList<RSI>();

    // the no-agrs constructor
    public RSICalculator() {

    }

    // http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:relative_strength_index_rsi
    // RSI has been broken down into its basic components: RS, Average Gain and
    // Average Loss.
    // This RSI calculation is based on 14 periods, which is the default
    // suggested by Wilder in his book.
    // Losses are expressed as positive values, not negative values.
    /*
     *
     * 100 RSI = 100 - -------- 1 + RS
     *
     * RS = Average Gain / Average Loss
     */
    /*
     * Let us calculate Relative Strength
     */
    private static double computeAverage(double dblValue[]) {
        double dblReturn = 0.0;
        for (double dblArrValue : dblValue) {
            dblReturn += dblArrValue;
        }
        return dblReturn / dblValue.length;
    }

    // Relative Strength Index
    public static int calculateRSI(Vector<StockPrice> lstHistoricalPrice) {
        try {
            int nArrayIndex = 0;
            int nIdx = 0;
            StockPrice objCurrent = null;
            StockPrice objNext = null;
            int nNumOfElem = lstHistoricalPrice.size();
            if (nNumOfElem <= 0) {
                return Constants.ERROR_EMPTY_LIST;
            }
            int nRSIDuration = Util.getInstance().getRSIDurationValue();
            MathContext objMathContext = new MathContext(7,
                    RoundingMode.HALF_UP);
            while ((objCurrent = (StockPrice) lstHistoricalPrice
                    .elementAt(nNumOfElem - 1)) != null) {
                nNumOfElem--;
                nIdx++;
                if (nNumOfElem == 0) {
                    return Constants.NO_ERROR;
                }
                objNext = (StockPrice) lstHistoricalPrice
                        .elementAt(nNumOfElem - 1);
                if (objCurrent != null && objNext != null) {
                    BigDecimal dblCurrentClosingPrice = new BigDecimal(
                            objCurrent.getClosePrice(), objMathContext);

                    BigDecimal dblNextClosingPrice = new BigDecimal(
                            objNext.getClosePrice(), objMathContext);

                    if (nArrayIndex == nRSIDuration) {
                        nArrayIndex = 0;
                    }
                    if (dblCurrentClosingPrice.compareTo(dblNextClosingPrice) == 1)// i.e.
                                                                                   // (dblCurrentClosingPrice
                                                                                   // >
                                                                                   // dblNextClosingPrice)
                    {
                        m_dblLoss[nArrayIndex] = dblCurrentClosingPrice
                                .subtract(dblNextClosingPrice).doubleValue();
                        m_dblGain[nArrayIndex] = 0.0;
                    } else {
                        m_dblGain[nArrayIndex] = dblNextClosingPrice.subtract(
                                dblCurrentClosingPrice).doubleValue();
                        m_dblLoss[nArrayIndex] = 0.0;
                    }
                    if (nIdx >= (nRSIDuration - 1)) {
                        BigDecimal dblAvgGain = new BigDecimal(
                                computeAverage(m_dblGain), objMathContext);

                        BigDecimal dblAvgLoss = new BigDecimal(
                                computeAverage(m_dblLoss), objMathContext);

                        if (dblAvgLoss.compareTo(Constants.m_objBigDecZero) == 0) {
                            objNext.setRSI(100);
                            continue;
                        }
                        if (dblAvgGain.compareTo(Constants.m_objBigDecZero) == 0) {
                            objNext.setRSI(0);
                            continue;
                        }
                        double dblRS = 0.00;
                        if (dblAvgLoss.compareTo(Constants.m_objBigDecZero) != 0) // avoid
                                                                                  // divide
                                                                                  // by
                                                                                  // Zero.
                        {
                            dblRS = dblAvgGain.doubleValue()
                                    / dblAvgLoss.doubleValue();

                            double dblRSI = 100 - (100 / (1 + dblRS));
                            BigDecimal objBigDecRSI = new BigDecimal(dblRSI,
                                    objMathContext);
                            objNext.setRSI(objBigDecRSI.doubleValue());
                        }
                        objNext.setAverageGain(dblAvgGain.doubleValue());
                        objNext.setAverageLoss(dblAvgLoss.doubleValue());

                        BigDecimal objBigDecRS = new BigDecimal(dblRS,
                                objMathContext);
                        objNext.setRelativeStrength(objBigDecRS.doubleValue());
                    }
                    objNext.setGain(m_dblGain[nArrayIndex]);
                    objNext.setLoss(m_dblLoss[nArrayIndex]);

                    nArrayIndex++;
                }

            }
        } catch (ArrayIndexOutOfBoundsException aiobeExcep) {
            aiobeExcep.printStackTrace();
        }
        return Constants.NO_ERROR; // Success
    }

    // Find Over Bought and Over Sold stocks as per RSI
    // Oversold stock is having RSI 30 or lower.
    // Overbought stock is having RSI 70 or above.
    public static void findRSIObOs(Vector<StockPrice> lstHistoricalPrice,
            String strStockCode) {
        if (lstHistoricalPrice == null || lstHistoricalPrice.size() == 0
                || strStockCode == null) {
            return;
        }
        StockPrice objCurrDayStockPrice = (StockPrice) lstHistoricalPrice
                .get(0);
        findRSIObOs(objCurrDayStockPrice, strStockCode);

        // int nStockRSI = (int)objCurrDayStockPrice.getRSI();
        // if(nStockRSI <= 30 || nStockRSI >= 70)
        // {
        // EMACrossover objEMACrossover = new EMACrossover();
        // objEMACrossover.setStockCode(strStockCode);
        // objEMACrossover.setStockIndexPriceHistory(objCurrDayStockPrice);
        // }
    }

    public static void findRSIObOs(StockPrice objCurrDayStockPrice,
            String strStockCode) {
        if (objCurrDayStockPrice == null || strStockCode == null) {
            return;
        }
        int nStockRSI = (int) objCurrDayStockPrice.getRSI();
        if (nStockRSI <= 20 || nStockRSI >= 70) {
            RSI objRSI = new RSI();
            objRSI.setStockCode(strStockCode);
            objRSI.setStockPrice(objCurrDayStockPrice);
            m_objListRSI.add(objRSI);
        }
    }
}
