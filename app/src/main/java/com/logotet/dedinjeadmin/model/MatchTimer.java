package com.logotet.dedinjeadmin.model;

import com.logotet.util.BJTime;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by boban on 9/22/15.
 */
public class MatchTimer {
    public static final int NOTSTARTED = 0;
    public static final int FIRSTHALFSTARTED = 1;
    public static final int FIRSTHALFENDED = 2;
    public static final int SECONDHALFSTARTED = 3;
    public static final int MATCHENDED = 4;


    private int currentTimeSlice;

    private BJTime lastTacanMinut; // serversko vreme
    private int zadnjiTacanMinut; // poslat parametar;
    // mozda preracunavati korektor kao diff i samo tako korigovati ??

    ArrayList<Dogadjaj> sviDogadjaji;

    private BJTime[] granicnoVreme;// 0 - pocetak ...3 - kraj ... kao tipDogadjaj

    ArrayList<Dogadjaj> korektor;

    public MatchTimer() {
        granicnoVreme = new BJTime[4];
        korektor = new ArrayList<Dogadjaj>();
    }

    public void recalculateAll(ArrayList<Dogadjaj> sviDogadjaji) {
        this.sviDogadjaji = sviDogadjaji;
        refreshTime();
    }

    /**
     * podrazumeva se da je sve sortirano prema serverskom vremenu
     * */
    private void refreshTime() {
        for (int i = 0; i < granicnoVreme.length; i++) {
            granicnoVreme[i] = null;
        }
        korektor.clear();
        lastTacanMinut = null;
        zadnjiTacanMinut = 0;
        currentTimeSlice = NOTSTARTED;

        Iterator<Dogadjaj> iter = sviDogadjaji.iterator();
        while (iter.hasNext()) {
            Dogadjaj dogadjaj = iter.next();

            switch (dogadjaj.getTipDogadjaja()) {
                case Dogadjaj.STARTUTAKMICE:
                    if (currentTimeSlice <= FIRSTHALFSTARTED) {
                        currentTimeSlice = FIRSTHALFSTARTED;
                        granicnoVreme[0] = dogadjaj.getBJTime();
                    }
                    break;
                case Dogadjaj.HALFTIME:
                    if (currentTimeSlice <= FIRSTHALFENDED) {
                        currentTimeSlice = FIRSTHALFENDED;
                        granicnoVreme[1] = dogadjaj.getBJTime();
                        if(granicnoVreme[0] == null){
                            granicnoVreme[0] = new BJTime(granicnoVreme[1].getSeconds() - 45*60);
                        }
                    }
                    break;
                case Dogadjaj.STARTDRUGOPOLUVREME:
                    if (currentTimeSlice <= SECONDHALFSTARTED) {
                        currentTimeSlice = SECONDHALFSTARTED;
                        granicnoVreme[2] = dogadjaj.getBJTime();
                        if(granicnoVreme[1] == null){
                            granicnoVreme[1] = new BJTime(granicnoVreme[2].getSeconds() - 15*60);
                        }
                        if(granicnoVreme[0] == null){
                            granicnoVreme[0] = new BJTime(granicnoVreme[1].getSeconds() - 45*60);
                        }
                    }
                    break;
                case Dogadjaj.FINALTIME:
                    currentTimeSlice = MATCHENDED;
                    granicnoVreme[3] = dogadjaj.getBJTime();
                    if(granicnoVreme[2] == null){
                        granicnoVreme[2] = new BJTime(granicnoVreme[3].getSeconds() - 45*60);
                    }
                    if(granicnoVreme[1] == null){
                        granicnoVreme[1] = new BJTime(granicnoVreme[2].getSeconds() - 15*60);
                    }
                    if(granicnoVreme[0] == null){
                        granicnoVreme[0] = new BJTime(granicnoVreme[1].getSeconds() - 45*60);
                    }
                    break;
                case Dogadjaj.TACANMINUT:
                    if (lastTacanMinut == null)
                        lastTacanMinut = dogadjaj.getBJTime();
                    if (lastTacanMinut.getSeconds() <= dogadjaj.getBJTime().getSeconds()) {
                        lastTacanMinut = dogadjaj.getBJTime();
                        zadnjiTacanMinut = dogadjaj.getMinut();
                    }
                    korektor.add(dogadjaj);
                    break;
                default:        // nije vremenski dogadjaj
                    obradiDogadjaj(dogadjaj);
                    break;
            }
        }
    }

