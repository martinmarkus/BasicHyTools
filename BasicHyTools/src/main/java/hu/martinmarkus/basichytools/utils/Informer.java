package hu.martinmarkus.basichytools.utils;

import hu.martinmarkus.basichytools.configmanagement.LanguageConfigManager;

public class Informer {
    private static final String SEPARATOR = LanguageConfigManager.getInstance().getLanguageConfig().getSeparator();

    public static void logInfo(String message) {
        String prefix = LanguageConfigManager.getInstance().getLanguageConfig().getBasicHyToolsPrefix();
        log(prefix + SEPARATOR + message);
    }

    public static void logWarn(String message) {
        String prefix = LanguageConfigManager.getInstance().getLanguageConfig().getWarnPrefix();
        log(prefix + SEPARATOR + message);
    }

    public static void logError(String message) {
        String prefix = LanguageConfigManager.getInstance().getLanguageConfig().getErrorPrefix();
        log(prefix + SEPARATOR + message);
    }

    private static void log(String message) {
        String prefix = LanguageConfigManager.getInstance().getLanguageConfig().getBasicHyToolsPrefix();
        String date = DateTimeUtil.getActualDate();
        System.out.println("[" + date + "] " + prefix + message);
    }
}
