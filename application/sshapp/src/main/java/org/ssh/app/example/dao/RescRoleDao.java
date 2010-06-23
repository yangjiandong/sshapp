package org.ssh.app.example.dao;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.example.entity.RescRole;

@Component
public class RescRoleDao extends HibernateDao<RescRole, Long> {
    private static final String COUNT_BOOKS = "select count(b) from "
            + RescRole.class.getName() + " b";

    /**
     * count book.
     */
    public Long getBookCount() {
        return findUnique(COUNT_BOOKS);
    }

}