    private void obradiDogadjaj(Dogadjaj dogadjaj) {
        int diffSeconds = Servertime.getInstance().getDiff();


        if (currentTimeSlice == NOTSTARTED) {
            if (dogadjaj.isKomentar()) {
                dogadjaj.setMinutIgre(-1);
                dogadjaj.setClientTime(new BJTime(dogadjaj.getBJTime().getSeconds() - diffSeconds));
                return;
            }
            currentTimeSlice = FIRSTHALFSTARTED;
            granicnoVreme[0] = new BJTime(dogadjaj.getBJTime().getSeconds() - 60);
            dogadjaj.setMinutIgre(1);
            return;
        }

        if (currentTimeSlice == FIRSTHALFSTARTED) {
            int tmp = (int) (dogadjaj.getBJTime().getSeconds() - granicnoVreme[0].getSeconds());
            tmp /= 60; // minuti od pocetka
            if (tmp <= 0)
                tmp = 1;
            if (tmp < 50) {
                if (tmp > 45) {
                    dogadjaj.setMinutIgre(45);
                    return;
                }
                dogadjaj.setMinutIgre(tmp);
                return;
            }
            currentTimeSlice = FIRSTHALFENDED;
            granicnoVreme[1] = new BJTime(dogadjaj.getBJTime().getSeconds() - 60);  // pre jedan minut se zavrsilo poluvreme
        }

        if (currentTimeSlice == FIRSTHALFENDED) {
            if (dogadjaj.isKomentar()) {
                dogadjaj.setMinutIgre(45);
                return;
            }

            currentTimeSlice = SECONDHALFSTARTED;
            granicnoVreme[2] = new BJTime(dogadjaj.getBJTime().getSeconds() - 60); // prejedan minut pocelo drugo poluvreme
            dogadjaj.setMinutIgre(46);
            return;
        }
        if (currentTimeSlice == SECONDHALFSTARTED) {
            int tmp = (int) (dogadjaj.getBJTime().getSeconds() - granicnoVreme[2].getSeconds());
            tmp /= 60; // minuti od pocetka
            if (tmp <= 0)
                tmp = 0;
            if (tmp > 45) {
                dogadjaj.setMinutIgre(90);
                return;
            }
            dogadjaj.setMinutIgre(tmp + 45);
            return;
        }

        if (currentTimeSlice == MATCHENDED) {
            if (dogadjaj.isKomentar()) {
                dogadjaj.setMinutIgre(90);
                return;
            } else {
                dogadjaj.setMinutIgre(90);
                dogadjaj.setTipDogadjaja(Dogadjaj.KOMENTAR);
                dogadjaj.setKomentar("*********************");
            }
        }
    }

    public int getCurrentMinut() {
        int diffSeconds = Servertime.getInstance().getDiff();
        BJTime now = new BJTime();
        BJTime serverNow = new BJTime(now.getSeconds() + diffSeconds);

        switch (currentTimeSlice) {
            case FIRSTHALFSTARTED:
                return (int) ((serverNow.getSeconds() - granicnoVreme[0].getSeconds()) / 60);
            case SECONDHALFSTARTED:
                return (int) ((serverNow.getSeconds() - granicnoVreme[2].getSeconds()) / 60);
            case FIRSTHALFENDED:
                return 45;
            case MATCHENDED:
                return 90;
            default:
                return 0;
        }
    }

    public boolean isFinished(){
        return currentTimeSlice == MATCHENDED;
    }
    public boolean isFirstHalfFinished(){
        return currentTimeSlice == FIRSTHALFSTARTED;
    }
    public boolean isStarted(){
        return (currentTimeSlice != NOTSTARTED);
    }
}
