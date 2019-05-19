package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.persistence.repositories.ILanguageConfigRepository;
import hu.martinmarkus.basichytools.persistence.repositories.LanguageConfigRepository;
import hu.martinmarkus.basichytools.synchronization.Synchronizer;

public class LanguageConfigManager {
    public static final String LANGUAGE_CONFIG ="language";

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
        Synchronizer synchronizer = new Synchronizer();

        languageConfigRepository.get(LANGUAGE_CONFIG, languageConfig -> {
            if (languageConfig == null) {
                writeNewLanguageConfig();
                synchronizer.continueRun();
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

    private LanguageConfig generateDefaultLanguageConfig() {
        LanguageConfig languageConfig = null;
        // TODO: use mock lang generator
        return languageConfig;
    }

    public LanguageConfig getLanguageConfig() {
        return languageConfig;
    }
}
