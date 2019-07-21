package hu.martinmarkus.basichytools.gamefunctions.chatfunctions;

import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SocialSpy extends GameFunction {

    @Autowired
    public SocialSpy(@Value("socialSpy") String functionName) {
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
            boolean socialSpyState;
            String message;

            if (executor.isSocialSpyActive()) {
                socialSpyState = false;
                message = languageConfig.getSocialSpyDeactivated();
            } else {
                socialSpyState = true;
                message = languageConfig.getSocialSpyActivated();
            }

            executor.setSocialSpyActive(socialSpyState);
            executor.sendMessage(message);
        });
    }
}
