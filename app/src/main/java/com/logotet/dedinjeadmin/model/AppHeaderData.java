package com.logotet.dedinjeadmin.model;

/**
 * Created by logotet on 8/27/15.
 */
public class AppHeaderData {
    private static AppHeaderData headerData = null;

    private String nazivLige;
    private String userTeamName;
    private String password;


    public static AppHeaderData getInstance() {
        if (headerData == null)
            headerData = new AppHeaderData();
        return headerData;
    }

    private AppHeaderData() {
    }

    public String getNazivLige() {
        return nazivLige;
    }

    public void setNazivLige(String nazivLige) {
        this.nazivLige = nazivLige;
    }

    public String getUserTeamName() {
        return userTeamName;
    }

    public void setUserTeamName(String userTeamName) {
        this.userTeamName = userTeamName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "liga: " + nazivLige + "\ttim " + userTeamName + "\tpwd " + password;
    }
}
