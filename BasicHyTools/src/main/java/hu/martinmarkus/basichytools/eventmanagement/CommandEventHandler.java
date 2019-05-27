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
import hu.martinmarkus.basichytools.utils.StringUtil;

import java.util.List;

public class CommandEventHandler {
    private LanguageConfig languageConfig;

    public CommandEventHandler() {
        languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
    }

    public void onUserExecuteCommand(String rawCommand) {
        // TODO: get sender and command
        String userName = "mockUser12345";

        User user = UserManager.getInstance().getOnlineUser(userName);
        if (user == null || !user.isValidated()) {
            return;
        }

        if (rawCommand == null || rawCommand.isEmpty()) {
            String message = languageConfig.getInvalidCommandUsage();
            user.sendMessage(message, false);
            return;
        }

        String command = getCommand(rawCommand);
        boolean isCommandBlocked = isCommandBlocked(user, command);
        if (isCommandBlocked) {
            return;
        }
        executeFunction(rawCommand, user);
    }

    private boolean isCommandBlocked(User user, String command) {
        DefaultConfig defaultConfig = DefaultConfigManager.getInstance().getDefaultConfig();
        String blockedCommandBypassPermission = defaultConfig.getGlobalMechanismPermissions().get("blockedCommandBypass");

        boolean isOperator = user.isOperator();
        boolean hasPermission = user.hasPermission(blockedCommandBypassPermission);
        if (isOperator || hasPermission) {
            return false;
        }

        List<String> blockedCommands = defaultConfig.getBlockedCommands();
        for (String blockedCommand : blockedCommands) {
            if (command.equalsIgnoreCase(blockedCommand)) {
                String message = languageConfig.getUnknownCommand();
                user.sendMessage(message, false);
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
        // if the command is unknown, just don't do anything
    }

    private GameFunction defineGameFunction(String rawCommand) {
        String command = getCommand(rawCommand);
        List<FunctionParameter> functionParameters = getFunctionParameters();

        boolean isCommand;
        boolean isAlias;

        for (FunctionParameter functionParameter : functionParameters) {
            isCommand = command.equalsIgnoreCase(functionParameter.getName());
            isAlias = isAlias(command, functionParameter.getAliases());

            if (isCommand || isAlias) {
                String name = functionParameter.getName().toLowerCase();
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
        return commandWithArgs[0];
    }
}
