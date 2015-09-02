package com.logotet.dedinjeadmin.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 8/26/15.
 */
public class BazaOsoba {
    private static BazaOsoba bazaOsoba = null;
    private ArrayList<Osoba> rukovodstvo;

    public static BazaOsoba getInstance() {
        if (bazaOsoba == null)
            bazaOsoba = new BazaOsoba();
        return bazaOsoba;
    }

    private BazaOsoba() {
        rukovodstvo = new ArrayList<Osoba>();
    }

    public void add(Osoba osoba) {
        if (!rukovodstvo.contains(osoba))
            rukovodstvo.add(osoba);
    }

    public Osoba getOsoba(int id) {
        for (int i = 0; i < rukovodstvo.size(); i++) {
           Osoba tmp = rukovodstvo.get(id);
            if (tmp.getId() == id)
                return tmp;
        }
        return null;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator iter = rukovodstvo.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            sb.append("\n");

        }
        return sb.toString();
    }
}
