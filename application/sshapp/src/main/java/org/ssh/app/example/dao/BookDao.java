package org.ssh.app.example.dao;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;

import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Component;

import org.springside.modules.orm.hibernate.HibernateDao;

import org.ssh.app.example.entity.Book;

import java.util.List;


@Component
public class BookDao extends HibernateDao<Book, String> {
    private static final String COUNT_BOOKS = "select count(b) from Book b";

    /**
     * count book.
     */
    public Long getBookCount() {
        return findUnique(COUNT_BOOKS);
    }

    public List<Book> getAll2() {
        Criteria criteria = getSession().createCriteria(entityClass);
        //criteria.setCacheable(true);

        return criteria.list();
    }

    //参考 hibernate3.5.4_reference.pdf
    //Criteria p227
    //大小写敏感 ? American 换成 american 没找到相应记录
    public List<Book> getBooksByTitle(String name) {
        List books = getSession().createCriteria(Book.class)
                         .add(Restrictions.like("title", "%" + name + "%"))
                         .list();

        return books;
    }

    //or
    public List<Book> getBooksByTitleOrIsbn(String name) {
        List books = getSession().createCriteria(Book.class)
                         .add(Restrictions.or(Restrictions.like("isbn",
                        name + "%"), Restrictions.like("title", name + "%")))
                         .list();

        return books;
    }

    //直接使用sql
    public List<Book> getBooksBySql(String name) {
        List books = getSession().createCriteria(Book.class)
                         .add(Restrictions.sqlRestriction(
                    "lower(title) like lower(?)", "%" + name + "%",
                    Hibernate.STRING)).list();

        return books;
    }
}
