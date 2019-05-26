package hu.martinmarkus.basichytools.globalmechanisms.savemechanisms;

import hu.martinmarkus.basichytools.configmanagement.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.LanguageConfigManager;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.utils.PlaceholderReplacer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class ConfigSaver implements IConfigSaver {
    protected final int MIN_SAVE_INTERVAL = 10;
    protected final int INITIAL_DELAY = 60;

    protected int saveInterval;
    protected ScheduledExecutorService executorService;
    protected boolean isRunning;

    protected ConfigSaver() {
        executorService = Executors.newScheduledThreadPool(0);
        saveInterval = DefaultConfigManager.getInstance().getDefaultConfig().getAutoSaveInterval();
    }

    public abstract void startAutoSave();

    public abstract void saveNow();

    @Override
    public void stopAutoSave() {
        executorService.shutdownNow();
        isRunning = false;
    }

    protected String createLogMessage(String configName) {
        LanguageConfig languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        String message = languageConfig.getConfigSaveSuccessful();
        PlaceholderReplacer replacer = new PlaceholderReplacer();

        return replacer.replace(message, configName);
    }
}
