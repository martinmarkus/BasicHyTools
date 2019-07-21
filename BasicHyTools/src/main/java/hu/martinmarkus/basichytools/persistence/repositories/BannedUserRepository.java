package hu.martinmarkus.basichytools.persistence.repositories;

import hu.martinmarkus.basichytools.models.containers.BannedUserContainer;
import hu.martinmarkus.basichytools.persistence.daos.BannedUserDao;
import hu.martinmarkus.basichytools.persistence.daos.IBannedUserDao;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

import java.util.List;

public class BannedUserRepository implements IBannedUserRepository {
    private IBannedUserDao bannedUserDao;

    public BannedUserRepository(String path) {
        bannedUserDao = new BannedUserDao(path);
    }


    @Override
    public void get(String valueId, ResultListener<BannedUserContainer> resultListener) {
        bannedUserDao.select(valueId, resultListener);
    }

    @Override
    public void add(String valueId, BannedUserContainer value) {
        bannedUserDao.insert(valueId, value);
    }

    @Override
    public void add(String valueId, BannedUserContainer value, ResultListener<Boolean> resultListener) {
        bannedUserDao.insert(valueId, value, resultListener);
    }

    @Override
    public void set(String valueId, BannedUserContainer newValue) {
        bannedUserDao.update(valueId, newValue);
    }

    @Override
    public void contains(BannedUserContainer value, ResultListener<Boolean> resultListener) {
        bannedUserDao.contains(value, resultListener);
    }

    @Override
    public void getAll(ResultListener<List<BannedUserContainer>> resultListener) {
        bannedUserDao.selectAll(resultListener);
    }

    @Override
    public void addAll(List<String> valueIds, List<BannedUserContainer> values) {
        bannedUserDao.insertAll(valueIds, values);
    }

    @Override
    public void setAll(List<String> valueIds, List<BannedUserContainer> values) {
        bannedUserDao.updateAll(valueIds, values);
    }
}
