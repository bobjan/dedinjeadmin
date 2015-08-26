package com.logotet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * Klasa koja treba da obezbedi bolju funkcionalnost za rad sa datumima i da uvek bude samo datum
 * bez casova, minuta i sekundi
 */
public class BJDatum extends GregorianCalendar implements DateComparable {
    private static final String[] imeMeseca = {"januar", "februar", "mart", "april", "maj",
                                               "jun", "jul", "avgust", "septembar", "oktobar",
                                               "novembar", "decembar"};
    private static final String[] cirilicnoImeMeseca = {"јануар", "фебруар", "март", "април", "мај",
                                                        "јун", "јул", "август", "септембар", "октобар",
                                                        "новембар", "децембар"};

    private static final String[] redniBroj = {"први", "други", "трећи", "четврти", "пети",
                                               "шести", "седми", "осми", "девети", "десети",
                                               "једанаести", "дванаести", "тринаести", "четрнаести", "петнаести",
                                               "шеснаести", "седамнаести", "осамнаести", "деветнаести", "двадесети",
                                               "двадесетпрви", "двадесетдруги", "двадесеттрећи", "двадесетчетврти", "двадесетпети",
                                               "двадесетшести", "двадесетседми", "двадесетосми", "двадесетдевети", "тридесети",
                                               "тридесетпрви"};

    private static final String[] nazivDana = {"понедељак", "уторак", "среда", "четвртак", "петак", "субота", "недеља"};


    private long datumLong;     // broj dana a ne milisekundi !!!
    private static long jedanDan = 24 * 60 * 60 * 1000;

    public BJDatum(Calendar cal) {
        super();
        this.setTimeInMillis(cal.getTimeInMillis());
        setHighNoon();
    }

    public BJDatum() {
        super();
        setHighNoon();
    }


    /**
     * @param y - godina npr. 2004
     * @param m - mesec (1-12)
     * @param d - dan (1-...31)
     *          *
     */
    public BJDatum(int y, int m, int d) {
        super(y, m - 1, d);
        setHighNoon();
    }

    public BJDatum(Date datum) {
        this();
        if (datum == null)
            datum = new Date(0L);
        this.setTimeInMillis(datum.getTime());
        this.computeFields();
        setHighNoon();
    }

    public BJDatum(long broj) {
        this();
        this.setTimeInMillis(broj * jedanDan);
        this.computeFields();
        setHighNoon();
    }

    public BJDatum(int y, int doy) {
        this();
        set(Calendar.YEAR, y);
        set(Calendar.DAY_OF_YEAR, doy);
    }


    /**
     * @param datum - String u jednom od sledecih formata:
     *              <br>"yyyy.MM.dd"
     *              <br>"dd-MM-yyyy"
     *              <br>"yyyy-MM-dd"
     *              <br>"dd/MM/yyyy"
     * @throws ParseException *
     */

    public BJDatum(String datum) throws ParseException {
        this();
        if (datum == null)
            return;
        if (datum.length() == 0)
            return;
        boolean parsed = false;
        SimpleDateFormat[] format = new SimpleDateFormat[5];
        format[0] = new SimpleDateFormat("yyyy.MM.dd");
        format[1] = new SimpleDateFormat("dd-MM-yyyy");
        format[2] = new SimpleDateFormat("yyyy-MM-dd");
        format[3] = new SimpleDateFormat("dd/MM/yyyy");
        format[4] = new SimpleDateFormat("yyyyMMdd");

        Date dat = new Date();
        for (int i = 0; i < format.length; i++) {
            try {
                dat = format[i].parse(datum);
                parsed = true;
                break;
            } catch (ParseException pe) {
            }
        }
        if (!parsed)
            throw new ParseException("BJDAtum-Neispravan datum", 0);
        this.setTime(dat);
        setHighNoon();
    }


    public Date getDate() {
        return this.getTime();
    }

    public Date getDatum() {
        return this.getTime();
    }

