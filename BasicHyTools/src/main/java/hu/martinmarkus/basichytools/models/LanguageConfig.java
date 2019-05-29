package hu.martinmarkus.basichytools.models;

import java.util.List;

public class LanguageConfig {
    private String motd;
    private String joinMessage;
    private String quitMessage;
    private String afkOn;
    private String afkOff;
    private String banned;
    private String tempBanned;
    private String ipBanned;
    private String tempIpBanned;
    private String unbanned;
    private String kicked;
    private String allKicked;
    private String muted;
    private String tempMuted;
    private String unmuted;

    private String notEnoughPermission;
    private String notEnoughMoney;
    private String errorHasOccurred;
    private String commandExecuted;
    private String userIsStillConnecting;
    private String unknownCommand;
    private String invalidCommandUsage;
    private String invalidCommandUsagePleaseTry;
    private String configSaveSuccessful;
    private String chatStillOnCooldown;
    private String functionStillOnCooldown;
    private String forOneMoreSecond;
    private String minute;
    private String second;

    private String balanceIncreased;
    private String balanceDecreased;
    private String balanceSet;

    private List<String> censoredWords;
    private String cantSendThisMessage;
    private String broadcastPrefix;
    private String helpOpPrefix;
    private String separator;
    private String unknownUser;
    private String notEnoughPermissionForWhisper;
    private String whisperToYou;
    private String whisperFromYou;
    private String whisperSeparator;
    private String socialSpyPrefix;
    private String commandSpyPrefix;
    private String socialSpyActivated;
    private String socialSpyDeactivated;
    private String youAreIgnoring;
    private String youAreIgnored;
    private String ignored;
    private String ignoreRemoved;
    private String warnPrefix;
    private String infoPrefix;
    private String errorPrefix;
    private String basicHyToolsPrefix;


    public LanguageConfig() {

    }

