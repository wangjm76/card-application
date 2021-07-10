package com.tradeledger.cards.common;

import jdk.jfr.DataAmount;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EligibilityResponse {
    private List<String> eligibleCards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EligibilityResponse that = (EligibilityResponse) o;
        return Objects.equals(eligibleCards, that.eligibleCards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eligibleCards);
    }

    public EligibilityResponse(List<String> eligibleCards) {
        this.eligibleCards = eligibleCards;
    }

    public EligibilityResponse(EligibilityResponse another) {
        this.eligibleCards = another.eligibleCards;
    }

    public EligibilityResponse() {
    }

    public List<String> getEligibleCards() {
        return eligibleCards;
    }

    public void setEligibleCards(List<String> eligibleCards) {
        this.eligibleCards = eligibleCards;
    }
}
