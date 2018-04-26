package com.bs.entity;

import javax.annotation.Generated;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "music")
public class Music {
    @Id
    @GeneratedValue

    private Long id;
    private Long singerId;
    private String musicName;
    private String album;
    private String type;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "musicId")
    private List<Comment> commentList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public Long getSingerId() {
        return singerId;
    }

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
