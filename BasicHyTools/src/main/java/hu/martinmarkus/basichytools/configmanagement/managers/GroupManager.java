package hu.martinmarkus.basichytools.configmanagement.managers;

import hu.martinmarkus.basichytools.configmanagement.initializers.HyToolsInitializer;
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
        String path = HyToolsInitializer.getRootPath();
        groupContainerRepository = new GroupContainerRepository(path);
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

    public List<Group> getAllPermissionGroups() {
        return groupList;
    }

    private void initPermissionGroupsFromFile() {
        ISynchronizer synchronizer = new Synchronizer();

        groupContainerRepository.get(GROUPS_CONFIG, groupContainer -> {
            if (groupContainer == null) {
                initGroupContainer();
            } else {
                groupList = groupContainer.getGroups();
            }
            synchronizer.continueRun();
        });

        synchronizer.waitRun();
    }

    private void initGroupContainer() {
        if (groupList != null && !groupList.isEmpty()) {
            return;
        }

        GroupContainer groupContainer = generateDefaultGroupContainer();
        groupList = groupContainer.getGroups();
        groupContainerRepository.add(GROUPS_CONFIG, groupContainer);
    }

    public GroupContainer generateDefaultGroupContainer() {
        List<Group> groupList = new ArrayList<>();

        List<String> permissions = new ArrayList<>();
        permissions.add("group.default");
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
