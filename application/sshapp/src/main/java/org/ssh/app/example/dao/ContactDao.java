package org.ssh.app.example.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.example.entity.Contact;

@Component
public class ContactDao extends HibernateDao<Contact, String> {
    private static final String COUNT_CONTACTS = "select count(b) from Contact b";

    private DataSource dataSource;

    @Autowired
    public void setDataSource(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private SimpleJdbcTemplate getJdbcTemplate() {
        return new SimpleJdbcTemplate(this.dataSource);
    }

    /**
     * count book.
     */
    public Long getContactCount() {
        return findUnique(COUNT_CONTACTS);
    }

}
