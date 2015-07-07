/**
 * 
 */
package cee.demo.spring.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

/**
 * cee.demo.spring.utils.SpringBeanFactory.java
 *
 * @author wangcee
 *
 * @version $Revision:$
 *          $Author:$
 */
public class SpringBeanFactory {

	private static ApplicationContext springApplicationContext;
    
    private static final String[] CONTEXT_FILES = 
    	new String[] {
			"/spring/spring-config.xml"
		};

    private synchronized static ApplicationContext getApplicationContext() {
        if (springApplicationContext == null) {
        	springApplicationContext = new ClassPathXmlApplicationContext(CONTEXT_FILES);
        }
        return springApplicationContext;
    }
    
    public static <T> T getBean(String beanName) {
        Assert.notNull(beanName);
        return (T) getApplicationContext().getBean(beanName);
    }
	
}
