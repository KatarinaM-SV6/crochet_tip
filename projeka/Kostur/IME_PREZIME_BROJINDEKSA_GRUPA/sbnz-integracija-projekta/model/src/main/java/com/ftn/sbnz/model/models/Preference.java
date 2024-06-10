package com.ftn.sbnz.model.models;

public class Preference {
    private Difficulty difficulty;
    // private CrochetType crochetType;
    


    public Preference() {
    }

    public Preference(Difficulty difficulty) {
        this.difficulty = difficulty;
        // this.crochetType = crochetType;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    // public CrochetType getCrochetType() {
    //     return this.crochetType;
    // }

    // public void setCrochetType(CrochetType crochetType) {
    //     this.crochetType = crochetType;
    // }

}
