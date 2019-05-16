package hu.martinmarkus.basichytools.permissionmanagement;

import hu.martinmarkus.basichytools.models.Group;

import java.util.ArrayList;
import java.util.List;

public class GroupPermissionValidator<T extends Group> extends PermissionValidator<T> {

    public GroupPermissionValidator() {
        super.permissionParts = new ArrayList<>();
    }

    @Override
    public boolean validate(Group group, String permission) {
        if (group == null || permission == null || permission.isEmpty()) {
            return false;
        }

        List<String> permissions = group.getAllPermissions();
        super.initializePermissionParts(permissions);

        String[] parts = permission.split(SEPARATOR);
        return super.doPartsMatch(parts);
    }
}
