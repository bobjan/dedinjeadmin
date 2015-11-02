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

    private boolean loaded; // load from ekipa.xml completed (without images)

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
        Igrac tmp = getIgrac(igrac.getId());
        if(tmp == null)
            squad.add(igrac);
        else{
            tmp.setNaziv(igrac.getNaziv());
            tmp.setNapomena(igrac.getNapomena());
            tmp.setBrojNaDresu(igrac.getBrojNaDresu());
            tmp.setNaTerenu(igrac.isNaTerenu());
            tmp.setTezina(igrac.getTezina());
            tmp.setVisina(igrac.getVisina());
            tmp.setImageFileName(igrac.getImageFileName());
            tmp.setImageLoaded(igrac.isImageLoaded());
            tmp.setImage(igrac.getImage());
            tmp.setDefaultPozicija(igrac.getDefaultPozicija());
        }
//        if (!squad.contains(igrac))
//            squad.add(igrac);
    }

    public ArrayList<Igrac> getSquad() {
        return squad;
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



    /**
     *
     * sve brojeve svima vraca na MAXDRES
     * **/
    public void refreshBrojeviNaDresu(){
        Iterator<Igrac> iter = squad.iterator();
        while(iter.hasNext()){
            Igrac igrac = iter.next();
            igrac.setBrojNaDresu(Igrac.MAXDRES);
        }
        vanProtokola.clear();
        uProtokolu.clear();
    }


    /**
     * na osnovi broja na dresu kreira protokol
     *
     * */
    public void refreshProtokol() {
        vanProtokola.clear();
        uProtokolu.clear();
        Iterator<Igrac> iter = squad.iterator();
        while (iter.hasNext()) {
            Igrac igrac = iter.next();
            vanProtokola.add(igrac);
            if(igrac.getBrojNaDresu() < Igrac.MAXDRES){
                ubaciUProtokol(igrac);
//                Log.w(TAG, igrac.punOpis());
            }
        }
        sortirajProtokol();
    }

    public void ubaciUProtokol(Igrac igrac) {
        if (vanProtokola.contains(igrac)) {
            if(igrac.getBrojNaDresu() == Igrac.MAXDRES)
                igrac.setBrojNaDresu(uProtokolu.size() + 1);
            uProtokolu.add(igrac);
            vanProtokola.remove(igrac);
            if(igrac.getBrojNaDresu() < 12)
                igrac.setNaTerenu(true);
            else
                igrac.setNaTerenu(false);
            return;
        }
    }


    /**
     * kada se vrsi interaktivna promena protokola obavezno je prenumersianje
     * */
    public void izbaciIzProtokola(Igrac igrac) {
        if (uProtokolu.contains(igrac)) {
            igrac.setBrojNaDresu(Igrac.MAXDRES);
            igrac.setNaTerenu(false);
            uProtokolu.remove(igrac);
            vanProtokola.add(igrac);
            prenumerisiProtokol();
            return;
        }

    }


    /**
     * igraci van protokola su sortirani po id[ radi displaya prilikom unosenja sastava
     *
     */
    public void sortirajVanProtokola() {
        IgracComparator ic = new IgracComparator(IgracComparator.BYID);
        Collections.sort(vanProtokola, ic);
    }

    /**
     * sortira po broju a dresu
     * */
    public void sortirajProtokol() {
        IgracComparator ic = new IgracComparator(IgracComparator.BYDRES);
        Collections.sort(uProtokolu, ic);
    }

    private void prenumerisiProtokol() {
        sortirajProtokol();
        for (int i = 0; i < uProtokolu.size(); i++) {
            Igrac tmp = uProtokolu.get(i);
            tmp.setBrojNaDresu(i + 1);
            if(tmp.getBrojNaDresu() < 12)
                tmp.setNaTerenu(true);
            else
                tmp.setNaTerenu(false);
        }
    }


    /**
     * rado slanja serveru i upisa sastava**/
    public String getProtokol() {
        prenumerisiProtokol(); // konac delo krasi
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
