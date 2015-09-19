package com.logotet.dedinjeadmin.model;

import java.util.Comparator;

/**
 * Created by logotet on 8/26/15.
 */
public class DogadjajComparator implements Comparator {
    public int compare(Object o1, Object o2) {
        DogadjajComparable d1 = (DogadjajComparable) o1;
        DogadjajComparable d2 = (DogadjajComparable) o2;
        return d1.getMinutIgre() - d2.getMinutIgre();
    }

    public boolean equals(Object obj) {
        return false;
    }
}
