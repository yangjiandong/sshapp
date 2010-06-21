package org.ssh.app.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ssh.app.example.dao.RoleDao;
import org.ssh.app.example.dao.UserDao;
import org.ssh.app.example.entity.Role;
import org.ssh.app.example.entity.User;

@Component
@Transactional
public class RoleService {

    private static Logger logger = LoggerFactory.getLogger(RoleService.class);

    @Autowired
    @Qualifier("sp_roleDao")
    private RoleDao userDao;

    public void initData() {
        if (this.userDao.getBookCount().longValue() != 0) {
            return;
        }

        Role b = new Role();
        b.setName("ROLE_ADMIN");
        b.setDescn("管理员角色");
        userDao.save(b);

        b = new Role();
        b.setName("ROLE_USER");
        b.setDescn("用户角色");
        userDao.save(b);
    }
}
