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

        if (rawName.equals("match")) {
            pcData = MATCH;
            utakmica = Utakmica.getInstance();
            utakmica.setFromHttpServer(true);

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

            pcData = MATCH;
        }
        if (rawName.equals("datum")) {
            pcData = DATUM + 100;
        }

        if (rawName.equals("vreme")) {
            pcData = VREME + 100;
        }
        if (rawName.equals("domacin")) {
            pcData = DOMACIN + 100;
        }
        if (rawName.equals("protivnik")) {
            pcData = PROTIVNIK + 100;
        }
        if (rawName.equals("stadion")) {
            pcData = STADION + 100;
        }
    }

    /**
     * Za sve PCDATA iz XML fajla
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);//ne znam cemu sluzi ali neka ostane
        String tekst = new String(ch, start, length);
        switch (pcData) {
            case DATUM:
                utakmica.setDatum(tekst);
                break;
            case VREME:
                utakmica.setPlaniranoVremePocetka(tekst);
                break;
            case PROTIVNIK:
                utakmica.setProtivnikId(tekst);
                break;
            case DOMACIN:
                utakmica.setUserTeamDomacin(tekst.trim().equals("1"));
                break;
            case STADION:
                utakmica.setStadionId(tekst);
                break;
            default:
                break;
        }
    }
}
