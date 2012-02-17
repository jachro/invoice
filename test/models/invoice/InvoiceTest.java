package models.invoice;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import models.Currency;
import models.company.Account;
import models.company.Company;
import models.company.CompanyTest;
import models.company.MyCompany;
import models.company.MyCompanyTest;
import models.rate.Rate;
import models.rate.RateTable;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class InvoiceTest extends UnitTest {
	private static final String NUMBER = "number";
	private static final String CLIENT = "client";
	private static final PaymentType PAYMENT_TYPE = PaymentType.TRANSFER;
	private static final String ACCOUNT_NUMBER = "2222";
	private static final Currency CURRENCY = Currency.EUR;
	private static final String ORDER = "order";
	private static final Date INVOICE_DATE = new GregorianCalendar(2012, 0, 2).getTime();
	private static final Date SELLING_DATE = new GregorianCalendar(2012, 0, 1).getTime();
	private static final Integer DUE_PERIOD = 31;
	private static final Date DUE_DATE = new GregorianCalendar(2012, 1, 1).getTime();
	private static final String TRANSPORT = "smif88";
	
	private final Account account = new Account(ACCOUNT_NUMBER);
	private MyCompany myCompany;
	private Company company;
	private Invoice invoice;
	
	@Before
	public void clearDatabase() {
		Fixtures.deleteDatabase();
	}

	private void prepareEntities() {
		myCompany = MyCompanyTest.prepareMyCompany();
		myCompany = myCompany.save();
		myCompany.company.addAccount(account);
		
		company = CompanyTest.prepareCompany(CLIENT);
		company.save();
		
		invoice = new Invoice(NUMBER, PAYMENT_TYPE, account, CURRENCY, ORDER, INVOICE_DATE, SELLING_DATE, DUE_PERIOD, DUE_DATE, TRANSPORT, myCompany, company);
	}

	@Test
	public void saved_invoice_can_be_fetched() {
		// given
		prepareEntities();
		
		// when
		invoice.save();
		
		Invoice fetched = Invoice.find("byNumber", NUMBER).first();
		
		// then
		assertThat(fetched, notNullValue());
		assertThat(fetched.number, is(NUMBER));
		assertThat(fetched.paymentType, is(PAYMENT_TYPE));
		assertThat(fetched.account.id, notNullValue());
		assertThat(fetched.currency, is(CURRENCY));
		assertThat(fetched.invoiceOrder, is(ORDER));
		assertThat(fetched.invoiceDate, is(INVOICE_DATE));
		assertThat(fetched.sellingDate, is(SELLING_DATE));
		assertThat(fetched.duePeriod, is(DUE_PERIOD));
		assertThat(fetched.dueDate, is(DUE_DATE));
		assertThat(fetched.transport, is(TRANSPORT));
		assertThat(fetched.myCompany, is(myCompany));
		assertThat(fetched.company, is(company));
	}
	
	@Test
	public void saved_invoice_with_exchange_rate_can_be_fetched() {
		// given
		prepareEntities();
		
		Date date = new GregorianCalendar(2012, 0, 1).getTime();
		String tableName = "table 1/2012";
		RateTable table = new RateTable(tableName, date);
		table.save();

		Currency fromCurrency1 = Currency.PLN;
		Currency toCurrency1 = Currency.EUR;
		BigDecimal exchangeRate1 = new BigDecimal(4.5);
		Rate rate1 = new Rate(fromCurrency1, toCurrency1, exchangeRate1);
		table.addRate(rate1);
		
		// when
		RateTable fetchedTable = RateTable.find("byName", tableName).first();
		Rate fetchedRate = fetchedTable.rates.get(0);
		
		invoice.setRate(fetchedRate);
		
		Invoice fetched = Invoice.find("byNumber", NUMBER).first();
		
		// then
		assertThat(fetched, notNullValue());
		assertThat(fetched.rate.toCurrency, is(toCurrency1));
		assertThat(fetched.rate.table.name, is(tableName));
	}
	
	@Test
	public void saved_invoice_with_items_can_be_fetched() {
		// given
		prepareEntities();
		
		Integer number = 1;
		String serviceName = "for transport";
		String symbol = "60.24.22";
		Integer quantity = 1;
		BigDecimal net = new BigDecimal(2.2);
		BigDecimal netTotal = new BigDecimal(2.2);
		Tax tax = Tax.VAT22;
		BigDecimal taxAmount = new BigDecimal(2.2);
		BigDecimal gross = new BigDecimal(2.2);
		InvoiceItem item1 = new InvoiceItem(number, serviceName, symbol, quantity, net, netTotal, tax, taxAmount, gross);
		
		// when
		invoice.save();
		invoice = invoice.addItem(item1);
		
		Invoice fetched = Invoice.find("byNumber", NUMBER).first();
		
		// then
		assertThat(fetched, notNullValue());
		assertThat(fetched.items.size(), is(1));
		
		Iterator<InvoiceItem> itemsIterator = fetched.items.iterator();
		InvoiceItem fetchedItem = itemsIterator.next();
		assertThat(fetchedItem.number, is(number));
		assertThat(fetchedItem.serviceName, is(serviceName));
		assertThat(fetchedItem.symbol, is(symbol));
		assertThat(fetchedItem.quantity, is(quantity));
		assertThat(fetchedItem.net, is(net));
		assertThat(fetchedItem.netTotal, is(netTotal));
		assertThat(fetchedItem.tax, is(tax));
		assertThat(fetchedItem.taxAmount, is(taxAmount));
		assertThat(fetchedItem.gross, is(gross));
	}
}
