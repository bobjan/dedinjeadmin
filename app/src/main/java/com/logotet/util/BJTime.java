package com.logotet.util;

import com.logotet.util.exception.LogotetException;

import java.sql.Time;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * kreirano:
 * Date: Oct 9, 2007
 *
 */
public class BJTime {
    private int hour;
    private int minute;
    private int second;

    public BJTime() {
        Calendar cal = Calendar.getInstance();
        hour = cal.get(Calendar.HOUR_OF_DAY);
        minute = cal.get(Calendar.MINUTE);
        second = cal.get(Calendar.SECOND);
    }

    public BJTime(long sekunde) {
        second = (int) (sekunde % 60);
        int tmp = (int) (sekunde / 60);
        minute =tmp % 60;
        hour = tmp / 60;
    }

    public BJTime(int hour, int minute) {
        this(hour, minute, 0);
    }

    public BJTime(int hour, int minute, int second) {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public BJTime(Time sql) {
        hour = 0;
        minute = 0;
        second = 0;
        try {
            String tim = sql.toString();
            StringTokenizer st = new StringTokenizer(tim, ":");
            String sat = st.nextToken();
            hour = Integer.parseInt(sat);
            String min = st.nextToken();
            minute = Integer.parseInt(min);
            String sek = st.nextToken();
            second = Integer.parseInt(sek);
        } catch (Exception e) {
        }
    }

    public BJTime(String str) {
        this(0);
        parseFreeString(str);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getSecond() {
        return second;
    }

    public Time getSQLTime() {
        return Time.valueOf(this.toString());
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (hour < 10)
            sb.append("0");
        sb.append("" + hour + ":");
        if (minute < 10)
            sb.append("0");
        sb.append("" + minute + ":");
        if (second < 10)
            sb.append("0");
        sb.append("" + second);
        return sb.toString();
    }


    private void parseFreeString(String s) {
        String cas, minut, sekunda;
        StringTokenizer st = new StringTokenizer(s, "-:");
        int tokens = st.countTokens();

        hour = 0;
        minute = 0;
        second = 0;

        switch (tokens) {
            case 2:
                cas = st.nextToken();
                minut = st.nextToken();
                try {
                    hour = Integer.parseInt(cas);
                } catch (NumberFormatException nfe) {
                }
                try {
                    minute = Integer.parseInt(minut);
                } catch (NumberFormatException nfe) {
                }
                break;
            case 3:
                cas = st.nextToken();
                minut = st.nextToken();
                sekunda = st.nextToken();
                try {
                    hour = Integer.parseInt(cas);
                } catch (NumberFormatException nfe) {
                }
                try {
                    minute = Integer.parseInt(minut);
                } catch (NumberFormatException nfe) {
                }
                try {
                    second = Integer.parseInt(sekunda);
                } catch (NumberFormatException nfe) {
                }
                break;
            default:
                try {
                    if (s.length() < 2)
                        cas = s;
                    else
                        cas = s.substring(0, 2);
                    hour = Integer.parseInt(cas);
                } catch (StringIndexOutOfBoundsException ae) {
                    break;
                } catch (NumberFormatException nfe) {
                    break;
                }
                try {
                    minut = s.substring(2, 4);
                    minute = Integer.parseInt(minut) % 60;
                } catch (StringIndexOutOfBoundsException ae) {
                    minute = 0;
                    break;
                } catch (NumberFormatException nfe) {
                    minute = 0;
                    break;
                }

                try {
                    sekunda = s.substring(4);
                    second = Integer.parseInt(sekunda) % 60;
                } catch (StringIndexOutOfBoundsException ae) {
                    second = 0;
                    break;
                } catch (NumberFormatException nfe) {
                    second = 0;
                    break;
                }
        }
        hour %= 24;
    }

    public int hashCode() {
        return (int) getSeconds();
    }

    public boolean equals(Object obj) {
        try {
            BJTime vreme = (BJTime) obj;
            if (vreme == this) return true;
            return (getSeconds() == vreme.getSeconds());
        } catch (ClassCastException cce) {
            return false;
        }
    }

    public long getSeconds() {
        return hour * 60 * 60 + minute * 60 + second;
    }

    public BJTime getTimeFrom(BJTime vreme) throws LogotetException{
         long sekunde = vreme.getSeconds();
        long thisec = getSeconds();
        if(getSeconds() < sekunde)
            throw new LogotetException("BJTime getTimeFrom() - reversed(?) BJTime values");
        long razlika = getSeconds() - sekunde;
        return new BJTime(razlika);
    }

    public static void main(String[] args) throws LogotetException {
        BJTime vreme = new BJTime("06:20:00");
        BJTime sad = new BJTime();
        String ss = sad.getTimeFrom(vreme).toString();
        System.out.println("razlika "  + ss);
        Time t = vreme.getSQLTime();
        System.out.println(t.toString());
    }
}
