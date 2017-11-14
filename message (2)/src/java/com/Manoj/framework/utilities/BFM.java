
package com.Manoj.framework.utilities;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/*
 Bean factory manager
*/
public class BFM {
    
    private static ApplicationContext context;
    
    static {
        context = new ClassPathXmlApplicationContext("Beans.xml");
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }
    
}
