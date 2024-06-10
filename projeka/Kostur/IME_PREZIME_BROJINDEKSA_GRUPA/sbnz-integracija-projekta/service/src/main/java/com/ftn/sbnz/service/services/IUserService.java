package com.ftn.sbnz.service.services;

import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.Pattern;
import com.ftn.sbnz.service.model.LoginDTO;
import com.ftn.sbnz.service.model.loginResponseDTO;

public interface IUserService {
    Korisnik findByEmail(String email);

    Korisnik get(Integer id);

    loginResponseDTO register(Korisnik dto);

    loginResponseDTO login(LoginDTO dto);

    void setCurrentProject(Integer userId, Pattern pattern);

    Pattern getCurrentProject(Integer userId);
}