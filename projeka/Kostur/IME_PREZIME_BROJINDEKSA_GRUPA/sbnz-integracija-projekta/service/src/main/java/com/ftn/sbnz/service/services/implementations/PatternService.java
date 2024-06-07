package com.ftn.sbnz.service.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.Pattern;
import com.ftn.sbnz.service.repositories.IPatternRepository;
import com.ftn.sbnz.service.services.IPatternService;
import com.ftn.sbnz.service.services.IUserService;

@Service
public class PatternService implements IPatternService{
    
    @Autowired
    private IPatternRepository patternRepository;

    @Override
    public List<Pattern> loadAllPatterns() {
        return patternRepository.findAll();
    }
}
