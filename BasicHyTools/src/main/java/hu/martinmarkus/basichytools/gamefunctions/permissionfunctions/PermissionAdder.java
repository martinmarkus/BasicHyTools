package hu.martinmarkus.basichytools.gamefunctions.permissionfunctions;

import hu.martinmarkus.basichytools.gamefunctions.GameFunction;
import hu.martinmarkus.basichytools.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PermissionAdder extends GameFunction {

    @Autowired
    public PermissionAdder(@Value("permissionAdder") String functionName) {
        super(functionName);
    }

    @Override
    public void setRequiredParams(String rawCommand, User executor) {
        super.rawCommand = rawCommand;
        super.executor = executor;
        initializeCooldownContainer();
    }

    @Override
    public Object executeWithReturnValue() {
        execute();
        return null;
    }

    @Override
    public void execute() {
        super.runFunction(() -> {
            // TODO: implement function
            System.out.println(this.getClass().getName() + " function is not implemented");
        });
    }
}
