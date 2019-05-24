package hu.martinmarkus.basichytools.models.placeholders;

public class Placeholder implements IPlaceholder {

    private String placeholder = "%value%";

    @Override
    public String getValue() {
        return placeholder;
    }
}
