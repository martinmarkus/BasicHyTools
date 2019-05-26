package hu.martinmarkus.basichytools.configmanagement.initializers.ioc;

import hu.martinmarkus.basichytools.gamefunctions.GameFunction;

public class GameFunctionFactory extends ObjectFactory<GameFunction> implements IGameFunctionFactory {
    private static GameFunctionFactory gameFunctionFactory;
    private static final String XML = "gameFunctionConfig.xml";

    public static GameFunctionFactory getInstance() {
        if (gameFunctionFactory == null) {
            gameFunctionFactory = new GameFunctionFactory(XML);
        }
        return gameFunctionFactory;
    }

    private GameFunctionFactory(String configFile) {
        super(configFile);
    }
}
