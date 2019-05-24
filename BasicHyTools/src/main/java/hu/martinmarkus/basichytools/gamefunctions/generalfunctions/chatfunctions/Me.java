package hu.martinmarkus.basichytools.gamefunctions.generalfunctions.chatfunctions;

import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.User;

public class Me<T> extends GameFunction<T> {

    private String meMessage;

    public Me(User executor, String meMessage /*add other function params*/) {
        super(executor, "me");
        this.meMessage = meMessage;

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
        super.rawCommand = "/me " + meMessage;
        // required for raw command logging
    }
}
