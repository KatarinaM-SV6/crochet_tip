package com.ftn.sbnz.model.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.kie.api.definition.type.Position;

@Entity
public class CrochetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column
    @Position(0)
    private String name;
    
    @Column
    @Position(1)
    private String parentCrochetType;
    
    @Column
    @Position(2)
    private Boolean disliked;


    public CrochetType() {
    }

    public CrochetType(String name, String parentCrochetType, Boolean disliked) {
        this.name = name;
        this.parentCrochetType = parentCrochetType;
        this.disliked = disliked;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCrochetType() {
        return this.parentCrochetType;
    }

    public void setParentCrochetType(String parentCrochetType) {
        this.parentCrochetType = parentCrochetType;
    }

    public Boolean getDisliked() {
        return this.disliked;
    }

    public void setDisliked(Boolean disliked) {
        this.disliked = disliked;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CrochetType other = (CrochetType) obj;
        return Objects.equals(name, other.name) &&
            Objects.equals(parentCrochetType, other.parentCrochetType) &&
            Objects.equals(disliked, other.disliked);
    }


    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", parentCrochetType='" + getParentCrochetType() + "'" +
            ", disliked='" + getDisliked() + "'" +
            "}";
    }

}