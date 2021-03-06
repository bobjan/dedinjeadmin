package com.logotet.dedinjeadmin.threads;

import com.logotet.dedinjeadmin.xmlparser.*;

import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by boban on 8/28/15.
 */
public class RequestThread extends Thread {
    private int what;
    private String requestParams;
    private MyXMLHandler myXMLHandler;
    private String serverAddress;

    InputStream instream;
    HttpURLConnection urlConnection;


    public RequestThread(int what, String host, Object object) {
        this.what = what;
        requestParams = RequestPreparator.getRequest(what, object);
//        Log.w("REQUEST OARAMSE = ", requestParams);
        serverAddress = host;
    }


    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(serverAddress + requestParams);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.addRequestProperty("Cache-Control", "no-cache");

            instream = new BufferedInputStream(urlConnection.getInputStream());
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
            myXMLHandler.doEntireJob();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
    }
}
