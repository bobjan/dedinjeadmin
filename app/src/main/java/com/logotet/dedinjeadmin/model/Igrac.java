package com.logotet.dedinjeadmin.model;


/**
 * Created by logotet on 8/26/15.
 */
public class Igrac {
    public static int MAXDRES = 99;

    private int id;
    private String naziv;
    private int godinaRodjenja;
    private int visina;
    private int tezina;
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


    public Igrac(int id, String naziv, String godinaRodjenja, String pozic, String visina, String tezina, String imgFileName) {
        this(id, naziv, imgFileName);
        try {
            this.godinaRodjenja = Integer.parseInt(godinaRodjenja.trim());
            this.visina = Integer.parseInt(visina.trim());
            this.tezina = Integer.parseInt(tezina.trim());
            this.defaultPozicija = Integer.parseInt(pozic.trim());
        } catch (NumberFormatException nfe) {
            this.godinaRodjenja = 2000;
            this.defaultPozicija = 2;
            this.visina = 180;
            this.tezina = 80;
        }

        try {
            this.godinaRodjenja = Integer.parseInt(godinaRodjenja.trim());
        } catch (NumberFormatException nfe) {
            this.godinaRodjenja = 2000;
        }

        try {
            this.visina = Integer.parseInt(visina.trim());
        } catch (NumberFormatException nfe) {
            this.visina = 180;
        }

        try {
            this.tezina = Integer.parseInt(tezina.trim());
        } catch (NumberFormatException nfe) {
            this.tezina = 80;
        }

        try {
            this.defaultPozicija = Integer.parseInt(pozic.trim());
        } catch (NumberFormatException nfe) {
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
        return naziv;
    }
    public String punOpis(){
        return "id=" + id + "\tnaziv=" + naziv + "\tdres=" + brojNaDresu + "\tgodina=" + godinaRodjenja + "\tpoz=" + defaultPozicija + "\tteren=" + isNaTerenu();

    }
}
