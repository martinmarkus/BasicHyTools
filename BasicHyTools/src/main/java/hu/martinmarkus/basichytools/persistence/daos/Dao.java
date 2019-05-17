package hu.martinmarkus.basichytools.persistence.daos;

import hu.martinmarkus.basichytools.models.User;
import hu.martinmarkus.basichytools.persistence.PersistenceMode;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configreaders.ConfigReader;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configreaders.JsonConfigReader;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configreaders.YamlConfigReader;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configwriters.ConfigWriter;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configwriters.JsonConfigWriter;
import hu.martinmarkus.configmanagerlibrary.fileprocessing.configwriters.YamlConfigWriter;

public class Dao<T> {
    ConfigReader<T> configReader;
    ConfigWriter<T> configWriter;

    public Dao(Class<T> cls, String path, String persistenceMode) {
        switch (persistenceMode) {
            case PersistenceMode.YAML_FILE:
                initForYaml(cls, path);
                break;
            case PersistenceMode.JSON_FILE:
                initForJson(cls, path);
                break;
                /* MSQL is not implemented
            case PersistenceMode.MYSQL:
                break;
                */
            default:
                initForYaml(cls, path);
                break;
        }
        configReader = new YamlConfigReader<>(cls, path);
        configWriter = new YamlConfigWriter<>(cls, path);
    }

    private void initForYaml(Class<T> cls, String path) {
        configReader = new YamlConfigReader<>(cls, path);
        configWriter = new YamlConfigWriter<>(cls, path);
    }

    private void initForJson(Class<T> cls, String path) {
        configReader = new JsonConfigReader<>(cls, path);
        configWriter = new JsonConfigWriter<>(cls, path);
    }
}
