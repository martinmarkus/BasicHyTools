package hu.martinmarkus.basichytools.configmanagement.managers;

import hu.martinmarkus.basichytools.configmanagement.initializers.HyToolsInitializer;
import hu.martinmarkus.basichytools.functions.FunctionParameter;
import hu.martinmarkus.basichytools.models.containers.FunctionParameterContainer;
import hu.martinmarkus.basichytools.models.containers.GroupContainer;
import hu.martinmarkus.basichytools.persistence.repositories.FunctionParameterContainerRepository;
import hu.martinmarkus.basichytools.synchronization.ISynchronizer;
import hu.martinmarkus.basichytools.synchronization.Synchronizer;

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

    private FunctionParameterManager() {
        String path = HyToolsInitializer.getRootPath();
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

        FunctionParameterContainer container = generateDefaultFunctionParameterContainer();
        functionParameterList = container.getFunctionParameters();
        functionParameterContainerRepository.add(FUNCTION_PARAMETERS, container);
    }

    public List<FunctionParameter> getAlLFunctionParameters() {
        return functionParameterList;
    }


    private FunctionParameterContainer generateDefaultFunctionParameterContainer() {
        List<FunctionParameter> functionParameters = new ArrayList<>();
        // TODO: implement default function parameters


        return new FunctionParameterContainer(functionParameters);
    }
}
