package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.basichytools.models.Group;
import hu.martinmarkus.basichytools.containers.PermissionGroupContainer;
import hu.martinmarkus.basichytools.synchronization.Synchronizer;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configreaders.ConfigReader;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configreaders.YamlConfigReader;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configwriters.ConfigWriter;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configwriters.YamlConfigWriter;

import java.util.ArrayList;
import java.util.List;

public class GroupManager {
    private static GroupManager groupManager = getInstance();

    private List<Group> groupList;

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
        ConfigReader<PermissionGroupContainer> configReader =
                new YamlConfigReader<>(PermissionGroupContainer.class, path);

        configReader.read("permissionGroups", permissionGroupContainer -> {
            if (permissionGroupContainer == null) {
                generateDefaultPermissionGroups();
                synchronizer.continueRun();
                return;
            }

            groupList = permissionGroupContainer.getGroups();
            synchronizer.continueRun();
        });

        synchronizer.waitRun();
    }

    private void generateDefaultPermissionGroups() {
        if (groupList != null && !groupList.isEmpty()) {
            return;
        }

        GroupContainerGenerator generator = new GroupContainerGenerator();
        PermissionGroupContainer groupContainer = generator.generateDefaultContainer();
        groupList = groupContainer.getGroups();

        String path = HyToolsInitializer.getRootPath();
        ConfigWriter<PermissionGroupContainer> configWriter = new YamlConfigWriter<>(
                PermissionGroupContainer.class, path);

        configWriter.write("permissionGroups", groupContainer);
    }
}
