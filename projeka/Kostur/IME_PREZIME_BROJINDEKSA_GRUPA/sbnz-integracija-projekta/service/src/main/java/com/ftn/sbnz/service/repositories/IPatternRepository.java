package com.ftn.sbnz.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Pattern;

public interface IPatternRepository extends JpaRepository<Pattern, Integer> {
    
}
