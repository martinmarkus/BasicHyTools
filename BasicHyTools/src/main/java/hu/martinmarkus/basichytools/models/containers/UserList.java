package hu.martinmarkus.basichytools.models.containers;

import hu.martinmarkus.basichytools.models.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserList {
    private List<User> users;

    public UserList() {
        users = new ArrayList<>();
    }

    public List<User> getList() {
        return users;
    }

    public User getUserByName(String name) {
        for(User user : users) {
            if (user.getName().equalsIgnoreCase(name)) {
                return user;
            }
        }
        return null;
    }

    public boolean contains(User user) {
        for (User aUser : users) {
            if (user.getName().equalsIgnoreCase(aUser.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(String name) {
        for (User aUser : users) {
            if (name.equalsIgnoreCase(aUser.getName())) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return users.size();
    }

    public User get(int i) {
        return users.get(i);
    }

    public void set(int i, User user) {
        users.set(i, user);
    }

    public int indexOf(User user) {
        return users.indexOf(user);
    }

    public boolean isEmpty() {
        return users.isEmpty();
    }

    public Iterator<User> iterator() {
        return users.iterator();
    }

    public void add(User user) {
        users.add(user);
    }

    public void remove(User user) {
        users.remove(user);
    }

    public void clear() {
        users.clear();
    }

    public void removeAll(List<User> userList) {
        users.removeAll(userList);
    }

    public void addAll(List<User> userList) {
        users.addAll(userList);
    }


}
