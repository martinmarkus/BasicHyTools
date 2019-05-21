package hu.martinmarkus.basichytools.globalmechanisms.savemechanisms;

import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.placeholders.placeholderhelpers.PlaceholderReplacer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class ConfigSaver implements IConfigSaver {
    protected final int MIN_SAVE_INTERVAL = 10;

    protected ScheduledExecutorService executorService;
    protected boolean isRunning;

    protected ConfigSaver() {
        executorService = Executors.newScheduledThreadPool(0);
    }

    public abstract void startAutoSave(int saveInterval);

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
