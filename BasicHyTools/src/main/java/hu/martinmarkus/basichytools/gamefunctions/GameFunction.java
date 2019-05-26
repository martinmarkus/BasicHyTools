package hu.martinmarkus.basichytools.gamefunctions;

import hu.martinmarkus.basichytools.configmanagement.managers.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.FunctionParameterManager;
import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.FunctionCooldown;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.Informer;
import hu.martinmarkus.basichytools.models.FunctionParameter;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.models.containers.CooldownContainer;
import hu.martinmarkus.basichytools.utils.PlaceholderReplacer;

import java.util.List;
import java.util.concurrent.Callable;

public abstract class GameFunction {
    protected LanguageConfig languageConfig;
    protected FunctionParameter functionParameter;
    protected CooldownContainer cooldownContainer;

    protected User executor;
    protected String rawCommand;

    // abstract methods
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

    protected boolean hasEnoughParam(String[] fullCommand, boolean atleast) {
        if (fullCommand != null && fullCommand.length != 0) {
            int requiredCount = functionParameter.getRequiredParameterCount();
            if (atleast && fullCommand.length - 1 >= requiredCount) {
                return true;
            }
            if (fullCommand.length - 1 == requiredCount) {
                return true;
            }
            sendInvalidParameterCountMessage();
        }
        return false;
    }

    private void sendInvalidParameterCountMessage() {
        String command = functionParameter.getCommand();
        String message = languageConfig.getInvalidCommandUsagePleaseTry();
        PlaceholderReplacer replacer = new PlaceholderReplacer();
        message = replacer.replace(message, command);
        executor.sendMessage(message);
    }

    protected void runFunction(Runnable runnable) {
        if (executor == null || rawCommand == null || !hasPermission()) {
            return;
        }

        boolean isOperator = executor.isOperator();
        boolean canDoFunction = canDoFunction();
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
        if (executor == null || rawCommand == null || !hasPermission()) {
            return null;
        }

        boolean isOperator = executor.isOperator();
        boolean canDoFunction = canDoFunction();
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

    private boolean canDoFunction() {
        boolean isOperator = executor.isOperator();
        if (!isOperator) {
            if (!hasMoney()) {
                return false;
            }
            String functionCooldownPassPermission = DefaultConfigManager.getInstance().getDefaultConfig()
                    .getGlobalMechanismPermissions().get("functionCooldownBypass");
            if (!executor.hasPermission(functionCooldownPassPermission) && isOnCooldown()) {
                return false;
            }
        }

        return true;
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
