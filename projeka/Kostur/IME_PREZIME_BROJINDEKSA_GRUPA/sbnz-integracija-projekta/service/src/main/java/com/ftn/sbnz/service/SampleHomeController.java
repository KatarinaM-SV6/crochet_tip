package com.ftn.sbnz.service;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.service.services.IUserService;
import com.ftn.sbnz.service.utils.TokenUtils;
import com.ftn.sbnz.service.config.DroolsApplicationConfig;

@RestController
@RequestMapping(value = "api")
@CrossOrigin
public class SampleHomeController {
	@Autowired
	private TokenUtils tokenUtils;

	@Autowired IUserService userService;

	@Autowired
	private KieSession kieSession;

	// @PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/")
	public Korisnik index(@RequestHeader("Authorization") String token) {
		kieSession.insert(token);
		int firedRules = kieSession.fireAllRules();
		System.out.println("fired rules " + firedRules);
		Integer id = tokenUtils.getIdFromToken(token.substring(7));
		return userService.get(id);
	}
}
