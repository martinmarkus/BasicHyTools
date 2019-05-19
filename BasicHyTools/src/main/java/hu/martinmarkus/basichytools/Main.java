package hu.martinmarkus.basichytools;

import hu.martinmarkus.basichytools.configmanagement.initializers.HyToolsInitializer;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.eventmanagement.UserConnectionEventHandler;
import hu.martinmarkus.basichytools.eventmanagement.UserValidationEventHandler;
import hu.martinmarkus.basichytools.functions.BaseFunction;
import hu.martinmarkus.basichytools.functions.generalfunctions.chatfunctions.Me;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.synchronization.Synchronizer;
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

        // TODO: refactor repo/dao for file write response
        HyToolsInitializer.initialize();

        UserValidationEventHandler validationEventHandler = new UserValidationEventHandler();
        validationEventHandler.OnInvalidUserInteraction();

        UserConnectionEventHandler handler = new UserConnectionEventHandler();
        handler.onUserJoin();
        handler.onUserJoin();

        UserManager.getInstance().getUser("mockUser12345", user -> {
            Me me = new Me(user);
            me.execute();
        });

        /*
        userManager.registerUser(birdemic);
        ConfigReader<User> reader = new YamlConfigReader<>(User.class, HyToolsInitializer.getUsersPath());
        reader.read("mockUser12345", user -> {

            String permission = "group.default";
            boolean hasPermission = user.hasPermission(permission);
        });*/
    }
}
