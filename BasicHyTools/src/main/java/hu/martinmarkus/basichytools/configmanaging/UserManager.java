package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.basichytools.models.BasicHyToolsLocation;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.persistence.repositories.IUserRepository;
import hu.martinmarkus.basichytools.persistence.repositories.UserRepository;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static UserManager userManager = getInstance();

    private List<User> onlineUserList;
    private IUserRepository userRepository;

    public User generateMockUser() {
        BasicHyToolsLocation location = new BasicHyToolsLocation("spawnWorld",10.0f, 10.0f, 10.0f);
        return new User("mockUser12345", "owner", 1000.0, 100.0,
                true, "123.123.123.123", "2019-05-16 13:15", location, new ArrayList<>(),
                true, false, false, false);
    }

    // Singleton
    public static UserManager getInstance() {
        if (userManager == null) {
            userManager = new UserManager();
        }

        return userManager;
    }

    private UserManager() {
        onlineUserList = new ArrayList<>();
    }

    public User getOnlineUser(String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        for(User user : onlineUserList) {
            if (user.getName().equals(name)) {
                return user;
            }
        }

        return null;
    }

    public void getUser(String name, ResultListener<User> resultListener) {
        String path = HyToolsInitializer.getUsersPath();
        userRepository = new UserRepository(path);
        userRepository.get(name, resultListener);
    }

    public List<User> getOnlineUsers() {
        return onlineUserList;
    }

    public void registerUser(User user) {
        if (user != null && user.isOnline() && !onlineUserList.contains(user)) {
            onlineUserList.add(user);
        }
    }

    public void unregisterUser(User user) {
        onlineUserList.remove(user);
    }
}
