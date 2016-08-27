/**
 *
 */
package com.fin.technical;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.itextpdf.text.Document;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * @author Manoj Tiwari
 * @version 1.0.0.0
 * @email manoj dot k dot tiwari at live dot com
 */

public abstract class ReportWriter {
    // Protected restricts access to members of the package and classes
    // inheriting from the class in question.

    // See
    // http://java.sun.com/docs/books/jls/second_edition/html/names.doc.html#62587
    // for details

    protected static String m_strPath = null;

    protected static Util m_objUtil = null;

    protected static String m_strReportFileNameForEMACrossover = Constants.STR_XLS_EMA_CROSSOVER_REPORT_FILE;

    protected static String m_strReportFileNameForPivotPoint = Constants.STR_XLS_PIVOT_POINT_REPORT_FILE;

    protected static String m_strReportFileNameForFibonacciPivotPoint = Constants.STR_XLS_FIBONACCI_PIVOT_POINT_REPORT_FILE;

    protected static String m_strReportFileNameForRSI = Constants.STR_XLS_RSI_REPORT_FILE;

    protected static String m_strReportFileNameForBoll = Constants.STR_XLS_BOLL_REPORT_FILE;

    protected static String m_strReportFileNameForADX = Constants.STR_XLS_ADX_REPORT_FILE;

    protected static String[] m_strHeaderColumnNames = null;

    static {
        if (m_objUtil == null) {
            m_objUtil = Util.getInstance();
        }
        m_strPath = m_objUtil.createOutputDir();
        if (m_strPath == null) {
            System.exit(-1); // calling the method is a must
        }

        m_strHeaderColumnNames = getHeaderForStockTechnicals().split(
                Constants.STR_SPILT_ON_COMMA);
    }

    /**
     * SingletonHolder is loaded on the first execution of
     * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
     * not before.
     */
    private static class SingletonHolder {
        public static ReportWriter m_objReports = null;

        // Later, when I implement Factory pattern, below code needs to be
        // re-written.
    }

    // An example implementation of Factory method.
    public static ReportWriter getInstance() {
        if (m_objUtil == null) {
            m_objUtil = Util.getInstance();
        }
        String strReportFileType = m_objUtil.getReportFileType();
        if (strReportFileType
                .equalsIgnoreCase(Constants.STR_REPORT_FILE_TYPE_CSV)) {
            SingletonHolder.m_objReports = new CSVReportWriter();
            m_strReportFileNameForEMACrossover = Constants.STR_CSV_EMA_CROSSOVER_REPORT_FILE;
            m_strReportFileNameForPivotPoint = Constants.STR_CSV_PIVOT_POINT_REPORT_FILE;
            m_strReportFileNameForFibonacciPivotPoint = Constants.STR_CSV_FIBONACCI_PIVOT_POINT_REPORT_FILE;
            m_strReportFileNameForRSI = Constants.STR_CSV_RSI_REPORT_FILE;
            m_strReportFileNameForBoll = Constants.STR_CSV_BOLL_REPORT_FILE;
            m_strReportFileNameForADX = Constants.STR_CSV_ADX_REPORT_FILE;
        } else if (strReportFileType
                .equalsIgnoreCase(Constants.STR_REPORT_FILE_TYPE_XLS)) {
            SingletonHolder.m_objReports = new XLSReportWriter();
            m_strReportFileNameForEMACrossover = Constants.STR_XLS_EMA_CROSSOVER_REPORT_FILE;
            m_strReportFileNameForPivotPoint = Constants.STR_XLS_PIVOT_POINT_REPORT_FILE;
            m_strReportFileNameForFibonacciPivotPoint = Constants.STR_XLS_FIBONACCI_PIVOT_POINT_REPORT_FILE;
            m_strReportFileNameForRSI = Constants.STR_XLS_RSI_REPORT_FILE;
            m_strReportFileNameForBoll = Constants.STR_XLS_BOLL_REPORT_FILE;
            m_strReportFileNameForADX = Constants.STR_XLS_ADX_REPORT_FILE;
        } else if (strReportFileType
                .equalsIgnoreCase(Constants.STR_REPORT_FILE_TYPE_PDF)) {
            SingletonHolder.m_objReports = new PDFReportWriter();
            m_strReportFileNameForEMACrossover = Constants.STR_PDF_EMA_CROSSOVER_REPORT_FILE;
            m_strReportFileNameForPivotPoint = Constants.STR_PDF_PIVOT_POINT_REPORT_FILE;
            m_strReportFileNameForFibonacciPivotPoint = Constants.STR_PDF_FIBONACCI_PIVOT_POINT_REPORT_FILE;
            m_strReportFileNameForRSI = Constants.STR_PDF_RSI_REPORT_FILE;
            m_strReportFileNameForBoll = Constants.STR_PDF_BOLL_REPORT_FILE;
            m_strReportFileNameForADX = Constants.STR_PDF_ADX_REPORT_FILE;
        }
        return SingletonHolder.m_objReports;
    }

