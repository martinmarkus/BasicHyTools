package hu.martinmarkus.basichytools.configmanagement.initializers.ioc;

public interface IObjectFactory<T> {
    T getBean(String beanName);
    void clear();
}
