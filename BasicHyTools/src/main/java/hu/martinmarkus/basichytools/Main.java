package hu.martinmarkus.basichytools;

import hu.martinmarkus.basichytools.configmanagement.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.eventmanagement.CommandEventHandler;
import hu.martinmarkus.basichytools.eventmanagement.UserConnectionEventHandler;
import hu.martinmarkus.basichytools.eventmanagement.UserValidationEventHandler;
import hu.martinmarkus.basichytools.globalmechanisms.savemechanisms.UserSaver;
import hu.martinmarkus.basichytools.models.User;

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
        printProjectProperties();
        ModuleInitializer.loadAllModules();

        UserValidationEventHandler validationEventHandler = new UserValidationEventHandler();
        validationEventHandler.OnInvalidUserInteraction();

        UserConnectionEventHandler handler = new UserConnectionEventHandler();
        handler.onUserJoin();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                User user = UserManager.getInstance().getOnlineUser("mockUser12345");
                CommandEventHandler commandEventHandler = new CommandEventHandler();
                commandEventHandler.onUserExecuteCommand();
            }
        });
        thread.start();


        UserSaver userSaver = UserSaver.getInstance();
        userSaver.startAutoSave(5);

        try {
            Thread.sleep(10000);
            for (int i = 0; i < 10; i++) {
                System.out.println("hello");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        userSaver.stopAutoSave();
    }
}
