package com.logotet.dedinjeadmin.model;


/**
 * Created by logotet on 8/26/15.
 */
public class Osoba {
    private int id;
    private String naziv;
    private String funkcija;
    private String imageFileName;

    private String napomena;

    private boolean imageLoaded;

private Object image;

    public Osoba(int id, String naziv,String funkcija, String imgFileName) {
        this.id = id;
        this.naziv = naziv;
        this.imageFileName = imgFileName;
        this.funkcija = funkcija;
        this.napomena = "";
        imageLoaded = false;
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

    public void setFunkcija(String funkcija) {
        this.funkcija = funkcija;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public String getFunkcija() {
        return funkcija;
    }

    public String getNapomena() {
        return napomena;
    }

    public boolean isImageLoaded() {
        return imageLoaded;
    }

    public void setImageLoaded(boolean imageLoaded) {
        this.imageLoaded = imageLoaded;
    }

    public void setNapomena(String napomena) {
        this.napomena = napomena;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String stringId(){
        return id + "R";
    }
    public String toString() {
        return "id=" + id + "\tnaziv=" + naziv + "\tfunkcija=" + funkcija + "\timg=" + imageFileName;
//        return naziv;
    }
}
