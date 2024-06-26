package com.ftn.sbnz.service.services.implementations;

import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.Pattern;
import com.ftn.sbnz.model.models.Recommendation;
import com.ftn.sbnz.service.model.LoginDTO;
import com.ftn.sbnz.service.model.loginResponseDTO;
import com.ftn.sbnz.service.repositories.IUserRepository;
import com.ftn.sbnz.service.services.IUserService;
import com.ftn.sbnz.service.utils.TokenUtils;

import java.util.Collection;

import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
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
    public loginResponseDTO register(Korisnik dto) {
        try {
            findByEmail(dto.getEmail());
            throw new Exception("User with given email already existis.");
        }catch (Exception ex) {
            Korisnik user = new Korisnik(dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
            user = userRepository.save(user);
            userRepository.flush();
            
            String token = tokenUtils.generateToken(user);
            kieSessionService.createUserSession(user);
            return new loginResponseDTO(user, token);
        }
    }

    @Override
    public loginResponseDTO login(LoginDTO dto) {
        try {
            // Find the user by email
            Korisnik user = findByEmail(dto.getEmail());
    
            // Check if the passwords match
            if (user != null && passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
                // Generate a token and create a user session
                kieSessionService.createUserSession(user);
                String token = tokenUtils.generateToken(user);
                return new loginResponseDTO(user, token);
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

    @Override
    public Korisnik finishCurrentProject(Integer userId) {
        Korisnik user = get(userId);
        Pattern pat = user.getCurrPattern();
        KieSession kieSession = kieSessionService.getUserSession(userId);
        Collection<?> patterns = kieSession.getObjects(new ClassObjectFilter(Pattern.class));
        Pattern foundPat = null;
        FactHandle factHandle = null;
        for (Object obj : patterns) {
            if (obj.equals(pat)) { // Ensure this check matches your Recommendation's equals method
                foundPat = (Pattern) obj;
                factHandle = kieSession.getFactHandle(foundPat);
                break;
            }
        }
        pat.setDone(true);

        kieSession.update(factHandle, pat);
        user.setCurrPattern(null);
        return userRepository.save(user);
    }

}


