package com.ftn.sbnz.service.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.CrochetPeriod;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.service.repositories.ICrochedPeriodRepository;
import com.ftn.sbnz.service.services.ICrochetPeriodService;
import com.ftn.sbnz.service.services.IUserService;

@Service
public class CrochetPeriodService implements ICrochetPeriodService{

    @Autowired
    private ICrochedPeriodRepository crochedPeriodRepository;

    @Autowired
    private IUserService userService;

    @Override
    public List<CrochetPeriod> loadCrochetPeriods(Integer userId) {
        Korisnik user = userService.get(userId);
        return crochedPeriodRepository.findAllByUser(user);
    }
    
}
