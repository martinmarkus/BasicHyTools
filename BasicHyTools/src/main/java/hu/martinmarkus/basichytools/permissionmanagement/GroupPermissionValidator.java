package hu.martinmarkus.basichytools.permissionmanagement;

import hu.martinmarkus.basichytools.models.PermissionGroup;

import java.util.ArrayList;
import java.util.List;

public class GroupPermissionValidator<T extends PermissionGroup> extends PermissionValidator<T> {

    public GroupPermissionValidator() {
        super.permissionParts = new ArrayList<>();
    }

    @Override
    public boolean validate(PermissionGroup permissionGroup, String permission) {
        if (permissionGroup == null || permission == null || permission.isEmpty()) {
            return false;
        }

        List<String> permissions = permissionGroup.getAllPermissions();
        super.initializePermissionParts(permissions);

        String[] parts = permission.split(SEPARATOR);
        return super.doPartsMatch(parts);
    }
}
