package hu.martinmarkus.basichytools.utils.repeatingfunctions;

import hu.martinmarkus.basichytools.models.containers.CooldownContainer;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FunctionCooldown {
    private static FunctionCooldown functionCooldown;

    private final int INITIAL_DELAY = 0;
    private final int CHECK_INTERVAL = 1;

    private List<CooldownContainer> cooldownContainers;
    private ScheduledExecutorService executorService;
    private boolean isRunning;

    public static FunctionCooldown getInstance() {
        if (functionCooldown == null) {
            functionCooldown = new FunctionCooldown();
        }

        return functionCooldown;
    }

    private FunctionCooldown() {
        this.cooldownContainers = new ArrayList<>();
        this.isRunning = false;
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

    private void checkCooldowns() {
        for (ListIterator<CooldownContainer> iter = cooldownContainers.listIterator(); iter.hasNext(); ) {
            CooldownContainer cooldownContainer = iter.next();
            int cooldownSeconds = cooldownContainer.getCooldownSeconds();

            cooldownContainer.setCooldownSeconds(cooldownSeconds - CHECK_INTERVAL);

            if (cooldownSeconds <= 0) {
                iter.remove();
            }
        }
    }

    public synchronized void stopCooldownCheck() {
        isRunning = false;
        executorService.shutdownNow();
        cooldownContainers = new ArrayList<>();
    }

    public synchronized int getCooldownValueOf(CooldownContainer cooldownContainer) {
        for (CooldownContainer aCooldownContainer : cooldownContainers) {
            String checkName = aCooldownContainer.getUserName();
            String passedName = cooldownContainer.getUserName();

            String checkFunction = aCooldownContainer.getFunctionName();
            String passedFunction = cooldownContainer.getFunctionName();

            if (checkName.equalsIgnoreCase(passedName) && checkFunction.equalsIgnoreCase(passedFunction)) {
                return aCooldownContainer.getCooldownSeconds();
            }
        }
        return 0;
    }

    public synchronized void addCooldown(CooldownContainer cooldownContainer) {
        boolean isOnCooldown = isOnCooldown(cooldownContainer);

        if (isOnCooldown) {
            return;
        }

        int cooldown = cooldownContainer.getCooldownSeconds();
        if (cooldown <= 0) {
            return;
        }

        cooldownContainers.add(cooldownContainer);
    }

    public synchronized void removeCooldown(CooldownContainer cooldownContainer) {
        boolean isOnCooldown = isOnCooldown(cooldownContainer);

        if (!isOnCooldown) {
            return;
        }

        doIteratively(cooldownContainer, () -> {
            cooldownContainers.remove(cooldownContainer);
            return false;
        });
    }

    public synchronized boolean isOnCooldown(CooldownContainer cooldownContainer) {
        return (Boolean) doIteratively(cooldownContainer, () -> true);
    }

    private synchronized Object doIteratively(CooldownContainer cooldownContainer, Callable<?> callable) {
        for (CooldownContainer aCooldownContainer : cooldownContainers) {
            String checkName = aCooldownContainer.getUserName();
            String passedName = cooldownContainer.getUserName();

            String checkFunction = aCooldownContainer.getFunctionName();
            String passedFunction = cooldownContainer.getFunctionName();

            if (checkName.equalsIgnoreCase(passedName) && checkFunction.equalsIgnoreCase(passedFunction)) {
                try {
                    return callable.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
}
