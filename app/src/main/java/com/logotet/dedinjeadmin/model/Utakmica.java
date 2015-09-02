package com.logotet.dedinjeadmin.model;

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


    private ArrayList<Dogadjaj> svaDogadjanja;
    private ArrayList<Dogadjaj> vremenskiTok;
    private ArrayList<Dogadjaj> tokZaPrikaz;

    private static Utakmica utakmica = null;


    private BJDatum datum;
    private BJTime planiranoVremePocetka; // client time
    private BJTime[] stvarnoVremePocetka; // server time
    private BJTime[] stvarnoVremeKraja; // server time

    private int protivnikId;
    private int stadionId;

    private boolean userTeamDomacin;

    private boolean fromHttpServer;     // kada je true znaci postoji livematch.xml na serveru


    private BJTime lastVremenski;

    private boolean sortirano;


    public static Utakmica getInstance() {
        if (utakmica == null)
            utakmica = new Utakmica();
        return utakmica;
    }

    private Utakmica() {
        svaDogadjanja = new ArrayList<Dogadjaj>();
        vremenskiTok = new ArrayList<Dogadjaj>();
        tokZaPrikaz = new ArrayList<Dogadjaj>();
        sortirano = true;
        fromHttpServer = false;
        stvarnoVremePocetka = new BJTime[2];
        stvarnoVremePocetka[0] = null;
        stvarnoVremePocetka[1] = null;

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

    public ArrayList<Dogadjaj> getTokZaPrikaz() {
        return tokZaPrikaz;
    }


    public void refresh() {
        svaDogadjanja.clear();
        vremenskiTok.clear();
        tokZaPrikaz.clear();
        sortirano = true;
    }


    public void add(Dogadjaj dogadjaj) {
        sortirano = false;
        if (!svaDogadjanja.contains(dogadjaj))
            svaDogadjanja.add(dogadjaj);

        if (dogadjaj.isVremenski())
            if (!vremenskiTok.contains(dogadjaj))
                vremenskiTok.add(dogadjaj);

        if (dogadjaj.isInformativni() || dogadjaj.isRezultatski() || dogadjaj.isKazneni())
            if (!tokZaPrikaz.contains(dogadjaj))
                tokZaPrikaz.add(dogadjaj);
    }

    public void sortiraj() {
        NumericStringComparator dc = new NumericStringComparator();
        Collections.sort(svaDogadjanja, dc);
        Collections.sort(vremenskiTok, dc);
        Collections.sort(tokZaPrikaz, dc);
        sortirano = true;
    }

    public void remove(Dogadjaj dogadjaj) {
        if (svaDogadjanja.contains(dogadjaj))
            svaDogadjanja.remove(dogadjaj);
        if (vremenskiTok.contains(dogadjaj))
            vremenskiTok.remove(dogadjaj);
        if (tokZaPrikaz.contains(dogadjaj))
            tokZaPrikaz.remove(dogadjaj);

    }

    public void odrediMinutazu() {
        sortiraj();
        Iterator<Dogadjaj> iter = vremenskiTok.iterator();
        while (iter.hasNext()) {
            Dogadjaj d = iter.next();
            if (d.getTipDogadjaja() == Dogadjaj.STARTUTAKMICE)
                stvarnoVremePocetka[0] = new BJTime(d.getServerTime().getSeconds() + d.getMinut() * 60);
            if (d.getTipDogadjaja() == Dogadjaj.HALFTIME)
                stvarnoVremeKraja[0] = new BJTime(d.getServerTime().getSeconds());

            if (d.getTipDogadjaja() == Dogadjaj.STARTDRUGOPOLUVREME)
                stvarnoVremePocetka[1] = new BJTime(d.getServerTime().getSeconds() + d.getMinut() * 60);
            if (d.getTipDogadjaja() == Dogadjaj.FINALTIME)
                stvarnoVremeKraja[1] = new BJTime(d.getServerTime().getSeconds());
        }


        if (stvarnoVremePocetka[0] == null)
            if (svaDogadjanja.size() > 0) {
                Dogadjaj d = svaDogadjanja.get(0);
                stvarnoVremePocetka[0] = new BJTime(d.getServerTime().getSeconds() - 300);// 5 minuta pre prvog dogadjaja
                if (stvarnoVremePocetka[1] == null)
                    stvarnoVremePocetka[1] = new BJTime(stvarnoVremePocetka[0].getSeconds() + (60 * 60));

                if (stvarnoVremeKraja[0] == null)
                    stvarnoVremeKraja[0] = new BJTime(stvarnoVremePocetka[0].getSeconds() + (50 * 60));

                if (stvarnoVremeKraja[1] == null)
                    stvarnoVremeKraja[0] = new BJTime(stvarnoVremePocetka[1].getSeconds() + (50 * 60));
            }

        iter = svaDogadjanja.iterator();
        while (iter.hasNext()) {
            Dogadjaj d = iter.next();
            d.modifyMinut(stvarnoVremePocetka, stvarnoVremeKraja);
        }
    }

    public boolean isTodayMatch() {
        return datum.isToday();

    }
/*
    public String getCurrentTime() {
        if (!sortirano)
            sortiraj();
       if(uToku()){}
        else
           if(isFinished())
               return "FT";
        else
               return  planiranoVremePocetka.toString();

    }
*/


    public boolean uToku() {
        if (!datum.isToday())
            return false;
        if (svaDogadjanja.size() == 0)
            return false;
        if (isFinished())
            return false;
        return isStarted();
    }

    public boolean isStarted() {
        Iterator<Dogadjaj> iter = vremenskiTok.iterator();
        while (iter.hasNext()) {
            Dogadjaj d = iter.next();
            if (d.getTipDogadjaja() == Dogadjaj.STARTUTAKMICE)
                return true;
        }
        return false;
    }

    public boolean isFinished() {
        Iterator<Dogadjaj> iter = vremenskiTok.iterator();
        while (iter.hasNext()) {
            Dogadjaj d = iter.next();
            if (d.getTipDogadjaja() == Dogadjaj.FINALTIME)
                return true;
        }
        return false;
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
