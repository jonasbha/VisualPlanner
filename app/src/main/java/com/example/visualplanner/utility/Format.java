package com.example.visualplanner.utility;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

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

    // ubrukt
    public static String timeLeft(long timeLeft) {
        timeLeft = timeLeft - Calendar.getInstance().getTimeInMillis();
        long daysLeft = TimeUnit.MILLISECONDS.toDays(timeLeft);
        long hoursLeft = TimeUnit.MILLISECONDS.toHours(timeLeft);
        long minutesLeft = TimeUnit.MILLISECONDS.toMinutes(timeLeft);

        if (timeLeft < 0)
            return "Hendelse ferdig.";

        if (minutesLeft < 15)
            return "Straks!";

        if (hoursLeft < 24 && hoursLeft > 1)
            return "Om " + hoursLeft + " timer.";

        if (hoursLeft < 1)
            return "Innen 1 time.";

        if (daysLeft == 1)
            return daysLeft + " dag igjen.";
        else
            return daysLeft + " dager igjen.";
    }

}
