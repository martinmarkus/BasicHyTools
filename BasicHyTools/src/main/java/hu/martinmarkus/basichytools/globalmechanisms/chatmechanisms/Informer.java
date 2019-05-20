package hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms;

import hu.martinmarkus.basichytools.models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Informer {
    private static final String BASE = "BasicHyTools";
    private static final String INFO = "[INFO]";
    private static final String WARN = "[WARN]";
    private static final String ERROR = "[ERROR]";

    public static void logInfo(String message) {
        log(INFO + ": " + message);
    }

    public static void logWarn(String message) {
        log(WARN + ": " + message);
    }

    public static void logError(String message) {
        log(ERROR + ": " + message);
    }

    private static void log(String message) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(Calendar.getInstance().getTime());

        System.out.println("[" + timeStamp + "] [" + BASE + "] " + message);
    }
}
