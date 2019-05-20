package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;

public class UserMoveEventHandler {

    public void onUserMoveEvent() {
        //TODO: get mover's name
        String moverName = "mockUser12345";

        boolean isOnline = UserManager.getInstance().isUserOnlineByName(moverName);

        if (!isOnline) {
            // TODO: block movement, because user is not online and not valid
        }
    }
}
