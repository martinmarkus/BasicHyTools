package hu.martinmarkus.basichytools.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.martinmarkus.basichytools.configmanagement.managers.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.managers.GroupManager;
import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;
import hu.martinmarkus.basichytools.models.placeholders.placeholderhelpers.PlaceholderReplacer;
import hu.martinmarkus.basichytools.permissionmanagement.PermissionValidator;
import hu.martinmarkus.basichytools.permissionmanagement.UserPermissionValidator;
import hu.martinmarkus.basichytools.stringhelpers.StringHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class User {
    @JsonIgnore
    private boolean isValidated = false;        // Signs the validation state of the User

    @JsonIgnore
    private Queue<String> lastSendMessages = new LinkedList<>();

    private String name;
    private String permissionGroupName;
    private double balance;
    private double exp;
    private boolean online;
    private String loginIp;
    private String loginTime;
    private BasicHyToolsLocation location;
    private List<String> uniquePermissions;
    private boolean operator;
    private boolean muted;
    private boolean banned;
    private boolean ipBanned;
    private boolean whiteListed;

    public User(String name, String permissionGroupName, double balance, double exp,
                boolean online, String loginIp, String loginTime,
                BasicHyToolsLocation location, List<String> uniquePermissions,
                boolean operator, boolean muted, boolean banned, boolean ipBanned,
                boolean whiteListed) {
        this.name = name;
        this.permissionGroupName = permissionGroupName;
        this.balance = balance;
        this.exp = exp;
        this.online = online;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
        this.location = location;
        this.uniquePermissions = uniquePermissions;
        this.operator = operator;
        this.muted = muted;
        this.banned = banned;
        this.ipBanned = ipBanned;
        this.whiteListed = whiteListed;
    }

    @JsonIgnore
    public void sendMessage(String message) {
        // TODO: implement default message sending to User
        message = StringHelper.censoreMessage(this, message);
        System.out.println("Msg to " + name + ": " + message);
    }

    @JsonIgnore
    public void sendMotd() {
        String motd = LanguageConfigManager.getInstance().getLanguageConfig().getMotd();
        sendMessage(motd);
    }

    @JsonIgnore
    public void teleportToSpawn() {
        BasicHyToolsLocation spawnLocation =
                DefaultConfigManager.getInstance().getDefaultConfig().getSpawnLocation();
        teleport(spawnLocation);
    }

    @JsonIgnore
    public boolean canSendMessage(String message) {
        if (lastSendMessages == null) {
            lastSendMessages = new LinkedList<>();
        }
        return !lastSendMessages.contains(message.toLowerCase());
    }

    @JsonIgnore
    public void addSentMessage(String message) {
        if (lastSendMessages == null) {
            lastSendMessages = new LinkedList<>();
        }

        lastSendMessages.add(message.toLowerCase());

        int maxMemorableMessage = 3;
        if (lastSendMessages.size() > maxMemorableMessage) {
            lastSendMessages.poll();
        }
    }

    @JsonIgnore
    public void teleportToSpawnOnFirstJoin() {
        float userX = location.getX();
        float userY = location.getY();
        float userZ = location.getZ();
        String userWorldName = location.getWorldName();

        BasicHyToolsLocation spawnLocation =
                DefaultConfigManager.getInstance().getDefaultConfig().getSpawnLocation();

        float spawnX = spawnLocation.getX();
        float spawnY = spawnLocation.getY();
        float spawnZ = spawnLocation.getZ();
        String spawnWorldName = spawnLocation.getWorldName();

        if (userX == spawnX && userY == spawnY && userZ == spawnZ &&
                userWorldName.equalsIgnoreCase(spawnWorldName)) {
            teleport(spawnLocation);
        }
    }

    @JsonIgnore
    public void teleport(BasicHyToolsLocation location) {
        // TODO teleport user to location
    }

    @JsonIgnore
    public boolean hasPermission(String permission) {
        if (operator || uniquePermissions.contains(permission)) {
            return true;
        }

        PermissionValidator validator = new UserPermissionValidator();
        return validator.validate(this, permission);
    }

    @JsonIgnore
    public List<String> getAllPermissions() {
        List<String> allPermissions = new ArrayList<>(uniquePermissions);
        GroupManager configManager = GroupManager.getInstance();
        Group group = configManager.getPermissionGroup(permissionGroupName);

        allPermissions.addAll(group.getAllPermissions());

        return allPermissions;
    }

    @JsonIgnore
    public void decreaseBalance(double value) {
        value = Math.abs(value);
        balance -= value;
        DefaultConfig config = DefaultConfigManager.getInstance().getDefaultConfig();
        double minMoney = config.getMinMoney();
        if (balance < minMoney) {
            balance = minMoney;
        }

        LanguageConfig languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        PlaceholderReplacer replacer = new PlaceholderReplacer();
        String message = replacer.replace(languageConfig.getBalanceDecreased(), name, String.valueOf(value),
                String.valueOf(balance));
        sendMessage(message);
    }

    @JsonIgnore
    public void increaseBalance(double value) {
        value = Math.abs(value);
        balance += value;
        DefaultConfig config = DefaultConfigManager.getInstance().getDefaultConfig();
        double maxMoney = config.getMaxMoney();
        if (balance > maxMoney) {
            balance = maxMoney;
        }

        LanguageConfig languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        PlaceholderReplacer replacer = new PlaceholderReplacer();
        String message = replacer.replace(languageConfig.getBalanceIncreased(), name, String.valueOf(value),
                String.valueOf(balance));
        sendMessage(message);
    }

    @JsonIgnore
    public void resetBalance() {
        DefaultConfig config = DefaultConfigManager.getInstance().getDefaultConfig();
        balance = config.getStartingBalance();

        LanguageConfig languageConfig = LanguageConfigManager.getInstance().getLanguageConfig();
        PlaceholderReplacer replacer = new PlaceholderReplacer();
        String message = replacer.replace(languageConfig.getBalanceSet(), String.valueOf(balance));
        sendMessage(message);
    }

    @JsonIgnore
    public boolean isValidated() {
        return isValidated;
    }

    @JsonIgnore
    public void setValidated(boolean isValidated) {
        this.isValidated = isValidated;
    }

    @JsonIgnore
    public Queue<String> getLastSendMessages() {
        return lastSendMessages;
    }

    @JsonIgnore
    public void setLastSendMessages(Queue<String> lastSendMessages) {
        this.lastSendMessages = lastSendMessages;
    }

    // getters/setters:

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissionGroupName() {
        return permissionGroupName;
    }

    public void setPermissionGroupName(String permissionGroupName) {
        this.permissionGroupName = permissionGroupName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getExp() {
        return exp;
    }

    public void setExp(double exp) {
        this.exp = exp;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean isOnline) {
        this.online = isOnline;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    public BasicHyToolsLocation getLocation() {
        return location;
    }

    public void setLocation(BasicHyToolsLocation location) {
        this.location = location;
    }

    public List<String> getUniquePermissions() {
        return uniquePermissions;
    }

    public void setUniquePermissions(List<String> uniquePermissions) {
        this.uniquePermissions = uniquePermissions;
    }

    public boolean isOperator() {
        return operator;
    }

    public void setOperator(boolean isOperator) {
        this.operator = isOperator;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean isMuted) {
        this.muted = isMuted;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean isBanned) {
        this.banned = isBanned;
    }

    public boolean isIpBanned() {
        return ipBanned;
    }

    public void setIpBanned(boolean ipBanned) {
        this.ipBanned = ipBanned;
    }

    public boolean isWhiteListed() {
        return whiteListed;
    }

    public void setWhiteListed(boolean whiteListed) {
        this.whiteListed = whiteListed;
    }
}
