package com.ftn.sbnz.service.repositories;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.CrochetPeriod;
import com.ftn.sbnz.model.models.Korisnik;

public interface ICrochedPeriodRepository extends JpaRepository<CrochetPeriod, Integer>{
    List<CrochetPeriod> findAllByUser(Korisnik user);
}
