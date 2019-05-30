package hu.martinmarkus.basichytools.gamefunctions;

import hu.martinmarkus.basichytools.configmanagement.FunctionParameterManager;
import hu.martinmarkus.basichytools.configmanagement.LanguageConfigManager;
import hu.martinmarkus.basichytools.configmanagement.UserManager;
import hu.martinmarkus.basichytools.utils.StringUtil;
import hu.martinmarkus.basichytools.utils.repeatingfunctions.FunctionCooldown;
import hu.martinmarkus.basichytools.utils.Informer;
import hu.martinmarkus.basichytools.models.FunctionParameter;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.models.containers.CooldownContainer;

import java.util.List;
import java.util.concurrent.Callable;

public abstract class GameFunction {
    protected LanguageConfig languageConfig;
    protected FunctionParameter functionParameter;
    protected CooldownContainer cooldownContainer;

    protected User executor;
    protected String rawCommand;

    public abstract void execute();

    public abstract Object executeWithReturnValue();

    public abstract void setRequiredParams(String rawCommand, User executor);

    public GameFunction(String functionName) {
        initFunctionParameter(functionName);
        languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
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

    protected void initializeCooldownContainer() {
        int cooldown = functionParameter.getCooldown();
        String functionName = functionParameter.getName();
        String executorName = executor.getName();
        cooldownContainer = new CooldownContainer(executorName, functionName, cooldown);
    }

    private void addCooldown() {
        FunctionCooldown functionCooldown = FunctionCooldown.getInstance();
        functionCooldown.addCooldown(cooldownContainer);
    }

    protected void runFunction(Runnable runnable) {
        if (executor == null || rawCommand == null) {
            return;
        }

        boolean isOperator = executor.isOperator();
        GameFunctionValidator validator = new GameFunctionValidator(this);
        boolean canDoFunction = validator.canDoFunction();
        if (!canDoFunction) {
            return;
        }

        if (runnable != null) {
            runnable.run();
        }

        if (!isOperator) {
            double usagePrice = functionParameter.getUsagePrice();
            if (usagePrice > 0) {
                executor.decreaseBalance(usagePrice);
            }
        }

        addCooldown();
        doLogging();
    }

    protected Object callFunction(Callable<Object> callable) {
        if (executor == null || rawCommand == null) {
            return null;
        }

        boolean isOperator = executor.isOperator();
        GameFunctionValidator validator = new GameFunctionValidator(this);
        boolean canDoFunction = validator.canDoFunction();
        if (!canDoFunction) {
            return null;
        }

        Object result = null;
        if (callable != null) {
            try {
                result = callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!isOperator) {
            double usagePrice = functionParameter.getUsagePrice();
            executor.decreaseBalance(usagePrice);
            executor.sendMessage(languageConfig.getCommandExecuted());
        }

        FunctionCooldown.getInstance().addCooldown(cooldownContainer);
        doLogging();
        return result;
    }

    private void doLogging() {
        if (functionParameter.isDoLogging()) {
            String message = languageConfig.getCommandExecuted();
            String userName = executor.getName();

            message = StringUtil.replace(message, userName, rawCommand);

            Informer.logInfo(message);
            notifyCommandSpies(message);
        }
    }

    private void notifyCommandSpies(String message) {
        List<User> onlineUsers = UserManager.getInstance().getAllOnlineUsers();
        String commandSpyPrefix = languageConfig.getCommandSpyPrefix();
        onlineUsers.forEach(onlineUser -> {
            if (onlineUser != executor && onlineUser.isCommandSpyActive()) {
                onlineUser.sendMessage(commandSpyPrefix.concat(message));
            }
        });
    }

    public FunctionParameter getFunctionParameter() {
        return functionParameter;
    }

    public void setFunctionParameter(FunctionParameter functionParameter) {
        this.functionParameter = functionParameter;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public String getRawCommand() {
        return rawCommand;
    }

    public void setRawCommand(String rawCommand) {
        this.rawCommand = rawCommand;
    }

    public CooldownContainer getCooldownContainer() {
        return cooldownContainer;
    }

    public void setCooldownContainer(CooldownContainer cooldownContainer) {
        this.cooldownContainer = cooldownContainer;
    }
}
