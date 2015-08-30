package com.logotet.dedinjeadmin.xmlparser;

import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * Klasa koja parsira stadion.xml
 */
public class NoResponseHandler extends MyXMLHandler {

    public NoResponseHandler(InputStream inputStream) {
        super(inputStream);
    }


    @Override
    public void doEntireJob() throws MalformedURLException {

    }

}
