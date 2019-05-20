package hu.martinmarkus.basichytools.configmanagement.initializers;

import hu.martinmarkus.basichytools.configmanagement.managers.*;

public class HyToolsInitializer {
    private static final String ROOT = "BasicHyTools";
    private static final String USERS = ROOT.concat("\\Users");

    public static void initializeAllModules() {
        initializeDefaultConfig();
        initializeFunctionParameterConfig();
        initializeLanguageConfig();
        initializeGroupConfig();
        initializeUserConfig();
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
