package com.revature.models;

import com.revature.models.annotation.ClassWorm;
import com.revature.models.annotation.FieldWorm;
import com.revature.models.enums.EnumConstraintsWorm;

import java.time.LocalDate;
import java.util.Date;


@ClassWorm(table = "contact_book2")
public class Entry {

	//@FieldWorm(Name = "first_name", constraints = EnumConstraintsWorm.NonNull)
	private String firstName;
	//@FieldWorm(Name = "last_name", constraints = EnumConstraintsWorm.NonNull)
	private String lastName;
	private String streetName;
	private String cityName;
	private String stateName;
	private int zipCode;
	private String phoneNumber;
	private LocalDate birthDay;

	public Entry() {
	}
	public Entry(String firstName, String lastName, String streetName, String cityName, String stateName, int zipCode, String phoneNumber, LocalDate birthDay) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.streetName = streetName;
		this.cityName = cityName;
		this.stateName = stateName;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber;
		this.birthDay = birthDay;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public int getZipCode() {
		return zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(LocalDate birthDay) {
		this.birthDay = birthDay;
	}

	@Override
	public String toString() {
		return "Entry{" +
			"firstName='" + firstName + '\'' +
			", lastName='" + lastName + '\'' +
			", streetName='" + streetName + '\'' +
			", cityName='" + cityName + '\'' +
			", stateName='" + stateName + '\'' +
			", zipCode=" + zipCode +
			", phoneNumber='" + phoneNumber + '\'' +
			", birthDay=" + birthDay +
			'}';
	}
}
