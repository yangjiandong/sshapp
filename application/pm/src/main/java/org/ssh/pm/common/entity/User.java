package org.ssh.pm.common.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springside.modules.orm.grid.ViewField;
import org.springside.modules.utils.reflection.ConvertUtils;

import com.google.common.collect.Lists;

/**
 * 用户.
 *
 */
@Entity
@Table(name = "register")
public class User implements Serializable{

    private static final long serialVersionUID = -5486157827319182878L;

    @ViewField
    private Long id;

    @ViewField(header = "登录名")
    private String loginName;
    @ViewField
    private String password;
    @ViewField(header = "用户名")
    private String name;
    @ViewField
    private String usrid1;
    @ViewField
    private String usrid2;
    @ViewField
    private String note;
    @ViewField
    private Integer manid;
    @ViewField
    private String stationid;

    //有序的关联对象集合
    private List<Role> roleList = Lists.newArrayList();

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Generator")
    @TableGenerator(name = "Id_Generator", table = "sysid", pkColumnName = "sysid_name", valueColumnName = "next_id", pkColumnValue = "REGISTER", initialValue = 1, allocationSize = 1)
    @Column(name = "usr_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "usr_logname", nullable = false, unique = true, length = 10)
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Column(nullable = false, name = "usr_name", length = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //多对多定义
    @ManyToMany
    //中间表定义,表名采用默认命名规则
    @JoinTable(name = "t_usr_grp", joinColumns = { @JoinColumn(name = "usr_id") }, inverseJoinColumns = { @JoinColumn(name = "grp_id") })
    //Fecth策略定义
    @Fetch(FetchMode.SUBSELECT)
    //集合按id排序
    @OrderBy("id ASC")
    public List<Role> getRoleList() {
        return roleList;
    }

    //@Fetch(FetchMode.JOIN) 会使用left join查询  只产生一条sql语句
    //@Fetch(FetchMode.SELECT)   会产生N+1条sql语句
    //@Fetch(FetchMode.SUBSELECT)  产生两条sql语句 第二条语句使用id in (.....)查询出所有关联的数据
    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    @Transient
    @JsonIgnore
    public String getRoleNames() {
        return ConvertUtils.convertElementPropertyToString(roleList, "name", ", ");
    }

    //    //多对多定义
    //    @ManyToMany
    //    //中间表定义,表名采用默认命名规则
    //    @JoinTable(name = "T_USER_DB", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "DBCODE") })
    //    //Fecth策略定义
    //    @Fetch(FetchMode.SUBSELECT)
    //    public List<PartDB> getPartDBList() {
    //        return partDBList;
    //    }
    //
    //    public void setPartDBList(List<PartDB> partDBList) {
    //        this.partDBList = partDBList;
    //    }
    //
    //    @Transient
    //    @JsonIgnore
    //    public String getDBNames() {
    //        return ConvertUtils.convertElementPropertyToString(partDBList, "dbname", ", ");
    //    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Column(name = "usr_password", nullable = false, length = 100)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Transient
    public String getUserName() {
        return name.equals("") ? loginName : name;
    }

    @Transient
    public boolean isTransient() {
        return this.id == null;
    }

    @Column(name = "usr_id1", length = 10)
    public String getUsrid1() {
        return usrid1;
    }

    public void setUsrid1(String usrid1) {
        this.usrid1 = usrid1;
    }

    @Column(name = "usr_id2", length = 10)
    public String getUsrid2() {
        return usrid2;
    }

    public void setUsrid2(String usrid2) {
        this.usrid2 = usrid2;
    }

    @Column(name = "note", length = 40)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name = "man_id")
    public Integer getManid() {
        return manid;
    }

    public void setManid(Integer manid) {
        this.manid = manid;
    }

    @Column(name = "station_id", length = 10)
    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid;
    }
}
