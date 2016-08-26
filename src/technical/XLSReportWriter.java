/**
 *
 */
package com.fin.technical;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import jxl.*;
import jxl.format.PageOrientation;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */

// http://stackoverflow.com/questions/5908405/get-installed-software-list
// http://www.andykhan.com/jexcelapi/tutorial.html
public class XLSReportWriter extends ReportWriter {
    // WritableFont m_objWFArial12Header = null;
    //
    // WritableFont m_objWFArial10Input = null;
    //
    // WritableFont m_objWFArial10Bullish = null;
    //
    // WritableFont m_objWFArial10Bearish = null;
    //
    // WritableFont m_objWFArial10RSIAbove70 = null;
    //
    // WritableFont m_objWFArial10RSIBelow20 = null;
    //
    // WritableFont m_objWFArial10ADXBelow25 = null;
    //
    // WritableFont m_objWFArial10ADXAbove25 = null;
    //
    // WritableFont m_objWFArial10CCIBelowMinus100 = null;
    //
    // WritableFont m_objWFArial10CCIAbovePlus100 = null;

    // objWFArial12font.setColour(Colour.BROWN);

    protected XLSReportWriter() {
        // try
        // {
        // m_objWFArial12Header = new WritableFont(WritableFont.ARIAL, 12,
        // WritableFont.BOLD);
        // m_objWFArial12Header.setColour(Colour.BROWN);
        //
        // m_objWFArial10Input = new WritableFont(WritableFont.ARIAL, 10);
        // m_objWFArial10Input.setColour(Colour.BROWN);
        //
        // m_objWFArial10Bullish = new WritableFont(WritableFont.ARIAL, 10);
        // m_objWFArial10Bullish.setColour(Colour.GREEN);
        //
        // m_objWFArial10Bearish = new WritableFont(WritableFont.ARIAL, 10);
        // m_objWFArial10Bearish.setColour(Colour.RED);
        //
        // m_objWFArial10RSIAbove70 = new WritableFont(WritableFont.ARIAL, 10);
        // m_objWFArial10RSIAbove70.setColour(Colour.RED);
        //
        // m_objWFArial10RSIBelow20 = new WritableFont(WritableFont.ARIAL, 10);
        // m_objWFArial10RSIBelow20.setColour(Colour.GREEN);
        //
        // m_objWFArial10ADXAbove25 = new WritableFont(WritableFont.ARIAL, 10);
        // m_objWFArial10ADXAbove25.setColour(Colour.GREEN);
        //
        // m_objWFArial10ADXBelow25 = new WritableFont(WritableFont.ARIAL, 10);
        // m_objWFArial10ADXBelow25.setColour(Colour.RED);
        //
        // m_objWFArial10CCIBelowMinus100 = new WritableFont(WritableFont.ARIAL,
        // 10);
        // m_objWFArial10ADXBelow25.setColour(Colour.RED);
        //
        // m_objWFArial10CCIAbovePlus100 = new WritableFont(WritableFont.ARIAL,
        // 10);
        // m_objWFArial10ADXBelow25.setColour(Colour.GREEN);
        // }
        // catch(WriteException objWEExcep)
        // {
        // System.out.println(objWEExcep.getLocalizedMessage());
        // }

    }

