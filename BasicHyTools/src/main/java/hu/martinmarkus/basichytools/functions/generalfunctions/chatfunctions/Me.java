package hu.martinmarkus.basichytools.functions.generalfunctions.chatfunctions;

import hu.martinmarkus.basichytools.functions.BaseFunction;
import hu.martinmarkus.basichytools.models.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Me<T> extends BaseFunction<T> {
    public Me(User executor /*add other function params*/) {
        super(executor, "me");
    }

    @Override
    public void execute() {
        super.runFunction(new Runnable() {
            @Override
            public void run() {
                // TODO: implement function
                System.out.println("hello world");
            }
        });
    }

    @Override
    public T executeWithReturnValue() {
        throw new NotImplementedException();
    }
}
