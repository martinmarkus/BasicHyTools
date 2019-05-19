package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;

public class UserConnectionEventHandler {

    private UserManager userManager;

    // TODO: implement game event handling

    public  UserConnectionEventHandler() {
        this.userManager = UserManager.getInstance();
    }

    public void onUserJoin() {
        // TODO: get joined user's name

        String joinedUserName = "mockUser12345";
        userManager.registerUser(joinedUserName, validUser -> {
            validUser.setValidated(true);
        });
    }

    public void onUserQuit() {
        // TODO: get quited user's name

        String quitedUserName = "mockUser12345";
        userManager.unregisterUser(quitedUserName);
    }
}
