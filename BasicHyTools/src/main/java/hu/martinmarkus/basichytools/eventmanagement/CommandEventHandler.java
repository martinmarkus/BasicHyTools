package hu.martinmarkus.basichytools.eventmanagement;

import hu.martinmarkus.basichytools.configmanagement.managers.FunctionParameterManager;
import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.UserManager;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.gamefunctions.generalfunctions.chatfunctions.Me;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;

public class CommandEventHandler {

    private LanguageConfig languageConfig;
    private FunctionParameterManager functionParameterManager;

    public CommandEventHandler() {
        languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        functionParameterManager = FunctionParameterManager.getInstance();
    }

    public void onUserExecuteCommand() {
        String rawCommand = "";
        String userName = "mockUser12345";

        User user = UserManager.getInstance().getOnlineUser(userName);
        if (user == null) {
            return;
        }

        if (rawCommand == null || rawCommand.isEmpty()) {
            String message = languageConfig.getInvalidCommandUsage();
            user.sendMessage(message);
            return;
        }

        String[] commandWithArgs = rawCommand.split(" ");
        String command = commandWithArgs[0].toLowerCase();
        GameFunction function = null;

        switch (command) {
            case "me":
                if (hasEnoughParam("me", commandWithArgs)) {
                    function = new Me(user, commandWithArgs[1]);
                }
                break;


            default:
                return;
        }

        function.execute();
    }

    private boolean hasEnoughParam(String functionName, String[] command) {
        if (command == null || command.length == 0) {
            return false;
        }

        int requiredCount = functionParameterManager.getByName(functionName).getRequiredParameterCount();
        if (command.length - 1 == requiredCount) {
            return true;
        }

        return false;
    }
}
