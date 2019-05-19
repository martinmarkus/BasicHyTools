package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.persistence.repositories.DefaultConfigRepository;
import hu.martinmarkus.basichytools.persistence.repositories.IDefaultConfigRepository;
import hu.martinmarkus.basichytools.synchronization.Synchronizer;

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
        Synchronizer synchronizer = new Synchronizer();

        defaultConfigRepository.get(DEFAULT_CONFIG, defaultConfig -> {
            if (defaultConfig == null) {
                writeNewDefaultConfig();
                synchronizer.continueRun();
            } else {
                this.defaultConfig = defaultConfig;
            }
            synchronizer.continueRun();
        });

        synchronizer.waitRun();
    }

    public DefaultConfig generateDefaultConfig() {
        return new DefaultConfig(60, 5, 5, 60,
                true, 300, 2800, 300, 30, 1000,
                0, 999999999, 1, 15);
    }

    private void writeNewDefaultConfig() {
        defaultConfig = generateDefaultConfig();
        defaultConfigRepository.add(DEFAULT_CONFIG, defaultConfig);
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }
}
