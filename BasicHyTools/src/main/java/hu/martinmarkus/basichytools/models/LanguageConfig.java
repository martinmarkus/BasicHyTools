package hu.martinmarkus.basichytools.models;

public class LanguageConfig {

    private String motd;
    private String joinMessage;
    private String quitMessage;
    private String cooldown;
    private String afkOn;
    private String afkOff;
    private String banned;
    private String ipBanned;
    private String unbanned;
    private String kicked;
    private String allKicked;
    private String muted;
    private String unmuted;

    private String notEnoughPermission;
    private String notEnoughMoney;
    private String errorHasOccurred;
    private String commandExecuted;
    private String userIsStillConnecting;

    private String balanceIncreased;
    private String balanceDecreased;
    private String balanceSet;

    public LanguageConfig() {

    }

    public LanguageConfig(String motd, String joinMessage, String quitMessage, String cooldown,
                          String afkOn, String afkOff, String banned, String ipBanned, String unbanned,
                          String kicked, String allKicked, String muted, String unmuted, String notEnoughPermission,
                          String notEnoughMoney, String errorHasOccurred, String commandExecuted,
                          String userIsStillConnecting, String balanceIncreased, String balanceDecreased,
                          String balanceSet) {
        this.motd = motd;
        this.joinMessage = joinMessage;
        this.quitMessage = quitMessage;
        this.cooldown = cooldown;
        this.afkOn = afkOn;
        this.afkOff = afkOff;
        this.banned = banned;
        this.ipBanned = ipBanned;
        this.unbanned = unbanned;
        this.kicked = kicked;
        this.allKicked = allKicked;
        this.muted = muted;
        this.unmuted = unmuted;
        this.notEnoughPermission = notEnoughPermission;
        this.notEnoughMoney = notEnoughMoney;
        this.errorHasOccurred = errorHasOccurred;
        this.commandExecuted = commandExecuted;
        this.userIsStillConnecting = userIsStillConnecting;
        this.balanceIncreased = balanceIncreased;
        this.balanceDecreased = balanceDecreased;
        this.balanceSet = balanceSet;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public void setJoinMessage(String joinMessage) {
        this.joinMessage = joinMessage;
    }

    public String getQuitMessage() {
        return quitMessage;
    }

    public void setQuitMessage(String quitMessage) {
        this.quitMessage = quitMessage;
    }

    public String getCooldown() {
        return cooldown;
    }

    public void setCooldown(String cooldown) {
        this.cooldown = cooldown;
    }

    public String getAfkOn() {
        return afkOn;
    }

    public void setAfkOn(String afkOn) {
        this.afkOn = afkOn;
    }

    public String getAfkOff() {
        return afkOff;
    }

    public void setAfkOff(String afkOff) {
        this.afkOff = afkOff;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    public String getIpBanned() {
        return ipBanned;
    }

    public void setIpBanned(String ipBanned) {
        this.ipBanned = ipBanned;
    }

    public String getUnbanned() {
        return unbanned;
    }

    public void setUnbanned(String unbanned) {
        this.unbanned = unbanned;
    }

    public String getKicked() {
        return kicked;
    }

    public void setKicked(String kicked) {
        this.kicked = kicked;
    }

    public String getAllKicked() {
        return allKicked;
    }

    public void setAllKicked(String allKicked) {
        this.allKicked = allKicked;
    }

    public String getMuted() {
        return muted;
    }

    public void setMuted(String muted) {
        this.muted = muted;
    }

    public String getUnmuted() {
        return unmuted;
    }

    public void setUnmuted(String unmuted) {
        this.unmuted = unmuted;
    }

    public String getNotEnoughPermission() {
        return notEnoughPermission;
    }

    public void setNotEnoughPermission(String notEnoughPermission) {
        this.notEnoughPermission = notEnoughPermission;
    }

    public String getNotEnoughMoney() {
        return notEnoughMoney;
    }

    public void setNotEnoughMoney(String notEnoughMoney) {
        this.notEnoughMoney = notEnoughMoney;
    }

    public String getErrorHasOccurred() {
        return errorHasOccurred;
    }

    public void setErrorHasOccurred(String errorHasOccurred) {
        this.errorHasOccurred = errorHasOccurred;
    }

    public String getCommandExecuted() {
        return commandExecuted;
    }

    public void setCommandExecuted(String commandExecuted) {
        this.commandExecuted = commandExecuted;
    }

    public String getUserIsStillConnecting() {
        return userIsStillConnecting;
    }

    public void setUserIsStillConnecting(String userIsStillConnecting) {
        this.userIsStillConnecting = userIsStillConnecting;
    }

    public String getBalanceIncreased() {
        return balanceIncreased;
    }

    public void setBalanceIncreased(String balanceIncreased) {
        this.balanceIncreased = balanceIncreased;
    }

    public String getBalanceDecreased() {
        return balanceDecreased;
    }

    public void setBalanceDecreased(String balanceDecreased) {
        this.balanceDecreased = balanceDecreased;
    }

    public String getBalanceSet() {
        return balanceSet;
    }

    public void setBalanceSet(String balanceSet) {
        this.balanceSet = balanceSet;
    }
}
