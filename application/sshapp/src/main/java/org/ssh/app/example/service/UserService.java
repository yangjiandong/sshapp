package org.ssh.app.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ssh.app.example.dao.UserDao;
import org.ssh.app.example.entity.User;


@Component
@Transactional
public class UserService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    @Qualifier("sp_userDao")
    private UserDao userDao;

    public void initData() {
        if (this.userDao.getBookCount().longValue() != 0) {
            return;
        }

        User b = new User();
        b.setUsername("admin");
        b.setPassword("admin");
        b.setEnabled(true);
        userDao.save(b);

        b = new User();
        b.setUsername("user");
        b.setPassword("user");
        b.setEnabled(true);
        userDao.save(b);
    }
}
