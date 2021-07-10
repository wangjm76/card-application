package com.tradeledger.cards.client;

import com.tradeledger.cards.common.Applicant;
import com.tradeledger.cards.common.EligibilityResponse;
import feign.FeignException;
import feign.RetryableException;
import feign.codec.DecodeException;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.SocketPolicy;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeoutException;


@SpringBootTest(classes = ThirdPartyClient.class)
@EnableFeignClients
@EnableAutoConfiguration
@TestPropertySource(locations = "application.properties")
class ThirdPartyClientTest {

    @Autowired
    private ThirdPartyClient thirdPartyClient;
    private MockWebServer thirdPartyServer = new MockWebServer();
    @BeforeEach
    void setup() throws IOException {
        thirdPartyServer.start(3380);
    }
    @AfterEach
    void finish() throws IOException {
        thirdPartyServer.shutdown();
    }
    @Test
    @DisplayName("should be able to call third party api and decode the response successfully")
    void checkEligibility() {
        thirdPartyServer.enqueue(new MockResponse()
                .setStatus("HTTP/1.1 " + HttpStatus.OK)
                //language=JSON
                .setBody("{\"eligibleCards\":[\"C1\",\"C2\"]}")
                .addHeader("Content-Type", "application/json")
        );
        EligibilityResponse response = thirdPartyClient.check(
            new Applicant("test","test@test.com","11 George Street, Sydney, NSW Australia")
        );
        Assertions.assertNotNull(response);
        Assertions.assertEquals(new EligibilityResponse(List.of("C1","C2")),response);
    }

    @Test
    @DisplayName("should throw exception if data from third party is not expected")
    void checkDecodeException() {
        thirdPartyServer.enqueue(new MockResponse()
                .setStatus("HTTP/1.1 " + HttpStatus.OK)
                //language=JSON
                .setBody("{\"eligibleCards\":\"C1\"}")
                .addHeader("Content-Type", "application/json")
        );

        Assertions.assertThrows(DecodeException.class, () ->  thirdPartyClient.check(
                new Applicant("test","test@test.com","11 George Street, Sydney, NSW Australia")
        ));
    }

    @Test
    @DisplayName("should throw exception if unexpected response from api")
    void checkException() {
        thirdPartyServer.enqueue(new MockResponse()
                .setStatus("HTTP/1.1 " + HttpStatus.BAD_REQUEST)
                .addHeader("Content-Type", "application/json")
        );

        Assertions.assertThrows(FeignException.BadRequest.class, () ->  thirdPartyClient.check(
                new Applicant("test","test@test.com","11 George Street, Sydney, NSW Australia")
        ));
    }

    @Test
    @DisplayName("should throw exception if api timeout")
    void checkTimeOutException() {
        thirdPartyServer.enqueue(new MockResponse()
                .setSocketPolicy(SocketPolicy.NO_RESPONSE)
        );

        Assertions.assertThrows(FeignException.class, () ->  thirdPartyClient.check(
                new Applicant("test","test@test.com","11 George Street, Sydney, NSW Australia")
        ));
    }
}