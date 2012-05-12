package controllers.company;

import org.junit.Test;

import play.mvc.Http.Response;
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

		// when
		Response response = POST("/saveMyCompany");

		// then
		assertIsOk(response);
	}
}
