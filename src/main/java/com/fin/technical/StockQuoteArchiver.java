package com.fin.technical;

/**
 * @author Manoj Tiwari
 * @version 1.0
 * @email manoj dot k dot tiwari at live dot com
 */

public class StockQuoteArchiver extends Thread {
    private String[] m_straStockCodes = null;

    protected static String m_strStockQuotePath = null;

    protected static ThreadGroup m_objTGSQAManager = null;

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

    public StockQuoteArchiver(String[] straStockCodes) {
        m_straStockCodes = straStockCodes;
    }

    public StockQuoteArchiver(String strThreadName, String[] straStockCodes) {
        super(strThreadName);
        m_straStockCodes = straStockCodes;
    }

    public StockQuoteArchiver(ThreadGroup objTG, String strThreadName,
            String[] straStockCodes) {

        super(objTG, strThreadName);
        m_straStockCodes = straStockCodes;
    }

    public String[] getStockCodes() {
    	return m_straStockCodes;
    }
    
    public void setStockCodes(String[] straStockCodes) {
    	m_straStockCodes = straStockCodes;
    }
    public String getStockQuotePath() {
    	return m_strStockQuotePath;
    }
    
    public void setStockQuotePath(String strStockQuotePath) {
    	m_strStockQuotePath = strStockQuotePath;
    }

    public ThreadGroup getTGSQAManager() {
    	return m_objTGSQAManager;
    }
    
    public void setTGSQAManager(ThreadGroup objTGSQAManager) {
    	m_objTGSQAManager = objTGSQAManager;
    }
    
    public void archiveStockQuote() {

        String[] straStockCodes = m_objUtil.getStockCodeValue();
        int nStockCount = straStockCodes.length;
        int nNumberOfStockArchiverThread = m_objUtil
                .getNumberOfStockArchiverThread();
        int nQuotient = nStockCount / nNumberOfStockArchiverThread;
        int nRemainder = nStockCount % nNumberOfStockArchiverThread;
        int nFromIndex = 0;

        if(nQuotient > 0) {
            nNumberOfStockArchiverThread = m_objUtil
                    .getNumberOfStockArchiverThread();
            for (int nIdx = 1; nIdx <= nNumberOfStockArchiverThread; nIdx++) {
                String[] straStockCodesTemp = new String[nQuotient];
                System.arraycopy(straStockCodes, nFromIndex, straStockCodesTemp, 0,
                        nQuotient);
                nFromIndex = nFromIndex + nQuotient;
                String strThreadName = String.format(
                        Constants.STR_STOCK_ARCHIVER_THREAD_NAME,
                        Constants.STR_STOCK_ARCHIVER_THREAD_GROUP_NAME, nIdx);
                StockQuoteArchiver objSQA = 
                		(StockQuoteArchiver)m_objUtil.getSpringFXApplicationContext().getBean("stockQuoteArchiver");
                objSQA.setTGSQAManager(m_objTGSQAManager);
                objSQA.setName(strThreadName);
                objSQA.setStockCodes(straStockCodesTemp);
                //StockQuoteArchiver objSQA = new StockQuoteArchiver(m_objTGSQAManager, strThreadName, straStockCodesTemp);
                objSQA.start();
            }
        } else {
            nNumberOfStockArchiverThread = 0;
        }
        if (nRemainder > 0) {
            String[] straStockCodesTemp = new String[nRemainder];
            System.arraycopy(straStockCodes, nFromIndex, straStockCodesTemp, 0,
                    nRemainder);
            String strThreadName = String.format(
                    Constants.STR_STOCK_ARCHIVER_THREAD_NAME,
                    Constants.STR_STOCK_ARCHIVER_THREAD_GROUP_NAME,
                    (nNumberOfStockArchiverThread + 1));
            StockQuoteArchiver objSQA = new StockQuoteArchiver(
                    m_objTGSQAManager, strThreadName, straStockCodesTemp);
            objSQA.setPriority(MAX_PRIORITY);
            objSQA.start();
        }
    }

    public void run() {
    	DataSource objDataSource = null;
        try {
        	if (Thread.interrupted()) {
        		throw new InterruptedException();
        	}
            // System.out.println("StockQuoteArchiver Id from the object reference 'this' is: "
            // + this.getId());
            objDataSource = DataSource.createObject();
            for (int nIdx = 0; nIdx < m_straStockCodes.length; nIdx++) {
                objDataSource.archiveStockQuote(m_straStockCodes[nIdx]);
                StringBuffer strBuf = new StringBuffer(this.getName());
                strBuf.append(" thread archived stock quote for ").append(
                        m_straStockCodes[nIdx]);
                // System.out.println(strBuf.toString());
            }
        } catch(InterruptedException objIEExcep) {
        	return; // exit from the run method
        } catch (Exception ieExcep) {
            ieExcep.printStackTrace();
        } finally {
        	objDataSource = null;
        }       
    }
}
