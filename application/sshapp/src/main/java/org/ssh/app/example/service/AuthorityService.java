package org.ssh.app.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ssh.app.example.dao.AuthorityDao;
import org.ssh.app.example.entity.Authority;


@Component
@Transactional
public class AuthorityService {

    private static Logger logger = LoggerFactory.getLogger(AuthorityService.class);

    @Autowired
    private AuthorityDao userDao;

    public void initData() {
        if (this.userDao.getBookCount().longValue() != 0) {
            return;
        }

        Authority b = new Authority();
        b.setUsername("admin");
        b.setAuthority("ROLE_ADMIN");
        userDao.save(b);

        b = new Authority();
        b.setUsername("admin");
        b.setAuthority("ROLE_USER");
        userDao.save(b);

        b = new Authority();
        b.setUsername("user");
        b.setAuthority("ROLE_USER");
        userDao.save(b);
    }
}
