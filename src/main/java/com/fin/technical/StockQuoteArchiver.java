package com.fin.technical;

/**
 * @author Manoj Tiwari
 * @version 1.0
 * @email manoj dot k dot tiwari at live dot com
 */

public class StockQuoteArchiver extends Thread {
    private String[] m_strStockCodes = null;

    protected static String m_strStockQuotePath = null;

    public static ThreadGroup m_objTGSQAManager = null;

    protected static Util m_objUtil = null;

    static {
        if (m_objUtil == null) {
            m_objUtil = Util.getInstance();
        }
        m_strStockQuotePath = m_objUtil.createStockQuoteDir();
        if (m_strStockQuotePath == null) {
            System.exit(-1); // calling the method is a must
        }
    }

    public StockQuoteArchiver() {
    }

    public StockQuoteArchiver(String[] strStockCodes) {
        m_strStockCodes = strStockCodes;
    }

    public StockQuoteArchiver(String strThreadName, String[] strStockCodes) {
        super(strThreadName);
        m_strStockCodes = strStockCodes;
    }

    public StockQuoteArchiver(ThreadGroup objTG, String strThreadName,
            String[] strStockCodes) {

        super(objTG, strThreadName);
        m_strStockCodes = strStockCodes;
    }

    public void archiveStockQuote() {

        String[] strStockCodes = m_objUtil.getStockCodeValue();
        int nStockCount = strStockCodes.length;
        int nNumberOfStockArchiverThread = m_objUtil
                .getNumberOfStockArchiverThread();
        int nQuotient = nStockCount / nNumberOfStockArchiverThread;
        int nRemainder = nStockCount % nNumberOfStockArchiverThread;
        int nFromIndex = 0;

        m_objTGSQAManager = new ThreadGroup(
                Constants.STR_STOCK_ARCHIVER_THREAD_GROUP_NAME);

        if(nQuotient > 0) {
            nNumberOfStockArchiverThread = m_objUtil
                    .getNumberOfStockArchiverThread();
            for (int nIdx = 1; nIdx <= nNumberOfStockArchiverThread; nIdx++) {
                String[] strStockCodesTemp = new String[nQuotient];
                System.arraycopy(strStockCodes, nFromIndex, strStockCodesTemp, 0,
                        nQuotient);
                nFromIndex = nFromIndex + nQuotient;
                String strThreadName = String.format(
                        Constants.STR_STOCK_ARCHIVER_THREAD_NAME,
                        Constants.STR_STOCK_ARCHIVER_THREAD_GROUP_NAME, nIdx);
                StockQuoteArchiver objSQA = new StockQuoteArchiver(
                        m_objTGSQAManager, strThreadName, strStockCodesTemp);
                objSQA.start();
            }
        } else {
            nNumberOfStockArchiverThread = 0;
        }
        if (nRemainder > 0) {
            String[] strStockCodesTemp = new String[nRemainder];
            System.arraycopy(strStockCodes, nFromIndex, strStockCodesTemp, 0,
                    nRemainder);
            String strThreadName = String.format(
                    Constants.STR_STOCK_ARCHIVER_THREAD_NAME,
                    Constants.STR_STOCK_ARCHIVER_THREAD_GROUP_NAME,
                    (nNumberOfStockArchiverThread + 1));
            StockQuoteArchiver objSQA = new StockQuoteArchiver(
                    m_objTGSQAManager, strThreadName, strStockCodesTemp);
            objSQA.setPriority(MAX_PRIORITY);
            objSQA.start();
        }
    }

    public void run() {
        try {
            // System.out.println("StockQuoteArchiver Id from the object reference 'this' is: "
            // + this.getId());
            DataSource objDataSource = DataSource.createObject();
            for (int nIdx = 0; nIdx < m_strStockCodes.length; nIdx++) {
                objDataSource.archiveStockQuote(m_strStockCodes[nIdx]);
                StringBuffer strBuf = new StringBuffer(this.getName());
                strBuf.append(" thread archived stock quote for ").append(
                        m_strStockCodes[nIdx]);
                System.out.println(strBuf.toString());
            }
        } catch (Exception ieExcep) {
            ieExcep.printStackTrace();
        }
    }
}
