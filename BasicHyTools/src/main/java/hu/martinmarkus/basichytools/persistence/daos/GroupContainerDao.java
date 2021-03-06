package hu.martinmarkus.basichytools.persistence.daos;

import hu.martinmarkus.basichytools.configmanagement.GroupManager;
import hu.martinmarkus.basichytools.models.containers.GroupContainer;
import hu.martinmarkus.basichytools.persistence.PersistenceMode;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class GroupContainerDao extends Dao<GroupContainer> implements IGroupContainerDao {
    public GroupContainerDao(String path) {
        super(GroupContainer.class, path, PersistenceMode.YAML_FILE);
    }

    @Override
    public void select(String valueId, ResultListener<GroupContainer> resultListener) {
        configReader.read(valueId, resultListener);
    }

    @Override
    public void insert(String valueId, GroupContainer value) {
        configWriter.write(valueId, value);
    }

    @Override
    public void insert(String valueId, GroupContainer value, ResultListener<Boolean> resultListener) {
        configWriter.write(valueId, value, resultListener);
    }

    @Override
    public void update(String valueId, GroupContainer value) {
        configWriter.write(valueId, value);
    }

    @Override
    public void contains(GroupContainer value, ResultListener<Boolean> resultListener) {
        configReader.read(GroupManager.GROUPS_CONFIG, groupContainer -> {
            if (groupContainer != null) {
                if (groupContainer == value) {
                    resultListener.getResultOnFinish(true);
                }
            }
            resultListener.getResultOnFinish(false);
        });
    }

    // The following methods are not required to implement for this project version

    @Override
    public void selectAll(ResultListener<List<GroupContainer>> resultListener) {
        throw new NotImplementedException();
    }

    @Override
    public void insertAll(List<String> valueIds, List<GroupContainer> values) {
        throw new NotImplementedException();
    }

    @Override
    public void updateAll(List<String> valueIds, List<GroupContainer> values) {
        throw new NotImplementedException();
    }

}
