package hu.martinmarkus.basichytools.permissionmanagement;

import hu.martinmarkus.basichytools.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserPermissionValidator<T extends User> extends IPermissionValidator<T> {

    public UserPermissionValidator() {
        super.permissionParts = new ArrayList<>();
    }

    @Override
    public boolean validate(User user, String permission) {
        if (user == null || permission == null || permission.isEmpty()) {
            return false;
        }

        List<String> permissions = user.getAllPermissions();
        initializePermissionParts(permissions);

        String[] parts = permission.split(SEPARATOR);
        return doPartsMatch(parts);
    }
}
