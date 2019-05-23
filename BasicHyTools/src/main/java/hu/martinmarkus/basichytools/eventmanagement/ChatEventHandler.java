package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.ChatCooldown;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.SwearFilter;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.models.placeholders.placeholderhelpers.PlaceholderReplacer;

public class ChatEventHandler {

    private DefaultConfig defaultConfig = DefaultConfigManager.getInstance().getDefaultConfig();

    public void onChatMessageContainsSwearWord() {
        User user = UserManager.getInstance().getOnlineUser("mockUser12345"); // TODO: get the sender
        String message = "Egy NaGyBetŰs fUCk szöPÉLke"; // TODO: get the message

        boolean isOperator = user.isOperator();
        String swearFilterPermission = defaultConfig.getGlobalMechanismPermissions().get("swearFilterBypass");
        if (isOperator || user.hasPermission(swearFilterPermission)) {
            executeMessageSending(user, message);
            return;
        }

        SwearFilter swearFilter = new SwearFilter();
        boolean containsSwearWord = swearFilter.containsCensoredWord(message);

        if (containsSwearWord) {
            message = swearFilter.doCensoring(message);
        }
        executeMessageSending(user, message);
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
        String chatCooldownPassPermission = defaultConfig.getGlobalMechanismPermissions().get("chatCooldownBypass");
        if (isOperator || user.hasPermission(chatCooldownPassPermission)) {
            executeMessageSending(user, message);
            return;
        }

        int cooldown = ChatCooldown.getInstance().getCooldownValue(userName);
        if (cooldown > 0) {
            ignoreMessageSendWithWarn(user, cooldown);
            return;
        }

        executeMessageSending(user, message);
    }

    private void executeMessageSending(User user, String message) {
        ChatCooldown.getInstance().addChatCooldown(user.getName());
        // TODO: send message by user to everyone

        System.out.println("execute msg send to everyone by " + user.getName() + ": " + message);
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
