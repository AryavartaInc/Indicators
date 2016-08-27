package com.fin.technical;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public class FibonacciPivotPoint {

    private double m_dblRange = 0.0;

    protected String m_strStockCode = null;

    protected PivotPoint m_objPivotPoint = null;

    public FibonacciPivotPoint() {
        super();
    }

    public FibonacciPivotPoint(String strStockCode, PivotPoint objPivotPoint) {
        m_objPivotPoint = objPivotPoint;
        m_strStockCode = strStockCode;
    }

    public double getRange() {
        return m_dblRange;
    }

    public void setRange(double dblRange) {
        m_dblRange = dblRange;
    }

    public PivotPoint getPivotPoint() {
        return m_objPivotPoint;
    }

}
