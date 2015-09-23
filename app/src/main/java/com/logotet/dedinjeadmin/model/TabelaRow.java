package com.logotet.dedinjeadmin.model;

import java.util.StringTokenizer;

/**
 * Created by logotet on 8/27/15.
 */
public class TabelaRow {
    private String broj;
    private String naziv;
    private String pwdl;
    private String goaldif;
    private String points;
    private boolean userTeam;

    private int goalsFor;
    private int goalsAgainst;

    private int win;
    private int draw;
    private int lose;
    private int played;

    public TabelaRow(String broj, String naziv, String pwdl, String goaldif, String points) {
        this.broj = broj;
        this.naziv = naziv;
        this.pwdl = pwdl;
        this.goaldif = goaldif;
        this.points = points;
        parseAll();
    }

    private void parseAll() {
        StringTokenizer st = new StringTokenizer(pwdl, " ");
        try {
           win = Integer.parseInt(st.nextToken().trim());
        } catch (NumberFormatException nfe) {
            win = 0;
        }
        try {
           draw = Integer.parseInt(st.nextToken().trim());
        } catch (NumberFormatException nfe) {
            draw = 0;
        }
        try {
           lose = Integer.parseInt(st.nextToken().trim());
        } catch (NumberFormatException nfe) {
            lose = 0;
        }

        st = new StringTokenizer(goaldif,":");
        try {
           goalsFor = Integer.parseInt(st.nextToken().trim());
        } catch (NumberFormatException nfe) {
            goalsFor = 0;
        }
        try {
           goalsAgainst = Integer.parseInt(st.nextToken().trim());
        } catch (NumberFormatException nfe) {
            goalsAgainst = 0;
        }
        played = win + draw + lose;
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
        return points.trim();
    }

    public boolean isUserTeam() {
        return userTeam;
    }

    public void setUserTeam(boolean userTeam) {
        this.userTeam = userTeam;
    }

    public void setUserTeam(String userTeam) {
        try {
            int tmp = Integer.parseInt(userTeam.trim());
            setUserTeam(tmp == 1);
        } catch (NumberFormatException nfe) {
            setUserTeam(false);
        } catch (NullPointerException npe) {
            setUserTeam(false);
        }
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public int getWin() {
        return win;
    }

    public int getDraw() {
        return draw;
    }

    public int getLose() {
        return lose;
    }

    public int getPlayed() {
        return played;
    }

    @Override
    public String toString() {
        return broj + "\t" + naziv + "\t" + pwdl + "\t" + goaldif + "\t" + points;
    }
}
