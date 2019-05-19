package hu.martinmarkus.basichytools.persistence.daos;

import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.persistence.PersistenceMode;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class LanguageConfigDao extends Dao<LanguageConfig> implements ILanguageConfigDao {

    public LanguageConfigDao(String path) {
        super(LanguageConfig.class, path, PersistenceMode.YAML_FILE);
    }

    @Override
    public void select(String valueId, ResultListener<LanguageConfig> resultListener) {
        configReader.read(valueId, resultListener);
    }

    @Override
    public void insert(String valueId, LanguageConfig value) {
        configWriter.write(valueId, value);
    }

    @Override
    public void update(String valueId, LanguageConfig value) {
        configWriter.write(valueId, value);
    }

    @Override
    public void contains(LanguageConfig value, ResultListener<Boolean> resultListener) {
        configReader.read(LanguageConfigManager.LANGUAGE_CONFIG, languageConfig -> {
            if (languageConfig != null) {
                if (languageConfig == value) {
                    resultListener.getResultOnFinish(true);
                }
            }
            resultListener.getResultOnFinish(false);
        });
    }

    // The following methods are not required to implement for this project version

    @Override
    public void selectAll(ResultListener<List<LanguageConfig>> resultListener) {
        throw new NotImplementedException();
    }

    @Override
    public void insertAll(List<String> valueIds, List<LanguageConfig> values) {
        throw new NotImplementedException();
    }

    @Override
    public void updateAll(List<String> valueIds, List<LanguageConfig> values) {
        throw new NotImplementedException();
    }
}
