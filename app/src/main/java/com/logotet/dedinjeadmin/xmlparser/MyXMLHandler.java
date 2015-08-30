package com.logotet.dedinjeadmin.xmlparser;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.CharArrayWriter;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by boban on 8/28/15.
 */
public class MyXMLHandler extends DefaultHandler {
    protected int pcData = 0;
    protected CharArrayWriter contents = new CharArrayWriter();
    protected InputStream inputStream;
    protected boolean isOk;

    public MyXMLHandler(InputStream inputStream) {
        this.inputStream = inputStream;
        isOk = false;
    }

    public final boolean isOk() {
        return isOk;
    }

    /**
     * odradjuje sve, tj. cita ulazni file i parsira ga
     */
    public void doEntireJob() throws MalformedURLException {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating(false);
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xr = saxParser.getXMLReader();
            xr.setContentHandler(this);

            // Parse the file...
            xr.parse(new InputSource(inputStream));
        } catch (MalformedURLException urle) {
            throw new MalformedURLException();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
