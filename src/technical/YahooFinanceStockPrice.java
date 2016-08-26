/**
 * 
 */
package com.fin.technical;

import java.math.BigDecimal;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */

public class YahooFinanceStockPrice extends StockPrice {

	/**
	 * 
	 */
	public YahooFinanceStockPrice() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param strDate
	 * @param dblOpenPrice
	 * @param dblHighPrice
	 * @param dblLowPrice
	 * @param dblClosePrice
	 * @param nVolume
	 * @param dblAdjClosePrice
	 */
	public YahooFinanceStockPrice(String strDate, double dblOpenPrice,
			double dblHighPrice, double dblLowPrice, double dblClosePrice,
			int nVolume, double dblAdjClosePrice) {
		super(strDate, dblOpenPrice, dblHighPrice, dblLowPrice, dblClosePrice,
				nVolume, dblAdjClosePrice);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param dblHighPrice
	 * @param dblLowPrice
	 * @param dblClosePrice
	 */
	public YahooFinanceStockPrice(double dblHighPrice, double dblLowPrice,
			double dblClosePrice) {
		super(dblHighPrice, dblLowPrice, dblClosePrice);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param strValues
	 * @param nSource
	 * @throws NumberFormatException
	 */
	public YahooFinanceStockPrice(String[] strValues, int nSource)
			throws NumberFormatException {
		super(strValues, nSource);
		// TODO Auto-generated constructor stub
        if (strValues != null && strValues.length > 0) {

	        m_strDate = strValues[0];
	        m_objBigDecOpenPrice = new BigDecimal(strValues[1].trim());
	        m_dblOpenPrice = m_objBigDecOpenPrice.doubleValue();
	
	        m_objBigDecHighPrice = new BigDecimal(strValues[2].trim());
	        m_dblHighPrice = m_objBigDecHighPrice.doubleValue();
	
	        m_objBigDecLowPrice = new BigDecimal(strValues[3].trim());
	        m_dblLowPrice = m_objBigDecLowPrice.doubleValue();
	
	        m_objBigDecClosePrice = new BigDecimal(strValues[4].trim());
	        m_dblClosePrice = m_objBigDecClosePrice.doubleValue();
	
	        m_objBigDecVolume = new BigDecimal(strValues[5].trim());
	        m_nVolume = m_objBigDecVolume.intValue();
	
	        m_objBigDecAdjClosePrice = m_objBigDecClosePrice;
	        m_dblAdjClosePrice = m_dblClosePrice;
	
	        m_dblTypicalPrice = (m_dblHighPrice + m_dblLowPrice + m_dblClosePrice) / 3;
        }
	}

}
