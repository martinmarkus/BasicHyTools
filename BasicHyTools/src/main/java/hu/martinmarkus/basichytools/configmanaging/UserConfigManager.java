package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.basichytools.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserConfigManager {
    private static UserConfigManager userConfigManager = getInstance();

    private List<User> onlineUserList;

    // Singleton
    public static UserConfigManager getInstance() {
        if (userConfigManager == null) {
            userConfigManager = new UserConfigManager();
        }
        return userConfigManager;
    }

    private UserConfigManager() {
        onlineUserList = new ArrayList<>();
    }

    public User getOnlineUser(String name) {
        for(User user : onlineUserList) {
            if (user.getName().equals(name)) {
                return user;
            }
        }

        return null;
    }

    public User getUser(String name) {
        return getOnlineUser(name);
    }

    public List<User> getOnlineUsers() {
        return onlineUserList;
    }

    public void registerUser(User user) {
        if (user != null && !onlineUserList.contains(user)) {
            onlineUserList.add(user);
        }
    }

    public void unregisterUser(User user) {
        onlineUserList.remove(user);
    }
}
