package hu.martinmarkus.basichytools.configmanagement.savemechanisms;

public interface IConfigSaver {
    void startAutoSave();
    void stopAutoSave();
    void saveNow();
}
