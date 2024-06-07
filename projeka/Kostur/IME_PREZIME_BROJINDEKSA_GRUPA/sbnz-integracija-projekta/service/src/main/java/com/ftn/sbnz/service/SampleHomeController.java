package com.ftn.sbnz.service;

import java.util.Collection;
import java.util.List;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.models.CrochetPeriod;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.Pattern;
import com.ftn.sbnz.service.services.ICrochetPeriodService;
import com.ftn.sbnz.service.services.IPatternService;
import com.ftn.sbnz.service.services.IUserService;
import com.ftn.sbnz.service.utils.PatternUtils;
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
	IPatternService patternService;

	@Autowired
	ICrochetPeriodService crochetPeriodService;

	@Autowired
	private KieSession kieSession;

	@Autowired
	private PatternUtils patternUtils;

	// @PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/")
	public void index(@RequestHeader("Authorization") String token) {
		Integer userId = tokenUtils.getIdFromToken(token.substring(7));
		Korisnik user = userService.get(userId);

		kieSession.insert(user);
		List<Pattern> patterns = patternService.loadAllPatterns();
		patterns.forEach((pattern) -> kieSession.insert(pattern));

		patternUtils.createCrochetPeriodsForUser(userId);
		List<CrochetPeriod> crochetPeriods = crochetPeriodService.loadCrochetPeriods(userId);
		System.out.println(crochetPeriods);
		crochetPeriods.forEach((crochetPeriod) -> kieSession.insert(crochetPeriod));

		kieSession.getObjects().forEach((item) -> System.out.println(item));
		int times = kieSession.fireAllRules();
		System.out.println(times);
		return;
	}
}
