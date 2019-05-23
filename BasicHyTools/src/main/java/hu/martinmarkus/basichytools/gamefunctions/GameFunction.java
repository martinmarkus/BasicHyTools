package hu.martinmarkus.basichytools.gamefunctions;

import hu.martinmarkus.basichytools.configmanagement.managers.FunctionParameterManager;
import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.FunctionCooldown;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.Informer;
import hu.martinmarkus.basichytools.models.FunctionParameter;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.models.containers.CooldownContainer;
import hu.martinmarkus.basichytools.models.placeholders.placeholderhelpers.PlaceholderReplacer;

import java.util.List;
import java.util.concurrent.Callable;

public abstract class GameFunction<T> {
    protected LanguageConfig languageConfig;
    protected FunctionParameter functionParameter;
    protected CooldownContainer cooldownContainer;

    protected User executor;
    protected String rawCommand;

    public abstract void execute();

    public abstract T executeWithReturnValue();

    public abstract void initRawCommand();

    public GameFunction(User executor, String functionName) {
        this.executor = executor;
        initFunctionParameter(functionName);
        languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        initializeCooldownContainer();
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

    private void initializeCooldownContainer() {
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
        if (executor == null) {
            Informer.logWarn(languageConfig.getUserIsStillConnecting());
            return;
        } else if (!hasPermission()) {
            return;
        }

        boolean isOperator = executor.isOperator();
        if (!isOperator) {
            if (!hasMoney() || isOnCooldown()) {
                return;
            }
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

    protected T callFunction(Callable<T> callable) {
        if (executor == null) {
            Informer.logWarn(languageConfig.getUserIsStillConnecting());
            return null;
        } else if (!hasPermission()) {
            return null;
        }

        boolean isOperator = executor.isOperator();
        if (!isOperator) {
            if (!hasMoney() || isOnCooldown()) {
                return null;
            }
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
        double balance = executor.getBalance();
        boolean hasMoney = balance >= usagePrice;

        if (!hasMoney) {
            executor.sendMessage(languageConfig.getNotEnoughMoney());
        }

        return hasMoney;
    }

    private boolean hasPermission() {
        if (functionParameter == null) {
            return false;
        }

        String permission = functionParameter.getPermission();
        boolean hasPermission = executor.hasPermission(permission);

        if (!hasPermission) {
            executor.sendMessage(languageConfig.getNotEnoughPermission());
        }

        return hasPermission;
    }

    private boolean isOnCooldown() {
        if (FunctionCooldown.getInstance().isOnCooldown(cooldownContainer)) {
            String message = languageConfig.getFunctionStillOnCooldown();
            PlaceholderReplacer replacer = new PlaceholderReplacer();

            String cooldownValue = createCooldownMessage();
            message = replacer.replace(message, functionParameter.getName(), cooldownValue);
            executor.sendMessage(message);
            return true;
        }

        return false;
    }

    private String createCooldownMessage() {
        int cooldown = FunctionCooldown.getInstance().getCooldownValueOf(cooldownContainer);

        int minutes = cooldown / 60;
        int seconds = cooldown % 60;

        String cooldownValue;
        String minuteString = languageConfig.getMinute();
        String secondString = languageConfig.getSecond();
        if (minutes == 0) {
            cooldownValue = String.format("%02d " + secondString, seconds);
            if (seconds == 0) {
                cooldownValue = languageConfig.getForOneMoreSecond();
            }
        } else {
            cooldownValue = String.format("%02d " + minuteString + ", %02d " + secondString, minutes, seconds);
        }
        return cooldownValue;
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
