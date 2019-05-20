package hu.martinmarkus.basichytools.functions;

import hu.martinmarkus.basichytools.configmanagement.managers.FunctionParameterManager;
import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.HyLogger;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.models.placeholders.placeholderhelpers.PlaceholderReplacer;

import java.util.List;
import java.util.concurrent.Callable;

public abstract class BaseFunction<T> {
    protected LanguageConfig languageConfig;
    protected FunctionParameter functionParameter;
    protected User executorUser;

    public abstract void execute();
    public abstract T executeWithReturnValue();

    public BaseFunction(User executor, String functionName) {
        languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        this.executorUser = executor;
        initFunctionParameter(functionName);
    }

    protected void runFunction(Runnable runnable) {
        if (executorUser == null) {
            HyLogger.log(HyLogger.INFO, languageConfig.getUserIsStillConnecting());
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
            HyLogger.log(HyLogger.INFO, languageConfig.getUserIsStillConnecting());
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
            HyLogger.send(executorUser, languageConfig.getCommandExecuted());
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
            HyLogger.log(HyLogger.INFO, executorUser.getName()
                    + " has executed: " + functionParameter.getCommand());
        }
    }

    private boolean hasMoney() {
        Double usagePrice = functionParameter.getUsagePrice();
        Double balance = executorUser.getBalance();
        boolean hasMoney = balance < usagePrice;

        if (!hasMoney) {
            HyLogger.send(executorUser, languageConfig.getNotEnoughMoney());
        }

        return hasMoney;
    }

    private boolean hasPermission() {
        String permission = functionParameter.getPermission();
        boolean hasPermission = executorUser.hasPermission(permission);

        if (!hasPermission) {
            HyLogger.send(executorUser, languageConfig.getNotEnoughPermission());
        }

        return hasPermission;
    }
}