    public void generateEMAReport(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice)
            throws RowsExceededException, WriteException, IOException {
        if (Util.getInstance().getGenStockEMAReportValue() == 0) {
            return;
        }
        int nSizeOfList = lstHistoricalPrice.size();
        if (lstHistoricalPrice == null || nSizeOfList == 0) {
            return;
        }
        // prepare file with absolute path for spread sheet document creation.
        StringBuffer strBufFileName = new StringBuffer(m_strPath);
        strBufFileName.append(Util.m_strFileSeparator);

        if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                Constants.STR_STOCK_CODE_DATA_SOURCE_YAHOO) == true) {
            strBufFileName.append(strStockCode).append(
                    Constants.STR_XLS_EMA_REPORT_FILE);
        } else if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                Constants.STR_STOCK_CODE_DATA_SOURCE_GOOGLE) == true) {
            strStockCode = strStockCode.replace(':', '.');
            strBufFileName.append(strStockCode).append(
                    Constants.STR_XLS_EMA_REPORT_FILE);
        } else if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                Constants.STR_STOCK_CODE_DATA_SOURCE_NSEINDIA) == true) {
            strBufFileName.append(strStockCode).append(
                    Constants.STR_XLS_EMA_REPORT_FILE);
        }

        File fProgOutput = new File(strBufFileName.toString());
        WritableWorkbook objMSExcelWorkbook = Workbook
                .createWorkbook(fProgOutput);
        // create working sheet in the spread sheet document
        WritableSheet objWorksheet = null;
        if (objMSExcelWorkbook != null) {
            objWorksheet = objMSExcelWorkbook.createSheet("Technicals", 0);
            objWorksheet.getSettings().setDisplayZeroValues(true);
            objWorksheet.getSettings().setOrientation(PageOrientation.PORTRAIT);

        }
        if (objWorksheet == null) {
            return;
        }

        StockPrice objCurrSIPH = null;

        WritableFont objWFArial12font = new WritableFont(WritableFont.ARIAL,
                12, WritableFont.BOLD);
        objWFArial12font.setColour(Colour.BROWN);
        WritableCellFormat objWCFArial12format = new WritableCellFormat(
                objWFArial12font);
        for (int nIdx = 0; nIdx < m_strHeaderColumnNames.length; nIdx++) {
            // Creates a cell which, when added to the sheet, will be
            // presented at the specified column and row co-ordinates
            // and will contain the specified text.
            Label objLabel = new Label(nIdx, 0, m_strHeaderColumnNames[nIdx],
                    objWCFArial12format);// Col, Row
            objWorksheet.addCell(objLabel);
        }

        // Actual file content.
        WritableCellFormat objWCFFloat = new WritableCellFormat(
                NumberFormats.FLOAT);
        WritableCellFormat objWCFInteger = new WritableCellFormat(
                NumberFormats.INTEGER);

        int nIdx = 0;
        int nRowNum = 1;
        while ((nIdx < nSizeOfList)
                && (objCurrSIPH = (StockPrice) lstHistoricalPrice.get(nIdx)) != null) {
            int nColNum = 0;
            Label objLabel = new Label(nColNum++, nRowNum,
                    objCurrSIPH.getDate());// Col, Row
            objWorksheet.addCell(objLabel);

            jxl.write.Number objJXLWriteNumber = new jxl.write.Number(
                    nColNum++, nRowNum, objCurrSIPH.getOpenPrice(), objWCFFloat);
            objWorksheet.addCell(objJXLWriteNumber);

            objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                    objCurrSIPH.getHighPrice(), objWCFFloat);
            objWorksheet.addCell(objJXLWriteNumber);

            objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                    objCurrSIPH.getLowPrice(), objWCFFloat);
            objWorksheet.addCell(objJXLWriteNumber);

            objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                    objCurrSIPH.getClosePrice(), objWCFFloat);
            objWorksheet.addCell(objJXLWriteNumber);

            objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                    objCurrSIPH.getVolume(), objWCFInteger);
            objWorksheet.addCell(objJXLWriteNumber);
            // Bollinger Band
            if (m_objUtil.getGenBollReportValue() == 1) {
                // Bollinger band debug Info
                if (m_objUtil.getGenBollDebugInfoValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getDeviationSquared(),
                            objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum,
                            objCurrSIPH.get20DaysSMAOfDeviationSquared(),
                            objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getStandardDeviation(),
                            objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                }
                objLabel = new Label(nColNum++, nRowNum, String.format(
                        "%.2f - %.2f", objCurrSIPH.getUpperBollgerBand(),
                        objCurrSIPH.getLowerBollgerBand()));// Col, Row
                objWorksheet.addCell(objLabel);
            }

            // SMAs
            if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objCurrSIPH.get20DaysSMA(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);
            }
            if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objCurrSIPH.get50DaysSMA(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

            }
            if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objCurrSIPH.get200DaysSMA(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);
            }
            // RSI
            if (m_objUtil.getGenRSIReportValue() == 1) {
                // RSI Debug Info.
                if (m_objUtil.getGenRSIDebugInfo() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getGain(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getLoss(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getAverageGain(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getAverageLoss(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getRelativeStrength(),
                            objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objCurrSIPH.getRSI(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

            }
            // ADL: Accumulation Distribution Line
            if (m_objUtil.getGenADLReportValue() == 1) {
                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objCurrSIPH.getADL(), objWCFInteger);
                objWorksheet.addCell(objJXLWriteNumber);
            }
            // CCI: Commodity Channel Index
            if (m_objUtil.getGenCCIReportValue() == 1) {
                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objCurrSIPH.getCommodityChannelIndex(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);
            }
            // For Average Directional Index (ADX)
            if (m_objUtil.getGenADXReportValue() == 1) {
                if (m_objUtil.getGenADXDebugInfo() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getTrueRange(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getDMPlus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getDMMinus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getEMAOfTrueRange(),
                            objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getEMAOfDMPlus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getEMAOfDMMinus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objCurrSIPH.getDIPlus(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objCurrSIPH.getDIMinus(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                if (m_objUtil.getGenADXDebugInfo() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getDIDiff(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getDISum(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objCurrSIPH.getDX(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objCurrSIPH.getADX(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);
            }
            // For Exponential Moving Average (EMA)
            EMA objEMATemp = null;
            String[] strEMADurationValues = m_objUtil.getEMADurationValue();
            for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                objEMATemp = ((EMA) objCurrSIPH.getEMAList().get(
                        strEMADurationValues[nEMAIdx]));
                if (objEMATemp != null) {
                    double dblShortDurationEMA = objEMATemp
                            .getShortDurationEMA();
                    double dblLongDurationEMA = objEMATemp.getLongDurationEMA();

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, dblShortDurationEMA, objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, dblLongDurationEMA, objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    String strCurrentTrend = objEMATemp.getTrend();
                    String strAction = objEMATemp.getAction();
                    // Is this the preferred EMA Combo?
                    boolean bCond1 = m_objUtil.getPreferredEMAComboValue()
                            .equalsIgnoreCase(strEMADurationValues[nEMAIdx]);
                    boolean bCond2 = m_objUtil.getPreferredEMAComboValue()
                            .length() == 0;
                    if (bCond1 || bCond2)
                    // if(m_objUtil.getPreferredEMAComboValue().equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                    // || m_objUtil.getPreferredEMAComboValue().length() == 0)
                    {
                        strCurrentTrend = objEMATemp.getTrend();
                        strAction = objEMATemp.getAction();

                        objLabel = new Label(nColNum++, nRowNum,
                                strCurrentTrend);// Col, Row
                        objWorksheet.addCell(objLabel);

                        objLabel = new Label(nColNum++, nRowNum, strAction);// Col,
                                                                            // Row
                        objWorksheet.addCell(objLabel);
                    }
                }
            }
            if (m_objUtil.getGenROCReportValue() == 1) {
                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objCurrSIPH.m_dblROC, objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);
            }
            nIdx++;
            nRowNum++;
        }

        objMSExcelWorkbook.write();
        objWorksheet.getSettings().setHorizontalFreeze(1);// Freeze first (the
                                                          // header) row of the
                                                          // sheet.
        objMSExcelWorkbook.close();
    }

    public void generateEMACrossoverReport() throws RowsExceededException,
            WriteException {
        if (Util.getInstance().getGenMasterEMAReportValue() == 0) {
            return;
        }
        try {
            File fProgOutput = new File(getReportFileNameForEMACrossover());
            WritableWorkbook objMSExcelWorkbook = Workbook
                    .createWorkbook(fProgOutput);
            WritableSheet objWorksheet = null;
            if (objMSExcelWorkbook != null) {
                objWorksheet = objMSExcelWorkbook.createSheet("EMA-Crossovers",
                        0);
                objWorksheet.getSettings().setDisplayZeroValues(true);
                objWorksheet.getSettings().setOrientation(
                        PageOrientation.PORTRAIT);
            }
            // The Report file Header.
            String[] strCells = getHeaderForEMACrossover().split(
                    Constants.STR_SPILT_ON_COMMA);

            WritableFont objWFArial12font = new WritableFont(
                    WritableFont.ARIAL, 12, WritableFont.BOLD);
            objWFArial12font.setColour(Colour.BROWN);
            WritableCellFormat arial12format = new WritableCellFormat(
                    objWFArial12font);
            for (int nIdx = 0; nIdx < strCells.length; nIdx++) {
                // Creates a cell which, when added to the sheet, will be
                // presented at the specified column and row co-ordinates
                // and will contain the specified text.
                Label objLabel = new Label(nIdx, 0, strCells[nIdx],
                        arial12format);// Col, Row
                objWorksheet.addCell(objLabel);
            }

            // Actual file content.
            int nRowNum = 1;
            WritableCellFormat objWCFFloat = new WritableCellFormat(
                    NumberFormats.FLOAT);
            WritableCellFormat objWCFInteger = new WritableCellFormat(
                    NumberFormats.INTEGER);

            while (EMACalculator.m_objListEMACrossover != null
                    && !EMACalculator.m_objListEMACrossover.isEmpty()) {
                int nColNum = 0;
                EMACrossover objEMACrossover = EMACalculator.m_objListEMACrossover
                        .remove(0);

                Label objLabel = new Label(nColNum++, nRowNum,
                        objEMACrossover.getStockCode());// Col, Row
                objWorksheet.addCell(objLabel);
                StockPrice objStockPrice = objEMACrossover.getStockPrice();

                objLabel = new Label(nColNum++, nRowNum,
                        objStockPrice.getDate());// Col, Row
                objWorksheet.addCell(objLabel);

                jxl.write.Number objJXLWriteNumber = new jxl.write.Number(
                        nColNum++, nRowNum, objStockPrice.getOpenPrice(),
                        objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getHighPrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getLowPrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getClosePrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getVolume(), objWCFInteger);
                objWorksheet.addCell(objJXLWriteNumber);

                if (m_objUtil.getGenBollReportValue() == 1) {
                    objLabel = new Label(nColNum++, nRowNum, String.format(
                            "%.2f - %.2f", objStockPrice.getUpperBollgerBand(),
                            objStockPrice.getLowerBollgerBand()));// Col, Row
                    objWorksheet.addCell(objLabel);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get20DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get50DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get200DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getRSI(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenADLReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getADL());
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getCommodityChannelIndex(),
                            objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }

//                if (m_objUtil.getGenROCReportValue() == 1) {
//                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
//                            nRowNum, objStockPrice.getROC(), objWCFFloat);
//                    objWorksheet.addCell(objJXLWriteNumber);
//                }

                if (m_objUtil.getGenADXReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getDIPlus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getDIMinus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getADX(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                // For Exponential Moving Average (EMA)
                EMA objEMATemp = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        double dblShortDurationEMA = objEMATemp
                                .getShortDurationEMA();
                        double dblLongDurationEMA = objEMATemp
                                .getLongDurationEMA();

                        objJXLWriteNumber = new jxl.write.Number(nColNum++,
                                nRowNum, dblShortDurationEMA, objWCFFloat);
                        objWorksheet.addCell(objJXLWriteNumber);

                        objJXLWriteNumber = new jxl.write.Number(nColNum++,
                                nRowNum, dblLongDurationEMA, objWCFFloat);
                        objWorksheet.addCell(objJXLWriteNumber);

                        String strCurrentTrend = objEMATemp.getTrend();
                        String strAction = objEMATemp.getAction();
                        // Is this the preferred EMA Combo?
                        boolean bCond1 = m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx]);
                        boolean bCond2 = m_objUtil.getPreferredEMAComboValue()
                                .length() == 0;
                        if (bCond1 || bCond2)
                        // if(m_objUtil.getPreferredEMAComboValue().equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                        // || m_objUtil.getPreferredEMAComboValue().length() ==
                        // 0)
                        {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();

                            objLabel = new Label(nColNum++, nRowNum,
                                    strCurrentTrend);// Col, Row
                            objWorksheet.addCell(objLabel);

                            objLabel = new Label(nColNum++, nRowNum, strAction);// Col,
                                                                                // Row
                            objWorksheet.addCell(objLabel);
                        }
                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                            objStockPrice.m_dblROC, objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                nRowNum++;
            }
            objMSExcelWorkbook.write();
            objMSExcelWorkbook.close();
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {

        }
    }

    public void generatePivotPointReport(
            ArrayList<PivotPoint> objListPivotPoint, boolean bFibonacci)
            throws RowsExceededException, WriteException {
        if (Util.getInstance().getGenPivotPointReportValue() == 0) {
            return;
        }
        try {
            File fProgOutput = new File(getReportFileNameForPivot(bFibonacci));
            WritableWorkbook objMSExcelWorkbook = Workbook
                    .createWorkbook(fProgOutput);
            WritableSheet objWorksheet = null;
            if (objMSExcelWorkbook != null) {
                objWorksheet = objMSExcelWorkbook
                        .createSheet("Pivot Points", 0);
                objWorksheet.getSettings().setDisplayZeroValues(true);
                objWorksheet.getSettings().setOrientation(
                        PageOrientation.LANDSCAPE);
            }
            if (objWorksheet == null) {
                return;
            }

            String[] strCells = getHeaderForPivotReport(bFibonacci).split(
                    Constants.STR_SPILT_ON_COMMA);
            WritableFont objWFArial12font = new WritableFont(
                    WritableFont.ARIAL, 12, WritableFont.BOLD);
            objWFArial12font.setColour(Colour.BROWN);
            WritableCellFormat arial12format = new WritableCellFormat(
                    objWFArial12font);
            for (int nIdx = 0; nIdx < strCells.length; nIdx++) {
                // Creates a cell which, when added to the sheet, will be
                // presented at the specified column and row co-ordinates
                // and will contain the specified text.
                Label objLabel = new Label(nIdx, 0, strCells[nIdx],
                        arial12format);// Col, Row
                objWorksheet.addCell(objLabel);
            }

            // Actual file content.
            int nRowNum = 1;
            WritableCellFormat objWCFFloat = new WritableCellFormat(
                    NumberFormats.FLOAT);
            WritableCellFormat objWCFInteger = new WritableCellFormat(
                    NumberFormats.INTEGER);

            for (int nIdx = 0; nIdx < objListPivotPoint.size(); nIdx++) {
                int nColNum = 0;
                PivotPoint objPivotPoint = objListPivotPoint.get(nIdx);

                Label objLabel = new Label(nColNum++, nRowNum,
                        objPivotPoint.getStockCode());// Col, Row
                objWorksheet.addCell(objLabel);

                jxl.write.Number objJXLWriteNumber = new jxl.write.Number(
                        nColNum++, nRowNum, objPivotPoint
                                .getPreviousDayStockPrice().getHighPrice(),
                        objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objPivotPoint.getPreviousDayStockPrice().getLowPrice(),
                        objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objPivotPoint.getPreviousDayStockPrice()
                                .getClosePrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objPivotPoint.getSupport1(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objPivotPoint.getSupport2(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objPivotPoint.getSupport3(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objPivotPoint.getPivotPoint(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objPivotPoint.getResistance1(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objPivotPoint.getResistance2(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objPivotPoint.getResistance3(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);
                if (m_objUtil.getGenBollReportValue() == 1) {
                    objLabel = new Label(nColNum++, nRowNum, String.format(
                            "%.2f - %.2f", objPivotPoint
                                    .getCurrentDayStockPrice()
                                    .getUpperBollgerBand(), objPivotPoint
                                    .getCurrentDayStockPrice()
                                    .getLowerBollgerBand()));// Col, Row
                    objWorksheet.addCell(objLabel);
                }
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objPivotPoint.getCurrentDayStockPrice()
                                    .get20DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objPivotPoint.getCurrentDayStockPrice()
                                    .get50DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objPivotPoint.getCurrentDayStockPrice()
                                    .get200DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objPivotPoint.getCurrentDayStockPrice()
                                    .getRSI(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenADLReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objPivotPoint.getCurrentDayStockPrice()
                                    .getADL(), objWCFInteger);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objPivotPoint.getCurrentDayStockPrice()
                                    .getCommodityChannelIndex(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objPivotPoint.getCurrentDayStockPrice()
                                    .getROC(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenADXReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objPivotPoint.getCurrentDayStockPrice()
                                    .getDIPlus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objPivotPoint.getCurrentDayStockPrice()
                                    .getDIMinus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objPivotPoint.getCurrentDayStockPrice()
                                    .getADX(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                // For Exponential Moving Average (EMA)
                EMA objEMATemp = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objPivotPoint.getCurrentDayStockPrice()
                            .getEMAList().get(strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        double dblShortDurationEMA = objEMATemp
                                .getShortDurationEMA();
                        double dblLongDurationEMA = objEMATemp
                                .getLongDurationEMA();

                        objJXLWriteNumber = new jxl.write.Number(nColNum++,
                                nRowNum, dblShortDurationEMA, objWCFFloat);
                        objWorksheet.addCell(objJXLWriteNumber);

                        objJXLWriteNumber = new jxl.write.Number(nColNum++,
                                nRowNum, dblLongDurationEMA, objWCFFloat);
                        objWorksheet.addCell(objJXLWriteNumber);

                        String strCurrentTrend = objEMATemp.getTrend();
                        String strAction = objEMATemp.getAction();
                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();

                            objLabel = new Label(nColNum++, nRowNum,
                                    strCurrentTrend);// Col, Row
                            objWorksheet.addCell(objLabel);

                            objLabel = new Label(nColNum++, nRowNum, strAction);// Col,
                                                                                // Row
                            objWorksheet.addCell(objLabel);
                        }
                    }
                }

                nRowNum++;
            }
            objMSExcelWorkbook.write();
            objMSExcelWorkbook.close();

        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {

        }
    }

    public void generateRSIObOsReport() throws RowsExceededException,
            WriteException {
        try {
            File fProgOutput = new File(getReportFileNameForRSIObOs());
            WritableWorkbook objMSExcelWorkbook = Workbook
                    .createWorkbook(fProgOutput);
            WritableSheet objWorksheet = null;
            if (objMSExcelWorkbook != null) {
                objWorksheet = objMSExcelWorkbook.createSheet(
                        "RSI-Oversold & Overbought", 0);
                objWorksheet.getSettings().setDisplayZeroValues(true);
                objWorksheet.getSettings().setOrientation(
                        PageOrientation.PORTRAIT);
            }
            // The Report file Header.
            String[] strCells = getHeaderForEMACrossover().split(
                    Constants.STR_SPILT_ON_COMMA);

            WritableFont objWFArial12font = new WritableFont(
                    WritableFont.ARIAL, 12, WritableFont.BOLD);
            objWFArial12font.setColour(Colour.BROWN);
            WritableCellFormat arial12format = new WritableCellFormat(
                    objWFArial12font);
            for (int nIdx = 0; nIdx < strCells.length; nIdx++) {
                // Creates a cell which, when added to the sheet, will be
                // presented at the specified column and row co-ordinates
                // and will contain the specified text.
                Label objLabel = new Label(nIdx, 0, strCells[nIdx],
                        arial12format);// Col, Row
                objWorksheet.addCell(objLabel);
            }

            // Actual file content.
            int nRowNum = 1;
            WritableCellFormat objWCFFloat = new WritableCellFormat(
                    NumberFormats.FLOAT);
            WritableCellFormat objWCFInteger = new WritableCellFormat(
                    NumberFormats.INTEGER);

            while (RSICalculator.m_objListRSI != null
                    && !RSICalculator.m_objListRSI.isEmpty()) {
                int nColNum = 0;
                RSI objRSI = RSICalculator.m_objListRSI.remove(0);

                Label objLabel = new Label(nColNum++, nRowNum,
                        objRSI.getStockCode());// Col, Row
                objWorksheet.addCell(objLabel);
                StockPrice objStockPrice = objRSI.getStockPrice();

                objLabel = new Label(nColNum++, nRowNum,
                        objStockPrice.getDate());// Col, Row
                objWorksheet.addCell(objLabel);

                jxl.write.Number objJXLWriteNumber = new jxl.write.Number(
                        nColNum++, nRowNum, objStockPrice.getOpenPrice(),
                        objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getHighPrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getLowPrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getClosePrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getVolume(), objWCFInteger);
                objWorksheet.addCell(objJXLWriteNumber);

                if (m_objUtil.getGenBollReportValue() == 1) {
                    objLabel = new Label(nColNum++, nRowNum, String.format(
                            "%.2f - %.2f", objStockPrice.getUpperBollgerBand(),
                            objStockPrice.getLowerBollgerBand()));// Col, Row
                    objWorksheet.addCell(objLabel);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get20DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get50DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get200DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getRSI(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenADLReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getADL());
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getCommodityChannelIndex(),
                            objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
//                if (m_objUtil.getGenROCReportValue() == 1) {
//                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
//                            nRowNum, objStockPrice.getROC(), objWCFFloat);
//                    objWorksheet.addCell(objJXLWriteNumber);
//                }
                if (m_objUtil.getGenADXReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getDIPlus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getDIMinus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getADX(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                // For Exponential Moving Average (EMA)
                EMA objEMATemp = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = strEMADurationValues.length - 1; nEMAIdx >= 0; nEMAIdx--) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        double dblShortDurationEMA = objEMATemp
                                .getShortDurationEMA();
                        double dblLongDurationEMA = objEMATemp
                                .getLongDurationEMA();

                        objJXLWriteNumber = new jxl.write.Number(nColNum++,
                                nRowNum, dblShortDurationEMA, objWCFFloat);
                        objWorksheet.addCell(objJXLWriteNumber);

                        objJXLWriteNumber = new jxl.write.Number(nColNum++,
                                nRowNum, dblLongDurationEMA, objWCFFloat);
                        objWorksheet.addCell(objJXLWriteNumber);

                        String strCurrentTrend = objEMATemp.getTrend();
                        String strAction = objEMATemp.getAction();
                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();

                            objLabel = new Label(nColNum++, nRowNum,
                                    strCurrentTrend);// Col, Row
                            objWorksheet.addCell(objLabel);

                            objLabel = new Label(nColNum++, nRowNum, strAction);// Col,
                                                                                // Row
                            objWorksheet.addCell(objLabel);
                        }
                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                            objStockPrice.m_dblROC, objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                nRowNum++;
            }
            objMSExcelWorkbook.write();
            objMSExcelWorkbook.close();
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {

        }

    }

    public void generateBollingerBandReport(TechnicalIndicators objTI)
            throws RowsExceededException, WriteException {
        try {
            File fProgOutput = new File(getReportFileNameForBollingerBand());
            WritableWorkbook objMSExcelWorkbook = Workbook
                    .createWorkbook(fProgOutput);
            WritableSheet objWorksheet = null;
            if (objMSExcelWorkbook != null) {
                objWorksheet = objMSExcelWorkbook.createSheet("Bollinger Band",
                        0);
                objWorksheet.getSettings().setDisplayZeroValues(true);
                objWorksheet.getSettings().setOrientation(
                        PageOrientation.PORTRAIT);
            }
            // The Report file Header.
            String[] strCells = getHeaderForEMACrossover().split(
                    Constants.STR_SPILT_ON_COMMA);

            WritableFont objWFArial12font = new WritableFont(
                    WritableFont.ARIAL, 12, WritableFont.BOLD);
            objWFArial12font.setColour(Colour.BROWN);
            WritableCellFormat arial12format = new WritableCellFormat(
                    objWFArial12font);
            for (int nIdx = 0; nIdx < strCells.length; nIdx++) {
                // Creates a cell which, when added to the sheet, will be
                // presented at the specified column and row co-ordinates
                // and will contain the specified text.
                Label objLabel = new Label(nIdx, 0, strCells[nIdx],
                        arial12format);// Col, Row
                objWorksheet.addCell(objLabel);
            }

            // Actual file content.
            int nRowNum = 1;
            WritableCellFormat objWCFFloat = new WritableCellFormat(
                    NumberFormats.FLOAT);
            WritableCellFormat objWCFInteger = new WritableCellFormat(
                    NumberFormats.INTEGER);

            while (objTI.m_objListBollingerBand != null
                    && !objTI.m_objListBollingerBand.isEmpty()) {
                int nColNum = 0;
                StockPriceInfo objStockPriceInfo = objTI.m_objListBollingerBand
                        .remove(0);

                Label objLabel = new Label(nColNum++, nRowNum,
                        objStockPriceInfo.getStockCode());// Col, Row
                objWorksheet.addCell(objLabel);
                StockPrice objStockPrice = objStockPriceInfo.getStockPrice();

                objLabel = new Label(nColNum++, nRowNum,
                        objStockPrice.getDate());// Col, Row
                objWorksheet.addCell(objLabel);

                jxl.write.Number objJXLWriteNumber = new jxl.write.Number(
                        nColNum++, nRowNum, objStockPrice.getOpenPrice(),
                        objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getHighPrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getLowPrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getClosePrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getVolume(), objWCFInteger);
                objWorksheet.addCell(objJXLWriteNumber);

                if (m_objUtil.getGenBollReportValue() == 1) {
                    objLabel = new Label(nColNum++, nRowNum, String.format(
                            "%.2f - %.2f", objStockPrice.getUpperBollgerBand(),
                            objStockPrice.getLowerBollgerBand()));// Col, Row
                    objWorksheet.addCell(objLabel);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get20DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get50DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get200DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getRSI(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenADLReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getADL());
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getCommodityChannelIndex(),
                            objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
//                if (m_objUtil.getGenROCReportValue() == 1) {
//                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
//                            nRowNum, objStockPrice.getROC(), objWCFFloat);
//                    objWorksheet.addCell(objJXLWriteNumber);
//                }
                if (m_objUtil.getGenADXReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getDIPlus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getDIMinus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getADX(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                // For Exponential Moving Average (EMA)
                EMA objEMATemp = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        double dblShortDurationEMA = objEMATemp
                                .getShortDurationEMA();
                        double dblLongDurationEMA = objEMATemp
                                .getLongDurationEMA();

                        objJXLWriteNumber = new jxl.write.Number(nColNum++,
                                nRowNum, dblShortDurationEMA, objWCFFloat);
                        objWorksheet.addCell(objJXLWriteNumber);

                        objJXLWriteNumber = new jxl.write.Number(nColNum++,
                                nRowNum, dblLongDurationEMA, objWCFFloat);
                        objWorksheet.addCell(objJXLWriteNumber);

                        String strCurrentTrend = objEMATemp.getTrend();
                        String strAction = objEMATemp.getAction();
                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();

                            objLabel = new Label(nColNum++, nRowNum,
                                    strCurrentTrend);// Col, Row
                            objWorksheet.addCell(objLabel);

                            objLabel = new Label(nColNum++, nRowNum, strAction);// Col,
                                                                                // Row
                            objWorksheet.addCell(objLabel);
                        }
                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                            objStockPrice.m_dblROC, objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                nRowNum++;
            }
            objMSExcelWorkbook.write();
            objMSExcelWorkbook.close();
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {

        }
    }

    public void generateADXReport(TechnicalIndicators objTI)
            throws RowsExceededException, WriteException {
        try {
            File fProgOutput = new File(getReportFileNameForADX());
            WritableWorkbook objMSExcelWorkbook = Workbook
                    .createWorkbook(fProgOutput);
            WritableSheet objWorksheet = null;
            if (objMSExcelWorkbook != null) {
                objWorksheet = objMSExcelWorkbook.createSheet(
                        "Average Directional Index", 0);
                objWorksheet.getSettings().setDisplayZeroValues(true);
                objWorksheet.getSettings().setOrientation(
                        PageOrientation.PORTRAIT);
            }
            // The Report file Header.
            String[] strCells = getHeaderForEMACrossover().split(
                    Constants.STR_SPILT_ON_COMMA);

            WritableFont objWFArial12font = new WritableFont(
                    WritableFont.ARIAL, 12, WritableFont.BOLD);
            objWFArial12font.setColour(Colour.BROWN);
            WritableCellFormat arial12format = new WritableCellFormat(
                    objWFArial12font);
            for (int nIdx = 0; nIdx < strCells.length; nIdx++) {
                // Creates a cell which, when added to the sheet, will be
                // presented at the specified column and row co-ordinates
                // and will contain the specified text.
                Label objLabel = new Label(nIdx, 0, strCells[nIdx],
                        arial12format);// Col, Row
                objWorksheet.addCell(objLabel);
            }

            // Actual file content.
            int nRowNum = 1;
            WritableCellFormat objWCFFloat = new WritableCellFormat(
                    NumberFormats.FLOAT);
            WritableCellFormat objWCFInteger = new WritableCellFormat(
                    NumberFormats.INTEGER);

            while (objTI.m_objListADX != null && !objTI.m_objListADX.isEmpty()) {
                int nColNum = 0;
                StockPriceInfo objStockPriceInfo = objTI.m_objListADX.remove(0);

                Label objLabel = new Label(nColNum++, nRowNum,
                        objStockPriceInfo.getStockCode());// Col, Row
                objWorksheet.addCell(objLabel);
                StockPrice objStockPrice = objStockPriceInfo.getStockPrice();

                objLabel = new Label(nColNum++, nRowNum,
                        objStockPrice.getDate());// Col, Row
                objWorksheet.addCell(objLabel);

                jxl.write.Number objJXLWriteNumber = new jxl.write.Number(
                        nColNum++, nRowNum, objStockPrice.getOpenPrice(),
                        objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getHighPrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getLowPrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getClosePrice(), objWCFFloat);
                objWorksheet.addCell(objJXLWriteNumber);

                objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                        objStockPrice.getVolume(), objWCFInteger);
                objWorksheet.addCell(objJXLWriteNumber);

                if (m_objUtil.getGenBollReportValue() == 1) {
                    objLabel = new Label(nColNum++, nRowNum, String.format(
                            "%.2f - %.2f", objStockPrice.getUpperBollgerBand(),
                            objStockPrice.getLowerBollgerBand()));// Col, Row
                    objWorksheet.addCell(objLabel);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get20DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get50DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.get200DaysSMA(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getRSI(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenADLReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getADL());
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getCommodityChannelIndex(),
                            objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
//                if (m_objUtil.getGenROCReportValue() == 1) {
//                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
//                            nRowNum, objStockPrice.getROC(), objWCFFloat);
//                    objWorksheet.addCell(objJXLWriteNumber);
//                }
                if (m_objUtil.getGenADXReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getDIPlus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getDIMinus(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);

                    objJXLWriteNumber = new jxl.write.Number(nColNum++,
                            nRowNum, objStockPrice.getADX(), objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                // For Exponential Moving Average (EMA)
                EMA objEMATemp = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        double dblShortDurationEMA = objEMATemp
                                .getShortDurationEMA();
                        double dblLongDurationEMA = objEMATemp
                                .getLongDurationEMA();

                        objJXLWriteNumber = new jxl.write.Number(nColNum++,
                                nRowNum, dblShortDurationEMA, objWCFFloat);
                        objWorksheet.addCell(objJXLWriteNumber);

                        objJXLWriteNumber = new jxl.write.Number(nColNum++,
                                nRowNum, dblLongDurationEMA, objWCFFloat);
                        objWorksheet.addCell(objJXLWriteNumber);

                        String strCurrentTrend = objEMATemp.getTrend();
                        String strAction = objEMATemp.getAction();
                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();

                            objLabel = new Label(nColNum++, nRowNum,
                                    strCurrentTrend);// Col, Row
                            objWorksheet.addCell(objLabel);

                            objLabel = new Label(nColNum++, nRowNum, strAction);// Col,
                                                                                // Row
                            objWorksheet.addCell(objLabel);
                        }
                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    objJXLWriteNumber = new jxl.write.Number(nColNum++, nRowNum,
                            objStockPrice.m_dblROC, objWCFFloat);
                    objWorksheet.addCell(objJXLWriteNumber);
                }
                nRowNum++;
            }
            objMSExcelWorkbook.write();
            objMSExcelWorkbook.close();
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {

        }
    }

}
