package org.ssh.app.example.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.record.formula.functions.T;
import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.ssh.app.example.dao.BookDao;
import org.ssh.app.example.entity.Book;


@Component
//@Service("accountManager")
//默认将类中的所有函数纳入事务管理.
@Transactional
public class BookService {

    private static Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookDao bookDao;

    @Transactional(readOnly = true)
    public List<Book> getBooks(){
        return bookDao.getAll();
    }

    @Transactional(readOnly = true)
    public List<Book> loadBooks(String sysName) {

        StringBuffer bf = new StringBuffer();
        bf.append("select oid,isbn,title,published ");
        bf.append(" from  t_Book ");

        SQLQuery query = this.bookDao.getSession().createSQLQuery(bf.toString());
        //query.setParameter(0, sysName);
        return query.list();
    }

    @Transactional(readOnly = true)
    public Page<Book> search(final Page<Book> page, List<PropertyFilter> filters) {
        return bookDao.findPage(page, filters);
    }

    public void initData() {
        if (this.bookDao.getBookCount().longValue() != 0) {
            return;
        }

        Book b = new Book();
        b.setIsbn("comto ok");
        b.setTitle("goto American");
        b.setEdition(10L);
        b.setPages(200L);
        b.setPublished("AM");
        bookDao.save(b);

        b = new Book();
        b.setIsbn("omoo");
        b.setTitle("计划生育");
        b.setEdition(10L);
        b.setPages(2000L);
        b.setPublished("AM");
        bookDao.save(b);

        b = new Book();
        b.setIsbn("comtosadfad ok");
        b.setTitle("同要有 面goto American");
        b.setEdition(910L);
        b.setPages(5300L);
        b.setPublished("AM");
        bookDao.save(b);
    }
}
