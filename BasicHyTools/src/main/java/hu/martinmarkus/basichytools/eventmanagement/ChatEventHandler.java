package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.GroupManager;
import hu.martinmarkus.basichytools.configmanagement.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.UserManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.ChatCooldown;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.models.Group;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.utils.ChatMessageBuilder;
import hu.martinmarkus.basichytools.utils.PlaceholderReplacer;
import hu.martinmarkus.basichytools.utils.StringUtil;

public class ChatEventHandler {

    private DefaultConfig defaultConfig = DefaultConfigManager.getInstance().getDefaultConfig();

    public void onMessageSent(String message) {
        User user = UserManager.getInstance().getOnlineUser("mockUser12345"); // TODO: get the sender
        //String message = "message send by user"; // TODO: get the message

        if (user == null || message == null || message.isEmpty()) {
            ignoreMessageEvent();
            return;
        }

        boolean isOperator = user.isOperator();
        if (isOperator) {
            executeMessageSending(user, message);
            return;
        }

        boolean isOnCooldown = isOnCooldown(user);
        if (isOnCooldown) {
            ignoreMessageEvent();
            return;
        }

        executeMessageSending(user, message);
    }

    private boolean isOnCooldown(User user) {
        String chatCooldownPassPermission = defaultConfig.getGlobalMechanismPermissions().get("chatCooldownBypass");
        int cooldown = ChatCooldown.getInstance().getCooldownValue(user.getName());

        if (!user.hasPermission(chatCooldownPassPermission) && cooldown > 0) {
            ignoreMessageSendWithWarn(user, cooldown);
            return true;
        }

        return false;
    }

    private void executeMessageSending(User user, String message) {
        if (user == null || message == null || message.isEmpty()) {
            return;
        }

        user.addSentMessage(message);
        Group group = GroupManager.getInstance().getPermissionGroup(user.getPermissionGroupName());
        ChatMessageBuilder builder = new ChatMessageBuilder();
        String prefix;
        String suffix;

        if (group == null) {
            prefix = builder.defineTitle(user.getUserPrefix());
            suffix = builder.defineTitle(user.getUserSuffix());
        } else {
            prefix = builder.defineTitle(user.getUserPrefix(), group.getPrefix());
            suffix = builder.defineTitle(user.getUserSuffix(), group.getSuffix());
        }

        String fullMessage = builder.buildMessage(prefix, suffix, user.getName(), message, true);
        System.out.println(fullMessage);    // TODO: send message by user to everyone
        ChatCooldown.getInstance().addChatCooldown(user.getName());
    }

    private void ignoreMessageSendWithWarn(User user, int cooldown) {
        ignoreMessageEvent();
        sendStillOnCooldown(user, cooldown);
    }

    private void ignoreMessageEvent() {
        // TODO: ignore message send event
    }

    private void sendStillOnCooldown(User user, int cooldown) {
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

        user.sendMessage(message, false);
    }
}
