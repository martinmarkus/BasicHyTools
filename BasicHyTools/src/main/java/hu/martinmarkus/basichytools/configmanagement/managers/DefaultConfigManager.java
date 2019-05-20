package hu.martinmarkus.basichytools.configmanagement.managers;

import hu.martinmarkus.basichytools.configmanagement.initializers.HyToolsInitializer;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.persistence.repositories.DefaultConfigRepository;
import hu.martinmarkus.basichytools.persistence.repositories.IDefaultConfigRepository;
import hu.martinmarkus.basichytools.synchronization.ISynchronizer;
import hu.martinmarkus.basichytools.synchronization.Synchronizer;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

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
        String path = HyToolsInitializer.getRootPath();
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
        return new DefaultConfig(60, 5, 5, 60,
                true, 300, 2800, 300, 30, 1000,
                0, 999999999, 1, 15);
    }
}
