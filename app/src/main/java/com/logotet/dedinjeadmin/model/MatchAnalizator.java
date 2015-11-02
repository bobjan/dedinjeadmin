package com.logotet.dedinjeadmin.model;

import com.logotet.util.BJDatum;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 9/3/15.
 */
public class MatchAnalizator {
    private ArrayList<Dogadjaj> vremenskiTok;
    private ArrayList<Dogadjaj> tokZaPrikaz;

    private BJDatum datum; // livematch.xml time

    MatchTimer matchTimer;

    Utakmica utakmica;

    private int[] rezultat;      // 0 - Dedinje   1 - protivnik
    boolean refreshed;

    public MatchAnalizator(Utakmica utakmica) {
        vremenskiTok = new ArrayList<Dogadjaj>();
        tokZaPrikaz = new ArrayList<Dogadjaj>();
        this.utakmica = utakmica;
        refreshed = false;
        rezultat = new int[2];
        matchTimer = new MatchTimer();
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


    /**
     *
     * iznova rasporedjuje dogadjaje na vremenske i one za prikaz
     *
     *
     * */
    private void refresh() {
        datum = utakmica.getDatum();
        vremenskiTok.clear();
        tokZaPrikaz.clear();
        utakmica.sortiraj();
        Iterator<Dogadjaj> iter = utakmica.getSvaDogadjanja().iterator();

        while (iter.hasNext()) {
            Dogadjaj dogadjaj = iter.next();
            if (dogadjaj.isVremenski()) {
                if (!vremenskiTok.contains(dogadjaj))
                    vremenskiTok.add(dogadjaj);
            } else if (!tokZaPrikaz.contains(dogadjaj))
                tokZaPrikaz.add(dogadjaj);
        }
        refreshed = true;
    }

    public void odrediMinutazu() {
        if(!refreshed)
            refresh();
        matchTimer.recalculateAll(utakmica.getSvaDogadjanja());
    }

    public boolean uToku() {
        if(!refreshed)
            refresh();
        if (!datum.isToday())
            return false;
        if (isFinished())
            return false;
        return isStarted();
    }

    public boolean isStarted() {
        if(!refreshed)
            refresh();
        return matchTimer.isStarted();
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
        return matchTimer.isFinished();
    }

    public boolean isFirstHalfFinished() {
        if(!refreshed)
            refresh();
        return matchTimer.isFirstHalfFinished();
    }

    public int getCurrentMinutIgre(){
        return matchTimer.getCurrentMinut();
    }

}
