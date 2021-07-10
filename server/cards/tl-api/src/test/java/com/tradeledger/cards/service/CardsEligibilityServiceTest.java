package com.tradeledger.cards.service;

import com.tradeledger.cards.client.ThirdPartyClient;
import com.tradeledger.cards.common.Applicant;
import com.tradeledger.cards.common.EligibilityResponse;
import feign.FeignException;
import feign.Request;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

class CardsEligibilityServiceTest {

    private final ThirdPartyClient thirdPartyClient = Mockito.mock(ThirdPartyClient.class);
    private final CardsEligibilityService cardsEligibilityService = new CardsEligibilityService(thirdPartyClient);
    private final Applicant applicant = new Applicant("test","test@test.com","11 George Street, Sydney, NSW Australia");

    @Test
    @DisplayName("call third party feign client to get eligiblity data")
    void checkEligibility() {
        EligibilityResponse eligibilityResponse = new EligibilityResponse(List.of("C1"));
        Mockito.when(thirdPartyClient.check(applicant)).thenReturn(eligibilityResponse);
        final EligibilityResponse response = cardsEligibilityService.checkEligibilityFor(applicant);
        Assertions.assertEquals(eligibilityResponse,response);
        Mockito.verify(thirdPartyClient).check(applicant);
    }

    @Test
    @DisplayName("should propagate exception from third party api call")
    void checkException() {
        Mockito.when(thirdPartyClient.check(applicant)).thenThrow(new RuntimeException());
        Assertions.assertThrows(RuntimeException.class, ()-> cardsEligibilityService.checkEligibilityFor(applicant));
        Mockito.verify(thirdPartyClient).check(applicant);
    }

}