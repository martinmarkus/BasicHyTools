package hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories;

import hu.martinmarkus.basichytools.initializers.iocfactories.ObjectFactory;
import hu.martinmarkus.basichytools.models.Group;

public class GroupFactory extends ObjectFactory<Group> {
    public GroupFactory() {
        super("configSettings/groupsSettings.xml");
    }
}
