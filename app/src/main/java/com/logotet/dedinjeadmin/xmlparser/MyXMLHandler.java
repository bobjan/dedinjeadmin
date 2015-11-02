package com.logotet.dedinjeadmin.xmlparser;


import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by boban on 8/28/15.
 */
public class MyXMLHandler extends DefaultHandler {
    private static final String TAG = "MyXMLHandler";
    protected int pcData = 0;
    protected CharArrayWriter contents = new CharArrayWriter();
    protected InputStream inputStream;
    protected boolean isOk;

    protected StringBuffer textBuffer;

    public MyXMLHandler(InputStream inputStream) {
        this.inputStream = inputStream;
        isOk = false;
    }

    public boolean isOk() {
        return isOk;
    }

    /**
     * odradjuje sve, tj. cita ulazni file i parsira ga
     */
    public void doEntireJob() throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        spf.setValidating(false);
        SAXParser saxParser = spf.newSAXParser();
        XMLReader xr = saxParser.getXMLReader();
        xr.setContentHandler(this);
        // Parse the file...
        xr.parse(new InputSource(inputStream));
        isOk = true; // no exception thrown up to this point!
    }

}
