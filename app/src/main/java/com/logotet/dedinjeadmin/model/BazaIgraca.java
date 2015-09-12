package com.logotet.dedinjeadmin.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by logotet on 8/26/15.
 */
public class BazaIgraca {
    private static final String TAG = "BazaIgraca";
    private static BazaIgraca bazaIgraca = null;

    private ArrayList<Igrac> squad;  // svi registrovani igraci

    private ArrayList<Igrac> vanProtokola;  // svi registrovani igraci
    private ArrayList<Igrac> uProtokolu;  // svi registrovani igraci

    private boolean loaded;

    private BazaIgraca() {
        squad = new ArrayList<Igrac>();
        vanProtokola = new ArrayList<Igrac>();
        uProtokolu = new ArrayList<Igrac>();
        loaded = false;
    }

    public static BazaIgraca getInstance() {
        if (bazaIgraca == null)
            bazaIgraca = new BazaIgraca();
        return bazaIgraca;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public void add(Igrac igrac) {
        if (!squad.contains(igrac))
            squad.add(igrac);
    }

    public ArrayList<Igrac> getVanProtokola() {
        if (vanProtokola.size() == 0)
            refreshProtokol();
        return vanProtokola;
    }

    public ArrayList<Igrac> getuProtokolu() {
        return uProtokolu;
    }

    public ArrayList<Igrac> getNaTerenu() {
        ArrayList<Igrac> teren = new ArrayList<Igrac>();
        Iterator<Igrac> iter = uProtokolu.iterator();
        while (iter.hasNext()) {
            Igrac tmp = iter.next();
            if (tmp.isNaTerenu())
                teren.add(tmp);
        }
        return teren;
    }
    public ArrayList<Igrac> getNaKlupi() {
        ArrayList<Igrac> klupa = new ArrayList<Igrac>();
        Iterator<Igrac> iter = uProtokolu.iterator();
        while (iter.hasNext()) {
            Igrac tmp = iter.next();
            if (!tmp.isNaTerenu())
                klupa.add(tmp);
        }
        return klupa;
    }
    public Iterator getIterator() {
        return squad.iterator();
    }

    public Igrac getIgrac(int id) {
        for (int i = 0; i < squad.size(); i++) {
            Igrac tmp = squad.get(i);
            if (tmp.getId() == id)
                return tmp;
        }
        return null;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator<Igrac> iter = squad.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    public void refreshProtokol() {
        vanProtokola.clear();
        uProtokolu.clear();
        Iterator<Igrac> iter = squad.iterator();
        while (iter.hasNext()) {
            vanProtokola.add(iter.next());
        }
    }

    public boolean ubaciUProtokol(Igrac igrac) {
        if (vanProtokola.contains(igrac)) {
            igrac.setBrojNaDresu(uProtokolu.size() + 1);
            uProtokolu.add(igrac);
            vanProtokola.remove(igrac);
            if(igrac.getBrojNaDresu() < 12)
                igrac.setNaTerenu(true);
            else
                igrac.setNaTerenu(false);
            return true;
        }
        return false;
    }

    public boolean izbaciIzProtokola(Igrac igrac) {
        if (uProtokolu.contains(igrac)) {
            igrac.setBrojNaDresu(Igrac.MAXDRES);
            uProtokolu.remove(igrac);
            vanProtokola.add(igrac);
            prenumerisiProtokol();
            return true;
        }
        return false;
    }


    public void sortirajVanProtokola() {
        IgracComparator ic = new IgracComparator(IgracComparator.BYID);
        Collections.sort(vanProtokola, ic);
    }


    private void prenumerisiProtokol() {
        for (int i = 0; i < uProtokolu.size(); i++) {
            Igrac tmp = uProtokolu.get(i);
            tmp.setBrojNaDresu(i + 1);
            if(tmp.getBrojNaDresu() < 12)
                tmp.setNaTerenu(true);
            else
                tmp.setNaTerenu(false);
        }
    }


    public String getProtokol() {
        StringBuffer sb = new StringBuffer();
        int idx = 0;
        Iterator<Igrac> iter = uProtokolu.iterator();
        while (iter.hasNext()) {
            if (idx != 0)
                sb.append("-");
            Igrac ig = iter.next();
            sb.append("" + ig.getId());
            idx++;
        }
        return sb.toString();
    }

}
