package hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms;

import hu.martinmarkus.basichytools.configmanagement.managers.LanguageConfigManager;

import java.util.List;
import java.util.Random;

public class SwearFilter {

    private final String[] CENSOR_CHARS = new String[]{"*", "%", "#", "&", "@"};

    private List<String> censoredWords;

    public SwearFilter() {
        censoredWords = LanguageConfigManager.getInstance().getLanguageConfig().getCensoredWords();

        for (int i = 0; i < censoredWords.size(); i++) {
            String actualWord = censoredWords.get(i);
            censoredWords.set(i, actualWord.toLowerCase());
        }
    }

    public boolean containsCensoredWord(String message) {
        return censoredWords.contains(message.toLowerCase());
    }

    public String doCensoring(String message) {
        for (String censoredWord : censoredWords) {
            if (message.toLowerCase().contains(censoredWord)) {
                int messageLength = message.length();
                String randomChars = getCensoredChars(messageLength);
                message = message.replace(censoredWord, randomChars);
            }
        }

        return message;
    }

    private String getCensoredChars(int charLength) {
        Random rand = new Random();
        StringBuilder censoredChars = new StringBuilder();
        int randomIndex;

        for (int i = 0; i < charLength; i++) {
            randomIndex = rand.nextInt(CENSOR_CHARS.length);
            censoredChars.append(CENSOR_CHARS[randomIndex]);
        }

        return censoredChars.toString();
    }
}
