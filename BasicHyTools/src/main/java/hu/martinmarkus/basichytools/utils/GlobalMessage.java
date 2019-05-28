package hu.martinmarkus.basichytools.utils;

import hu.martinmarkus.basichytools.models.User;

public class GlobalMessage {
    public static void sendWithCensor(User sender, String message) {
        message = StringUtil.censorMessage(sender, message);
        send(message);
    }

    public static void send(String message) {
        // TODO: sendWithCensor hytale global message
        String lineSeparator = System.getProperty("line.separator");
        message = message.replace("%newline%", lineSeparator);
        System.out.println("To Everyone: " + message);
    }
}
