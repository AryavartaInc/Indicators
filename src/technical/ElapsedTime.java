package com.fin.technical;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */

public class ElapsedTime {

    private long m_lStartTimeInMillis = 0;

    private long m_lEndTimeInMillis = 0;

    // the no-arg constructor
    private ElapsedTime() {

    }

    private void resetTimer() {
        m_lStartTimeInMillis = m_lEndTimeInMillis = 0;
    }

    /**
     * SingletonHolder is loaded on the first execution of
     * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
     * not before.
     */
    private static class SingletonHolder {
        // Later, when I implement Factory pattern, below code needs to be
        // re-written.
        public static final ElapsedTime m_objElapsedTime = new ElapsedTime();
    }

    public static ElapsedTime getInstance() {
        return SingletonHolder.m_objElapsedTime;
    }

    public void BeginStopWatch() {
        m_lStartTimeInMillis = System.currentTimeMillis();
    }

    public String checkStopWatch(String strInfo1, String strInfo2) {
        m_lEndTimeInMillis = System.currentTimeMillis();
        long lElapsedTime = m_lEndTimeInMillis - m_lStartTimeInMillis;
        // long lElapsedTimeInSec = lElapsedTime / 1000;
        String strCRLF = System.getProperty("line.separator");
        String strLogMessage = String.format(
                Constants.STR_PERFORMANCE_MEASUREMENT, strCRLF, strInfo1,
                strInfo2, lElapsedTime);
        resetTimer();
        return strLogMessage;
    }

    public void logStopWatch(String strInfo1, String strInfo2) {
        System.out.println(checkStopWatch(strInfo1, strInfo2));
    }

}
