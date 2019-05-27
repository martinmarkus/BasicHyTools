package hu.martinmarkus.basichytools.utils;

public class ChatMessageBuilder {

    private final String SEPARATOR = ":";

    public String buildMessage(String prefix, String suffix, String userName, String message, boolean useSeparator) {
        if (userName == null) {
            return null;
        }

        if (prefix == null) {
            prefix = "";
        }

        if (suffix == null) {
            suffix = "";
        }

        if (message == null || message.isEmpty()) {
            return null;
        }

        String result = prefix.concat(userName).concat(suffix);
        if (useSeparator) {
            result = result.concat(SEPARATOR);
        }
        return result.concat(" ").concat(message);
    }

    public String defineTitle(String specificTitle, String groupTitle) {
        if (specificTitle == null || specificTitle.isEmpty()) {
            return groupTitle;
        } else {
            return specificTitle;
        }
    }

    public String defineTitle(String title) {
        if (title == null || title.isEmpty()) {
            return "";
        } else {
            return title;
        }
    }
}
