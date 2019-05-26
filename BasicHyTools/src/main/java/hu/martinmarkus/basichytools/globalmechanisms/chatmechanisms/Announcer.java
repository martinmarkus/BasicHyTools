package hu.martinmarkus.basichytools.globalmechanisms.chatmechanisms;

import hu.martinmarkus.basichytools.configmanagement.DefaultConfigManager;
import hu.martinmarkus.basichytools.models.DefaultConfig;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Announcer {
    private static Announcer announcer;

    private ScheduledExecutorService executorService;
    private List<String> messages;
    private int announceInterval;
    private boolean isRunning;
    private final int INITIAL_DELAY = 60;

    public static Announcer getInstance() {
        if (announcer == null) {
            announcer = new Announcer();
        }

        return announcer;
    }

    private Announcer() {
        DefaultConfig defaultConfig = DefaultConfigManager.getInstance().getDefaultConfig();
        announceInterval = defaultConfig.getAnnouncerInterval();
        messages = defaultConfig.getAnnouncerMessages();
        this.isRunning = false;
    }

    public void startAnnouncing() {
        if (isRunning) {
            return;
        }

        isRunning = true;
        executorService = Executors.newScheduledThreadPool(0);
        executorService.scheduleAtFixedRate(this::announceRandom,
                INITIAL_DELAY, announceInterval, TimeUnit.SECONDS);
    }

    public void stopAnnouncing() {
        executorService.shutdownNow();
        isRunning = false;
    }

    private void announceRandom() {
        Random rand = new Random();
        int random = rand.nextInt(messages.size());

        String messageToAnnounce = messages.get(random);
        // TODO: announce message
        System.out.println("announcement test: " + messageToAnnounce);
    }
}
