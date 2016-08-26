/**
 *
 */
package com.fin.technical;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */
public class EMACrossover extends StockPriceInfo {
    private String m_strEMACrossover = "No";

    /**
	 *
	 */
    public EMACrossover() {

    }

    public EMACrossover(String strStockCode, StockPrice objStockPrice) {
        super(strStockCode, objStockPrice);
    }

    public String getEMACrossover() {
        return m_strEMACrossover;
    }

    public void setEMACrossover(String strEMACrossover) {
        m_strEMACrossover = strEMACrossover;
    }
}
