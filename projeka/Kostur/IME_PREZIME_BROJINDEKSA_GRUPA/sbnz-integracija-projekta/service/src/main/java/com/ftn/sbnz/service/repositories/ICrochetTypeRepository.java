package com.ftn.sbnz.service.repositories;

import com.ftn.sbnz.model.models.CrochetType;
import com.ftn.sbnz.model.models.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICrochetTypeRepository extends JpaRepository<CrochetType,Integer>{
    
}
