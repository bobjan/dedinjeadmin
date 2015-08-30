package com.logotet.dedinjeadmin.model;

/**
 * Created by logotet on 8/26/15.
 */
public class Pozicija {
    private int id;
    private String naziv;

    public Pozicija(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public String toString() {
        return "id=" + id + "\tnaziv=" + naziv;
    }

}
