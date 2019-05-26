package hu.martinmarkus.basichytools.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ObjectFactory<T> {
    private static ApplicationContext context;
    private final String XML_NAME = "objectFactoryConfiguration.xml";

    public ObjectFactory() {
        if (context == null ) {
            context = new ClassPathXmlApplicationContext(XML_NAME);
        }
    }

    public T getBean(String beanName) {
        try {
            return (T) context.getBean(beanName);
        } catch (Exception e) {
            return null;
        }
    }
}
