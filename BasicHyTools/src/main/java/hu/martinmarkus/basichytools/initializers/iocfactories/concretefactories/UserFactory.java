package hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories;

import hu.martinmarkus.basichytools.initializers.iocfactories.ObjectFactory;
import hu.martinmarkus.basichytools.models.User;

public class UserFactory extends ObjectFactory<User> {
    public UserFactory() {
        super("configSettings/newUserSettings.xml");
    }
}
