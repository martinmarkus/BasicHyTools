package hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories;

import hu.martinmarkus.basichytools.initializers.iocfactories.ObjectFactory;
import hu.martinmarkus.basichytools.models.DefaultConfig;

public class DefaultConfigFactory extends ObjectFactory<DefaultConfig> {
    public DefaultConfigFactory() {
        super("configSettings/defaultConfigSettings.xml");
    }
}
