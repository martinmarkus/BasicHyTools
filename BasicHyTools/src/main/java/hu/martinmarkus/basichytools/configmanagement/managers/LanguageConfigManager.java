package hu.martinmarkus.basichytools.configmanagement.managers;

import hu.martinmarkus.basichytools.configmanagement.initializers.HyToolsInitializer;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.persistence.repositories.ILanguageConfigRepository;
import hu.martinmarkus.basichytools.persistence.repositories.LanguageConfigRepository;
import hu.martinmarkus.basichytools.synchronization.ISynchronizer;
import hu.martinmarkus.basichytools.synchronization.Synchronizer;

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
        String path = HyToolsInitializer.getRootPath();
        languageConfigRepository = new LanguageConfigRepository(path);
        initLanguageConfigFromFile();
    }

    // config initialization:
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
        languageConfig.setNotEnoughPermission("You don't have permission to execute this function.");
        languageConfig.setNotEnoughMoney("You don't have enough money to execute this function.");
        languageConfig.setErrorHasOccurred("An error has occurred while trying to execute a function. Please contact an administrator.");
        languageConfig.setCommandExecuted("User %value% has executed the following function: %value%");
        languageConfig.setUserIsStillConnecting("The user is still not available (still connecting?)");
        return languageConfig;
    }
}
