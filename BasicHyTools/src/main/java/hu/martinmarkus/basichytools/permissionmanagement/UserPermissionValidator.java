package hu.martinmarkus.basichytools.permissionmanagement;

import hu.martinmarkus.basichytools.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserPermissionValidator implements PermissionValidator {
    private final String SEPARATOR = "\\.";
    private final String ALL_PERMISSIONS = "*";

    private List<String[]> permissionParts;

    public UserPermissionValidator() {
        permissionParts = new ArrayList<>();
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

    private void initializePermissionParts(List<String> permissions) {
        for (String permission : permissions) {
            String[] singleParts = permission.split(SEPARATOR);
            permissionParts.add(singleParts);
        }
    }

    private boolean doPartsMatch(String[] parts) {
        PartCheckResult partCheckResult = new PartCheckResult(false, false, false);
        for (String[] singleParts : permissionParts) {
            for (int i = 0; i < parts.length; i++) {
                partCheckResult = compareSingleParts(parts[i], singleParts[i]);

                if (partCheckResult.hasAllPermissions()) {
                    return true;
                }
                if (!partCheckResult.hasConcretePermission()
                        || partCheckResult.hasThrewException()) {
                    break;
                }
            }

            if (partCheckResult.hasConcretePermission()
                    && !partCheckResult.hasThrewException()) {
                return true;
            }
        }

        return false;
    }

    private PartCheckResult compareSingleParts(String ownedPart, String inspectedPart) {
        PartCheckResult result = new PartCheckResult(false, false, false);

        try {
            if (!ownedPart.equalsIgnoreCase(inspectedPart)) {
                if (inspectedPart.equalsIgnoreCase(ALL_PERMISSIONS)) {
                    result.setAllPermissions(true);
                }
            } else {
                result.setConcretePermission(true);
            }
        } catch (IndexOutOfBoundsException ignored) {
            result.setThrewException(true);
        }

        return result;
    }
}
