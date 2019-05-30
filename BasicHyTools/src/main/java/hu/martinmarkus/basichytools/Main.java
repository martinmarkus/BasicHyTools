package hu.martinmarkus.basichytools;

import hu.martinmarkus.basichytools.configmanagement.BannedUserManager;
import hu.martinmarkus.basichytools.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.eventmanagement.CommandEventHandler;
import hu.martinmarkus.basichytools.eventmanagement.UserConnectionEventHandler;
import hu.martinmarkus.basichytools.eventmanagement.UserValidationEventHandler;
import hu.martinmarkus.basichytools.models.BannedUser;
import hu.martinmarkus.basichytools.utils.DateTimeUtil;

public class Main {
    public static void main(String[] args) {
        ModuleInitializer.load();

        UserValidationEventHandler validationEventHandler = new UserValidationEventHandler();
        validationEventHandler.OnInvalidUserInteraction();
        UserConnectionEventHandler handler = new UserConnectionEventHandler();
        handler.onUserJoin();

        Thread thread = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            CommandEventHandler commandEventHandler = new CommandEventHandler();
            commandEventHandler.onUserExecuteCommand("balancetoplist");

            BannedUser bannedUser = new BannedUser("mockUser12345", "123.123.123.123", DateTimeUtil.getActualDate(), 60, "banned hahahaa", "birdemic", true);
            BannedUserManager.getInstance().addBannedUser(bannedUser);
            BannedUserManager.getInstance().addBannedUser(bannedUser);
            BannedUserManager.getInstance().addBannedUser(bannedUser);
            BannedUserManager.getInstance().addBannedUser(bannedUser);

            ModuleInitializer.unload();
        });
        thread.start();
    }
}
