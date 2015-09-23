package com.logotet.util;

import java.util.HashMap;
import java.util.Map;


/**
 * 
 */
public class AzbukaConvertor {
    private char[] cyrilic = new char[]{
        '\u0410', '\u0430', //A
        '\u0411', '\u0431', //B
        '\u0412', '\u0432', //V
        '\u0413', '\u0433', //G
        '\u0414', '\u0434', //D
        '\u0402', '\u0452', //Dj
        '\u0415', '\u0435', //E
        '\u0416', '\u0436', //zh
        '\u0417', '\u0437', //Z
        '\u0418', '\u0438', //I
        '\u0408', '\u0458', //J
        '\u041A', '\u043A', //K
        '\u041B', '\u043B', //L
        '\u0409', '\u0459', //Lj
        '\u041C', '\u043C', //M
        '\u041D', '\u043D', //N
        '\u040A', '\u045A', //Nj
        '\u041E', '\u043E', //O
        '\u041F', '\u043F', //P
        '\u0420', '\u0440', //R
        '\u0421', '\u0441', //S
        '\u0422', '\u0442', //T
        '\u040B', '\u045B', //meko cj
        '\u0423', '\u0443', //U
        '\u0424', '\u0444', //F
        '\u0425', '\u0445', //H
        '\u0426', '\u0446', //C
        '\u0427', '\u0447', //tvrdo CH
        '\u040F', '\u045F', //Dzh
        '\u0428', '\u0448',	//SH
                            // za latinicni dodatak

            '\u0428', '\u0448',	//SH unosi se kao ss
            '\u040B', '\u045B', //meko cj
            '\u0427', '\u0447', //tvrdo CH
            '\u0416', '\u0436' //zh unosi se kao zz


    };

    private String[] latin = new String[]{
        "A", "a",
        "B", "b",
        "V", "v",
        "G", "g",
        "D", "d",
        "\u0110", "\u0111",
        "E", "e",
        "\u017D", "\u017E",             // ovo otezava dj, ali je to dole u kodu dodato
        "Z", "z",
        "I", "i",
        "J", "j",
        "K", "k",
        "L", "l",
        "Lj", "lj",
        "M", "m",
        "N", "n",
        "Nj", "nj",
        "O", "o",
        "P", "p",
        "R", "r",
        "S", "s",
        "T", "t",
        "\u0106", "\u0107",
        "U", "u",
        "F", "f",
        "H", "h",
        "C", "c",
        "\u010C", "\u010D",
        "D\u017E", "d\u017E",
        "\u0160", "\u0161", // sada ide dodatak
            "Ss","ss",
            "Cj","cj",
            "Ch","ch",
            "Zz","zz"    };

    // @todo  razmisliti da se ubaci i mogusnost da se u cirilicu prebacuju ch, cj, ss, zz


    private Map cyrMapping = new HashMap();
    private Map latMapping = new HashMap();

    public AzbukaConvertor() {

        for (int i = 0; i < 60; i++) {
            cyrMapping.put(new Character(cyrilic[i]), latin[i]);
//            latMapping.put(latin[i], new Character(cyrilic[i]));
        }

        // razdvojeno da bi bilo vise latinicnih kombinaciaj u jednu cirilicnu
        for (int i = 0; i < latin.length; i++) {
//            cyrMapping.put(new Character(cyrilic[i]), latin[i]);
            latMapping.put(latin[i], new Character(cyrilic[i]));
        }


//			//Dj moze i ovako da se mapira
//			latMapping.put("Dj", new Character('\u0402'));
//			latMapping.put("dj", new Character('\u0452'));

        String cyrilicRulesAsc = "";
        String cyrilicRulesDesc = "";
        for (int i = 0; i < 60; i = i + 2) {
            cyrilicRulesAsc = cyrilicRulesAsc + "<" + cyrilic[i] +
                    "=" + cyrilic[i + 1];
            cyrilicRulesDesc = cyrilicRulesDesc + "<" +
                    cyrilic[59 - i] + "=" + cyrilic[58 - i];
        }
    }

    public String cyrilicToLatin(String cyrilicText) {
        StringBuffer cyrBuffer = new StringBuffer(cyrilicText);
        StringBuffer latinBuffer = new StringBuffer();
        for (int i = 0; i < cyrBuffer.length(); i++) {
            char c = cyrBuffer.charAt(i);
            Character character = new Character(c);
            if (cyrMapping.containsKey(character)) {
                latinBuffer.append(cyrMapping.get(character));
            } else {
                latinBuffer.append(c);
            }
        }
        return latinBuffer.toString();
    }

    public String latinToCyrilic(String latinText) {
        StringBuffer latBuffer = new StringBuffer(latinText);
        StringBuffer cyrBuffer = new StringBuffer();

        for (int i = 0; i < latBuffer.length(); i++) {
            String s = latBuffer.substring(i, i + 1);
            if (i < latBuffer.length() - 1) {
                char c = latBuffer.charAt(i + 1);
                if (((s.equals("L") || s.equals("l")
                        || s.equals("N") || s.equals("n"))
                        && (c == 'J' || c == 'j'))) {
                    s = s + 'j';
                    i++;
                } else if ((s.equals("D") || s.equals("d"))
                        && (c == '\u017D' || c == '\u017E')) {
                    s = s + '\u017E';
                    i++;

                }else if ((s.equals("D") )      // ov je ubaceno da se dj konvertuje uvek u ђ,
                        && (c == 'J' || c == 'j')) {
                    s = "\u0110";
                    i++;
                }else if (( s.equals("d"))
                        && (c == 'J' || c == 'j')) {
                    s = "\u0111";
                    i++;
                }else if ((s.equals("S") || s.equals("s"))
                        && (c == 'S' || c == 's')) {
                    s = s + 's';
                    i++;
                }else if ((s.equals("C") || s.equals("c"))
                        && (c == 'H' || c == 'h')) {
                    s = s + 'h';
                    i++;
                }else if ((s.equals("C") || s.equals("c"))
                        && (c == 'J' || c == 'j')) {
                    s = s + 'j';
                    i++;
                }else if ((s.equals("Z") || s.equals("z"))
                        && (c == 'Z' || c == 'z')) {
                    s = s + 'z';
                    i++;
                }
            }
            if (latMapping.containsKey(s)) {
                cyrBuffer.append(((Character) latMapping.get(s)).charValue());
            } else {
                cyrBuffer.append(s);
            }
        }
        return cyrBuffer.toString();
    }
}
