package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.BannedUserManager;
import hu.martinmarkus.basichytools.configmanagement.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.UserManager;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.utils.GlobalMessage;
import hu.martinmarkus.basichytools.utils.StringUtil;

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

        boolean isBanned = BannedUserManager.getInstance().isBanned(joinedUserName);
        if (isBanned) {
            kick(joinedUserName);
        }

        User checkUser = userManager.getOnlineUser(joinedUserName);
        if (checkUser != null) {
            kick(joinedUserName);
            return;
        }

        String message = StringUtil.replace(languageConfig.getJoinMessage(), joinedUserName);
        GlobalMessage.send(message);

        userManager.registerUser(joinedUserName, validUser -> {
            validUser.sendMotd();
            validUser.teleportToSpawnOnFirstJoin();
        });
    }

    public void onUserQuit() {
        // TODO: get quited user's name
        String quitedUserName = "mockUser12345";

        String message = StringUtil.replace(languageConfig.getQuitMessage(), quitedUserName);
        GlobalMessage.send(message);

        userManager.unregisterUser(quitedUserName);
    }

    private void kick(String userName) {
        // TODO: kick user
    }
}
