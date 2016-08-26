package com.fin.technical;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Vector;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */

/*
 http://stockcharts.com/school/doku.php?id=chart_school:technical_indicators:accumulation_distrib

 Accumulation Distribution Line
 Introduction

 Developed by Marc Chaikin, the Accumulation Distribution Line is a volume-based
 indicator designed to measure the cumulative flow of money into and out of a
 security. Chaikin originally referred to the indicator as the Cumulative
 Money Flow Line. As with cumulative indicators, the Accumulation Distribution
 Line is a running total of each period's Money Flow Volume. First, a
 multiplier is calculated based on the relationship of the close to the
 high-low range. Second, the Money Flow Multiplier is multiplied by the
 period's volume to come up with a Money Flow Volume. A running total of
 the Money Flow Volume forms the Accumulation Distribution Line.

 Chartists can use this indicator to affirm a security's underlying trend or
 anticipate reversals when the indicator diverges from the security price.

 Calculation

 There are three steps to calculating the Accumulation Distribution Line (ADL).
 First, calculate the Money Flow Multiplier.
 Second, multiply this value by volume to find the Money Flow Volume.
 Third, create a running total of Money Flow Volume to form the Accumulation Distribution Line (ADL).


 1. Money Flow Multiplier = [(Close  -  Low) - (High - Close)] /(High - Low)

 2. Money Flow Volume = Money Flow Multiplier x Volume for the Period

 3. ADL = Previous ADL + Current Period's Money Flow Volume

 The Money Flow Multiplier fluctuates between +1 and -1. As such, it holds the key to the Money Flow Volume and the Accumulation Distribution Line. The multiplier is positive when the close is in the upper half of the high-low range and negative when in the lower half. This makes perfect sense. Buying pressure is stronger than selling pressure when prices close in the upper half of the period's range (and visa versa). The Accumulation Distribution Line rises when the multiplier is positive and falls when the multiplier is negative.
 */
public class ADLCalculator {

    public static int calculateADL(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice) {
        int nADL = 0;
        StockPrice objCurrADL = null;
        StockPrice objPrevADL = null;
        int nNumOfElem = lstHistoricalPrice.size();

        MathContext objMathContext = new MathContext(8);
        BigDecimal dblMoneyFlowMultiplier = new BigDecimal("0.00",
                objMathContext);
        BigDecimal dblMoneyFlowVolume = new BigDecimal("0.00", objMathContext);

        // Formula to calculate ADL
        // 1. Money Flow Multiplier = [(Close - Low) - (High - Close)] /(High -
        // Low)

        // 2. Money Flow Volume = Money Flow Multiplier x Volume for the Period

        // 3. ADL = Previous ADL + Current Period's Money Flow Volume
        while ((objCurrADL = (StockPrice) lstHistoricalPrice
                .elementAt(nNumOfElem - 1)) != null) {
            BigDecimal dblHighPrice = new BigDecimal(objCurrADL.m_dblHighPrice,
                    objMathContext);
            BigDecimal dblLowPrice = new BigDecimal(objCurrADL.m_dblLowPrice,
                    objMathContext);
            BigDecimal dblClosePrice = new BigDecimal(
                    objCurrADL.m_dblClosePrice, objMathContext);
            BigDecimal dblDenominator = new BigDecimal("0.00", objMathContext);
            dblDenominator = dblHighPrice.subtract(dblLowPrice); // (objCurrADL.m_dblHighPrice
                                                                 // -
                                                                 // objCurrADL.m_dblLowPrice);

            if (dblDenominator.compareTo(Constants.m_objBigDecZero) != 0) // to
                                                                          // avoid
                                                                          // divide
                                                                          // by
                                                                          // zero.
            {
                BigDecimal objBigDecTemp = new BigDecimal("0.00",
                        objMathContext);
                objBigDecTemp = dblClosePrice.subtract(dblLowPrice);
                dblMoneyFlowMultiplier = dblHighPrice.subtract(dblClosePrice);
                dblMoneyFlowMultiplier = objBigDecTemp
                        .subtract(dblMoneyFlowMultiplier);
                dblMoneyFlowMultiplier = new BigDecimal(
                        dblMoneyFlowMultiplier.doubleValue()
                                / dblDenominator.doubleValue());
                // dblMoneyFlowMultiplier = ( (objCurrADL.m_dblClosePrice -
                // objCurrADL.m_dblLowPrice) -
                // (objCurrADL.m_dblHighPrice - objCurrADL.m_dblClosePrice) ) /
                // dblDenominator;

                if (dblMoneyFlowMultiplier.intValue() > 1
                        || dblMoneyFlowMultiplier.intValue() < -1) {
                    System.out
                            .println("Money Flow Multiplier is at its extremes (+1 or -1). The multiplier is +1 when the close is on the high and -1 when the close is on the low.");
                    System.out
                            .println("However, the data retrieved from the DataSource is having some problem, that is why you will see Money Flow Multiplier > 1.0 or less than -1.0.");
                    System.out.println(strStockCode + ": "
                            + dblMoneyFlowMultiplier);
                }
            }

            dblMoneyFlowVolume = dblMoneyFlowMultiplier
                    .multiply(new BigDecimal(objCurrADL.m_nVolume));// *
                                                                    // (double)objCurrADL.m_nVolume;

            if (nNumOfElem == lstHistoricalPrice.size()) {
                nADL = dblMoneyFlowVolume.intValue();
            } else {
                objPrevADL = (StockPrice) lstHistoricalPrice
                        .elementAt(nNumOfElem);
                nADL = objPrevADL.m_nADL + dblMoneyFlowVolume.intValue();
            }
            objCurrADL.m_nADL = nADL;

            nNumOfElem--;
            if (nNumOfElem <= 0) {
                return Constants.NO_ERROR;
            }
        }
        return Constants.NO_ERROR; // Success
    }

}
