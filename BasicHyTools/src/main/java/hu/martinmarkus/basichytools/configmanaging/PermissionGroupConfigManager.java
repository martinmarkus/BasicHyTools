package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.basichytools.models.PermissionGroup;
import hu.martinmarkus.basichytools.containers.PermissionGroupContainer;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configreaders.ConfigReader;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configreaders.YamlConfigReader;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configwriters.ConfigWriter;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configwriters.YamlConfigWriter;

import java.util.ArrayList;
import java.util.List;

public class PermissionGroupConfigManager {
    private static PermissionGroupConfigManager permissionGroupConfigManager = getInstance();

    private List<PermissionGroup> permissionGroupList;

    // Singleton
    public static PermissionGroupConfigManager getInstance() {
        if (permissionGroupConfigManager == null) {
            permissionGroupConfigManager = new PermissionGroupConfigManager();
        }

        return permissionGroupConfigManager;
    }

    private PermissionGroupConfigManager() {
        permissionGroupList = new ArrayList<>();
        initPermissionGroupsFromFile();
    }

    public PermissionGroup getPermissionGroup(String name) {
        for (PermissionGroup permissionGroup : permissionGroupList) {
            if (permissionGroup.getName().equalsIgnoreCase(name)) {
                return permissionGroup;
            }
        }
        return null;
    }

    public List<PermissionGroup> getPermissionGroups() {
        return permissionGroupList;
    }

    private void initPermissionGroupsFromFile() {
        Integer notifier = 0;
        ConfigReader<PermissionGroupContainer> configReader =
                new YamlConfigReader<>(PermissionGroupContainer.class);

        configReader.read("permissionGroups", permissionGroupContainer -> {
            if (permissionGroupContainer == null) {
                generateDefaultPermissionGroups();
                return;
            }

            permissionGroupList = permissionGroupContainer.getPermissionGroups();
            synchronized(notifier) {
                notifier.notify();
            }
        });

        synchronized(notifier) {
            try {
                notifier.wait();
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void generateDefaultPermissionGroups() {
        if (permissionGroupList != null && !permissionGroupList.isEmpty()) {
            return;
        }

        PermissionGroupContainerGenerator generator = new PermissionGroupContainerGenerator();
        PermissionGroupContainer groupContainer = generator.generateDefaultContainer();

        ConfigWriter<PermissionGroupContainer> configWriter = new YamlConfigWriter<>(PermissionGroupContainer.class);
        configWriter.write("permissionGroups", groupContainer);
    }
}