    public String getStringValue() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd.HH:mm:ss.SSS");
        return formatter.format(getDate());
    }

    public String getStringDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(getDate());

    }

    public String getExcelDate(String delim) {
        String del;
        try {
            del = delim.trim();
        } catch (NullPointerException npe) {
            del = ",";
        }
        return "=DATE(" + get(Calendar.YEAR) + del + (get(Calendar.MONTH) + 1) + del + get(Calendar.DAY_OF_MONTH) + ")";
    }

    public String getRegionalString() {
        return getRegionalString(0);
    }

    public String getRegionalString(int i) {
        if (i == 0) {// English-US regional setting
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            return formatter.format(getDate());
        } else {//Serbian regional seting
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
            return formatter.format(getDate());
        }
    }

    public static String getNazivDana(int idx) {
        return nazivDana[idx];
    }

    /**
     * index 1 - 12;
     */
    public static String getImeMeseca(int ind) {
        try {
            return imeMeseca[ind - 1];

        } catch (ArrayIndexOutOfBoundsException aeb) {
            return imeMeseca[0];
        }
    }

    public int getIndexMeseca() {
        return get(Calendar.MONTH) + 1;
    }

    public static String getNamedMonthYear(int mesec, int godina) {
        try {
            return imeMeseca[mesec - 1] + " " + godina + ".";
        } catch (Exception e) {
            return "." + godina + ".";
        }
    }

    public String getNamedMonthYear() {
        int mm = this.get(Calendar.MONTH);
        int yy = this.get(Calendar.YEAR);
        return imeMeseca[mm] + " " + yy + ".";
    }

    public String getLatinString() {
        int mm = this.get(Calendar.MONTH);
        int yy = this.get(Calendar.YEAR);
        return this.get(Calendar.DAY_OF_MONTH) + "-" + imeMeseca[mm] + "-" + yy + ".";
    }

    public String getCirilString() {
        int didx = this.get(Calendar.DAY_OF_MONTH) - 1;
        int mm = this.get(Calendar.MONTH);
        int yy = this.get(Calendar.YEAR);
        return this.get(Calendar.DAY_OF_MONTH) + "(" + redniBroj[didx] + ")" + "-" + cirilicnoImeMeseca[mm] + "-" + yy + ".";
    }

    /**
     * @return String datum u formatu "yyyy-MM-dd"
     */
    public String getSQLDateString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(getDate());

    }

    public java.sql.Date getSQLDate() {
        java.sql.Date d = new java.sql.Date(this.getTimeInMillis());
        return d;
    }

    public void setDatum(int year, int month, int day) {
        this.set(year, month, day);
        this.computeTime();
        setHighNoon();
    }

    public long getLong() {
        datumLong = this.getTimeInMillis() / jedanDan;
        return datumLong;
    }

    public int getDayOfWeek() {
        return this.get(Calendar.DAY_OF_WEEK);
    }

    public BJDatum afterDays(int days) {
        return (new BJDatum(this.getLong() + days));
    }

    /**
     * */
    public int daysDifference(BJDatum datum) {
        long razlika = 0;
        razlika = this.getLong() - datum.getLong();
        return (int) razlika;
    }

    public int getGodina() {
        return get(Calendar.YEAR);
    }

    public int getMesec() {
        return get(Calendar.MONTH);
    }


    /**
     * anulira sve tako da bude tacno u podne
     */
    public void setHighNoon() {
        this.set(Calendar.HOUR_OF_DAY, 12);
        this.set(Calendar.MINUTE, 0);
        this.set(Calendar.SECOND, 0);
        this.set(Calendar.MILLISECOND, 0);
        datumLong = this.getTimeInMillis() / jedanDan;
    }


    public void parseFreeString(String s) {
        int day, month, year;
        String dan, mes, god;
        BJDatum danas = new BJDatum();
        StringTokenizer st = new StringTokenizer(s, "-./");
        int tokens = st.countTokens();

        day = danas.get(Calendar.DAY_OF_MONTH);
        month = danas.getMesec() + 1;
        year = danas.getGodina();

        switch (tokens) {
            case 2:
                dan = st.nextToken();
                mes = st.nextToken();
                year = danas.getGodina();
                try {
                    day = Integer.parseInt(dan);
                } catch (NumberFormatException nfe) {
                }
                try {
                    month = Integer.parseInt(mes);
                } catch (NumberFormatException nfe) {
                }
                setDatum(year, month - 1, day);
                break;
            case 3:
                dan = st.nextToken();
                mes = st.nextToken();
                god = st.nextToken();
                try {
//                    set(Calendar.DAY_OF_MONTH, Integer.parseInt(dan));
                    day = Integer.parseInt(dan);
                } catch (NumberFormatException nfe) {
                }
                try {
//                    set(Calendar.DAY_OF_MONTH, Integer.parseInt(dan));
//                    day = Integer.parseInt(dan);
                    month = Integer.parseInt(mes);
                } catch (NumberFormatException nfe) {
                }
                try {
                    year = Integer.parseInt(god);
                    if (year < 101)
                        year += 2000;
                } catch (NumberFormatException nfe) {
                }
                setDatum(year, month - 1, day);
                break;
            default:
                try {
                    dan = s.substring(0, 2);
                    day = Integer.parseInt(dan);
                } catch (StringIndexOutOfBoundsException ae) {
                    break;
                } catch (NumberFormatException nfe) {
                    break;
                }
                try {
                    mes = s.substring(2, 4);
                    month = Integer.parseInt(mes);
                } catch (StringIndexOutOfBoundsException ae) {
                    month = danas.getMesec() + 1;
                } catch (NumberFormatException nfe) {
                    month = danas.getMesec() + 1;
                }
                try {
                    god = s.substring(4);
                    year = Integer.parseInt(god);
                    if (year < 101)
                        year += 2000;
                } catch (StringIndexOutOfBoundsException ae) {
                    year = danas.getGodina();
                } catch (NumberFormatException nfe) {
                    year = danas.getGodina();
                }
                setDatum(year, month - 1, day);
        }
    }

    public BJDatum getBJDatum() {
        return this;
    }

    public String toString() {
        return getStringDate();
    }

    public int hashCode() {
        return (int) getLong();
    }

    public boolean equals(Object obj) {
        try {
            BJDatum bjd = (BJDatum) obj;
            if (bjd == this)
                return true;
            if(bjd.hashCode() == this.hashCode())
            return true;
//            if (bjd.daysDifference(this) == 0)
//                return true;
            return false;
        } catch (ClassCastException cce) {
            return false;
        }
    }

    public boolean lessThanToday() {
        BJDatum danas = new BJDatum();
        return (daysDifference(danas) < 0);
    }

    public boolean greaterThanToday() {
        BJDatum danas = new BJDatum();
        return (daysDifference(danas) > 0);
    }

    public boolean isToday() {
        BJDatum danas = new BJDatum();
        return (daysDifference(danas) == 0);
    }

    public String getJmbgStart() {
        String tmp = getStringDate();
        return tmp.substring(0, 2) + tmp.substring(3, 5) + tmp.substring(7, 10);
    }

