package hu.martinmarkus.basichytools.configmanagement.initializers;

import hu.martinmarkus.basichytools.configmanagement.managers.*;

public class ModuleInitializer {
    private static final String ROOT = "BasicHyTools";
    private static final String USERS = ROOT.concat("\\Users");

    public static void initializeAllModules() {
        initializeDefaultConfig();
        initializeLanguageConfig();
        initializeFunctionParameterConfig();
        initializeGroupConfig();
        initializeUserConfig();

        // TODO: add HERE the instantiation of event handler classes???
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
