package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.gamefunctions.ConcreteGameFunctionFactory;
import hu.martinmarkus.basichytools.configmanagement.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.UserManager;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;

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
            return;
        }

        String command = getCommand(rawCommand);
        if (command.isEmpty()) {
            return;
        }

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
                user.sendMessage(message);
                return true;
            }
        }

        return false;
    }

    private void executeFunction(String rawCommand, User user) {
        ConcreteGameFunctionFactory concreteGameFunctionFactory = ConcreteGameFunctionFactory.getInstance();
        GameFunction gameFunction = concreteGameFunctionFactory.createGameFunction(rawCommand);

        if (gameFunction != null) {
            gameFunction.setRequiredParams(rawCommand, user);
            gameFunction.execute();
        }
        // if the command is unknown, do nothing
    }

    private String getCommand(String rawCommand) {
        String[] commandWithArgs = rawCommand.split(" ");
        if (commandWithArgs.length > 0) {
            return commandWithArgs[0];
        }
        return "";
    }
}
