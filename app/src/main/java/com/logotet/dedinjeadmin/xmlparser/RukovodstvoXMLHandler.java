package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.model.Klub;
import com.logotet.dedinjeadmin.model.Osoba;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira stadion.xml
 */
public class RukovodstvoXMLHandler extends MyXMLHandler {
    Klub klub;
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
        textBuffer = new StringBuffer("");
        if (rawName.equals("rukovodstvo")) {
            klub = Klub.getInstance();
            isOk = true;
        }
        if (rawName.equals("klub")) {
            klub = Klub.getInstance();
            isOk = true;
            klub.setAdresa(attr.getValue("adresa"));
            klub.setMesto(attr.getValue("grad"));
            klub.setEmail(attr.getValue("email"));
            klub.setWeb(attr.getValue("web"));
            klub.setPib(attr.getValue("pib"));
            klub.setMatbroj(attr.getValue("matbroj"));
            klub.setDatumOsnivanja(attr.getValue("datum"));
            klub.setTekrac(attr.getValue("tekrac"));
            klub.setFrontimage(attr.getValue("frontimage"));
        }
        if (rawName.equals("osoba")) {
            pcData = OSOBA;
            try {
                int id = Integer.parseInt(attr.getValue("oid").trim());
                currentOsoba = new Osoba(id, attr.getValue("naziv"), attr.getValue("funkcija"),
                         attr.getValue("img"));
                klub.add(currentOsoba);
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
            klub.setLoaded(true);
        }
        if (rawName.equals("osoba")) {
            pcData = 1;
        }
        if(rawName.equals("napomena"))
            currentOsoba.setNapomena(textBuffer.toString());
    }

    /**
     * Za sve PCDATA iz XML fajla
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);//ne znam cemu sluzi ali neka ostane
        textBuffer.append(new String(ch, start, length));
    }

}
