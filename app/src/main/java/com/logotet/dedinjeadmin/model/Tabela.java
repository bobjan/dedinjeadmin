package com.logotet.dedinjeadmin.model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by logotet on 8/27/15.
 */
public class Tabela {
    private static Tabela tabela = null;
    private ArrayList<TabelaRow> lista;

    public static Tabela getInstance() {
        if (tabela == null)
            tabela = new Tabela();
        return tabela;
    }

    private Tabela() {
        lista = new ArrayList<TabelaRow>();
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
