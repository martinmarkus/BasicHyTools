package hu.martinmarkus.basichytools.synchronization;

public class Synchronizer implements ISynchronizer {
    private final Integer notifier = 1;

    public void waitRun() {
        synchronized(notifier) {
            try {
                notifier.wait();
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void continueRun() {
        synchronized(notifier) {
            notifier.notify();
        }
    }
}
