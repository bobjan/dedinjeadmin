package com.logotet.dedinjeadmin.xmlparser;

import android.util.Log;

import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.Utakmica;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by boban on 8/29/15.
 */
public class RequestPreparator {
    private static final String TAG = "StartMatchActivity";



    public static final int SERVERTIME = 0;

    public static final int GETPOZICIJA = 1;
    public static final int GETSTADION = 2;
    public static final int GETEKIPA = 3;
    public static final int GETLIGA = 4;
    public static final int GETTABELA = 5;
    public static final int GETFIXTURES = 6;


    public static final int STARTMATCH = 7;
    public static final int MAKESASTAV = 8;
    public static final int MAKEEVENT = 9;
    public static final int DELETEEVENT = 10;
    public static final int DELETEALL = 11;
    public static final int ALLEVENTS = 12;
    public static final int GETLIVEMATCH = 13;

    private static final String[] request = {"servertime.php", "pozicija.xml", "stadion.xml", "ekipa.xml", "liga.xml", "tabela.xml", "fixtures.xml",
            "startmatch.php", "makesastav.php", "makeevent.php", "deleteevent.php", "deleteall.php", "allevents.php", "livematch.xml"};

    public static String getRequest(int what) {
        switch (what) {
            case SERVERTIME:
            case GETPOZICIJA:
            case GETSTADION:
            case GETEKIPA:
            case GETLIGA:
            case GETTABELA:
            case GETFIXTURES:
            case DELETEALL:
            case ALLEVENTS:
            case GETLIVEMATCH:
                return request[what];

            case STARTMATCH:
                return request[what] + startMatch();
            case MAKESASTAV:
                return request[what] + getProtokol();

            case MAKEEVENT:
                return request[what] + getEvent();
            case DELETEEVENT:
                break;
        }
        return null;
    }


    private static String getProtokol() {
        StringBuffer sb = new StringBuffer("?sastav=");
        sb.append(BazaIgraca.getInstance().getProtokol());
        return sb.toString();
    }

    private static String startMatch() {
        Utakmica utakmica = Utakmica.getInstance();
        StringBuffer sb = new StringBuffer("?datum=");
        sb.append(utakmica.getDatum().toString());
        sb.append("&vreme=");
        sb.append(utakmica.getPlaniranoVremePocetka().toString());

        sb.append("&stadion=");
        sb.append(utakmica.getStadionId());

        sb.append("&protivnik=");
        sb.append(utakmica.getProtivnikId());

        sb.append("&domacin=");
        sb.append(utakmica.isUserTeamDomacin() ? "1" : "0");
        Log.w(TAG,sb.toString());
        return sb.toString();

    }


    private static String getEvent() {
        int rnd = (int) (Math.random() * 6);
        StringBuffer sb = new StringBuffer("/makeevent.php?");
        switch (rnd){
            case 0:
                sb.append("eventid=0&minut=5");
                break;
            case 1:
                sb.append("eventid=1&player=5");
                break;
            case 2:
                sb.append("eventid=2&playerin=5&playerout=6");
                break;
            case 3:
                sb.append("eventid=3");
                break;
            case 4:
                try {
                    sb.append("eventid=4&komentar=");
                    sb.append(URLEncoder.encode("ovo je neki komentar bla bla","UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            default:
                sb.append("eventid=5&minut=90");
                break;
        }
    return sb.toString();

    }


}
