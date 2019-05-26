package hu.martinmarkus.basichytools.initializers.iocfactories;

public interface IObjectFactory<T> {
    T getBean(String beanName);
    void clear();
}
