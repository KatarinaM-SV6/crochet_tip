package com.ftn.sbnz.model.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Yarn {

    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private YarnType yarnType;

    @Enumerated(EnumType.STRING)
    private WoolSize size;

    @Column
    private double amount;

    @Enumerated(EnumType.STRING)
    private Color color;

    public Yarn() {
    }


    public Yarn(YarnType yarnType, WoolSize size, double amount, Color color) {
        this.yarnType = yarnType;
        this.size = size;
        this.amount = amount;
        this.color = color;
    }

    public YarnType getYarnType() {
        return this.yarnType;
    }

    public void setYarnType(YarnType yarnType) {
        this.yarnType = yarnType;
    }

    public WoolSize getSize() {
        return this.size;
    }

    public void setSize(WoolSize size) {
        this.size = size;
    }

    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
