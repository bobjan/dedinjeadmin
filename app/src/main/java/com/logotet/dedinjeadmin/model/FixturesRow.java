package com.logotet.dedinjeadmin.model;

import com.logotet.util.BJDatum;

import java.text.ParseException;

/**
 * Created by logotet on 8/28/15.
 */
public class FixturesRow {
    private int kolo;
    private BJDatum datum;
    private String protivnik;
    private boolean domacin;
    private boolean played;
    private int wescored;
    private int theyscored;


    public FixturesRow(String kolo, String datum, String protivnik, String domacin, String played, String wescored, String theyscored) {

        this.protivnik = protivnik;

        try {
            this.kolo = Integer.parseInt(kolo.trim());
        } catch (NumberFormatException nfe) {
        }

        try {
            this.datum = new BJDatum(datum.trim());
        } catch (ParseException e) {
            this.datum = new BJDatum();
        }
        try {
            this.domacin = (Integer.parseInt(domacin.trim()) == 1);
        } catch (NumberFormatException nfe) {
            this.domacin = false;
        }
        try {
            this.played = (Integer.parseInt(played.trim()) == 1);

        } catch (NumberFormatException nfe) {
            this.played = false;
        }
        try {
            this.wescored = Integer.parseInt(wescored.trim());
        } catch (NumberFormatException nfe) {
        }
        try {
            this.theyscored = Integer.parseInt(theyscored.trim());
        } catch (NumberFormatException nfe) {
        }
    }

    public int getKolo() {
        return kolo;
    }

    public BJDatum getDatum() {
        return datum;
    }

    public String getProtivnik() {
        return protivnik;
    }

    public boolean isDomacin() {
        return domacin;
    }

    public boolean isPlayed() {
        return played;
    }

    public int getWescored() {
        return wescored;
    }

    public int getTheyscored() {
        return theyscored;
    }



    @Override
    public String toString() {
        return "kolo=" + kolo + "\tdatum=" + datum.toString() + "\tprotivnik=" + protivnik + "\tdomacin=" + domacin + "\tplayed=" +
                played + "\twescored=" + wescored + "\ttheyscored=" + theyscored;
    }
}
