package org.ssh.app.example.dao;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.example.entity.Resc;

@Component
public class RescDao extends HibernateDao<Resc, String> {
    private static final String COUNT_BOOKS = "select count(b) from "
            + Resc.class.getName() + " b";

    /**
     * count book.
     */
    public Long getBookCount() {
        return findUnique(COUNT_BOOKS);
    }
}
