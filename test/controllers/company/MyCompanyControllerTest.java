package controllers.company;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Currency;
import models.company.Account;
import models.company.MyCompany;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class MyCompanyControllerTest extends FunctionalTest {

	@Test
	public void get_myCompany_renders_the_myCompany_page() {
		// given

		// when
		Response response = GET("/myCompany");

		// then
        assertIsOk(response);
	}

	@Test
	public void post_myCompany_gathers_the_form_data__saves_it_and_renders_the_myCompany_page() {
		// given
		Map<String, String> parameters = new HashMap<String, String>();
		String companyName = "name";
		parameters.put("myCompany.company.name", companyName);
		String companyNip = "123";
		parameters.put("myCompany.company.nip", companyNip);

		String street = "street";
		parameters.put("myCompany.company.address.street", street);
		String postCode = "N15 5QP";
		parameters.put("myCompany.company.address.postCode", postCode);
		String country = "UK";
		parameters.put("myCompany.company.address.country", country);

		String accountNumber = "1234";
		parameters.put("myCompany.company.accounts[0].number", accountNumber);
		Currency accountCurrency = Currency.GBP;
		parameters.put("myCompany.company.accounts[0].currency", accountCurrency.name());

		// when
		Response response = POST("/myCompany", parameters);

		// then
		assertIsOk(response);

		MyCompany actual = MyCompany.find("byCompany.name", companyName).first();
		assertThat(actual.id, notNullValue());

		assertThat(actual.company.name, is(companyName));
		assertThat(actual.company.nip, is(companyNip));

		assertThat(actual.company.address.id, notNullValue());
		assertThat(actual.company.address.street, is(street));
		assertThat(actual.company.address.postCode, is(postCode));
		assertThat(actual.company.address.country, is(country));

		assertThat(actual.company.accounts.size(), is(1));
		Account actualAccount = actual.company.accounts.get(0);
		assertThat(actualAccount.id, notNullValue());
		assertThat(actualAccount.number, is(accountNumber));
		assertThat(actualAccount.currency, is(accountCurrency));
	}

	@Test
	public void post_addAccount_gathers_all_the_form_data__adds_a_new_empty_account__does_not_save_and_renders_the_myCompany_page() {
		// given
		Map<String, String> parameters = new HashMap<String, String>();
		String companyName = "name";
		parameters.put("myCompany.company.name", companyName);
		String companyNip = "123";
		parameters.put("myCompany.company.nip", companyNip);

		String street = "street";
		parameters.put("myCompany.company.address.street", street);
		String postCode = "N15 5QP";
		parameters.put("myCompany.company.address.postCode", postCode);
		String country = "UK";
		parameters.put("myCompany.company.address.country", country);

		String accountNumber = "1234";
		parameters.put("myCompany.company.accounts[0].number", accountNumber);
		Currency accountCurrency = Currency.GBP;
		parameters.put("myCompany.company.accounts[0].currency", accountCurrency.name());

		String newAccountNumber = "12345";
		parameters.put("newAccountNumber", newAccountNumber);
		Currency newAccountCurrency = Currency.PLN;
		parameters.put("newAccountCurrency", newAccountCurrency.name());

		// when
		Response response = POST("/myCompany/addAccount", parameters);

		// then
		assertIsOk(response);
	}
}
