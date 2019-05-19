package hu.martinmarkus.basichytools.persistence.repositories;

import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.persistence.daos.DefaultConfigDao;
import hu.martinmarkus.basichytools.persistence.daos.IDefaultConfigDao;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

import java.util.List;

public class DefaultConfigRepository implements IDefaultConfigRepository {

    private IDefaultConfigDao defaultConfigDao;

    public DefaultConfigRepository(String path) {
        defaultConfigDao = new DefaultConfigDao(path);
    }

    @Override
    public void get(String valueId, ResultListener<DefaultConfig> resultListener) {
        defaultConfigDao.select(valueId, resultListener);
    }

    @Override
    public void add(String valueId, DefaultConfig value) {
        defaultConfigDao.insert(valueId, value);
    }

    @Override
    public void set(String valueId, DefaultConfig newValue) {
        defaultConfigDao.update(valueId, newValue);
    }

    @Override
    public void contains(DefaultConfig value, ResultListener<Boolean> resultListener) {
        defaultConfigDao.contains(value, resultListener);
    }

    @Override
    public void getAll(ResultListener<List<DefaultConfig>> resultListener) {
        defaultConfigDao.selectAll(resultListener);
    }

    @Override
    public void addAll(List<String> valueIds, List<DefaultConfig> values) {
        defaultConfigDao.insertAll(valueIds, values);
    }

    @Override
    public void setAll(List<String> valueIds, List<DefaultConfig> values) {
        defaultConfigDao.updateAll(valueIds, values);
    }
}
