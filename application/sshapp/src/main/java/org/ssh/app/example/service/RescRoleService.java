package org.ssh.app.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ssh.app.example.dao.RescRoleDao;
import org.ssh.app.example.entity.RescRole;

@Component
@Transactional
public class RescRoleService {
    private static Logger logger = LoggerFactory.getLogger(RescRoleService.class);

    @Autowired
    private RescRoleDao rescRoleDao;

    public void initData() {
        if (this.rescRoleDao.getBookCount().longValue() != 0) {
            return;
        }

        RescRole b = new RescRole();
        b.setRescId(1L);
        b.setRoleId(1L);
        rescRoleDao.save(b);

        b = new RescRole();
        b.setRescId(2L);
        b.setRoleId(1L);
        rescRoleDao.save(b);

        b = new RescRole();
        b.setRescId(2L);
        b.setRoleId(2L);
        rescRoleDao.save(b);
    }
}
