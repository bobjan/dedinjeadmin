package com.logotet.dedinjeadmin.model;


/**
 * Created by logotet on 8/26/15.
 */
public class Stadion {
    private int id;
    private String naziv;

    private double latitude;
    private double longitude;

    public Stadion(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;
        this.latitude = 0.0;
        this.longitude = 0.0;
    }


    public Stadion(int id, String naziv, double lat, double longit) {
        this(id, naziv);
        this.latitude = lat;
        this.longitude = longit;
    }

    public Stadion(int id, String naziv, String lat, String longit) {
        this(id, naziv);
        try {
            this.latitude = Double.parseDouble(lat.trim());
            this.longitude = Double.parseDouble(longit.trim());
        } catch (NumberFormatException nfe) {
            this.latitude = 0.0D;
            this.longitude = 0.0D;
        }
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String toString() {
//        return "id=" + id + "\tnaziv=" + naziv + "\tlat=" + lattitude + "\tlong=" + longitude;
        return naziv;
    }
}
