package com.logotet.util;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Comparator koji se zasniva na poredjenju datuma pod pretpostavkom da
 * objekti koji se porede imaju metodu getDate();
 */
public class TimeBasedComparator implements Comparator {
    private int smer;	// ako je negativan - desc, pozitivan - asc

    public TimeBasedComparator(int ascdesc) {
        smer = ascdesc;
    }

    public TimeBasedComparator() {
        this(1);
    }

    public int compare(Object obj1, Object obj2) {
        TimeComparable dat1 = (TimeComparable) obj1;
        TimeComparable dat2 = (TimeComparable) obj2;

        return (int) (dat1.getBJTime().getSeconds() - dat2.getBJTime().getSeconds());
    }

    public boolean equals(Object obj) {
        return false;
    }
}