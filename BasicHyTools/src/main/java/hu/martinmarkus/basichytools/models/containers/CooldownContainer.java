package hu.martinmarkus.basichytools.models.containers;

public class CooldownContainer {
    private String userName;
    private String functionName;
    private int cooldownSeconds;

    public CooldownContainer(String userName, String functionName, int cooldownSeconds) {
        this.userName = userName;
        this.functionName = functionName;
        this.cooldownSeconds = cooldownSeconds;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public int getCooldownSeconds() {
        return cooldownSeconds;
    }

    public void setCooldownSeconds(int cooldownSeconds) {
        this.cooldownSeconds = cooldownSeconds;
    }
}
