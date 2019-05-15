package hu.martinmarkus.basichytools.configmanagers;

import hu.martinmarkus.basichytools.models.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class UserConfigManager {
    private static UserConfigManager userConfigManager;

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
        User user = getOnlineUser(name);

        if (user == null) {
            // TODO: search for user in files
            throw new NotImplementedException();
        }

        return user;
    }

    public List<User> getOnlineUsers() {
        return onlineUserList;
    }

    public void registerUser(User user) {
        if (!onlineUserList.contains(user)) {
            onlineUserList.add(user);
        }
    }

    public void unregisterUser(User user) {
        onlineUserList.remove(user);
    }
}
