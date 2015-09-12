package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.BazaOsoba;
import com.logotet.dedinjeadmin.model.Igrac;
import com.logotet.dedinjeadmin.model.Osoba;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira stadion.xml
 */
public class RukovodstvoXMLHandler extends MyXMLHandler {
    BazaOsoba bazaOsoba;
    Osoba currentOsoba;
    final private int OSOBA = 123;

    public RukovodstvoXMLHandler(InputStream inputStream) {
        super(inputStream);
    }


    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("rukovodstvo")) {
            bazaOsoba = BazaOsoba.getInstance();
            isOk = true;
        }
        if (rawName.equals("osoba")) {
            pcData = OSOBA;
            try {
                int id = Integer.parseInt(attr.getValue("oid").trim());
                currentOsoba = new Osoba(id, attr.getValue("naziv"), attr.getValue("funkcija"),
                         attr.getValue("img"));
                bazaOsoba.add(currentOsoba);
            } catch (NumberFormatException nfe) {
            }
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("rukovodstvo")) {
            pcData = 0;
            bazaOsoba.setLoaded(true);
        }
        if (rawName.equals("osoba")) {
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
