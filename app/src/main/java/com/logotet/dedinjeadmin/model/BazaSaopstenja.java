package com.logotet.dedinjeadmin.model;

import com.logotet.util.DateBasedComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by logotet on 8/27/15.
 */
public class BazaSaopstenja {
    private static BazaSaopstenja bazaSaopstenja = null;
    private ArrayList<Saopstenje> vesti;

    private boolean loaded; // load from saopstenje.xml xompleted

    public static BazaSaopstenja getInstance() {
        if (bazaSaopstenja == null)
            bazaSaopstenja = new BazaSaopstenja();
        return bazaSaopstenja;
    }

    private BazaSaopstenja() {
        vesti = new ArrayList<Saopstenje>();
        loaded = false;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void add(Saopstenje row) {
        if (!vesti.contains(row))
            vesti.add(row);
    }

    public void sortiraj() {
        DateBasedComparator dbc = new DateBasedComparator(-1);
        Collections.sort(vesti, dbc);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator iter = vesti.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
