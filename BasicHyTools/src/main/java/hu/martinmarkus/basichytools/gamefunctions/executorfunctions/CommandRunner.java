package hu.martinmarkus.basichytools.gamefunctions.executorfunctions;

import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.User;

public class CommandRunner<T> extends GameFunction<T> {

    public CommandRunner(User executor /*add other function params*/) {
        super(executor,  "");

        initRawCommand();   // must be called for correct logging
    }

    @Override
    public void execute() {
        super.runFunction(() -> {
            // TODO: implement function
            System.out.println(this.getClass().getName() + " function is not implemented");
        });
    }

    @Override
    public T executeWithReturnValue() {
        execute();
        return null;
    }

    @Override
    public void initRawCommand() {
        super.rawCommand = "empty default raw command";
        // required for raw command logging
    }
}