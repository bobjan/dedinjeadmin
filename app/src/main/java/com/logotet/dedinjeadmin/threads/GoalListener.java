package com.logotet.dedinjeadmin.threads;

/**
 * Created by logotet on 9/2/15.
 */
public class GoalListener {
    private static GoalListener goalListener = null;

    public GoalListener getInstance(int d,int p){
        if(goalListener == null)
            goalListener = new GoalListener(d,p);
        return goalListener;
    }

    private int lastGoalsDedinje;
    private int lastGoalsProtivnik;

    private GoalListener(int dedinje, int protivnik) {
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
