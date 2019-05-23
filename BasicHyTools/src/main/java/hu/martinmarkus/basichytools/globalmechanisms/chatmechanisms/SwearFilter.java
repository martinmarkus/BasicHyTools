package hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms;

import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;

import java.util.List;
import java.util.Random;

public class SwearFilter {

    private final String CENSOR_CHAR = "*";

    private List<String> censoredWords;

    public SwearFilter() {
        censoredWords = LanguageConfigManager.getInstance().getLanguageConfig().getCensoredWords();

        for (int i = 0; i < censoredWords.size(); i++) {
            String actualWord = censoredWords.get(i);
            censoredWords.set(i, actualWord.toLowerCase());
        }
    }

    public boolean containsCensoredWord(String message) {
        if (message == null || message.isEmpty()) {
            return false;
        }

        for (String censoredWord : censoredWords) {
            if (censoredWord == null) {
                continue;
            }

            if (containsIgnoreCase(message, censoredWord)) {
                return true;
            }
        }
        return false;
    }

    public String doCensoring(String message) {
        if (message == null || message.isEmpty()) {
            return null;
        }

        for (String censoredWord : censoredWords) {
            if (censoredWord == null) {
                continue;
            }

            if (containsIgnoreCase(message, censoredWord)) {
                String censorChars = getCensoredChars(censoredWord.length());
                message = message.replaceAll("(?i)" + censoredWord, censorChars);
            }
        }

        return message;
    }

    private String getCensoredChars(int charLength) {
        StringBuilder censoredChars = new StringBuilder();

        for (int i = 0; i < charLength; i++) {
            censoredChars.append(CENSOR_CHAR);
        }

        return censoredChars.toString();
    }

    private boolean containsIgnoreCase(String str, String searchStr) {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }
}
