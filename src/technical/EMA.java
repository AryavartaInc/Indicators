package com.fin.technical;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */

public class EMA {
    protected double m_dblShortDurationEMA = 0.0; // e.g. 3 Days Exponential
                                                  // Moving Average

    protected double m_dblMediumDurationEMA = 0.0; // //e.g. 9 Days Exponential
                                                   // Moving Average in case of
                                                   // MACD where 12,9 and 26
                                                   // Days EMA are considered.

    protected double m_dblLongDurationEMA = 0.0; // e.g. 15 Days Exponential
                                                 // Moving Average

    // Based on 3EMA-15 EMA or any combination like 12EMA-26 EMA, Crossover
    protected String m_strTrend = "";

    // Action specifies following at the moment.
    // "Book Profit"
    // "Hold"
    // "Go Long"
    // "Go Short"
    protected String m_strAction = "";

    public EMA() {
        m_dblShortDurationEMA = 0.0;
        m_dblMediumDurationEMA = 0.0;
        m_dblLongDurationEMA = 0.0;

    }

    public double getShortDurationEMA() {
        return m_dblShortDurationEMA;
    }

    public void setShortDurationEMA(double dblShortDurationEMA) {
        m_dblShortDurationEMA = dblShortDurationEMA;
    }

    public double getMediumDurationEMA() {
        return m_dblMediumDurationEMA;
    }

    public void setMediumDurationEMA(double dblMediumDurationEMA) {
        m_dblMediumDurationEMA = dblMediumDurationEMA;
    }

    public double getLongDurationEMA() {
        return m_dblLongDurationEMA;
    }

    public void setLongDurationEMA(double dblLongDurationEMA) {
        m_dblLongDurationEMA = dblLongDurationEMA;
    }

    public String getTrend() {
        return m_strTrend;
    }

    public void setTrend(String strTrend) {
        m_strTrend = strTrend;
    }

    public String getAction() {
        return m_strAction;
    }

    public void setAction(String strAction) {
        m_strAction = strAction;
    }

}
