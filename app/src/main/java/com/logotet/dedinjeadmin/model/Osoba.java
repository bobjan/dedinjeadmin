package com.logotet.dedinjeadmin.model;


/**
 * Created by logotet on 8/26/15.
 */
public class Osoba {
    private int id;
    private String naziv;
    private String funkcija;
    private String imageFileName;


    public Osoba(int id, String naziv,String funkcija, String imgFileName) {
        this.id = id;
        this.naziv = naziv;
        this.imageFileName = imgFileName;
        this.funkcija = funkcija;
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String getFunkcija() {
        return funkcija;
    }

    public String toString() {
        return "id=" + id + "\tnaziv=" + naziv + "\tfunkcija=" + funkcija + "\timg=" + imageFileName;
//        return naziv;
    }
}
