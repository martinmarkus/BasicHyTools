package hu.martinmarkus.basichytools.configmanaging;

public class HyToolsInitializer {
    private static final String ROOT = "BasicHyTools";
    private static final String USERS = ROOT.concat("\\Users");

    public static void initialize() {
        PermissionGroupConfigManager.getInstance();
        UserConfigManager.getInstance();
    }

    public static String getRootPath() {
        return ROOT;
    }

    public static String getUsersPath() {
        return USERS;
    }
}
