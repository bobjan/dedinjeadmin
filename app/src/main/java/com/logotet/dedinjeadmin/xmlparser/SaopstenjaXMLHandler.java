package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.HttpCatcher;
import com.logotet.dedinjeadmin.model.BazaSaopstenja;
import com.logotet.dedinjeadmin.model.Saopstenje;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Klasa koja parsira saopstenja.xml
 */
public class SaopstenjaXMLHandler extends MyXMLHandler {

    BazaSaopstenja bazaSaopstenja;
    Saopstenje currentSaopstenje;

    final int VEST = 1;
    final int NASLOV = 10;
    final int TEXT = 11;
    final int SLIKA = 12;

    public SaopstenjaXMLHandler(InputStream inputStream) {
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
        if (rawName.equals("saopstenje")) {
            bazaSaopstenja = BazaSaopstenja.getInstance();
            pcData = 0;
        }
        if (rawName.equals("vest")) {
            pcData = VEST;
            currentSaopstenje = new Saopstenje(attr.getValue("datum"));
            bazaSaopstenja.add(currentSaopstenje);
        }
        if (rawName.equals("naslov")) {
            pcData = NASLOV;
        }
        if (rawName.equals("tekst")) {
            pcData = TEXT;
        }
        if (rawName.equals("slika")) {
            pcData = SLIKA;
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("saopstenje")) {
            pcData = 0;
            bazaSaopstenja.setLoaded(true);
        }
        if (rawName.equals("vest")) {
            pcData = 1;
        }

        if (rawName.equals("naslov")) {
            currentSaopstenje.setNaslov(textBuffer.toString());
        }
        if (rawName.equals("tekst")) {
            currentSaopstenje.setFullText(textBuffer.toString());
        }
        if (rawName.equals("slika")) {
            currentSaopstenje.setImageFileName(textBuffer.toString());
        }
    }

    /**
     * Za sve PCDATA iz XML fajla
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);//ne znam cemu sluzi ali neka ostane
        textBuffer.append(new String(ch, start, length));
    }


/*
    public static void main(String[] args) {
        HttpCatcher http = null;
        try {
            http = new HttpCatcher(RequestPreparator.GETSAOPSTENJA,"http://www.logotet.com/fkdedinje/",null);
            SaopstenjaXMLHandler hip = new SaopstenjaXMLHandler(http.getInputStream());
            hip.doEntireJob();
            http.disconnect();
            if(hip.isOk()){
                BazaSaopstenja.getInstance().sortiraj();
                System.out.println(BazaSaopstenja.getInstance().toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
