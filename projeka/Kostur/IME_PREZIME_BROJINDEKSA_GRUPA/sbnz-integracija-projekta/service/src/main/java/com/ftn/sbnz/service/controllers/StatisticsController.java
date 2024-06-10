package com.ftn.sbnz.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.events.TimeEvent;
import com.ftn.sbnz.service.model.StatsDTO;
import com.ftn.sbnz.service.services.ICrochetPeriodService;
import com.ftn.sbnz.service.services.IPatternService;
import com.ftn.sbnz.service.services.IUserService;
import com.ftn.sbnz.service.services.implementations.KieSessionService;
import com.ftn.sbnz.service.utils.TokenUtils;

@RestController
@RequestMapping(value = "api/stats")
@CrossOrigin
public class StatisticsController {
    @Autowired
	private TokenUtils tokenUtils;

	@Autowired IUserService userService;

	@Autowired
	IPatternService patternService;

	@Autowired
	ICrochetPeriodService crochetPeriodService;

	@Autowired
	private KieSessionService kieSessionService;

    @GetMapping("/")
	public ResponseEntity<?> getStats(@RequestHeader("Authorization") String token, @RequestParam String period) {
        Integer userId = tokenUtils.getIdFromToken(token.substring(7));
		StatsDTO dto = kieSessionService.getStats(userId);
        return new ResponseEntity<StatsDTO>(dto, HttpStatus.OK);
    }
    
}
