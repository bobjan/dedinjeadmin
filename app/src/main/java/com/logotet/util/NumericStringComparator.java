package com.logotet.util;

import java.util.Calendar;
import java.util.Comparator;

/**
 * Comparator koji se zasniva na poredjenju stringova  pod pretpostavkom da
 * su stringovi numerici i objekti koji se porede imaju metodu getNumericString();
 */
public class NumericStringComparator implements Comparator {
    private int smer;	// ako je negativan - desc, pozitivan - asc

    public NumericStringComparator(int ascdesc) {
        smer = ascdesc;
    }

    public NumericStringComparator() {
        this(1);
    }

    public int compare(Object obj1, Object obj2) {
        NumericStringComparable dat1 = (NumericStringComparable) obj1;
        NumericStringComparable dat2 = (NumericStringComparable) obj2;

        int tmp1 = dat1.getNumericString();
        int tmp2 = dat2.getNumericString();

        return (tmp1 -tmp2) * smer;
    }

    public boolean equals(Object obj) {
        return false;
    }
}