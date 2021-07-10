package com.tradeledger.cards.client;

import com.tradeledger.cards.common.Applicant;
import com.tradeledger.cards.common.EligibilityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Set;

@FeignClient(
        name = "thirdPartyClient",
        url = "${thirdparty.url}"
)
@RequestMapping("/eligibility")
public
interface ThirdPartyClient {
    @PostMapping("/check")
    EligibilityResponse check(@Valid @RequestBody Applicant applicant);
}