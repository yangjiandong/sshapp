package org.ssh.app.example.service;

import java.util.List;

import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.orm.Page;
import org.springside.modules.orm.PropertyFilter;
import org.ssh.app.example.dao.BookDao;
import org.ssh.app.example.dao.ContactDao;
import org.ssh.app.example.entity.Blog;
import org.ssh.app.example.entity.Book;
import org.ssh.app.example.entity.Contact;

@SuppressWarnings("unchecked")
@Component
//@Service("accountManager")
//默认将类中的所有函数纳入事务管理.
@Transactional
public class BookService {

    private static Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookDao bookDao;

    @Autowired
    private ContactDao contactDao;

    //采用了方法缓存
    @Transactional(readOnly = true)
    public List<Book> getBooksOnMethodCache(){
        //return bookDao.getAll();
        StringBuffer bf = new StringBuffer();
        bf.append("select oid,isbn,title,published ");
        bf.append(" from  t_Book ");

        SQLQuery query = this.bookDao.getSession().createSQLQuery(bf.toString());
        return query.list();
    }

    //测试开发环境下增加了其他类是否要重启？
    //还是需要重启
    public List<Blog> getExamples(){
        //return bookDao.getAll();
        StringBuffer bf = new StringBuffer();
        bf.append("select name from t_blogs ");

        SQLQuery query = this.bookDao.getSession().createSQLQuery(bf.toString());
        return query.list();
    }

    //测试开发环境下增加了其他类是否要重启？
    //采用jetty-debug自动重启
    public List<Object> getExamples2(){
        //return bookDao.getAll();
        StringBuffer bf = new StringBuffer();
        // create table t_examles(id int,name varchar(20),code varchar(20))
        // insert into t_examles values(1,'test','code')
        // insert into t_examles values(1,'测试中午','编号')
        bf.append("SELECT * FROM t_examles ");

        SQLQuery query = this.bookDao.getSession().createSQLQuery(bf.toString());
        return query.list();
    }

    //采用表缓存
    //2011.03.28,已关闭hibernate 缓存
    @Transactional(readOnly = true)
    public List<Book> getBooks2(){
        return bookDao.getAll2();
    }

    @Transactional(readOnly = true)
    public List<Book> getBooks3(){
        //return bookDao.getAll();
        // 2011.03.27
        // 注意,book中加入Contact属性,采用默认findAll,会产生
        // org.hibernate.LazyInitializationException: could not initialize proxy - no Session
        StringBuffer bf = new StringBuffer();
        bf.append("select oid,isbn,title,published ");
        bf.append(" from  t_Book ");

        SQLQuery query = this.bookDao.getSession().createSQLQuery(bf.toString());
        return query.list();
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

        //预置contact
        Contact c = contactDao.findOneBy("name", "yang");
        if (c==null){
            logger.debug("contact name yang is null");
            return;
        }

        Book b = new Book();
        b.setIsbn("comto ok");
        b.setTitle("goto American");
        b.setEdition(10L);
        b.setPages(200L);
        b.setPublished("AM");
        b.setContact(c);
        bookDao.save(b);

        b = new Book();
        b.setIsbn("omoo");
        b.setTitle("计划生育");
        b.setEdition(10L);
        b.setPages(2000L);
        b.setPublished("AM");
        b.setContact(c);
        bookDao.save(b);

        b = new Book();
        b.setIsbn("comtosadfad ok");
        b.setTitle("同要有 面goto American");
        b.setEdition(910L);
        b.setPages(5300L);
        b.setPublished("AM");
        b.setContact(c);
        bookDao.save(b);
    }

    public List<Book> getBooksByTile(String name){
        return this.bookDao.getBooksByTitle(name);
    }

    public List<Book> getBooksBySql(String name){
        return this.bookDao.getBooksBySql(name);
    }
}
