package com.fin.technical;

import java.util.ArrayList;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public class PivotPointCalculator {
    public static ArrayList<PivotPoint> m_objListPivotPoint = new ArrayList<PivotPoint>();

    public static ArrayList<PivotPoint> m_objListFibonacciPivotPoint = new ArrayList<PivotPoint>();

    public static StringBuffer m_strBufProgOutput = new StringBuffer();

    // the no-agrs constructor
    public PivotPointCalculator() {

    }

    public static PivotPoint calculatePivotPoints(String strStockCode,
            StockPrice objPreviousDayPrice, StockPrice objCurrentDayPrice) {
        if (strStockCode == null || objPreviousDayPrice == null) {
            return null;
        }
        double dblPreviousDayHighPrice = objPreviousDayPrice.getHighPrice() * 1;
        double dblPreviousDayLowPrice = objPreviousDayPrice.getLowPrice() * 1;
        double dblPreviousDayClosePrice = objPreviousDayPrice.getLowPrice() * 1;
        PivotPoint objPP = new PivotPoint(strStockCode, objPreviousDayPrice,
                objCurrentDayPrice);

        // Formula to calculate Pivot Point
        // http://in.answers.yahoo.com/question/index?qid=20100115102615AAkvg0l
        // Resistance 3 = High + 2*(Pivot - Low)
        // Resistance 2 = Pivot + (R1 - S1)
        // Resistance 1 = 2 * Pivot - Low
        // Pivot Point = ( High + Close + Low )/3
        // Support 1 = 2 * Pivot - High
        // Support 2 = Pivot - (R1 - S1)
        // Support 3 = Low - 2*(High - Pivot)

        // Pivot Point = ( High + Close + Low )/3

        double dblPivotPoint = ((dblPreviousDayHighPrice
                + dblPreviousDayLowPrice + dblPreviousDayClosePrice) / 3)
                * Math.pow(10, 4) / 10000;
        objPP.setPivotPoint(dblPivotPoint);
        // Resistance 1 = 2 * Pivot - Low
        double dblResistance1 = (2 * dblPivotPoint - dblPreviousDayLowPrice)
                * Math.pow(10, 4) / 10000;
        objPP.setResistance1(dblResistance1);
        // Support 1 = 2 * Pivot - High
        double dblSupport1 = (2 * dblPivotPoint - dblPreviousDayHighPrice)
                * Math.pow(10, 4) / 10000;
        objPP.setSupport1(dblSupport1);
        // Resistance 2 = Pivot + (R1 - S1)
        double dblResistance2 = (dblPivotPoint + dblResistance1 - dblSupport1)
                * Math.pow(10, 4) / 10000;
        objPP.setResistance2(dblResistance2);
        // Support 2 = Pivot - (R1 - S1)
        double dblSupport2 = (dblPivotPoint - (dblResistance1 - dblSupport1))
                * Math.pow(10, 4) / 10000;
        objPP.setSupport2(dblSupport2);
        // Resistance 3 = High + 2*(Pivot - Low)
        double dblResistance3 = (dblPreviousDayHighPrice + (2 * (dblPivotPoint - dblPreviousDayLowPrice)))
                * Math.pow(10, 4) / 10000;
        objPP.setResistance3(dblResistance3);
        // Support 3 = Low - 2*(High - Pivot)
        double dblSupport3 = (dblPreviousDayLowPrice - (2 * (dblPreviousDayHighPrice - dblPivotPoint)))
                * Math.pow(10, 4) / 10000;
        objPP.setSupport3(dblSupport3);

        // public static final String STR_PIVOT_POINT_REPORT_HEADER =
        // "  Stock-Code,  High,  Low,  Close,  S1,  S2,  S3,  Pivot,  R1,  R2,  R3 \n";
        return objPP; // Success
    }

    public static PivotPoint calculateFibonacciPivotPoints(String strStockCode,
            StockPrice objPreviousDayPrice, StockPrice objCurrentDayPrice) {
        if (strStockCode == null || objPreviousDayPrice == null) {
            return null;
        }
        double dblPreviousDayHighPrice = objPreviousDayPrice.getHighPrice() * 1;
        double dblPreviousDayLowPrice = objPreviousDayPrice.getLowPrice() * 1;
        double dblPreviousDayClosePrice = objPreviousDayPrice.getLowPrice() * 1;
        PivotPoint objPP = new PivotPoint(strStockCode, objPreviousDayPrice,
                objCurrentDayPrice);

        // Pivot Point = ( High + Close + Low )/3
        double dblPivotPoint = ((dblPreviousDayHighPrice
                + dblPreviousDayLowPrice + dblPreviousDayClosePrice) / 3)
                * Math.pow(10, 4) / 10000;
        objPP.setPivotPoint(dblPivotPoint);
        double dblRange = (dblPreviousDayHighPrice - dblPreviousDayLowPrice)
                * Math.pow(10, 4) / 10000;
        // Resistance 1
        double dblFibonacciR1 = (dblPivotPoint + (dblRange * 0.38))
                * Math.pow(10, 4) / 10000;
        objPP.setResistance1(dblFibonacciR1);
        // Support 1
        double dblFibonacciS1 = (dblPivotPoint - (dblRange * 0.38))
                * Math.pow(10, 4) / 10000;
        objPP.setSupport1(dblFibonacciS1);
        // Resistance 2
        double dblFibonacciR2 = (dblPivotPoint + (dblRange * 0.62))
                * Math.pow(10, 4) / 10000;
        objPP.setResistance2(dblFibonacciR2);
        // Support 2
        double dblFibonacciS2 = (dblPivotPoint - (dblRange * 0.62))
                * Math.pow(10, 4) / 10000;
        objPP.setSupport2(dblFibonacciS2);
        // Resistance 3
        double dblFibonacciR3 = (dblPivotPoint + (dblRange * 0.98))
                * Math.pow(10, 4) / 10000;
        objPP.setResistance3(dblFibonacciR3);
        // Support 3
        double dblFibonacciS3 = (dblPivotPoint - (dblRange * 0.98))
                * Math.pow(10, 4) / 10000;
        objPP.setSupport3(dblFibonacciS3);

        // public static final String STR_PIVOT_POINT_REPORT_HEADER =
        // "  Stock-Code,  High,  Low,  Close,  Fibonacci-S1,  Fibonacci-S2,  Fibonacci-S3,  Range,  Fibonacci-R1,  Fibonacci-R2,  Fibonacci-R3 \n";
        return objPP; // Success
    }
}
