package com.logotet.dedinjeadmin.model;

import android.util.Log;

import com.logotet.util.BJDatum;
import com.logotet.util.BJTime;
import com.logotet.util.NumericStringComparator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by logotet on 8/26/15.
 */
public class Utakmica {
    private static final String TAG = "Utakmica";
    private ArrayList<Dogadjaj> svaDogadjanja;

    private static Utakmica utakmica = null;

    private BJDatum datum;
    private BJTime planiranoVremePocetka; // livematch.xml time


    private int protivnikId;
    private int stadionId;

    private boolean userTeamDomacin;

    private boolean fromHttpServer;     // kada je true znaci postoji livematch.xml na serveru


    private boolean sortirano;


    public static Utakmica getInstance() {
        if (utakmica == null)
            utakmica = new Utakmica();
        return utakmica;
    }

    private Utakmica() {
        svaDogadjanja = new ArrayList<Dogadjaj>();
        sortirano = true;
        fromHttpServer = false;
    }

    public BJDatum getDatum() {
        return datum;
    }

    public void setDatum(BJDatum datum) {
        this.datum = datum;
    }

    public void setDatum(String dat) {
        try {
            this.datum = new BJDatum(dat.trim());
        } catch (ParseException pe) {
            this.datum = new BJDatum();
        }
    }

    public BJTime getPlaniranoVremePocetka() {
        return planiranoVremePocetka;
    }

    public void setPlaniranoVremePocetka(BJTime planiranoVremePocetka) {
        this.planiranoVremePocetka = planiranoVremePocetka;
    }


    public void setPlaniranoVremePocetka(String vreme) {
        this.planiranoVremePocetka = new BJTime(vreme.trim());
    }

    public int getProtivnikId() {
        return protivnikId;
    }

    public void setProtivnikId(int protivnikId) {
        this.protivnikId = protivnikId;
    }

    public void setProtivnikId(String id) {
        try {
            protivnikId = Integer.parseInt(id.trim());
        } catch (NumberFormatException nfe) {
            setProtivnikId(1);
        }
    }

    public int getStadionId() {
        return stadionId;
    }

    public void setStadionId(int stadionId) {
        this.stadionId = stadionId;
    }


    public void setStadionId(String id) {
        try {
            stadionId = Integer.parseInt(id.trim());
        } catch (NumberFormatException nfe) {
            stadionId = 1;
        }
    }

    public boolean isUserTeamDomacin() {
        return userTeamDomacin;
    }

    public void setUserTeamDomacin(boolean userTeamDomacin) {
        this.userTeamDomacin = userTeamDomacin;
    }


    public boolean isFromHttpServer() {
        return fromHttpServer;
    }

    public void setFromHttpServer(boolean fromHttpServer) {
        this.fromHttpServer = fromHttpServer;
    }

    public ArrayList<Dogadjaj> getSvaDogadjanja() {
        return svaDogadjanja;
    }

    public void refresh() {
        svaDogadjanja.clear();

        sortirano = true;
    }

    public void add(Dogadjaj dogadjaj) {
        sortirano = false;
        if (!svaDogadjanja.contains(dogadjaj))
            svaDogadjanja.add(dogadjaj);
    }

    public void sortiraj() {
        NumericStringComparator dc = new NumericStringComparator();
        Collections.sort(svaDogadjanja, dc);
        sortirano = true;
    }

    public void remove(Dogadjaj dogadjaj) {
        if (svaDogadjanja.contains(dogadjaj)){
            Log.w(TAG," contains dogadja and is to be removed");
            svaDogadjanja.remove(dogadjaj);}else{
            Log.w(TAG," NOT containing dogadja NOR removed");

        }
    }

    public boolean isTodayMatch() {
        return datum.isToday();
    }



    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(datum.toString() + "\t" + planiranoVremePocetka.toString() + "\t" + protivnikId + "\t" + stadionId + "\n");
        Iterator iter = svaDogadjanja.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
