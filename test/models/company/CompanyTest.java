package models.company;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import models.company.Address;
import models.company.Company;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class CompanyTest extends UnitTest {

	@Before
	public void clearDatabase() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void after_save_with_address_everything_can_be_fetched() {
		// given
		String street = "street";
		String postCode = "N15 5QP";
		String country = "Poland";
		Address address = new Address(street, postCode, country);
		String name = "company";
		Company company = new Company(name, address);
		
		// when
		company.save();
		Company fetchedCompany = Company.find("byName", name).first();
		
		// then
		assertThat(fetchedCompany, notNullValue());
		assertThat(company.name, is(name));
		
		Address fetchedAddress = fetchedCompany.address;
		assertThat(fetchedAddress, notNullValue());
		assertThat(fetchedAddress.street, is(street));
		assertThat(fetchedAddress.postCode, is(postCode));
		assertThat(fetchedAddress.country, is(country));
	}
}
