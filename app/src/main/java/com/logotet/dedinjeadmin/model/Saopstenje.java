package com.logotet.dedinjeadmin.model;

import com.logotet.util.BJDatum;
import com.logotet.util.DateComparable;

import java.text.ParseException;

/**
 * Created by logotet on 8/28/15.
 */
public class Saopstenje implements DateComparable {
    private BJDatum datum;
    private String naslov;
    private String fullText;
    private String imageFileName;

    public Saopstenje(String datum) {
        try {
            this.datum = new BJDatum(datum.trim());
        } catch (ParseException e) {
            this.datum = new BJDatum();
        }
        this.naslov = "";
        this.fullText = "";
        this.imageFileName = "";
    }

    public Saopstenje(String datum, String naslov, String fullText, String imgFileName) {
        this(datum);
        this.naslov = naslov;
        this.fullText = fullText;
        this.imageFileName = imgFileName;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public BJDatum getDatum() {
        return datum;
    }

    public String getNaslov() {
        return naslov;
    }

    public String getFullText() {
        return fullText;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    @Override
    public String toString() {
        return datum.toString() + "\t" + naslov + "\t" + fullText + "\t" + imageFileName;
    }

    public BJDatum getBJDatum() {
        return datum;
    }
}
