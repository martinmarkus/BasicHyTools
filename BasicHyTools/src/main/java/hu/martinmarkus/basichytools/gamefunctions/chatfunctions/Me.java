package hu.martinmarkus.basichytools.gamefunctions.chatfunctions;

import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.GlobalMessage;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.utils.ChatMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.text.AttributeSet;

@Component
public class Me extends GameFunction {

    @Autowired
    public Me(@Value("me") String functionName) {
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
            ChatMessageBuilder builder = new ChatMessageBuilder();

            for (int i = 1; i < commandArgs.length; i++) {
                message.append(commandArgs[i]);
                if (i != commandArgs.length - 1) {
                    message.append(" ");
                }
            }

            String prefix = executor.getUserPrefix();
            String suffix = executor.getUserSuffix();
            String userName = executor.getName();

            String fullMessage = builder.buildMessage(prefix, suffix, userName, message.toString(), false);
            GlobalMessage.send(fullMessage);
        });
    }
}
