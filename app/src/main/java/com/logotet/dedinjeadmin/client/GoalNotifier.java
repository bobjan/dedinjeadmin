package com.logotet.dedinjeadmin.client;

/**
 * Created by logotet on 9/2/15.
 */
public class GoalNotifier {
    private static GoalNotifier goalNotifier = null;


    /**
     * poziva se prilikom pokretanja aplikacije (onCreate()) i nikad vise vec se kasnij eprilikom redovnih
     * osvezavanja koriste smao metode dedinjeScored() i protivnikScored()
     *
     * */
    public GoalNotifier getInstance(int d,int p){
        if(goalNotifier == null)
            goalNotifier = new GoalNotifier(d,p);
        return goalNotifier;
    }


/**
 * ova metoda je zapravo samo radi koriscenja Songleton instance kansije....
 * */
    public GoalNotifier getInstance(){
        if(goalNotifier == null)
            goalNotifier = new GoalNotifier(0,0);
        return goalNotifier;
    }

    private int lastGoalsDedinje;
    private int lastGoalsProtivnik;

    private GoalNotifier(int dedinje, int protivnik) {
        lastGoalsDedinje = dedinje;
        lastGoalsProtivnik = protivnik;
    }

    /**
     * parametar je broj golova koje je dedinje postiglo u trenutku poziva metode
     * */
    public boolean dedinjeScored(int dedinjeGoals){
        boolean gol = (dedinjeGoals > lastGoalsDedinje);
        lastGoalsDedinje = dedinjeGoals;
        return gol;
    }

    /**
     * parametar je broj golova koje je dedinje postiglo u trenutku poziva metode
     * */
    public boolean protivnikScored(int protivnikGoals){
        boolean gol = (protivnikGoals > lastGoalsProtivnik);
        lastGoalsProtivnik = protivnikGoals;
        return gol;
    }

}
