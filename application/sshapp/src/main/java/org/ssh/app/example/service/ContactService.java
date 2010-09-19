package org.ssh.app.example.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.ssh.app.example.dao.ContactDao;
import org.ssh.app.example.entity.Contact;

@Component
@Transactional
public class ContactService {

    private static Logger logger = LoggerFactory
            .getLogger(ContactService.class);

    @Autowired
    private ContactDao contactDao;

    @Transactional(readOnly = true)
    public List<Contact> getContacts() {
        return contactDao.getAll2();
    }

    public void initData() {
        if (this.contactDao.getContactCount().longValue() != 0) {
            return;
        }

        logger.info("contact 初始化...");

        Contact c = new Contact();
        c.setName("yang");
        c.setBirthday("2001.01.01");
        c.setEmail("yang@gmail.com");
        c.setPhone("0519-1");
        c.setNums(new BigDecimal("100.02"));
        contactDao.save(c);

        c = new Contact();
        c.setName("谍灰有面");
        c.setBirthday("2001.11.01");
        c.setEmail("yangs@gmail.com");
        c.setPhone("0519-21");
        c.setNums(new BigDecimal("101.02"));
        contactDao.save(c);

        c = new Contact();
        c.setName("杨巡档");
        c.setBirthday("2001.01.01");
        c.setEmail("yang@gmail.com");
        c.setPhone("0519-1");
        c.setNums(new BigDecimal("110.02"));
        contactDao.save(c);

        c = new Contact();
        c.setName("榾灿有为要");
        c.setBirthday("2001.01.01");
        c.setEmail("yang@gmail.com");
        c.setPhone("0519-1");
        c.setNums(new BigDecimal("120.02"));
        contactDao.save(c);

        c = new Contact();
        c.setName("yang222");
        c.setBirthday("2001.01.01");
        c.setEmail("yang@gmail.com");
        c.setPhone("0519-1");
        c.setNums(new BigDecimal("123.99"));
        contactDao.save(c);
    }

    @Transactional(readOnly = true)
    public List<Contact> getAlls() {
        return contactDao.getAll2();
    }

    /**
     * Create new Contact/Contacts
     *
     * @param data
     *            - json data from request
     * @return created contacts
     * @throws ParseException
     */
    public List<Contact> create(Object data) throws ParseException {

        List<Contact> newContacts = new ArrayList<Contact>();
        List<Contact> list = getContactsFromRequest(data);
        for (Contact contact : list) {
            contactDao.save(contact);
            newContacts.add(contact);
        }
        return newContacts;
    }

    /**
     * Update contact/contacts
     *
     * @param data
     *            - json data from request
     * @return updated contacts
     * @throws ParseException
     */
    public List<Contact> update(Object data) throws ParseException {

        List<Contact> returnContacts = new ArrayList<Contact>();

        List<Contact> updatedContacts = getContactsFromRequest(data);

        for (Contact contact : updatedContacts) {
            contactDao.save(contact);
            returnContacts.add(contact);
        }

        return returnContacts;
    }

    /**
     * Delete contact/contacts
     *
     * @param data
     *            - json data from request
     */
    public void delete(Object data) {
        // it is an array - have to cast to array object
        if (data.toString().indexOf('[') > -1) {
            List<String> deleteContacts = getListIdFromJSON(data);
            for (String id : deleteContacts) {
                contactDao.delete(id);
            }
        } else { // it is only one object - cast to object/bean
            String id = data.toString();
            contactDao.delete(id);
        }
    }

    // 以下需改写到JsonDataUtil
    /**
     * Get list of Contacts from request.
     *
     * @param data
     *            - json data from request
     * @return list of Contacts
     */
    public List<Contact> getContactsFromRequest(Object data) {
        List<Contact> list;
        // it is an array - have to cast to array object
        if (data.toString().indexOf('[') > -1) {
            list = getListContactsFromJSON(data);
        } else { // it is only one object - cast to object/bean
            Contact contact = getContactFromJSON(data);
            list = new ArrayList<Contact>();
            list.add(contact);
        }

        return list;
    }

    /**
     * Transform json data format into Contact object
     *
     * @param data
     *            - json data from request
     * @return
     */
    public Contact getContactFromJSON(Object data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        Contact newContact = (Contact) JSONObject.toBean(jsonObject,
                Contact.class);
        return newContact;
    }

    /**
     * Transform json data format into list of Contact objects
     *
     * @param data
     *            - json data from request
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Contact> getListContactsFromJSON(Object data) {
        JSONArray jsonArray = JSONArray.fromObject(data);
        List<Contact> newContacts = (List<Contact>) JSONArray.toCollection(
                jsonArray, Contact.class);
        return newContacts;
    }

    /**
     * Tranform array of numbers in json data format into list of Integer
     *
     * @param data
     *            - json data from request
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<String> getListIdFromJSON(Object data) {
        JSONArray jsonArray = JSONArray.fromObject(data);
        List<String> idContacts = (List<String>) JSONArray.toCollection(
                jsonArray, String.class);
        return idContacts;
    }

    public List<Contact> getContactByProjections(String p_name) {
        return this.contactDao.getContactByProjections(p_name);
    }

    public List<Contact> getContactByDetachedCriteria(String p_name) {
        return this.contactDao.getContactByDetachedCriteria(p_name);
    }

    public List<Contact> getContactByDetachedCriteria2(String p_name) {
        return this.contactDao.getContactByDetachedCriteria2(p_name);
    }

    public List<Contact> getContactByNaturalId(String p_name) {
        return this.contactDao.getContactByNaturalId(p_name);
    }

    public List getContactBySql(String p_name) {
        return this.contactDao.getContactBySql(p_name);
    }
}
