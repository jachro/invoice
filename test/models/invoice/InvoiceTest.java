package models.invoice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import models.company.Company;
import models.company.CompanyTest;
import models.company.MyCompany;
import models.company.MyCompanyTest;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class InvoiceTest extends UnitTest {
	private static final String NUMBER = "number";
	private static final String CLIENT = "client";
	private static final Date DATE = new GregorianCalendar(2012, 0, 1).getTime();

	@Before
	public void clearDatabase() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void saved_invoice_can_be_fetched() {
		// given
		MyCompany myCompany = MyCompanyTest.prepareMyCompany();
		myCompany.save();
		
		Company company = CompanyTest.prepareCompany(CLIENT);
		company.save();
		
		Invoice invoice = new Invoice(NUMBER, DATE, myCompany, company);
		
		// when
		invoice.save();
		
		Invoice fetched = Invoice.find("byNumber", NUMBER).first();
		
		// then
		assertThat(fetched, notNullValue());
		assertThat(fetched.number, is(NUMBER));
		assertThat(fetched.date, is(DATE));
		assertThat(fetched.myCompany, is(myCompany));
		assertThat(fetched.company, is(company));
	}
	
	@Test
	public void saved_invoice_with_items_can_be_fetched() {
		// given
		MyCompany myCompany = MyCompanyTest.prepareMyCompany();
		myCompany.save();
		
		Company company = CompanyTest.prepareCompany(CLIENT);
		company.save();
		
		Invoice invoice = new Invoice(NUMBER, DATE, myCompany, company);
		
		BigDecimal amount1 = new BigDecimal(2.2);
		Tax tax1 = Tax.VAT22;
		InvoiceItem item1 = new InvoiceItem(amount1, tax1);
		
		BigDecimal amount2 = new BigDecimal(0.2);
		Tax tax2 = Tax.VAT0;
		InvoiceItem item2 = new InvoiceItem(amount2, tax2);
		
		// when
		invoice.save();
		invoice = invoice.addItem(item1);
		invoice = invoice.addItem(item2);
		
		Invoice fetched = Invoice.find("byNumber", NUMBER).first();
		
		// then
		assertThat(fetched, notNullValue());
		assertThat(fetched.items.size(), is(2));
		
		Iterator<InvoiceItem> itemsIterator = fetched.items.iterator();
		InvoiceItem fetchedItem = itemsIterator.next();
		assertThat(fetchedItem.amount, is(amount1));
		assertThat(fetchedItem.tax, is(tax1));
		
		fetchedItem = itemsIterator.next();
		assertThat(fetchedItem.amount, is(amount2));
		assertThat(fetchedItem.tax, is(tax2));
	}
}
