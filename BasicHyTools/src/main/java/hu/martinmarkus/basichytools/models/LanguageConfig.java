package hu.martinmarkus.basichytools.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hu.martinmarkus.basichytools.configmanaging.placeholders.IPlaceholderReplacer;
import hu.martinmarkus.basichytools.configmanaging.placeholders.PlaceholderReplacer;

public class LanguageConfig {

    @JsonIgnore
    private IPlaceholderReplacer placeholderReplacer;

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
    private String notEnoughXp;
    private String errorHasOccurred;

    public LanguageConfig() {
        placeholderReplacer = new PlaceholderReplacer();
    }

    public String getPlaceholderCorrected(String mesasge, String ... placeholderValues) {
        // TODO: add this method call to all getters
        return placeholderReplacer.replace(mesasge, placeholderValues);
    }

}
