package hu.martinmarkus.basichytools.configmanagement;

import hu.martinmarkus.basichytools.initializers.iocfactories.IObjectFactory;
import hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories.FunctionParameterFactory;
import hu.martinmarkus.basichytools.initializers.ModuleInitializer;
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
        IObjectFactory<FunctionParameter> functionParameterFactory = new FunctionParameterFactory();

        functionParameters.add(functionParameterFactory.getBean("me"));
        functionParameters.add(functionParameterFactory.getBean("broadcast"));
        functionParameters.add(functionParameterFactory.getBean("balanceTopList"));
        functionParameters.add(functionParameterFactory.getBean("helpOp"));

        return new FunctionParameterContainer(functionParameters);
    }
}
