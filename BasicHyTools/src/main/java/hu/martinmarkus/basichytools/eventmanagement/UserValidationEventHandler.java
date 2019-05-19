package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.models.User;

public class UserValidationEventHandler {

    // TODO: implement game event handling

    private UserManager userManager;

    public UserValidationEventHandler() {
        this.userManager = UserManager.getInstance();
    }

    public void OnInvalidUserInteraction() {
        // TODO: get the user's name
        String username = "mockUser12345";

        User user = userManager.getOnlineUser(username);
        if (user == null || !user.isValidated()) {
            // TODO: ignore interaction, until the user is not validated
        }
    }
}
