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
	public static final String STREET = "street";
	public static final String POST_CODE = "N15 5QP";
	public static final String COUNTRY = "Poland";
	public static final String NAME = "company";
	public static final String NIP = "2233";

	public static Company prepareCompany() {
		return prepareCompany(NAME);
	}
	
	public static Company prepareCompany(String name) {
		Address address = new Address(STREET, POST_CODE, COUNTRY);
		return new Company(name, NIP, address);
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
		
		Company fetchedCompany = Company.find("byName", NAME).first();
		
		// then
		assertThat(fetchedCompany, notNullValue());
		assertThat(company.name, is(NAME));
		assertThat(company.nip, is(NIP));
		
		Address fetchedAddress = fetchedCompany.address;
		assertThat(fetchedAddress, notNullValue());
		assertThat(fetchedAddress.street, is(STREET));
		assertThat(fetchedAddress.postCode, is(POST_CODE));
		assertThat(fetchedAddress.country, is(COUNTRY));
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
		
		Company fetchedCompany = Company.find("byName", NAME).first();
		
		// then
		List<Account> fetchedAccounts = fetchedCompany.accounts;
		assertThat(fetchedAccounts.size(), is(2));
		Iterator<Account> fetchedAccountIterator = fetchedAccounts.iterator();
		assertThat(fetchedAccountIterator.next().number, is(number1));
		assertThat(fetchedAccountIterator.next().number, is(number2));
	}
}
