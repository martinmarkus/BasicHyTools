package hu.martinmarkus.basichytools.permissionmanagement;

import hu.martinmarkus.basichytools.models.User;

public interface PermissionValidator {
    boolean validate(User user, String permission);
}
