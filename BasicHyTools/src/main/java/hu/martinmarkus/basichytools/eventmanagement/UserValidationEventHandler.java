package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.UserManager;
import hu.martinmarkus.basichytools.models.User;

public class UserValidationEventHandler {
    private UserManager userManager;

    // TODO: implement game event handling
    public UserValidationEventHandler() {
        this.userManager = UserManager.getInstance();
    }

    // if the user is not validated, block all necessary interactions
    public void OnInvalidUserInteraction() {
        // TODO: get the user's name
        String username = "mockUser12345";

        User user = userManager.getOnlineUser(username);
        if (user == null || !user.isValidated()) {
            // TODO: ignore interaction, until the user is not validated
        }
    }
}
