package com.fin.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import com.fin.technical.Constants;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

@Entity
public class StockQuote implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1594847034072009979L;

	// http://stackoverflow.com/questions/4732760/creating-springsource-tool-suite-sts-hibernate-template
	//
	// private members
	// Date,Open,High,Low,Close,TotalTradedQuantity,Adj Close
	// Input to the program
	protected long Id;

	private Date TradeDate = null;

	protected double OpenPrice = 0.0;

	protected double HighPrice = 0.0;

	protected double LowPrice = 0.0;

	protected double LastTradedPrice = 0.0;

	protected double ClosePrice = 0.0;

	protected int TotalTradedQuantity = 0;

	protected double Turnover = 0.0;

	/**
	 */
	public StockQuote() {
		super();
	}

	/**
	 * @param tradeDate
	 * @param openPrice
	 * @param highPrice
	 * @param lowPrice
	 * @param lastTradedPrice
	 * @param closePrice
	 * @param totalTradedQuantity
	 * @param turnover
	 */
	public StockQuote(Date tradeDate, double openPrice,
			double highPrice, double lowPrice, double lastTradedPrice,
			double closePrice, int totalTradedQuantity, double turnover) {
		super();
		TradeDate = tradeDate;
		OpenPrice = openPrice;
		HighPrice = highPrice;
		LowPrice = lowPrice;
		LastTradedPrice = lastTradedPrice;
		ClosePrice = closePrice;
		TotalTradedQuantity = totalTradedQuantity;
		Turnover = turnover;
	}

	/**
	 * @param id
	 * @param tradeDate
	 * @param openPrice
	 * @param highPrice
	 * @param lowPrice
	 * @param lastTradedPrice
	 * @param closePrice
	 * @param totalTradedQuantity
	 * @param turnover
	 */
	public StockQuote(long id, Date tradeDate, double openPrice,
			double highPrice, double lowPrice, double lastTradedPrice,
			double closePrice, int totalTradedQuantity, double turnover) {

		this(tradeDate, openPrice, highPrice, lowPrice, lastTradedPrice, closePrice, totalTradedQuantity, turnover);
		Id = id;
	}

	/**
	 * @param strValues
	 */
	public StockQuote(String[] strValues, int nSource) throws NumberFormatException
	{
		if(strValues != null && strValues.length > 0)
		{
			SimpleDateFormat sdfDateFormat = new SimpleDateFormat(Constants.STR_DATE_FORMAT_PATTERN);
			TradeDate = sdfDateFormat.parse(strValues[0].trim(), new ParsePosition(0));
			OpenPrice = new BigDecimal(strValues[1].trim()).doubleValue();
			HighPrice = new BigDecimal(strValues[2].trim()).doubleValue();
			LowPrice = new BigDecimal(strValues[3].trim()).doubleValue();
			LastTradedPrice = new BigDecimal(strValues[4].trim()).doubleValue();
			switch (nSource)
			{
				case 0:
					ClosePrice = new BigDecimal(strValues[4].trim()).doubleValue();
					TotalTradedQuantity = new BigDecimal(strValues[5].trim()).intValue();
					Turnover = 0.0;
					break;
				case 1:
					ClosePrice = new BigDecimal(strValues[5].trim()).doubleValue();
					TotalTradedQuantity = new BigDecimal(strValues[6].trim()).intValue();
					Turnover = new BigDecimal(strValues[7].trim()).doubleValue();
					break;
				default:
					break;
			}
		}
	}

	/**
	 * @return the lastTradedPrice
	 */
	public double getLastTradedPrice() {
		return LastTradedPrice;
	}

	/**
	 * @param lastTradedPrice the lastTradedPrice to set
	 */
	public void setLastTradedPrice(double lastTradedPrice) {
		LastTradedPrice = lastTradedPrice;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		Id = id;
	}

	/**
	 * @return the stockCode
	 */
//	public String getStockCode() {
//		return StockCode;
//	}

	/**
	 * @param stockCode the stockCode to set
	 */
//	public void setStockCode(String stockCode) {
//		StockCode = stockCode;
//	}

	/**
	 * @return the tradeDate
	 */
	public Date getTradeDate() {
		return TradeDate;
	}

	/**
	 * @param tradeDate the tradeDate to set
	 */
	public void setTradeDate(Date tradeDate) {
		TradeDate = tradeDate;
	}

	/**
	 * @return the openPrice
	 */
	public double getOpenPrice() {
		return OpenPrice;
	}

	/**
	 * @param openPrice the openPrice to set
	 */
	public void setOpenPrice(double openPrice) {
		OpenPrice = openPrice;
	}

	/**
	 * @return the highPrice
	 */
	public double getHighPrice() {
		return HighPrice;
	}

	/**
	 * @param highPrice the highPrice to set
	 */
	public void setHighPrice(double highPrice) {
		HighPrice = highPrice;
	}

	/**
	 * @return the lowPrice
	 */
	public double getLowPrice() {
		return LowPrice;
	}

	/**
	 * @param lowPrice the lowPrice to set
	 */
	public void setLowPrice(double lowPrice) {
		LowPrice = lowPrice;
	}

	/**
	 * @return the closePrice
	 */
	public double getClosePrice() {
		return ClosePrice;
	}

	/**
	 * @param closePrice the closePrice to set
	 */
	public void setClosePrice(double closePrice) {
		ClosePrice = closePrice;
	}

	/**
	 * @return the volume
	 */
	public int getTotalTradedQuantity() {
		return TotalTradedQuantity;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setTotalTradedQuantity(int volume) {
		TotalTradedQuantity = volume;
	}

	/**
	 * @return the adjClosePrice
	 */
	public double getTurnover() {
		return Turnover;
	}

	/**
	 * @param adjClosePrice the adjClosePrice to set
	 */
	public void setTurnover(double adjClosePrice) {
		Turnover = adjClosePrice;
	}


}
