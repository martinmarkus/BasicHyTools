package hu.martinmarkus.basichytools.persistence.repositories;

import hu.martinmarkus.basichytools.containers.GroupContainer;
import hu.martinmarkus.basichytools.persistence.daos.GroupContainerDao;
import hu.martinmarkus.basichytools.persistence.daos.IGroupContainerDao;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

import java.util.List;

public class GroupContainerRepository implements IGroupContainerRepository {
    private IGroupContainerDao groupContainerDao;

    public GroupContainerRepository(String path) {
        groupContainerDao = new GroupContainerDao(path);
    }

    @Override
    public void get(String valueId, ResultListener<GroupContainer> resultListener) {
        groupContainerDao.select(valueId, resultListener);
    }

    @Override
    public void add(String valueId, GroupContainer value) {
        groupContainerDao.insert(valueId, value);
    }

    @Override
    public void set(String valueId, GroupContainer newValue) {
        groupContainerDao.update(valueId, newValue);
    }

    @Override
    public void contains(GroupContainer value, ResultListener<Boolean> resultListener) {
        groupContainerDao.contains(value, resultListener);
    }

    @Override
    public void getAll(ResultListener<List<GroupContainer>> resultListener) {
        groupContainerDao.selectAll(resultListener);
    }

    @Override
    public void addAll(List<String> valueIds, List<GroupContainer> values) {
        groupContainerDao.insertAll(valueIds, values);
    }

    @Override
    public void setAll(List<String> valueIds, List<GroupContainer> values) {
        groupContainerDao.updateAll(valueIds, values);
    }
}
