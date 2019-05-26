package hu.martinmarkus.basichytools.configmanagement.initializers.ioc;

import hu.martinmarkus.basichytools.models.FunctionParameter;

public class FunctionParameterFactory extends ObjectFactory<FunctionParameter> implements IFunctionParameterFactory {
    public FunctionParameterFactory() {
        super("functionParameterSettings.xml");
    }
}
