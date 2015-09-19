package com.logotet.dedinjeadmin.model;

import com.logotet.dedinjeadmin.model.Dogadjaj;
import com.logotet.dedinjeadmin.model.Utakmica;
import com.logotet.util.BJDatum;
import com.logotet.util.BJTime;
import com.logotet.util.NumericStringComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by logotet on 9/3/15.
 */
public class MatchAnalizator {
    private ArrayList<Dogadjaj> vremenskiTok;
    private ArrayList<Dogadjaj> tokZaPrikaz;

    private BJDatum datum; // livematch.xml time

    private BJTime[] stvarnoVremePocetka; // server time
    private BJTime[] stvarnoVremeKraja; // server time

    Utakmica utakmica;

    private int[] rezultat;      // 0 - Dedinje   1 - protivnik
    boolean refreshed;

    public MatchAnalizator(Utakmica utakmica) {
        vremenskiTok = new ArrayList<Dogadjaj>();
        tokZaPrikaz = new ArrayList<Dogadjaj>();
        stvarnoVremePocetka = new BJTime[2];
        stvarnoVremeKraja = new BJTime[2];
        stvarnoVremePocetka[0] = null;
        stvarnoVremePocetka[1] = null;
        stvarnoVremeKraja[0] = null;
        stvarnoVremeKraja[1] = null;
        this.utakmica = utakmica;
        refreshed = false;
        rezultat = new int[2];
    }

    public ArrayList<Dogadjaj> getVremenskiTok() {
        if(!refreshed)
            refresh();
        return vremenskiTok;
    }

    public ArrayList<Dogadjaj> getTokZaPrikaz() {
        if(!refreshed)
            refresh();
        return tokZaPrikaz;
    }

    public void setRefreshed(boolean refreshed) {
        this.refreshed = refreshed;
    }

    private void refresh() {
        datum = utakmica.getDatum();
        vremenskiTok.clear();
        tokZaPrikaz.clear();
        Iterator<Dogadjaj> iter = utakmica.getSvaDogadjanja().iterator();

        while (iter.hasNext()) {
            Dogadjaj dogadjaj = iter.next();
            if (dogadjaj.isVremenski()) {
                if (!vremenskiTok.contains(dogadjaj))
                    vremenskiTok.add(dogadjaj);
            } else if (!tokZaPrikaz.contains(dogadjaj))
                tokZaPrikaz.add(dogadjaj);
        }
        sortiraj();
        refreshed = true;
    }

    private void sortiraj() {
        DogadjajComparator dc = new DogadjajComparator();
        NumericStringComparator nsc = new NumericStringComparator();
        Collections.sort(vremenskiTok, nsc);
        Collections.sort(tokZaPrikaz, dc);
    }

    public void odrediMinutazu() {
        if(!refreshed)
            refresh();
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


// za slucaj da nema pocetka a ima dogadjaja
        if (stvarnoVremePocetka[0] == null)
            if (tokZaPrikaz.size() > 0) {
                Dogadjaj d = tokZaPrikaz.get(0);
                stvarnoVremePocetka[0] = new BJTime(d.getServerTime().getSeconds() - 300);// 5 minuta pre prvog dogadjaja
                if (stvarnoVremePocetka[1] == null)
                    stvarnoVremePocetka[1] = new BJTime(stvarnoVremePocetka[0].getSeconds() + (60 * 60));  // sat nakon pocetka

                if (stvarnoVremeKraja[0] == null)
                    stvarnoVremeKraja[0] = new BJTime(stvarnoVremePocetka[0].getSeconds() + (50 * 60));  // 50 minuta nakonpocetka

                if (stvarnoVremeKraja[1] == null)
                    stvarnoVremeKraja[0] = new BJTime(stvarnoVremePocetka[1].getSeconds() + (50 * 60));  // 50 minuta nakon pocetka II
            }
//
        iter = tokZaPrikaz.iterator();
        while (iter.hasNext()) {
            Dogadjaj d = iter.next();
            d.modifyMinut(stvarnoVremePocetka, stvarnoVremeKraja);
        }
    }

    public boolean uToku() {
        if(!refreshed)
            refresh();
        if (!datum.isToday())
            return false;
        if ((tokZaPrikaz.size() == 0) && (vremenskiTok.size() == 0))
            return false;
        if (isFinished())
            return false;
        return isStarted();
    }

    public boolean isStarted() {
        if(!refreshed)
            refresh();
        if (tokZaPrikaz.size() > 0)
            return true;
        if (vremenskiTok.size() > 0)
            return true;

        Iterator<Dogadjaj> iter = tokZaPrikaz.iterator();
        while (iter.hasNext()) {
            Dogadjaj d = iter.next();
            if (d.getTipDogadjaja() != Dogadjaj.KOMENTAR)
                return true;
        }

        return false;
    }

    public int[] getRezultat(){
        if(!refreshed)
            refresh();
        rezultat[0] = 0;
        rezultat[1] = 0;
        Iterator<Dogadjaj> iter = tokZaPrikaz.iterator();
        while (iter.hasNext()){
            Dogadjaj dogadjaj = iter.next();
            if(dogadjaj.isGoalDedinje())
                rezultat[0]++;
            if(dogadjaj.isGoalProtivnik())
                rezultat[1]++;
            dogadjaj.setRezultat(rezultat);
        }
        return rezultat;
    }

    public boolean isFinished() {
        if(!refreshed)
            refresh();
        Iterator<Dogadjaj> iter = vremenskiTok.iterator();
        while (iter.hasNext()) {
            Dogadjaj d = iter.next();
            if (d.getTipDogadjaja() == Dogadjaj.FINALTIME)
                return true;
        }
        return false;
    }

}
