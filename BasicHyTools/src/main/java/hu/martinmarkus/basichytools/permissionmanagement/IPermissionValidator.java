package hu.martinmarkus.basichytools.permissionmanagement;

import java.util.ArrayList;
import java.util.List;

public abstract class IPermissionValidator<T> {
    final String SEPARATOR = "\\.";
    final String ALL_PERMISSIONS = "*";

    List<String[]> permissionParts;

    public abstract boolean validate(T object, String permission);

    IPermissionValidator() {
        permissionParts = new ArrayList<>();
    }

    void initializePermissionParts(List<String> permissions) {
        for (String permission : permissions) {
            String[] singleParts = permission.split(SEPARATOR);
            permissionParts.add(singleParts);
        }
    }

    boolean doPartsMatch(String[] parts) {
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
