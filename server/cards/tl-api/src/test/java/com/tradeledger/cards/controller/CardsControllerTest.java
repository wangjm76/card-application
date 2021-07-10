package com.tradeledger.cards.controller;

import com.tradeledger.cards.client.ThirdPartyClient;
import com.tradeledger.cards.common.Applicant;
import com.tradeledger.cards.common.EligibilityResponse;
import com.tradeledger.cards.service.CardsEligibilityService;
import feign.FeignException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.SocketPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = CardsController.class)
@WebMvcTest
class CardsControllerTest {
    @MockBean
    private CardsEligibilityService cardsEligibilityService;
    private final CardsController cardsController = new CardsController(cardsEligibilityService);
    private final Applicant applicant = new Applicant("test","test@test.com","11 George Street, Sydney, NSW Australia");
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should call CardEligibilityService to get eligibility data")
    void checkEligibility() throws Exception {
        EligibilityResponse eligibilityResponse = new EligibilityResponse(List.of("C1"));
        Mockito.when(cardsEligibilityService.checkEligibilityFor(applicant)).thenReturn(eligibilityResponse);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/eligibility/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        //language=JSON
                        //language=JSON
                        .content("{\"name\":\"test\",\"email\":\"test@test.com\",\"address\":\"11 George Street, Sydney, NSW Australia\"}")
                        .contentType(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().is2xxSuccessful());
        Mockito.verify(cardsEligibilityService).checkEligibilityFor(applicant);
    }

    @Test
    @DisplayName("should return service unavailable if any exception")
    void checkServiceUnavailable() throws Exception {
        Mockito.when(cardsEligibilityService.checkEligibilityFor(applicant)).thenThrow(new RuntimeException());
        mockMvc.perform(
                MockMvcRequestBuilders.post("/eligibility/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        //language=JSON
                        //language=JSON
                        .content("{\"name\":\"test\",\"email\":\"test@test.com\",\"address\":\"11 George Street, Sydney, NSW Australia\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().is5xxServerError());
        Mockito.verify(cardsEligibilityService).checkEligibilityFor(applicant);
    }
}