package hu.martinmarkus.basichytools.gamefunctions.miscenalliousfunctions;

import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.User;

public class Weather extends GameFunction {

    public Weather(User executor /*add other function params*/) {
        super(executor, "");

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
    public Object executeWithReturnValue() {
        execute();
        return null;
    }

    @Override
    public void initRawCommand() {
        super.rawCommand = "empty default raw command";
        // required for raw command logging
    }
}