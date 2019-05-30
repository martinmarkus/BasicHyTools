package hu.martinmarkus.basichytools.configmanagement.configsavers;

import hu.martinmarkus.basichytools.configmanagement.BannedUserManager;
import hu.martinmarkus.basichytools.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.models.containers.BannedUserContainer;
import hu.martinmarkus.basichytools.persistence.repositories.BannedUserRepository;
import hu.martinmarkus.basichytools.utils.Informer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        String path = ModuleInitializer.getRootPath();
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
        String bannedUserConfig = BannedUserManager.BANNED_USER_CONFIG;

        // BUG: if the generated file is empty, it doesnt serialize correctly
        if (bannedUserContainer.getBannedUsers().size() == 0) {
            String rootPath = ModuleInitializer.getRootPath().concat("\\").concat(bannedUserConfig).concat(".yml");
            try {
                Files.deleteIfExists(Paths.get(rootPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bannedUserRepository.set(bannedUserConfig, bannedUserContainer);


        Informer.logInfo(createLogMessage(bannedUserConfig));
    }
}
