package hu.martinmarkus.basichytools.configmanagement.initializers;

import hu.martinmarkus.basichytools.configmanagement.managers.*;
import hu.martinmarkus.basichytools.globalmechanisms.savemechanisms.UserSaver;
import hu.martinmarkus.basichytools.models.DefaultConfig;

public class ModuleInitializer {
    private static final String ROOT = "BasicHyTools";
    private static final String USERS = ROOT.concat("\\Users");

    public static void unloadAllModules() {
        UserSaver userSaver = UserSaver.getInstance();
        userSaver.stopAutoSave();
    }

    public static void loadAllModules() {
        initializeDefaultConfig();
        initializeLanguageConfig();
        initializeFunctionParameterConfig();
        initializeGroupConfig();
        initializeUserConfig();
        initializeSaverModules();
    }

    private static void initializeSaverModules() {
        initializeUserSaver();
        // TODO: add all saver module
    }

    private static void initializeUserSaver() {
        DefaultConfig defaultConfig = DefaultConfigManager.getInstance().getDefaultConfig();
        int saveInterval = defaultConfig.getAutoSaveInterval();

        UserSaver userSaver = UserSaver.getInstance();
        userSaver.startAutoSave(saveInterval);
    }

    public static void initializeDefaultConfig() {
        DefaultConfigManager.getInstance();
    }

    public static void initializeFunctionParameterConfig() {
        FunctionParameterManager.getInstance();
    }

    public static void initializeLanguageConfig() {
        LanguageConfigManager.getInstance();
    }
    
    public static void initializeGroupConfig() {
        GroupManager.getInstance();
    }

    public static void initializeUserConfig() {
        UserManager.getInstance();
    }

    public static String getRootPath() {
        return ROOT;
    }

    public static String getUsersPath() {
        return USERS;
    }
}
