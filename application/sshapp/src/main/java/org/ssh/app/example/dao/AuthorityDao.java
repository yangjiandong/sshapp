package org.ssh.app.example.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.example.entity.Authority;

@Component
public class AuthorityDao extends HibernateDao<Authority, Long> {
    private static final String COUNT_BOOKS = "select count(b) from "+ Authority.class.getName() + " b";

    /**
     * count book.
     */
    public Long getBookCount() {
        return findUnique(COUNT_BOOKS);
    }

    public List<Authority> getAll2() {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setCacheable(true);
        return criteria.list();
    }
}
