package org.ssh.app.example.dao;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
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

    public List<Contact> getAll2() {
        Criteria criteria = getSession().createCriteria(entityClass);
        //criteria.setCacheable(true);
        return criteria.list();
    }

    // 投影 Projections
    // p230
    public List<Contact> getContactByProjections(String p_name) {
        List results =
                getSession().createCriteria(Contact.class).setProjection(Projections.rowCount())
                        .add(Restrictions.like("name", p_name + "%")).list();

        // List results2 =
        // getSession()
        // .createCriteria(Contact.class)
        // .setProjection(
        // Projections.projectionList().add(Projections.rowCount())
        // .add(Projections.avg("weight"))
        // .add(Projections.max("weight"))
        // .add(Projections.groupProperty("color"))).list();

        return results;
    }

    // 16.9. 根据自然标识查询（Queries by natural identifier）

    //
    public List<Contact> getContactByDetachedCriteria(String p_name) {
        // 子查詢，计算平均值
        DetachedCriteria avgNum =
                DetachedCriteria.forClass(Contact.class).setProjection(
                        Property.forName("nums").avg());
        // 列出大于平均值的记录
        List results =
                getSession().createCriteria(Contact.class).add(Property.forName("nums").gt(avgNum))
                        .list();

        return results;
    }

    // 要查下hibernate DetachedCriteria 的具体用法
    public List<Contact> getContactByDetachedCriteria2(String p_name) {
        DetachedCriteria num =
                DetachedCriteria.forClass(Contact.class).setProjection(Property.forName("nums"));
        //
        List results =
                getSession().createCriteria(Contact.class).add(Subqueries.geAll("nums", num))
                        .list();

        return results;
    }

    // 采用naturalid ,提高缓存效果，需对name 设置 @NaturalId
    public List<Contact> getContactByNaturalId(String p_name) {
        List results =
                getSession().createCriteria(Contact.class)
                        .add(Restrictions.naturalId().set("name", p_name))
                        //.setCacheable(true)
                        .list();

        return results;
    }

    // 采用原生的sql，注意，表名、字段名都必须是原生
    public List getContactBySql(String p_name) {
        Query query = getSession().createSQLQuery("SELECT * FROM t_contact where name like ?")
                        .addScalar("id", Hibernate.STRING).addScalar("name").addScalar("num");
                List results = query.setString(0, p_name+"%").list();
        return results;
    }

    // 采用实体查询,自动对应
    public List getContactByEntitySql(String p_name) {
        List results =
                getSession().createSQLQuery("SELECT * FROM t_contact")
                        .addEntity(Contact.class).list();

        return results;
    }
}
