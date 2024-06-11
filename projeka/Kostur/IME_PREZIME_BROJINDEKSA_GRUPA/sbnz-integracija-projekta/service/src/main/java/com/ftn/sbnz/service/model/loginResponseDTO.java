package com.ftn.sbnz.service.model;

import com.ftn.sbnz.model.models.Korisnik;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class loginResponseDTO {
    Korisnik user;
    String token;
}
