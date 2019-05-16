package hu.martinmarkus.basichytools;

import hu.martinmarkus.basichytools.configmanaging.HyToolsInitializer;
import hu.martinmarkus.basichytools.configmanaging.PermissionGroupManager;
import hu.martinmarkus.basichytools.configmanaging.UserManager;
import hu.martinmarkus.basichytools.functions.BaseFunction;
import hu.martinmarkus.basichytools.models.PermissionGroup;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.permissionmanagement.GroupPermissionValidator;
import hu.martinmarkus.basichytools.permissionmanagement.PermissionValidator;
import hu.martinmarkus.basichytools.permissionmanagement.UserPermissionValidator;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configreaders.ConfigReader;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configreaders.YamlConfigReader;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configwriters.ConfigWriter;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configwriters.YamlConfigWriter;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

import java.io.IOException;
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
        HyToolsInitializer.initialize();

        /*
        UserManager userConfigManager = UserManager.getInstance();
        User birdemic = userConfigManager.generateMockUser();
        ConfigWriter<User> configWriter = new YamlConfigWriter<>(User.class, HyToolsInitializer.getUsersPath());
        configWriter.write("mockUser12345", birdemic);
        */

        ConfigReader<User> reader = new YamlConfigReader<>(User.class, HyToolsInitializer.getUsersPath());
        reader.read("mockUser12345", user -> {

            String permission = "group.default";
            boolean hasPermission = user.hasPermission(permission);
        });


    }
}
