import React from "react";
import { useFormik } from "formik";
import styled from "styled-components";

import FormInput from "../../../DesignSystem/Form/FormInput";
import SubmitButton from "../../../DesignSystem/Form/SubmitButton";
import Title from "../../../DesignSystem/Title";
import {EligibilityApplicationProps, FormValues} from "../Eligibility";

const FormWrapper = styled.div`
  flex: 1 1 auto;
  width: 100%;
`;

const EligibilityApplication :React.FC<EligibilityApplicationProps> = (props) => {
  const { handleChange, handleSubmit, values } = useFormik<FormValues>({
    initialValues: {
      name: "",
      email: "",
      address: "",
    },
    onSubmit: (values) => props.onSubmit(values)
  });
  return (
    <FormWrapper>
      <Title>Cards</Title>
      <form onSubmit={handleSubmit}>
        <FormInput
          type="text"
          name="name"
          id="name"
          onChange={handleChange}
          value={values.name}
          placeholder="Name"
        />
        <FormInput
          type="email"
          name="email"
          id="email"
          onChange={handleChange}
          value={values.email}
          placeholder="Email"
        />
        <FormInput
          type="text"
          name="address"
          id="address"
          onChange={handleChange}
          value={values.address}
          placeholder="Address"
        />
        <SubmitButton text="Submit" />
      </form>
    </FormWrapper>
  );
};

export default EligibilityApplication;
