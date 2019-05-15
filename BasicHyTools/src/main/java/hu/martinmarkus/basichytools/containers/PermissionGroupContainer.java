package hu.martinmarkus.basichytools.containers;

import hu.martinmarkus.basichytools.models.PermissionGroup;

import java.util.List;

public class PermissionGroupContainer {
    private List<PermissionGroup> permissionGroups;

    public PermissionGroupContainer(List<PermissionGroup> permissionGroups) {
        this.permissionGroups = permissionGroups;
    }

    public List<PermissionGroup> getPermissionGroups() {
        return permissionGroups;
    }

    public void setPermissionGroups(List<PermissionGroup> permissionGroups) {
        this.permissionGroups = permissionGroups;
    }
}
