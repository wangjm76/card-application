package com.tradeledger.cards;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tradeledger.cards.client.ThirdPartyClient;
import com.tradeledger.cards.common.EligibilityResponse;
import okhttp3.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
@TestPropertySource(locations = "application.properties")
public class CardsApplicationTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();
    private MockWebServer thirdPartyServer = new MockWebServer();

    @Test
    @DisplayName("should be able to call third party api and return correct response")
    public void testGetEligibility() throws Exception {
        thirdPartyServer.start(3390);
        thirdPartyServer.enqueue(new MockResponse()
                .setStatus("HTTP/1.1 " + HttpStatus.OK)
                //language=JSON
                .setBody("{\"eligibleCards\":[\"C1\"]}")
                .addHeader("Content-Type", "application/json")
        );
        MvcResult response = mockMvc.perform(
                MockMvcRequestBuilders.post("/eligibility/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        //language=JSON
                        .content("{\"name\":\"test\",\"email\":\"test@test.com\",\"address\":\"11 George Street, Sydney, NSW Australia\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().is2xxSuccessful())
        .andReturn();
        EligibilityResponse result = mapper.readValue(response.getResponse().getContentAsString(),EligibilityResponse.class);
        Assertions.assertEquals(new EligibilityResponse(List.of("C1")),result);
        thirdPartyServer.shutdown();
    }

    @Test
    @DisplayName("should return service unavailable when api fails")
    public void testServiceUnavailable() throws Exception {
        thirdPartyServer.start(3390);
        thirdPartyServer.enqueue(new MockResponse()
                .setStatus("HTTP/1.1 " + HttpStatus.BAD_REQUEST)
                .addHeader("Content-Type", "application/json")
        );
        MvcResult response = mockMvc.perform(
                MockMvcRequestBuilders.post("/eligibility/check")
                        .contentType(MediaType.APPLICATION_JSON)
                        //language=JSON
                        .content("{\"name\":\"test\",\"email\":\"test@test.com\",\"address\":\"11 George Street, Sydney, NSW Australia\"}")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isServiceUnavailable())
                .andReturn();
        Assertions.assertEquals("{\"error\":\"service unavailable\"}",response.getResponse().getContentAsString());
        thirdPartyServer.shutdown();
    }
}
