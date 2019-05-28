package hu.martinmarkus.basichytools.utils;

import hu.martinmarkus.basichytools.models.placeholders.IPlaceholder;
import hu.martinmarkus.basichytools.models.placeholders.Placeholder;

public class PlaceholderReplacer {

    private IPlaceholder placeholder;

    public PlaceholderReplacer() {
        placeholder = new Placeholder();
    }

    public String replace(String message, String... placeholderValues) {
        if (message == null || message.isEmpty() || placeholderValues.length == 0) {
            return message;
        }

        String helperMessage;
        for (String placeholderValue : placeholderValues) {
            helperMessage = message;
            message = message.replaceFirst(placeholder.getValue(), placeholderValue);

            if (message.equals(helperMessage)) {
                break;      // no change has happened
            }
        }

        return message;
    }
}
