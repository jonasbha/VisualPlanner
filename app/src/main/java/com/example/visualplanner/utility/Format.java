package com.example.visualplanner.utility;

public class Format {

    public static String date(String datetime) {
        if (datetime != null)
            return datetime.substring(0, 10).
                    concat(" ,").concat(datetime.substring(datetime.length()-5));
        return null;
    }

    public static String time(String datetime) {
        if (datetime != null)
            return "kl. ".concat(datetime.substring(11, 16));
        return null;
    }
}
