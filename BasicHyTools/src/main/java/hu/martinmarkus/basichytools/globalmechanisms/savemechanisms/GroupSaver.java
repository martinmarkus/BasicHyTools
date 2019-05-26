package hu.martinmarkus.basichytools.globalmechanisms.savemechanisms;

import hu.martinmarkus.basichytools.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.configmanagement.GroupManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.Informer;
import hu.martinmarkus.basichytools.models.containers.GroupContainer;
import hu.martinmarkus.basichytools.persistence.repositories.GroupContainerRepository;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GroupSaver extends ConfigSaver {

    private static GroupSaver groupSaver;

    private GroupContainerRepository groupContainerRepository;

    public static GroupSaver getInstance() {
        if (groupSaver == null) {
            groupSaver = new GroupSaver();
        }

        return groupSaver;
    }

    private GroupSaver() {
        String path = ModuleInitializer.getRootPath();
        groupContainerRepository = new GroupContainerRepository(path);
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
        super.executorService.scheduleAtFixedRate(this::saveAllGroups,
                INITIAL_DELAY, saveInterval, TimeUnit.SECONDS);
    }

    @Override
    public void saveNow() {
        saveAllGroups();
    }

    private void saveAllGroups() {
        GroupContainer groupContainer = GroupManager.getInstance().getGroupContainer();
        groupContainerRepository.set(GroupManager.GROUPS_CONFIG, groupContainer);

        Informer.logInfo(createLogMessage(GroupManager.GROUPS_CONFIG));
    }
}
