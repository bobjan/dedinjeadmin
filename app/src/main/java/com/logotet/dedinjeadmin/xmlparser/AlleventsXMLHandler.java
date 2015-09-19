package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.model.Dogadjaj;
import com.logotet.dedinjeadmin.model.Utakmica;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira config.xml
 */
public class AlleventsXMLHandler extends MyXMLHandler {
    final int ALLEVENTS = 0;
    final int EVENTTAG = 1;

    final int MINUT = 102;
    final int PLAYER = 103;
    final int PLAYERIN = 104;
    final int PLAYEROUT = 105;
    final int KOMENTAR = 106;

    Utakmica utakmica;

    Dogadjaj currentDogadjaj;


    public AlleventsXMLHandler(InputStream inputStream) {
        super(inputStream);
    }

    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("allevents")) {
            utakmica = Utakmica.getInstance();
            utakmica.clear();
            pcData = ALLEVENTS;
        }

        if (rawName.equals("event")) {
            try {
                int id = Integer.parseInt(attr.getValue("eventid").trim());
                currentDogadjaj = new Dogadjaj(attr.getValue("filename"),
                        attr.getValue("systime"), id);
                utakmica.add(currentDogadjaj);
            } catch (NumberFormatException nfe) {
            }
            pcData = EVENTTAG;
        }

        if (rawName.equals("minut")) {
            pcData = MINUT;
        }
        if (rawName.equals("player")) {
            pcData = PLAYER;
        }
        if (rawName.equals("playerin")) {
            pcData = PLAYERIN;
        }
        if (rawName.equals("playerout")) {
            pcData = PLAYEROUT;
        }
        if (rawName.equals("komentar")) {
            pcData = KOMENTAR;
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("event")) {
            pcData = 0;
        }

        if (rawName.equals("minut")) {
            pcData = MINUT + 100;
        }
        if (rawName.equals("player")) {
            pcData = PLAYER + 100;
        }
        if (rawName.equals("playerin")) {
            pcData = PLAYERIN + 100;
        }
        if (rawName.equals("playerout")) {
            pcData = PLAYEROUT + 100;
        }
        if (rawName.equals("komentar")) {
            pcData = KOMENTAR + 100;
        }
    }

    /**
     * Za sve PCDATA iz XML fajla
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);//ne znam cemu sluzi ali neka ostane
        String tekst = new String(ch, start, length);
        switch (pcData) {

            case PLAYER:
                currentDogadjaj.setPlayerId(tekst);
                break;
            case PLAYERIN:
                currentDogadjaj.setPlayerInId(tekst);
                break;
            case PLAYEROUT:
                currentDogadjaj.setPlayerOutId(tekst);
                break;
            case MINUT:
                currentDogadjaj.setMinut(tekst);
                break;
            case KOMENTAR:
                currentDogadjaj.setKomentar(tekst);
                break;
            default:
                break;
        }
    }


}
