package com.logotet.dedinjeadmin.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 8/27/15.
 */
public class Fixtures {
    private static Fixtures raspored = null;
    private ArrayList<FixturesRow> lista;

    public static Fixtures getInstance() {
        if (raspored == null)
            raspored = new Fixtures();
        return raspored;
    }

    private Fixtures() {
        lista = new ArrayList<FixturesRow>();
    }

    public void add(FixturesRow row) {
        if (!lista.contains(row))
            lista.add(row);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator iter = lista.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
