package hu.martinmarkus.basichytools.configmanagement.managers;

import hu.martinmarkus.basichytools.configmanagement.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.models.Group;
import hu.martinmarkus.basichytools.models.containers.GroupContainer;
import hu.martinmarkus.basichytools.persistence.repositories.GroupContainerRepository;
import hu.martinmarkus.basichytools.persistence.repositories.IGroupContainerRepository;
import hu.martinmarkus.basichytools.synchronization.ISynchronizer;
import hu.martinmarkus.basichytools.synchronization.Synchronizer;

import java.util.ArrayList;
import java.util.Arrays;
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

        List<String> permissions = new ArrayList<>();
        permissions.add("group.default");
        permissions.add("hytools.basic.*");
        List<String> inheritances = new ArrayList<>();
        Group group = new Group("default", "[Default]", "", permissions, inheritances);
        groupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.builder");
        inheritances = new ArrayList<>(Arrays.asList("default"));
        group = new Group("builder", "[Builder]", "", permissions, inheritances);
        groupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.moderator");
        permissions.add("hytools.bypass.*");
        inheritances = new ArrayList<>(Arrays.asList("default", "builder"));
        group = new Group("moderator", "[Moderator]", "", permissions, inheritances);
        groupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.helper");
        inheritances = new ArrayList<>(Arrays.asList("default", "builder", "moderator"));
        group = new Group("helper", "[Helper]", "", permissions, inheritances);
        groupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.admin");
        inheritances = new ArrayList<>(Arrays.asList("default", "builder", "moderator", "helper"));
        group = new Group("admin", "[Admin]", "", permissions, inheritances);
        groupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.owner");
        inheritances = new ArrayList<>(Arrays.asList("default", "builder", "moderator", "helper", "admin"));
        group = new Group("owner", "[Owner]", "", permissions, inheritances);
        groupList.add(group);

        return new GroupContainer(groupList);
    }
}
