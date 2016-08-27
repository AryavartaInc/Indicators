/**
 *
 */
package com.fin.technical;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

// http://sourceforge.net/projects/itext/files/iText/iText5.1.2/itext-5.1.2.zip/download
// http://www.vogella.de/articles/JavaPDF/article.html

public class PDFReportWriter extends ReportWriter {

    /**
	 *
	 */
    protected PDFReportWriter() {

    }

    public void generateEMAReport(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice)
            throws RowsExceededException, WriteException, IOException {
        if (m_objUtil.getGenStockEMAReportValue() == 0) {
            return;
        }
        int nSizeOfList = lstHistoricalPrice.size();
        if (lstHistoricalPrice == null || nSizeOfList == 0) {
            return;
        }

        Document objDocument = null;
        PdfWriter objPDFWriter = null;
        Font objFont = null;
        // open pdf document
        if (objDocument == null) {
            objDocument = new Document();
        }
        // prepare file with absolute path for pdf document creation.
        StringBuffer strBufFileName = new StringBuffer(m_strPath);
        strBufFileName.append(Util.m_strFileSeparator);

        if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                Constants.STR_STOCK_CODE_DATA_SOURCE_YAHOO) == true) {
            strBufFileName.append(strStockCode).append(
                    Constants.STR_PDF_EMA_REPORT_FILE);
        } else if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                Constants.STR_STOCK_CODE_DATA_SOURCE_GOOGLE) == true) {
            strStockCode = strStockCode.replace(':', '.');
            strBufFileName.append(strStockCode).append(
                    Constants.STR_PDF_EMA_REPORT_FILE);
        } else if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                Constants.STR_STOCK_CODE_DATA_SOURCE_NSEINDIA) == true) {
            strBufFileName.append(strStockCode).append(
                    Constants.STR_PDF_EMA_REPORT_FILE);
        }

        try {
            objPDFWriter = PdfWriter.getInstance(objDocument,
                    new FileOutputStream(strBufFileName.toString()));
        } catch (DocumentException deExcep) {
            deExcep.printStackTrace();
        }
        if (objDocument != null && objPDFWriter != null) {
            objDocument.open();
            objFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
            objFont.setColor(new BaseColor(0xa5, 0x2a, 0x2a));
            // document title
            StringBuffer strBufParagraph = null;
            if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                    Constants.STR_STOCK_CODE_DATA_SOURCE_YAHOO) == true) {
                strBufParagraph = new StringBuffer(
                        "Technical Indicator for Stock having Yahoo Finance Code: ");
            } else if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                    Constants.STR_STOCK_CODE_DATA_SOURCE_GOOGLE) == true) {
                strBufParagraph = new StringBuffer(
                        "Technical Indicator for Stock having Google Finance Code: ");

            } else if (m_objUtil.getDataSourceSettings().equalsIgnoreCase(
                    Constants.STR_STOCK_CODE_DATA_SOURCE_NSEINDIA) == true) {
                strBufParagraph = new StringBuffer(
                        "Technical Indicator for Stock having NSE India Code: ");
            }
            strBufParagraph.append(strStockCode).append(
                    System.getProperty("line.separator"));
            try {
                Paragraph objParagraph = new Paragraph(
                        strBufParagraph.toString(), objFont);
                objParagraph.setAlignment(Element.ALIGN_CENTER);
                objDocument.add(objParagraph);
                objDocument.add(new Paragraph(" "));
                addMetaData(strStockCode, objDocument);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        objFont = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD);
        StockPrice objCurrSIPH = null;
        // create pdf document table's header.
        PdfPTable objPDFPTable = new PdfPTable(m_strHeaderColumnNames.length); // Number
                                                                               // of
                                                                               // Rows
                                                                               // =
                                                                               // nSizeOfList
                                                                               // +
                                                                               // 1
                                                                               // (the
                                                                               // table
                                                                               // header)
        objPDFPTable.setWidthPercentage(100);
        for (int nIdx = 0; nIdx < m_strHeaderColumnNames.length; nIdx++) {
            PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                    m_strHeaderColumnNames[nIdx], objFont));
            objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            objPDFPTable.addCell(objPDFPCell);
        }
        objPDFPTable.completeRow();
        objPDFPTable.setHeaderRows(1);
        // Actual file content.
        int nIdx = 0;
        // http://www.java2s.com/Code/Java/I18N/JavaI18NFormatNumberFormat.htm
        // http://mindprod.com/jgloss/floatingpoint.html
        // create pdf document table and write into it.
        objFont = new Font(Font.FontFamily.COURIER, 5);
        while ((nIdx < nSizeOfList)
                && (objCurrSIPH = (StockPrice) lstHistoricalPrice.get(nIdx)) != null) {
            PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                    objCurrSIPH.getDate(), objFont));
            objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            objPDFPTable.addCell(objPDFPCell);

            objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                    objCurrSIPH.getOpenPrice()), objFont));
            objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            objPDFPTable.addCell(objPDFPCell);

            objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                    objCurrSIPH.getHighPrice()), objFont));
            objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            objPDFPTable.addCell(objPDFPCell);

            objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                    objCurrSIPH.getLowPrice()), objFont));
            objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            objPDFPTable.addCell(objPDFPCell);

            objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                    objCurrSIPH.getClosePrice()), objFont));
            objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            objPDFPTable.addCell(objPDFPCell);

            objPDFPCell = new PdfPCell(new Paragraph(String.valueOf(objCurrSIPH
                    .getVolume()), objFont));
            objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            objPDFPTable.addCell(objPDFPCell);

            // Bollinger Band
            if (m_objUtil.getGenBollReportValue() == 1) {
                // Bollinger band debug Info
                if (m_objUtil.getGenBollDebugInfoValue() == 1) {
                    objPDFPCell = new PdfPCell(
                            new Paragraph(String.format("%.2f",
                                    objCurrSIPH.getDeviationSquared()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f",
                            objCurrSIPH.get20DaysSMAOfDeviationSquared()),
                            objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getStandardDeviation()),
                            objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // the Bollinger Band
                double dblUpperBollgerBand = objCurrSIPH.getUpperBollgerBand();
                double dblLowerBollgerBand = objCurrSIPH.getLowerBollgerBand();
                double dblClosingPrice = objCurrSIPH.getClosePrice();
                double dblBollUpperBandDiff = Math.abs(dblUpperBollgerBand
                        - dblClosingPrice);
                double dblBollLowerBandDiff = Math.abs(dblLowerBollgerBand
                        - dblClosingPrice);

                objPDFPCell = new PdfPCell(new Paragraph(
                        String.format("%.2f - %.2f", dblUpperBollgerBand,
                                dblLowerBollgerBand), objFont));
                double dblPercentRange = dblLowerBollgerBand / 100;
                if (dblBollLowerBandDiff <= dblPercentRange) {
                    objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                }
                dblPercentRange = dblUpperBollgerBand / 100;
                if (dblBollUpperBandDiff <= dblPercentRange) {
                    objPDFPCell.setBackgroundColor(BaseColor.RED);
                }
                objPDFPTable.addCell(objPDFPCell);
            }
            // SMAs
            if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objCurrSIPH.get20DaysSMA()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);
            }
            if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objCurrSIPH.get50DaysSMA()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);
            }
            if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objCurrSIPH.get200DaysSMA()), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);
            }
            // RSI
            if (m_objUtil.getGenRSIReportValue() == 1) {
                // RSI Debug Info
                if (m_objUtil.getGenRSIDebugInfo() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getGain()), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getLoss()), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getAverageGain()), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getAverageLoss()), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(
                            new Paragraph(String.format("%.2f",
                                    objCurrSIPH.getRelativeStrength()), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objCurrSIPH.getRSI()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                if ((int) objCurrSIPH.getRSI() <= 20
                        && (int) objCurrSIPH.getRSI() > 0) {
                    objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                }
                if ((int) objCurrSIPH.getRSI() >= 70) {
                    objPDFPCell.setBackgroundColor(BaseColor.RED);
                }
                objPDFPTable.addCell(objPDFPCell);
            }
            // ADL: Accumulation Distribution Line
            if (m_objUtil.getGenADLReportValue() == 1) {
                objPDFPCell = new PdfPCell(new Paragraph(
                        String.valueOf(objCurrSIPH.getADL()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);
            }
            // CCI: Commodity Channel Index
            if (m_objUtil.getGenCCIReportValue() == 1) {
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objCurrSIPH.getCommodityChannelIndex()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                if ((int) objCurrSIPH.getCommodityChannelIndex() >= 100) {
                    objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                }
                if ((int) objCurrSIPH.getRSI() <= -100) {
                    objPDFPCell.setBackgroundColor(BaseColor.RED);
                }
                objPDFPTable.addCell(objPDFPCell);
            }
            // For Average Directional Index (ADX)
            if (m_objUtil.getGenADXReportValue() == 1) {
                if (m_objUtil.getGenADXDebugInfo() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getTrueRange()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getDMPlus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getDMMinus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getEMAOfTrueRange()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getEMAOfDMPlus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getEMAOfDMMinus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPTable.addCell(objPDFPCell);
                }
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objCurrSIPH.getDIPlus()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objCurrSIPH.getDIMinus()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                objPDFPTable.addCell(objPDFPCell);

                if (m_objUtil.getGenADXDebugInfo() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getDIDiff()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getDISum()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objCurrSIPH.getDX()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.YELLOW);
                    objPDFPTable.addCell(objPDFPCell);
                }
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objCurrSIPH.getADX()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                if ((int) objCurrSIPH.getADX() > 25) {
                    objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                }
                objPDFPTable.addCell(objPDFPCell);
            }
            // For Exponential Moving Average (EMA)
            double dblShortDurationEMA = 0.00;
            double dblLongDurationEMA = 0.00;
            String strCurrentTrend = "";
            String strAction = "";

            EMA objEMATemp = null;
            String[] strEMADurationValues = m_objUtil.getEMADurationValue();
            for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                objEMATemp = ((EMA) objCurrSIPH.getEMAList().get(
                        strEMADurationValues[nEMAIdx]));
                if (objEMATemp != null) {
                    dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                    dblLongDurationEMA = objEMATemp.getLongDurationEMA();

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", dblShortDurationEMA), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", dblLongDurationEMA), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.ORANGE);
                    objPDFPTable.addCell(objPDFPCell);
                    // Is this the preferred EMA Combo?
                    // if(m_objUtil.getPreferredEMAComboValue().equalsIgnoreCase(strEMADurationValues[nIdx])
                    // || m_objUtil.getPreferredEMAComboValue().length() == 0)
                    boolean bCond1 = m_objUtil.getPreferredEMAComboValue()
                            .equalsIgnoreCase(strEMADurationValues[nEMAIdx]);
                    boolean bCond2 = m_objUtil.getPreferredEMAComboValue()
                            .length() == 0;
                    if (bCond1 || bCond2) {
                        strCurrentTrend = objEMATemp.getTrend();
                        strAction = objEMATemp.getAction();
                        objPDFPCell = new PdfPCell(new Paragraph(
                                (strCurrentTrend), objFont));
                        if (strCurrentTrend
                                .compareToIgnoreCase(Constants.STR_INDEX_STOCK_TREND_BULLISH) == 0) {
                            objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                        } else {
                            objPDFPCell.setBackgroundColor(BaseColor.RED);
                        }
                        objPDFPTable.addCell(objPDFPCell);

                        objPDFPCell = new PdfPCell(new Paragraph(strAction,
                                objFont));
                        objPDFPTable.addCell(objPDFPCell);
                    }
                }
            }
            if (m_objUtil.getGenROCReportValue() == 1) {
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objCurrSIPH.getROC()), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);
            }
            objPDFPTable.completeRow();
            nIdx++;
        }
        try {
            objDocument.add(objPDFPTable);
        } catch (DocumentException deExcep) {
            deExcep.printStackTrace();
        }
        objPDFPTable.flushContent();
        objDocument.close();
    }

    public void generateEMACrossoverReport() throws RowsExceededException,
            WriteException {
        if (m_objUtil.getGenMasterEMAReportValue() == 0) {
            return;
        }
        try {
            Document objDocument = null;
            PdfWriter objPDFWriter = null;
            Font objFont = null;
            if (objDocument == null) {
                objDocument = new Document();
            }

            try {
                objPDFWriter = PdfWriter
                        .getInstance(objDocument, new FileOutputStream(
                                getReportFileNameForEMACrossover()));
            } catch (DocumentException deExcep) {
                deExcep.printStackTrace();
            }
            if (objDocument != null && objPDFWriter != null) {
                objDocument.open();
                objFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
                objFont.setColor(new BaseColor(0xa5, 0x2a, 0x2a));

                StringBuffer strBufParagraph = new StringBuffer(
                        "EMA Crossover Report");
                strBufParagraph.append(System.getProperty("line.separator"));
                try {
                    Paragraph objParagraph = new Paragraph(
                            strBufParagraph.toString(), objFont);
                    objParagraph.setAlignment(Element.ALIGN_CENTER);
                    objDocument.add(objParagraph);
                    objDocument.add(new Paragraph(" "));
                    addMetaData(objDocument);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }

            String[] strCells = getHeaderForEMACrossover().split(
                    Constants.STR_SPILT_ON_COMMA);
            objFont = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD);
            PdfPTable objPDFPTable = new PdfPTable(strCells.length); // Number
                                                                     // of Rows
                                                                     // =
                                                                     // nSizeOfList
                                                                     // + 1 (the
                                                                     // table
                                                                     // header)
            objPDFPTable.setWidthPercentage(100);
            for (int nIdx = 0; nIdx < strCells.length; nIdx++) {
                PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                        strCells[nIdx], objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);
            }
            objPDFPTable.completeRow();
            objPDFPTable.setHeaderRows(1);
            // Actual file content.
            objFont = new Font(Font.FontFamily.COURIER, 5);
            while (EMACalculator.m_objListEMACrossover != null
                    && !EMACalculator.m_objListEMACrossover.isEmpty()) {
                EMACrossover objEMACrossover = EMACalculator.m_objListEMACrossover
                        .remove(0);
                StockPrice objStockPrice = objEMACrossover.getStockPrice();

                PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                        objEMACrossover.getStockCode(), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(
                        objStockPrice.getDate(), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getOpenPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getHighPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getLowPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                double dblClosingPrice = objStockPrice.getClosePrice();
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        dblClosingPrice), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(
                        String.valueOf(objStockPrice.getVolume()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                // Bollinger Band
                if (m_objUtil.getGenBollReportValue() == 1) {
                    double dblUpperBollgerBand = objStockPrice
                            .getUpperBollgerBand();
                    double dblLowerBollgerBand = objStockPrice
                            .getLowerBollgerBand();
                    double dblBollUpperBandDiff = Math.abs(dblUpperBollgerBand
                            - dblClosingPrice);
                    double dblBollLowerBandDiff = Math.abs(dblLowerBollgerBand
                            - dblClosingPrice);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f - %.2f", dblUpperBollgerBand,
                            dblLowerBollgerBand), objFont));
                    double dblPercentRange = dblLowerBollgerBand / 100;
                    if (dblBollLowerBandDiff <= dblPercentRange) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }
                    dblPercentRange = dblUpperBollgerBand / 100;
                    if (dblBollUpperBandDiff <= dblPercentRange) {
                        objPDFPCell.setBackgroundColor(BaseColor.RED);
                    }
                    objPDFPTable.addCell(objPDFPCell);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get20DaysSMA()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get50DaysSMA()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get200DaysSMA()), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // RSI
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getRSI()), objFont));
                    if ((int) objStockPrice.getRSI() <= 20) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }
                    if ((int) objStockPrice.getRSI() >= 70) {
                        objPDFPCell.setBackgroundColor(BaseColor.RED);
                    }
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // ADL
                if (m_objUtil.getGenADLReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(
                            String.valueOf(objStockPrice.getADL()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // CCI
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getCommodityChannelIndex()),
                            objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // ADX
                if (m_objUtil.getGenADXReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getDIPlus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getDIMinus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getADX()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // For Exponential Moving Average (EMA)
                double dblShortDurationEMA = 0.00;
                double dblLongDurationEMA = 0.00;
                String strCurrentTrend = "";
                String strAction = "";

                EMA objEMATemp = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                        dblLongDurationEMA = objEMATemp.getLongDurationEMA();

                        objPDFPCell = new PdfPCell(new Paragraph(String.format(
                                "%.2f", dblShortDurationEMA), objFont));
                        objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                        objPDFPTable.addCell(objPDFPCell);

                        objPDFPCell = new PdfPCell(new Paragraph(String.format(
                                "%.2f", dblLongDurationEMA), objFont));
                        objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        objPDFPCell.setBackgroundColor(BaseColor.ORANGE);
                        objPDFPTable.addCell(objPDFPCell);
                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();
                            objPDFPCell = new PdfPCell(new Paragraph(
                                    (strCurrentTrend), objFont));
                            if (strCurrentTrend
                                    .compareToIgnoreCase(Constants.STR_INDEX_STOCK_TREND_BULLISH) == 0) {
                                objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                            } else {
                                objPDFPCell.setBackgroundColor(BaseColor.RED);
                            }
                            objPDFPTable.addCell(objPDFPCell);

                            objPDFPCell = new PdfPCell(new Paragraph(strAction,
                                    objFont));
                            objPDFPTable.addCell(objPDFPCell);
                        }
                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                            objStockPrice.m_dblROC), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                objPDFPTable.completeRow();
            }
            try {
                objDocument.add(objPDFPTable);
            } catch (DocumentException deExcep) {
                deExcep.printStackTrace();
            }
            objPDFPTable.flushContent();
            objDocument.close();
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {

        }
    }

    // public static final String STR_PIVOT_POINT_REPORT_HEADER =
    // "  Stock-Code,  High,  Low,  Close,  S1,  S2,  S3,  Pivot,  R1,  R2,  R3 \n";
    // public static final String STR_PIVOT_POINT_REPORT_HEADER =
    // "  Stock-Code,  High,  Low,  Close,  Fibonacci-S1,  Fibonacci-S2,  Fibonacci-S3,  Range,  Fibonacci-R1,  Fibonacci-R2,  Fibonacci-R3 \n";
    public void generatePivotPointReport(
            ArrayList<PivotPoint> objListPivotPoint, boolean bFibonacci)
            throws RowsExceededException, WriteException {
        if (m_objUtil.getGenStockEMAReportValue() == 0) {
            return;
        }
        Document objDocument = null;
        PdfWriter objPDFWriter = null;
        Font objFont = null;

        if (objDocument == null) {
            objDocument = new Document();
        }

        try {
            SimpleDateFormat sdfDateFormat = new SimpleDateFormat(
                    Constants.STR_DATE_FORMAT);
            String strToday = sdfDateFormat.format(new Date());

            try {
                objPDFWriter = PdfWriter.getInstance(objDocument,
                        new FileOutputStream(
                                getReportFileNameForPivot(bFibonacci)));
            } catch (DocumentException deExcep) {
                deExcep.printStackTrace();
            }
            if (objDocument != null && objPDFWriter != null) {
                objDocument.open();
                objFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
                objFont.setColor(new BaseColor(0xa5, 0x2a, 0x2a));

                StringBuffer strBufParagraph = new StringBuffer(
                        "Pivot Point Report and other technical indicators for "
                                + strToday);
                strBufParagraph.append(System.getProperty("line.separator"));
                try {
                    Paragraph objParagraph = new Paragraph(
                            strBufParagraph.toString(), objFont);
                    objParagraph.setAlignment(Element.ALIGN_CENTER);
                    objDocument.add(objParagraph);
                    objDocument.add(new Paragraph(" "));
                    addMetaData(objDocument);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }
            objFont = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD);
            String[] strCells = getHeaderForPivotReport(bFibonacci).split(
                    Constants.STR_SPILT_ON_COMMA);
            PdfPTable objPDFPTable = new PdfPTable(strCells.length); // Number
                                                                     // of Rows
                                                                     // =
                                                                     // nSizeOfList
                                                                     // + 1 (the
                                                                     // table
                                                                     // header)
            objPDFPTable.setWidthPercentage(100);
            for (int nIdx = 0; nIdx < strCells.length; nIdx++) {
                // Creates a cell which, when added to the sheet, will be
                // presented at the specified column and row co-ordinates
                // and will contain the specified text.
                PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                        strCells[nIdx], objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);
            }
            objPDFPTable.completeRow();
            objPDFPTable.setHeaderRows(1);

            // Actual file content.
            objFont = new Font(Font.FontFamily.COURIER, 5);
            for (int nIdx = 0; nIdx < objListPivotPoint.size(); nIdx++) {
                PivotPoint objPivotPoint = objListPivotPoint.get(nIdx);

                PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                        objPivotPoint.getStockCode(), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(
                        String.format("%.2f", objPivotPoint
                                .getPreviousDayStockPrice().getHighPrice()),
                        objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(
                        String.format("%.2f", objPivotPoint
                                .getPreviousDayStockPrice().getLowPrice()),
                        objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);

                double dblClosingPrice = objPivotPoint
                        .getCurrentDayStockPrice().getClosePrice();
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        dblClosingPrice), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objPivotPoint.getSupport1()), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objPivotPoint.getSupport2()), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objPivotPoint.getSupport3()), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objPivotPoint.getPivotPoint()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objPivotPoint.getResistance1()), objFont));
                objPDFPCell.setBackgroundColor(new BaseColor(0xa5, 0x2a, 0x2a));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objPivotPoint.getResistance2()), objFont));
                objPDFPCell.setBackgroundColor(new BaseColor(0xa5, 0x2a, 0x2a));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objPivotPoint.getResistance3()), objFont));
                objPDFPCell.setBackgroundColor(new BaseColor(0xa5, 0x2a, 0x2a));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPTable.addCell(objPDFPCell);
                // Bollinger Band
                if (m_objUtil.getGenBollReportValue() == 1) {
                    double dblUpperBollgerBand = objPivotPoint
                            .getCurrentDayStockPrice().getUpperBollgerBand();
                    double dblLowerBollgerBand = objPivotPoint
                            .getCurrentDayStockPrice().getLowerBollgerBand();

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f - %.2f", dblUpperBollgerBand,
                            dblLowerBollgerBand), objFont));
                    double dblPercentRange = dblUpperBollgerBand / 100;
                    double dblBollUpperBandDiff = Math.abs(dblUpperBollgerBand
                            - dblClosingPrice);
                    double dblBollLowerBandDiff = Math.abs(dblLowerBollgerBand
                            - dblClosingPrice);

                    if (dblBollUpperBandDiff <= dblPercentRange) {
                        objPDFPCell.setBackgroundColor(BaseColor.RED);
                    }
                    dblPercentRange = dblLowerBollgerBand / 100;
                    if (dblBollLowerBandDiff <= dblPercentRange) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objPivotPoint.getCurrentDayStockPrice()
                                    .get20DaysSMA()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objPivotPoint.getCurrentDayStockPrice()
                                    .get50DaysSMA()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objPivotPoint.getCurrentDayStockPrice()
                                    .get200DaysSMA()), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objPivotPoint.getCurrentDayStockPrice()
                                    .getRSI()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    if ((int) objPivotPoint.getCurrentDayStockPrice().getRSI() <= 20
                            && (int) objPivotPoint.getCurrentDayStockPrice()
                                    .getRSI() > 0) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }
                    if ((int) objPivotPoint.getCurrentDayStockPrice().getRSI() >= 70) {
                        objPDFPCell.setBackgroundColor(BaseColor.RED);
                    }
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenADLReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(
                            String.valueOf(objPivotPoint
                                    .getCurrentDayStockPrice().getADL()),
                            objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenCCIReportValue() == 1) {

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objPivotPoint.getCurrentDayStockPrice()
                                    .getCommodityChannelIndex()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    if ((int) objPivotPoint.getCurrentDayStockPrice()
                            .getCommodityChannelIndex() >= 100) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }
                    if ((int) objPivotPoint.getCurrentDayStockPrice().getRSI() <= -100) {
                        objPDFPCell.setBackgroundColor(BaseColor.RED);
                    }
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenROCReportValue() == 1) {

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objPivotPoint.getCurrentDayStockPrice()
                                    .getROC()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenADXReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objPivotPoint.getCurrentDayStockPrice()
                                    .getDIPlus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objPivotPoint.getCurrentDayStockPrice()
                                    .getDIMinus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objPivotPoint.getCurrentDayStockPrice()
                                    .getADX()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    if ((int) objPivotPoint.getCurrentDayStockPrice().getADX() > 25) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }
                    objPDFPTable.addCell(objPDFPCell);
                }
                // For Exponential Moving Average (EMA)
                double dblShortDurationEMA = 0.00;
                double dblLongDurationEMA = 0.00;
                String strCurrentTrend = "";
                String strAction = "";

                EMA objEMATemp = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objPivotPoint.getCurrentDayStockPrice()
                            .getEMAList().get(strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                        dblLongDurationEMA = objEMATemp.getLongDurationEMA();

                        objPDFPCell = new PdfPCell(new Paragraph(String.format(
                                "%.2f", dblShortDurationEMA), objFont));
                        objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                        objPDFPTable.addCell(objPDFPCell);

                        objPDFPCell = new PdfPCell(new Paragraph(String.format(
                                "%.2f", dblLongDurationEMA), objFont));
                        objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        objPDFPCell.setBackgroundColor(BaseColor.ORANGE);
                        objPDFPTable.addCell(objPDFPCell);
                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();
                            objPDFPCell = new PdfPCell(new Paragraph(
                                    (strCurrentTrend), objFont));
                            if (strCurrentTrend
                                    .compareToIgnoreCase(Constants.STR_INDEX_STOCK_TREND_BULLISH) == 0) {
                                objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                            } else {
                                objPDFPCell.setBackgroundColor(BaseColor.RED);
                            }
                            objPDFPTable.addCell(objPDFPCell);

                            objPDFPCell = new PdfPCell(new Paragraph(strAction,
                                    objFont));
                            objPDFPTable.addCell(objPDFPCell);
                        }
                    }
                }

                objPDFPTable.completeRow();
            }
            try {
                objDocument.add(objPDFPTable);
            } catch (DocumentException deExcep) {
                deExcep.printStackTrace();
            }
            objPDFPTable.flushContent();
            objDocument.close();
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {
        }
    }

    public void generateRSIObOsReport() throws RowsExceededException,
            WriteException {
        try {
            Document objDocument = null;
            PdfWriter objPDFWriter = null;
            Font objFont = null;
            if (objDocument == null) {
                objDocument = new Document();
            }

            try {
                objPDFWriter = PdfWriter.getInstance(objDocument,
                        new FileOutputStream(getReportFileNameForRSIObOs()));
            } catch (DocumentException deExcep) {
                deExcep.printStackTrace();
            }
            if (objDocument != null && objPDFWriter != null) {
                objDocument.open();
                objFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
                objFont.setColor(new BaseColor(0xa5, 0x2a, 0x2a));

                StringBuffer strBufParagraph = new StringBuffer(
                        "Stocks that are either Overbought or Oversold on RSI. Stocks having RSI less than 20 are Oversold and above 70 are Overbought.");
                strBufParagraph.append(System.getProperty("line.separator"));
                try {
                    Paragraph objParagraph = new Paragraph(
                            strBufParagraph.toString(), objFont);
                    objParagraph.setAlignment(Element.ALIGN_CENTER);
                    objDocument.add(objParagraph);
                    objDocument.add(new Paragraph(" "));
                    addMetaData(objDocument);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }

            String[] strCells = getHeaderForEMACrossover().split(
                    Constants.STR_SPILT_ON_COMMA);
            objFont = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD);
            PdfPTable objPDFPTable = new PdfPTable(strCells.length); // Number
                                                                     // of Rows
                                                                     // =
                                                                     // nSizeOfList
                                                                     // + 1 (the
                                                                     // table
                                                                     // header)
            objPDFPTable.setWidthPercentage(100);
            for (int nIdx = 0; nIdx < strCells.length; nIdx++) {
                PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                        strCells[nIdx], objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);
            }
            objPDFPTable.completeRow();
            objPDFPTable.setHeaderRows(1);
            // Actual file content.
            objFont = new Font(Font.FontFamily.COURIER, 5);
            while (RSICalculator.m_objListRSI != null
                    && !RSICalculator.m_objListRSI.isEmpty()) {
                RSI objRSI = RSICalculator.m_objListRSI.remove(0);
                StockPrice objStockPrice = objRSI.getStockPrice();

                PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                        objRSI.getStockCode(), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(
                        objStockPrice.getDate(), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getOpenPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getHighPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getLowPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);
                double dblClosingPrice = objStockPrice.getClosePrice();
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        dblClosingPrice), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(
                        String.valueOf(objStockPrice.getVolume()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                // Bollinger Band
                if (m_objUtil.getGenBollReportValue() == 1) {
                    double dblUpperBollgerBand = objStockPrice
                            .getUpperBollgerBand();
                    double dblLowerBollgerBand = objStockPrice
                            .getLowerBollgerBand();

                    double dblBollUpperBandDiff = Math.abs(dblUpperBollgerBand
                            - dblClosingPrice);
                    double dblBollLowerBandDiff = Math.abs(dblLowerBollgerBand
                            - dblClosingPrice);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f - %.2f", dblUpperBollgerBand,
                            dblLowerBollgerBand), objFont));
                    double dblPercentRange = dblLowerBollgerBand / 100;
                    if (dblBollLowerBandDiff <= dblPercentRange) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }
                    dblPercentRange = dblUpperBollgerBand / 100;
                    if (dblBollUpperBandDiff <= dblPercentRange) {
                        objPDFPCell.setBackgroundColor(BaseColor.RED);
                    }
                    objPDFPTable.addCell(objPDFPCell);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get20DaysSMA()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get50DaysSMA()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get200DaysSMA()), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // RSI
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getRSI()), objFont));
                    if ((int) objStockPrice.getRSI() <= 20) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }
                    if ((int) objStockPrice.getRSI() >= 70) {
                        objPDFPCell.setBackgroundColor(BaseColor.RED);
                    }

                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // ADL
                if (m_objUtil.getGenADLReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(
                            String.valueOf(objStockPrice.getADL()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // CCI
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getCommodityChannelIndex()),
                            objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // ADX
                if (m_objUtil.getGenADXReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getDIPlus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getDIMinus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getADX()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // For Exponential Moving Average (EMA)
                double dblShortDurationEMA = 0.00;
                double dblLongDurationEMA = 0.00;
                String strCurrentTrend = "";
                String strAction = "";

                EMA objEMATemp = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                        dblLongDurationEMA = objEMATemp.getLongDurationEMA();

                        objPDFPCell = new PdfPCell(new Paragraph(String.format(
                                "%.2f", dblShortDurationEMA), objFont));
                        objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                        objPDFPTable.addCell(objPDFPCell);

                        objPDFPCell = new PdfPCell(new Paragraph(String.format(
                                "%.2f", dblLongDurationEMA), objFont));
                        objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        objPDFPCell.setBackgroundColor(BaseColor.ORANGE);
                        objPDFPTable.addCell(objPDFPCell);
                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();
                            objPDFPCell = new PdfPCell(new Paragraph(
                                    (strCurrentTrend), objFont));
                            if (strCurrentTrend
                                    .compareToIgnoreCase(Constants.STR_INDEX_STOCK_TREND_BULLISH) == 0) {
                                objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                            } else {
                                objPDFPCell.setBackgroundColor(BaseColor.RED);
                            }
                            objPDFPTable.addCell(objPDFPCell);

                            objPDFPCell = new PdfPCell(new Paragraph(strAction,
                                    objFont));
                            objPDFPTable.addCell(objPDFPCell);
                        }
                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                            objStockPrice.m_dblROC), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                objPDFPTable.completeRow();
            }
            try {
                objDocument.add(objPDFPTable);
            } catch (DocumentException deExcep) {
                deExcep.printStackTrace();
            }
            objPDFPTable.flushContent();
            objDocument.close();
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {

        }
    }

    public void generateBollingerBandReport(TechnicalIndicators objTI)
            throws RowsExceededException, WriteException {
        try {
            if (objTI.m_objListBollingerBand.isEmpty()) {
                return;
            }
            Document objDocument = null;
            PdfWriter objPDFWriter = null;
            Font objFont = null;
            if (objDocument == null) {
                objDocument = new Document();
            }

            try {
                objPDFWriter = PdfWriter.getInstance(objDocument,
                        new FileOutputStream(
                                getReportFileNameForBollingerBand()));
            } catch (DocumentException deExcep) {
                deExcep.printStackTrace();
            }
            if (objDocument != null && objPDFWriter != null) {
                objDocument.open();
                objFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
                objFont.setColor(new BaseColor(0xa5, 0x2a, 0x2a));

                StringBuffer strBufParagraph = new StringBuffer(
                        "Bolling Band based report for Stocks.");
                strBufParagraph.append(System.getProperty("line.separator"));
                try {
                    Paragraph objParagraph = new Paragraph(
                            strBufParagraph.toString(), objFont);
                    objParagraph.setAlignment(Element.ALIGN_CENTER);
                    objDocument.add(objParagraph);
                    objDocument.add(new Paragraph(" "));
                    addMetaData(objDocument);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }

            String[] strCells = getHeaderForEMACrossover().split(
                    Constants.STR_SPILT_ON_COMMA);
            objFont = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD);
            PdfPTable objPDFPTable = new PdfPTable(strCells.length); // Number
                                                                     // of Rows
                                                                     // =
                                                                     // nSizeOfList
                                                                     // + 1 (the
                                                                     // table
                                                                     // header)
            objPDFPTable.setWidthPercentage(100);
            for (int nIdx = 0; nIdx < strCells.length; nIdx++) {
                PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                        strCells[nIdx], objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);
            }
            objPDFPTable.completeRow();
            objPDFPTable.setHeaderRows(1);
            // Actual file content.
            objFont = new Font(Font.FontFamily.COURIER, 5);
            while (objTI.m_objListBollingerBand != null
                    && !objTI.m_objListBollingerBand.isEmpty()) {
                StockPriceInfo objStockPriceInfo = objTI.m_objListBollingerBand
                        .remove(0);
                StockPrice objStockPrice = objStockPriceInfo.getStockPrice();

                PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                        objStockPriceInfo.getStockCode(), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(
                        objStockPrice.getDate(), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getOpenPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getHighPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getLowPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);
                double dblClosingPrice = objStockPrice.getClosePrice();
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        dblClosingPrice), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(
                        String.valueOf(objStockPrice.getVolume()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                // Bollinger Band

                if (m_objUtil.getGenBollReportValue() == 1) {
                    double dblUpperBollgerBand = objStockPrice
                            .getUpperBollgerBand();
                    double dblLowerBollgerBand = objStockPrice
                            .getLowerBollgerBand();
                    double dblBollUpperBandDiff = Math.abs(dblUpperBollgerBand
                            - dblClosingPrice);
                    double dblBollLowerBandDiff = Math.abs(dblLowerBollgerBand
                            - dblClosingPrice);
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f - %.2f", objStockPrice.getUpperBollgerBand(),
                            objStockPrice.getLowerBollgerBand()), objFont));
                    double dblPercentRange = dblUpperBollgerBand / 100;
                    if (dblBollUpperBandDiff <= dblPercentRange) {
                        objPDFPCell.setBackgroundColor(BaseColor.RED);
                    }
                    dblPercentRange = dblLowerBollgerBand / 100;
                    if (dblBollLowerBandDiff <= dblPercentRange) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }

                    objPDFPTable.addCell(objPDFPCell);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get20DaysSMA()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get50DaysSMA()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get200DaysSMA()), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // RSI
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getRSI()), objFont));
                    if ((int) objStockPrice.getRSI() <= 20) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }
                    if ((int) objStockPrice.getRSI() >= 70) {
                        objPDFPCell.setBackgroundColor(BaseColor.RED);
                    }

                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // ADL
                if (m_objUtil.getGenADLReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(
                            String.valueOf(objStockPrice.getADL()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // CCI
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getCommodityChannelIndex()),
                            objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // ADX
                if (m_objUtil.getGenADXReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getDIPlus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getDIMinus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getADX()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // For Exponential Moving Average (EMA)
                double dblShortDurationEMA = 0.00;
                double dblLongDurationEMA = 0.00;
                String strCurrentTrend = "";
                String strAction = "";

                EMA objEMATemp = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                        dblLongDurationEMA = objEMATemp.getLongDurationEMA();

                        objPDFPCell = new PdfPCell(new Paragraph(String.format(
                                "%.2f", dblShortDurationEMA), objFont));
                        objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                        objPDFPTable.addCell(objPDFPCell);

                        objPDFPCell = new PdfPCell(new Paragraph(String.format(
                                "%.2f", dblLongDurationEMA), objFont));
                        objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        objPDFPCell.setBackgroundColor(BaseColor.ORANGE);
                        objPDFPTable.addCell(objPDFPCell);
                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();
                            objPDFPCell = new PdfPCell(new Paragraph(
                                    (strCurrentTrend), objFont));
                            if (strCurrentTrend
                                    .compareToIgnoreCase(Constants.STR_INDEX_STOCK_TREND_BULLISH) == 0) {
                                objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                            } else {
                                objPDFPCell.setBackgroundColor(BaseColor.RED);
                            }
                            objPDFPTable.addCell(objPDFPCell);

                            objPDFPCell = new PdfPCell(new Paragraph(strAction,
                                    objFont));
                            objPDFPTable.addCell(objPDFPCell);
                        }
                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                            objStockPrice.m_dblROC), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                objPDFPTable.completeRow();
            }
            try {
                objDocument.add(objPDFPTable);
            } catch (DocumentException deExcep) {
                deExcep.printStackTrace();
            }
            objPDFPTable.flushContent();
            objDocument.close();
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {

        }
    }

    public void generateADXReport(TechnicalIndicators objTI)
            throws RowsExceededException, WriteException {
        try {
            if (objTI.m_objListADX.isEmpty()) {
                return;
            }
            Document objDocument = null;
            PdfWriter objPDFWriter = null;
            Font objFont = null;
            if (objDocument == null) {
                objDocument = new Document();
            }

            try {
                objPDFWriter = PdfWriter.getInstance(objDocument,
                        new FileOutputStream(getReportFileNameForADX()));
            } catch (DocumentException deExcep) {
                deExcep.printStackTrace();
            }
            if (objDocument != null && objPDFWriter != null) {
                objDocument.open();
                objFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);
                objFont.setColor(new BaseColor(0xa5, 0x2a, 0x2a));

                StringBuffer strBufParagraph = new StringBuffer(
                        "ADX and Directional Index based report for Stocks.");
                strBufParagraph.append(System.getProperty("line.separator"));
                try {
                    Paragraph objParagraph = new Paragraph(
                            strBufParagraph.toString(), objFont);
                    objParagraph.setAlignment(Element.ALIGN_CENTER);
                    objDocument.add(objParagraph);
                    objDocument.add(new Paragraph(" "));
                    addMetaData(objDocument);
                } catch (DocumentException e) {
                    e.printStackTrace();
                }
            }

            String[] strCells = getHeaderForEMACrossover().split(
                    Constants.STR_SPILT_ON_COMMA);
            objFont = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD);
            PdfPTable objPDFPTable = new PdfPTable(strCells.length); // Number
                                                                     // of Rows
                                                                     // =
                                                                     // nSizeOfList
                                                                     // + 1 (the
                                                                     // table
                                                                     // header)
            objPDFPTable.setWidthPercentage(100);
            for (int nIdx = 0; nIdx < strCells.length; nIdx++) {
                PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                        strCells[nIdx], objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);
            }
            objPDFPTable.completeRow();
            objPDFPTable.setHeaderRows(1);
            // Actual file content.
            objFont = new Font(Font.FontFamily.COURIER, 5);
            while (objTI.m_objListADX != null && !objTI.m_objListADX.isEmpty()) {
                StockPriceInfo objStockPriceInfo = objTI.m_objListADX.remove(0);
                StockPrice objStockPrice = objStockPriceInfo.getStockPrice();

                PdfPCell objPDFPCell = new PdfPCell(new Paragraph(
                        objStockPriceInfo.getStockCode(), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(
                        objStockPrice.getDate(), objFont));
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getOpenPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getHighPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        objStockPrice.getLowPrice()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);
                double dblClosingPrice = objStockPrice.getClosePrice();
                objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                        dblClosingPrice), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                objPDFPCell = new PdfPCell(new Paragraph(
                        String.valueOf(objStockPrice.getVolume()), objFont));
                objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                objPDFPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                objPDFPTable.addCell(objPDFPCell);

                // Bollinger Band

                if (m_objUtil.getGenBollReportValue() == 1) {
                    double dblUpperBollgerBand = objStockPrice
                            .getUpperBollgerBand();
                    double dblLowerBollgerBand = objStockPrice
                            .getLowerBollgerBand();
                    double dblBollUpperBandDiff = Math.abs(dblUpperBollgerBand
                            - dblClosingPrice);
                    double dblBollLowerBandDiff = Math.abs(dblLowerBollgerBand
                            - dblClosingPrice);
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f - %.2f", objStockPrice.getUpperBollgerBand(),
                            objStockPrice.getLowerBollgerBand()), objFont));
                    double dblPercentRange = dblUpperBollgerBand / 100;
                    if (dblBollUpperBandDiff <= dblPercentRange) {
                        objPDFPCell.setBackgroundColor(BaseColor.RED);
                    }
                    dblPercentRange = dblLowerBollgerBand / 100;
                    if (dblBollLowerBandDiff <= dblPercentRange) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }

                    objPDFPTable.addCell(objPDFPCell);
                }
                // SMAs
                if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get20DaysSMA()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get50DaysSMA()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.get200DaysSMA()), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // RSI
                if (m_objUtil.getGenRSIReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getRSI()), objFont));
                    if ((int) objStockPrice.getRSI() <= 20) {
                        objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                    }
                    if ((int) objStockPrice.getRSI() >= 70) {
                        objPDFPCell.setBackgroundColor(BaseColor.RED);
                    }

                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // ADL
                if (m_objUtil.getGenADLReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(
                            String.valueOf(objStockPrice.getADL()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // CCI
                if (m_objUtil.getGenCCIReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getCommodityChannelIndex()),
                            objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // ADX
                if (m_objUtil.getGenADXReportValue() == 1) {
                    double dblADX = objStockPrice.getADX();
                    double dblDIPlus = objStockPrice.getDIPlus();
                    double dblDIMinus = objStockPrice.getDIMinus();
                    boolean bCondBullish = dblADX > 25.0
                            && dblDIPlus > dblDIMinus;
                    boolean bCondBearish = dblADX > 25.0
                            && dblDIMinus > dblDIPlus;
                    BaseColor objBaseColor = null;
                    if (bCondBullish) {
                        objBaseColor = BaseColor.GREEN;
                    } else if (bCondBearish) {
                        objBaseColor = BaseColor.RED;
                    }

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getDIPlus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(objBaseColor);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getDIMinus()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(objBaseColor);
                    objPDFPTable.addCell(objPDFPCell);

                    objPDFPCell = new PdfPCell(new Paragraph(String.format(
                            "%.2f", objStockPrice.getADX()), objFont));
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPCell.setBackgroundColor(objBaseColor);
                    objPDFPTable.addCell(objPDFPCell);
                }
                // For Exponential Moving Average (EMA)
                double dblShortDurationEMA = 0.00;
                double dblLongDurationEMA = 0.00;
                String strCurrentTrend = "";
                String strAction = "";

                EMA objEMATemp = null;
                String[] strEMADurationValues = m_objUtil.getEMADurationValue();
                for (int nEMAIdx = 0; nEMAIdx < strEMADurationValues.length; nEMAIdx++) {
                    objEMATemp = ((EMA) objStockPrice.getEMAList().get(
                            strEMADurationValues[nEMAIdx]));
                    if (objEMATemp != null) {
                        dblShortDurationEMA = objEMATemp.getShortDurationEMA();
                        dblLongDurationEMA = objEMATemp.getLongDurationEMA();

                        objPDFPCell = new PdfPCell(new Paragraph(String.format(
                                "%.2f", dblShortDurationEMA), objFont));
                        objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                        objPDFPTable.addCell(objPDFPCell);

                        objPDFPCell = new PdfPCell(new Paragraph(String.format(
                                "%.2f", dblLongDurationEMA), objFont));
                        objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        objPDFPCell.setBackgroundColor(BaseColor.ORANGE);
                        objPDFPTable.addCell(objPDFPCell);
                        // Is this the preferred EMA Combo?
                        if (m_objUtil
                                .getPreferredEMAComboValue()
                                .equalsIgnoreCase(strEMADurationValues[nEMAIdx])
                                || m_objUtil.getPreferredEMAComboValue()
                                        .length() == 0) {
                            strCurrentTrend = objEMATemp.getTrend();
                            strAction = objEMATemp.getAction();
                            objPDFPCell = new PdfPCell(new Paragraph(
                                    (strCurrentTrend), objFont));
                            if (strCurrentTrend
                                    .compareToIgnoreCase(Constants.STR_INDEX_STOCK_TREND_BULLISH) == 0) {
                                objPDFPCell.setBackgroundColor(BaseColor.GREEN);
                            } else {
                                objPDFPCell.setBackgroundColor(BaseColor.RED);
                            }
                            objPDFPTable.addCell(objPDFPCell);

                            objPDFPCell = new PdfPCell(new Paragraph(strAction,
                                    objFont));
                            objPDFPTable.addCell(objPDFPCell);
                        }
                    }
                }
                if (m_objUtil.getGenROCReportValue() == 1) {
                    objPDFPCell = new PdfPCell(new Paragraph(String.format("%.2f",
                            objStockPrice.m_dblROC), objFont));
                    objPDFPCell.setBackgroundColor(BaseColor.GRAY);
                    objPDFPCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    objPDFPTable.addCell(objPDFPCell);
                }
                objPDFPTable.completeRow();
            }
            try {
                objDocument.add(objPDFPTable);
            } catch (DocumentException deExcep) {
                deExcep.printStackTrace();
            }
            objPDFPTable.flushContent();
            objDocument.close();
        } catch (IOException ioExcep) {
            ioExcep.printStackTrace();
        } finally {
            // what???
        }
    }

}
