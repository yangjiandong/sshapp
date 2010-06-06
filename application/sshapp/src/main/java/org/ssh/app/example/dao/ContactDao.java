package org.ssh.app.example.dao;

import org.springframework.stereotype.Component;
import org.springside.modules.orm.hibernate.HibernateDao;
import org.ssh.app.example.entity.Contact;

@Component
public class ContactDao extends HibernateDao<Contact, String> {
    private static final String COUNT_CONTACTS = "select count(b) from Contact b";

    /**
     * count book.
     */
    public Long getContactCount() {
        return findUnique(COUNT_CONTACTS);
    }

}
