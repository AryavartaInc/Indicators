package com.fin.technical;

/**
 * @author 
 * @version 1.0.0.0
 * @email 
 */

public class IllegalInputException extends Exception {

    /**
	 *
	 */
    private static final long serialVersionUID = 2705984171132011026L;

    public IllegalInputException() {

    }

    public IllegalInputException(String strReason) {
        super.initCause(new Throwable(strReason));
    }
}
