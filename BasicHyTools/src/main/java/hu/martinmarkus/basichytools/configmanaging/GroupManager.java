package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.basichytools.models.Group;
import hu.martinmarkus.basichytools.containers.GroupContainer;
import hu.martinmarkus.basichytools.persistence.repositories.GroupContainerRepository;
import hu.martinmarkus.basichytools.persistence.repositories.IGroupContainerRepository;
import hu.martinmarkus.basichytools.synchronization.Synchronizer;
import java.util.ArrayList;
import java.util.List;

public class GroupManager {
    private static GroupManager groupManager = getInstance();

    private List<Group> groupList;

    private IGroupContainerRepository groupContainerRepository;

    // Singleton
    public static GroupManager getInstance() {
        if (groupManager == null) {
            groupManager = new GroupManager();
        }

        return groupManager;
    }

    private GroupManager() {
        groupList = new ArrayList<>();
        initPermissionGroupsFromFile();
    }

    public Group getPermissionGroup(String name) {
        for (Group group : groupList) {
            if (group.getName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    public List<Group> getPermissionGroups() {
        return groupList;
    }

    private void initPermissionGroupsFromFile() {
        Synchronizer synchronizer = new Synchronizer();

        String path = HyToolsInitializer.getRootPath();
        groupContainerRepository = new GroupContainerRepository(path);

        groupContainerRepository.get(GroupContainer.NAME, groupContainer -> {
            if (groupContainer == null) {
                generateDefaultPermissionGroups();
                synchronizer.continueRun();
                return;
            }

            groupList = groupContainer.getGroups();
            synchronizer.continueRun();
        });

        synchronizer.waitRun();
    }

    private void generateDefaultPermissionGroups() {
        if (groupList != null && !groupList.isEmpty()) {
            return;
        }

        GroupContainerGenerator generator = new GroupContainerGenerator();
        GroupContainer groupContainer = generator.generateDefaultContainer();
        groupList = groupContainer.getGroups();

        String path = HyToolsInitializer.getRootPath();
        groupContainerRepository = new GroupContainerRepository(path);
        groupContainerRepository.add(GroupContainer.NAME, groupContainer);
    }
}
