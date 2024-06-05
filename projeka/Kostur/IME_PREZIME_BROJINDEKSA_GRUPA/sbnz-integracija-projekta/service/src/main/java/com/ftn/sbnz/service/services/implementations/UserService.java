package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.service.repositories.IUserRepository;
import com.ftn.sbnz.service.services.IUserService;
import com.ftn.sbnz.service.utils.TokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService, UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenUtils tokenUtils;

    @Override
    public Korisnik findByEmail(String email) {
        return userRepository.findByEmail(email).orElseGet(null);
    }

    @Override
    public Korisnik get(Integer id) {
        return userRepository.findById(id).orElseGet(null);
    }
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Korisnik user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        } else {
            return user;
        }
    }

    @Override
    public String register(Korisnik dto) {
        try {
            findByEmail(dto.getEmail());
            throw new Exception("User with given email already existis.");
        }catch (Exception ex) {
            Korisnik user = new Korisnik(null, dto.getUsername(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
            userRepository.save(user);
            userRepository.flush();
            
            String token = tokenUtils.generateToken(user);

            return token;
        }
    }

}


