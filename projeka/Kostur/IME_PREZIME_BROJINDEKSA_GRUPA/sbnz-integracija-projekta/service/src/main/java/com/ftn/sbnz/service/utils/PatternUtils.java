package com.ftn.sbnz.service.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.log.LogDelegateFactory;
import org.springframework.stereotype.Service;

import com.ftn.sbnz.model.models.Color;
import com.ftn.sbnz.model.models.CrochetPeriod;
import com.ftn.sbnz.model.models.CrochetType;
import com.ftn.sbnz.model.models.Difficulty;
import com.ftn.sbnz.model.models.Korisnik;
import com.ftn.sbnz.model.models.Pattern;
import com.ftn.sbnz.model.models.WoolSize;
import com.ftn.sbnz.model.models.Yarn;
import com.ftn.sbnz.model.models.YarnType;
import com.ftn.sbnz.service.repositories.ICrochedPeriodRepository;
import com.ftn.sbnz.service.repositories.ICrochetTypeRepository;
import com.ftn.sbnz.service.repositories.IPatternRepository;
import com.ftn.sbnz.service.services.IUserService;

@Service
public class PatternUtils {

    @Autowired
    private IPatternRepository patternRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICrochetTypeRepository crochetTypeRepository;

    @Autowired
    private ICrochedPeriodRepository crochedPeriodRepository;

    public Pattern createPatterns() {
        CrochetType typeRoot = new CrochetType("Crochet", "", false);
        CrochetType type1 = new CrochetType("Amigurumi", "Crochet", false);
        CrochetType type2 = new CrochetType("Clothes", "Crochet", false);
        CrochetType type3 = new CrochetType("Animals", "Amigurumi", false);
        CrochetType type4 = new CrochetType("Toys", "Amigurumi", false);
        CrochetType type5 = new CrochetType("Winter clothes", "Clothes", false);
        CrochetType type6 = new CrochetType("Summer clothes", "Clothes", false);
        CrochetType type7 = new CrochetType("Accessories", "Crochet", false);
        
        typeRoot = crochetTypeRepository.save(typeRoot);
        type1 = crochetTypeRepository.save(type1);
        type2 = crochetTypeRepository.save(type2);
        type3 = crochetTypeRepository.save(type3);
        type4 = crochetTypeRepository.save(type4);
        type5 = crochetTypeRepository.save(type5);
        type6 = crochetTypeRepository.save(type6);
        type7 = crochetTypeRepository.save(type7);

        crochetTypeRepository.flush();


        Pattern babyBearPoncho = new Pattern(null, "Baby bear poncho", Difficulty.ADVANCED, 6.0, 
            new ArrayList<>(Arrays.asList(new Yarn(YarnType.FUZZY, WoolSize.BULKY, 2, Color.BROWN), 
            new Yarn(YarnType.FUZZY, WoolSize.BULKY, 1, Color.BEIGE_LIGHT))),
            "clothes_baby_bear_poncho.pdf", new ArrayList<>(Arrays.asList("Sewing needle")), false, type5);
        
        Pattern squareRabbit = new Pattern(null, "Square rabbit", Difficulty.INTERMEDIATE, 3.5, 
            new ArrayList<>(Arrays.asList(new Yarn(YarnType.ACRYLLIC, WoolSize.WORSTED, 1, Color.PINK_LIGHT), 
            new Yarn(YarnType.ACRYLLIC, WoolSize.WORSTED, 1, Color.WHITE))),
            "square_rabbit.pdf", new ArrayList<>(Arrays.asList("Sewing needle", "safety eyes", "stuffing")), false, type3);

        Pattern octopus = new Pattern(null, "Octopus", Difficulty.INTERMEDIATE, 4.0, 
            new ArrayList<>(Arrays.asList(new Yarn(YarnType.COTTON, WoolSize.WORSTED, 2, Color.ORANGE), 
            new Yarn(YarnType.COTTON, WoolSize.WORSTED, 2, Color.BLACK), 
            new Yarn(YarnType.COTTON, WoolSize.WORSTED, 1, Color.WHITE))),
            "octopus.pdf", new ArrayList<>(Arrays.asList("Yarn needle", "stuffing")), false, type3);

        Pattern cactus = new Pattern(null, "Cactus", Difficulty.BEGGINER, 4.0, 
            new ArrayList<>(Arrays.asList(new Yarn(YarnType.COTTON, WoolSize.WORSTED, 1, Color.BROWN_LIGHT), 
            new Yarn(YarnType.COTTON, WoolSize.WORSTED, 2, Color.GREEN_DARK), 
            new Yarn(YarnType.COTTON, WoolSize.WORSTED, 1, Color.YELLOW))),
            "cactus.pdf", new ArrayList<>(Arrays.asList("stuffing")), false, type4);

        Pattern basket = new Pattern(null, "Basket", Difficulty.BEGGINER, 6.5, 
            new ArrayList<>(Arrays.asList(new Yarn(YarnType.CHENILLE, WoolSize.SUPER_BULKY, 2, Color.GRAY))),
            "basket.pdf", new ArrayList<>(), false, type7);

        Pattern hatWithBubble = new Pattern(null, "Beanie with bubble", Difficulty.BEGGINER, 8.0, 
            new ArrayList<>(Arrays.asList(new Yarn(YarnType.ACRYLLIC, WoolSize.SUPER_BULKY, 2, Color.BEIGE_DARK))),
            "hat_with_bubble.pdf", new ArrayList<>(Arrays.asList()), false, type5);

        Pattern corset = new Pattern(null, "Corset", Difficulty.INTERMEDIATE, 4.0, 
            new ArrayList<>(Arrays.asList(new Yarn(YarnType.COTTON, WoolSize.BULKY, 1, Color.PINK_LIGHT))),
            "corset.pdf", new ArrayList<>(Arrays.asList("Satin ribbon", "corset hooks", "yarn needle")), false, type6);

        patternRepository.save(babyBearPoncho);
        patternRepository.save(squareRabbit);
        patternRepository.save(cactus);
        patternRepository.save(basket);
        patternRepository.save(hatWithBubble);
        patternRepository.save(corset);
        octopus = patternRepository.save(octopus);

        return octopus;
    }

    public void createCrochetPeriodsForUser(Integer userId) {
        Korisnik user = userService.get(userId);
        CrochetPeriod cp1 = new CrochetPeriod(user, Duration.ofMinutes(30), LocalDate.of(2024, 6, 1));
        CrochetPeriod cp2 = new CrochetPeriod(user, Duration.ofMinutes(30), LocalDate.of(2024, 5, 15));
        CrochetPeriod cp3 = new CrochetPeriod(user, Duration.ofMinutes(30), LocalDate.of(2024, 6, 7));
        
        crochedPeriodRepository.save(cp1);
        crochedPeriodRepository.save(cp2);
        crochedPeriodRepository.save(cp3);
        crochedPeriodRepository.flush();
    }

}
