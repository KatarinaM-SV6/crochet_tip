package com.ftn.sbnz.model.models;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CrochetPeriod implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public Korisnik user;

    @Column
    public Duration duration;

    @Column
    public LocalDate date;

    public CrochetPeriod() {
    }

    public CrochetPeriod(Korisnik user, Duration duration, LocalDate date) {
        this.user = user;
        this.duration = duration;
        this.date = date;
    }

    public Korisnik getUserId() {
        return this.user;
    }

    public void setUserId(Korisnik user) {
        this.user = user;
    }

    public Duration getDuration() {
        return this.duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
