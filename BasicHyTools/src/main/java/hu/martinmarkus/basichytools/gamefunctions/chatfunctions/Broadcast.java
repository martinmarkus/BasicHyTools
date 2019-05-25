package hu.martinmarkus.basichytools.gamefunctions.chatfunctions;

import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.Informer;
import hu.martinmarkus.basichytools.models.User;

import java.util.List;

public class Broadcast extends GameFunction {

    private String broadcastMessage;

    public Broadcast(User executor, String message) {
        super(executor,  "Broadcast");
        this.broadcastMessage = getBroadcastMessage(message);

        initRawCommand();   // must be called for correct logging
    }

    @Override
    public void execute() {
        super.runFunction(() -> {
            List<User> onlineUsers = UserManager.getInstance().getAllOnlineUsers();
            onlineUsers.forEach(onlineUser -> onlineUser.sendMessage(broadcastMessage));
            Informer.logBroadcast(broadcastMessage);
        });
    }

    @Override
    public Object executeWithReturnValue() {
        execute();
        return null;
    }

    @Override
    public void initRawCommand() {
        super.rawCommand = "/broadcast " + broadcastMessage;
        // required for raw command logging
    }

    private String getBroadcastMessage(String message) {
        String broadcastPrefix = LanguageConfigManager.getInstance().getLanguageConfig().getBroadcastPrefix();
        return broadcastPrefix + ": " + message;
    }
}
