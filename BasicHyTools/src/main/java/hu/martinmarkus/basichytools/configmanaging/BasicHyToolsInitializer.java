package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.configmanagerlibrary.fileprocessing.GlobalPath;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.exceptions.RootPathAlreadySetException;

public class BasicHyToolsInitializer {

    public static void initialize() {
        try {
            GlobalPath.setPath("path");
        } catch (RootPathAlreadySetException e) {
            e.printStackTrace();
        }
        PermissionGroupConfigManager.getInstance();
        UserConfigManager.getInstance();
    }
}
