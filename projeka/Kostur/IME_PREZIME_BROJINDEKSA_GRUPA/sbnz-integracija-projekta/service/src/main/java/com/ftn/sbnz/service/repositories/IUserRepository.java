package com.ftn.sbnz.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.sbnz.model.models.Korisnik;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<Korisnik,Integer> {

    Optional<Korisnik> findByEmail(String email);

    Optional<Korisnik> findById(Integer id);
}
