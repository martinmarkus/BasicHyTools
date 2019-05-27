package hu.martinmarkus.basichytools.configmanagement;

import hu.martinmarkus.basichytools.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.initializers.iocfactories.IObjectFactory;
import hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories.UserFactory;
import hu.martinmarkus.basichytools.utils.DateTimeUtil;
import hu.martinmarkus.basichytools.models.containers.UserList;
import hu.martinmarkus.basichytools.models.BasicHyToolsLocation;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.persistence.repositories.IUserRepository;
import hu.martinmarkus.basichytools.persistence.repositories.UserRepository;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

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

    public synchronized User getOnlineUser(String name) {
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

    public synchronized List<User> getAllOnlineUsers() {
        return onlineUserList.getList();
    }

    public synchronized boolean isUserOnlineByName(String userName) {
        for (User user : onlineUserList.getList()) {
            if (user.getName().equalsIgnoreCase(userName)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void registerUser(String name, ResultListener<User> registerListener) {
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
                String loginIp = getLoginIp(user.getName());
                user.setLoginIp(loginIp);
                addUser(user, registerListener);
            }
        });
    }

    private synchronized void addUser(User user, ResultListener<User> registerListener) {
        onlineUserList.add(user);
        user.setValidated(true);
        user.setOnline(true);
        registerListener.getResultOnFinish(user);
    }

    public synchronized void unregisterUser(String name) {
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

    private String getLoginIp(String userName) {
        String loginIp = "123.123.123.123";

        // TODO: obtain the login ip of userName's User

        return loginIp;
    }

    public void set(String name, User user) {
        userRepository.set(name, user);
    }

    // TODO: delete on release, doesn't require IoC solution
    public User generateDefaultUser(String name) {
        String group = DefaultConfigManager.getInstance().getDefaultConfig().getDefaultGroup();
        BasicHyToolsLocation location = DefaultConfigManager.getInstance().getDefaultConfig().getSpawnLocation();
        IObjectFactory<User> userFactory = new UserFactory();
        User newUser = userFactory.getBean("newUser");

        newUser.setName(name);
        newUser.setPermissionGroupName(group);
        newUser.setLocation(location);
        newUser.setLoginTime(DateTimeUtil.getActualDate());

        return newUser;
    }
}
