package org.ssh.app.example.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="AUDIO_DISC_2")
@PrimaryKeyJoinColumn(name="DISC_ID")
public class AudioDisc_2 extends Disc_2 implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2923992024829241572L;
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
