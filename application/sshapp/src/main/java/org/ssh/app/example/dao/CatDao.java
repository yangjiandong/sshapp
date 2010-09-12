package org.ssh.app.example.dao;

import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Component;

import org.springside.modules.orm.hibernate.HibernateDao;

import org.ssh.app.example.entity.Cat;

import java.awt.Color;

import java.util.List;

@Component
public class CatDao extends HibernateDao<Cat, String> {
    public List<Cat> getCatByExample() {
        Cat cat = new Cat();
        cat.setSex('F');
        cat.setColor(Color.BLACK);

        List<Cat> results = null;
        results = getSession().createCriteria(Cat.class).add(Example.create(cat)).list();

        return results;
    }

    // 投影 Projections
    // p230
    public List<Cat> getCatByProjections() {
        List results =
                getSession().createCriteria(Cat.class).setProjection(Projections.rowCount())
                        .add(Restrictions.eq("color", Color.BLACK)).list();

        List results2 =
                getSession()
                        .createCriteria(Cat.class)
                        .setProjection(
                                Projections.projectionList().add(Projections.rowCount())
                                        .add(Projections.avg("weight"))
                                        .add(Projections.max("weight"))
                                        .add(Projections.groupProperty("color"))).list();

        return results;
    }

    //16.9. 根据自然标识查询（Queries by natural identifier）
}
