package hu.martinmarkus.basichytools.utils;

import hu.martinmarkus.basichytools.configmanagement.UserManager;
import hu.martinmarkus.basichytools.gamefunctions.chatfunctions.miscenallious.IgnoreHelper;
import hu.martinmarkus.basichytools.models.User;

import java.util.List;

public class GlobalMessage {
    public static void send(final String message) {
        List<User> onlineUsers = UserManager.getInstance().getAllOnlineUsers();
        onlineUsers.forEach(onlineUser -> onlineUser.sendMessage(message));
    }

    public static void send(User sender, final String message) {
        List<User> onlineUsers = UserManager.getInstance().getAllOnlineUsers();
        onlineUsers.forEach(onlineUser -> {
            boolean ignoring = IgnoreHelper.areIgnoringEachOther(sender, onlineUser);
            if (!ignoring) {
                onlineUser.sendMessage(message);
            }
        });
    }
}
