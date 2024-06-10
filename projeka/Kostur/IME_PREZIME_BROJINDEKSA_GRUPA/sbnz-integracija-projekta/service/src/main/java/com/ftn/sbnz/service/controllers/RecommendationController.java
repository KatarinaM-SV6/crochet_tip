package com.ftn.sbnz.service.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.sbnz.model.events.TimeEvent;
import com.ftn.sbnz.model.models.Pattern;
import com.ftn.sbnz.model.models.Recommendation;
import com.ftn.sbnz.service.services.ICrochetPeriodService;
import com.ftn.sbnz.service.services.IPatternService;
import com.ftn.sbnz.service.services.IUserService;
import com.ftn.sbnz.service.services.implementations.KieSessionService;
import com.ftn.sbnz.service.utils.TokenUtils;

@RestController
@RequestMapping(value = "api/recommendation")
@CrossOrigin
public class RecommendationController {
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
	public ResponseEntity<?> getRecommendation(@RequestHeader("Authorization") String token, @RequestParam String difficulty) {
        Integer userId = tokenUtils.getIdFromToken(token.substring(7));

        Recommendation rec = kieSessionService.getRecommendation(userId, difficulty);
        return new ResponseEntity<Recommendation>(rec, HttpStatus.OK);
    }

    @PutMapping("/reject")
	public ResponseEntity<?> rejectRecommendation(@RequestHeader("Authorization") String token, @RequestBody Recommendation rec) {
        Integer userId = tokenUtils.getIdFromToken(token.substring(7));

        Recommendation rec2 = kieSessionService.rejectRecommendation(userId, rec);
        return new ResponseEntity<Recommendation>(rec2, HttpStatus.OK);
    }

    @PutMapping("/accept")
	public ResponseEntity<?> acceptRecommendation(@RequestHeader("Authorization") String token, @RequestBody Recommendation rec) {
        Integer userId = tokenUtils.getIdFromToken(token.substring(7));
        userService.setCurrentProject(userId, rec.getPattern());

        Recommendation rec2 = kieSessionService.acceptRecommendation(userId, rec);
        return new ResponseEntity<Recommendation>(rec2, HttpStatus.OK);
    }
}
