package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.ChatCooldown;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.models.placeholders.placeholderhelpers.PlaceholderReplacer;

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

        int cooldown = ChatCooldown.getInstance().getCooldownValue(userName);
        if (cooldown >= 0) {
            ignoreMessageSend(user, cooldown);
        }
    }

    private void ignoreMessageSend(User user, int cooldown) {
        ignoreMessageSend();
        sendWarnToUser(user, cooldown);
    }


    private void ignoreMessageSend() {
        // TODO: ignore message send event
    }

    private void sendWarnToUser(User user, int cooldown) {
        LanguageConfig languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        String message = languageConfig.getChatStillOnCooldown();
        String secondString = languageConfig.getSecond();
        String messageValue = String.format("%02d " + secondString, cooldown);

        PlaceholderReplacer replacer = new PlaceholderReplacer();
        message = replacer.replace(message, messageValue);

        user.sendMessage(message);
    }
}
