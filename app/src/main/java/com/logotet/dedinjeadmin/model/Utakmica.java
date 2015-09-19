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

    MatchAnalizator matchAnalizator;

    public static Utakmica getInstance() {
        if (utakmica == null)
            utakmica = new Utakmica();
        return utakmica;
    }

    private Utakmica() {
        svaDogadjanja = new ArrayList<Dogadjaj>();
        sortirano = true;
        fromHttpServer = false;
        matchAnalizator = new MatchAnalizator(this);
        datum = new BJDatum();
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


    public ArrayList<Dogadjaj> getVremenskiTok() {
        return matchAnalizator.getVremenskiTok();
    }


    public ArrayList<Dogadjaj> getTokZaPrikaz() {
        return matchAnalizator.getTokZaPrikaz();
    }


    public void clear() {
        svaDogadjanja.clear();
        sortirano = true;
        matchAnalizator.setRefreshed(false);
    }

    public void add(Dogadjaj dogadjaj) {
        matchAnalizator.setRefreshed(false);
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
        matchAnalizator.setRefreshed(false);
        if (svaDogadjanja.contains(dogadjaj)) {
//            Log.w(TAG," contains dogadja and is to be removed");
            svaDogadjanja.remove(dogadjaj);
        } else {
//            Log.w(TAG," NOT containing dogadja NOR removed");
        }
    }


    /**
     * ako je u livescore.xml mec koji j eplaniran za danas, sutra ...
     * akoje danasnji, a vec je zavrsen vraca false
     **/
    public boolean isNextMatch() {
        BJDatum danas = new BJDatum();
        if (danas.daysDifference(datum) > 0) return false;
        if (datum.isToday() && isFinished()) return false;
        return true;
    }

    public boolean isFinished() {
        return matchAnalizator.isFinished();
    }

    public boolean uToku() {
        return matchAnalizator.uToku();
    }

    public boolean isStarted() {
        return matchAnalizator.isStarted();
    }

    public void odrediMinutazu() {

        matchAnalizator.odrediMinutazu();
    }


    public String getHomeTeamName() {
        if (!isUserTeamDomacin()) {
            Tim tim = BazaTimova.getInstance().getTim(protivnikId);
            if(tim == null)
                return "******";
            return tim.getNaziv();
        } else
            return AppHeaderData.getInstance().getUserTeamName();


    }

    public String getAwayTeamName() {
        if (isUserTeamDomacin()) {
          Tim tim = BazaTimova.getInstance().getTim(protivnikId);
            if(tim == null)
                return "*****";
            return tim.getNaziv();
        } else
            return AppHeaderData.getInstance().getUserTeamName();

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

    public String getCurrentRezulat() {
        int[] rez = matchAnalizator.getRezultat();
        if (userTeamDomacin) {
            return rez[0] + ":" + rez[1];
        } else {
            return rez[1] + ":" + rez[0];
        }
    }

    public String getCurrentRezulat(Dogadjaj dogadjaj) {
        return dogadjaj.getRezultat(userTeamDomacin);
    }


    public String getCurrentMinutIgre(){
        if(isFinished())
            return "FT";
        if(!isStarted())
            return planiranoVremePocetka.toString();
        return matchAnalizator.getCurrentMinutIgre() + "'";
    }

    public String getCurrentMinutIgre(Dogadjaj dogadjaj) {
        return dogadjaj.getMinutIgre() + "'";
    }

}
