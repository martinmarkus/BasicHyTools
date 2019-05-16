package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanaging.UserConfigManager;
import hu.martinmarkus.basichytools.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserConnectionEventHandler {

    private List<String> preJoinedUserNames;

    // TODO: implement game event handling

    public  UserConnectionEventHandler() {
        this.preJoinedUserNames = new ArrayList<>();
    }

    public void onUserJoin() {

        // TODO: get joined user's name

        String joinedUserName = "mockUser12345";
        this.preJoinedUserNames.add(joinedUserName);

        UserConfigManager configManager = UserConfigManager.getInstance();

        configManager.getUser(joinedUserName, user -> {
            if (user != null && preJoinedUserNames.contains(joinedUserName)) {
                configManager.registerUser(user);
            }
        });
    }

    public void onUserQuit() {

        // TODO: get quited user's name

        String quitedUserName = "mockUser12345";
        UserConfigManager configManager = UserConfigManager.getInstance();

        User quitedUser = configManager.getOnlineUser(quitedUserName);
        boolean isPreJoined = preJoinedUserNames.contains(quitedUserName);
        if (quitedUser != null || isPreJoined) {
            configManager.unregisterUser(quitedUser);
            preJoinedUserNames.remove(quitedUserName);
        }
    }
}
