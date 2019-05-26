package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories.GameFunctionFactory;
import hu.martinmarkus.basichytools.configmanagement.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.FunctionParameterManager;
import hu.martinmarkus.basichytools.configmanagement.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.UserManager;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.models.FunctionParameter;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;

import java.util.List;

public class CommandEventHandler {
    private LanguageConfig languageConfig;

    public CommandEventHandler() {
        languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
    }

    public void onUserExecuteCommand() {
        // TODO: get sender and command
        String rawCommand = "broadcast hello FUCk";
        String userName = "mockUser12345";

        User user = UserManager.getInstance().getOnlineUser(userName);
        if (user == null || !user.isValidated()) {
            return;
        }

        if (rawCommand == null || rawCommand.isEmpty()) {
            String message = languageConfig.getInvalidCommandUsage();
            user.sendMessage(message);
            return;
        }

        String command = rawCommand.split(" ")[0];
        boolean isCommandBlocked = isCommandBlocked(user, command);
        if (isCommandBlocked) {
            return;
        }

        executeFunction(rawCommand, user);
    }

    private boolean isCommandBlocked(User user, String command) {
        DefaultConfig defaultConfig = DefaultConfigManager.getInstance().getDefaultConfig();
        String blockedCommandBypassPermission = defaultConfig.getGlobalMechanismPermissions().get("blockedCommandBypass");

        if (user.isOperator() || user.hasPermission(blockedCommandBypassPermission)) {
            return false;
        }

        List<String> blockedCommands = defaultConfig.getBlockedCommands();
        for (String blockedCommand : blockedCommands) {
            if (command.equalsIgnoreCase(blockedCommand)) {
                String message = languageConfig.getUnknownCommand();
                user.sendMessage(message);
                return true;
            }
        }

        return false;
    }

    private void executeFunction(String rawCommand, User user) {
        GameFunction gameFunction = defineGameFunction(rawCommand);

        if (gameFunction != null) {
            gameFunction.setRequiredParams(rawCommand, user);
            gameFunction.execute();
        }
    }

    private GameFunction defineGameFunction(String rawCommand) {
        String command = getCommand(rawCommand);
        List<FunctionParameter> functionParameters = getFunctionParameters();
        GameFunctionFactory gameFunctionFactory = GameFunctionFactory.getInstance();

        for (FunctionParameter functionParameter : functionParameters) {
            String name = functionParameter.getName().toLowerCase();
            if (command.equalsIgnoreCase(functionParameter.getName())) {
                return gameFunctionFactory.getBean(name);
            }
        }

        return null;
    }

    private List<FunctionParameter> getFunctionParameters() {
        FunctionParameterManager functionParameterManager = FunctionParameterManager.getInstance();
        return functionParameterManager.getAlLFunctionParameters();
    }

    private String getCommand(String rawCommand) {
        String[] commandWithArgs = rawCommand.split(" ");
        return commandWithArgs[0];
    }
}
