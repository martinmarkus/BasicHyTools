package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.ChatCooldown;
import hu.martinmarkus.basichytools.models.User;

public class ChatEventHandler {

    public void onSendChatMessageCooldown() {
        String userName = "mockUser12345";

        User user = UserManager.getInstance().getOnlineUser(userName);
        if (user == null) {
            ignoreMessageSend();
        }

        boolean isOperator = user.isOperator();
        if (isOperator) {
            return;
        }

        boolean isOnCooldown = ChatCooldown.getInstance().containsChatCooldown(userName);
        if (isOnCooldown) {
            ignoreMessageSend();
        }
    }

    private void ignoreMessageSend() {
        // TODO: ignore chat msg send
    }
}
