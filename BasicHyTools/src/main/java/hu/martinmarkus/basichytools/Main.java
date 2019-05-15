package hu.martinmarkus.basichytools;

import hu.martinmarkus.basichytools.configmanaging.BasicHyToolsInitializer;
import hu.martinmarkus.basichytools.configmanaging.PermissionGroupConfigManager;
import hu.martinmarkus.basichytools.models.PermissionGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.sun.org.apache.xalan.internal.utils.SecuritySupport.getResourceAsStream;

public class Main {
    private static void printProjectProperties() {
        try {
            Properties properties = new Properties();
            properties.load(getResourceAsStream("project.properties"));

            String message = properties.getProperty("artifactId") + " v" + properties.getProperty("version")
                    + " by '" + properties.getProperty("author") + "' is loaded."
                    + "\nProject repository: " + properties.getProperty("repo");

            System.out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        BasicHyToolsInitializer.initialize();

        PermissionGroupConfigManager groupConfigManager = PermissionGroupConfigManager.getInstance();

        PermissionGroup admin = groupConfigManager.getPermissionGroup("admin");

        List<Boolean> bools = new ArrayList<>();
        bools.add(admin.hasPermission("group.default"));
        bools.add(admin.hasPermission("group.builder"));
        bools.add(admin.hasPermission("group.moderator"));
        bools.add(admin.hasPermission("group.helper"));
        bools.add(admin.hasPermission("group.admin"));
        bools.add(admin.hasPermission("group.owner"));
        bools.add(admin.hasPermission("valamijog"));

    }
}
