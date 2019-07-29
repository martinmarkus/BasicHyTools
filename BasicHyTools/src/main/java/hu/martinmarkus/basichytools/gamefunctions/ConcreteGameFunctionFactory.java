package hu.martinmarkus.basichytools.gamefunctions;

import hu.martinmarkus.basichytools.configmanagement.FunctionParameterManager;
import hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories.GameFunctionFactory;
import hu.martinmarkus.basichytools.models.FunctionParameter;

import java.util.List;

public class ConcreteGameFunctionFactory {

    private static ConcreteGameFunctionFactory concreteGameFunctionFactory;

    public static ConcreteGameFunctionFactory getInstance() {
        if (concreteGameFunctionFactory == null) {
            concreteGameFunctionFactory = new ConcreteGameFunctionFactory();
        }

        return concreteGameFunctionFactory;
    }

    public GameFunction createGameFunction(String rawCommand) {
        String command = getCommand(rawCommand);
        List<FunctionParameter> functionParameters = getFunctionParameters();

        boolean isCommand;
        boolean isAlias;

        for (FunctionParameter functionParameter : functionParameters) {
            isCommand = command.equalsIgnoreCase(functionParameter.getName());
            isAlias = isAlias(command, functionParameter.getAliases());

            if (isCommand || isAlias) {
                String name = functionParameter.getName();
                return GameFunctionFactory.getInstance().getBean(name);
            }
        }

        return null;
    }

    private boolean isAlias(String command, List<String> aliases) {
        for (String alias : aliases) {
            if (alias.equalsIgnoreCase(command)) {
                return true;
            }
        }
        return false;
    }

    private List<FunctionParameter> getFunctionParameters() {
        FunctionParameterManager functionParameterManager = FunctionParameterManager.getInstance();
        return functionParameterManager.getAlLFunctionParameters();
    }

    private String getCommand(String rawCommand) {
        String[] commandWithArgs = rawCommand.split(" ");
        if (commandWithArgs.length > 0) {
            return commandWithArgs[0];
        }
        return "";
    }
}
