import React from 'react';
import EligibilityApplication from './EligibilityApplication';
import { shallow } from 'enzyme';
import FormInput from "../../../DesignSystem/Form/FormInput";
import SubmitButton from "../../../DesignSystem/Form/SubmitButton";
import flushPromises from 'flush-promises';
import {act, fireEvent, render, screen} from "@testing-library/react";

describe('<EligibilityApplication />', () => {
    it(`should render EligibilityApplication component with form of 3 fields`, () => {
        const mockSubmit = jest.fn();
        const wrapper = shallow(<EligibilityApplication onSubmit={mockSubmit}/>);
        expect(wrapper.find(FormInput)).toHaveLength(3);
    });
});
describe('<EligibilityApplication />', () => {
    it(`should call submit with form data`, async () => {
    const mockSubmit = jest.fn();
    render(<EligibilityApplication onSubmit={mockSubmit}/>);
    (await screen.findByText('Submit')).dispatchEvent(new MouseEvent('click'));
    await act(flushPromises);
    expect(mockSubmit).toBeCalled();
    });
});