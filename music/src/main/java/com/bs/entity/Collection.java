package com.bs.entity;

import javax.persistence.*;

@Entity
@Table(name="collection")

public class Collection {
    @Id
    @GeneratedValue

    private Long id;
    @Column
    private Long userId;
    @Column
    private Long musicId;
    @Column
    private Long count;
    @Column
    private Integer love;

    public Collection(){

    }

    public Collection(Long id, Long userId, Long musicId, Long count, Integer love) {
        this.id = id;
        this.userId = userId;
        this.musicId = musicId;
        this.count = count;
        this.love = love;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    public Integer getLove() {
        return love;
    }

    public void setLove(Integer love) {
        this.love = love;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
