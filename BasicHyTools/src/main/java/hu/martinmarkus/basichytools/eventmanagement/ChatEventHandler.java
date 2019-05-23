package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.ChatCooldown;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.SwearFilter;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.models.placeholders.placeholderhelpers.PlaceholderReplacer;

public class ChatEventHandler {

    public void onChatMessageContainsSwearWord() {
        User user = null; // TODO: get the sender
        String message = "fUCk"; // TODO: get the message

        SwearFilter swearFilter = new SwearFilter();
        boolean containsSwearWord = swearFilter.containsCensoredWord(message);

        if (containsSwearWord) {
            message = swearFilter.doCensoring(message);
        }

        // TODO: send "message" to chat

        sendMessage(user, message);
    }

    // TODO: implement message cooldown checking
    public void onSendChatMessageCooldown() {
        String userName = "mockUser12345"; // TODO: get the user
        String message = "mockMessage"; // TODO: get the message

        User user = UserManager.getInstance().getOnlineUser(userName);
        if (user == null) {
            ignoreMessage();
            return;
        }

        boolean isOperator = user.isOperator();
        if (isOperator) {
            return;
        }

        int cooldown = ChatCooldown.getInstance().getCooldownValue(userName);
        if (cooldown > 0) {
            ignoreMessageSendWithWarn(user, cooldown);
            return;
        }

        sendMessage(user, message);
    }

    private void sendMessage(User user, String message) {
        // TODO: send message by user
    }

    private void ignoreMessageSendWithWarn(User user, int cooldown) {
        ignoreMessage();
        sendWarnToUser(user, cooldown);
    }


    private void ignoreMessage() {
        // TODO: ignore message send event
    }

    private void sendWarnToUser(User user, int cooldown) {
        LanguageConfig languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        String message = languageConfig.getChatStillOnCooldown();
        String secondString = languageConfig.getSecond();

        String messageValue;
        if (cooldown == 0) {
            messageValue = languageConfig.getForOneMoreSecond();
        } else {
            messageValue = String.format("%02d " + secondString, cooldown);
        }

        PlaceholderReplacer replacer = new PlaceholderReplacer();
        message = replacer.replace(message, messageValue);

        user.sendMessage(message);
    }
}
