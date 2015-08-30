package com.logotet.dedinjeadmin.model;

import com.logotet.util.BJTime;

/**
 * Created by logotet on 8/26/15.
 */
public class Dogadjaj implements DogadjajComparable {
    public static final int STARTUTAKMICE = 0;
    public static final int HALFTIME = 1;
    public static final int STARTDRUGOPOLUVREME = 2;
    public static final int FINALTIME = 3;
    public static final int TACANMINUT = 4;

    public static final int GOLFKDEDINJE = 5;
    public static final int GOLPROTIVNIK = 6;
    public static final int GOLPENALFKDEDINJE = 7;
    public static final int GOLPENALPROTIVNIK = 8;
    public static final int MISSEDPENALFKDEDINJE = 9;
    public static final int MISSEDPENALPROTIVNIK = 10;


    public static final int ZUTIKARTONFKDEDINJE = 11;
    public static final int ZUTIKARTONPROTIVNIK = 12;
    public static final int DRUGIZUTIFKDEDINJE = 13;
    public static final int DRUGIZUTIPROTIVNIK = 14;
    public static final int CRVENIKARTONFKDEDINJE = 15;
    public static final int CRVENIKARTONPROTIVNIK = 16;


    public static final int KOMENTAR = 17;
    public static final int IZMENAIGRACA = 18;

    public static final String[] opis = {"Почетак утакмице","Полувреме","Почетак 2. полувремена","Крај утакмице", "Корекција минута",
                        "Гол", "Гол", "Гол из пенала","Гол из пенала","Промашен пенал","Промашен пенал",
                        "Жути картон","Жути картон","Други жути картон","Други жути картон","Црвени картон","Црвени картон","Коментар","Измена играча"};


    private int tipDogadjaja;
    private int minut;
    private int playerId;
    private int playerInId;
    private int playerOutId;
    private String komentar;

    private String fileName;
    private BJTime serverTime;


    public Dogadjaj(String file, String serverVreme, int tipDogadjaja) {
        this.tipDogadjaja = tipDogadjaja;
        this.fileName = file;
        int tmpTime = Integer.parseInt(serverVreme.trim());

        int sec = tmpTime % 100;
        tmpTime /= 100;
        int min = tmpTime % 100;
        int hour = tmpTime / 100;
        serverTime = new BJTime(hour, min, sec);
    }


    public Dogadjaj(String file, String serverVreme, int tipDogadjaja, int someInt) {
        this(file, serverVreme, tipDogadjaja);
        if (isIgracki())
            this.playerId = someInt;
        else if (isVremenski())
            this.minut = someInt;
        else
            System.out.println("GRESKA !!!!!!");
    }


    public Dogadjaj(String file, String serverVreme, int tipDogadjaja, int playerInId, int playerOutId) {
        this(file, serverVreme, tipDogadjaja);
        this.playerInId = playerInId;
        this.playerOutId = playerOutId;
    }

    public Dogadjaj(String file, String serverVreme, int tipDogadjaja, String komentar) {
        this(file, serverVreme, tipDogadjaja);
        this.komentar = komentar;
    }

    public int getTipDogadjaja() {
        return tipDogadjaja;
    }

    public int getMinut() {
        return minut;
    }

    public int getPlayerId() {
        return playerId;
    }

    public int getPlayerInId() {
        return playerInId;
    }

    public int getPlayerOutId() {
        return playerOutId;
    }

    public String getKomentar() {
        return komentar;
    }


    public void setMinut(int minut) {
        this.minut = minut;
    }

    public void setMinut(String sminut) {
        try {
            this.minut = Integer.parseInt(sminut.trim());
        } catch (NumberFormatException nfe) {
            this.minut = 0;
        }
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public void setPlayerId(String pId) {
        try {
            this.playerId = Integer.parseInt(pId.trim());
        } catch (NumberFormatException nfe) {
            this.playerId = 0;
        }
    }


    public void setPlayerInId(int playerInId) {
        this.playerInId = playerInId;
    }

    public void setPlayerInId(String pId) {
        try {
            this.playerInId = Integer.parseInt(pId.trim());
        } catch (NumberFormatException nfe) {
            this.playerInId = 0;
        }
    }

    public void setPlayerOutId(int playerOutId) {
        this.playerOutId = playerOutId;
    }

    public void setPlayerOutId(String pId) {
        try {
            this.playerOutId = Integer.parseInt(pId.trim());
        } catch (NumberFormatException nfe) {
            this.playerOutId = 0;
        }
    }


    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public boolean isVremenski() {
        return (tipDogadjaja == STARTDRUGOPOLUVREME) ||
                (tipDogadjaja == STARTUTAKMICE) ||
                (tipDogadjaja == TACANMINUT);
    }

    public boolean isRezultatski() {
        return (tipDogadjaja == GOLFKDEDINJE) ||
                (tipDogadjaja == GOLPENALFKDEDINJE) ||
                (tipDogadjaja == GOLPENALPROTIVNIK) ||
                (tipDogadjaja == GOLPROTIVNIK);
    }

    public boolean isInformativni() {
        return (tipDogadjaja == KOMENTAR) ||
                (tipDogadjaja == MISSEDPENALFKDEDINJE) ||
                (tipDogadjaja == MISSEDPENALPROTIVNIK) ||
                (tipDogadjaja == IZMENAIGRACA);
    }

    public boolean isKazneni() {
        return (tipDogadjaja == ZUTIKARTONFKDEDINJE) ||
                (tipDogadjaja == ZUTIKARTONPROTIVNIK) ||
                (tipDogadjaja == DRUGIZUTIFKDEDINJE) ||
                (tipDogadjaja == DRUGIZUTIPROTIVNIK) ||
                (tipDogadjaja == CRVENIKARTONFKDEDINJE) ||
                (tipDogadjaja == CRVENIKARTONPROTIVNIK);
    }

    public boolean isIgracki() {
        return (tipDogadjaja == GOLFKDEDINJE) ||
                (tipDogadjaja == GOLPENALFKDEDINJE) ||
                (tipDogadjaja == ZUTIKARTONFKDEDINJE) ||
                (tipDogadjaja == DRUGIZUTIFKDEDINJE) ||
                (tipDogadjaja == CRVENIKARTONFKDEDINJE) ||
                (tipDogadjaja == MISSEDPENALFKDEDINJE) ||
                (tipDogadjaja == IZMENAIGRACA);
    }


    public String toString() {
        StringBuffer sb = new StringBuffer(opis[tipDogadjaja]);
        if(isIgracki())
                sb.append("  " + AppHeaderData.getInstance().getUserTeamName());
        return sb.toString();

//        return fileName + "\t" + tipDogadjaja + "\t" + serverTime.toString();
    }

}
