package hu.martinmarkus.basichytools.models;

import java.util.List;

public class DefaultConfig {
    private int teleportCooldown;
    private int teleportDelay;
    private int teleportInvulnerability;
    private int healCooldown;
    private boolean allowAfk;
    private int autoAfk;
    private int autoAfkKick;
    private int autoSaveInterval;
    private int tpAcceptCancellation;
    private double startingBalance;
    private double minMoney;
    private double maxMoney;
    private double minPayAmount;
    private int payCooldown;
    private int announcerInterval;
    private List<String> announcerMessages;

    public DefaultConfig(int teleportCooldown, int teleportDelay, int teleportInvulnerability,
                         int healCooldown, boolean allowAfk, int autoAfk, int autoAfkKick, int autoSaveInterval,
                         int tpAcceptCancellation, double startingBalance, double minMoney, double maxMoney,
                         double minPayAmount, int payCooldown, int announcerInterval, List<String> announcerMessages) {
        this.teleportCooldown = teleportCooldown;
        this.teleportDelay = teleportDelay;
        this.teleportInvulnerability = teleportInvulnerability;
        this.healCooldown = healCooldown;
        this.allowAfk = allowAfk;
        this.autoAfk = autoAfk;
        this.autoAfkKick = autoAfkKick;
        this.autoSaveInterval = autoSaveInterval;
        this.tpAcceptCancellation = tpAcceptCancellation;
        this.startingBalance = startingBalance;
        this.minMoney = minMoney;
        this.maxMoney = maxMoney;
        this.minPayAmount = minPayAmount;
        this.payCooldown = payCooldown;
        this.announcerInterval = announcerInterval;
        this.announcerMessages = announcerMessages;
    }

    public int getTeleportCooldown() {
        return teleportCooldown;
    }

    public void setTeleportCooldown(int teleportCooldown) {
        this.teleportCooldown = teleportCooldown;
    }

    public int getTeleportDelay() {
        return teleportDelay;
    }

    public void setTeleportDelay(int teleportDelay) {
        this.teleportDelay = teleportDelay;
    }

    public int getTeleportInvulnerability() {
        return teleportInvulnerability;
    }

    public void setTeleportInvulnerability(int teleportInvulnerability) {
        this.teleportInvulnerability = teleportInvulnerability;
    }

    public int getHealCooldown() {
        return healCooldown;
    }

    public void setHealCooldown(int healCooldown) {
        this.healCooldown = healCooldown;
    }

    public boolean isAllowAfk() {
        return allowAfk;
    }

    public void setAllowAfk(boolean allowAfk) {
        this.allowAfk = allowAfk;
    }

    public int getAutoAfk() {
        return autoAfk;
    }

    public void setAutoAfk(int autoAfk) {
        this.autoAfk = autoAfk;
    }

    public int getAutoAfkKick() {
        return autoAfkKick;
    }

    public void setAutoAfkKick(int autoAfkKick) {
        this.autoAfkKick = autoAfkKick;
    }

    public int getAutoSaveInterval() {
        return autoSaveInterval;
    }

    public void setAutoSaveInterval(int autoSaveInterval) {
        this.autoSaveInterval = autoSaveInterval;
    }

    public int getTpAcceptCancellation() {
        return tpAcceptCancellation;
    }

    public void setTpAcceptCancellation(int tpAcceptCancellation) {
        this.tpAcceptCancellation = tpAcceptCancellation;
    }

    public double getStartingBalance() {
        return startingBalance;
    }

    public void setStartingBalance(double startingBalance) {
        this.startingBalance = startingBalance;
    }

    public double getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(double minMoney) {
        this.minMoney = minMoney;
    }

    public double getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(double maxMoney) {
        this.maxMoney = maxMoney;
    }

    public double getMinPayAmount() {
        return minPayAmount;
    }

    public void setMinPayAmount(double minPayAmount) {
        this.minPayAmount = minPayAmount;
    }

    public int getPayCooldown() {
        return payCooldown;
    }

    public void setPayCooldown(int payCooldown) {
        this.payCooldown = payCooldown;
    }

    public int getAnnouncerInterval() {
        return announcerInterval;
    }

    public void setAnnouncerInterval(int announcerInterval) {
        this.announcerInterval = announcerInterval;
    }

    public List<String> getAnnouncerMessages() {
        return announcerMessages;
    }

    public void setAnnouncerMessages(List<String> announcerMessages) {
        this.announcerMessages = announcerMessages;
    }
}
