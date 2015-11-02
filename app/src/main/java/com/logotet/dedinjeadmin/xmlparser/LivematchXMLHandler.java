package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.model.Utakmica;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira config.xml
 */
public class LivematchXMLHandler extends MyXMLHandler {

    Utakmica utakmica;

    final int MATCH = 0;
    final int DATUM = 11;
    final int VREME = 12;
    final int PROTIVNIK = 13;
    final int DOMACIN = 14;
    final int STADION = 15;

    public LivematchXMLHandler(InputStream inputStream) {
        super(inputStream);
    }


    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();
        textBuffer = new StringBuffer("");

        if (rawName.equals("match")) {
            pcData = MATCH;
            utakmica = Utakmica.getInstance();
        }
        if (rawName.equals("datum")) {
            pcData = DATUM;
        }

        if (rawName.equals("vreme")) {
            pcData = VREME;
        }
        if (rawName.equals("domacin")) {
            pcData = DOMACIN;
        }
        if (rawName.equals("protivnik")) {
            pcData = PROTIVNIK;
        }
        if (rawName.equals("stadion")) {
            pcData = STADION;
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("match")) {
            utakmica.setFromHttpServer(true);
        }
        if (rawName.equals("datum")) {
            utakmica.setDatum(textBuffer.toString());
        }
        if (rawName.equals("vreme")) {
            utakmica.setPlaniranoVremePocetka(textBuffer.toString());
        }
        if (rawName.equals("domacin")) {
            utakmica.setUserTeamDomacin(textBuffer.toString().trim().equals("1"));
        }
        if (rawName.equals("protivnik")) {
            utakmica.setProtivnikId(textBuffer.toString());
        }
        if (rawName.equals("stadion")) {
            utakmica.setStadionId(textBuffer.toString());
        }
    }

    /**
     * Za sve PCDATA iz XML fajla
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);//ne znam cemu sluzi ali neka ostane
        textBuffer.append(new String(ch, start, length));
    }
}
