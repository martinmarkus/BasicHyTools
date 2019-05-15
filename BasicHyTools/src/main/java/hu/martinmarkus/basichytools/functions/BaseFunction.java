package hu.martinmarkus.basichytools.functions;

import java.util.List;

public abstract class BaseFunction<T> {
    private String permission;
    private List<String> aliases;
    private String description;

    public abstract T execute(String... params);

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public void setAliases(List<String> aliases) {
        this.aliases = aliases;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
