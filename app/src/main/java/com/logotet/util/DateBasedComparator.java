package com.logotet.util;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Comparator koji se zasniva na poredjenju datuma pod pretpostavkom da
 * objekti koji se porede imaju metodu getDate();
 */
public class DateBasedComparator implements Comparator {
    private int smer;	// ako je negativan - desc, pozitivan - asc

    public DateBasedComparator(int ascdesc) {
        smer = ascdesc;
    }

    public DateBasedComparator() {
        this(1);
    }

    public int compare(Object obj1, Object obj2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        DateComparable dat1 = (DateComparable) obj1;
        DateComparable dat2 = (DateComparable) obj2;
        cal1.setTime(dat1.getBJDatum().getDate());
        cal2.setTime(dat2.getBJDatum().getDate());

        cal1.set(Calendar.HOUR, 0);	// ovo izgleda nema efekta
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MILLISECOND, 0);
        cal2.set(Calendar.HOUR, 0);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);
        cal2.set(Calendar.MILLISECOND, 0);

        if (cal1.after(cal2))
            return smer;
        if (cal1.before(cal2))
            return smer * (-1);
        return 0;
    }

    public boolean equals(Object obj) {
        return false;
    }
}