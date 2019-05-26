package hu.martinmarkus.basichytools.configmanagement.managers;

import hu.martinmarkus.basichytools.configmanagement.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.utils.DateTimeUtil;
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

    public boolean isUserOnlineByName(String userName) {
        for (User user : onlineUserList.getList()) {
            if (user.getName().equalsIgnoreCase(userName)) {
                return true;
            }
        }
        return false;
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
        onlineUserList.add(user);
        user.setValidated(true);
        user.setOnline(true);
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

    public void set(String name, User user) {
        userRepository.set(name, user);
    }

    // TODO: delete on release, doesn't require IoC solution
    public User generateDefaultUser(String name) {
        // TODO get the IP of 'name' named player
        String loginIp ="123.123.123.123";
        String group = "default";
        double balance = 1000.0;
        double exp = 0;
        String date = DateTimeUtil.getActualDate();
        BasicHyToolsLocation location = DefaultConfigManager.getInstance().getDefaultConfig().getSpawnLocation();

        return new User(name, group, balance, exp,
                false, loginIp, date, location, new ArrayList<>(),
                false, false, false, false, true);
    }
}
