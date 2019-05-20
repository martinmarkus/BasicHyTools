package hu.martinmarkus.basichytools.configmanagement.managers;

import hu.martinmarkus.basichytools.configmanagement.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.models.containers.UserList;
import hu.martinmarkus.basichytools.models.BasicHyToolsLocation;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.persistence.repositories.IUserRepository;
import hu.martinmarkus.basichytools.persistence.repositories.UserRepository;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager userManager;

    private UserList onlineUserList;
    private IUserRepository userRepository;

    // Singleton
    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }

        return userManager;
    }

    private UserManager() {
        String path = ModuleInitializer.getUsersPath();
        userRepository = new UserRepository(path);
        onlineUserList = new UserList();
    }

    public User getOnlineUser(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        for (User user : onlineUserList.getList()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }

        return null;
    }

    public void getUser(String name, ResultListener<User> resultListener) {
        userRepository.get(name, resultListener);
    }

    public List<User> getAllOnlineUsers() {
        return onlineUserList.getList();
    }

    public void registerUser(String name, ResultListener<User> registerListener) {
        if (name == null || name.isEmpty()) {
            return;
        }

        userRepository.get(name, user -> {
            if (user == null) {
                final User newUser = generateDefaultUser(name);
                userRepository.add(name, newUser, aBoolean -> {
                    if (aBoolean) {     // successful save
                        addUser(newUser, registerListener);
                    }
                });
            } else {
                addUser(user, registerListener);
            }
        });
    }

    private void addUser(User user, ResultListener<User> registerListener) {
        user.setOnline(true);
        user.setValidated(true);
        onlineUserList.add(user);
        registerListener.getResultOnFinish(user);
    }

    public void unregisterUser(String name) {
        if (name == null || name.isEmpty()) {
            return;
        }

        User user = onlineUserList.getUserByName(name);
        if (user != null) {
            user.setOnline(false);
            user.setValidated(false);
            onlineUserList.remove(user);
            userRepository.set(name, user);
        } else {
            userRepository.add(name, generateDefaultUser(name));
        }
    }

    public User generateDefaultUser(String name) {
        BasicHyToolsLocation location = new BasicHyToolsLocation("spawnWorld",10.0f, 10.0f, 10.0f);
        return new User(name, "default", 1000.0, 100.0,
                false, "123.123.123.123", "2019-05-16 13:15", location, new ArrayList<>(),
                true, false, false, false);
    }
}
