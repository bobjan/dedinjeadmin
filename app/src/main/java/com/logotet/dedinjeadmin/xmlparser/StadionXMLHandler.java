package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.model.BazaStadiona;
import com.logotet.dedinjeadmin.model.Stadion;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira stadion.xml
 */
public class StadionXMLHandler extends MyXMLHandler {

    BazaStadiona bazaStadiona;
    Stadion currentStadion;
    final private int TEREN = 123;

    public StadionXMLHandler(InputStream inputStream) {
        super(inputStream);
    }

    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("stadion")) {
            bazaStadiona = BazaStadiona.getInstance();
            isOk = true;
        }
        if (rawName.equals("teren")) {
            pcData = TEREN;
            try {
                int id = Integer.parseInt(attr.getValue("sid").trim());
                currentStadion = new Stadion(id, attr.getValue("naziv"), attr.getValue("lat"), attr.getValue("long"));
                bazaStadiona.add(currentStadion);
            } catch (NumberFormatException nfe) {
            }
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("stadion")) {
            pcData = 0;
            bazaStadiona.setLoaded(true);
        }
        if (rawName.equals("teren")) {
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
