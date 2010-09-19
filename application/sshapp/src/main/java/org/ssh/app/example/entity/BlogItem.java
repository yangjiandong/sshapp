package org.ssh.app.example.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "t_blogItems")
public class BlogItem {
    private Long id;
    private String datetimes;
    private String text;
    private String title;
    private Blog blog;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "Id_Generator")
    @TableGenerator(name = "Id_Generator", table = "ID_GENERATOR", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL", pkColumnValue = "t_blogItems", initialValue = 1, allocationSize = 1)
    @Column(name="detail_id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDatetimes() {
        return datetimes;
    }
    public void setDatetimes(String datetimes) {
        this.datetimes = datetimes;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="blog_id")
    public Blog getBlog() {
        return blog;
    }
    public void setBlog(Blog blog) {
        this.blog = blog;
    }

}
