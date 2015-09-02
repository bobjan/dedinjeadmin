package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.model.Fixtures;
import com.logotet.dedinjeadmin.model.FixturesRow;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira config.xml
 */
public class FixturesXMLHandler extends MyXMLHandler {


    private Fixtures fixtures;
    final static int KOLO = 123;

    public FixturesXMLHandler(InputStream inputStream) {
        super(inputStream);
    }


    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("fixtures")) {
            isOk = true;
            fixtures = Fixtures.getInstance();
        }
        if (rawName.equals("kolo")) {
            pcData = KOLO;
            fixtures.add(new FixturesRow(attr.getValue("broj"),
                    attr.getValue("datum"), attr.getValue("protivnik"),
                    attr.getValue("domacin"), attr.getValue("played"),
                    attr.getValue("wescored"), attr.getValue("theyscored")));

        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("fixtures")) {
            pcData = 0;
        }
        if (rawName.equals("kolo")) {
            pcData = KOLO;
        }
    }

    /**
     * Za sve PCDATA iz XML fajla
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);//ne znam cemu sluzi ali neka ostane
        String tekst = new String(ch, start, length);
    }



}

