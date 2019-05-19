package hu.martinmarkus.basichytools.configmanaging.placeholders;

public class Placeholder implements IPlaceholder {

    private String placeholder = "%value%";

    @Override
    public String getValue() {
        return placeholder;
    }
}
