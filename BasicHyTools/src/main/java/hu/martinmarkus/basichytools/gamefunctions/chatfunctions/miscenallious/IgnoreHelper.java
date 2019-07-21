package hu.martinmarkus.basichytools.gamefunctions.chatfunctions.miscenallious;

import hu.martinmarkus.basichytools.configmanagement.LanguageConfigManager;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;

public class IgnoreHelper {
    public static boolean areIgnoringEachOther(User executor, User target) {
        LanguageConfig languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        if (executor.isIgnoring(target.getName())) {
            executor.sendMessage(languageConfig.getYouAreIgnoring());
            return true;
        } else if (target.isIgnoring(executor.getName())) {
            executor.sendMessage(languageConfig.getYouAreIgnored());
            return true;
        }
        return false;
    }
}
