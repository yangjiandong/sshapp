package org.ssh.app.example.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.example.entity.UserRole;

@Component("sp_userRoleDao")
public class UserRoleDao extends HibernateDao<UserRole, Long> {
    private static final String COUNT_BOOKS = "select count(b) from "
            + UserRole.class.getName() + " b";

    /**
     * count book.
     */
    public Long getBookCount() {
        return findUnique(COUNT_BOOKS);
    }

    public List<UserRole> getAll2() {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setCacheable(true);
        return criteria.list();
    }
}
