package com.logotet.dedinjeadmin.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 8/27/15.
 */
public class Tabela {
    private static Tabela tabela = null;
    private ArrayList<TabelaRow> lista;

    private String sezona;
    private int lastRound;
    public static Tabela getInstance() {
        if (tabela == null)
            tabela = new Tabela();
        return tabela;
    }

    private Tabela() {
        lista = new ArrayList<TabelaRow>();
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
        if (!lista.contains(row))
            lista.add(row);
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator iter = lista.iterator();
        while (iter.hasNext()) {
            sb.append(iter.next().toString());
            sb.append("\n");
        }
        return sb.toString();
    }
}
