package org.ssh.app.example.dao;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.example.entity.Category;

@Component
public class CategoryDao extends HibernateDao<Category, Integer> {
    private static final String COUNTS = "select count(b) from Category b";

    /**
     * count .
     */
    public Long getCategoryCount() {
        return findUnique(COUNTS);
    }
}
