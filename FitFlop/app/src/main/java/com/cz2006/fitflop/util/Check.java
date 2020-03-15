package com.cz2006.fitflop.util;

public class Check {
    /**
     * Return true if the @param is null
     * @param string
     * @return
     */
    public static boolean isEmpty(String string){
        return string.equals("");
    }

    /**
     * Return true if @param 's1' matches @param 's2'
     * @param s1
     * @param s2
     * @return
     */
    public static boolean areStringsEqual(String s1, String s2){
        return s1.equals(s2);
    }

}
