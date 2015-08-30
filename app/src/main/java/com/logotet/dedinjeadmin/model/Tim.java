package com.logotet.dedinjeadmin.model;

/**
 * Created by logotet on 8/26/15.
 */
public class Tim {
    private int id;
    private String naziv;

    private boolean userTeam;

    public Tim(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;
        userTeam = false;
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public boolean isUserTeam() {
        return userTeam;
    }

    public void setUserTeam(boolean userTeam) {
        this.userTeam = userTeam;
    }

    public String toString() {
        return naziv;
//        return "id=" + id + "\tnaziv=" + naziv;
    }
}
