package com.tradeledger.cards.service;

import com.tradeledger.cards.client.ThirdPartyClient;
import com.tradeledger.cards.common.Applicant;
import com.tradeledger.cards.common.EligibilityResponse;
import org.springframework.stereotype.Service;

@Service
public class CardsEligibilityService{
    private final ThirdPartyClient thirdPartyClient;
    public CardsEligibilityService(ThirdPartyClient thirdPartyClient) {
        this.thirdPartyClient = thirdPartyClient;
    }

    public EligibilityResponse checkEligibilityFor(Applicant applicant){
        EligibilityResponse result = thirdPartyClient.check(applicant);
        return result;
    }
}
