package hu.martinmarkus.basichytools.functions;

import hu.martinmarkus.basichytools.models.User;

import java.util.List;
import java.util.concurrent.Callable;

/*
    The superclass of all functions
 */

public abstract class BaseFunction<T> {
    private String name;
    private String permission;
    private String command;
    private String description;

    private List<String> aliases;

    private User executorUser;
    private Callable<T> functionCallable;

    public BaseFunction(User executorUser) {
        this.executorUser = executorUser;
    }

    public T execute(Callable<T> callable) {
        if (callable == null || !hasPermission()) {
            return null;
        }

        T result = null;

        try {
            result =  callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void execute(Runnable runnable) {
        if (runnable == null || !hasPermission()) {
            return;
        }

        runnable.run();
    }

    private boolean hasPermission() {
        return executorUser.hasPermission(permission);
    }

}
