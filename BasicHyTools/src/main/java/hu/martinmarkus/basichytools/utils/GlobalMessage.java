package hu.martinmarkus.basichytools.utils;

import hu.martinmarkus.basichytools.models.User;

public class GlobalMessage {
    public static void send(User sender, String message) {
        message = StringUtil.censorMessage(sender, message);
        send(message);
    }

    public static void send(String message) {
        System.out.println("global: " + message);
        // TODO: send hytale global message
    }
}
