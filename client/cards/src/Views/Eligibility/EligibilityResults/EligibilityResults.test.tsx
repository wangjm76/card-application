import {shallow} from "enzyme";
import EligibilityApplication from "../EligibilityApplication";
import FormInput from "../../../DesignSystem/Form/FormInput";
import React from "react";
import EligibilityResults from "./EligibilityResults";
import {render, screen} from "@testing-library/react";
import Card from "../../../DesignSystem/Card";

describe('<EligibilityResults />', () => {
    it(`should render EligibilityResults component with 2 eligible cards`, async () => {
        render(<EligibilityResults eligibleCards={['C1','C2']}/>);
        expect(await screen.findByText('C1')).toBeInTheDocument();
        expect(await screen.findByText('C1')).toBeInTheDocument();
    });
    it(`should render EligibilityResults component with no eligible card`, async () => {
        render(<EligibilityResults eligibleCards={[]}/>);
        expect(await screen.findByText('No Eligible Card')).toBeInTheDocument();
    });

    it(`should render EligibilityResults component with error information card`, async () => {
        render(<EligibilityResults error={'test'}/>);
        expect(await screen.findByText('test')).toBeInTheDocument();
    });
});