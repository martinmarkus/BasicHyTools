package hu.martinmarkus.basichytools.configmanagement.configsavers;

public interface IConfigSaver {
    void startAutoSave();
    void stopAutoSave();
    void saveNow();
}
