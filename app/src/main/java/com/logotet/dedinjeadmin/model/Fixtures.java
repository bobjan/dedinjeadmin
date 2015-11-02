package com.logotet.dedinjeadmin.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 8/27/15.
 */
public class Fixtures {
    private static Fixtures fixtures = null;
    private ArrayList<FixturesRow> raspored;

    private boolean loaded; // load from fixtures.xml completed

    private String sezona;
    public static Fixtures getInstance() {
        if (fixtures == null)
            fixtures = new Fixtures();
        return fixtures;
    }

    private Fixtures() {
        raspored = new ArrayList<FixturesRow>();
        loaded = false;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public ArrayList<FixturesRow> getRaspored() {
        return raspored;
    }

    public String getSezona() {
        return sezona;
    }

    public void setSezona(String sezona) {
        this.sezona = sezona;
    }

    public void add(FixturesRow row) {
        FixturesRow tmp = getKolo(row.getKolo());
        if(tmp == null)
            raspored.add(row);
        else{
            tmp.setDatum(row.getDatum());
            tmp.setDomacin(row.isDomacin());
            tmp.setPlayed(row.isPlayed());
            tmp.setProtivnik(row.getProtivnik());
            tmp.setTheyscored(row.getTheyscored());
            tmp.setWescored(row.getWescored());
        }
//        if (!raspored.contains(row))
//            raspored.add(row);
    }

    public FixturesRow getKolo(int kolo){
        for (int i = 0; i < raspored.size(); i++) {
            FixturesRow tmp = raspored.get(i);
            if (tmp.getKolo() == kolo)
                return tmp;
        }
        return null;
    }


    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator iter = raspored.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
