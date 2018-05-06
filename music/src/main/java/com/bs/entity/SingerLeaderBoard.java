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
    private Long signerId;
    private Long hot;
    private String tag;

    public SingerLeaderBoard(){

    }

    public SingerLeaderBoard(Long id, Long signerId, Long hot, String tag) {
        this.id = id;
        this.signerId = signerId;
        this.hot = hot;
        this.tag = tag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSignerId() {
        return signerId;
    }

    public void setSignerId(Long signerId) {
        this.signerId = signerId;
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
