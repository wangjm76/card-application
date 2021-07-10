package com.tradeledger.cards.common;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public final class Applicant {

	@NotNull
	private String name;

	@NotNull
	private String address;

	@NotNull
	private String email;

	public Applicant() {}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Applicant applicant = (Applicant) o;
		return Objects.equals(name, applicant.name) && Objects.equals(address, applicant.address) && Objects.equals(email, applicant.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, address, email);
	}

	public Applicant(String name, String email, String address) {

		this.name = name;
		this.email = email;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

}
