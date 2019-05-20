package hu.martinmarkus.basichytools.gamefunctions;

import hu.martinmarkus.basichytools.configmanagement.managers.FunctionParameterManager;
import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.Informer;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.models.placeholders.placeholderhelpers.PlaceholderReplacer;

import java.util.List;
import java.util.concurrent.Callable;

public abstract class GameFunction<T> {
    protected LanguageConfig languageConfig;
    protected FunctionParameter functionParameter;

    protected User executorUser;
    protected String rawCommand;

    public abstract void execute();

    public abstract T executeWithReturnValue();

    public abstract void initRawCommand();

    public GameFunction(User executor, String functionName) {
        languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        this.executorUser = executor;
        initFunctionParameter(functionName);
    }

    protected void runFunction(Runnable runnable) {
        if (executorUser == null) {
            Informer.logWarn(languageConfig.getUserIsStillConnecting());
            return;
        } else if (!hasPermission()) {
            return;
        }

        boolean isOperator = executorUser.isOperator();
        if (!isOperator && !hasMoney()) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        if (!isOperator) {
            double usagePrice = functionParameter.getUsagePrice();
            executorUser.decreaseBalance(usagePrice);
        }

        doLogging();
    }

    protected T callFunction(Callable<T> callable) {
        if (executorUser == null) {
            Informer.logWarn(languageConfig.getUserIsStillConnecting());
            return null;
        } else if (!hasPermission()) {
            return null;
        }

        boolean isOperator = executorUser.isOperator();
        if (!isOperator && !hasMoney()) {
            return null;
        }

        T result = null;
        if (callable != null) {
            try {
                result = callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isOperator) {
            double usagePrice = functionParameter.getUsagePrice();
            executorUser.decreaseBalance(usagePrice);
            executorUser.sendMessage(languageConfig.getCommandExecuted());
        }

        doLogging();
        return result;
    }

    private void initFunctionParameter(String functionName) {
        if (functionName == null || functionName.isEmpty()) {
            return;
        }

        List<FunctionParameter> functionParameters =
                FunctionParameterManager.getInstance().getAlLFunctionParameters();

        for (FunctionParameter aFunctionParameter : functionParameters) {
            String checkName = aFunctionParameter.getName();
            if (functionName.equalsIgnoreCase(checkName)) {
                this.functionParameter = aFunctionParameter;
            }
        }
    }

    private void doLogging() {
        if (functionParameter.isDoLogging()) {
            String message = languageConfig.getCommandExecuted();
            String userName = executorUser.getName();

            PlaceholderReplacer replacer = new PlaceholderReplacer();
            message = replacer.replace(message, userName, rawCommand);

            Informer.logInfo(message);
        }
    }

    private boolean hasMoney() {
        if (functionParameter == null) {
            return false;
        }

        double usagePrice = functionParameter.getUsagePrice();
        double balance = executorUser.getBalance();
        boolean hasMoney = balance >= usagePrice;

        if (!hasMoney) {
            executorUser.sendMessage(languageConfig.getNotEnoughMoney());
        }

        executorUser.decreaseBalance(usagePrice);
        return hasMoney;
    }

    private boolean hasPermission() {
        if (functionParameter == null) {
            return false;
        }

        String permission = functionParameter.getPermission();
        boolean hasPermission = executorUser.hasPermission(permission);

        if (!hasPermission) {
            executorUser.sendMessage(languageConfig.getNotEnoughPermission());
        }

        return hasPermission;
    }

    public FunctionParameter getFunctionParameter() {
        return functionParameter;
    }

    public void setFunctionParameter(FunctionParameter functionParameter) {
        this.functionParameter = functionParameter;
    }

    public User getExecutorUser() {
        return executorUser;
    }

    public void setExecutorUser(User executorUser) {
        this.executorUser = executorUser;
    }

    public String getRawCommand() {
        return rawCommand;
    }

    public void setRawCommand(String rawCommand) {
        this.rawCommand = rawCommand;
    }
}
