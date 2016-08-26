package com.fin.technical;

import java.util.Vector;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */

public class ROCCalculator {

    public ROCCalculator() {
        // TODO Auto-generated constructor stub
    }

    // ROC = [(Close - Close n periods ago) / (Close n periods ago)] * 100

    public static int calculateROC(String strStockCode, Vector<StockPrice> lstHistoricalPrice) {

        int nNumOfElem = lstHistoricalPrice.size();
        StockPrice objNDaysAgoStockPrice = null;
        StockPrice objCurrDayStockPrice = null;
        int nIdx = 0;
        if (nNumOfElem <= 0) {
            return Constants.ERROR_EMPTY_LIST;
        }
        try {
            Util objUtil = Util.getInstance();
            int nPeriod = objUtil.getROCDurationValue();
            while ((objCurrDayStockPrice = (StockPrice) lstHistoricalPrice
                    .elementAt(nNumOfElem - 1)) != null) {
                nNumOfElem--;
                nIdx++;
                if(nIdx > nPeriod) {
                    objNDaysAgoStockPrice = (StockPrice) lstHistoricalPrice.elementAt(nNumOfElem + nPeriod);
                    double dblTodayClosingPrice = objCurrDayStockPrice.getClosePrice();
                    double dblNDaysAgoClosingPrice = objNDaysAgoStockPrice.getClosePrice();
                    if(dblNDaysAgoClosingPrice > 0.00) {
                        objCurrDayStockPrice.m_dblROC = (((float)dblTodayClosingPrice - (float)dblNDaysAgoClosingPrice) / (float)dblNDaysAgoClosingPrice) * 100;
                    }
                }
                if (nNumOfElem == 0) {
                    return Constants.NO_ERROR;
                }
            }
        } catch(Exception excep) {
            excep.printStackTrace();
        }
        return Constants.NO_ERROR; // Success
    }
}
