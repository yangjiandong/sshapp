package org.ssh.app.httpinvoke.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssh.app.httpinvoke.domain.User;
import org.ssh.app.httpinvoke.service.UserService;

public class UserServiceImpl implements UserService {
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see
     * org.zlex.spring.httpinvoke.service.UserService#getUser(java.lang.String)
     */
    public User getUser(String username) {
        if (logger.isDebugEnabled()) {
            logger.debug("username:[" + username + "]");
        }
        User user = new User(username, new Date());
        if (logger.isDebugEnabled()) {
            logger.debug("user:[" + user + "]");
        }
        return user;
    }
}