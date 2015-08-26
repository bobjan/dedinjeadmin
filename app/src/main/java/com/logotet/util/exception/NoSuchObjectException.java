package com.logotet.util.exception;

public class NoSuchObjectException extends Exception {
    public NoSuchObjectException() {
        super();
    }

    public NoSuchObjectException(String t) {
        super(t);
    }

    public NoSuchObjectException(int t) {
        super("" + t);
    }
}