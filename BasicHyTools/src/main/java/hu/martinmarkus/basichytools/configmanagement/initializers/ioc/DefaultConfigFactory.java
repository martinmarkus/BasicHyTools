package hu.martinmarkus.basichytools.configmanagement.initializers.ioc;

import hu.martinmarkus.basichytools.models.DefaultConfig;

public class DefaultConfigFactory extends ObjectFactory<DefaultConfig> implements IDefaultConfigFactory {
    public DefaultConfigFactory() {
        super("defaultConfigSettings.xml");
    }
}
