package hu.martinmarkus.basichytools.ioc;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ObjectFactory<T> {
    private static final String XML_NAME = "springBeanConfig.xml";
    private static AbstractApplicationContext context;

    public static void initialize() {
        context = new ClassPathXmlApplicationContext(XML_NAME);
    }

    public T getBean(String beanName) {
        try {
            return (T) context.getBean(beanName);
        } catch (Exception e) {
            return null;
        }
    }

    public static void clear() {
        context.close();
    }
}
