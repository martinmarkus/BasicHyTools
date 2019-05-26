package hu.martinmarkus.basichytools.configmanagement.managers;

import hu.martinmarkus.basichytools.configmanagement.initializers.ioc.FunctionParameterFactory;
import hu.martinmarkus.basichytools.configmanagement.initializers.ModuleInitializer;
import hu.martinmarkus.basichytools.configmanagement.initializers.ioc.GameFunctionFactory;
import hu.martinmarkus.basichytools.configmanagement.initializers.ioc.IFunctionParameterFactory;
import hu.martinmarkus.basichytools.configmanagement.initializers.ioc.IGameFunctionFactory;
import hu.martinmarkus.basichytools.models.FunctionParameter;
import hu.martinmarkus.basichytools.models.containers.FunctionParameterContainer;
import hu.martinmarkus.basichytools.persistence.repositories.FunctionParameterContainerRepository;
import hu.martinmarkus.basichytools.utils.synchronization.ISynchronizer;
import hu.martinmarkus.basichytools.utils.synchronization.Synchronizer;

import java.util.ArrayList;
import java.util.List;

public class FunctionParameterManager {

    public static final String FUNCTION_PARAMETERS = "functionParameters";
    private static FunctionParameterManager functionParameterManager;

    private List<FunctionParameter> functionParameterList;
    private FunctionParameterContainerRepository functionParameterContainerRepository;

    public static FunctionParameterManager getInstance() {
        if (functionParameterManager == null) {
            functionParameterManager = new FunctionParameterManager();
        }

        return functionParameterManager;
    }

    public FunctionParameter getByName(String functionName) {
        for (FunctionParameter parameter : functionParameterList) {
            if (parameter.getName().equalsIgnoreCase(functionName)) {
                return parameter;
            }
        }

        return null;
    }

    private FunctionParameterManager() {
        String path = ModuleInitializer.getRootPath();
        functionParameterContainerRepository = new FunctionParameterContainerRepository(path);
        initFunctionParameterContainerFromFile();
    }

    private void initFunctionParameterContainerFromFile() {
        ISynchronizer synchronizer = new Synchronizer();
        functionParameterContainerRepository.get(FUNCTION_PARAMETERS, functionParameterContainer -> {
            if (functionParameterContainer == null) {
                initFunctionParameterContainerContainer();
                synchronizer.continueRun();
                return;
            }

            functionParameterList = functionParameterContainer.getFunctionParameters();
            synchronizer.continueRun();
        });

        synchronizer.waitRun();
    }

    private void initFunctionParameterContainerContainer() {
        if (functionParameterList != null && !functionParameterList.isEmpty()) {
            return;
        }

        ISynchronizer synchronizer = new Synchronizer();
        FunctionParameterContainer container = generateDefaultFunctionParameterContainer();
        functionParameterList = container.getFunctionParameters();

        functionParameterContainerRepository.add(FUNCTION_PARAMETERS,
                container, aBoolean -> synchronizer.continueRun());

        synchronizer.waitRun();
    }

    public List<FunctionParameter> getAlLFunctionParameters() {
        return functionParameterList;
    }

    private FunctionParameterContainer generateDefaultFunctionParameterContainer() {
        List<FunctionParameter> functionParameters = new ArrayList<>();
        IFunctionParameterFactory functionParameterFactory = FunctionParameterFactory.getInstance();

        functionParameters.add(functionParameterFactory.getBean("Me"));
        functionParameters.add(functionParameterFactory.getBean("Broadcast"));

        return new FunctionParameterContainer(functionParameters);
    }
}
