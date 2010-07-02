package org.ssh.app.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "T_HZK")
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Hz {
    private String hz;
    private String wb;
    private String py;

    @Id
    @Column(length = 10)
    public String getHz() {
        return hz;
    }

    public void setHz(String hz) {
        this.hz = hz;
    }

    @Column(length = 10)
    public String getWb() {
        return wb;
    }

    public void setWb(String wb) {
        this.wb = wb;
    }

    @Column(length = 10)
    public String getPy() {
        return py;
    }

    public void setPy(String py) {
        this.py = py;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
