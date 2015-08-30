package com.logotet.dedinjeadmin.model;

import java.util.Comparator;

/**
 * Created by logotet on 8/26/15.
 */
public class IgracComparator implements Comparator {
    public static final int BYID = 2;
    public static final int BYPOSITION = 3;
    public static final int BYDRES = 1;

    private int bywhat;

    public IgracComparator(int id) {
        bywhat = id;
    }

    public int compare(Object o1, Object o2) {
        Igrac d1 = (Igrac) o1;
        Igrac d2 = (Igrac) o2;
        if (bywhat == BYID)
            return d2.getId() - d1.getId();
        else if (bywhat == BYDRES){
            int koef1 = d1.isNaTerenu() ? 0 : 100;
            int koef2 = d2.isNaTerenu() ? 0 : 100;
            return (koef1 * d1.getBrojNaDresu()) - (koef2 * d2.getBrojNaDresu());
        }
        else
            return d1.getId() - d2.getId();
    }

    public boolean equals(Object obj) {
        return false;
    }
}
