package hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms;

import hu.martinmarkus.basichytools.configmanagement.LanguageConfigManager;
import hu.martinmarkus.basichytools.utils.DateTimeUtil;

public class Informer {
    private static final String BASE = "BasicHyTools";
    private static final String INFO = "[INFO]";
    private static final String WARN = "[WARN]";
    private static final String ERROR = "[ERROR]";

    private static final String BROADCAST =
            LanguageConfigManager.getInstance().getLanguageConfig().getBroadcastPrefix();

    public static void logInfo(String message) {
        log(INFO + ": " + message);
    }

    public static void logBroadcast(String message) {
        log(BROADCAST + ": " + message);
    }

    public static void logWarn(String message) {
        log(WARN + ": " + message);
    }

    public static void logError(String message) {
        log(ERROR + ": " + message);
    }

    private static void log(String message) {
        String date = DateTimeUtil.getActualDate();
        System.out.println("[" + date + "] [" + BASE + "] " + message);
    }
}
