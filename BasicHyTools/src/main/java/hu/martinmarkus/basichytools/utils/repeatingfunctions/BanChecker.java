package hu.martinmarkus.basichytools.utils.repeatingfunctions;

import hu.martinmarkus.basichytools.configmanagement.BannedUserManager;
import hu.martinmarkus.basichytools.models.BannedUser;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BanChecker {
    private static BanChecker banChecker;

    private ScheduledExecutorService executorService;
    private boolean isRunning;

    private final int CHECK_INTERVAL = 1;
    private final int INITIAL_DELAY = 0;

    public static BanChecker getInstance() {
        if (banChecker == null) {
            banChecker = new BanChecker();
        }

        return banChecker;
    }

    private BanChecker() {
        this.isRunning = false;
    }

    public void startCheck() {
        if (isRunning) {
            return;
        }

        isRunning = true;

        executorService = Executors.newScheduledThreadPool(0);
        executorService.scheduleAtFixedRate(this::doChecking,
                INITIAL_DELAY, CHECK_INTERVAL, TimeUnit.SECONDS);
    }

    public void stopCheck() {
        executorService.shutdownNow();
        isRunning = false;
    }

    private void doChecking() {
        List<BannedUser> bannedUsers = BannedUserManager.getInstance().getBannedUsers();
        for (ListIterator<BannedUser> iter = bannedUsers.listIterator(); iter.hasNext(); ) {
            BannedUser bannedUser = iter.next();
            bannedUser.setBanTimer(bannedUser.getBanTimer() - CHECK_INTERVAL);
            if (bannedUser.getBanTimer() == 0) {
                iter.remove();
            }
        }
    }
}
