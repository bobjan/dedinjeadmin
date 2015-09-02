package com.logotet.dedinjeadmin.model;

import com.logotet.util.BJTime;

/**
 * Created by boban on 8/30/15.
 */
public class Servertime {
    private static Servertime servertime = null;


    private BJTime serverTime;
    private BJTime clientTime;


    public static Servertime getInstance() {
        if (servertime == null)
            servertime = new Servertime();
        return servertime;
    }


    public static Servertime getInstance(String stime) {
        if (servertime == null)
            servertime = new Servertime();
        servertime.setClientTime(new BJTime());

        int numtime = Integer.parseInt(stime.trim());

        int sec = numtime % 100;
        numtime /= 100;
        int min = numtime % 100;
        int hour = numtime / 100;
        servertime.setServerTime(new BJTime(hour, min, sec));
        return servertime;
    }

    private Servertime() {
        serverTime = new BJTime();
        clientTime = new BJTime();
    }


    public BJTime getServerTime() {
        return serverTime;
    }

    public void setServerTime(BJTime serverTime) {
        this.serverTime = serverTime;
    }

    public BJTime getClientTime() {
        return clientTime;
    }

    public void setClientTime(BJTime clientTime) {
        this.clientTime = clientTime;
    }


    public int getDiff(){
        return (int) (serverTime.getSeconds() - clientTime.getSeconds());
    }

    @Override
    public String toString() {
        return "S:" + serverTime.toString() + "\tC:" + clientTime.toString();
    }
}
