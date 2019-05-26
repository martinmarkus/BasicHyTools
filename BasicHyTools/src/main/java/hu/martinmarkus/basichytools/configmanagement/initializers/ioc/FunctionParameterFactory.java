package hu.martinmarkus.basichytools.configmanagement.initializers.ioc;

import hu.martinmarkus.basichytools.models.FunctionParameter;

public class FunctionParameterFactory extends ObjectFactory<FunctionParameter> implements IFunctionParameterFactory {
    private static FunctionParameterFactory functionParameterFactory;
    private static final String XML = "functionParameterConfig.xml";

    public static FunctionParameterFactory getInstance() {
        if (functionParameterFactory == null) {
            functionParameterFactory = new FunctionParameterFactory(XML);
        }
        return functionParameterFactory;
    }

    private FunctionParameterFactory(String configFile) {
        super(configFile);
    }
}
