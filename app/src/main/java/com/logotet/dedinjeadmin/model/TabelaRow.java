package com.logotet.dedinjeadmin.model;

/**
 * Created by logotet on 8/27/15.
 */
public class TabelaRow {
    private String broj;
    private String naziv;
    private String pwdl;
    private String goaldif;
    private String points;

    public TabelaRow(String broj, String naziv, String pwdl, String goaldif, String points) {
        this.broj = broj;
        this.naziv = naziv;
        this.pwdl = pwdl;
        this.goaldif = goaldif;
        this.points = points;
    }

    public String getBroj() {
        return broj;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getPwdl() {
        return pwdl;
    }

    public String getGoaldif() {
        return goaldif;
    }

    public String getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return broj + "\t" + naziv + "\t" + pwdl + "\t" + goaldif + "\t" + points;
    }
}
