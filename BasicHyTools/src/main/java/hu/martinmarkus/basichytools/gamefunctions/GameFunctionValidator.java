package hu.martinmarkus.basichytools.gamefunctions;

import hu.martinmarkus.basichytools.configmanagement.DefaultConfigManager;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.FunctionCooldown;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.models.FunctionParameter;
import hu.martinmarkus.basichytools.models.LanguageConfig;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.models.containers.CooldownContainer;
import hu.martinmarkus.basichytools.utils.PlaceholderReplacer;

public class GameFunctionValidator {

    private LanguageConfig languageConfig;
    private FunctionParameter functionParameter;
    private CooldownContainer cooldownContainer;

    private User executor;
    private String rawCommand;

    public GameFunctionValidator(GameFunction gameFunction) {
        this.languageConfig = gameFunction.languageConfig;
        this.functionParameter = gameFunction.getFunctionParameter();
        this.cooldownContainer = gameFunction.getCooldownContainer();
        this.executor = gameFunction.getExecutor();
        this.rawCommand = gameFunction.getRawCommand();
    }

    public boolean canDoFunction() {
        boolean isOperator = executor.isOperator();
        if (!isOperator) {
            if (!hasPermission() || !hasMoney()) {
                return false;
            }

            DefaultConfig defaultConfig = DefaultConfigManager.getInstance().getDefaultConfig();
            String functionCooldownPassPermission =
                    defaultConfig.getGlobalMechanismPermissions().get("functionCooldownBypass");
            boolean canBypass = executor.hasPermission(functionCooldownPassPermission);

            if (isOnCooldown() && !canBypass) {
                sendCooldownMessage();
                return false;
            }
        }

        return hasEnoughParam();
    }

    private boolean hasEnoughParam() {
        String[] fullCommand = rawCommand.split(" ");

        if (fullCommand.length != 0) {
            int requiredCount = functionParameter.getRequiredParameterCount();
            boolean atLeast = functionParameter.isConcreteParameterCount();
            if (atLeast && fullCommand.length >= requiredCount) {
                return true;
            }
            if (fullCommand.length == requiredCount) {
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
        executor.sendMessage(message, false);
    }

    private boolean hasMoney() {
        if (functionParameter == null) {
            return false;
        }

        double usagePrice = functionParameter.getUsagePrice();
        double balance = executor.getBalance();
        boolean hasMoney = balance >= usagePrice;

        if (!hasMoney) {
            executor.sendMessage(languageConfig.getNotEnoughMoney(), false);
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
            executor.sendMessage(languageConfig.getNotEnoughPermission(), false);
        }

        return hasPermission;
    }

    private boolean isOnCooldown() {
        return FunctionCooldown.getInstance().isOnCooldown(cooldownContainer);
    }

    private void sendCooldownMessage() {
        String message = languageConfig.getFunctionStillOnCooldown();
        PlaceholderReplacer replacer = new PlaceholderReplacer();

        String cooldownValue = createCooldownMessage();
        message = replacer.replace(message, functionParameter.getName(), cooldownValue);
        executor.sendMessage(message, false);
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
}
