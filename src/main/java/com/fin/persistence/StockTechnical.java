package com.fin.persistence;

import java.util.Date;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public class StockTechnical extends StockQuote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7116514129294215615L;

	// Begin For RSI, intermediate as well as end result, such as EMA
	protected transient double RSIGain = 0.0;

	protected transient double RSILoss = 0.0;

	protected transient double RSIAverageGain = 0.0;

	protected transient double RSIAverageLoss = 0.0;

	protected transient double RSIRelativeStrength = 0.0;

	protected double RSI = 0.0;
	// End For RSI, intermediate as well as end result, such as EMA

	// Begin For Accumulation Distribution Line Calculation.
	protected int ADL = 0;
	// End For Accumulation Distribution Line Calculation.

	// For Simple Moving Average of 20 Days
	protected double SMA20Day = 0.0;

	// For Simple Moving Average of 50 Days
	protected double SMA50Day = 0.0;

	// For Simple Moving Average of 100 Days
	protected double SMA100Day = 0.0;

	// For Simple Moving Average of 200 Days
	protected double SMA200Day = 0.0;

	// Begin For Commodity Channel Index.
	protected transient double TypicalPrice = 0.0;

	protected transient double SMAOfTypicalPrice = 0.0;

	protected double CommodityChannelIndex = 0.0;
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
	protected transient double TrueRange = 0.0;

	// To calculate the Directional Movement System:
	// +DM = Today's High - Yesterday's High (when price moves upward)
	protected transient double DMPlus = 0.0;

	// -DM = Yesterday's Low - Today's Low (when price moves downward)
	protected transient double DMMinus = 0.0;

	// exponential moving average* of TR
	protected transient double EMAOfTrueRange = 0.0;

	// exponential moving average* of +DM
	protected transient double EMAOfDMPlus = 0.0;

	// exponential moving average* of -DM
	protected transient double EMAOfDMMinus = 0.0;

	// Next, calculate the Directional Indicators:
	// +DM14 divided by TR14
	protected double DIPlus = 0.0;

	// -DM14 divided by TR14
	protected double DIMinus = 0.0;

	// Record the difference between +DI14 and -DI14 as a positive number.
	protected transient double DIDiff = 0.0;

	protected transient double DISum = 0.0;

	// Calculate the Directional Index (DX):
	// DX = DI Difference divided by the sum of +DI14 and -DI14
	protected transient double DX = 0.0;

	// ADX = the exponential moving average* of DX
	protected double ADX = 0.0;

	// Begin For Bollinger Band.
	protected transient double DeviationSquared = 0.0;

	protected transient double SMAOfDeviationSquared = 0.0;

	protected transient double StandardDeviation = 0.0;

	// BOLL = the Upper Bandwidth of Bollinger Band
	protected double UpperBollgerBand = 0.0;

	// BOLL = the Lower Bandwidth of Bollinger Band

	// Begin Exponential Moving Average
	protected double LowerBollgerBand = 0.0;

	protected double EMA3Day = 0.0; // e.g. 3 Days Exponential Moving Average

	protected double EMA15Day = 0.0; // e.g. 15 Days Exponential Moving Average

	protected double EMA5Day = 0.0; // e.g. 5 Days Exponential Moving Average

	protected double EMA20Day = 0.0; // e.g. 20 Days Exponential Moving Average

	protected double EMA12Day = 0.0; // //e.g. 12 Days Exponential Moving
										// Average in case of MACD where 12,9
										// and 26 Days EMA are considered.

	protected double EMA9Day = 0.0; // //e.g. 9 Days Exponential Moving Average
									// in case of MACD where 12,9 and 26 Days
									// EMA are considered.

	protected double EMA26Day = 0.0; // //e.g. 26 Days Exponential Moving
										// Average in case of MACD where 12,9
										// and 26 Days EMA are considered.

	// Based on 3EMA-15 EMA or any combination like 12EMA-26 EMA, Crossover
	protected String EMATrend = "";

	// Action specifies following at the moment.
	// "Book Profit"
	// "Hold"
	// "Go Long"
	// "Go Short"
	protected String EMARecommendation = "";

	// End Exponential Moving Average

	public StockTechnical() {
		// TODO Auto-generated constructor stub
	}

	public StockTechnical(Date tradeDate, double openPrice, double highPrice,
			double lowPrice, double lastTradedPrice, double closePrice,
			int totalTradedQuantity, double turnover) {
		super(tradeDate, openPrice, highPrice, lowPrice, lastTradedPrice,
				closePrice, totalTradedQuantity, turnover);
		// TODO Auto-generated constructor stub
	}

	public StockTechnical(long id, Date tradeDate, double openPrice,
			double highPrice, double lowPrice, double lastTradedPrice,
			double closePrice, int totalTradedQuantity, double turnover) {
		super(id, tradeDate, openPrice, highPrice, lowPrice, lastTradedPrice,
				closePrice, totalTradedQuantity, turnover);
		// TODO Auto-generated constructor stub
	}

	public StockTechnical(String[] strValues, int nSource)
			throws NumberFormatException {
		super(strValues, nSource);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the rSIGain
	 */
	public double getRSIGain() {
		return RSIGain;
	}

	/**
	 * @return the rSILoss
	 */
	public double getRSILoss() {
		return RSILoss;
	}

	/**
	 * @return the rSIAverageGain
	 */
	public double getRSIAverageGain() {
		return RSIAverageGain;
	}

	/**
	 * @return the rSIAverageLoss
	 */
	public double getRSIAverageLoss() {
		return RSIAverageLoss;
	}

	/**
	 * @return the rSIRelativeStrength
	 */
	public double getRSIRelativeStrength() {
		return RSIRelativeStrength;
	}

	/**
	 * @return the rSI
	 */
	public double getRSI() {
		return RSI;
	}

	/**
	 * @return the aDL
	 */
	public int getADL() {
		return ADL;
	}

	/**
	 * @return the sMA20Day
	 */
	public double getSMA20Day() {
		return SMA20Day;
	}

	/**
	 * @return the sMA50Day
	 */
	public double getSMA50Day() {
		return SMA50Day;
	}

	/**
	 * @return the sMA100Day
	 */
	public double getSMA100Day() {
		return SMA100Day;
	}

	/**
	 * @return the sMA200Day
	 */
	public double getSMA200Day() {
		return SMA200Day;
	}

	/**
	 * @return the typicalPrice
	 */
	public double getTypicalPrice() {
		return TypicalPrice;
	}

	/**
	 * @return the sMAOfTypicalPrice
	 */
	public double getSMAOfTypicalPrice() {
		return SMAOfTypicalPrice;
	}

	/**
	 * @return the commodityChannelIndex
	 */
	public double getCommodityChannelIndex() {
		return CommodityChannelIndex;
	}

	/**
	 * @return the trueRange
	 */
	public double getTrueRange() {
		return TrueRange;
	}

	/**
	 * @return the dMPlus
	 */
	public double getDMPlus() {
		return DMPlus;
	}

	/**
	 * @return the dMMinus
	 */
	public double getDMMinus() {
		return DMMinus;
	}

	/**
	 * @return the eMAOfTrueRange
	 */
	public double getEMAOfTrueRange() {
		return EMAOfTrueRange;
	}

	/**
	 * @return the eMAOfDMPlus
	 */
	public double getEMAOfDMPlus() {
		return EMAOfDMPlus;
	}

	/**
	 * @return the eMAOfDMMinus
	 */
	public double getEMAOfDMMinus() {
		return EMAOfDMMinus;
	}

	/**
	 * @return the dIPlus
	 */
	public double getDIPlus() {
		return DIPlus;
	}

	/**
	 * @return the dIMinus
	 */
	public double getDIMinus() {
		return DIMinus;
	}

	/**
	 * @return the dIDiff
	 */
	public double getDIDiff() {
		return DIDiff;
	}

	/**
	 * @return the dISum
	 */
	public double getDISum() {
		return DISum;
	}

	/**
	 * @return the dX
	 */
	public double getDX() {
		return DX;
	}

	/**
	 * @return the aDX
	 */
	public double getADX() {
		return ADX;
	}

	/**
	 * @return the deviationSquared
	 */
	public double getDeviationSquared() {
		return DeviationSquared;
	}

	/**
	 * @return the sMAOfDeviationSquared
	 */
	public double getSMAOfDeviationSquared() {
		return SMAOfDeviationSquared;
	}

	/**
	 * @return the standardDeviation
	 */
	public double getStandardDeviation() {
		return StandardDeviation;
	}

	/**
	 * @return the upperBollgerBand
	 */
	public double getUpperBollgerBand() {
		return UpperBollgerBand;
	}

	/**
	 * @return the lowerBollgerBand
	 */
	public double getLowerBollgerBand() {
		return LowerBollgerBand;
	}

	/**
	 * @param rSIGain
	 *            the rSIGain to set
	 */
	public void setRSIGain(double rSIGain) {
		RSIGain = rSIGain;
	}

	/**
	 * @param rSILoss
	 *            the rSILoss to set
	 */
	public void setRSILoss(double rSILoss) {
		RSILoss = rSILoss;
	}

	/**
	 * @param rSIAverageGain
	 *            the rSIAverageGain to set
	 */
	public void setRSIAverageGain(double rSIAverageGain) {
		RSIAverageGain = rSIAverageGain;
	}

	/**
	 * @param rSIAverageLoss
	 *            the rSIAverageLoss to set
	 */
	public void setRSIAverageLoss(double rSIAverageLoss) {
		RSIAverageLoss = rSIAverageLoss;
	}

	/**
	 * @param rSIRelativeStrength
	 *            the rSIRelativeStrength to set
	 */
	public void setRSIRelativeStrength(double rSIRelativeStrength) {
		RSIRelativeStrength = rSIRelativeStrength;
	}

	/**
	 * @param rSI
	 *            the rSI to set
	 */
	public void setRSI(double rSI) {
		RSI = rSI;
	}

	/**
	 * @param aDL
	 *            the aDL to set
	 */
	public void setADL(int aDL) {
		ADL = aDL;
	}

	/**
	 * @param sMA20Day
	 *            the sMA20Day to set
	 */
	public void setSMA20Day(double sMA20Day) {
		SMA20Day = sMA20Day;
	}

	/**
	 * @param sMA50Day
	 *            the sMA50Day to set
	 */
	public void setSMA50Day(double sMA50Day) {
		SMA50Day = sMA50Day;
	}

	/**
	 * @param sMA100Day
	 *            the sMA100Day to set
	 */
	public void setSMA100Day(double sMA100Day) {
		SMA100Day = sMA100Day;
	}

	/**
	 * @param sMA200Day
	 *            the sMA200Day to set
	 */
	public void setSMA200Day(double sMA200Day) {
		SMA200Day = sMA200Day;
	}

	/**
	 * @param typicalPrice
	 *            the typicalPrice to set
	 */
	public void setTypicalPrice(double typicalPrice) {
		TypicalPrice = typicalPrice;
	}

	/**
	 * @param sMAOfTypicalPrice
	 *            the sMAOfTypicalPrice to set
	 */
	public void setSMAOfTypicalPrice(double sMAOfTypicalPrice) {
		SMAOfTypicalPrice = sMAOfTypicalPrice;
	}

	/**
	 * @param commodityChannelIndex
	 *            the commodityChannelIndex to set
	 */
	public void setCommodityChannelIndex(double commodityChannelIndex) {
		CommodityChannelIndex = commodityChannelIndex;
	}

	/**
	 * @param trueRange
	 *            the trueRange to set
	 */
	public void setTrueRange(double trueRange) {
		TrueRange = trueRange;
	}

	/**
	 * @param dMPlus
	 *            the dMPlus to set
	 */
	public void setDMPlus(double dMPlus) {
		DMPlus = dMPlus;
	}

	/**
	 * @param dMMinus
	 *            the dMMinus to set
	 */
	public void setDMMinus(double dMMinus) {
		DMMinus = dMMinus;
	}

	/**
	 * @param eMAOfTrueRange
	 *            the eMAOfTrueRange to set
	 */
	public void setEMAOfTrueRange(double eMAOfTrueRange) {
		EMAOfTrueRange = eMAOfTrueRange;
	}

	/**
	 * @param eMAOfDMPlus
	 *            the eMAOfDMPlus to set
	 */
	public void setEMAOfDMPlus(double eMAOfDMPlus) {
		EMAOfDMPlus = eMAOfDMPlus;
	}

	/**
	 * @param eMAOfDMMinus
	 *            the eMAOfDMMinus to set
	 */
	public void setEMAOfDMMinus(double eMAOfDMMinus) {
		EMAOfDMMinus = eMAOfDMMinus;
	}

	/**
	 * @param dIPlus
	 *            the dIPlus to set
	 */
	public void setDIPlus(double dIPlus) {
		DIPlus = dIPlus;
	}

	/**
	 * @param dIMinus
	 *            the dIMinus to set
	 */
	public void setDIMinus(double dIMinus) {
		DIMinus = dIMinus;
	}

	/**
	 * @param dIDiff
	 *            the dIDiff to set
	 */
	public void setDIDiff(double dIDiff) {
		DIDiff = dIDiff;
	}

	/**
	 * @param dISum
	 *            the dISum to set
	 */
	public void setDISum(double dISum) {
		DISum = dISum;
	}

	/**
	 * @param dX
	 *            the dX to set
	 */
	public void setDX(double dX) {
		DX = dX;
	}

	/**
	 * @param aDX
	 *            the aDX to set
	 */
	public void setADX(double aDX) {
		ADX = aDX;
	}

	/**
	 * @param deviationSquared
	 *            the deviationSquared to set
	 */
	public void setDeviationSquared(double deviationSquared) {
		DeviationSquared = deviationSquared;
	}

	/**
	 * @param sMAOfDeviationSquared
	 *            the sMAOfDeviationSquared to set
	 */
	public void setSMAOfDeviationSquared(double sMAOfDeviationSquared) {
		SMAOfDeviationSquared = sMAOfDeviationSquared;
	}

	/**
	 * @param standardDeviation
	 *            the standardDeviation to set
	 */
	public void setStandardDeviation(double standardDeviation) {
		StandardDeviation = standardDeviation;
	}

	/**
	 * @param upperBollgerBand
	 *            the upperBollgerBand to set
	 */
	public void setUpperBollgerBand(double upperBollgerBand) {
		UpperBollgerBand = upperBollgerBand;
	}

	/**
	 * @param lowerBollgerBand
	 *            the lowerBollgerBand to set
	 */
	public void setLowerBollgerBand(double lowerBollgerBand) {
		LowerBollgerBand = lowerBollgerBand;
	}

	/**
	 * @return the eMA3Day
	 */
	public double getEMA3Day() {
		return EMA3Day;
	}

	/**
	 * @return the eMA15Day
	 */
	public double getEMA15Day() {
		return EMA15Day;
	}

	/**
	 * @return the eMA5Day
	 */
	public double getEMA5Day() {
		return EMA5Day;
	}

	/**
	 * @return the eMA20Day
	 */
	public double getEMA20Day() {
		return EMA20Day;
	}

	/**
	 * @return the eMA12Day
	 */
	public double getEMA12Day() {
		return EMA12Day;
	}

	/**
	 * @return the eMA9Day
	 */
	public double getEMA9Day() {
		return EMA9Day;
	}

	/**
	 * @return the eMA26Day
	 */
	public double getEMA26Day() {
		return EMA26Day;
	}

	/**
	 * @return the eMATrend
	 */
	public String getEMATrend() {
		return EMATrend;
	}

	/**
	 * @return the eMARecommendation
	 */
	public String getEMARecommendation() {
		return EMARecommendation;
	}

	/**
	 * @param eMA3Day
	 *            the eMA3Day to set
	 */
	public void setEMA3Day(double eMA3Day) {
		EMA3Day = eMA3Day;
	}

	/**
	 * @param eMA15Day
	 *            the eMA15Day to set
	 */
	public void setEMA15Day(double eMA15Day) {
		EMA15Day = eMA15Day;
	}

	/**
	 * @param eMA5Day
	 *            the eMA5Day to set
	 */
	public void setEMA5Day(double eMA5Day) {
		EMA5Day = eMA5Day;
	}

	/**
	 * @param eMA20Day
	 *            the eMA20Day to set
	 */
	public void setEMA20Day(double eMA20Day) {
		EMA20Day = eMA20Day;
	}

	/**
	 * @param eMA12Day
	 *            the eMA12Day to set
	 */
	public void setEMA12Day(double eMA12Day) {
		EMA12Day = eMA12Day;
	}

	/**
	 * @param eMA9Day
	 *            the eMA9Day to set
	 */
	public void setEMA9Day(double eMA9Day) {
		EMA9Day = eMA9Day;
	}

	/**
	 * @param eMA26Day
	 *            the eMA26Day to set
	 */
	public void setEMA26Day(double eMA26Day) {
		EMA26Day = eMA26Day;
	}

	/**
	 * @param eMATrend
	 *            the eMATrend to set
	 */
	public void setEMATrend(String eMATrend) {
		EMATrend = eMATrend;
	}

	/**
	 * @param eMARecommendation
	 *            the eMARecommendation to set
	 */
	public void setEMARecommendation(String eMARecommendation) {
		EMARecommendation = eMARecommendation;
	}

}
