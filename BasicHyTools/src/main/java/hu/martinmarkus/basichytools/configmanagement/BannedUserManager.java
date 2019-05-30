package hu.martinmarkus.basichytools.configmanagement;

import hu.martinmarkus.basichytools.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.models.BannedUser;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.models.containers.BannedUserContainer;
import hu.martinmarkus.basichytools.persistence.repositories.BannedUserRepository;
import hu.martinmarkus.basichytools.persistence.repositories.IBannedUserRepository;
import hu.martinmarkus.basichytools.utils.synchronization.ISynchronizer;
import hu.martinmarkus.basichytools.utils.synchronization.Synchronizer;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class BannedUserManager {
    public static final String BANNED_USER_CONFIG = "bannedUsers";

    private static BannedUserManager bannedUserManager;

    private IBannedUserRepository bannedUserRepository;
    private BannedUserContainer bannedUserContainer;

    public static BannedUserManager getInstance() {
        if (bannedUserManager == null) {
            bannedUserManager = new BannedUserManager();
        }

        return bannedUserManager;
    }

    private BannedUserManager() {
        String path = ModuleInitializer.getRootPath();
        bannedUserRepository = new BannedUserRepository(path);
        bannedUserContainer = new BannedUserContainer(new ArrayList<>());
        initBannedUsersFromFile();
    }

    private void initBannedUsersFromFile() {
        ISynchronizer synchronizer = new Synchronizer();

        bannedUserRepository.get(BANNED_USER_CONFIG, bannedUserContainer -> {
            if (bannedUserContainer == null) {
                initGroupContainer();
            } else {
                this.bannedUserContainer.setBannedUsers(bannedUserContainer.getBannedUsers());
            }
            synchronizer.continueRun();
        });

        synchronizer.waitRun();
    }

    private void initGroupContainer() {
        if (bannedUserContainer.getBannedUsers() != null && !bannedUserContainer.getBannedUsers().isEmpty()) {
            return;
        }

        BannedUserContainer aBannedUserContainer = generateDefaultGroupContainer();
        this.bannedUserContainer.setBannedUsers(aBannedUserContainer.getBannedUsers());
        bannedUserRepository.add(BANNED_USER_CONFIG, aBannedUserContainer);
    }

    public synchronized List<BannedUser> getBannedUsers() {
        return bannedUserContainer.getBannedUsers();
    }

    public synchronized BannedUserContainer getBannedUserContainer() {
        return bannedUserContainer;
    }

    public synchronized  BannedUser getBannedUser(String userName) {
        for (BannedUser bannedUser : bannedUserContainer.getBannedUsers()) {
            if (bannedUser.getName().equalsIgnoreCase(userName)) {
                return bannedUser;
            }
        }
        return null;
    }

    public synchronized void addBannedUser(BannedUser bannedUser) {
        for (BannedUser aBannedUser : bannedUserContainer.getBannedUsers()) {
            if (aBannedUser.getName().equalsIgnoreCase(bannedUser.getName())) {
                return;
            }
        }

        bannedUserContainer.getBannedUsers().add(bannedUser);

        User onlineUser = UserManager.getInstance().getOnlineUser(bannedUser.getName());
        if (onlineUser != null) {
            onlineUser.setBanned(true);
            return;
        }

        UserManager.getInstance().getUser(bannedUser.getName(), user -> {
            if (user != null) {
                user.setBanned(true);
                if (user.isOnline()) {
                    user.setBanned(true);
                }
            }
        });
    }

    public synchronized void removeBannedUser(String bannedUserName) {
        for (ListIterator<BannedUser> iter = bannedUserContainer.getBannedUsers().listIterator(); iter.hasNext(); ) {
            BannedUser bannedUser = iter.next();
            String name = bannedUser.getName();

            if (name.equalsIgnoreCase(bannedUserName)) {
                iter.remove();
            }
        }

        User onlineUser = UserManager.getInstance().getOnlineUser(bannedUserName);
        if (onlineUser != null) {
            onlineUser.setBanned(false);
            return;
        }

        UserManager.getInstance().getUser(bannedUserName, user -> {
            if (user != null) {
                user.setBanned(false);
                if (user.isOnline()) {
                    user.setBanned(false);
                }
            }
        });
    }

    public synchronized boolean isBanned(String userName) {
        for (BannedUser bannedUser : bannedUserContainer.getBannedUsers()) {
            if (bannedUser.getName().equalsIgnoreCase(userName)) {
                return true;
            }
        }

        return false;
    }

    public synchronized boolean isIpBanned(String userName) {
        for (BannedUser bannedUser : bannedUserContainer.getBannedUsers()) {
            if (bannedUser.getName().equalsIgnoreCase(userName)) {
                if (bannedUser.isIpBanned()) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    public synchronized boolean isTempBanned(String userName) {
        for (BannedUser bannedUser : bannedUserContainer.getBannedUsers()) {
            if (bannedUser.getName().equalsIgnoreCase(userName)) {
                if (bannedUser.getBanTimer() > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        return false;
    }

    private BannedUserContainer generateDefaultGroupContainer() {
        List<BannedUser> bannedUsers = new ArrayList<>();
        return new BannedUserContainer(bannedUsers);
    }
}
