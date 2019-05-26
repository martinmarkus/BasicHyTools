package hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories;

import hu.martinmarkus.basichytools.initializers.iocfactories.ObjectFactory;
import hu.martinmarkus.basichytools.gamefunctions.GameFunction;

public class GameFunctionFactory extends ObjectFactory<GameFunction> {
    private static GameFunctionFactory gameFunctionFactory;

    public static GameFunctionFactory getInstance() {
        if (gameFunctionFactory == null) {
            gameFunctionFactory = new GameFunctionFactory();
        }
        return gameFunctionFactory;
    }

    private GameFunctionFactory() {
        super("gameFunctionSettings.xml");
    }
}
