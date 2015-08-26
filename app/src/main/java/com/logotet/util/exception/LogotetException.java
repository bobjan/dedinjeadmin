package com.logotet.util.exception;

/**
 * kreirano:
 * Date: Mar 25, 2006
 * Time: 11:41:56 AM
 */
public class LogotetException extends Exception {
    public LogotetException() {
    }

    public LogotetException(String message) {
        super(message);
    }

    public LogotetException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogotetException(Throwable cause) {
        super(cause);
    }
}
