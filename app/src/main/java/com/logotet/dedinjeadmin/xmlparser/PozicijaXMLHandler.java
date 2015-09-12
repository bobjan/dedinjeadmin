package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.model.BazaPozicija;
import com.logotet.dedinjeadmin.model.Pozicija;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira pozicija.xml
 */
public class PozicijaXMLHandler extends MyXMLHandler {

    BazaPozicija bazaPozicija;
    Pozicija currentPozicija;
    final private int POZICIJA = 123;

    public PozicijaXMLHandler(InputStream inputStream) {
        super(inputStream);
    }

    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("pozicija")) {
            bazaPozicija = BazaPozicija.getInstance();
            isOk = true;
        }
        if (rawName.equals("mestoutimu")) {
            pcData = POZICIJA;
            try {
                int id = Integer.parseInt(attr.getValue("pozid").trim());
                currentPozicija = new Pozicija(id, attr.getValue("naziv"));
                bazaPozicija.add(currentPozicija);
            } catch (NumberFormatException nfe) {
            }
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("pozicija")) {
            pcData = 0;
            bazaPozicija.setLoaded(true);
        }
        if (rawName.equals("mestoutimu")) {
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
