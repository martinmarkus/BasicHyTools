package hu.martinmarkus.basichytools.models;

import java.util.List;

public class FunctionParameter {
    private String name;
    private String command;
    private List<String> aliases;
    private String permission;
    private double usagePrice;
    private String description;
    private boolean doLogging;

    public FunctionParameter(String name, String command, List<String> aliases, String permission,
                             double usagePrice, String description, boolean doLogging) {
        this.name = name;
        this.command = command;
        this.aliases = aliases;
        this.permission = permission;
        this.usagePrice = usagePrice;
        this.description = description;
        this.doLogging = doLogging;
    }

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

    public boolean isDoLogging() {
        return doLogging;
    }

    public void setDoLogging(boolean doLogging) {
        this.doLogging = doLogging;
    }
}
