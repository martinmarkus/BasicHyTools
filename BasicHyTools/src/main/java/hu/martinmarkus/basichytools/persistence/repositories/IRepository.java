package hu.martinmarkus.basichytools.persistence.repositories;

import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

import java.util.List;

public interface IRepository<T> {
    void get(String valueId, ResultListener<T> resultListener);
    void add(String valueId, T value);
    void set(String valueId, T newValue);
    void contains(T value, ResultListener<Boolean> resultListener);

    void getAll(ResultListener<List<T>> resultListener);
    void addAll(List<String> valueIds, List<T> values);
    void setAll(List<String> valueIds, List<T> values);
}
