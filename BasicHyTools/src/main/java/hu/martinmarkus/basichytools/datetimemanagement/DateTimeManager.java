package hu.martinmarkus.basichytools.datetimemanagement;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeManager {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String getActualDate() {
        return new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());
    }
}
