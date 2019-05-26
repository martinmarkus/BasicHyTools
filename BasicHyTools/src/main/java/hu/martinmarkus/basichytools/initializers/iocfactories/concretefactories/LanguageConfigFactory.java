package hu.martinmarkus.basichytools.initializers.iocfactories.concretefactories;

import hu.martinmarkus.basichytools.initializers.iocfactories.ObjectFactory;
import hu.martinmarkus.basichytools.models.LanguageConfig;

public class LanguageConfigFactory extends ObjectFactory<LanguageConfig> {
    public LanguageConfigFactory() {
        super("languageConfigSettings.xml");
    }
}
