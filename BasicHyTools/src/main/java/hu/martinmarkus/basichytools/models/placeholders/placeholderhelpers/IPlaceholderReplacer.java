package hu.martinmarkus.basichytools.models.placeholders.placeholderhelpers;

public interface IPlaceholderReplacer {
    String replace(String message, String... placeholderValues);
}
