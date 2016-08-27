package com.fin.persistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

@Entity
public class KOTAKBANK extends StockTechnical implements Serializable {

	// http://stackoverflow.com/questions/4732760/creating-springsource-tool-suite-sts-hibernate-template
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 889545723352330387L;

	/**
	 */
	public KOTAKBANK() {
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
	public KOTAKBANK(Date tradeDate, double openPrice,
			double highPrice, double lowPrice, double lastTradedPrice,
			double closePrice, int totalTradedQuantity, double turnover) {
		
		super(tradeDate, openPrice, highPrice, lowPrice, lastTradedPrice,
				closePrice, totalTradedQuantity, turnover);
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
	public KOTAKBANK(long id, Date tradeDate, double openPrice,
			double highPrice, double lowPrice, double lastTradedPrice,
			double closePrice, int totalTradedQuantity, double turnover) {
		
		super(id, tradeDate, openPrice, highPrice, lowPrice, lastTradedPrice, closePrice, totalTradedQuantity, turnover);	
	}

	/**
	 * @param strValues
	 */
	public KOTAKBANK(String[] strValues, int nSource) throws NumberFormatException {
		super(strValues, nSource);
	}	
}
