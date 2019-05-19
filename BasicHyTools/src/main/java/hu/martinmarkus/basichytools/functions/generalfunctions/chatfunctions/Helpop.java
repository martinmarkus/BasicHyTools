package hu.martinmarkus.basichytools.functions.generalfunctions.chatfunctions;

import hu.martinmarkus.basichytools.functions.BaseFunction;
import hu.martinmarkus.basichytools.models.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Helpop <T> extends BaseFunction<T> {
    public Helpop(User executor /*add other function params*/) {
        super(executor, "");
    }

    @Override
    public void execute() {
        super.runFunction(new Runnable() {
            @Override
            public void run() {
                // TODO: implement function
            }
        });
    }

    @Override
    public T executeWithReturnValue() {
        throw new NotImplementedException();
    }
}
