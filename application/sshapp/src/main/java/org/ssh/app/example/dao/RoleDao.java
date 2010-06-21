package org.ssh.app.example.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.example.entity.Role;

@Component("sp_roleDao")
public class RoleDao extends HibernateDao<Role, Long> {
    private static final String COUNT_BOOKS = "select count(b) from "+ Role.class.getName() + " b";

    /**
     * count book.
     */
    public Long getBookCount() {
        return findUnique(COUNT_BOOKS);
    }

    public List<Role> getAll2() {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setCacheable(true);
        return criteria.list();
    }
}
