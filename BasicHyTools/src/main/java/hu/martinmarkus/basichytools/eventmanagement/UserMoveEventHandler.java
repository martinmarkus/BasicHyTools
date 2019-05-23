package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.models.User;

public class UserMoveEventHandler {

    public void onUserMoveEvent() {
        //TODO: get mover's name
        String moverName = "mockUser12345";

        User user = UserManager.getInstance().getOnlineUser(moverName);
        if (user == null) {
            ignoreMoveEvent();
        }
    }

    private void ignoreMoveEvent() {
        // TODO: ignore move event
    }
}
