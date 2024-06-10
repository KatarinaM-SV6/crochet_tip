package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.Pattern;
import com.ftn.sbnz.service.model.LoginDTO;
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

    @Autowired
    private KieSessionService kieSessionService;

    @Override
    public Korisnik findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public Korisnik get(Integer id) {
        return userRepository.findById(id).orElse(null);
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
            user = userRepository.save(user);
            userRepository.flush();
            
            String token = tokenUtils.generateToken(user);
            kieSessionService.createUserSession(user);
            return token;
        }
    }

    @Override
    public String login(LoginDTO dto) {
        try {
            // Find the user by email
            Korisnik user = findByEmail(dto.getEmail());
    
            // Check if the passwords match
            if (user != null && passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                // Generate a token and create a user session
                kieSessionService.createUserSession(user);
                String token = tokenUtils.generateToken(user);
                return token;
            } else {
                throw new Exception("Invalid email or password.");
            }
        } catch (Exception ex) {
            throw new RuntimeException("Login failed: " + ex.getMessage());
        }
    }

    @Override
    public void setCurrentProject(Integer userId, Pattern pattern) {
        Korisnik user = get(userId);
        user.setCurrPattern(pattern);
        userRepository.save(user);
    }

    @Override
    public Pattern getCurrentProject(Integer userId) {
        Korisnik user = get(userId);
        return user.getCurrPattern();
    }

}


