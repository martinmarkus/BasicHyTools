package hu.martinmarkus.basichytools.configmanagement.initializers.ioc;

import hu.martinmarkus.basichytools.gamefunctions.GameFunction;

public class GameFunctionFactory extends ObjectFactory<GameFunction> implements IGameFunctionFactory {
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