/////////////////////// everything is perfect !!!
    public static void main(String[] args) {
        BJDatum nekiDatum, kroz20, dalekaBuducnost;
//        BJDatum nekiDan = new BJDatum(2008, 1, 2);
        Date abc = null;
        BJDatum nekiDan = null;
        nekiDan = new BJDatum(abc);
        System.out.println("get() " + nekiDan.getLong());

        if (nekiDan.lessThanToday()) {
            System.out.println("Manje od danas " + nekiDan.getStringDate());
        }

        if (nekiDan.greaterThanToday()) {
            System.out.println("Veci  od danas " + nekiDan.getStringDate());

        }


        BJDatum danas = new BJDatum();

        if (danas.isToday()) {
            System.out.println("JESET  danas " + danas.getStringValue());

        }


        nekiDatum = danas.afterDays(-100);
        kroz20 = nekiDatum.afterDays(20);
        dalekaBuducnost = nekiDan.afterDays(365);
        System.out.println("Danas je           " + danas.get(Calendar.YEAR) + " " + danas.get(Calendar.MONTH) + " " + danas.get(Calendar.DATE));

        System.out.println("Ako je this = " + danas.getStringDate() + "   this.daysDifference(" + nekiDan.getStringDate() + ") je " + danas.daysDifference(nekiDan));

        for (int i = 1; i <= 3; i++) {
            nekiDatum = danas.afterDays(i);
            System.out.println("nekiDatum(-100)je     " + nekiDatum.getTime() + " " + nekiDatum.getLong());
            nekiDatum.setDatum(nekiDatum.get(Calendar.YEAR), nekiDatum.get(Calendar.MONTH) + 2 * i, nekiDatum.get(Calendar.DATE) + 4 * i);
            System.out.println("Novi nekiDatum(-100)  " + nekiDatum.getTime() + " " + nekiDatum.getLong());
        }
/*
		System.out.println("Danas je           " + danas.get(Calendar.YEAR) + " " + danas.get(Calendar.MONTH) + " " + danas.get(Calendar.DATE));
		System.out.println("nekiDatum(-100) je " + nekiDatum.get(Calendar.YEAR) + " " + nekiDatum.get(Calendar.MONTH) + " " + nekiDatum.get(Calendar.DATE));
		System.out.println("Kroz 20 je         " + kroz20.get(Calendar.YEAR) + " " + kroz20.get(Calendar.MONTH) + " " + kroz20.get(Calendar.DATE));
		System.out.println("Neki dan je        " + nekiDan.get(Calendar.YEAR) + " " + nekiDan.get(Calendar.MONTH) + " " + nekiDan.get(Calendar.DAY_OF_MONTH));
		System.out.println("Futur   je         " + dalekaBuducnost.get(Calendar.YEAR) + " " + dalekaBuducnost.get(Calendar.MONTH) + " " + dalekaBuducnost.get(Calendar.DATE));

		System.out.println("Danas je           " + danas.getTime());
		System.out.println("Kroz 20 je         " + kroz20.getTime());
		System.out.println("Neki dan je        " + nekiDan.getTime());
		System.out.println("Futur je           " + dalekaBuducnost.getTime());
*/
//		System.out.println("Razlika je         " + danas.daysDifference(nekiDan) + " " + danas.getLong() + " " + nekiDan.getLong());
        System.out.println(danas.getLatinString());
        System.out.println(danas.getCirilString());


        danas.parseFreeString("02");
        System.out.println(danas.getStringDate());
        danas.parseFreeString("02-07");
        System.out.println(danas.getStringDate());
        danas.parseFreeString("0207-1953");
        System.out.println(danas.getStringDate());
        danas.parseFreeString("02/07/1953");
        System.out.println(danas.getStringDate());
        danas.parseFreeString("02-07-1953");
        System.out.println(danas.getStringDate());
        danas.parseFreeString("02071953");
        System.out.println(danas.getStringDate());
        danas.parseFreeString("02.07");
        System.out.println(danas.getStringDate());
        danas.parseFreeString("02.07.");
        System.out.println(danas.getStringDate());
        danas.parseFreeString("02.07.1953");
        System.out.println(danas.getStringDate());
        danas.parseFreeString("02.07.1953.");
        System.out.println(danas.getStringDate());
    }


}