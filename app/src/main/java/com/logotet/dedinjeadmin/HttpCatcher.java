package com.logotet.dedinjeadmin;

import com.logotet.dedinjeadmin.xmlparser.AlleventsXMLHandler;
import com.logotet.dedinjeadmin.xmlparser.EkipaXMLHandler;
import com.logotet.dedinjeadmin.xmlparser.FixturesXMLHandler;
import com.logotet.dedinjeadmin.xmlparser.LigaXMLHandler;
import com.logotet.dedinjeadmin.xmlparser.LivematchXMLHandler;
import com.logotet.dedinjeadmin.xmlparser.MyXMLHandler;
import com.logotet.dedinjeadmin.xmlparser.NoResponseHandler;
import com.logotet.dedinjeadmin.xmlparser.PozicijaXMLHandler;
import com.logotet.dedinjeadmin.xmlparser.RequestPreparator;
import com.logotet.dedinjeadmin.xmlparser.RukovodstvoXMLHandler;
import com.logotet.dedinjeadmin.xmlparser.SastavXMLHandler;
import com.logotet.dedinjeadmin.xmlparser.ServertimeXMLHandler;
import com.logotet.dedinjeadmin.xmlparser.StadionXMLHandler;
import com.logotet.dedinjeadmin.xmlparser.TabelaXMLHandler;

import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by boban on 9/11/15.
 */
public class HttpCatcher {
    private String urlAdresa;
    private String requestParams;

    private MyXMLHandler myXMLHandler;
    BufferedReader inFile;
    InputStream instream;
    HttpURLConnection urlConnection;
    int what;

    public HttpCatcher(int what, String host, Object object) throws IOException {
        this.urlAdresa = host;
        this.what = what;
        requestParams = RequestPreparator.getRequest(what, object);
        URL url = new URL(urlAdresa + requestParams);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.addRequestProperty("Cache-Control", "no-cache");
    }


    public HttpCatcher(String host) throws IOException {
        this.urlAdresa = host;
        URL url = new URL(urlAdresa);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.addRequestProperty("Cache-Control", "no-cache");
    }

    public InputStream getInputStream() {
        try {
            instream = new BufferedInputStream(urlConnection.getInputStream());
        } catch (IOException e) {
            return null;
        }
        return instream;
    }

    public void disconnect() {
        urlConnection.disconnect();
    }

    public StringBuffer getResponsePage() throws IOException {
        getInputStream();
        inFile = new BufferedReader(new InputStreamReader(instream));
        StringBuffer sb = readAndParse();
        return sb;
    }

    private StringBuffer readAndParse() throws IOException {
        StringBuffer sb = new StringBuffer();
        boolean petlja = true;
        while (petlja) {
            String linija = null;
            try {
                linija = inFile.readLine();
                if (linija != null) {
                    sb.append("\n" + linija);
                } else
                    petlja = false;
            } catch (NullPointerException e) {
                petlja = false;
            } catch (IOException e) {
                petlja = false;
                throw new IOException(e);
            }
        }
        try {
            inFile.close();
        } catch (IOException e) {
            throw new IOException(e);
        }
        return sb;
    }

    public void catchData() throws IOException {
        try {
            instream = new BufferedInputStream(urlConnection.getInputStream());
            myXMLHandler = null;
            switch (what) {
                case RequestPreparator.SERVERTIME:
                    myXMLHandler = new ServertimeXMLHandler(instream);
                    break;
                case RequestPreparator.GETPOZICIJA:
                    myXMLHandler = new PozicijaXMLHandler(instream);
                    break;
                case RequestPreparator.GETSTADION:
                    myXMLHandler = new StadionXMLHandler(instream);
                    break;
                case RequestPreparator.GETEKIPA:
                    myXMLHandler = new EkipaXMLHandler(instream);
                    break;
                case RequestPreparator.GETLIGA:
                    myXMLHandler = new LigaXMLHandler(instream);
                    break;
                case RequestPreparator.GETTABELA:
                    myXMLHandler = new TabelaXMLHandler(instream);
                    break;
                case RequestPreparator.GETFIXTURES:
                    myXMLHandler = new FixturesXMLHandler(instream);
                    break;
                case RequestPreparator.DELETEALL:
                    myXMLHandler = new NoResponseHandler(instream);
                    break;
                case RequestPreparator.ALLEVENTS:
                    myXMLHandler = new AlleventsXMLHandler(instream);
                    break;
                case RequestPreparator.GETLIVEMATCH:
                    myXMLHandler = new LivematchXMLHandler(instream);
                    break;
                case RequestPreparator.STARTMATCH:
                    myXMLHandler = new NoResponseHandler(instream);
                    break;
                case RequestPreparator.MAKESASTAV:
                    myXMLHandler = new NoResponseHandler(instream);
                    break;
                case RequestPreparator.MAKEEVENT:
                    myXMLHandler = new NoResponseHandler(instream);
                    break;
                case RequestPreparator.DELETEEVENT:
                    myXMLHandler = new NoResponseHandler(instream);
                    break;
                case RequestPreparator.GETRUKOVODSTVO:
                    myXMLHandler = new RukovodstvoXMLHandler(instream);
                    break;
                case RequestPreparator.GETSASTAV:
                    myXMLHandler = new SastavXMLHandler(instream);
                    break;
            }
            if (myXMLHandler != null) {
                myXMLHandler.doEntireJob();
                if(!myXMLHandler.isOk())
                    throw new IOException("myXMLHandler error");
            }
        } catch (ParserConfigurationException pce) {
            throw new IOException("XMLParsing error " + pce.getMessage());
        } catch (SAXException saxe) {
            throw new IOException("SAX error " + saxe.getMessage());
        } finally {
            urlConnection.disconnect();
        }
    }

    public byte[] catchByteArray() throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        try {
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                int bytesRead = 0;
                byte[] buffer = new byte[1024];
                while ((bytesRead = urlConnection.getInputStream().read(buffer)) > 0) {
                    outstream.write(buffer, 0, bytesRead);
                }
                outstream.flush();
                outstream.close();
            }
        } finally {
            urlConnection.disconnect();
        }
        return outstream.toByteArray();
    }
}

