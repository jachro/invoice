package controllers.company;

import static org.hamcrest.CoreMatchers.is;

import java.util.HashMap;
import java.util.Map;

import models.Currency;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Http.Response;
import play.test.Fixtures;
import play.test.FunctionalTest;

public class MyCompanyControllerTest extends FunctionalTest {

	@Test
	public void myCompany_renders_the_My_Company_page() {
		// given

		// when
		Response response = GET("/myCompany");

		// then
        assertIsOk(response);
	}

	@Test
	public void saveMyCompany_renders_the_My_Company_page() {
		// given
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("myCompany.company.name", "name");
		parameters.put("myCompany.company.nip", "123");

		parameters.put("myCompany.company.address.street", "street");
		parameters.put("myCompany.company.address.postCode", "N15 5QP");
		parameters.put("myCompany.company.address.country", "UK");

		parameters.put("myCompany.company.accounts[0].number", "1234");
		parameters.put("myCompany.company.accounts[0].currency", Currency.GBP.name());

		// when
		Response response = POST("/myCompany", parameters);

		// then
		assertIsOk(response);
	}
}
