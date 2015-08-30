package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.model.Sastav;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira config.xml
 */
public class SastavXMLHandler extends MyXMLHandler {

    public SastavXMLHandler(InputStream inputStream) {
        super(inputStream);
    }

    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("sastav")) {
            isOk = true;
        }
        if (rawName.equals("player")) {
            try {
                int pid = Integer.parseInt(attr.getValue("pid").trim());
                int dres = Integer.parseInt(attr.getValue("dres").trim());
                Sastav.add(pid, dres);
            } catch (NumberFormatException nfe) {
            }
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("sastav")) {
            pcData = 0;
        }
        if (rawName.equals("player")) {
            pcData = 1;
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
