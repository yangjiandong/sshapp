package org.ssh.app.example.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.example.entity.User;

@Component("sp_userDao")
public class UserDao extends HibernateDao<User, Long> {
    private static final String COUNT_BOOKS = "select count(b) from " + User.class.getName() + " b";

    /**
     * count book.
     */
    public Long getBookCount() {
        return findUnique(COUNT_BOOKS);
    }

    public List<User> getAll2() {
        Criteria criteria = getSession().createCriteria(entityClass);
        criteria.setCacheable(true);
        return criteria.list();
    }
}
