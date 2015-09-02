package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.model.AppHeaderData;
import com.logotet.dedinjeadmin.model.BazaTimova;
import com.logotet.dedinjeadmin.model.Tim;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira config.xml
 */
public class LigaXMLHandler extends MyXMLHandler {

    AppHeaderData headerData;
    BazaTimova bazaTimova;

    final int LIGA = 101;
    final int USER = 111;
    final int PASSWORD = 121;
    final int PROTIVNIK = 131;

    public LigaXMLHandler(InputStream inputStream) {
        super(inputStream);
    }


    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("liga")) {
            pcData = 0;
            headerData = AppHeaderData.getInstance();
            bazaTimova = BazaTimova.getInstance();
        }
        if (rawName.equals("naziv")) {
            pcData = LIGA;
        }

        if (rawName.equals("user")) {
            pcData = USER;
        }
        if (rawName.equals("password")) {
            pcData = PASSWORD;
        }
        if (rawName.equals("protivnik")) {
            pcData = PROTIVNIK;
        }
        if (rawName.equals("tim")) {
            try {
                int id = Integer.parseInt(attr.getValue("tid").trim());
                bazaTimova.add(new Tim(id, attr.getValue("naziv")));
            } catch (NumberFormatException nfe) {
            }
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("liga")) {

            pcData = 1;
        }
        if (rawName.equals("naziv")) {
            pcData = LIGA + 555;
        }
        if (rawName.equals("user")) {
            pcData = USER + 555;
        }
        if (rawName.equals("password")) {
            pcData = PASSWORD + 555;
        }

    }

    /**
     * Za sve PCDATA iz XML fajla
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);//ne znam cemu sluzi ali neka ostane
        String tekst = new String(ch, start, length);
        switch (pcData) {
            case LIGA:
                headerData.setNazivLige(tekst);
                break;
            case USER:
                headerData.setUserTeamName(tekst);
                break;
            case PASSWORD:
                headerData.setPassword(tekst);
                break;
            default:
                break;
        }
    }

}
