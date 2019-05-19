package hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms;

import hu.martinmarkus.basichytools.models.User;

public class Informer {

    public static void send(User user, String message) {
        if (user == null) {
            return;
        }

        // TODO: implement default message sending to User
    }

    public static void log(String message) {
        // TODO: log to console
    }
}
