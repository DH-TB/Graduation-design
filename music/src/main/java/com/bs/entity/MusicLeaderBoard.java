package com.bs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "musicLeaderBoard")
public class MusicLeaderBoard {

    @Id
    @GeneratedValue

    private Long id;
    private Long musicId;
    private String type;
    private Long hot;
    private String tag;
    private Long singerId;

    public MusicLeaderBoard(){

    }

    public MusicLeaderBoard(Long id, Long musicId, String type, Long hot, String tag, Long singerId) {
        this.id = id;
        this.musicId = musicId;
        this.type = type;
        this.hot = hot;
        this.tag = tag;
        this.singerId = singerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getHot() {
        return hot;
    }

    public void setHot(Long hot) {
        this.hot = hot;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getSingerId() {
        return singerId;
    }

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }
}
