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
    private String napomena;

    private int defaultPozicija;

    private int brojNaDresu;// ako je < MAXDRES onda je znaci igrac u protokolu

    private boolean naTerenu;

    private boolean imageLoaded;

    private Object image;

    private Igrac(int id, String naziv, String imgFileName) {
        this.id = id;
        this.naziv = naziv;
        this.imageFileName = imgFileName;
        naTerenu = false;
        brojNaDresu = MAXDRES;
        imageLoaded = false;
        this.napomena = "";
    }


    public Igrac(int id, String naziv, String godinaRodjenja, String pozic, String visina, String tezina, String imgFileName) {
        this(id, naziv, imgFileName);

        try {
            this.godinaRodjenja = Integer.parseInt(godinaRodjenja.trim());
        } catch (NumberFormatException nfe) {
            this.godinaRodjenja = 0;
        }

        try {
            this.visina = Integer.parseInt(visina.trim());
        } catch (NumberFormatException nfe) {
            this.visina = 0;
        }

        try {
            this.tezina = Integer.parseInt(tezina.trim());
        } catch (NumberFormatException nfe) {
            this.tezina = 0;
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

    public boolean isImageLoaded() {
        return imageLoaded;
    }

    public void setImageLoaded(boolean imageLoaded) {
        this.imageLoaded = imageLoaded;
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

    public String getNapomena() {
        if(napomena == null)
            napomena = "";
        return napomena;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public void setGodinaRodjenja(int godinaRodjenja) {
        this.godinaRodjenja = godinaRodjenja;
    }

    public void setVisina(int visina) {
        this.visina = visina;
    }

    public void setTezina(int tezina) {
        this.tezina = tezina;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public void setDefaultPozicija(int defaultPozicija) {
        this.defaultPozicija = defaultPozicija;
    }

    public int getVisina() {
        return visina;
    }

    public int getTezina() {
        return tezina;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public boolean isNaTerenu() {
        return naTerenu;
    }

    public void setNaTerenu(boolean naTerenu) {
        this.naTerenu = naTerenu;
    }

    public String stringId(){
        return id + "I";
    }

    public String toString() {
        return naziv;
    }
    public String punOpis(){
        return "id=" + id + "\tnaziv=" + naziv + "\tdres=" + brojNaDresu + "\tgodina=" + godinaRodjenja + "\tpoz=" + defaultPozicija + "\tteren=" + isNaTerenu();

    }
}
