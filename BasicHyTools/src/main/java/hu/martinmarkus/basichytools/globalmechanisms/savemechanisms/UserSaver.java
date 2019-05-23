package hu.martinmarkus.basichytools.globalmechanisms.savemechanisms;

import hu.martinmarkus.basichytools.configmanagement.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.Informer;

import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.persistence.repositories.UserRepository;

import java.util.ArrayList;
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
    public void startAutoSave() {
        if (isRunning) {
            return;
        }

        if (saveInterval < MIN_SAVE_INTERVAL) {
            saveInterval = MIN_SAVE_INTERVAL;
        }

        isRunning = true;
        super.executorService = Executors.newScheduledThreadPool(0);
        super.executorService.scheduleAtFixedRate(this::saveAllUsers,
                INITIAL_DELAY, saveInterval, TimeUnit.SECONDS);
    }

    @Override
    public void saveNow() {
        saveAllUsers();
    }

    private void saveAllUsers() {

        List<User> onlineUsers = UserManager.getInstance().getAllOnlineUsers();
        List<String> valueIds = new ArrayList<>();

        for (User user : onlineUsers) {
            valueIds.add(user.getName());
        }

        userRepository.setAll(valueIds, onlineUsers);
        Informer.logInfo(createLogMessage("users"));
    }
}
