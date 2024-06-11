package com.ftn.sbnz.model.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class Korisnik implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String email;
    @Column
    private String password;

    @ManyToOne
    private Pattern currPattern;
    // @OneToMany(fetch = FetchType.EAGER)
    // private List<Pattern> finishedPatterns;

    public Korisnik() {
    }

    public Korisnik(String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.currPattern = null;
        // this.finishedPatterns = new ArrayList<Pattern>();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    public Pattern getCurrPattern() {
        return this.currPattern;
    }

    public void setCurrPattern(Pattern currPattern) {
        this.currPattern = currPattern;
    }


    // public List<Pattern> getFinishedPatterns() {
    //     return this.finishedPatterns;
    // }

    // public void setFinishedPatterns(List<Pattern> finishedPatterns) {
    //     this.finishedPatterns = finishedPatterns;
    // }


}
