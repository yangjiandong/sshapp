package com.ekingsoft.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ekingsoft.core.orm.EntityService;
import com.ekingsoft.example.dao.BookDao;
import com.ekingsoft.example.entity.Book;

@Service
@Transactional
public class BookService extends EntityService<Book, Long> {
    @Autowired
    private BookDao bookDao;

    @Override
    protected BookDao getEntityDao() {
        return this.bookDao;
    }

}
