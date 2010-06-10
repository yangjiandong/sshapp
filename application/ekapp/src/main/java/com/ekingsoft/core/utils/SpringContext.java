package com.ekingsoft.core.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 不在spring配置范围内的类静态地使用该类的getBean()方法可以得到spring的ApplicationContext
 */
public class SpringContext implements ApplicationContextAware {

    private static ApplicationContext context;
    /* (non-Javadoc)
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }

    /**
     * @return ApplicationContext
     */
     public static ApplicationContext getApplicationContext() {
       return context;
     }

    public static Object getBean(String name){
        return context.getBean(name);
    }
}
