package hu.martinmarkus.basichytools.persistence.repositories;

import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.persistence.daos.ILanguageConfigDao;
import hu.martinmarkus.basichytools.persistence.daos.LanguageConfigDao;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

import java.util.List;

public class LanguageConfigRepository implements ILanguageConfigRepository {

    ILanguageConfigDao languageConfigDao;

    public LanguageConfigRepository(String path) {
        languageConfigDao = new LanguageConfigDao(path);
    }

    @Override
    public void get(String valueId, ResultListener<LanguageConfig> resultListener) {
        languageConfigDao.select(valueId, resultListener);
    }

    @Override
    public void add(String valueId, LanguageConfig value) {
        languageConfigDao.insert(valueId, value);
    }

    @Override
    public void add(String valueId, LanguageConfig value, ResultListener<Boolean> resultListener) {
        languageConfigDao.insert(valueId, value, resultListener);
    }

    @Override
    public void set(String valueId, LanguageConfig newValue) {
        languageConfigDao.update(valueId, newValue);
    }

    @Override
    public void contains(LanguageConfig value, ResultListener<Boolean> resultListener) {
        languageConfigDao.contains(value, resultListener);
    }

    @Override
    public void getAll(ResultListener<List<LanguageConfig>> resultListener) {
        languageConfigDao.selectAll(resultListener);
    }

    @Override
    public void addAll(List<String> valueIds, List<LanguageConfig> values) {
        languageConfigDao.insertAll(valueIds, values);
    }

    @Override
    public void setAll(List<String> valueIds, List<LanguageConfig> values) {
        languageConfigDao.insertAll(valueIds, values);
    }
}
