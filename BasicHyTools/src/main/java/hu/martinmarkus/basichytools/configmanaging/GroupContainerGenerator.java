package hu.martinmarkus.basichytools.configmanaging;

import hu.martinmarkus.basichytools.containers.GroupContainer;
import hu.martinmarkus.basichytools.models.Group;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GroupContainerGenerator {

    public GroupContainer generateDefaultContainer() {
        List<Group> groupList = new ArrayList<>();

        List<String> permissions = new ArrayList<>();
        permissions.add("group.default");
        List<String> inheritances = new ArrayList<>();
        Group group = new Group("default", "[Default]", "", permissions, inheritances);
        groupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.builder");
        inheritances = new ArrayList<>(Arrays.asList("default"));
        group = new Group("builder", "[Builder]", "", permissions, inheritances);
        groupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.moderator");
        inheritances = new ArrayList<>(Arrays.asList("default", "builder"));
        group = new Group("moderator", "[Moderator]", "", permissions, inheritances);
        groupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.helper");
        inheritances = new ArrayList<>(Arrays.asList("default", "builder", "moderator"));
        group = new Group("helper", "[Helper]", "", permissions, inheritances);
        groupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.admin");
        inheritances = new ArrayList<>(Arrays.asList("default", "builder", "moderator", "helper"));
        group = new Group("admin", "[Admin]", "", permissions, inheritances);
        groupList.add(group);

        permissions = new ArrayList<>();
        permissions.add("group.owner");
        inheritances = new ArrayList<>(Arrays.asList("default", "builder", "moderator", "helper", "admin"));
        group = new Group("owner", "[Owner]", "", permissions, inheritances);
        groupList.add(group);

        return new GroupContainer(groupList);
    }
}
