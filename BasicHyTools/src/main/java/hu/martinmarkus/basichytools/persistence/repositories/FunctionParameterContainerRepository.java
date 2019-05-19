package hu.martinmarkus.basichytools.persistence.repositories;

import hu.martinmarkus.basichytools.models.containers.FunctionParameterContainer;
import hu.martinmarkus.basichytools.persistence.daos.FunctionParameterContainerDao;
import hu.martinmarkus.basichytools.persistence.daos.IFunctionParameterContainerDao;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

import java.util.List;

public class FunctionParameterContainerRepository implements IFunctionParameterContainerRepository {

    private IFunctionParameterContainerDao functionParameterContainerDao;

    public FunctionParameterContainerRepository(String path) {
        functionParameterContainerDao = new FunctionParameterContainerDao(path);
    }

    @Override
    public void get(String valueId, ResultListener<FunctionParameterContainer> resultListener) {
        functionParameterContainerDao.select(valueId, resultListener);
    }

    @Override
    public void add(String valueId, FunctionParameterContainer value) {
        functionParameterContainerDao.insert(valueId, value);
    }

    @Override
    public void set(String valueId, FunctionParameterContainer newValue) {
        functionParameterContainerDao.insert(valueId, newValue);
    }

    @Override
    public void contains(FunctionParameterContainer value, ResultListener<Boolean> resultListener) {
        functionParameterContainerDao.contains(value, resultListener);
    }

    @Override
    public void getAll(ResultListener<List<FunctionParameterContainer>> resultListener) {
        functionParameterContainerDao.selectAll(resultListener);
    }

    @Override
    public void addAll(List<String> valueIds, List<FunctionParameterContainer> values) {
        functionParameterContainerDao.insertAll(valueIds, values);
    }

    @Override
    public void setAll(List<String> valueIds, List<FunctionParameterContainer> values) {
        functionParameterContainerDao.updateAll(valueIds, values);
    }
}
