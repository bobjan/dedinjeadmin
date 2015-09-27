package com.logotet.dedinjeadmin.model;

import com.logotet.util.AzbukaConvertor;
import com.logotet.util.BJTime;
import com.logotet.util.NumericStringComparable;
import com.logotet.util.TimeComparable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by logotet on 8/26/15.
 */
public class Dogadjaj implements TimeComparable {
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

    public static final String[] opis = {"Почетак утакмице", "Полувреме", "Почетак 2. полувремена", "Крај утакмице", "Корекција минута",
            "Гол", "Гол", "Гол из пенала", "Гол из пенала", "Промашен пенал", "Промашен пенал",
            "Жути картон", "Жути картон", "Други жути картон", "Други жути картон", "Црвени картон", "Црвени картон", "Коментар", "Измена играча"};


    private int tipDogadjaja;
    private String fileName;
    private String stringServerTime;


    private int minut;       // minut iz http requesta NE MENJATI !!!
    private int playerId;
    private int playerInId;
    private int playerOutId;
    private String komentar;

    private BJTime serverTime;


    private BJTime clientTime;

    private int[] rezultat;  // rezultat nakon dogadjaja 0 - dedinje 1 - protivnik

    private boolean toBeCreated;

    private int minutIgre;

    private  static AzbukaConvertor azbukaConvertor = null;

    public Dogadjaj() {
        toBeCreated = true;
        rezultat = new int[2];
        komentar = "";

    }

    public Dogadjaj(String file, String serverVreme, int tipDogadjaja) {
        toBeCreated = false;
        rezultat = new int[2];
        this.tipDogadjaja = tipDogadjaja;
        this.fileName = file;
        stringServerTime = serverVreme;
        createServerTime();

    }
    private void createServerTime(){
        int tmpTime = Integer.parseInt(stringServerTime.trim());
        int sec = tmpTime % 100;
        tmpTime /= 100;
        int min = tmpTime % 100;
        int hour = tmpTime / 100;
        serverTime = new BJTime(hour, min, sec);
    }

    public void setTipDogadjaja(int tipDogadjaja) {
        if (toBeCreated)
            this.tipDogadjaja = tipDogadjaja;
    }

    public int getTipDogadjaja() {
        return tipDogadjaja;
    }

    public int getMinut() {
        return minut;
    }

    public String getKomentar() {
        if(azbukaConvertor == null)
            azbukaConvertor = new AzbukaConvertor();
        if(azbukaConvertor == null)
            return komentar;
        return azbukaConvertor.latinToCyrilic(komentar);
    }

