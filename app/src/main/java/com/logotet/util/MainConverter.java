package com.logotet.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;


/**
 * Static metode za razna konvertovanja.
 * Vrmenom ce se prisirivati potrebnim metodama.
 * <BR>
 * Pretpostavka je da sve radi na 1.1.7
 *
 * @author Boban Jankovic
 * @version 1.0
 * @see
 */
public class MainConverter {

    /**
     * String niz sa vrednostima za oznake hexa vrednosti "0" -- "F".
     */
    protected final static String[] hexChars = {"0", "1", "2", "3", "4", "5", "6", "7",
                                                "8", "9", "A", "B", "C", "D", "E", "F"};

    /**
     * Konvertuje int broj u String odredjene duzine
     * <p/>
     * 
     */
    public static String intToString(int broj, int brojCifara) {
        int i;
        long longBroj = (long) broj;
        String izlaz;
        String nule = new String();
        String jednaNula = new String("0");
        for (i = 0; i < brojCifara; i++)
            nule += jednaNula;

        DecimalFormatSymbols decSymbol = new DecimalFormatSymbols();
        decSymbol.setDecimalSeparator('.');
        DecimalFormat decFormat = new DecimalFormat(nule, decSymbol);

        decFormat.setGroupingUsed(false);
        izlaz = decFormat.format(longBroj);
        return izlaz;
    }

    /**
     * Konvertuje long broj u String odredjene duzine
     */
    public static String intToString(long broj, int brojCifara) {
        int i;
        long longBroj = broj;
        String izlaz;
        String nule = new String();
        String jednaNula = new String("0");
        for (i = 0; i < brojCifara; i++)
            nule += jednaNula;

        DecimalFormatSymbols decSymbol = new DecimalFormatSymbols();
        decSymbol.setDecimalSeparator('.');
        DecimalFormat decFormat = new DecimalFormat(nule, decSymbol);

        decFormat.setGroupingUsed(false);
        izlaz = decFormat.format(longBroj);
        return izlaz;
    }

    public static String doubleToString(int region, double broj, int brojDecimalnihMesta) {
        if(region == 0)
            return doubleToStringUS(broj, brojDecimalnihMesta);
        else
            return doubleToString(broj, brojDecimalnihMesta);

    }


    public static String doubleToString(double broj, int brojDecimalnihMesta) {
        double tmp = broj;
        StringBuffer dec = new StringBuffer(".");
        for (int i = 0; i < brojDecimalnihMesta; i++) {
            tmp *= 10;
            dec.append("0");

        }
        StringBuffer fmt = new StringBuffer("#,###,##0");
        if (brojDecimalnihMesta > 0)
            fmt.append(dec);
        DecimalFormatSymbols dfc = new DecimalFormatSymbols(new Locale("de", "DE"));
        DecimalFormat df = new DecimalFormat(fmt.toString(), dfc);
        return df.format(broj);
//        String izlaz;
//        long longBroj = Math.round(tmp);
//        izlaz = intToString(longBroj, 20).trim();
//        if (brojDecimalnihMesta == 0) {
//            return izlaz;
//        }
//        String ceoDeo = izlaz.substring(0, izlaz.length() - brojDecimalnihMesta);
//        String decDeo = izlaz.substring(izlaz.length() - brojDecimalnihMesta);
//        return ceoDeo + "." + decDeo;

    }


      public static String doubleToStringUS(double broj, int brojDecimalnihMesta) {
        double tmp = broj;
        StringBuffer dec = new StringBuffer(".");
        for (int i = 0; i < brojDecimalnihMesta; i++) {
            tmp *= 10;
            dec.append("0");

        }
        StringBuffer fmt = new StringBuffer("#,###,##0");
        if (brojDecimalnihMesta > 0)
            fmt.append(dec);
        DecimalFormatSymbols dfc = new DecimalFormatSymbols(new Locale("us", "US"));
        DecimalFormat df = new DecimalFormat(fmt.toString(), dfc);
        return df.format(broj);
    }

