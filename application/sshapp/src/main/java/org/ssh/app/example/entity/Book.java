package org.ssh.app.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.ssh.app.common.entity.IdEntity;

/**
 * book
 */
@Entity
@Table(name = "t_book")
//@JsonAutoDetect
public class Book extends IdEntity {

    private String isbn;
    private String title;
    private int edition;
    private int pages;
    private String published;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Column(nullable = false, unique = true)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }
}
