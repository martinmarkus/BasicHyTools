package hu.martinmarkus.basichytools;

import hu.martinmarkus.basichytools.eventmanagement.UserConnectionEventHandler;

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
        //HyToolsInitializer.initialize();

        /*
        UserConfigManager userConfigManager = UserConfigManager.getInstance();
        User birdemic = userConfigManager.generateMockUser();

        ConfigWriter<User> configWriter = new YamlConfigWriter<>(User.class, HyToolsInitializer.getUsersPath());
        configWriter.write("mockUser12345", birdemic);
        */

        UserConnectionEventHandler handler = new UserConnectionEventHandler();
        handler.onUserJoin();
        handler.onUserQuit();

    }
}
