package hu.martinmarkus.basichytools.gamefunctions.chatfunctions;

import hu.martinmarkus.basichytools.configmanagement.GroupManager;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms.GlobalMessage;
import hu.martinmarkus.basichytools.models.Group;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.utils.ChatMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
            Group group = GroupManager.getInstance().getPermissionGroup(executor.getPermissionGroupName());
            if (group == null) {
                return;
            }

            String[] commandArgs = rawCommand.split(" ");
            StringBuilder message = new StringBuilder();
            ChatMessageBuilder builder = new ChatMessageBuilder();

            for (int i = 1; i < commandArgs.length; i++) {
                message.append(commandArgs[i]);
                if (i != commandArgs.length - 1) {
                    message.append(" ");
                }
            }

            String prefix = builder.defineTitle(executor.getUserPrefix(), group.getPrefix());
            String suffix = builder.defineTitle(executor.getUserSuffix(), group.getSuffix());
            String userName = executor.getName();

            String fullMessage = builder.buildMessage(prefix, suffix, userName, " ", message.toString());
            GlobalMessage.send(executor, fullMessage);
        });
    }
}
