package hu.martinmarkus.basichytools.persistence.daos;

import hu.martinmarkus.basichytools.configmanagement.managers.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.FunctionParameterManager;
import hu.martinmarkus.basichytools.models.containers.FunctionParameterContainer;
import hu.martinmarkus.basichytools.persistence.PersistenceMode;
import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

public class FunctionParameterContainerDao extends Dao<FunctionParameterContainer> implements IFunctionParameterContainerDao {

    public FunctionParameterContainerDao(String path) {
        super(FunctionParameterContainer.class, path, PersistenceMode.YAML_FILE);
    }

    @Override
    public void select(String valueId, ResultListener<FunctionParameterContainer> resultListener) {
        configReader.read(valueId, resultListener);
    }

    @Override
    public void insert(String valueId, FunctionParameterContainer value) {
        configWriter.write(valueId, value);
    }

    @Override
    public void update(String valueId, FunctionParameterContainer value) {
        configWriter.write(valueId, value);
    }

    @Override
    public void contains(FunctionParameterContainer value, ResultListener<Boolean> resultListener) {
        configReader.read(FunctionParameterManager.FUNCTION_PARAMETERS, functionParameterContainer -> {
            if (functionParameterContainer != null) {
                if (functionParameterContainer == value) {
                    resultListener.getResultOnFinish(true);
                }
            }
            resultListener.getResultOnFinish(false);
        });
    }

    // The following methods are not required to implement for this project version

    @Override
    public void selectAll(ResultListener<List<FunctionParameterContainer>> resultListener) {
        throw new NotImplementedException();
    }

    @Override
    public void insertAll(List<String> valueIds, List<FunctionParameterContainer> values) {
        throw new NotImplementedException();
    }

    @Override
    public void updateAll(List<String> valueIds, List<FunctionParameterContainer> values) {
        throw new NotImplementedException();
    }
}
