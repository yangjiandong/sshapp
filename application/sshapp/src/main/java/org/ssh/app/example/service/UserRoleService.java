package org.ssh.app.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ssh.app.example.dao.UserRoleDao;
import org.ssh.app.example.entity.UserRole;

@Component
@Transactional
public class UserRoleService {

    private static Logger logger = LoggerFactory
            .getLogger(UserRoleService.class);

    @Autowired
    @Qualifier("sp_userRoleDao")
    private UserRoleDao userDao;

    public void initData() {
        if (this.userDao.getBookCount().longValue() != 0) {
            return;
        }

        UserRole b = new UserRole();
        b.setUserid(1L);
        b.setRoleid(1L);
        userDao.save(b);

        b = new UserRole();
        b.setUserid(1L);
        b.setRoleid(2L);
        userDao.save(b);

        b = new UserRole();
        b.setUserid(2L);
        b.setRoleid(2L);
        userDao.save(b);

    }
}
