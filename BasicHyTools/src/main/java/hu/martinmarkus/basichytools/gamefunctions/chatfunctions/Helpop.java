package hu.martinmarkus.basichytools.gamefunctions.chatfunctions;

import hu.martinmarkus.basichytools.configmanagement.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.GroupManager;
import hu.martinmarkus.basichytools.configmanagement.UserManager;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.DefaultConfig;
import hu.martinmarkus.basichytools.models.Group;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.utils.ChatMessageBuilder;
import hu.martinmarkus.basichytools.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Helpop extends GameFunction {

    @Autowired
    public Helpop(@Value("helpOp") String functionName) {
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
            DefaultConfig defaultConfig = DefaultConfigManager.getInstance().getDefaultConfig();
            String readHelpOpPermission = defaultConfig.getGlobalMechanismPermissions().get("readHelpOp");
            String helpOpMessage = StringUtil.concatCommandToMessage(rawCommand);

            List<User> onlineUsers = UserManager.getInstance().getAllOnlineUsers();
            for (User onlineUser : onlineUsers) {
                if (onlineUser.hasPermission(readHelpOpPermission)) {
                    sendHelpOpMessage(onlineUser, helpOpMessage, false);
                }
            }
            if (!executor.hasPermission(readHelpOpPermission)) {
                sendHelpOpMessage(executor, helpOpMessage, true);
            }
        });
    }

    private void sendHelpOpMessage(User receiver, String helpOpMessage, boolean doSpamFiltering) {
        ChatMessageBuilder builder = new ChatMessageBuilder();
        Group group = GroupManager.getInstance().getPermissionGroup(executor.getPermissionGroupName());

        if (group == null) {
            return;
        }

        String prefix = builder.defineTitle(executor.getUserPrefix(), group.getPrefix());
        String suffix = builder.defineTitle(executor.getUserSuffix(), group.getSuffix());
        String userName = executor.getName();

        String messageWithoutHelpOpPrefix = builder.buildMessage(prefix, suffix, userName,
                languageConfig.getSeparator(), helpOpMessage);
        String helpOpPrefix = languageConfig.getHelpOpPrefix().concat(" ");

        receiver.sendMessage(helpOpPrefix.concat(messageWithoutHelpOpPrefix), doSpamFiltering);

    }
}
