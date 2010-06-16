package org.ssh.app.example.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.ssh.app.common.entity.IdEntity;

@Entity
@Table(name = "t_contact")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Contact extends IdEntity {

    private String name;
    private String phone;
    private String email;
    private String birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
