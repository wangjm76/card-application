import React from 'react';
import App from './App';
import { shallow } from 'enzyme';
import Eligibility from "./Views/Eligibility";

describe('<App />', () => {
  it(`should render app with Eligiblity component`, () => {
    const wrapper = shallow(<App />);
    expect(wrapper.find(Eligibility)).toHaveLength(1);
  });
});
