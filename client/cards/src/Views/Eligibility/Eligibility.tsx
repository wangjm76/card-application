import React, {useState} from "react";
import View from "../../DesignSystem/View";
import EligibilityApplication from "./EligibilityApplication";
import EligibilityResults from "./EligibilityResults";

export interface FormValues {
    name: string;
    email: string;
    address: string;
}
export interface EligibilityApplicationProps {
    onSubmit: (values: FormValues) => void
}
export interface EligibilityResult {
    eligibleCards?: string[];
    error?: string;
}


function Eligibility() {
  const [result, setResult] = useState<EligibilityResult>();
  const onSubmit = async (values: FormValues) => {
      const response = await fetch('http://localhost:8080/eligibility/check', {
              method: "POST",
              body: JSON.stringify(values),
              headers: { "Content-Type": "application/json"}});
       const result = await response.json();
       setResult(result);
  };

  return (
    <View>
      <EligibilityApplication onSubmit={onSubmit}/>
      <EligibilityResults {...result}/>
    </View>
  );
}

export default Eligibility;
