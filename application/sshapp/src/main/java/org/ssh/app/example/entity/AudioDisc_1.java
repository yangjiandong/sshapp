package org.ssh.app.example.entity;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("AUDIO")
public class AudioDisc_1 extends Disc_1 implements Serializable {

    private static final long serialVersionUID = 7616734893374246882L;

    private Integer noOfSongs;
    private String singer;

    public Integer getNoOfSongs() {
        return noOfSongs;
    }

    public void setNoOfSongs(Integer noOfSongs) {
        this.noOfSongs = noOfSongs;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

}
