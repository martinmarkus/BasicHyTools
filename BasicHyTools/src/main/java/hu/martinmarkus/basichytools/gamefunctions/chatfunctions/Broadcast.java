package hu.martinmarkus.basichytools.gamefunctions.chatfunctions;

import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.utils.ChatMessageBuilder;
import hu.martinmarkus.basichytools.utils.GlobalMessage;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.utils.StringUtil;
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
            String message =  StringUtil.concatCommandToMessage(rawCommand, 1);

            String fulMessage = broadcastPrefix.concat(message);
            GlobalMessage.sendWithCensor(executor, fulMessage);
        });
    }
}
