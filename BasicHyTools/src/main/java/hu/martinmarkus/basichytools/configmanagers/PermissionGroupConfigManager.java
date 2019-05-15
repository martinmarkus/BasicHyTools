package hu.martinmarkus.basichytools.configmanagers;

import hu.martinmarkus.basichytools.models.PermissionGroup;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

public class PermissionGroupConfigManager {
    private static PermissionGroupConfigManager permissionGroupConfigManager;

    private List<PermissionGroup> permissionGroupList;

    // Singleton
    public static PermissionGroupConfigManager getInstance() {
        if (permissionGroupConfigManager == null) {
            permissionGroupConfigManager = new PermissionGroupConfigManager();
        }

        return permissionGroupConfigManager;
    }

    public PermissionGroupConfigManager() {
        permissionGroupList = new ArrayList<>();

        // TODO: read in permissiongroups.yml
    }

    private void generateDefaultPermissionGroups() {
        // TODO: write out default permission groups
        throw new NotImplementedException();
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
}
