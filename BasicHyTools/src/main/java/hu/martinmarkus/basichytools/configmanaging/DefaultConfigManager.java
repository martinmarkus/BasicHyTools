package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.synchronization.Synchronizer;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configreaders.ConfigReader;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configreaders.YamlConfigReader;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configwriters.ConfigWriter;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configwriters.YamlConfigWriter;

public class DefaultConfigManager {
    public static final String DEFAULT_CONFIG = "config";

    private static DefaultConfigManager defaultConfigManager;

    private DefaultConfig defaultConfig;

    public static DefaultConfigManager getInstance() {
        if (defaultConfigManager == null) {
            defaultConfigManager = new DefaultConfigManager();
        }

        return defaultConfigManager;
    }

    private DefaultConfigManager() {
        initDefaultConfigFromFile();
    }

    private void initDefaultConfigFromFile() {
        Synchronizer synchronizer = new Synchronizer();
        String path = HyToolsInitializer.getRootPath();
        ConfigReader<DefaultConfig> configConfigReader = new YamlConfigReader<>(DefaultConfig.class, path);

        configConfigReader.read(DEFAULT_CONFIG, defaultConfig -> {
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
        String path = HyToolsInitializer.getRootPath();
        defaultConfig = generateDefaultConfig();
        ConfigWriter<DefaultConfig> configConfigWriter = new YamlConfigWriter<>(DefaultConfig.class, path);
        configConfigWriter.write(DefaultConfigManager.DEFAULT_CONFIG, defaultConfig);
    }

    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }
}
