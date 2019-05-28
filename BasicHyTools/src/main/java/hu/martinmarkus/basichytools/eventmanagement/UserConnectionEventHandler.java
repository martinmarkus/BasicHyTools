package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.UserManager;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.utils.GlobalMessage;
import hu.martinmarkus.basichytools.utils.PlaceholderReplacer;

public class UserConnectionEventHandler {
    private UserManager userManager;
    private LanguageConfig languageConfig;

    public UserConnectionEventHandler() {
        this.userManager = UserManager.getInstance();
        this.languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
    }

    public void onUserJoin() {
        // TODO: get joined user's name

        String joinedUserName = "mockUser12345";

        User checkUser = userManager.getOnlineUser(joinedUserName);
        if (checkUser != null) {
            // TODO: kick, because user is already online
            return;
        }

        PlaceholderReplacer replacer = new PlaceholderReplacer();
        String message = replacer.replace(languageConfig.getJoinMessage(), joinedUserName);
        GlobalMessage.send(message);

        userManager.registerUser(joinedUserName, validUser -> {
            validUser.sendMessage(languageConfig.getMotd(), false);
            validUser.teleportToSpawnOnFirstJoin();
        });
    }

    public void onUserQuit() {
        // TODO: get quited user's name
        String quitedUserName = "mockUser12345";

        PlaceholderReplacer replacer = new PlaceholderReplacer();
        String message = replacer.replace(languageConfig.getQuitMessage(), quitedUserName);
        GlobalMessage.send(message);

        userManager.unregisterUser(quitedUserName);
    }
}
