package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.FunctionParameterManager;
import hu.martinmarkus.basichytools.models.FunctionParameter;

import java.util.List;

public class CommandEventHandler {

    public void onUserExecuteCommand() {
        String command = "me hello";
        String[] splitedCommand = command.split(" ");

        List<FunctionParameter> functionParameters = FunctionParameterManager.getInstance().getAlLFunctionParameters();

        for (FunctionParameter functionParameter : functionParameters) {
            if (functionParameter.getCommand().equalsIgnoreCase(splitedCommand[0])) {

            }
        }
    }
}
