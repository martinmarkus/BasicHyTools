package hu.martinmarkus.basichytools.initializers.iocfactories;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ObjectFactory<T> implements IObjectFactory<T> {
    private ApplicationContext context;

    public ObjectFactory(String configFile) {
        try {
            context = new ClassPathXmlApplicationContext(configFile);
        } catch (BeansException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public T getBean(String beanName) {
        try {
            return (T) context.getBean(beanName);
        } catch (Exception e) {
            return null;
        }
    }

    public void clear() {
        ((ConfigurableApplicationContext) context).close();
    }
}
