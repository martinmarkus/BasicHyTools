package hu.martinmarkus.basichytools.configmanagement.initializers;

import hu.martinmarkus.basichytools.configmanagement.managers.*;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.Announcer;
import hu.martinmarkus.basichytools.globalmechanisms.savemechanisms.GroupSaver;
import hu.martinmarkus.basichytools.globalmechanisms.savemechanisms.UserSaver;
import hu.martinmarkus.basichytools.models.DefaultConfig;

public class ModuleInitializer {
    private static final String ROOT = "BasicHyTools";
    private static final String USERS = ROOT.concat("\\Users");
    private static final int SAVE_INTERVAL = getSaveInterval();

    public static void unload() {
        UserSaver.getInstance().stopAutoSave();
        UserSaver.getInstance().saveNow();
        GroupSaver.getInstance().stopAutoSave();
        GroupSaver.getInstance().saveNow();

        Announcer.getInstance().stopAnnouncing();
    }

    public static void load() {
        DefaultConfigManager.getInstance();
        LanguageConfigManager.getInstance();
        FunctionParameterManager.getInstance();
        GroupManager.getInstance();
        UserManager.getInstance();

        initializeSaverModules();
        initializeAnnouncerModule();
    }

    public static String getRootPath() {
        return ROOT;
    }

    public static String getUsersPath() {
        return USERS;
    }

    private static void initializeSaverModules() {
        UserSaver.getInstance().startAutoSave(SAVE_INTERVAL);
        GroupSaver.getInstance().startAutoSave(SAVE_INTERVAL);
    }

    private static void initializeAnnouncerModule() {
        Announcer.getInstance().startAnnouncing();
    }

    private static int getSaveInterval() {
        DefaultConfig defaultConfig = DefaultConfigManager.getInstance().getDefaultConfig();
        return defaultConfig.getAutoSaveInterval();
    }
}
