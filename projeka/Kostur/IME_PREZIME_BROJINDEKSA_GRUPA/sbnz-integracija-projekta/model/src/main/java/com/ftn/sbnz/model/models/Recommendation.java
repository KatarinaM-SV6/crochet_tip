package com.ftn.sbnz.model.models;

import org.eclipse.sisu.Nullable;
import java.util.Objects;

public class Recommendation {

    private Pattern pattern;

    @Nullable
    private Boolean accepted;


    public Recommendation() {
    }

    public Recommendation(Pattern pattern) {
        this.pattern = pattern;
        this.accepted = null;
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

    public Boolean isAccepted() {
        return this.accepted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(pattern.getId(), that.pattern.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern, accepted);
    }
}
