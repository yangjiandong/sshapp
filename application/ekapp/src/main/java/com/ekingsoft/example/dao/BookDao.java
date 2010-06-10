package com.ekingsoft.example.dao;

import org.springframework.stereotype.Repository;

import com.ekingsoft.core.orm.hibernate.HibernateDao;
import com.ekingsoft.example.entity.Book;

@Repository
public class BookDao extends HibernateDao<Book, Long> {

}
