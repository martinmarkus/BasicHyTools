package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.models.User;

public class UserConnectionEventHandler {

    private UserManager userManager;

    // TODO: implement game event handling

    public UserConnectionEventHandler() {
        this.userManager = UserManager.getInstance();
    }

    public void onUserJoin() {
        // TODO: get joined user's name

        String joinedUserName = "mockUser12345";

        User checkUser = userManager.getOnlineUser(joinedUserName);
        if (checkUser != null) {
            // TODO: kick, because user is already online
            return;
        }

        userManager.registerUser(joinedUserName, validUser -> {
            // TODO: successful validation, handle it
        });
    }

    public void onUserQuit() {
        // TODO: get quited user's name

        String quitedUserName = "mockUser12345";
        userManager.unregisterUser(quitedUserName);
    }
}
