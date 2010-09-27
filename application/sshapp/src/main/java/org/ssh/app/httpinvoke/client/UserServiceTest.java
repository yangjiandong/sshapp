package org.ssh.app.httpinvoke.client;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.ssh.app.httpinvoke.domain.User;
import org.ssh.app.httpinvoke.service.UserService;

public class UserServiceTest {
    private Logger logger = Logger.getLogger(UserServiceTest.class);
    private ApplicationContext context;

    private UserService userService;

    @Before
    public void initialize() {
        context = new ClassPathXmlApplicationContext("classpath:/httpinvoke/applicationContext.httpinvoke.test.xml");
        userService = (UserService) context.getBean("userService");
    }

    @Test
    public void getUser() {
        User user = userService.getUser("zlex");
        if (logger.isDebugEnabled()) {
            logger.debug("user[" + user + "]");
        }

        System.out.println("user[" + user + "]");
    }
}