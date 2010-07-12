package org.ssh.app.example.entity;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("VIDEO")
public class VideoDisc_1 extends Disc_1 implements Serializable {

    private static final long serialVersionUID = 6295831413878918936L;

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
