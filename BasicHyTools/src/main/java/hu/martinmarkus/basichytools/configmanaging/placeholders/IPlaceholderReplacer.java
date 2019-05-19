package hu.martinmarkus.basichytools.configmanaging.placeholders;

public interface IPlaceholderReplacer {
    String replace(String message, String... placeholderValues);
}
