package hu.martinmarkus.basichytools.globalmechanisms.savemechanisms;

import hu.martinmarkus.basichytools.configmanagement.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.Informer;

import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.persistence.repositories.UserRepository;

import java.util.List;
import java.util.concurrent.*;

public class UserSaver extends ConfigSaver {

    private static UserSaver userSaver;

    private UserRepository userRepository;

    public static UserSaver getInstance() {
        if (userSaver == null) {
            userSaver = new UserSaver();
        }

        return userSaver;
    }

    private UserSaver() {
        String path = ModuleInitializer.getUsersPath();
        userRepository = new UserRepository(path);
    }

    @Override
    public void startAutoSave(int saveInterval) {
        if (isRunning) {
            return;
        }

        if (saveInterval < MIN_SAVE_INTERVAL) {
            saveInterval = MIN_SAVE_INTERVAL;
        }

        isRunning = true;
        super.executorService = Executors.newScheduledThreadPool(0);
        super.executorService.scheduleAtFixedRate(this::saveAllUsers,
                0, saveInterval, TimeUnit.SECONDS);
    }

    @Override
    public void saveNow() {
        saveAllUsers();
    }

    private void saveAllUsers() {
        List<User> onlineUsers = UserManager.getInstance().getAllOnlineUsers();
        for (User user : onlineUsers) {
            userRepository.set(user.getName(), user);
        }

        Informer.logInfo(createLogMessage("users"));
    }
}
