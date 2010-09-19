package org.ssh.app.example.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "t_blogs")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Blog {
    private Long id;
    private String name;
    private Set<BlogItem> items=new LinkedHashSet<BlogItem>();

    public Blog(){

    }
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Generator")
    @TableGenerator(name = "Id_Generator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "t_blogs", initialValue = 1, allocationSize = 1)
    @Column(name="blog_id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable=false, unique=true, length=100)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    //@OneToMany(cascade={CascadeType.ALL}, mappedBy="blog")
    //@JoinColumn(name="blog_id")
    //@Fetch(FetchMode.SUBSELECT)
    //mappedBy 是否指向 BlogItem 定义的private Blog blog
    @OneToMany(mappedBy = "blog", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
    //id 为 BlogItem 的主键id, 必需为属性名，不能为字段名
    @OrderBy("id")
    public Set<BlogItem> getItems() {
        return items;
    }
    public void setItems(Set<BlogItem> items) {
        this.items = items;
    }
}
