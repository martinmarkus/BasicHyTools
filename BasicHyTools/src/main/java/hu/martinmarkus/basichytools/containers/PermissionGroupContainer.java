package hu.martinmarkus.basichytools.containers;

import hu.martinmarkus.basichytools.models.Group;

import java.util.List;

public class PermissionGroupContainer {
    private List<Group> groups;

    public PermissionGroupContainer(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
