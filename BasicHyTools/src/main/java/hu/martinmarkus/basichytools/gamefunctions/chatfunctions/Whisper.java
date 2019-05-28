package hu.martinmarkus.basichytools.gamefunctions.chatfunctions;

import hu.martinmarkus.basichytools.configmanagement.DefaultConfigManager;
import hu.martinmarkus.basichytools.configmanagement.GroupManager;
import hu.martinmarkus.basichytools.configmanagement.UserManager;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.DefaultConfig;
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
                // send whisper for both users
                executor.sendMessage(buildWhisperToMessage(message));
                addressee.sendMessage(buildWhisperFromMessage(message));
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
}
