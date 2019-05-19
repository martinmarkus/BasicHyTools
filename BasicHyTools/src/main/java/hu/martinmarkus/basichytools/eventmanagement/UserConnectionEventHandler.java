package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanaging.UserManager;

import java.util.ArrayList;
import java.util.List;

public class UserConnectionEventHandler {

    private List<String> preJoinedUserNames;
    UserManager useManager;

    // TODO: implement game event handling

    public  UserConnectionEventHandler() {
        this.useManager = UserManager.getInstance();
    this.preJoinedUserNames = new ArrayList<>();
    }

    public void onUserJoin() {

        // TODO: get joined user's name

        String joinedUserName = "mockUser12345";
        useManager.registerUser(joinedUserName);

        /*
                this.preJoinedUserNames.add(joinedUserName);
        useManager.getUser(joinedUserName, user -> {
            if (user != null && preJoinedUserNames.contains(joinedUserName)) {
                useManager.registerUser(joinedUserName);
            }
        });
        */
    }

    public void onUserQuit() {

        // TODO: get quited user's name

        String quitedUserName = "mockUser12345";
        useManager.unregisterUser(quitedUserName);
        /*
        User quitedUser = useManager.getOnlineUser(quitedUserName);
        boolean isPreJoined = preJoinedUserNames.contains(quitedUserName);
        if (quitedUser != null || isPreJoined) {
            useManager.unregisterUser(quitedUserName);
            preJoinedUserNames.remove(quitedUserName);
        }
        */
    }
}
