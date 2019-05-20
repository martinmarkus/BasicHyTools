package hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms;

import hu.martinmarkus.basichytools.models.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HyLogger {
    private static final String BASE = "BasicHyTools";

    public static final String INFO = "[INFO]";
    public static final String WARN = "[WARN]";
    public static final String ERROR = "[ERROR]";

    public static void send(User user, String message) {
        if (user == null) {
            return;
        }

        // TODO: implement default message sending to User
        System.out.println("Send to '" + user.getName() + "': " + message);
    }

    public static void log(String prefix, String message) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        System.out.println(prefix + " [" + timeStamp +  "] " +  BASE + ": " + message);
    }
}
