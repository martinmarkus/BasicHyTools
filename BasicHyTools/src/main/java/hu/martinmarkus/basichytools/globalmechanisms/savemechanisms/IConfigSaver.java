package hu.martinmarkus.basichytools.globalmechanisms.savemechanisms;

public interface IConfigSaver<T> {
    void startAutoSave(int saveInterval);
    void stopAutoSave();
    void saveNow(T value);
}
