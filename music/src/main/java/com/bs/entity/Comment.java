package com.bs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue

    private Long id;
    private Long musicId;
    private Long userId;
    private String content;
    private int count;
    private Boolean isLike;
    private String nickName;
    private String image;
    private String createTime;

    public Comment(){

    }

    public Comment(Long id, Long musicId, Long userId, String content, int count, Boolean isLike) {
        this.id = id;
        this.musicId = musicId;
        this.userId = userId;
        this.content = content;
        this.count = count;
        this.isLike = isLike;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
