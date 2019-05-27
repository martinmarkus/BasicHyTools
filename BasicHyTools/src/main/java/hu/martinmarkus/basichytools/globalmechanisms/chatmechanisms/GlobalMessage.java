package hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms;

import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.utils.StringUtil;

public class GlobalMessage {

    public static void send(User sender, String message) {
        message = StringUtil.censorMessage(sender, message);
        System.out.println("global: " + message);
    }
}
