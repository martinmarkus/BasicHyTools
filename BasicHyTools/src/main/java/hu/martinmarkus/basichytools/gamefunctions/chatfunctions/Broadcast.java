package hu.martinmarkus.basichytools.gamefunctions.chatfunctions;

import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.utils.GlobalMessage;
import hu.martinmarkus.basichytools.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Broadcast extends GameFunction {
    private String broadcastPrefix = languageConfig.getBroadcastPrefix();

    @Autowired
    public Broadcast(@Value("broadcast") String functionName) {
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
            String[] commandArgs = rawCommand.split(" ");
            StringBuilder message = new StringBuilder();

            for (int i = 1; i < commandArgs.length; i++) {
                message.append(commandArgs[i]);
                if (i != commandArgs.length - 1) {
                    message.append(" ");
                }
            }

            String separator = languageConfig.getSeparator();
            String fulMessage = broadcastPrefix.concat(separator).concat(message.toString());
            GlobalMessage.send(executor, fulMessage);
        });
    }
}
