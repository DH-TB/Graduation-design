package com.bs.entity;

import javax.persistence.*;

@Entity
@Table(name = "singer")
public class Singer {
    @Id
    @GeneratedValue

    private Long id;
    private String tag;
    private String singer;

    public Singer(){

    }

    public Singer(Long id, String tag, String singer) {
        this.id = id;
        this.tag = tag;
        this.singer = singer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
