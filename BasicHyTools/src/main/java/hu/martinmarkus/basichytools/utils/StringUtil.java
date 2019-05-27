package hu.martinmarkus.basichytools.utils;

import hu.martinmarkus.basichytools.configmanagement.DefaultConfigManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.SwearFilter;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.models.User;

public class StringUtil {

    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }

    public static String censorMessage(User sender, String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }

        DefaultConfig defaultConfig = DefaultConfigManager.getInstance().getDefaultConfig();
        String swearFilterPermission = defaultConfig.getGlobalMechanismPermissions().get("swearFilterBypass");
        SwearFilter swearFilter = new SwearFilter();
        boolean containsSwearWord = swearFilter.containsCensoredWord(message);

        if (!sender.hasPermission(swearFilterPermission) && containsSwearWord) {
            message = swearFilter.doCensoring(message);
        }

        return message;
    }
}
