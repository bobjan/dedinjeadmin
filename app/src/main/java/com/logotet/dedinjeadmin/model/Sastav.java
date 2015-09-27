package com.logotet.dedinjeadmin.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by logotet on 8/26/15.
 * nakon sto je sastav vec upisan u protokolu mogu se iz baze igraca izvuci oniigraci koji broj na dresu imaju manji od Igrac.MAXDRES
 */
public class Sastav {
    public static void add(int playerid, int dresbroj) {
        Igrac tmp = BazaIgraca.getInstance().getIgrac(playerid);
        if (tmp != null)
            tmp.setBrojNaDresu(dresbroj);
        else
            System.out.printf("Sastav.add() error:tmp == NUll");
    }
/*
    public static ArrayList<Igrac> getSastav() {
        ArrayList<Igrac> sastav = new ArrayList<Igrac>();
        BazaIgraca baza = BazaIgraca.getInstance();
        Iterator<Igrac> iter = baza.getIterator();
        while (iter.hasNext()) {
            Igrac tmp = iter.next();
            if (tmp.getBrojNaDresu() < Igrac.MAXDRES)
                sastav.add(tmp);
        }
        IgracComparator ic = new IgracComparator(IgracComparator.BYDRES);
        Collections.sort(sastav, ic);
        return sastav;
    }

    public static void prikazi() {
        Iterator iter = getSastav().iterator();
        int i = 0;
        while (iter.hasNext()) {
            System.out.println(++i + ".\t" + iter.next().toString());
        }
    }*/
}
