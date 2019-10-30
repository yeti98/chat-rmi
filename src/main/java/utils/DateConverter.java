package utils;

import java.text.SimpleDateFormat;

public class DateConverter {
    public static String formatDate(long date) {
        SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");
        return df.format(date);
    }
}