    protected static String getHeaderForStockTechnicals() {
        StringBuffer strBufProgOutput = new StringBuffer();
        try {
            String strIncludeBollingerBandHeader = "";
            String strInclude20DaysSMAHeader = "";
            String strInclude50DaysSMAHeader = "";
            String strInclude200DaysSMAHeader = "";
            String strIncludeRSIHeader = "";
            String strIncludeADLHeader = "";
            String strIncludeCCIHeader = "";
            String strIncludeROCHeader = "";
            String strIncludeADXHeader = "";
            if (m_objUtil.getGenBollReportValue() == 1) {
                if (m_objUtil.getGenBollDebugInfoValue() == 1) {
                    strIncludeBollingerBandHeader = Constants.STR_INCLUDE_BOLLINGER_DEBUG_HEADER;
                } else {
                    strIncludeBollingerBandHeader = Constants.STR_INCLUDE_BOLLINGER_HEADER;
                }
            }
            if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
                strInclude20DaysSMAHeader = Constants.STR_INCLUDE_20DAY_SMA_HEADER;
            }
            if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
                strInclude50DaysSMAHeader = Constants.STR_INCLUDE_50DAY_SMA_HEADER;
            }
            if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
                strInclude200DaysSMAHeader = Constants.STR_INCLUDE_200DAY_SMA_HEADER;
            }
            if (m_objUtil.getGenRSIReportValue() == 1) {
                strIncludeRSIHeader = Constants.STR_INCLUDE_RSI_HEADER;
                if (m_objUtil.getGenRSIDebugInfo() == 1) {
                    strIncludeRSIHeader = Constants.STR_INCLUDE_RSI_DEBUG_HEADER;
                } else {
                    strIncludeRSIHeader = Constants.STR_INCLUDE_RSI_HEADER;
                }
            }
            if (m_objUtil.getGenADLReportValue() == 1) {
                strIncludeADLHeader = Constants.STR_INCLUDE_ADL_HEADER;
            }
            if (m_objUtil.getGenCCIReportValue() == 1) {
                strIncludeCCIHeader = Constants.STR_INCLUDE_CCI_HEADER;
            }
            if (m_objUtil.getGenADXReportValue() == 1) {
                int nPeriod = m_objUtil.getADXDurationValue();
                if (m_objUtil.getGenADXDebugInfo() == 1) {
                    strIncludeADXHeader = String.format(
                            Constants.STR_INCLUDE_ADX_DEBUG_HEADER, nPeriod,
                            nPeriod, nPeriod, nPeriod, nPeriod);
                } else {
                    strIncludeADXHeader = String.format(
                            Constants.STR_INCLUDE_ADX_HEADER, nPeriod, nPeriod);
                }
            }
            String[] strEMADurationValues = m_objUtil.getEMADurationValue();
            String[] strEMAValues = null;
            StringBuffer strBufEMAHeader = new StringBuffer();
            for (int nIdx = 0; nIdx < strEMADurationValues.length; nIdx++) {
                String strEMAValueSet = strEMADurationValues[nIdx];
                strEMAValues = strEMAValueSet
                        .split(Constants.STR_SPILT_ON_COMMA);
                try {
                    for (int nInnerIdx = 0; nInnerIdx < strEMAValues.length; nInnerIdx++) {
                        strBufEMAHeader.append(String.format(
                                Constants.STR_INCLUDE_EMA_HEADER,
                                strEMAValues[nInnerIdx].trim()));
                    }
                    // Is this the preferred EMA Combo?
                    if (m_objUtil.getPreferredEMAComboValue().equalsIgnoreCase(
                            strEMADurationValues[nIdx])
                            || m_objUtil.getPreferredEMAComboValue().length() == 0) {
                        strBufEMAHeader.append(String.format(
                                Constants.STR_INCLUDE_ACTION_HEADER,
                                strEMAValues[0], strEMAValues[1]));
                    }
                } catch (NumberFormatException nfeExcep) {

                }
            }
            if (m_objUtil.getGenROCReportValue() == 1) {
                strIncludeROCHeader = Constants.STR_INCLUDE_ROC_HEADER;
            }
            String strCRLF = System.getProperty("line.separator");
            strBufProgOutput.append(String.format(
                    Constants.STR_STOCK_EMA_REPORT_HEADER,
                    strIncludeBollingerBandHeader, strInclude20DaysSMAHeader,
                    strInclude50DaysSMAHeader, strInclude200DaysSMAHeader,
                    strIncludeRSIHeader, strIncludeADLHeader,
                    strIncludeCCIHeader, strIncludeADXHeader,
                    strBufEMAHeader.toString(), strIncludeROCHeader, /*
                                                 * ,Constants.
                                                 * STR_INCLUDE_ACTION_HEADER
                                                 */
                    strCRLF));
        } catch (Exception eExcep) {
            eExcep.printStackTrace();
        }
        return strBufProgOutput.toString();
    }

    protected String getReportFileNameForEMACrossover() {
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat(
                Constants.STR_DATE_FORMAT);
        String strToday = sdfDateFormat.format(new Date());
        // Report File name with absolute path.
        StringBuffer strBufFileName = new StringBuffer(m_strPath);
        strBufFileName.append(Util.m_strFileSeparator);
        strBufFileName.append(String.format(m_strReportFileNameForEMACrossover,
                strToday));
        return strBufFileName.toString();
    }

    protected String getHeaderForEMACrossover() {
        String strIncludeBollingerBandHeader = "";
        String strInclude20DaysSMAHeader = "";
        String strInclude50DaysSMAHeader = "";
        String strInclude200DaysSMAHeader = "";
        String strIncludeRSIHeader = "";
        String strIncludeADLHeader = "";
        String strIncludeCCIHeader = "";
        String strIncludeROCHeader = "";
        String strIncludeADXHeader = "";
        if (m_objUtil.getGenBollReportValue() == 1) {
            strIncludeBollingerBandHeader = Constants.STR_INCLUDE_BOLLINGER_HEADER;
        }
        if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
            strInclude20DaysSMAHeader = Constants.STR_INCLUDE_20DAY_SMA_HEADER;
        }
        if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
            strInclude50DaysSMAHeader = Constants.STR_INCLUDE_50DAY_SMA_HEADER;
        }
        if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
            strInclude200DaysSMAHeader = Constants.STR_INCLUDE_200DAY_SMA_HEADER;
        }
        if (m_objUtil.getGenRSIReportValue() == 1) {
            strIncludeRSIHeader = Constants.STR_INCLUDE_RSI_HEADER;
        }
        if (m_objUtil.getGenADLReportValue() == 1) {
            strIncludeADLHeader = Constants.STR_INCLUDE_ADL_HEADER;
        }
        if (m_objUtil.getGenCCIReportValue() == 1) {
            strIncludeCCIHeader = Constants.STR_INCLUDE_CCI_HEADER;
        }
        if (m_objUtil.getGenADXReportValue() == 1) {
            int nPeriod = m_objUtil.getADXDurationValue();
            strIncludeADXHeader = String.format(
                    Constants.STR_INCLUDE_ADX_HEADER, nPeriod, nPeriod);

        }
        String[] strEMADurationValues = m_objUtil.getEMADurationValue();
        String[] strEMAValues = null;
        StringBuffer strBufEMAHeader = new StringBuffer();
        for (int nIdx = 0; nIdx < strEMADurationValues.length; nIdx++) {
            String strEMAValueSet = strEMADurationValues[nIdx];
            strEMAValues = strEMAValueSet.split(Constants.STR_SPILT_ON_COMMA);
            try {
                for (int nInnerIdx = 0; nInnerIdx < strEMAValues.length; nInnerIdx++) {
                    strBufEMAHeader.append(String.format(
                            Constants.STR_INCLUDE_EMA_HEADER,
                            strEMAValues[nInnerIdx].trim()));
                }
                // Is this the preferred EMA Combo?
                if (m_objUtil.getPreferredEMAComboValue().equalsIgnoreCase(
                        strEMADurationValues[nIdx])
                        || m_objUtil.getPreferredEMAComboValue().length() == 0) {
                    strBufEMAHeader.append(String.format(
                            Constants.STR_INCLUDE_ACTION_HEADER,
                            strEMAValues[0], strEMAValues[1]));
                }
            } catch (NumberFormatException nfeExcep) {

            }
        }
        if (m_objUtil.getGenROCReportValue() == 1) {
            strIncludeROCHeader = Constants.STR_INCLUDE_ROC_HEADER;
        }
        // The Report file Header.
        String strCRLF = System.getProperty("line.separator");
        StringBuffer strBufLine = new StringBuffer(String.format(
                Constants.STR_EMA_CROSSOVER_REPORT_HEADER,
                strIncludeBollingerBandHeader, strInclude20DaysSMAHeader,
                strInclude50DaysSMAHeader, strInclude200DaysSMAHeader,
                strIncludeRSIHeader, strIncludeADLHeader, strIncludeCCIHeader,
                strIncludeADXHeader, strBufEMAHeader.toString(),
                strIncludeROCHeader, strCRLF));

        return strBufLine.toString();

    }

    protected String getReportFileNameForPivot(boolean bFibonacci) {
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat(
                Constants.STR_DATE_FORMAT);
        String strToday = sdfDateFormat.format(new Date());
        // StringBuffer strBufFileName = new
        // StringBuffer(m_objUtil.getReportLocation());
        StringBuffer strBufFileName = new StringBuffer(m_strPath);
        strBufFileName.append(Util.m_strFileSeparator);
        if (bFibonacci) {
            strBufFileName.append(String.format(
                    ReportWriter.m_strReportFileNameForFibonacciPivotPoint,
                    strToday));
        } else {
            strBufFileName.append(String.format(
                    ReportWriter.m_strReportFileNameForPivotPoint, strToday));
        }
        return strBufFileName.toString();
    }

    protected String getHeaderForPivotReport(boolean bFibonacci) {
        StringBuffer strBufLine = new StringBuffer();
        String strIncludeBollingerBandHeader = "";
        String strInclude20DaysSMAHeader = "";
        String strInclude50DaysSMAHeader = "";
        String strInclude200DaysSMAHeader = "";
        String strIncludeRSIHeader = "";
        String strIncludeADLHeader = "";
        String strIncludeCCIHeader = "";
        String strIncludeROCHeader = "";
        String strIncludeADXHeader = "";
        if (m_objUtil.getGenBollReportValue() == 1) {
            strIncludeBollingerBandHeader = Constants.STR_INCLUDE_BOLLINGER_HEADER;
        }
        if (m_objUtil.getGenSMA20DaysReportValue() == 1) {
            strInclude20DaysSMAHeader = Constants.STR_INCLUDE_20DAY_SMA_HEADER;
        }
        if (m_objUtil.getGenSMA50DaysReportValue() == 1) {
            strInclude50DaysSMAHeader = Constants.STR_INCLUDE_50DAY_SMA_HEADER;
        }
        if (m_objUtil.getGenSMA200DaysReportValue() == 1) {
            strInclude200DaysSMAHeader = Constants.STR_INCLUDE_200DAY_SMA_HEADER;
        }
        if (m_objUtil.getGenRSIReportValue() == 1) {
            strIncludeRSIHeader = Constants.STR_INCLUDE_RSI_HEADER;
        }
        if (m_objUtil.getGenADLReportValue() == 1) {
            strIncludeADLHeader = Constants.STR_INCLUDE_ADL_HEADER;
        }
        if (m_objUtil.getGenCCIReportValue() == 1) {
            strIncludeCCIHeader = Constants.STR_INCLUDE_CCI_HEADER;
        }
        if (m_objUtil.getGenADXReportValue() == 1) {
            int nPeriod = m_objUtil.getADXDurationValue();
            strIncludeADXHeader = String.format(
                    Constants.STR_INCLUDE_ADX_HEADER, nPeriod, nPeriod);
        }
        if (m_objUtil.getGenROCReportValue() == 1) {
            strIncludeROCHeader = Constants.STR_INCLUDE_ROC_HEADER;
        }

        String[] strEMADurationValues = m_objUtil.getEMADurationValue();
        String[] strEMAValues = null;
        StringBuffer strBufEMAHeader = new StringBuffer();
        for (int nIdx = 0; nIdx < strEMADurationValues.length; nIdx++) {
            String strEMAValueSet = strEMADurationValues[nIdx];
            strEMAValues = strEMAValueSet.split(Constants.STR_SPILT_ON_COMMA);
            try {
                for (int nInnerIdx = 0; nInnerIdx < strEMAValues.length; nInnerIdx++) {
                    strBufEMAHeader.append(String.format(
                            Constants.STR_INCLUDE_EMA_HEADER,
                            strEMAValues[nInnerIdx].trim()));
                }
                // Is this the preferred EMA Combo?
                if (m_objUtil.getPreferredEMAComboValue().equalsIgnoreCase(
                        strEMADurationValues[nIdx])
                        || m_objUtil.getPreferredEMAComboValue().length() == 0) {
                    strBufEMAHeader.append(String.format(
                            Constants.STR_INCLUDE_ACTION_HEADER,
                            strEMAValues[0], strEMAValues[1]));
                }
            } catch (NumberFormatException nfeExcep) {

            }
        }
        // The Report file Header.
        String strCRLF = System.getProperty("line.separator");
        if (bFibonacci) {
            strBufLine.append(String.format(
                    Constants.STR_FIBONACCI_PIVOT_POINT_REPORT_HEADER,
                    strIncludeBollingerBandHeader, strInclude20DaysSMAHeader,
                    strInclude50DaysSMAHeader, strInclude200DaysSMAHeader,
                    strIncludeRSIHeader, strIncludeADLHeader,
                    strIncludeCCIHeader, strIncludeROCHeader,
                    strIncludeADXHeader, strBufEMAHeader.toString(), strCRLF));
        } else {
            strBufLine.append(String.format(
                    Constants.STR_PIVOT_POINT_REPORT_HEADER,
                    strIncludeBollingerBandHeader, strInclude20DaysSMAHeader,
                    strInclude50DaysSMAHeader, strInclude200DaysSMAHeader,
                    strIncludeRSIHeader, strIncludeADLHeader,
                    strIncludeCCIHeader, strIncludeROCHeader,
                    strIncludeADXHeader, strBufEMAHeader.toString(), strCRLF));
        }
        return strBufLine.toString();
    }

    protected String getReportFileNameForRSIObOs() {
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat(
                Constants.STR_DATE_FORMAT);
        String strToday = sdfDateFormat.format(new Date());
        // Report File name with absolute path.
        StringBuffer strBufFileName = new StringBuffer(m_strPath);
        strBufFileName.append(Util.m_strFileSeparator);
        strBufFileName.append(String
                .format(m_strReportFileNameForRSI, strToday));
        return strBufFileName.toString();
    }

    protected String getReportFileNameForBollingerBand() {
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat(
                Constants.STR_DATE_FORMAT);
        String strToday = sdfDateFormat.format(new Date());
        // Report File name with absolute path.
        StringBuffer strBufFileName = new StringBuffer(m_strPath);
        strBufFileName.append(Util.m_strFileSeparator);
        strBufFileName.append(String.format(m_strReportFileNameForBoll,
                strToday));
        return strBufFileName.toString();
    }

    protected String getReportFileNameForADX() {
        SimpleDateFormat sdfDateFormat = new SimpleDateFormat(
                Constants.STR_DATE_FORMAT);
        String strToday = sdfDateFormat.format(new Date());
        // Report File name with absolute path.
        StringBuffer strBufFileName = new StringBuffer(m_strPath);
        strBufFileName.append(Util.m_strFileSeparator);
        strBufFileName.append(String
                .format(m_strReportFileNameForADX, strToday));
        return strBufFileName.toString();
    }

    // iText allows to add metadata to the PDF which can be viewed in your Adobe
    // Reader
    // under File -> Properties
    public void addMetaData(Document objDocument) {
        objDocument.addTitle(Constants.STR_DOCUMENT_TITLE);
        objDocument.addAuthor(Constants.STR_DOCUMENT_AUTHOR_NAME);
        objDocument.addCreator(Constants.STR_DOCUMENT_AUTHOR_NAME);
        objDocument.addKeywords(Constants.STR_DOCUMENT_KEY_WORDS);
        // objDocument.addSubject(strStockCode);
        objDocument.addCreationDate();
    }

    public void addMetaData(String strStockCode, Document objDocument) {
        objDocument.addTitle(String.format(Constants.STR_DOCUMENT_TITLE,
                strStockCode));
        objDocument.addAuthor(Constants.STR_DOCUMENT_AUTHOR_NAME);
        objDocument.addCreator(Constants.STR_DOCUMENT_AUTHOR_NAME);
        objDocument.addKeywords(Constants.STR_DOCUMENT_KEY_WORDS);
        objDocument.addSubject(strStockCode);
        objDocument.addCreationDate();
    }

    public abstract void generateEMAReport(String strStockCode,
            Vector<StockPrice> lstHistoricalPrice)
            throws RowsExceededException, WriteException, IOException;

    public abstract void generateEMACrossoverReport()
            throws RowsExceededException, WriteException;

    public abstract void generatePivotPointReport(
            ArrayList<PivotPoint> objListPivotPoint, boolean bFibonacci)
            throws RowsExceededException, WriteException;

    public abstract void generateRSIObOsReport() throws RowsExceededException,
            WriteException;

    public abstract void generateBollingerBandReport(TechnicalIndicators objTI)
            throws RowsExceededException, WriteException;

    public abstract void generateADXReport(TechnicalIndicators objTI)
            throws RowsExceededException, WriteException;

}
