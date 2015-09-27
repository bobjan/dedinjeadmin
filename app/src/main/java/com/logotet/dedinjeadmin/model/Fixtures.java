package com.logotet.dedinjeadmin.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 8/27/15.
 */
public class Fixtures {
    private static Fixtures fixtures = null;
    private ArrayList<FixturesRow> raspored;

    private boolean loaded;

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
        if (!raspored.contains(row))
            raspored.add(row);
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
