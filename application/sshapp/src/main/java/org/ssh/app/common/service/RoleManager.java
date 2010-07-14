package org.ssh.app.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssh.app.common.dao.RoleDao;
import org.ssh.app.common.entity.Role;
import org.ssh.app.orm.hibernate.EntityService;

@Service
@Transactional
public class RoleManager extends EntityService<Role, String> {
    private static Logger logger = LoggerFactory.getLogger(RoleManager.class);

    @Autowired
    private RoleDao roleDao;

    public RoleDao getEntityDao() {
        return roleDao;
    }
}
