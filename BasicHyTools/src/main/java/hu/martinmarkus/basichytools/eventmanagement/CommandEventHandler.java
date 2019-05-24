package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.FunctionParameterManager;
import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.gamefunctions.generalfunctions.chatfunctions.Me;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.models.placeholders.placeholderhelpers.PlaceholderReplacer;

import java.util.List;

public class CommandEventHandler {

    private LanguageConfig languageConfig;
    private FunctionParameterManager functionParameterManager;

    public CommandEventHandler() {
        languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        functionParameterManager = FunctionParameterManager.getInstance();
    }

    public void onUserExecuteCommand() {
        String rawCommand = "me hello FUCk";
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
        List<String> blockedCommands = defaultConfig.getBlockedCommands();

        if (!user.isOperator() && !user.hasPermission(blockedCommandBypassPermission)) {
            for (String blockedCommand : blockedCommands) {
                if (command.equalsIgnoreCase(blockedCommand)) {
                    String message = languageConfig.getUnknownCommand();
                    user.sendMessage(message);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasEnoughParam(String functionName, String[] fullCommand, User user, boolean atleast) {
        if (fullCommand != null && fullCommand.length != 0) {
            int requiredCount = functionParameterManager.getByName(functionName).getRequiredParameterCount();
            if (atleast && fullCommand.length - 1 >= requiredCount) {
                return true;
            }
            if (fullCommand.length - 1 == requiredCount) {
                return true;
            }
        }

        sendErrorMessageToUser(user, fullCommand[0]);
        return false;
    }

    private void sendErrorMessageToUser(User user, String baseCommand) {
        String command = functionParameterManager.getByName(baseCommand).getCommand();
        String message = languageConfig.getInvalidCommandUsagePleaseTry();
        PlaceholderReplacer replacer = new PlaceholderReplacer();
        message = replacer.replace(message, command);
        user.sendMessage(message);
    }

    private void executeFunction(String rawCommand, User user) {
        String[] commandWithArgs = rawCommand.split(" ");
        String command = commandWithArgs[0].toLowerCase();
        switch (command) {
            case "me":
                if (hasEnoughParam("me", commandWithArgs, user, true)) {
                    executeMe(commandWithArgs, user);
                }
                return;
            default:
                return;
        }
    }

    private void executeMe(String[] commandWithArgs, User user) {
        GameFunction function;
        String params = "";
        for (int i = 1; i < commandWithArgs.length; i++) {
            params = params.concat(commandWithArgs[i]).concat(" ");
        }
        function = new Me(user, params);
        function.execute();
    }
}
