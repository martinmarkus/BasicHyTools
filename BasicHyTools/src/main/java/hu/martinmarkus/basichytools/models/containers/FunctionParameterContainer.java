package hu.martinmarkus.basichytools.models.containers;

import hu.martinmarkus.basichytools.gamefunctions.FunctionParameter;

import java.util.List;

public class FunctionParameterContainer {
    private List<FunctionParameter> functionParameters;

    public FunctionParameterContainer(List<FunctionParameter> functionParameters) {
        this.functionParameters = functionParameters;
    }

    public List<FunctionParameter> getFunctionParameters() {
        return functionParameters;
    }

    public void setFunctionParameters(List<FunctionParameter> functionParameters) {
        this.functionParameters = functionParameters;
    }
}
