package hu.martinmarkus.basichytools.configmanagement;

import hu.martinmarkus.basichytools.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.models.BannedUser;
import hu.martinmarkus.basichytools.models.containers.BannedUserContainer;
import hu.martinmarkus.basichytools.persistence.repositories.BannedUserRepository;
import hu.martinmarkus.basichytools.persistence.repositories.IBannedUserRepository;
import hu.martinmarkus.basichytools.utils.synchronization.ISynchronizer;
import hu.martinmarkus.basichytools.utils.synchronization.Synchronizer;

import java.util.ArrayList;
import java.util.List;

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

    public List<BannedUser> getBannedUsers() {
        return bannedUserContainer.getBannedUsers();
    }

    public BannedUserContainer getBannedUserContainer() {
        return bannedUserContainer;
    }

    private BannedUserContainer generateDefaultGroupContainer() {
        List<BannedUser> bannedUsers = new ArrayList<>();
        return new BannedUserContainer(bannedUsers);
    }
}
