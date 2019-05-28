package hu.martinmarkus.basichytools.initializers;

import hu.martinmarkus.basichytools.configmanagement.*;
import hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories.GameFunctionFactory;
import hu.martinmarkus.basichytools.utils.repeatingfunctions.Announcer;
import hu.martinmarkus.basichytools.utils.repeatingfunctions.ChatCooldown;
import hu.martinmarkus.basichytools.utils.repeatingfunctions.FunctionCooldown;
import hu.martinmarkus.basichytools.utils.Informer;
import hu.martinmarkus.basichytools.configmanagement.configsavers.GroupSaver;
import hu.martinmarkus.basichytools.configmanagement.configsavers.UserSaver;

import java.io.IOException;
import java.util.Properties;

import static com.sun.org.apache.xalan.internal.utils.SecuritySupport.getResourceAsStream;

public class ModuleInitializer {
    private static final String ROOT = "BasicHyTools";
    private static final String USERS = ROOT.concat("\\Users");

    public static void unload() {
        UserSaver.getInstance().stopAutoSave();
        UserSaver.getInstance().saveNow();
        GroupSaver.getInstance().stopAutoSave();
        GroupSaver.getInstance().saveNow();

        Announcer.getInstance().stopAnnouncing();
        FunctionCooldown.getInstance().stopCooldownCheck();
        ChatCooldown.getInstance().stopCooldownCheck();

        GameFunctionFactory.getInstance().clear();
        
        Informer.logInfo("The system has unloaded.");
    }

    public static void load() {
        GameFunctionFactory.getInstance();

        DefaultConfigManager.getInstance();
        LanguageConfigManager.getInstance();
        FunctionParameterManager.getInstance();
        GroupManager.getInstance();
        UserManager.getInstance();

        initializeSaverModules();
        initializeAnnouncerModule();
        initializeCooldownModules();

        Informer.logInfo(getProjectProperties());
    }

    public static String getRootPath() {
        return ROOT;
    }

    public static String getUsersPath() {
        return USERS;
    }

    private static void initializeSaverModules() {
        UserSaver.getInstance().startAutoSave();
        GroupSaver.getInstance().startAutoSave();
    }

    private static void initializeAnnouncerModule() {
        Announcer.getInstance().startAnnouncing();
    }

    private static void initializeCooldownModules() {
        FunctionCooldown.getInstance().startCooldownCheck();
        ChatCooldown.getInstance().startCooldownCheck();
    }

    private static String getProjectProperties() {
        String message = "The system has loaded.";
        try {
            Properties properties = new Properties();
            properties.load(getResourceAsStream("project.properties"));

            message = properties.getProperty("artifactId") + " v" + properties.getProperty("version")
                    + " by '" + properties.getProperty("author") + "' is loaded."
                    + "\nProject repository: " + properties.getProperty("repo");
        } catch (IOException ignored) {
        }
        return message;
    }
}
