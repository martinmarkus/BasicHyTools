package hu.martinmarkus.basichytools.models.containers;

import hu.martinmarkus.basichytools.models.Group;

import java.util.List;

public class GroupContainer {
    private List<Group> groups;

    public GroupContainer(List<Group> groups) {
        this.groups = groups;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
