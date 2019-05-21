package hu.martinmarkus.basichytools.globalmechanisms.savemechanisms;

public interface IConfigSaver {
    void startAutoSave(int saveInterval);
    void stopAutoSave();
    void saveNow();
}
