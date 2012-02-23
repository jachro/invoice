package models.company;

import static org.hamcrest.CoreMatchers.is;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class MyCompanyTest extends UnitTest {

	@Before
	public void clearDatabase() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void saved_company_can_be_fetched() {
//		// given
//		MyCompany myCompany = prepareMyCompany();
//		
//		// when
//		myCompany.save();
//		
//		List<MyCompany> allFetched = MyCompany.findAll();
//		MyCompany fetched = allFetched.get(0);
//
//		// then
//		assertThat(fetched.company.name, is(CompanyTest.NAME));
	}

	public static MyCompany prepareMyCompany() {
		Company company = CompanyTest.prepareCompany();
		company.save();
		
		return new MyCompany(company);
	}

}
