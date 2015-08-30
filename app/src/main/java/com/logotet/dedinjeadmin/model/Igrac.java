package com.logotet.dedinjeadmin.model;


/**
 * Created by logotet on 8/26/15.
 */
public class Igrac {
    public static int MAXDRES = 99;

    private int id;
    private String naziv;
    private int godinaRodjenja;
    private String imageFileName;

    private int defaultPozicija;

    private int brojNaDresu;// ako je < MAXDRES onda je znaci igrac u protokolu

    private boolean naTerenu;

    private Igrac(int id, String naziv, String imgFileName) {
        this.id = id;
        this.naziv = naziv;
        this.imageFileName = imgFileName;
        naTerenu = false;
        brojNaDresu = MAXDRES;
    }

    public Igrac(int id, String naziv, int godinaRodjenja, int pozic, String imgFileName) {
        this(id, naziv, imgFileName);
        this.godinaRodjenja = godinaRodjenja;
        this.defaultPozicija = pozic;
    }

    public Igrac(int id, String naziv, String godinaRodjenja, String pozic, String imgFileName) {
        this(id, naziv, imgFileName);
        try {
            this.godinaRodjenja = Integer.parseInt(godinaRodjenja.trim());
            this.defaultPozicija = Integer.parseInt(pozic.trim());
        } catch (NumberFormatException nfe) {
            this.godinaRodjenja = 2000;
            this.defaultPozicija = 2;
        }
    }


    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getGodinaRodjenja() {
        return godinaRodjenja;
    }

    public String getImageFileName() {
        return imageFileName;
    }


    public int getBrojNaDresu() {
        return brojNaDresu;
    }

    public void setBrojNaDresu(int brojNaDresu) {
        this.brojNaDresu = brojNaDresu;
    }

    public int getDefaultPozicija() {
        return defaultPozicija;
    }

    public boolean isNaTerenu() {
        return naTerenu;
    }

    public void setNaTerenu(boolean naTerenu) {
        this.naTerenu = naTerenu;
    }

    public String toString() {
//        return "id=" + id + "\tnaziv=" + naziv + "\tdres=" + brojNaDresu + "\tgodina=" + godinaRodjenja + "\tpoz=" + defaultPozicija + "\timg=" + imageFileName;
        return naziv;
    }
}
