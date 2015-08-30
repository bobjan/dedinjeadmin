package com.logotet.dedinjeadmin.xmlparser;


import com.logotet.dedinjeadmin.model.Servertime;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira servertime.xml
 */
public class ServertimeXMLHandler extends MyXMLHandler {


    final int SERVERTIME = 10;

    public ServertimeXMLHandler(InputStream inputStream) {
        super(inputStream);
    }


    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("servertime")) {
            isOk = true;
            pcData = SERVERTIME;
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("servertime")) {
            pcData = SERVERTIME;
        }
    }

    /**
     * Za sve PCDATA iz XML fajla
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);//ne znam cemu sluzi ali neka ostane
        String tekst = new String(ch, start, length);
        if (pcData == SERVERTIME)
            Servertime.TIME = tekst.trim();
    }


}
