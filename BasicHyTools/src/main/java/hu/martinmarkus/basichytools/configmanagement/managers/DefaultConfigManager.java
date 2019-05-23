package hu.martinmarkus.basichytools.configmanagement.managers;

import hu.martinmarkus.basichytools.configmanagement.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.models.BasicHyToolsLocation;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.persistence.repositories.DefaultConfigRepository;
import hu.martinmarkus.basichytools.persistence.repositories.IDefaultConfigRepository;
import hu.martinmarkus.basichytools.synchronization.ISynchronizer;
import hu.martinmarkus.basichytools.synchronization.Synchronizer;

import java.util.ArrayList;
import java.util.List;

public class DefaultConfigManager {
    public static final String DEFAULT_CONFIG = "config";

    private static DefaultConfigManager defaultConfigManager;

    private IDefaultConfigRepository defaultConfigRepository;
    private DefaultConfig defaultConfig;

    public static DefaultConfigManager getInstance() {
        if (defaultConfigManager == null) {
            defaultConfigManager = new DefaultConfigManager();
        }

        return defaultConfigManager;
    }

    private DefaultConfigManager() {
        String path = ModuleInitializer.getRootPath();
        defaultConfigRepository = new DefaultConfigRepository(path);
        initDefaultConfigFromFile();
    }

    private void initDefaultConfigFromFile() {
        ISynchronizer synchronizer = new Synchronizer();

        defaultConfigRepository.get(DEFAULT_CONFIG, defaultConfig -> {
            if (defaultConfig == null) {
                writeNewDefaultConfig();
                synchronizer.continueRun();
                return;
            } else {
                this.defaultConfig = defaultConfig;
            }
            synchronizer.continueRun();
        });

        synchronizer.waitRun();
    }

    private void writeNewDefaultConfig() {
        ISynchronizer synchronizer = new Synchronizer();

        defaultConfig = generateDefaultConfig();
        defaultConfigRepository.add(DEFAULT_CONFIG, defaultConfig, aBoolean -> {
            synchronizer.continueRun();
        });

        synchronizer.waitRun();
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    public DefaultConfig generateDefaultConfig() {
        List<String> announcerMessages = new ArrayList<>();
        announcerMessages.add("This is the first example announcement.");
        announcerMessages.add("This is the second example announcement.");
        announcerMessages.add("This is the third example announcement.");

        BasicHyToolsLocation spawnLocation = new BasicHyToolsLocation("spawnWorld", 10.0f, 10.0f, 10.0f);

        return new DefaultConfig(60, 5, 5, 60,
                true, 300, 2800, 300, 30, 1000,
                0, 999999999, 1, 15, 3,180, announcerMessages, spawnLocation);
    }
}
