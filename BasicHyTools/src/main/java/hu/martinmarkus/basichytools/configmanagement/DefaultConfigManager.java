package hu.martinmarkus.basichytools.configmanagement;

import hu.martinmarkus.basichytools.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories.DefaultConfigFactory;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.persistence.repositories.DefaultConfigRepository;
import hu.martinmarkus.basichytools.persistence.repositories.IDefaultConfigRepository;
import hu.martinmarkus.basichytools.utils.synchronization.ISynchronizer;
import hu.martinmarkus.basichytools.utils.synchronization.Synchronizer;

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
        DefaultConfigFactory defaultConfigFactory = new DefaultConfigFactory();
        return defaultConfigFactory.getBean("defaultConfig");
    }
}
