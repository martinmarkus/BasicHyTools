package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.FunctionParameterManager;
import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.models.FunctionParameter;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.ioc.ObjectFactory;

import java.util.List;

public class CommandEventHandler {
    private LanguageConfig languageConfig;

    public CommandEventHandler() {
        languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
    }

    public void onUserExecuteCommand() {
        // TODO: get sender and command
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

        if (!user.isOperator() && !user.hasPermission(blockedCommandBypassPermission)) {
            List<String> blockedCommands = defaultConfig.getBlockedCommands();
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

    private void executeFunction(String rawCommand, User user) {
        GameFunction gameFunction = null;
        String[] commandWithArgs = rawCommand.split(" ");
        String command = commandWithArgs[0].toLowerCase();

        FunctionParameterManager functionParameterManager = FunctionParameterManager.getInstance();
        List<FunctionParameter> functionParameters = functionParameterManager.getAlLFunctionParameters();
        ObjectFactory<GameFunction> objectFactory = new ObjectFactory<>();

        for (FunctionParameter functionParameter : functionParameters) {
            String name = functionParameter.getName();
            if (command.equalsIgnoreCase(functionParameter.getName())) {
                gameFunction = objectFactory.getBean(name.toLowerCase());
                break;
            }
        }

        if (gameFunction != null) {
            gameFunction.setRequiredParams(rawCommand, user);
            gameFunction.execute();
        }
    }
}
