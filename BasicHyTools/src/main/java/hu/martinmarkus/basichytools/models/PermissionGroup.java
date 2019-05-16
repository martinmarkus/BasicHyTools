package hu.martinmarkus.basichytools.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.martinmarkus.basichytools.configmanaging.PermissionGroupManager;

import java.util.ArrayList;
import java.util.List;

public class PermissionGroup {
    private String name;
    private String prefix;
    private String suffix;
    private List<String> permissions;
    private List<String> inheritances;

    public PermissionGroup(String name, String prefix, String suffix,
                           List<String> permissions, List<String> inheritances) {
        this.name = name;
        this.prefix = prefix;
        this.suffix = suffix;
        this.permissions = permissions;
        this.inheritances = inheritances;
    }

    @JsonIgnore
    public boolean hasPermission(String permission) {
        if (permissions.contains(permission)) {
            return true;
        }

        PermissionGroupManager configManager = PermissionGroupManager.getInstance();
        List<PermissionGroup> permissionGroupList = configManager.getPermissionGroups();

        for (String inheritance : inheritances) {
            for (PermissionGroup permissionGroup : permissionGroupList) {
                if (permissionGroup.getName().equalsIgnoreCase(inheritance)) {
                    PermissionGroup inheritanceGroup = configManager.getPermissionGroup(inheritance);
                    List<String> inheritancePermissions = inheritanceGroup.getPermissions();
                    if (inheritancePermissions.contains(permission)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @JsonIgnore
    public List<String> getAllPermissions() {
        List<String> allPermissions = new ArrayList<>(permissions);

        PermissionGroupManager configManager = PermissionGroupManager.getInstance();
        List<PermissionGroup> permissionGroupList = configManager.getPermissionGroups();

        for (String inheritance : inheritances) {
            for (PermissionGroup permissionGroup : permissionGroupList) {
                if (permissionGroup.getName().equalsIgnoreCase(inheritance)) {
                    allPermissions.addAll(permissionGroup.getPermissions());
                }
            }
        }

        return allPermissions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

    public List<String> getInheritances() {
        return inheritances;
    }

    public void setInheritances(List<String> inheritances) {
        this.inheritances = inheritances;
    }
}
