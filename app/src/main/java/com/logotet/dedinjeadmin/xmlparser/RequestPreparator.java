package com.logotet.dedinjeadmin.xmlparser;


import com.logotet.dedinjeadmin.model.BazaIgraca;
import com.logotet.dedinjeadmin.model.Dogadjaj;
import com.logotet.dedinjeadmin.model.Utakmica;


/**
 * Created by boban on 8/29/15.
 */
public class RequestPreparator {
    private static final String TAG = "RequestPreparator";


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
    public static final int GETRUKOVODSTVO = 14;
    public static final int GETSASTAV = 15;

    private static final String[] request = {"servertime.php", "pozicija.xml", "stadion.xml", "ekipa.xml", "liga.xml", "tabela.xml", "fixtures.xml",
            "startmatch.php", "makesastav.php", "makeevent.php", "deleteevent.php", "deleteall.php", "allevents.php", "livematch.xml", "rukovodstvo.xml", "sastav.xml"};

    public static String getRequest(int what, Object object) {
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
            case GETRUKOVODSTVO:
            case GETSASTAV:
                return request[what];
            case STARTMATCH:
                return request[what] + startMatch();
            case MAKESASTAV:
                return request[what] + getProtokol();
            case MAKEEVENT:
                return request[what] + getEvent(object);
            case DELETEEVENT:
                return request[what] + getEventForDeletion(object);
        }
        return null;
    }

    private static String getEventForDeletion(Object object) {
        try {
            Dogadjaj dogadjaj = (Dogadjaj) object;
            StringBuffer sb = new StringBuffer("?eventfile=");
            if (dogadjaj != null)
                sb.append(dogadjaj.getFileName());
            return sb.toString();
        } catch (ClassCastException cce) {
            return "";
        }
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
//        Log.w(TAG,sb.toString());
        return sb.toString();
    }


    private static String getEvent(Object object) {
        Dogadjaj dogadjaj;
        try {
            dogadjaj = (Dogadjaj) object;
        } catch (ClassCastException cce) {
            dogadjaj = new Dogadjaj();
            dogadjaj.setTipDogadjaja(Dogadjaj.KOMENTAR);
            dogadjaj.setKomentar("Class cast exception ");
        }

        return dogadjaj.getCreationHttpParams();
    }
}
