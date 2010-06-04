package org.ssh.app.example.dao;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.example.entity.Book;

@Component
public class BookDao extends HibernateDao<Book, String> {
    private static final String COUNT_BOOKS = "select count(b) from Book b";

    /**
     * count book.
     */
    public Long getBookCount() {
        return findUnique(COUNT_BOOKS);
    }
}