    public String getFileName() {
        return fileName;
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

    public int getPlayerId() {
        return playerId;
    }

    public int getPlayerInId() {
        return playerInId;
    }

    public int getPlayerOutId() {
        return playerOutId;
    }


    public int[] getRezultat() {
        return rezultat;
    }

    public void setRezultat(int[] rez) {
        rezultat[0] = rez[0];
        rezultat[1] = rez[1];
    }

    public int getMinutIgre() {
        return minutIgre;
    }

    public void setMinutIgre(int minutIgre) {
        this.minutIgre = minutIgre;
    }

    public String getPlayerName() {
        try {
            return BazaIgraca.getInstance().getIgrac(playerId).getNaziv();
        } catch (Exception npe) {
            return "***";
        }
    }

    public String getPlayerInName() {
        try {
            return BazaIgraca.getInstance().getIgrac(playerInId).getNaziv();
        } catch (Exception npe) {
            return "***";
        }
    }

    public String getPlayerOutName() {
        try {
            return BazaIgraca.getInstance().getIgrac(playerOutId).getNaziv();
        } catch (Exception npe) {
            return "***";
        }
    }

    public BJTime getServerTime() {
        return serverTime;
    }

    public BJTime getClientTime() {
        return clientTime;
    }

    public void setClientTime(BJTime clientTime) {
        this.clientTime = clientTime;
    }

    public boolean isVremenski() {
        return (tipDogadjaja == STARTDRUGOPOLUVREME) ||
                (tipDogadjaja == HALFTIME) ||
                (tipDogadjaja == STARTUTAKMICE) ||
                (tipDogadjaja == FINALTIME) ||
                (tipDogadjaja == TACANMINUT);
    }

    public boolean isKomentar() {
        return (tipDogadjaja == KOMENTAR);
    }

/**
 * da bi se trazio unos igraca
 * */
    public boolean isIgracki() {
        return (tipDogadjaja == GOLFKDEDINJE) ||
                (tipDogadjaja == GOLPENALFKDEDINJE) ||
                (tipDogadjaja == ZUTIKARTONFKDEDINJE) ||
                (tipDogadjaja == DRUGIZUTIFKDEDINJE) ||
                (tipDogadjaja == CRVENIKARTONFKDEDINJE) ||
                (tipDogadjaja == MISSEDPENALFKDEDINJE) ||
                (tipDogadjaja == IZMENAIGRACA);
    }

    public boolean isGoalDedinje() {
        return ((tipDogadjaja == GOLFKDEDINJE) || (tipDogadjaja == GOLPENALFKDEDINJE));
    }

    public boolean isGoalProtivnik() {
        return ((tipDogadjaja == GOLPROTIVNIK) || (tipDogadjaja == GOLPENALPROTIVNIK));
    }

    public boolean isZutiKarton(){
        return ((tipDogadjaja == ZUTIKARTONFKDEDINJE) ||
                (tipDogadjaja == ZUTIKARTONPROTIVNIK));
    }
    public boolean isDrugiZuti(){
        return ((tipDogadjaja == DRUGIZUTIFKDEDINJE) ||
                (tipDogadjaja == DRUGIZUTIPROTIVNIK));
    }
    public boolean isCrveniKarton(){
        return ((tipDogadjaja == CRVENIKARTONFKDEDINJE) ||
                (tipDogadjaja == CRVENIKARTONPROTIVNIK));
    }

    public boolean isMissedPenalty(){
        return ((tipDogadjaja == MISSEDPENALFKDEDINJE) ||
                (tipDogadjaja == MISSEDPENALPROTIVNIK));
    }

    public boolean isGoal(){
        return isGoalDedinje() || isGoalProtivnik();
    }
    public boolean isPenaltyGoal(){
        return ((tipDogadjaja == GOLPENALFKDEDINJE) ||
                (tipDogadjaja == GOLPENALPROTIVNIK));
    }

    public boolean isIzmena(){
        return (tipDogadjaja == IZMENAIGRACA);
    }


    public boolean isForDedinje(){
        return ((tipDogadjaja == GOLFKDEDINJE) ||
                (tipDogadjaja == GOLPENALFKDEDINJE) ||
                (tipDogadjaja == MISSEDPENALFKDEDINJE) ||
                (tipDogadjaja == ZUTIKARTONFKDEDINJE) ||
                (tipDogadjaja == DRUGIZUTIFKDEDINJE) ||
                (tipDogadjaja == CRVENIKARTONFKDEDINJE));
    }


    public boolean isForProtivnik(){
        return ((tipDogadjaja == GOLPROTIVNIK) ||
                (tipDogadjaja == GOLPENALPROTIVNIK) ||
                (tipDogadjaja == MISSEDPENALPROTIVNIK) ||
                (tipDogadjaja == ZUTIKARTONPROTIVNIK) ||
                (tipDogadjaja == DRUGIZUTIPROTIVNIK) ||
                (tipDogadjaja == CRVENIKARTONPROTIVNIK));
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(opis[tipDogadjaja]);
        sb.append(" ");
        if (!isIgracki()) {
            if(isForDedinje())
            sb.append("  " + AppHeaderData.getInstance().getUserTeamName());
            else if(isKomentar())
                sb.append(getKomentar());
            else
                sb.append("  **** ");
        }else
            sb.append(getIgrackiTekst());
        return sb.toString();

//        return fileName + "\t" + tipDogadjaja + "\t" + serverTime.toString();
    }

    public String getIgrackiTekst() {
        if (isIgracki())
            if (tipDogadjaja == IZMENAIGRACA) {
                return getPlayerInName() + " <- -> " + getPlayerOutName();
            } else {
                return getPlayerName();
            }
        return "";
    }

    public String getCreationHttpParams() {
        if (!toBeCreated)
            return "";
        StringBuffer sb = new StringBuffer("?eventid=" + tipDogadjaja);
        if (minut >= 0)
            sb.append("&minut=" + minut);
        if (playerId != 0)
            sb.append("&player=" + playerId);
        if (playerInId != 0)
            sb.append("&playerin=" + playerInId);
        if (playerOutId != 0)
            sb.append("&playerout=" + playerOutId);
        try {
            if (tipDogadjaja == KOMENTAR)
                sb.append("&komentar=" + URLEncoder.encode(komentar, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        }
        return sb.toString();
    }

    public String getRezultat(boolean userTeamDomacin) {
        if (userTeamDomacin) {
            return rezultat[0] + ":" + rezultat[1];
        } else {
            return rezultat[1] + ":" + rezultat[0];
        }
    }


    /**
     * vraca ono tacno vreme kada se dogadjaj desio. serversko vreme umanjeno za minute korkcije
     * **/
    public BJTime getBJTime() {
        return new BJTime(serverTime.getSeconds() - minut * 60);
    }
}
