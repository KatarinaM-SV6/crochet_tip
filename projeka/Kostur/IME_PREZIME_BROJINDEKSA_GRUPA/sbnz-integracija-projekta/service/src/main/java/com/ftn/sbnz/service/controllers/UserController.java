package com.ftn.sbnz.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.service.services.IUserService;
import com.ftn.sbnz.service.services.implementations.UserService;
import com.ftn.sbnz.service.utils.TokenUtils;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class UserController {
    @Autowired
    IUserService userService;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping(value="/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(@RequestBody Korisnik dto) {
        String ret = userService.register(dto);
        return new ResponseEntity<String>(ret, HttpStatus.OK);
    }
}
