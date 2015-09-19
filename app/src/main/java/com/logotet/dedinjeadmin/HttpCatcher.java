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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by boban on 9/11/15.
 */
public class HttpCatcher {
    private static int httpCounter = 0;
    private String urlAdresa;
    private String requestParams;

    private MyXMLHandler myXMLHandler;
    BufferedReader inFile;
    InputStream instream;
    HttpURLConnection urlConnection;
    int what;

    public HttpCatcher(int what, String host, Object object) throws IOException {
        httpCounter++;
        this.urlAdresa = host;
        this.what = what;
        requestParams = RequestPreparator.getRequest(what, object);
        URL url = new URL(urlAdresa + requestParams);
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

    public StringBuffer getResponsePage() {
        getInputStream();
        inFile = new BufferedReader(new InputStreamReader(instream));
        StringBuffer sb = readAndParse();
        return sb;
    }

    private StringBuffer readAndParse() {
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
            }
        }
        try {
            inFile.close();
        } catch (IOException e) {
        }
        return sb;
    }

    public static int getHttpCounter() {
        return httpCounter;
    }

    public void catchData() {
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
                httpCounter--;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
    }

}
