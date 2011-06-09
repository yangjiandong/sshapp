package org.ssh.pm.common.entity;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.springside.modules.orm.grid.ViewField;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

/**
 * 角色.
 *
 */
@Entity
@Table(name = "usrgroup")
public class Role implements Serializable {
    private static final long serialVersionUID = -214250998640222894L;
    @ViewField()
    protected Long id;
    @ViewField(header = "角色名称")
    private String name;
    @ViewField(header = "描述")
    private String desc;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Generator")
    @TableGenerator(name = "Id_Generator", table = "sysid", pkColumnName = "sysid_name", valueColumnName = "next_id", pkColumnValue = "USRGROUP", initialValue = 1, allocationSize = 1)
    @Column(name = "grp_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "grp_name", nullable = false, unique = true, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "note", length = 60)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Transient
    public boolean isTransient() {
        return this.id == null;
    }
}
