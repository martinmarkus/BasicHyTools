package hu.martinmarkus.basichytools.configmanagement.managers;

import hu.martinmarkus.basichytools.configmanagement.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.configmanagement.initializers.ioc.GroupFactory;
import hu.martinmarkus.basichytools.configmanagement.initializers.ioc.IGroupFactory;
import hu.martinmarkus.basichytools.models.Group;
import hu.martinmarkus.basichytools.models.containers.GroupContainer;
import hu.martinmarkus.basichytools.persistence.repositories.GroupContainerRepository;
import hu.martinmarkus.basichytools.persistence.repositories.IGroupContainerRepository;
import hu.martinmarkus.basichytools.utils.synchronization.ISynchronizer;
import hu.martinmarkus.basichytools.utils.synchronization.Synchronizer;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {
    private static GroupManager groupManager;
    public static final String GROUPS_CONFIG = "groups";

    private GroupContainer groupContainer;
    private IGroupContainerRepository groupContainerRepository;

    // Singleton
    public static GroupManager getInstance() {
        if (groupManager == null) {
            groupManager = new GroupManager();
        }

        return groupManager;
    }

    private GroupManager() {
        String path = ModuleInitializer.getRootPath();
        groupContainerRepository = new GroupContainerRepository(path);
        groupContainer = new GroupContainer(new ArrayList<>());
        initPermissionGroupsFromFile();
    }

    public Group getPermissionGroup(String name) {
        for (Group group : groupContainer.getGroups()) {
            if (group.getName().equalsIgnoreCase(name)) {
                return group;
            }
        }
        return null;
    }

    public List<Group> getAllPermissionGroups() {
        return groupContainer.getGroups();
    }

    public GroupContainer getGroupContainer() {
        return groupContainer;
    }

    private void initPermissionGroupsFromFile() {
        ISynchronizer synchronizer = new Synchronizer();

        groupContainerRepository.get(GROUPS_CONFIG, groupContainer -> {
            if (groupContainer == null) {
                initGroupContainer();
            } else {
                this.groupContainer.setGroups(groupContainer.getGroups());
            }
            synchronizer.continueRun();
        });

        synchronizer.waitRun();
    }

    private void initGroupContainer() {
        if (groupContainer.getGroups() != null && !groupContainer.getGroups().isEmpty()) {
            return;
        }

        GroupContainer aGroupContainer = generateDefaultGroupContainer();
        this.groupContainer.setGroups(aGroupContainer.getGroups());
        groupContainerRepository.add(GROUPS_CONFIG, aGroupContainer);
    }

    public GroupContainer generateDefaultGroupContainer() {
        List<Group> groupList = new ArrayList<>();

        IGroupFactory groupFactory = new GroupFactory();
        groupList.add(groupFactory.getBean("default"));
        groupList.add(groupFactory.getBean("builder"));
        groupList.add(groupFactory.getBean("moderator"));
        groupList.add(groupFactory.getBean("helper"));
        groupList.add(groupFactory.getBean("admin"));
        groupList.add(groupFactory.getBean("owner"));

        return new GroupContainer(groupList);
    }
}
