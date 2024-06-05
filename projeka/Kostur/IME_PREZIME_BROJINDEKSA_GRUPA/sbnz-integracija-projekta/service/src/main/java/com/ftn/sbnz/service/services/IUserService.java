package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.models.Korisnik;

public interface IUserService {
    Korisnik findByEmail(String email);

    Korisnik get(Integer id);

    String register(Korisnik dto);
}