package org.ssh.app.example.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.ssh.app.orm.hibernate.AbstractEntity;
import org.ssh.app.orm.hibernate.Historizable;

/**
 * 商品分类
 * @author jeffrey
 */
@Entity
public class Category extends AbstractEntity implements Serializable, Historizable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category[" + id + "," + name + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash = 47 * hash + id;
    }
}
