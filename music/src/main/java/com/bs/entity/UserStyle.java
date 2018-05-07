package com.bs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userStyle")
public class UserStyle {

    @Id
    @GeneratedValue

    private Long id;
    private Long userId;
    private Double jazz;
    private Double rock;
    private Double hiphop;
    private Double light;
    private Double blues;
    private Double classical;
    private Double pop;
    private Double heavyMetal;
    private Double rap;
    private Double folk;

    public UserStyle() {

    }

    public UserStyle(Long userId, Double jazz, Double rock, Double hiphop, Double light, Double blues, Double classical, Double pop, Double heavyMetal, Double rap, Double folk) {
        this.userId = userId;
        this.jazz = jazz;
        this.rock = rock;
        this.hiphop = hiphop;
        this.light = light;
        this.blues = blues;
        this.classical = classical;
        this.pop = pop;
        this.heavyMetal = heavyMetal;
        this.rap = rap;
        this.folk = folk;
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

    public Double getJazz() {
        return jazz;
    }

    public void setJazz(Double jazz) {
        this.jazz = jazz;
    }

    public Double getRock() {
        return rock;
    }

    public void setRock(Double rock) {
        this.rock = rock;
    }

    public Double getHiphop() {
        return hiphop;
    }

    public void setHiphop(Double hiphop) {
        this.hiphop = hiphop;
    }

    public Double getLight() {
        return light;
    }

    public void setLight(Double light) {
        this.light = light;
    }

    public Double getBlues() {
        return blues;
    }

    public void setBlues(Double blues) {
        this.blues = blues;
    }

    public Double getClassical() {
        return classical;
    }

    public void setClassical(Double classical) {
        this.classical = classical;
    }

    public Double getPop() {
        return pop;
    }

    public void setPop(Double pop) {
        this.pop = pop;
    }

    public Double getHeavyMetal() {
        return heavyMetal;
    }

    public void setHeavyMetal(Double heavyMetal) {
        this.heavyMetal = heavyMetal;
    }

    public Double getRap() {
        return rap;
    }

    public void setRap(Double rap) {
        this.rap = rap;
    }

    public Double getFolk() {
        return folk;
    }

    public void setFolk(Double folk) {
        this.folk = folk;
    }
}
