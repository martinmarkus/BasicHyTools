package hu.martinmarkus.basichytools.configmanagement.configsavers;

import hu.martinmarkus.basichytools.configmanagement.BannedUserManager;
import hu.martinmarkus.basichytools.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.models.containers.BannedUserContainer;
import hu.martinmarkus.basichytools.persistence.repositories.BannedUserRepository;
import hu.martinmarkus.basichytools.utils.Informer;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BannedUserSaver extends ConfigSaver {

    private static BannedUserSaver bannedUserSaver;

    private BannedUserRepository bannedUserRepository;

    public static BannedUserSaver getInstance() {
        if (bannedUserSaver == null) {
            bannedUserSaver = new BannedUserSaver();
        }

        return bannedUserSaver;
    }

    private BannedUserSaver() {
        String path = ModuleInitializer.getUsersPath();
        bannedUserRepository = new BannedUserRepository(path);
    }

    @Override
    public void startAutoSave() {
        if (isRunning) {
            return;
        }

        if (saveInterval < MIN_SAVE_INTERVAL) {
            saveInterval = MIN_SAVE_INTERVAL;
        }

        isRunning = true;
        super.executorService = Executors.newScheduledThreadPool(0);
        super.executorService.scheduleAtFixedRate(this::saveBannedUsers,
                INITIAL_DELAY, saveInterval, TimeUnit.SECONDS);
    }

    @Override
    public void saveNow() {
        saveBannedUsers();
    }

    private synchronized void saveBannedUsers() {
        BannedUserContainer bannedUserContainer = BannedUserManager.getInstance().getBannedUserContainer();
        bannedUserRepository.set(BannedUserManager.BANNED_USER_CONFIG, bannedUserContainer);

        Informer.logInfo(createLogMessage(BannedUserManager.BANNED_USER_CONFIG));
    }
}
