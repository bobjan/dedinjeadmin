package com.logotet.dedinjeadmin.model;


/**
 * Created by logotet on 8/26/15.
 */
public class Stadion {
    private int id;
    private String naziv;
    private String imageFileName;

    private double lattitude;
    private double longitude;

    public Stadion(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;
        this.lattitude = 0.0;
        this.longitude = 0.0;
        imageFileName = "";
    }


    public Stadion(int id, String naziv, String imageFileName) {
        this(id, naziv);
        this.imageFileName = imageFileName;
    }

    public Stadion(int id, String naziv, double lat, double longit) {
        this(id, naziv);
        this.lattitude = lat;
        this.longitude = longit;
    }

    public Stadion(int id, String naziv, String lat, String longit) {
        this(id, naziv);
        try {
            this.lattitude = Double.parseDouble(lat.trim());
            this.longitude = Double.parseDouble(longit.trim());
        } catch (NumberFormatException nfe) {
        }
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

    public String toString() {
//        return "id=" + id + "\tnaziv=" + naziv + "\tlat=" + lattitude + "\tlong=" + longitude;
        return naziv;
    }
}
