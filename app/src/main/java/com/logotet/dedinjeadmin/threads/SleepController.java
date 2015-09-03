package com.logotet.dedinjeadmin.threads;

/**
 * Created by logotet on 9/2/15.
 */
public class SleepController {
    private static SleepController sleepController = null;

    private static final int MINUT = 60*1000;  //  60 * 1000 milisekundi

    private int sleepTime;  //   u mintima

    public static SleepController getInstance(){
           if(sleepController == null)
               sleepController = new SleepController();
        return sleepController;
    }

    private SleepController() {
        sleepTime = 5;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getFullSleepTime(){
           return sleepTime * MINUT;      // milisekunde
    }
}
