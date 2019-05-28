package hu.martinmarkus.basichytools.gamefunctions.chatfunctions;

import hu.martinmarkus.basichytools.configmanagement.FunctionParameterManager;
import hu.martinmarkus.basichytools.configmanagement.GroupManager;
import hu.martinmarkus.basichytools.configmanagement.UserManager;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.FunctionParameter;
import hu.martinmarkus.basichytools.models.Group;
import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.utils.ChatMessageBuilder;
import hu.martinmarkus.basichytools.utils.PlaceholderReplacer;
import hu.martinmarkus.basichytools.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Whisper extends GameFunction {

    private ChatMessageBuilder builder;
    private String addresseeName;

    @Autowired
    public Whisper(@Value("whisper") String functionName) {
        super(functionName);
    }

    @Override
    public void setRequiredParams(String rawCommand, User executor) {
        super.rawCommand = rawCommand;
        super.executor = executor;
        initializeCooldownContainer();
        builder = new ChatMessageBuilder();
    }

    @Override
    public Object executeWithReturnValue() {
        execute();
        return null;
    }

    @Override
    public void execute() {
        super.runFunction(() -> {
            String message = StringUtil.concatCommandToMessage(rawCommand, 2);
            addresseeName = rawCommand.split(" ")[1];
            User addressee = UserManager.getInstance().getOnlineUser(addresseeName);
            String permission = functionParameter.getPermission();

            if (addressee == null) {
                executor.sendMessage(languageConfig.getUnknownUser());
            } else if (!executor.hasPermission(permission)) {
                executor.sendMessage(languageConfig.getNotEnoughPermission());
            } else if (!addressee.hasPermission(permission)) {
                addressee.sendMessage(languageConfig.getNotEnoughPermission());
                PlaceholderReplacer replacer = new PlaceholderReplacer();
                String executorMessage = replacer.replace(languageConfig.getNotEnoughPermissionForWhisper(), addresseeName);
                executor.sendMessage(executorMessage);
            } else {
                executor.sendMessage(buildWhisperToMessage(message));
                addressee.sendMessage(buildWhisperFromMessage(message));
                notifySocialSpies(message, addressee);
            }
        });
    }

    private String buildWhisperToMessage(String message) {
        Group group = GroupManager.getInstance().getPermissionGroup(executor.getPermissionGroupName());
        if (group == null) {
            return "";
        }

        String prefix = builder.defineTitle(executor.getUserPrefix(), group.getPrefix());
        String suffix = builder.defineTitle(executor.getUserSuffix(), group.getSuffix());

        String fullMessage = builder.buildMessage(prefix, suffix, addresseeName,
                languageConfig.getSeparator(), message);
        return languageConfig.getWhisperFromYou().concat(fullMessage);
    }

    private String buildWhisperFromMessage(String message) {
        Group group = GroupManager.getInstance().getPermissionGroup(executor.getPermissionGroupName());
        if (group == null) {
            return "";
        }

        String prefix = builder.defineTitle(executor.getUserPrefix(), group.getPrefix());
        String suffix = builder.defineTitle(executor.getUserSuffix(), group.getSuffix());

        String addresseeNameWithWhisperSign = addresseeName.concat(languageConfig.getWhisperToYou());
        return builder.buildMessage(prefix, suffix, addresseeNameWithWhisperSign,
                languageConfig.getSeparator(), message);
    }

    private void notifySocialSpies(String message, User addressee) {
        List<User> onlineUsers = UserManager.getInstance().getAllOnlineUsers();
        String socialSpyMessage = buildWhisperMessageForSocialSpy(message, addressee);

        FunctionParameter socialSpyParameter = FunctionParameterManager.getInstance().getByName("socialSpy");
        if (socialSpyParameter == null) {
            return;
        }

        String socialSpyPerm = socialSpyParameter.getPermission();
        onlineUsers.forEach(onlineUser -> {
            if (onlineUser.isOperator() || onlineUser.hasPermission(socialSpyPerm)) {
                if (onlineUser.isSocialSpyActive()) {
                    onlineUser.sendMessage(socialSpyMessage);
                }
            }
        });
    }

    private String buildWhisperMessageForSocialSpy(String message, User addressee) {
        Group executorGroup = GroupManager.getInstance().getPermissionGroup(executor.getPermissionGroupName());
        Group addresseeGroup = GroupManager.getInstance().getPermissionGroup(addressee.getPermissionGroupName());
        if (executorGroup == null || addresseeGroup == null) {
            return "";
        }

        String executorPrefix = builder.defineTitle(executor.getUserPrefix(), executorGroup.getPrefix());
        String executorSuffix = builder.defineTitle(executor.getUserSuffix(), executorGroup.getSuffix());
        String addresseePrefix = builder.defineTitle(addressee.getUserPrefix(), addresseeGroup.getPrefix());
        String addresseeSuffix = builder.defineTitle(addressee.getUserSuffix(), addresseeGroup.getSuffix());

        String separator = languageConfig.getWhisperSeparator();
        String executorWithSeparator = builder.buildMessage(executorPrefix, executorSuffix, executor.getName(), separator, "");
        String addresseeWhisper = builder.buildMessage(addresseePrefix, addresseeSuffix, addresseeName, "", "");
        String socialSpyPrefix = languageConfig.getSocialSpyPrefix();

        return socialSpyPrefix
                .concat(executorWithSeparator)
                .concat(addresseeWhisper)
                .concat(languageConfig.getSeparator())
                .concat(message);
    }
}
