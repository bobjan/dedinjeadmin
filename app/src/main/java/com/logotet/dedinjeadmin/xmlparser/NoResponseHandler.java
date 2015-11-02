package com.logotet.dedinjeadmin.xmlparser;

import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Klasa koja nema sta da parsira, a
 */
public class NoResponseHandler extends MyXMLHandler {

    public NoResponseHandler(InputStream inputStream) {
        super(inputStream);
    }


    @Override
    public void doEntireJob() throws MalformedURLException {

    }

}
