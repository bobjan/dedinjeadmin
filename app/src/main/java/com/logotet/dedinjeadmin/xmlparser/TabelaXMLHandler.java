package com.logotet.dedinjeadmin.xmlparser;

import com.logotet.dedinjeadmin.model.Tabela;
import com.logotet.dedinjeadmin.model.TabelaRow;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.InputStream;

/**
 * Klasa koja parsira tabela.xml
 */
public class TabelaXMLHandler extends MyXMLHandler {


    Tabela tabela;

    public TabelaXMLHandler(InputStream inputStream) {
        super(inputStream);
    }

    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("tabela")) {
            isOk = true;
            tabela = Tabela.getInstance();
            pcData = 0;
        }

        if (rawName.equals("mesto")) {
            pcData = 2;
            tabela.add(new TabelaRow(attr.getValue("broj"), attr.getValue("naziv"),
                    attr.getValue("pwdl"), attr.getValue("goaldif"), attr.getValue("points")));
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("mesto")) {
            pcData = 0;
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
