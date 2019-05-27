package hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms;

import hu.martinmarkus.basichytools.configmanagement.DefaultConfigManager;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ChatCooldown {
    private static ChatCooldown chatCooldown;

    private final int INITIAL_DELAY = 0;
    private final int CHECK_INTERVAL = 1;

    private Map<String, Integer> chatMessageCooldown;
    private ScheduledExecutorService executorService;
    private boolean isRunning;
    private int deafultChatCooldown;

    public static ChatCooldown getInstance() {
        if (chatCooldown == null) {
            chatCooldown = new ChatCooldown();
        }

        return chatCooldown;
    }

    private ChatCooldown() {
        chatMessageCooldown = new HashMap<>();
        deafultChatCooldown = DefaultConfigManager.getInstance().getDefaultConfig().getChatCooldown();
        isRunning = false;
    }

    public void startCooldownCheck() {
        if (isRunning) {
            return;
        }

        isRunning = true;
        executorService = Executors.newScheduledThreadPool(0);
        executorService.scheduleAtFixedRate(this::checkCooldowns,
                INITIAL_DELAY, CHECK_INTERVAL, TimeUnit.SECONDS);
    }

    public void stopCooldownCheck() {
        executorService.shutdownNow();
        isRunning = false;
    }

    private void checkCooldowns() {
        Iterator iterator = chatMessageCooldown.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            int cooldown = (Integer) pair.getValue();

            pair.setValue(cooldown - CHECK_INTERVAL);

            if (cooldown <= 0) {
                iterator.remove();
            }
        }
    }

    public synchronized void addChatCooldown(String userName) {
        if (!chatMessageCooldown.containsKey(userName)) {
            chatMessageCooldown.put(userName, deafultChatCooldown);
        }
    }

    public synchronized void removeChatCooldown(String userName) {
        if (chatMessageCooldown.containsKey(userName)) {
            chatMessageCooldown.remove(userName);
        }
    }

    public synchronized boolean containsChatCooldown(String userName) {
        return chatMessageCooldown.containsKey(userName);
    }

    public synchronized int getCooldownValue(String userName) {
        if (chatMessageCooldown.containsKey(userName)) {
            return chatMessageCooldown.get(userName);
        }

        return 0;
    }
}
