package com.logotet.dedinjeadmin.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 8/27/15.
 */
public class Tabela {
    private static Tabela tabela = null;
    private ArrayList<TabelaRow> plasman;

    private boolean loaded; // load from tabela.xml

    private String sezona;
    private int lastRound;
    public static Tabela getInstance() {
        if (tabela == null)
            tabela = new Tabela();
        return tabela;
    }

    private Tabela() {
        plasman = new ArrayList<TabelaRow>();
        loaded = false;
    }

    public ArrayList<TabelaRow> getPlasman() {
        return plasman;
    }


    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public String getSezona() {
        return sezona;
    }

    public void setSezona(String sezona) {
        this.sezona = sezona;
    }

    public int getLastRound() {
        return lastRound;
    }

    public void setLastRound(int lastRound) {
        this.lastRound = lastRound;
    }
    public void setLastRound(String lround) {
        try{
            setLastRound(Integer.parseInt(lround.trim()));
        }catch (NumberFormatException nfe){
            setLastRound(0);
        }
    }

    public void add(TabelaRow row) {
        if (!plasman.contains(row))
            plasman.add(row);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator iter = plasman.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
