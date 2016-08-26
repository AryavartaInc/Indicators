package com.fin.technical;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */

public class PivotPoint {

    protected StockPrice m_objPreviousDayStockPrice = null;

    protected StockPrice m_objCurrentDayStockPrice = null;

    protected String m_strStockCode = null;

    protected double m_dblSupport1 = 0.0;

    protected double m_dblSupport2 = 0.0;

    protected double m_dblSupport3 = 0.0;

    protected double m_dblPivotPoint = 0.0;

    protected double m_dblResistance1 = 0.0;

    protected double m_dblResistance2 = 0.0;

    protected double m_dblResistance3 = 0.0;

    public PivotPoint() {
        super();
    }

    public PivotPoint(String strStockCode, StockPrice objPreviousDayStockPrice) {
        m_objPreviousDayStockPrice = objPreviousDayStockPrice;
        m_strStockCode = strStockCode;
    }

    public PivotPoint(String strStockCode, StockPrice objPreviousDayStockPrice,
            StockPrice objCurrentDayStockPrice) {
        m_objPreviousDayStockPrice = objPreviousDayStockPrice;
        m_objCurrentDayStockPrice = objCurrentDayStockPrice;
        m_strStockCode = strStockCode;
    }

    public StockPrice getPreviousDayStockPrice() {
        return m_objPreviousDayStockPrice;
    }

    public void setPreviousDayStockPrice(StockPrice objPreviousDayStockPrice) {
        m_objPreviousDayStockPrice = objPreviousDayStockPrice;
    }

    public StockPrice getCurrentDayStockPrice() {
        return m_objCurrentDayStockPrice;
    }

    public void setCurrentDayStockPrice(StockPrice objCurrentDayStockPrice) {
        m_objCurrentDayStockPrice = objCurrentDayStockPrice;
    }

    public String getStockCode() {
        return m_strStockCode;
    }

    public double getSupport1() {
        return m_dblSupport1;
    }

    public double getSupport2() {
        return m_dblSupport2;
    }

    public double getSupport3() {
        return m_dblSupport3;
    }

    public double getPivotPoint() {
        return m_dblPivotPoint;
    }

    public double getResistance1() {
        return m_dblResistance1;
    }

    public double getResistance2() {
        return m_dblResistance2;
    }

    public double getResistance3() {
        return m_dblResistance3;
    }

    public void setStockCode(String strStockCode) {
        m_strStockCode = strStockCode;
    }

    public void setPivotPoint(double dblPivotPoint) {
        m_dblPivotPoint = dblPivotPoint;
    }

    public void setSupport1(double dblSupport1) {
        m_dblSupport1 = dblSupport1;
    }

    public void setSupport2(double dblSupport2) {
        m_dblSupport2 = dblSupport2;
    }

    public void setSupport3(double dblSupport3) {
        m_dblSupport3 = dblSupport3;
    }

    public void setResistance1(double dblResistance1) {
        m_dblResistance1 = dblResistance1;
    }

    public void setResistance2(double dblResistance2) {
        m_dblResistance2 = dblResistance2;
    }

    public void setResistance3(double dblResistance3) {
        m_dblResistance3 = dblResistance3;
    }

}
