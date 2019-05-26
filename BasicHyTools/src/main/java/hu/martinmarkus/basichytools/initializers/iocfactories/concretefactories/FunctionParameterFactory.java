package hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories;

import hu.martinmarkus.basichytools.initializers.iocfactories.ObjectFactory;
import hu.martinmarkus.basichytools.models.FunctionParameter;

public class FunctionParameterFactory extends ObjectFactory<FunctionParameter>{
    public FunctionParameterFactory() {
        super("functionParameterSettings.xml");
    }
}
