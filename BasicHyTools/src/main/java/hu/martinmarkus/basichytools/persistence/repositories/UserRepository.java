package hu.martinmarkus.basichytools.persistence.repositories;

import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.persistence.daos.IUserDao;
import hu.martinmarkus.basichytools.persistence.daos.UserDao;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

import java.util.List;

public class UserRepository implements IUserRepository {
    private IUserDao userDao;

    public UserRepository(String path) {
        userDao = new UserDao(path);
    }

    @Override
    public void get(String valueId, ResultListener<User> resultListener) {
        userDao.select(valueId, resultListener);
    }

    @Override
    public void add(String valueId, User value) {
        userDao.insert(valueId, value);
    }

    @Override
    public void set(String valueId, User newValue) {
        userDao.update(valueId, newValue);
    }

    @Override
    public void contains(User value, ResultListener<Boolean> resultListener) {
        userDao.contains(value, resultListener);
    }

    @Override
    public void getAll(ResultListener<List<User>> resultListener) {
        userDao.selectAll(resultListener);
    }

    @Override
    public void addAll(List<String> valueIds, List<User> values) {
        userDao.insertAll(valueIds, values);
    }

    @Override
    public void setAll(List<String> valueIds, List<User> values) {
        userDao.updateAll(valueIds, values);
    }
}
