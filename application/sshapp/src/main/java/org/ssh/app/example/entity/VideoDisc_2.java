package org.ssh.app.example.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="VIDEO_DISC_2")
@PrimaryKeyJoinColumn(name="DISC_ID")
public class VideoDisc_2 extends Disc_2 implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7339000587727997556L;
    private String director;
    private String language;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
