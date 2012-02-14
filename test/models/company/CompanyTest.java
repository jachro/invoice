package models.company;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class CompanyTest extends UnitTest {
	public static final String street = "street";
	public static final String postCode = "N15 5QP";
	public static final String country = "Poland";
	public static final String name = "company";

	public static Company prepareCompany() {
		return prepareCompany(name);
	}
	
	public static Company prepareCompany(String name) {
		Address address = new Address(street, postCode, country);
		return new Company(name, address);
	}

	@Before
	public void clearDatabase() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void after_save_with_address_everything_can_be_fetched() {
		// given
		Company company = prepareCompany();
		
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
	
	@Test
	public void after_save_with_account_everything_can_be_fetched() {
		// given
		Company company = prepareCompany();
		
		String number1 = "1111";
		Account account1 = new Account(number1);
		String number2 = "2222";
		Account account2 = new Account(number2);
		
		// when
		company.save();
		company = company.addAccount(account1);
		company = company.addAccount(account2);
		
		Company fetchedCompany = Company.find("byName", name).first();
		
		// then
		List<Account> fetchedAccounts = fetchedCompany.accounts;
		assertThat(fetchedAccounts.size(), is(2));
		Iterator<Account> fetchedAccountIterator = fetchedAccounts.iterator();
		assertThat(fetchedAccountIterator.next().number, is(number1));
		assertThat(fetchedAccountIterator.next().number, is(number2));
	}
}
