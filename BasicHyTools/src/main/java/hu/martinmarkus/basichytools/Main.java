package hu.martinmarkus.basichytools;

import hu.martinmarkus.basichytools.configmanagement.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.eventmanagement.ChatEventHandler;
import hu.martinmarkus.basichytools.eventmanagement.CommandEventHandler;
import hu.martinmarkus.basichytools.eventmanagement.UserConnectionEventHandler;
import hu.martinmarkus.basichytools.eventmanagement.UserValidationEventHandler;
import hu.martinmarkus.basichytools.models.User;

import java.io.IOException;
public class Main {
        public static void main(String[] args) {

        // TODO: test cooldown system
        // TODO: test cooldown system
        // TODO: test cooldown system
        // TODO: test cooldown system
        // TODO: test cooldown system
        ModuleInitializer.load();

        UserValidationEventHandler validationEventHandler = new UserValidationEventHandler();
        validationEventHandler.OnInvalidUserInteraction();
        UserConnectionEventHandler handler = new UserConnectionEventHandler();
        handler.onUserJoin();

        Thread thread = new Thread(() -> {
            CommandEventHandler commandEventHandler = new CommandEventHandler();
            commandEventHandler.onUserExecuteCommand();

            ChatEventHandler chatEventHandler = new ChatEventHandler();
            chatEventHandler.onMessageSent("Egy NaGyBetŰs fUCk szöPÉLke");
            chatEventHandler.onMessageSent("asd");
            chatEventHandler.onMessageSent("Egy");
            chatEventHandler.onMessageSent("Egy éálkszöPÉLke");
            chatEventHandler.onMessageSent("asélkélkd");

            ModuleInitializer.unload();
        });
        thread.start();
    }
}
