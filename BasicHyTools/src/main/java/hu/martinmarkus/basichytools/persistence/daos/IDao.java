package hu.martinmarkus.basichytools.persistence.daos;

import hu.martinmarkus.configmanagerlibrary.threading.ResultListener;

import java.util.List;

public interface IDao<T> {
    void select(String valueId, ResultListener<T> resultListener);
    void insert(String valueId, T value);
    void update(String valueId, T value);
    void contains(T value, ResultListener<Boolean> resultListener);

    void selectAll(ResultListener<List<T>> resultListener);
    void insertAll(List<String> valueIds, List<T> values);
    void updateAll(List<String> valueIds, List<T> values);

    void insert(String valueId, T value, ResultListener<Boolean> resultListener);
}
