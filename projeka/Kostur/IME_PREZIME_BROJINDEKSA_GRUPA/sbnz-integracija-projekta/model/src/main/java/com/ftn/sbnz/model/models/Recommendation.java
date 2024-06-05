package com.ftn.sbnz.model.models;

public class Recommendation {
    private Pattern pattern;
    private Boolean accepted;


    public Recommendation() {
    }

    public Recommendation(Pattern pattern) {
        this.pattern = pattern;
        this.accepted = false;
    }


    public Pattern getPattern() {
        return this.pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Boolean getAccepted() {
        return this.accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
    

    @Override
    public String toString() {
        return "{" +
            " pattern='" + getPattern() + "'" +
            ", accepted='" + getAccepted() + "'" +
            "}";
    }
    
}
