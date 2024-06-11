package com.ftn.sbnz.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.Pattern;
import com.ftn.sbnz.service.model.LoginDTO;
import com.ftn.sbnz.service.model.loginResponseDTO;
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
        loginResponseDTO ret = userService.register(dto);
        return new ResponseEntity<loginResponseDTO>(ret, HttpStatus.OK);
    }

    @PostMapping(value="/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
        loginResponseDTO ret = userService.login(dto);
        return new ResponseEntity<loginResponseDTO>(ret, HttpStatus.OK);
    }

    @GetMapping(value="/current-project", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCurrentProject(@RequestHeader("Authorization") String token) {
        Integer userId = tokenUtils.getIdFromToken(token.substring(7));
        Pattern pattern = userService.getCurrentProject(userId);
        return new ResponseEntity<Pattern>(pattern, HttpStatus.OK);
    }

    @GetMapping(value="/finish-project", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> finishCurrentProject(@RequestHeader("Authorization") String token) {
        Integer userId = tokenUtils.getIdFromToken(token.substring(7));
        Korisnik user = userService.finishCurrentProject(userId);
        return new ResponseEntity<Korisnik>(user, HttpStatus.OK);
    }
}
