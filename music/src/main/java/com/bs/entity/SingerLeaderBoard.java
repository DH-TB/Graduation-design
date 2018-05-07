package com.bs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sinerLeaderBoard")
public class SingerLeaderBoard {

    @Id
    @GeneratedValue

    private Long id;
    private Long singerId;
    private Long hot;
    private String tag;

    public SingerLeaderBoard(){

    }

    public SingerLeaderBoard(Long id, Long singerId, Long hot, String tag) {
        this.id = id;
        this.singerId = singerId;
        this.hot = hot;
        this.tag = tag;
    }

    public Long getSingerId() {
        return singerId;
    }

    public void setSingerId(Long singerId) {
        this.singerId = singerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
