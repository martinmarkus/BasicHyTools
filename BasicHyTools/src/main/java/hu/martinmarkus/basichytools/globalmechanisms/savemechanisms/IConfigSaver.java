package hu.martinmarkus.basichytools.globalmechanisms.savemechanisms;

public interface IConfigSaver {
    void startAutoSave();
    void stopAutoSave();
    void saveNow();
}
