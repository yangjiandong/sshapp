package org.ssh.app.example.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
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

    public void initData() {
        if (this.bookDao.getBookCount().longValue() != 0) {
            return;
        }

        Book b = new Book();
        b.setIsbn("comto ok");
        b.setTitle("goto American");
        b.setEdition(10);
        b.setPages(200);
        b.setPublished("AM");
        bookDao.save(b);

        b = new Book();
        b.setIsbn("omoo");
        b.setTitle("计划生育");
        b.setEdition(10);
        b.setPages(2000);
        b.setPublished("AM");
        bookDao.save(b);

        b = new Book();
        b.setIsbn("comtosadfad ok");
        b.setTitle("同要有 面goto American");
        b.setEdition(910);
        b.setPages(5300);
        b.setPublished("AM");
        bookDao.save(b);
    }
}
