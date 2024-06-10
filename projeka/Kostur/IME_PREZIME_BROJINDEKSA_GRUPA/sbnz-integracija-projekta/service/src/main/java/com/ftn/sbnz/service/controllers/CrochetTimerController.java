package com.ftn.sbnz.service.controllers;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.events.TimeEvent;
import com.ftn.sbnz.model.models.CrochetPeriod;
import com.ftn.sbnz.service.services.ICrochetPeriodService;
import com.ftn.sbnz.service.services.IPatternService;
import com.ftn.sbnz.service.services.IUserService;
import com.ftn.sbnz.service.services.implementations.KieSessionService;
import com.ftn.sbnz.service.utils.PatternUtils;
import com.ftn.sbnz.service.utils.TokenUtils;


@RestController
@RequestMapping(value = "api/timer")
@CrossOrigin
public class CrochetTimerController {
    @Autowired
	private TokenUtils tokenUtils;

	@Autowired IUserService userService;

	@Autowired
	IPatternService patternService;

	@Autowired
	ICrochetPeriodService crochetPeriodService;

	@Autowired
	private KieSessionService kieSessionService;

	@Autowired
	private PatternUtils patternUtils;

    @GetMapping("/start")
	public ResponseEntity<?> start(@RequestHeader("Authorization") String token) {
        Integer userId = tokenUtils.getIdFromToken(token.substring(7));
		TimeEvent te = kieSessionService.startCrocheting(userId);
        return new ResponseEntity<TimeEvent>(te, HttpStatus.OK);
    }

	@GetMapping("/stop")
	public ResponseEntity<?> stop(@RequestHeader("Authorization") String token) {
        Integer userId = tokenUtils.getIdFromToken(token.substring(7));
		CrochetPeriod cp = kieSessionService.stopCrocheting(userId);
        return new ResponseEntity<CrochetPeriod>(cp, HttpStatus.OK);
    }
}
