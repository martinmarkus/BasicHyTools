package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.basichytools.containers.PermissionGroupContainer;
import hu.martinmarkus.basichytools.models.PermissionGroup;

import java.util.ArrayList;
import java.util.List;

public class PermissionGroupContainerGenerator {

    public PermissionGroupContainer generateDefaultContainer() {
        List<PermissionGroup> permissionGroupList = new ArrayList<>();

        List<String> permissions = new ArrayList<>();
        permissions.add("group.default");
        List<String> inheritances = new ArrayList<>();
        PermissionGroup group = new PermissionGroup("default", "[Default]", "", permissions, inheritances);
        permissionGroupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.builder");
        inheritances = new ArrayList<>();
        group = new PermissionGroup("builder", "[Builder]", "", permissions, inheritances);
        permissionGroupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.moderator");
        inheritances = new ArrayList<>();
        group = new PermissionGroup("moderator", "[Moderator]", "", permissions, inheritances);
        permissionGroupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.helper");
        inheritances = new ArrayList<>();
        group = new PermissionGroup("helper", "[Helper]", "", permissions, inheritances);
        permissionGroupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.admin");
        inheritances = new ArrayList<>();
        group = new PermissionGroup("admin", "[Admin]", "", permissions, inheritances);
        permissionGroupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.owner");
        inheritances = new ArrayList<>();
        group = new PermissionGroup("owner", "[Owner]", "", permissions, inheritances);
        permissionGroupList.add(group);

        return new PermissionGroupContainer(permissionGroupList);
    }
}
