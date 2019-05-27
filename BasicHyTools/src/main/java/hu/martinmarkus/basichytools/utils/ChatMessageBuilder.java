package hu.martinmarkus.basichytools.utils;

public class ChatMessageBuilder {

    public String buildMessage(String prefix, String suffix, String userName, String message) {
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

        return prefix.concat(userName).concat(suffix).concat(": ").concat(message);
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
