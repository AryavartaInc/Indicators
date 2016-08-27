/**
 *
 */
package com.fin.technical;

import java.math.BigDecimal;
import java.util.Hashtable;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public class StockPrice {

    // private members
    // Date,Open,High,Low,Close,Volume,Adj Close
    // Input to the program
    protected String m_strDate = null;

    protected double m_dblOpenPrice = 0.0;

    protected double m_dblHighPrice = 0.0;

    protected double m_dblLowPrice = 0.0;

    protected double m_dblClosePrice = 0.0;

    protected int m_nVolume = 0;

    protected double m_dblAdjClosePrice = 0.0;

    protected BigDecimal m_objBigDecOpenPrice = null;

    protected BigDecimal m_objBigDecHighPrice = null;

    protected BigDecimal m_objBigDecLowPrice = null;

    protected BigDecimal m_objBigDecClosePrice = null;

    protected BigDecimal m_objBigDecVolume = null;

    protected BigDecimal m_objBigDecAdjClosePrice = null;

    public Hashtable<String, EMA> m_objListEMA = new Hashtable<String, EMA>();

    // Begin For RSI, intermediate as well as end result, such as EMA
    protected double m_dblGain = 0.0;

    protected double m_dblLoss = 0.0;

    protected double m_dblAverageGain = 0.0;

    protected double m_dblAverageLoss = 0.0;

    protected double m_dblRelativeStrength = 0.0;

    protected double m_dblRSI = 0.0;
    // End For RSI, intermediate as well as end result, such as EMA

    // Begin For Accumulation Distribution Line Calculation.
    protected int m_nADL = 0;
    // End For Accumulation Distribution Line Calculation.

    // For Simple Moving Average of 20 Days
    protected double m_dbl20DaySMA = 0.0;

    // For Simple Moving Average of 50 Days
    protected double m_dbl50DaySMA = 0.0;

    // For Simple Moving Average of 100 Days
    protected double m_dbl100DaySMA = 0.0;

    // For Simple Moving Average of 200 Days
    protected double m_dbl200DaySMA = 0.0;

    // Begin For Commodity Channel Index.
    protected double m_dblTypicalPrice = 0.0;

    protected double m_dbl20DaysSMAOfTypicalPrice = 0.0;

    protected double m_dblCommodityChannelIndex = 0.0;
    // End For Commodity Channel Index.

    // Based on 3EMA-15 EMA or any combination like 12EMA-26 EMA, Crossover
    // protected String m_strTrend = "";

    // Begin For ADX (Average Directional Index), intermediate as well as end
    // result,
    // such as ADX (Average Directional Index)
    // True range is the largest of:
    // Today's High - Today's Low,
    // Today's High - Yesterday's Close, and
    // Yesterday's Close - Today's Low
    protected double m_dblTrueRange = 0.0;

    // To calculate the Directional Movement System:
    // +DM = Today's High - Yesterday's High (when price moves upward)
    protected double m_dblDMPlus = 0.0;

    // -DM = Yesterday's Low - Today's Low (when price moves downward)
    protected double m_dblDMMinus = 0.0;

    // exponential moving average* of TR
    protected double m_dblEMAOfTrueRange = 0.0;

    // exponential moving average* of +DM
    protected double m_dblEMAOfDMPlus = 0.0;

    // exponential moving average* of -DM
    protected double m_dblEMAOfDMMinus = 0.0;

    // Next, calculate the Directional Indicators:
    // +DM14 divided by TR14
    protected double m_dblDIPlus = 0.0;

    // -DM14 divided by TR14
    protected double m_dblDIMinus = 0.0;

    // Record the difference between +DI14 and -DI14 as a positive number.
    protected double m_dblDIDiff = 0.0;

    protected double m_dblDISum = 0.0;

    // Calculate the Directional Index (DX):
    // DX = DI Difference divided by the sum of +DI14 and -DI14
    protected double m_dblDX = 0.0;

    // ADX = the exponential moving average* of DX
    protected double m_dblADX = 0.0;

    // Begin For Bollinger Band.
    protected double m_dblDeviationSquared = 0.0;

    protected double m_dbl20DaysSMAOfDeviationSquared = 0.0;

    protected double m_dblStandardDeviation = 0.0;

    // BOLL = the Upper Bandwidth of Bollinger Band
    protected double m_dblUpperBollgerBand = 0.0;

    // BOLL = the Lower Bandwidth of Bollinger Band
    protected double m_dblLowerBollgerBand = 0.0;

    // Begin For Rate Of Change.
    protected float m_dblROC;
    // End For Rate Of Change.

    /**
	 *
	 */
    public StockPrice() {
        if (m_objListEMA == null) {
            m_objListEMA = new Hashtable<String, EMA>();
        }
    }

    // Constructor For EMA Calculation
    public StockPrice(String strDate, double dblOpenPrice, double dblHighPrice,
            double dblLowPrice, double dblClosePrice, int nVolume,
            double dblAdjClosePrice) {
        if (m_objListEMA == null) {
            m_objListEMA = new Hashtable<String, EMA>();
        }

        m_strDate = strDate;

        m_objBigDecOpenPrice = new BigDecimal(dblOpenPrice);
        m_objBigDecHighPrice = new BigDecimal(dblHighPrice);
        m_objBigDecLowPrice = new BigDecimal(dblLowPrice);
        m_objBigDecClosePrice = new BigDecimal(dblClosePrice);
        m_objBigDecVolume = new BigDecimal(nVolume);

        m_dblOpenPrice = dblOpenPrice;
        m_dblHighPrice = dblHighPrice;
        m_dblLowPrice = dblLowPrice;
        m_dblClosePrice = dblClosePrice;
        m_nVolume = nVolume;
        m_dblAdjClosePrice = dblAdjClosePrice;

        m_dblTypicalPrice = (m_dblHighPrice + m_dblLowPrice + m_dblClosePrice) / 3;
    }

    // Constructor For Pivot Point Calculation
    public StockPrice(double dblHighPrice, double dblLowPrice,
            double dblClosePrice) {
        if (m_objListEMA == null) {
            m_objListEMA = new Hashtable<String, EMA>();
        }

        m_objBigDecHighPrice = new BigDecimal(dblHighPrice);
        m_objBigDecLowPrice = new BigDecimal(dblLowPrice);
        m_objBigDecClosePrice = new BigDecimal(dblClosePrice);

        m_dblHighPrice = dblHighPrice;
        m_dblLowPrice = dblLowPrice;
        m_dblClosePrice = dblClosePrice;

        m_dblTypicalPrice = (m_dblHighPrice + m_dblLowPrice + m_dblClosePrice) / 3;
    }

    public StockPrice(String[] strValues, int nSource)
            throws NumberFormatException {
        if (m_objListEMA == null) {
            m_objListEMA = new Hashtable<String, EMA>();
        }
    }

    // getters
    public String getDate() {
        return m_strDate;
    }

    public double getOpenPrice() {
        return m_dblOpenPrice;
    }

    public double getHighPrice() {
        return m_dblHighPrice;
    }

    public double getLowPrice() {
        return m_dblLowPrice;
    }

    public double getClosePrice() {
        return m_dblClosePrice;
    }

    public int getVolume() {
        return m_nVolume;
    }

    public double getAdjClosePrice() {
        return m_dblAdjClosePrice;
    }

    // public double getShortDurationEMA()
    // {
    // return m_dblShortDurationEMA;
    // }
    //
    // public double getLongDurationEMA()
    // {
    // return m_dblLongDurationEMA;
    // }

    public Hashtable<String, EMA> getEMAList() {
        return m_objListEMA;
    }

    public double getRSI() {
        return m_dblRSI;
    }

    public int getADL() {
        return m_nADL;
    }

    // public String getTrend()
    // {
    // return m_strTrend;
    // }

    public double get20DaysSMA() {
        return m_dbl20DaySMA;
    }

    public double get50DaysSMA() {
        return m_dbl50DaySMA;
    }

    public double get100DaysSMA() {
        return m_dbl100DaySMA;
    }

    public double get200DaysSMA() {
        return m_dbl200DaySMA;
    }

    public double getTypicalPrice() {
        return m_dblTypicalPrice;
    }

    public double get20DaysSMAOfTypicalPrice() {
        return m_dbl20DaysSMAOfTypicalPrice;
    }

    public double getCommodityChannelIndex() {
        return m_dblCommodityChannelIndex;
    }

    public double getTrueRange() {
        return m_dblTrueRange;
    }

    public double getDMPlus() {
        return m_dblDMPlus;
    }

    public double getDMMinus() {
        return m_dblDMMinus;
    }

    public double getEMAOfTrueRange() {
        return m_dblEMAOfTrueRange;
    }

    public double getEMAOfDMPlus() {
        return m_dblEMAOfDMPlus;
    }

    public double getEMAOfDMMinus() {
        return m_dblEMAOfDMMinus;
    }

    public double getDIPlus() {
        return m_dblDIPlus;
    }

    public double getDIMinus() {
        return m_dblDIMinus;
    }

    public double getDIDiff() {
        return m_dblDIDiff;
    }

    public double getDISum() {
        return m_dblDISum;
    }

    public double getDX() {
        return m_dblDX;
    }

    public double getADX() {
        return m_dblADX;
    }

    public double getDeviationSquared() {
        return m_dblDeviationSquared;
    }

    public double get20DaysSMAOfDeviationSquared() {
        return m_dbl20DaysSMAOfDeviationSquared;
    }

    public double getStandardDeviation() {
        return m_dblStandardDeviation;
    }

    public double getUpperBollgerBand() {
        return m_dblUpperBollgerBand;
    }

    public double getLowerBollgerBand() {
        return m_dblLowerBollgerBand;
    }

    public double getSMA(int nDuration) {
        switch (nDuration) {
        case 20:
            return m_dbl20DaySMA;
        case 50:
            return m_dbl50DaySMA;
        case 100:
            return m_dbl100DaySMA;
        case 200:
            return m_dbl200DaySMA;
        default:
            return 0.0;
        }
    }

    public float getROC() {
        return m_dblROC;
    }

    // public String getAction()
    // {
    // return m_strAction;
    // }
    //
    // public void setAction(String strAction)
    // {
    // m_strAction = strAction;
    // }
    // setters
    public void setDate(String strDate) {
        m_strDate = strDate;
    }

    public void setOpenPrice(double dblOpenPrice) {
        m_dblOpenPrice = dblOpenPrice;
    }

    public void setHighPrice(double dblHighPrice) {
        m_dblHighPrice = dblHighPrice;
    }

    public void setLowPrice(double dblLowPrice) {
        m_dblLowPrice = dblLowPrice;
    }

    public void setClosePrice(double dblClosePrice) {
        m_dblClosePrice = dblClosePrice;
    }

    public void setVolume(int nVolume) {
        m_nVolume = nVolume;
    }

    public void setAdjClosePrice(double dblAdjClosePrice) {
        m_dblAdjClosePrice = dblAdjClosePrice;
    }

    // public void setShortDurationEMA(double dblShortDurationEMA)
    // {
    // m_dblShortDurationEMA = dblShortDurationEMA;
    // }
    //
    // public void setLongDurationEMA(double dblLongDurationEMA)
    // {
    // m_dblLongDurationEMA = dblLongDurationEMA;
    // }
    //
    // public void setTrend(String strTrend)
    // {
    // m_strTrend = strTrend;
    // }

    public void setEMAList(Hashtable<String, EMA> objListEMA) {
        m_objListEMA = objListEMA;
    }

    public void set20DaysSMA(double dbl20DaySMA) {
        m_dbl20DaySMA = dbl20DaySMA;
    }

    public void set50DaysSMA(double dbl50DaySMA) {
        m_dbl50DaySMA = dbl50DaySMA;
    }

    public void set100DaysSMA(double dbl100DaySMA) {
        m_dbl100DaySMA = dbl100DaySMA;
    }

    public void set200DaysSMA(double dbl200DaySMA) {
        m_dbl200DaySMA = dbl200DaySMA;
    }

    public void setTypicalPrice(double dblTypicalPrice) {
        m_dblTypicalPrice = dblTypicalPrice;
    }

    public void set20DaysSMAOfTypicalPrice(double dbl20DaysSMAOfTypicalPrice) {
        m_dbl20DaysSMAOfTypicalPrice = dbl20DaysSMAOfTypicalPrice;
    }

    public void setCommodityChannelIndex(double dblCommodityChannelIndex) {
        m_dblCommodityChannelIndex = dblCommodityChannelIndex;
    }

    public void setSMA(double dblSMA, int nDuration) {
        switch (nDuration) {
        case 20:
            m_dbl20DaySMA = dblSMA;
            break;
        case 50:
            m_dbl50DaySMA = dblSMA;
            break;
        case 100:
            m_dbl100DaySMA = dblSMA;
            break;
        case 200:
            m_dbl200DaySMA = dblSMA;
            break;
        default:
            break;
        }
    }

    public double getGain() {
        return m_dblGain;
    }

    public double getLoss() {
        return m_dblLoss;
    }

    public void setGain(double dblGain) {
        m_dblGain = dblGain;
    }

    public void setLoss(double dblLoss) {
        m_dblLoss = dblLoss;
    }

    public double getAverageGain() {
        return m_dblAverageGain;
    }

    public double getAverageLoss() {
        return m_dblAverageLoss;
    }

    public void setAverageGain(double dblAverageGain) {
        m_dblAverageGain = dblAverageGain;
    }

    public void setAverageLoss(double dblAverageLoss) {
        m_dblAverageLoss = dblAverageLoss;
    }

    public double getRelativeStrength() {
        return m_dblRelativeStrength;
    }

    public void setRelativeStrength(double dblRelativeStrength) {
        m_dblRelativeStrength = dblRelativeStrength;
    }

    public void setRSI(double dblRSI) {
        m_dblRSI = dblRSI;
    }

    public void setADL(int nADL) {
        m_nADL = nADL;
    }

    public void setTrueRange(double dblTrueRange) {
        m_dblTrueRange = dblTrueRange;
    }

    public void setDMPlus(double dblDMPlus) {
        m_dblDMPlus = dblDMPlus;
    }

    public void setDMMinus(double dblDMMinus) {
        m_dblDMMinus = dblDMMinus;
    }

    public void setEMAOfTrueRange(double dblEMAOfTrueRange) {
        m_dblEMAOfTrueRange = dblEMAOfTrueRange;
    }

    public void setEMAOfDMPlus(double dblEMAOfDMPlus) {
        m_dblEMAOfDMPlus = dblEMAOfDMPlus;
    }

    public void setEMAOfDMMinus(double dblEMAOfDMMinus) {
        m_dblEMAOfDMMinus = dblEMAOfDMMinus;
    }

    public void setDIPlus(double dblDIPlus) {
        m_dblDIPlus = dblDIPlus;
    }

    public void setDIMinus(double dblDIMinus) {
        m_dblDIMinus = dblDIMinus;
    }

    public void setDIDiff(double dblDIDiff) {
        m_dblDIDiff = dblDIDiff;
    }

    public void setDISum(double dblDISum) {
        m_dblDISum = dblDISum;
    }

    public void setDX(double dblDX) {
        m_dblDX = dblDX;
    }

    public void setADX(double dblADX) {
        m_dblADX = dblADX;
    }

    public void setDeviationSquared(double dblDeviationSquared) {
        m_dblDeviationSquared = dblDeviationSquared;
    }

    public void set20DaysSMAOfDeviationSquared(
            double dbl20DaysSMAOfDeviationSquared) {
        m_dbl20DaysSMAOfDeviationSquared = dbl20DaysSMAOfDeviationSquared;
    }

    public void setStandardDeviation(double dblStandardDeviation) {
        m_dblStandardDeviation = dblStandardDeviation;
    }

    public void setUpperBollgerBand(double dblUpperBollgerBand) {
        m_dblUpperBollgerBand = dblUpperBollgerBand;
    }

    public void setLowerBollgerBand(double dblLowerBollgerBand) {
        m_dblLowerBollgerBand = dblLowerBollgerBand;
    }

    public void setROC(float dblROC) {
        m_dblROC = dblROC;
    }

    public void println() {

    }

}