    /**
     * Vraca String koji predstavlja tekuci datum u formatu dd.mm.yyyy
     */
    public static String todayString() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy.");
        Date now = new Date();
        return formatter.format(now);
    }

    /**
     * Vraca String koji predstavlja tekuci datum u formatu MMdd
     */
    public static String todayMMdd() {
        SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
        Date now = new Date();
        return formatter.format(now);
    }

    /**
     * Vraca String koji  predstavlja tekuce vreme u formatu hh:mm:ss<BR>
     * Sati su na bazi 0-23, minuti 0-59, sekunde 0-59
     */
    public static String nowString() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();
        return formatter.format(now);
    }

    /**
     * Vraca String koji  predstavlja tekuce vreme u formatu HHmmss<BR>
     * Sati su na bazi 0-23, minuti 0-59, sekunde 0-59
     */
    public static String nowHHmmss() {
        SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
        Date now = new Date();
        return formatter.format(now);
    }

    /**
     * Vraca String koji  predstavlja tekuce vreme u formatu preciznom hh:mm:ss.SSS<BR>
     * Sati su na bazi 0-23, minuti 0-59, sekunde 0-59, a iza njih hiljaditi delovi sekunde
     */
    public static String nowPrecizno() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
        Date now = new Date();
        return formatter.format(now);
    }

    /**
     * Vraca String koji predstavlja timestamp u citljivom formatu
     */
    public static String getTimeStamp() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd");
        Date now = new Date();
        return formatter.format(now) + "+" + nowPrecizno();
    }

    public static String getFullNowString() {
        return todayString() + " " + nowPrecizno();
    }

    /**
     * Pretvara byte niz u String sa hexa vrednostima<p>
     *
     * @param data Byte niz koji se konvertuje u String
     * @return String
     */
    public static String hexify(byte[] data) {
        if (data == null) return "null";

        StringBuffer out = new StringBuffer(256);
        int n = 0;

        for (int i = 0; i < data.length; i++) {
            out.append(hexChars[(data[i] >> 4) & 0x0f]);
            out.append(hexChars[data[i] & 0x0f]);
        }
        return out.toString();
    }

    public static String hexify(byte data) {
        byte[] niz;
        niz = new byte[1];
        niz[0] = data;
        return hexify(niz);


    }

    /**
     * Parsira String koji zadrzi hexa vrednosti u byte niz.<p>
     *
     * @param byteString String koji sadrzi samo hexa vrednosti.
     * @return byte niz koji sadrzi vrednosti dobijene konverzijom.
     */
    public static byte[] parseHexString(String byteString) {
        byte[] result = new byte[byteString.length() / 2];
        for (int i = 0; i < byteString.length(); i += 2) {
            try {
                String toParse = byteString.substring(i, i + 2);
                result[i / 2] = (byte) Integer.parseInt(toParse, 16);
            } catch (NumberFormatException nfe) {
                result[i / 2] = 0;
            } catch (StringIndexOutOfBoundsException soue) {
//	        	result[i/2] = 0;
            }
        }
        return result;
    }


    /**
     * Gnerise slucajni String <p>
     *
     * @param size duzina zeljenog Stringa.
     * @return String koji se sastoji od malih i velikih slova engleskog alfabeta.
     */

    public static String randomString(int size) {
        if (size < 1)
            size = 1;
        byte[] result = new byte[size];
        int randomSlovo;
        for (int i = 0; i < size; i++) {
            randomSlovo = (int) (Math.random() * 26.0);
            result[i] = (Math.random() < 0.5) ? (byte) (randomSlovo + 65) : (byte) (randomSlovo + 97);
        }
        return new String(result);
    }

    /**
     * pretvara u double String u kome je decimalni zarez,
     *  a mogu da postoje i tacke za razdvajanje hiljadarki
     * <p/>
     * *
     */
    public static double makeDouble(String s) {
        try {
            String tmp = s.trim();
            int tacka = tmp.indexOf(".");
            while (tacka > 0) {
                String ispred = tmp.substring(0, tacka);
                String iza = tmp.substring(tacka + 1);
                tmp = ispred + iza;
                tacka = tmp.indexOf(".");
            }
            StringBuffer sb = new StringBuffer(tmp);
            int zarez = sb.indexOf(",");
            if (zarez > -1)
                sb = sb.replace(zarez, zarez + 1, ".");
            double broj = Double.parseDouble(sb.toString());
            return broj;
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static boolean isNumeric(String string) {
        try {
            int iii = Integer.parseInt(string.trim());
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * vraca String u keme su se svim recima prvi karakter menja na veliko slovo,
     * a ostalo na malo slovo
     */
    public static String capitalize(String s) {
        StringBuffer sb = new StringBuffer();
        StringTokenizer st = new StringTokenizer(s.toLowerCase(), " ");
        while (st.hasMoreTokens()) {
            String tmp = st.nextToken();
            String slovo = String.valueOf(tmp.charAt(0));
            sb.append(slovo.toUpperCase() + tmp.substring(1) + " ");
        }
        return sb.toString().trim();
    }

    public static String substring(String tekst, int len){
        try{
            return tekst.trim().substring(0,len);
        }catch(IndexOutOfBoundsException ee){
            return tekst;
        }
    }

    public static String shortString(String tekst, int len){
        try{
            return tekst.trim().substring(0,len) + "...";
        }catch(IndexOutOfBoundsException ee){
            return tekst;
        }
    }


    public static String[] charArraysToStrings(char[] chars){
        String[] izlaz = new String[chars.length];

        for (int i = 0; i < izlaz.length; i++) {
            char[] tmp = new char[1];
            tmp[0] = chars[i];
            izlaz[i] = new String(tmp);
        }
    return izlaz;
    }

    public static String removeChars(String s, String chars){
        StringBuffer sb = new StringBuffer("");
        StringTokenizer st = new StringTokenizer(s, chars);
        while(st.hasMoreTokens()){
            sb.append(st.nextToken());
        }
        return sb.toString();
    }


    public static void main(String[] args) {
//        String tekst = "680123000008102038010002800004920000010211115538012301233030313030313030303100164544333944393530464137344243433";
//        byte[] a = parseHexString(tekst);
//        System.out.println(tekst);
//        for (int i = 0; i < a.length; i++) {
//            System.out.print(" " + a[i]);
//        }
//        System.out.println("\n" + getTimeStamp());
//
//        for (int i = 0; i < 20; i++) {
//            System.out.println(randomString(i));
//        }

//        String tst = "PETAR \"PETRONIJEVIĆ";
//        System.out.println(capitalize(tst));
//        double ppp = 1250.50;
//        System.out.println("1. \t" + MainConverter.doubleToString(ppp,2));
//        System.out.println("2. \t" + MainConverter.doubleToStringUS(ppp,2));
    System.out.println(MainConverter.removeChars("123/   456-789","-/ "));



    }
}