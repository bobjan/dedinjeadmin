package com.logotet.dedinjeadmin.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 8/26/15.
 */
public class BazaStadiona {
    private static BazaStadiona bazaStadiona = null;
    private ArrayList<Stadion> tereni;
    private boolean loaded;
    public static BazaStadiona getInstance() {
        if (bazaStadiona == null)
            bazaStadiona = new BazaStadiona();
        return bazaStadiona;
    }

    private BazaStadiona() {
        tereni = new ArrayList<Stadion>();
        loaded = false;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void add(Stadion std) {
        if (!tereni.contains(std))
            tereni.add(std);
    }

    public Stadion getStadion(int id) {
        for (int i = 0; i < tereni.size(); i++) {
            Stadion tmp = tereni.get(id);
            if (tmp.getId() == id)
                return tmp;
        }
        return null;
    }

    public ArrayList<Stadion> getTereni() {
        return tereni;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator iter = tereni.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            sb.append("\n");

        }
        return sb.toString();
    }
}
