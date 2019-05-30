package hu.martinmarkus.basichytools.models.containers;

import hu.martinmarkus.basichytools.models.BannedUser;

import java.util.List;
import java.util.ListIterator;

public class BannedUserContainer {
    private List<BannedUser> bannedUsers;

    public BannedUserContainer(List<BannedUser> bannedUsers) {
        this.bannedUsers = bannedUsers;
    }

    public List<BannedUser> getBannedUsers() {
        return bannedUsers;
    }

    public void setBannedUsers(List<BannedUser> bannedUsers) {
        this.bannedUsers = bannedUsers;
    }

}
