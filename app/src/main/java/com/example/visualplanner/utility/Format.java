package com.example.visualplanner.utility;

public class Format {

    public static String date(String datetime) {
        if (datetime != null)
            return datetime.substring(0, 10).
                    concat(" ," + datetime.substring(datetime.length()-5));
        return null;
    }
}
