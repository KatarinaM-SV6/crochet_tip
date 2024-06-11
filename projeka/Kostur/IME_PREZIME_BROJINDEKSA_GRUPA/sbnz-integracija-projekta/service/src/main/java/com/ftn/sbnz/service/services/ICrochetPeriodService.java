package com.ftn.sbnz.service.services;

import java.util.List;

import com.ftn.sbnz.model.models.CrochetPeriod;

public interface ICrochetPeriodService {
    public List<CrochetPeriod> loadCrochetPeriods(Integer userId);
}
