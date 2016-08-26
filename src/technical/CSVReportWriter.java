package com.fin.technical;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Vector;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */

public class CSVReportWriter extends ReportWriter {
    // Apache POI - the Java API for Microsoft Documents
    // http://poi.apache.org/

    protected CSVReportWriter() {

    }

    public void generateEMAReport(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice) {
        if (m_objUtil.getGenStockEMAReportValue() == 0) {
            return;
        }
        int nSizeOfList = lstHistoricalPrice.size();
        if (lstHistoricalPrice == null || nSizeOfList == 0) {
            return;
        }
        int nIdx = 0;
        StockPrice objCurrSIPH = null;
        StringBuffer strBufProgOutput = new StringBuffer(
                getHeaderForStockTechnicals());

        while ((nIdx < nSizeOfList)
                && (objCurrSIPH = (StockPrice) lstHistoricalPrice.get(nIdx)) != null) {
            StringBuffer strBufLine = new StringBuffer();
            String strDate = objCurrSIPH.getDate().replace(", ", "-");
            strBufLine.append(strDate).append(Constants.STR_COMMA_SEPARATOR);
            strBufLine
                    .append(String.format("%.2f", objCurrSIPH.getOpenPrice()))
                    .append(Constants.STR_COMMA_SEPARATOR);
            strBufLine
                    .append(String.format("%.2f", objCurrSIPH.getHighPrice()))
                    .append(Constants.STR_COMMA_SEPARATOR);
            strBufLine.append(String.format("%.2f", objCurrSIPH.getLowPrice()))
                    .append(Constants.STR_COMMA_SEPARATOR);
            strBufLine.append(
                    String.format("%.2f", objCurrSIPH.getClosePrice())).append(
                    Constants.STR_COMMA_SEPARATOR);
            strBufLine.append(objCurrSIPH.getVolume()).append(
                    Constants.STR_COMMA_SEPARATOR);
            // Bollinger Band
            if (m_objUtil.getGenBollReportValue() == 1) {
                if (m_objUtil.getGenBollDebugInfoValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f",
                                    objCurrSIPH.getDeviationSquared())).append(
                            Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objCurrSIPH
                                    .get20DaysSMAOfDeviationSquared())).append(
                            Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f",
                                    objCurrSIPH.getStandardDeviation()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                strBufLine.append(
                        String.format("%.2f - %.2f",
                                objCurrSIPH.getUpperBollgerBand(),
                                objCurrSIPH.getLowerBollgerBand())).append(
                        Constants.STR_COMMA_SEPARATOR);
            }

            // SMAs
            if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                strBufLine.append(
                        String.format("%.2f", objCurrSIPH.get20DaysSMA()))
                        .append(Constants.STR_COMMA_SEPARATOR);
            }
            if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                strBufLine.append(
                        String.format("%.2f", objCurrSIPH.get50DaysSMA()))
                        .append(Constants.STR_COMMA_SEPARATOR);
            }
            if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                strBufLine.append(
                        String.format("%.2f", objCurrSIPH.get200DaysSMA()))
                        .append(Constants.STR_COMMA_SEPARATOR);
            }
            // RSI
            if (m_objUtil.getGenRSIReportValue() == 1) {
                if (m_objUtil.getGenRSIDebugInfo() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objCurrSIPH.getGain()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objCurrSIPH.getLoss()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine
                            .append(String.format("%.2f",
                                    objCurrSIPH.getAverageGain())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                    strBufLine
                            .append(String.format("%.2f",
                                    objCurrSIPH.getAverageLoss())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f",
                                    objCurrSIPH.getRelativeStrength())).append(
                            Constants.STR_COMMA_SEPARATOR);
                }
                strBufLine.append(String.format("%.2f", objCurrSIPH.getRSI()))
                        .append(Constants.STR_COMMA_SEPARATOR);
            }
            //
            // ADL: Accumulation Distribution Line
            if (m_objUtil.getGenADLReportValue() == 1) {
                strBufLine.append(objCurrSIPH.getADL()).append(
                        Constants.STR_COMMA_SEPARATOR);
            }
            // CCI: Commodity Channel Index
            if (m_objUtil.getGenCCIReportValue() == 1) {
                strBufLine.append(
                        String.format("%.2f",
                                objCurrSIPH.getCommodityChannelIndex()))
                        .append(Constants.STR_COMMA_SEPARATOR);
            }
            // For Average Directional Index (ADX)
            if (m_objUtil.getGenADXReportValue() == 1) {
                if (m_objUtil.getGenADXDebugInfo() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objCurrSIPH.getTrueRange()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objCurrSIPH.getDMPlus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objCurrSIPH.getDMMinus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f",
                                    objCurrSIPH.getEMAOfTrueRange())).append(
                            Constants.STR_COMMA_SEPARATOR);
                    strBufLine
                            .append(String.format("%.2f",
                                    objCurrSIPH.getEMAOfDMPlus())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                    strBufLine
                            .append(String.format("%.2f",
                                    objCurrSIPH.getEMAOfDMMinus())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }

                strBufLine.append(
                        String.format("%.2f", objCurrSIPH.getDIPlus())).append(
                        Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objCurrSIPH.getDIMinus()))
                        .append(Constants.STR_COMMA_SEPARATOR);

                if (m_objUtil.getGenADXDebugInfo() == 1) {

                    strBufLine.append(
                            String.format("%.2f", objCurrSIPH.getDIDiff()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objCurrSIPH.getDISum()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objCurrSIPH.getDX())).append(
                            Constants.STR_COMMA_SEPARATOR);
                }
                strBufLine.append(String.format("%.2f", objCurrSIPH.getADX()))
                        .append(Constants.STR_COMMA_SEPARATOR);
            }
            // For Exponential Moving Average (EMA)
            double dblShortDurationEMA = 0.00;
            double dblLongDurationEMA = 0.00;
            EMA objEMATemp = null;
            String strCurrentTrend = "";
            String strAction = null;
            String[] strEMADurationValues = m_objUtil.getEMADurationValue();
            for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                objEMATemp = ((EMA) objCurrSIPH.getEMAList().get(
                        strEMADurationValues[nEMAIdx]));
                if (objEMATemp != null) {
                    dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                    dblLongDurationEMA = objEMATemp.getLongDurationEMA();
                    strBufLine.append(
                            String.format("%.2f", dblShortDurationEMA)).append(
                            Constants.STR_COMMA_SEPARATOR);
                    strBufLine
                            .append(String.format("%.2f", dblLongDurationEMA))
                            .append(Constants.STR_COMMA_SEPARATOR);

                    // Is this the preferred EMA Combo?
                    if (m_objUtil.getPreferredEMAComboValue().equalsIgnoreCase(
                            strEMADurationValues[nEMAIdx])
                            || m_objUtil.getPreferredEMAComboValue().length() == 0) {
                        strCurrentTrend = objEMATemp.getTrend();
                        strAction = objEMATemp.getAction();
                        strBufLine.append(strCurrentTrend).append(
                                Constants.STR_COMMA_SEPARATOR);
                        strBufLine.append(strAction).append(
                                Constants.STR_COMMA_SEPARATOR);
                    }

                }
            }
            if (m_objUtil.getGenROCReportValue() == 1) {
                strBufLine.append(
                        String.format("%.2f", objCurrSIPH.m_dblROC))
                        .append(Constants.STR_COMMA_SEPARATOR);
            }

            strBufLine.append(System.getProperty("line.separator"));
            strBufProgOutput.append(strBufLine);
            strBufLine = null;
            nIdx++;
        }

        FileWriter fwObj = null;
        BufferedWriter bwOutputStream = null;
        try {
            StringBuffer strBufFileName = new StringBuffer(m_strPath);
            strBufFileName.append(Util.m_strFileSeparator);

            if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                    Constants.STR_STOCK_CODE_DATA_SOURCE_YAHOO) == true) {
                strBufFileName.append(strStockCode).append(
                        Constants.STR_CSV_EMA_REPORT_FILE);
            } else if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                    Constants.STR_STOCK_CODE_DATA_SOURCE_GOOGLE) == true) {
                strStockCode = strStockCode.replace(':', '.');
                strBufFileName.append(strStockCode).append(
                        Constants.STR_CSV_EMA_REPORT_FILE);
            } else if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                    Constants.STR_STOCK_CODE_DATA_SOURCE_NSEINDIA) == true) {
                strBufFileName.append(strStockCode).append(
                        Constants.STR_CSV_EMA_REPORT_FILE);
            }

            // File fProgOutput = new File(strBufFileName.toString());
            // fwObj = new FileWriter(fProgOutput);
            // bwOutputStream = new BufferedWriter(fwObj);
            // bwOutputStream.write(strBufProgOutput.toString());
            // fwObj.write(strBufProgOutput.toString());

            Charset charset = Charset.forName("US-ASCII");
            Path file = Paths.get(strBufFileName.toString());
            BufferedWriter writer = Files.newBufferedWriter(file, charset);
            try {
                writer.write(strBufProgOutput.toString(), 0, strBufProgOutput
                        .toString().length());
                writer.flush();
                writer.close();
            } catch (IOException ioExcep) {
                System.err.format("IOException: %s%n", ioExcep);
            }
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } catch (SecurityException seExcep) {
            seExcep.printStackTrace();
        } finally {
            try {
                if (fwObj != null && bwOutputStream != null) {
                    bwOutputStream.flush();
                    fwObj.flush();
                    bwOutputStream.close();
                    fwObj.close();
                }
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace();
            }
        }
    }

    public void generateEMACrossoverReport() throws RowsExceededException,
            WriteException {
        if (m_objUtil.getGenMasterEMAReportValue() == 0) {
            return;
        }
        FileWriter fwObj = null;
        BufferedWriter bwOutputStream = null;
        try {
            File fProgOutput = new File(getReportFileNameForEMACrossover());
            fwObj = new FileWriter(fProgOutput);

            // The Report file Header.
            StringBuffer strBufLine = new StringBuffer(
                    getHeaderForEMACrossover());

            // Actual file content.
            // while (EMACalculator.m_objListEMACrossover != null &&
            // !EMACalculator.m_objListEMACrossover.isEmpty())
            for (int nIdx = 0; nIdx < EMACalculator.m_objListEMACrossover
                    .size(); nIdx++) {
                EMACrossover objEMACrossover = EMACalculator.m_objListEMACrossover
                        .get(nIdx);
                strBufLine.append(objEMACrossover.getStockCode()).append(
                        Constants.STR_COMMA_SEPARATOR);
                StockPrice objStockPrice = objEMACrossover.getStockPrice();
                strBufLine.append(objStockPrice.getDate()).append(
                        Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getOpenPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getHighPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getLowPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getClosePrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(objStockPrice.getVolume()).append(
                        Constants.STR_COMMA_SEPARATOR);
                // strBufLine.append(String.format("%.2f",
                // objStockPrice.getAdjClosePrice())).append(Constants.STR_COMMA_SEPARATOR);
                if (m_objUtil.getGenBollReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f - %.2f",
                                    objStockPrice.getUpperBollgerBand(),
                                    objStockPrice.getLowerBollgerBand()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get20DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get50DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get200DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getRSI()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenADLReportValue() == 1) {
                    strBufLine.append(String.valueOf(objStockPrice.getADL()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f",
                                    objStockPrice.getCommodityChannelIndex()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenADXReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getDIPlus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getDIMinus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getADX()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                // For Exponential Moving Average (EMA)
                double dblShortDurationEMA = 0.00;
                double dblLongDurationEMA = 0.00;
                EMA objEMATemp = null;
                String strCurrentTrend = "";
                String strAction = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                        dblLongDurationEMA = objEMATemp.getLongDurationEMA();
                        strBufLine.append(
                                String.format("%.2f", dblShortDurationEMA))
                                .append(Constants.STR_COMMA_SEPARATOR);
                        strBufLine.append(
                                String.format("%.2f", dblLongDurationEMA))
                                .append(Constants.STR_COMMA_SEPARATOR);

                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();
                            strBufLine.append(strCurrentTrend).append(
                                    Constants.STR_COMMA_SEPARATOR);
                            strBufLine.append(strAction).append(
                                    Constants.STR_COMMA_SEPARATOR);
                        }

                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getROC()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                strBufLine.append(System.getProperty("line.separator"));
            }
            bwOutputStream = new BufferedWriter(fwObj);
            bwOutputStream.write(strBufLine.toString());
            bwOutputStream.flush();
            // fwObj.write(strBufLine.toString());
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {
            try {
                if (fwObj != null && bwOutputStream != null) {
                    bwOutputStream.flush();
                    fwObj.flush();
                    bwOutputStream.close();
                    fwObj.close();
                }
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace();
            }
        }
    }

    // public static final String STR_PIVOT_POINT_REPORT_HEADER =
    // "  Stock-Code,  High,  Low,  Close,  S1,  S2,  S3,  Pivot,  R1,  R2,  R3 %s";
    // public static final String STR_PIVOT_POINT_REPORT_HEADER =
    // "  Stock-Code,  High,  Low,  Close,  Fibonacci-S1,  Fibonacci-S2,  Fibonacci-S3,  Range,  Fibonacci-R1,  Fibonacci-R2,  Fibonacci-R3 %s";
    public void generatePivotPointReport(
            ArrayList<PivotPoint> objListPivotPoint, boolean bFibonacci)
            throws RowsExceededException, WriteException {
        if (m_objUtil.getGenPivotPointReportValue() == 0) {
            return;
        }
        FileWriter fwObj = null;
        BufferedWriter bwOutputStream = null;
        try {
            StringBuffer strBufLine = new StringBuffer(
                    getHeaderForPivotReport(bFibonacci));
            File fProgOutput = new File(getReportFileNameForPivot(bFibonacci));
            fwObj = new FileWriter(fProgOutput);
            // http://download.oracle.com/javase/1,5.0/docs/api/java/lang/String.html
            // http://download.oracle.com/javase/1,5.0/docs/api/java/util/Formatter.html#syntax
            for (int nIdx = 0; nIdx < objListPivotPoint.size(); nIdx++) {
                PivotPoint objPivotPoint = objListPivotPoint.get(nIdx);
                strBufLine.append(objPivotPoint.getStockCode()).append(
                        Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objPivotPoint
                                .getPreviousDayStockPrice().getHighPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objPivotPoint
                                .getPreviousDayStockPrice().getLowPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objPivotPoint
                                .getPreviousDayStockPrice().getClosePrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objPivotPoint.getSupport1()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objPivotPoint.getSupport2()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objPivotPoint.getSupport3()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objPivotPoint.getPivotPoint()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objPivotPoint.getResistance1()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objPivotPoint.getResistance2()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objPivotPoint.getResistance3()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                if (m_objUtil.getGenBollReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f - %.2f", objPivotPoint
                                    .getCurrentDayStockPrice()
                                    .getUpperBollgerBand(), objPivotPoint
                                    .getCurrentDayStockPrice()
                                    .getLowerBollgerBand())).append(
                            Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objPivotPoint
                                    .getCurrentDayStockPrice().get20DaysSMA()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objPivotPoint
                                    .getCurrentDayStockPrice().get50DaysSMA()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f", objPivotPoint
                                    .getCurrentDayStockPrice().get200DaysSMA()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objPivotPoint
                                    .getCurrentDayStockPrice().getRSI()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenADLReportValue() == 1) {
                    strBufLine.append(
                            String.valueOf(objPivotPoint
                                    .getCurrentDayStockPrice().getADL()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objPivotPoint
                                    .getCurrentDayStockPrice()
                                    .getCommodityChannelIndex())).append(
                            Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objPivotPoint
                                    .getCurrentDayStockPrice()
                                    .getROC())).append(
                            Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenADXReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objPivotPoint
                                    .getCurrentDayStockPrice().getDIPlus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objPivotPoint
                                    .getCurrentDayStockPrice().getDIMinus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objPivotPoint
                                    .getCurrentDayStockPrice().getADX()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                // For Exponential Moving Average (EMA)
                double dblShortDurationEMA = 0.00;
                double dblLongDurationEMA = 0.00;
                EMA objEMATemp = null;
                String strCurrentTrend = "";
                String strAction = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objPivotPoint.getCurrentDayStockPrice()
                            .getEMAList().get(strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                        dblLongDurationEMA = objEMATemp.getLongDurationEMA();
                        strBufLine.append(
                                String.format("%.2f", dblShortDurationEMA))
                                .append(Constants.STR_COMMA_SEPARATOR);
                        strBufLine.append(
                                String.format("%.2f", dblLongDurationEMA))
                                .append(Constants.STR_COMMA_SEPARATOR);

                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();
                            strBufLine.append(strCurrentTrend).append(
                                    Constants.STR_COMMA_SEPARATOR);
                            strBufLine.append(strAction).append(
                                    Constants.STR_COMMA_SEPARATOR);
                        }

                    }
                }
                strBufLine.append(System.getProperty("line.separator"));
            }
            bwOutputStream = new BufferedWriter(fwObj);
            bwOutputStream.write(strBufLine.toString());
            // fwObj.write(strBufLine.toString());
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {
            try {
                if (fwObj != null && bwOutputStream != null) {
                    bwOutputStream.flush();
                    fwObj.flush();
                    bwOutputStream.close();
                    fwObj.close();
                }
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace();
            }
        }
    }

    public void generateRSIObOsReport() throws RowsExceededException,
            WriteException {
        FileWriter fwObj = null;
        BufferedWriter bwOutputStream = null;
        try {
            File fProgOutput = new File(getReportFileNameForRSIObOs());
            fwObj = new FileWriter(fProgOutput);

            // The Report file Header.
            StringBuffer strBufLine = new StringBuffer(
                    getHeaderForEMACrossover());

            // Actual file content.
            // while (EMACalculator.m_objListEMACrossover != null &&
            // !EMACalculator.m_objListEMACrossover.isEmpty())
            for (int nIdx = 0; nIdx < RSICalculator.m_objListRSI.size(); nIdx++) {
                RSI objRSI = RSICalculator.m_objListRSI.get(nIdx);
                strBufLine.append(objRSI.getStockCode()).append(
                        Constants.STR_COMMA_SEPARATOR);
                StockPrice objStockPrice = objRSI.getStockPrice();
                strBufLine.append(objStockPrice.getDate()).append(
                        Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getOpenPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getHighPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getLowPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getClosePrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(objStockPrice.getVolume()).append(
                        Constants.STR_COMMA_SEPARATOR);
                // strBufLine.append(String.format("%.2f",
                // objStockPrice.getAdjClosePrice())).append(Constants.STR_COMMA_SEPARATOR);
                if (m_objUtil.getGenBollReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f - %.2f",
                                    objStockPrice.getUpperBollgerBand(),
                                    objStockPrice.getLowerBollgerBand()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get20DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get50DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get200DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getRSI()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenADLReportValue() == 1) {
                    strBufLine.append(String.valueOf(objStockPrice.getADL()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f",
                                    objStockPrice.getCommodityChannelIndex()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenADXReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getDIPlus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getDIMinus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getADX()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                // For Exponential Moving Average (EMA)
                double dblShortDurationEMA = 0.00;
                double dblLongDurationEMA = 0.00;
                EMA objEMATemp = null;
                String strCurrentTrend = "";
                String strAction = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                        dblLongDurationEMA = objEMATemp.getLongDurationEMA();
                        strBufLine.append(
                                String.format("%.2f", dblShortDurationEMA))
                                .append(Constants.STR_COMMA_SEPARATOR);
                        strBufLine.append(
                                String.format("%.2f", dblLongDurationEMA))
                                .append(Constants.STR_COMMA_SEPARATOR);

                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();
                            strBufLine.append(strCurrentTrend).append(
                                    Constants.STR_COMMA_SEPARATOR);
                            strBufLine.append(strAction).append(
                                    Constants.STR_COMMA_SEPARATOR);
                        }

                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getROC()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                strBufLine.append(System.getProperty("line.separator"));
            }
            bwOutputStream = new BufferedWriter(fwObj);
            bwOutputStream.write(strBufLine.toString());
            // fwObj.write(strBufLine.toString());
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {
            try {
                if (fwObj != null && bwOutputStream != null) {
                    bwOutputStream.flush();
                    fwObj.flush();
                    bwOutputStream.close();
                    fwObj.close();
                }
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace();
            }
        }

    }

    public void generateBollingerBandReport(TechnicalIndicators objTI)
            throws RowsExceededException, WriteException {
        FileWriter fwObj = null;
        BufferedWriter bwOutputStream = null;
        try {
            File fProgOutput = new File(getReportFileNameForBollingerBand());
            fwObj = new FileWriter(fProgOutput);

            // The Report file Header.
            StringBuffer strBufLine = new StringBuffer(
                    getHeaderForEMACrossover());

            // Actual file content.
            // while (EMACalculator.m_objListEMACrossover != null &&
            // !EMACalculator.m_objListEMACrossover.isEmpty())
            for (int nIdx = 0; nIdx < objTI.m_objListBollingerBand.size(); nIdx++) {
                StockPriceInfo objStockPriceInfo = objTI.m_objListBollingerBand
                        .get(nIdx);
                strBufLine.append(objStockPriceInfo.getStockCode()).append(
                        Constants.STR_COMMA_SEPARATOR);
                StockPrice objStockPrice = objStockPriceInfo.getStockPrice();
                strBufLine.append(objStockPrice.getDate()).append(
                        Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getOpenPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getHighPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getLowPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getClosePrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(objStockPrice.getVolume()).append(
                        Constants.STR_COMMA_SEPARATOR);
                // strBufLine.append(String.format("%.2f",
                // objStockPrice.getAdjClosePrice())).append(Constants.STR_COMMA_SEPARATOR);
                if (m_objUtil.getGenBollReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f - %.2f",
                                    objStockPrice.getUpperBollgerBand(),
                                    objStockPrice.getLowerBollgerBand()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get20DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get50DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get200DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getRSI()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenADLReportValue() == 1) {
                    strBufLine.append(String.valueOf(objStockPrice.getADL()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f",
                                    objStockPrice.getCommodityChannelIndex()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenADXReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getDIPlus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getDIMinus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getADX()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                // For Exponential Moving Average (EMA)
                double dblShortDurationEMA = 0.00;
                double dblLongDurationEMA = 0.00;
                EMA objEMATemp = null;
                String strCurrentTrend = "";
                String strAction = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                        dblLongDurationEMA = objEMATemp.getLongDurationEMA();
                        strBufLine.append(
                                String.format("%.2f", dblShortDurationEMA))
                                .append(Constants.STR_COMMA_SEPARATOR);
                        strBufLine.append(
                                String.format("%.2f", dblLongDurationEMA))
                                .append(Constants.STR_COMMA_SEPARATOR);

                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();
                            strBufLine.append(strCurrentTrend).append(
                                    Constants.STR_COMMA_SEPARATOR);
                            strBufLine.append(strAction).append(
                                    Constants.STR_COMMA_SEPARATOR);
                        }

                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getROC()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                strBufLine.append(System.getProperty("line.separator"));
            }
            bwOutputStream = new BufferedWriter(fwObj);
            bwOutputStream.write(strBufLine.toString());
            // fwObj.write(strBufLine.toString());
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {
            try {
                if (fwObj != null && bwOutputStream != null) {
                    bwOutputStream.flush();
                    fwObj.flush();
                    bwOutputStream.close();
                    fwObj.close();
                }
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace();
            }
        }
    }

    public void generateADXReport(TechnicalIndicators objTI)
            throws RowsExceededException, WriteException {
        FileWriter fwObj = null;
        BufferedWriter bwOutputStream = null;
        try {
            File fProgOutput = new File(getReportFileNameForADX());
            fwObj = new FileWriter(fProgOutput);

            // The Report file Header.
            StringBuffer strBufLine = new StringBuffer(
                    getHeaderForEMACrossover());

            // Actual file content.
            // while (EMACalculator.m_objListEMACrossover != null &&
            // !EMACalculator.m_objListEMACrossover.isEmpty())
            for (int nIdx = 0; nIdx < objTI.m_objListADX.size(); nIdx++) {
                StockPriceInfo objStockPriceInfo = objTI.m_objListADX.get(nIdx);
                strBufLine.append(objStockPriceInfo.getStockCode()).append(
                        Constants.STR_COMMA_SEPARATOR);
                StockPrice objStockPrice = objStockPriceInfo.getStockPrice();
                strBufLine.append(objStockPrice.getDate()).append(
                        Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getOpenPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getHighPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getLowPrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(
                        String.format("%.2f", objStockPrice.getClosePrice()))
                        .append(Constants.STR_COMMA_SEPARATOR);
                strBufLine.append(objStockPrice.getVolume()).append(
                        Constants.STR_COMMA_SEPARATOR);
                if (m_objUtil.getGenBollReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f - %.2f",
                                    objStockPrice.getUpperBollgerBand(),
                                    objStockPrice.getLowerBollgerBand()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get20DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get50DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    strBufLine
                            .append(String.format("%.2f",
                                    objStockPrice.get200DaysSMA())).append(
                                    Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getRSI()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenADLReportValue() == 1) {
                    strBufLine.append(String.valueOf(objStockPrice.getADL()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f",
                                    objStockPrice.getCommodityChannelIndex()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                if (m_objUtil.getGenADXReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getDIPlus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getDIMinus()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getADX()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                // For Exponential Moving Average (EMA)
                double dblShortDurationEMA = 0.00;
                double dblLongDurationEMA = 0.00;
                EMA objEMATemp = null;
                String strCurrentTrend = "";
                String strAction = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                        dblLongDurationEMA = objEMATemp.getLongDurationEMA();
                        strBufLine.append(
                                String.format("%.2f", dblShortDurationEMA))
                                .append(Constants.STR_COMMA_SEPARATOR);
                        strBufLine.append(
                                String.format("%.2f", dblLongDurationEMA))
                                .append(Constants.STR_COMMA_SEPARATOR);

                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();
                            strBufLine.append(strCurrentTrend).append(
                                    Constants.STR_COMMA_SEPARATOR);
                            strBufLine.append(strAction).append(
                                    Constants.STR_COMMA_SEPARATOR);
                        }

                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    strBufLine.append(
                            String.format("%.2f", objStockPrice.getROC()))
                            .append(Constants.STR_COMMA_SEPARATOR);
                }
                strBufLine.append(System.getProperty("line.separator"));
            }
            bwOutputStream = new BufferedWriter(fwObj);
            bwOutputStream.write(strBufLine.toString());
            // fwObj.write(strBufLine.toString());
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {
            try {
                if (fwObj != null && bwOutputStream != null) {
                    bwOutputStream.flush();
                    fwObj.flush();
                    bwOutputStream.close();
                    fwObj.close();
                }
            } catch (IOException ioExcep) {
                ioExcep.printStackTrace();
            }
        }
    }
}
