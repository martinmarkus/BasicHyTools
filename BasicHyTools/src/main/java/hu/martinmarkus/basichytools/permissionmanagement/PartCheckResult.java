package hu.martinmarkus.basichytools.permissionmanagement;

class PartCheckResult {
    private boolean concretePermission;
    private boolean allPermissions;
    private boolean threwException;

    PartCheckResult(boolean concretePermission, boolean allPermissions, boolean threwException) {
        this.concretePermission = concretePermission;
        this.allPermissions = allPermissions;
        this.threwException = threwException;
    }

    boolean hasConcretePermission() {
        return concretePermission;
    }

    void setConcretePermission(boolean concretePermission) {
        this.concretePermission = concretePermission;
    }

    boolean hasAllPermissions() {
        return allPermissions;
    }

    void setAllPermissions(boolean allPermissions) {
        this.allPermissions = allPermissions;
    }

    boolean hasThrewException() {
        return threwException;
    }

    void setThrewException(boolean threwException) {
        this.threwException = threwException;
    }
}
