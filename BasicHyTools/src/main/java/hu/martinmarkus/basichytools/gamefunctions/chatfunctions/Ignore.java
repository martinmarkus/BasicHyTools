package hu.martinmarkus.basichytools.gamefunctions.chatfunctions;

import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Ignore extends GameFunction {

    @Autowired
    public Ignore(@Value("ignore") String functionName) {
        super(functionName);
    }

    @Override
    public void setRequiredParams(String rawCommand, User executor) {
        super.rawCommand = rawCommand;
        super.executor = executor;
        initializeCooldownContainer();
    }

    @Override
    public Object executeWithReturnValue() {
        execute();
        return null;
    }

    @Override
    public void execute() {
        super.runFunction(() -> {
            String ignoredUserName = rawCommand.split(" ")[1];
            String message;

            if (executor.isIgnoring(ignoredUserName)) {
                executor.removeIgnored(ignoredUserName);
                message = languageConfig.getIgnoreRemoved();
            } else {
                executor.addIgnored(ignoredUserName);
                message = languageConfig.getIgnored();
            }

            message = StringUtil.replace(message, ignoredUserName);
            executor.sendMessage(message);
        });
    }
}
