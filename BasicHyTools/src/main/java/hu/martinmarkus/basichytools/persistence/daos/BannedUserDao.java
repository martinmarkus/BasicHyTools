package hu.martinmarkus.basichytools.persistence.daos;

import hu.martinmarkus.basichytools.configmanagement.BannedUserManager;
import hu.martinmarkus.basichytools.models.containers.BannedUserContainer;
import hu.martinmarkus.basichytools.persistence.PersistenceMode;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class BannedUserDao extends Dao<BannedUserContainer> implements IBannedUserDao {

    public BannedUserDao(String path) {
        super(BannedUserContainer.class, path, PersistenceMode.YAML_FILE);
    }


    @Override
    public void select(String valueId, ResultListener<BannedUserContainer> resultListener) {
        configReader.read(valueId, resultListener);
    }

    @Override
    public void insert(String valueId, BannedUserContainer value) {
        configWriter.write(valueId, value);
    }

    @Override
    public void update(String valueId, BannedUserContainer value) {
        configWriter.write(valueId, value);
    }

    @Override
    public void contains(BannedUserContainer value, ResultListener<Boolean> resultListener) {
        configReader.read(BannedUserManager.BANNED_USER_CONFIG, bannedUserContainer -> {
            if (bannedUserContainer != null) {
                if (bannedUserContainer == value) {
                    resultListener.getResultOnFinish(true);
                }
            }
            resultListener.getResultOnFinish(false);
        });
    }

    // The following methods are not required to implement for this project version

    @Override
    public void selectAll(ResultListener<List<BannedUserContainer>> resultListener) {
        throw new NotImplementedException();
    }

    @Override
    public void insertAll(List<String> valueIds, List<BannedUserContainer> values) {
        throw new NotImplementedException();
    }

    @Override
    public void updateAll(List<String> valueIds, List<BannedUserContainer> values) {
        throw new NotImplementedException();
    }

    @Override
    public void insert(String valueId, BannedUserContainer value, ResultListener<Boolean> resultListener) {
        throw new NotImplementedException();
    }
}
