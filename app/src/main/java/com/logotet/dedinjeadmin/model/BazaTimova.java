package com.logotet.dedinjeadmin.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 8/26/15.
 */
public class BazaTimova {
    private static BazaTimova bazaTimova = null;
    private ArrayList<Tim> protivnici;

    private boolean loaded;

    public static BazaTimova getInstance() {
        if (bazaTimova == null)
            bazaTimova = new BazaTimova();
        return bazaTimova;
    }

    private BazaTimova() {
        protivnici = new ArrayList<Tim>();
        loaded = false;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void add(Tim tim) {
        if (!protivnici.contains(tim))
            protivnici.add(tim);
    }

    public Tim getTim(int id) {
        for (int i = 0; i < protivnici.size(); i++) {
            Tim tmp = protivnici.get(id);
            if (tmp.getId() == id)
                return tmp;
        }
        return null;
    }

    public ArrayList<Tim> getProtivnici() {
        return protivnici;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator iter = protivnici.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            sb.append("\n");

        }
        return sb.toString();
    }
}
