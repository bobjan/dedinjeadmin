package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.Igrac;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira stadion.xml
 */
public class EkipaXMLHandler extends MyXMLHandler {
    BazaIgraca bazaIgraca;
    Igrac currentIgrac;
    final private int PLAYER = 123;

    public EkipaXMLHandler(InputStream inputStream) {
        super(inputStream);
    }


    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("ekipa")) {
            bazaIgraca = BazaIgraca.getInstance();
            isOk = true;
        }
        if (rawName.equals("player")) {
            pcData = PLAYER;
            try {
                int id = Integer.parseInt(attr.getValue("pid").trim());
                currentIgrac = new Igrac(id, attr.getValue("naziv"), attr.getValue("godina"), attr.getValue("mestoutimu"), attr.getValue("img"));
                bazaIgraca.add(currentIgrac);
            } catch (NumberFormatException nfe) {
            }
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("ekipa")) {
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
