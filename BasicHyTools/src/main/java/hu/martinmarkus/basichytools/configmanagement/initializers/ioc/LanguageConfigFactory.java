package hu.martinmarkus.basichytools.configmanagement.initializers.ioc;

import hu.martinmarkus.basichytools.models.LanguageConfig;

public class LanguageConfigFactory extends ObjectFactory<LanguageConfig> implements  ILanguageConfigFactory {
    public LanguageConfigFactory() {
        super("languageConfigSettings.xml");
    }
}
