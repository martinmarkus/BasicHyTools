package hu.martinmarkus.basichytools.persistence.daos;

import hu.martinmarkus.basichytools.configmanaging.DefaultConfigManager;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.persistence.PersistenceMode;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class DefaultConfigDao extends Dao<DefaultConfig> implements IDefaultConfigDao {

    public DefaultConfigDao(String path) {
        super(DefaultConfig.class, path, PersistenceMode.YAML_FILE);
    }

    @Override
    public void select(String valueId, ResultListener<DefaultConfig> resultListener) {
        configReader.read(valueId, resultListener);
    }

    @Override
    public void insert(String valueId, DefaultConfig value) {
        configWriter.write(valueId, value);
    }

    @Override
    public void update(String valueId, DefaultConfig value) {
        configWriter.write(valueId, value);
    }

    @Override
    public void contains(DefaultConfig value, ResultListener<Boolean> resultListener) {
        configReader.read(DefaultConfigManager.DEFAULT_CONFIG, defaultConfig -> {
            if (defaultConfig != null) {
                if (defaultConfig == value) {
                    resultListener.getResultOnFinish(true);
                }
            }
            resultListener.getResultOnFinish(false);
        });
    }

    // The following methods are not required to implement for this project version

    @Override
    public void selectAll(ResultListener<List<DefaultConfig>> resultListener) {
        throw new NotImplementedException();
    }

    @Override
    public void insertAll(List<String> valueIds, List<DefaultConfig> values) {
        throw new NotImplementedException();
    }

    @Override
    public void updateAll(List<String> valueIds, List<DefaultConfig> values) {
        throw new NotImplementedException();
    }
}
