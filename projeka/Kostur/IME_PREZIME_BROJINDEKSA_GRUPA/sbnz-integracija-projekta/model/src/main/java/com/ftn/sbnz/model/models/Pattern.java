package com.ftn.sbnz.model.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pattern {
    @Id
    private Integer id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private Difficulty difficultyLevel;

    @Column
    private Double crochetHookSize;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Yarn> yarns;

    @Column
    private String patternImage;

    @ElementCollection
    private List<String> extras;

    @Column
    private Boolean done;

    @ManyToOne
    @JoinColumn(name = "parent_type_id")
    private CrochetType parentType;

    public Pattern() {
    }
    

    public Pattern(Integer id, String name, Difficulty difficultyLevel, Double crochetHookSize, List<Yarn> yarns, String patternImage, List<String> extras, Boolean done, CrochetType parentType) {
        this.id = id;
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.crochetHookSize = crochetHookSize;
        this.yarns = yarns;
        this.patternImage = patternImage;
        this.extras = extras;
        this.done = done;
        this.parentType = parentType;
    }



    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Difficulty getDifficultyLevel() {
        return this.difficultyLevel;
    }

    public void setDifficultyLevel(Difficulty difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Double getCrochetHookSize() {
        return this.crochetHookSize;
    }

    public void setCrochetHookSize(Double crochetHookSize) {
        this.crochetHookSize = crochetHookSize;
    }

    public List<Yarn> getYarns() {
        return this.yarns;
    }

    public void setYarns(List<Yarn> yarns) {
        this.yarns = yarns;
    }

    public String getPatternImage() {
        return this.patternImage;
    }

    public void setPatternImage(String patternImage) {
        this.patternImage = patternImage;
    }

    public List<String> getExtras() {
        return this.extras;
    }

    public void setExtras(List<String> extras) {
        this.extras = extras;
    }

    public Boolean getDone() {
        return this.done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }


    public CrochetType getParentType() {
        return this.parentType;
    }

    public void setParentType(CrochetType parentType) {
        this.parentType = parentType;
    }


    @Override
    public String toString() {
        return "{" +
            ", name='" + getName() + "'" +
            ", difficultyLevel='" + getDifficultyLevel() + "'" +
            ", crochetHookSize='" + getCrochetHookSize() + "'" +
            ", yarns='" + getYarns() + "'" +
            ", patternImage='" + getPatternImage() + "'" +
            ", extras='" + getExtras() + "'" +
            ", done='" + getDone() + "'" +
            ", parentType='" + getParentType() + "'" +
            "}";
    }

}
