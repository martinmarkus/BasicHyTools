package hu.martinmarkus.basichytools.models;

import java.util.List;

public class FunctionParameter {
    private String name;
    private String command;
    private List<String> aliases;
    private String permission;
    private double usagePrice;
    private String description;
    private int requiredParameterCount;
    private int cooldown;
    private boolean doLogging;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public double getUsagePrice() {
        return usagePrice;
    }

    public void setUsagePrice(double usagePrice) {
        this.usagePrice = usagePrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRequiredParameterCount() {
        return requiredParameterCount;
    }

    public void setRequiredParameterCount(int requiredParameterCount) {
        this.requiredParameterCount = requiredParameterCount;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isDoLogging() {
        return doLogging;
    }

    public void setDoLogging(boolean doLogging) {
        this.doLogging = doLogging;
    }

}
