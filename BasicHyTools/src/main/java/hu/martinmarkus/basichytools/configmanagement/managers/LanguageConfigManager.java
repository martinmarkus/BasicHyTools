package hu.martinmarkus.basichytools.configmanagement.managers;

import hu.martinmarkus.basichytools.configmanagement.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.persistence.repositories.ILanguageConfigRepository;
import hu.martinmarkus.basichytools.persistence.repositories.LanguageConfigRepository;
import hu.martinmarkus.basichytools.utils.synchronization.ISynchronizer;
import hu.martinmarkus.basichytools.utils.synchronization.Synchronizer;

import java.util.ArrayList;
import java.util.List;

public class LanguageConfigManager {
    public static final String LANGUAGE_CONFIG = "language";

    private static LanguageConfigManager languageConfigManager;

    private ILanguageConfigRepository languageConfigRepository;
    private LanguageConfig languageConfig;

    public static LanguageConfigManager getInstance() {
        if (languageConfigManager == null) {
            languageConfigManager = new LanguageConfigManager();
        }

        return languageConfigManager;
    }

    private LanguageConfigManager() {
        String path = ModuleInitializer.getRootPath();
        languageConfigRepository = new LanguageConfigRepository(path);
        initLanguageConfigFromFile();
    }

    private void initLanguageConfigFromFile() {
        ISynchronizer synchronizer = new Synchronizer();

        languageConfigRepository.get(LANGUAGE_CONFIG, languageConfig -> {
            if (languageConfig == null) {
                writeNewLanguageConfig();
            } else {
                this.languageConfig = languageConfig;
            }
            synchronizer.continueRun();
        });

        synchronizer.waitRun();
    }

    private void writeNewLanguageConfig() {
        languageConfig = generateDefaultLanguageConfig();
        languageConfigRepository.add(LANGUAGE_CONFIG, languageConfig);
    }

    public LanguageConfig getLanguageConfig() {
        return languageConfig;
    }

    private LanguageConfig generateDefaultLanguageConfig() {
        LanguageConfig languageConfig = new LanguageConfig();
        // TODO: use mock lang generator

        List<String> censoredWords = new ArrayList<>();
        censoredWords.add("damn");
        censoredWords.add("frick");
        censoredWords.add("poop");
        languageConfig.setCensoredWords(censoredWords);

        languageConfig.setMotd("Example motd message\nNew line");
        languageConfig.setJoinMessage("[%value%] has joined.");
        languageConfig.setQuitMessage("[%value%] has quited.");
        languageConfig.setAfkOn("[%value%] is AFK.");
        languageConfig.setAfkOff("[%value%] is no longer AFK.");
        languageConfig.setBanned("[%value%] has been banned by [%value%].");
        languageConfig.setTempBanned("[%value%] has been temp-banned by [%value%] for %value%.");
        languageConfig.setIpBanned("[%value%] has been ipbanned by [%value%].");
        languageConfig.setTempIpBanned("[%value%] has been temp-ipbanned by [%value%] for %value%.");
        languageConfig.setUnbanned("[%value%] has been unbanned by [%value%].");
        languageConfig.setKicked("[%value%] has been kicked by [%value%].");
        languageConfig.setAllKicked("Everyone has been kicked by [%value%].");
        languageConfig.setMuted("[%value%] has been muted by [%value%].");
        languageConfig.setTempMuted("[%value%] has been temp-muted by [%value%] for %value%.");
        languageConfig.setUnmuted("[%value%] has been unmuted by [%value%].");
        languageConfig.setBalanceIncreased("The balance of [%value%] has been increased by %value%. New Balance: %value%");
        languageConfig.setBalanceDecreased("The balance of [%value%] has been decreased by %value%. New Balance: %value%");
        languageConfig.setBalanceSet("The balance of[%value%] has been set to [%value%].");

        languageConfig.setNotEnoughPermission("You don't have permission to execute this function.");
        languageConfig.setNotEnoughMoney("You don't have enough money to execute this function.");
        languageConfig.setErrorHasOccurred("An error has occurred while trying to execute a function. Please contact an administrator.");
        languageConfig.setCommandExecuted("User %value% has executed: %value%");
        languageConfig.setUserIsStillConnecting("The user is still not available (still connecting?)");
        languageConfig.setUnknownCommand("Unknown command.");
        languageConfig.setInvalidCommandUsage("Invalid command usage.");
        languageConfig.setInvalidCommandUsagePleaseTry("Invalid command usage. Please try '%value%'");
        languageConfig.setConfigSaveSuccessful("The auto-saving of [%value%] config was successful.");
        languageConfig.setChatStillOnCooldown("You can't use the chat for %value%. Please don't spam!");
        languageConfig.setFunctionStillOnCooldown("You can't use the function [%value%] for %value%.");
        languageConfig.setCantSendThisMessage("You can't send this message, because one of your last messages was the same.");
        languageConfig.setForOneMoreSecond("one more second");
        languageConfig.setMinute("minute(s)");
        languageConfig.setSecond("second(s)");
        languageConfig.setBroadcastPrefix("[Broadcast]");

        return languageConfig;

    }
}
