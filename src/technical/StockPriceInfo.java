package com.fin.technical;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */

public class StockPriceInfo {

    protected String m_strStockCode = null;

    protected StockPrice m_objStockPrice = null;

    public StockPriceInfo() {

    }

    public StockPriceInfo(String strStockCode, StockPrice objStockPrice) {
        m_strStockCode = strStockCode;
        m_objStockPrice = objStockPrice;
    }

    public String getStockCode() {
        return m_strStockCode;
    }

    public void setStockCode(String strStockCode) {
        m_strStockCode = strStockCode;
    }

    public StockPrice getStockPrice() {
        return m_objStockPrice;
    }

    public void setStockPrice(StockPrice objStockPrice) {
        m_objStockPrice = objStockPrice;
    }

}
