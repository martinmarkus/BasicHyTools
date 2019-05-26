package hu.martinmarkus.basichytools.configmanagement.initializers.ioc;

import hu.martinmarkus.basichytools.models.Group;

public class GroupFactory extends ObjectFactory<Group> implements IGroupFactory{
    public GroupFactory() {
        super("groupsSettings.xml");
    }
}
