package com.logotet.dedinjeadmin.model;

import com.logotet.util.BJDatum;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 8/26/15.
 */
public class Klub {
    private static Klub klub = null;
    private ArrayList<Osoba> rukovodstvo;

    private boolean loaded;

    private BJDatum datumOsnivanja;
    private String adresa;
    private String mesto;
    private String email;
    private String web;
    private String pib;
    private String matbroj;
    private String tekrac;


    public static Klub getInstance() {
        if (klub == null)
            klub = new Klub();
        return klub;
    }

    private Klub() {
        rukovodstvo = new ArrayList<Osoba>();
        loaded = false;
    }


    public BJDatum getDatumOsnivanja() {
        return datumOsnivanja;
    }

    public void setDatumOsnivanja(BJDatum datumOsnivanja) {
        this.datumOsnivanja = datumOsnivanja;
    }
    public void setDatumOsnivanja(String datum) {
        try{
           datumOsnivanja = new BJDatum(datum.trim());
        } catch (ParseException e) {
            datumOsnivanja = new BJDatum();
        }
    }


    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getMesto() {
        return mesto;
    }

    public void setMesto(String mesto) {
        this.mesto = mesto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getPib() {
        return pib;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public String getMatbroj() {
        return matbroj;
    }

    public void setMatbroj(String matbroj) {
        this.matbroj = matbroj;
    }

    public String getTekrac() {
        return tekrac;
    }

    public void setTekrac(String tekrac) {
        this.tekrac = tekrac;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
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
