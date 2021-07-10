import React from "react";
import styled from "styled-components";
import Card from "../../../DesignSystem/Card";
import {EligibilityResult} from "../Eligibility";

const ResultsWrapper = styled.div`
  flex: 1 1 auto;
  padding-top: 48px;
  justify-content: center;
  margin: 0 -8px;
  display: flex;
  flex-wrap: wrap;
`;

const EligibilityResults : React.FC<EligibilityResult> = ({eligibleCards,error } : EligibilityResult ) => {
  return <ResultsWrapper>
    {(eligibleCards !== undefined && eligibleCards.length > 0) &&
        eligibleCards.map((card,index) => {
          return <Card key={index}>{card}</Card>;
        })
    }
    {(eligibleCards !== undefined && eligibleCards.length === 0) &&
       <Card>No Eligible Card</Card>
    }
    {error !== undefined &&
      <Card>{error}</Card>
    }
  </ResultsWrapper>;
};

export default EligibilityResults;