    public LanguageConfig(String motd, String joinMessage, String quitMessage, String afkOn, String afkOff,
                          String banned, String tempBanned, String ipBanned, String tempIpBanned, String unbanned,
                          String kicked, String allKicked, String muted, String tempMuted, String unmuted,
                          String notEnoughPermission, String notEnoughMoney, String errorHasOccurred,
                          String commandExecuted, String userIsStillConnecting, String unknownCommand,
                          String invalidCommandUsage,
                          String invalidCommandUsagePleaseTry, String configSaveSuccessful, String chatStillOnCooldown,
                          String functionStillOnCooldown, String forOneMoreSecond, String minute, String second,
                          String balanceIncreased, String balanceDecreased, String balanceSet, List<String> censoredWords,
                          String cantSendThisMessage, String broadcastPrefix, String helpOpPrefix, String separator,
                          String unknownUser, String notEnoughPermissionForWhisper, String whisperToYou, String whisperFromYou,
                          String whisperSeparator, String socialSpyPrefix, String commandSpyPrefix, String socialSpyActivated,
                          String socialSpyDeactivated, String youAreIgnoring, String youAreIgnored, String ignored,
                          String ignoreRemoved, String warnPrefix, String infoPrefix, String errorPrefix,
                          String basicHyToolsPrefix) {
        this.motd = motd;
        this.joinMessage = joinMessage;
        this.quitMessage = quitMessage;
        this.afkOn = afkOn;
        this.afkOff = afkOff;
        this.banned = banned;
        this.tempBanned = tempBanned;
        this.ipBanned = ipBanned;
        this.tempIpBanned = tempIpBanned;
        this.unbanned = unbanned;
        this.kicked = kicked;
        this.allKicked = allKicked;
        this.muted = muted;
        this.tempMuted = tempMuted;
        this.unmuted = unmuted;
        this.notEnoughPermission = notEnoughPermission;
        this.notEnoughMoney = notEnoughMoney;
        this.errorHasOccurred = errorHasOccurred;
        this.commandExecuted = commandExecuted;
        this.userIsStillConnecting = userIsStillConnecting;
        this.unknownCommand = unknownCommand;
        this.invalidCommandUsage = invalidCommandUsage;
        this.invalidCommandUsagePleaseTry = invalidCommandUsagePleaseTry;
        this.configSaveSuccessful = configSaveSuccessful;
        this.chatStillOnCooldown = chatStillOnCooldown;
        this.functionStillOnCooldown = functionStillOnCooldown;
        this.forOneMoreSecond = forOneMoreSecond;
        this.minute = minute;
        this.second = second;
        this.balanceIncreased = balanceIncreased;
        this.balanceDecreased = balanceDecreased;
        this.balanceSet = balanceSet;
        this.censoredWords = censoredWords;
        this.cantSendThisMessage = cantSendThisMessage;
        this.broadcastPrefix = broadcastPrefix;
        this.helpOpPrefix = helpOpPrefix;
        this.separator = separator;
        this.unknownUser = unknownUser;
        this.notEnoughPermissionForWhisper = notEnoughPermissionForWhisper;
        this.whisperToYou = whisperToYou;
        this.whisperFromYou = whisperFromYou;
        this.whisperSeparator = whisperSeparator;
        this.socialSpyPrefix = socialSpyPrefix;
        this.commandSpyPrefix = commandSpyPrefix;
        this.socialSpyActivated = socialSpyActivated;
        this.socialSpyDeactivated = socialSpyDeactivated;
        this.youAreIgnoring = youAreIgnoring;
        this.youAreIgnored = youAreIgnored;
        this.ignored = ignored;
        this.ignoreRemoved = ignoreRemoved;
        this.warnPrefix = warnPrefix;
        this.infoPrefix = infoPrefix;
        this.errorPrefix = errorPrefix;
        this.basicHyToolsPrefix = basicHyToolsPrefix;
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

    public String getTempBanned() {
        return tempBanned;
    }

    public void setTempBanned(String tempBanned) {
        this.tempBanned = tempBanned;
    }

    public String getIpBanned() {
        return ipBanned;
    }

    public void setIpBanned(String ipBanned) {
        this.ipBanned = ipBanned;
    }

    public String getTempIpBanned() {
        return tempIpBanned;
    }

    public void setTempIpBanned(String tempIpBanned) {
        this.tempIpBanned = tempIpBanned;
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

    public String getTempMuted() {
        return tempMuted;
    }

    public void setTempMuted(String tempMuted) {
        this.tempMuted = tempMuted;
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

    public String getUnknownCommand() {
        return unknownCommand;
    }

    public void setUnknownCommand(String unknownCommand) {
        this.unknownCommand = unknownCommand;
    }

    public String getInvalidCommandUsage() {
        return invalidCommandUsage;
    }

    public void setInvalidCommandUsage(String invalidCommandUsage) {
        this.invalidCommandUsage = invalidCommandUsage;
    }

    public String getInvalidCommandUsagePleaseTry() {
        return invalidCommandUsagePleaseTry;
    }

    public void setInvalidCommandUsagePleaseTry(String invalidCommandUsagePleaseTry) {
        this.invalidCommandUsagePleaseTry = invalidCommandUsagePleaseTry;
    }

    public String getConfigSaveSuccessful() {
        return configSaveSuccessful;
    }

    public void setConfigSaveSuccessful(String configSaveSuccessful) {
        this.configSaveSuccessful = configSaveSuccessful;
    }

    public String getChatStillOnCooldown() {
        return chatStillOnCooldown;
    }

    public void setChatStillOnCooldown(String chatStillOnCooldown) {
        this.chatStillOnCooldown = chatStillOnCooldown;
    }

    public String getFunctionStillOnCooldown() {
        return functionStillOnCooldown;
    }

    public void setFunctionStillOnCooldown(String functionStillOnCooldown) {
        this.functionStillOnCooldown = functionStillOnCooldown;
    }

    public String getForOneMoreSecond() {
        return forOneMoreSecond;
    }

    public void setForOneMoreSecond(String forOneMoreSecond) {
        this.forOneMoreSecond = forOneMoreSecond;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
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

    public List<String> getCensoredWords() {
        return censoredWords;
    }

    public void setCensoredWords(List<String> censoredWords) {
        this.censoredWords = censoredWords;
    }

    public String getCantSendThisMessage() {
        return cantSendThisMessage;
    }

    public void setCantSendThisMessage(String cantSendThisMessage) {
        this.cantSendThisMessage = cantSendThisMessage;
    }

    public String getBroadcastPrefix() {
        return broadcastPrefix;
    }

    public void setBroadcastPrefix(String broadcastPrefix) {
        this.broadcastPrefix = broadcastPrefix;
    }

    public String getHelpOpPrefix() {
        return helpOpPrefix;
    }

    public void setHelpOpPrefix(String helpOpPrefix) {
        this.helpOpPrefix = helpOpPrefix;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getUnknownUser() {
        return unknownUser;
    }

    public void setUnknownUser(String unknownUser) {
        this.unknownUser = unknownUser;
    }

    public String getNotEnoughPermissionForWhisper() {
        return notEnoughPermissionForWhisper;
    }

    public void setNotEnoughPermissionForWhisper(String notEnoughPermissionForWhisper) {
        this.notEnoughPermissionForWhisper = notEnoughPermissionForWhisper;
    }

    public String getWhisperToYou() {
        return whisperToYou;
    }

    public void setWhisperToYou(String whisperToYou) {
        this.whisperToYou = whisperToYou;
    }

    public String getWhisperFromYou() {
        return whisperFromYou;
    }

    public void setWhisperFromYou(String whisperFromYou) {
        this.whisperFromYou = whisperFromYou;
    }

    public String getWhisperSeparator() {
        return whisperSeparator;
    }

    public void setWhisperSeparator(String whisperSeparator) {
        this.whisperSeparator = whisperSeparator;
    }

    public String getSocialSpyPrefix() {
        return socialSpyPrefix;
    }

    public void setSocialSpyPrefix(String socialSpyPrefix) {
        this.socialSpyPrefix = socialSpyPrefix;
    }

    public String getCommandSpyPrefix() {
        return commandSpyPrefix;
    }

    public void setCommandSpyPrefix(String commandSpyPrefix) {
        this.commandSpyPrefix = commandSpyPrefix;
    }

    public String getSocialSpyActivated() {
        return socialSpyActivated;
    }

    public void setSocialSpyActivated(String socialSpyActivated) {
        this.socialSpyActivated = socialSpyActivated;
    }

    public String getSocialSpyDeactivated() {
        return socialSpyDeactivated;
    }

    public void setSocialSpyDeactivated(String socialSpyDeactivated) {
        this.socialSpyDeactivated = socialSpyDeactivated;
    }

    public String getYouAreIgnoring() {
        return youAreIgnoring;
    }

    public void setYouAreIgnoring(String youAreIgnoring) {
        this.youAreIgnoring = youAreIgnoring;
    }

    public String getYouAreIgnored() {
        return youAreIgnored;
    }

    public void setYouAreIgnored(String youAreIgnored) {
        this.youAreIgnored = youAreIgnored;
    }

    public String getIgnored() {
        return ignored;
    }

    public void setIgnored(String ignored) {
        this.ignored = ignored;
    }

    public String getIgnoreRemoved() {
        return ignoreRemoved;
    }

    public void setIgnoreRemoved(String ignoreRemoved) {
        this.ignoreRemoved = ignoreRemoved;
    }

    public String getWarnPrefix() {
        return warnPrefix;
    }

    public void setWarnPrefix(String warnPrefix) {
        this.warnPrefix = warnPrefix;
    }

    public String getInfoPrefix() {
        return infoPrefix;
    }

    public void setInfoPrefix(String infoPrefix) {
        this.infoPrefix = infoPrefix;
    }

    public String getErrorPrefix() {
        return errorPrefix;
    }

    public void setErrorPrefix(String errorPrefix) {
        this.errorPrefix = errorPrefix;
    }

    public String getBasicHyToolsPrefix() {
        return basicHyToolsPrefix;
    }

    public void setBasicHyToolsPrefix(String basicHyToolsPrefix) {
        this.basicHyToolsPrefix = basicHyToolsPrefix;
    }
}
