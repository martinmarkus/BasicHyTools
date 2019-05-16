package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.configmanagerlibrary.fileprocessing.GlobalPath;

public class BasicHyToolsInitializer {

    public static void initialize() {
        GlobalPath.setPath("path");
        PermissionGroupConfigManager.getInstance();
        UserConfigManager.getInstance();
    }
}
