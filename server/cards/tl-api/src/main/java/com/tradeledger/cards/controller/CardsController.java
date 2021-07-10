package com.tradeledger.cards.controller;

import com.tradeledger.cards.common.Applicant;
import com.tradeledger.cards.common.EligibilityResponse;
import com.tradeledger.cards.service.CardsEligibilityService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Map;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin
@RestController
@RequestMapping("/eligibility")
public class CardsController{
    private final CardsEligibilityService cardsEligibilityService;
    public CardsController(CardsEligibilityService cardsEligibilityService) {
        this.cardsEligibilityService = cardsEligibilityService;
    }

    @PostMapping(path = "/check", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public EligibilityResponse checkEligibility(@Valid @RequestBody Applicant applicant) {
        return cardsEligibilityService.checkEligibilityFor(applicant);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map<String, String> serviceUnavailable() {
        return Map.of("error" , "service unavailable");
    }
}
