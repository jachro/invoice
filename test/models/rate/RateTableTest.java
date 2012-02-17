package models.rate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import models.Currency;
import models.company.Account;

import org.junit.Before;
import org.junit.Test;

import play.test.Fixtures;
import play.test.UnitTest;

public class RateTableTest extends UnitTest {
	public static final String TABLE_NAME = "table 1/2012";

	@Before
	public void clearDatabase() {
		Fixtures.deleteDatabase();
	}

	@Test
	public void saved_rate_table_can_be_fetched() {
		// given
		Date date = new GregorianCalendar(2012, 0, 1).getTime();
		RateTable table = new RateTable(TABLE_NAME, date);
		
		// when
		table.save();
		
		RateTable fetched = RateTable.find("byName", TABLE_NAME).first();
		
		// then
		assertThat(fetched, notNullValue());
		assertThat(fetched.name, is(TABLE_NAME));
		assertThat(fetched.date, is(date));
	}
	
	@Test
	public void rates_saved_with_rate_table_can_be_fetched() {
		// given
		Date date = new GregorianCalendar(2012, 0, 1).getTime();
		RateTable table = new RateTable(TABLE_NAME, date);

		Currency fromCurrency1 = Currency.PLN;
		Currency toCurrency1 = Currency.EUR;
		BigDecimal exchangeRate1 = new BigDecimal(4.5);
		Rate rate1 = new Rate(fromCurrency1, toCurrency1, exchangeRate1);
		
		Currency fromCurrency2 = Currency.PLN;
		Currency toCurrency2 = Currency.GBP;
		BigDecimal exchangeRate2 = new BigDecimal(5);
		Rate rate2 = new Rate(fromCurrency2, toCurrency2, exchangeRate2);
		
		// when
		table.save();
		
		table = table.addRate(rate1);
		table = table.addRate(rate2);
		
		RateTable fetched = RateTable.find("byName", TABLE_NAME).first();
		
		// then
		List<Rate> fetchedRates = fetched.rates;
		assertThat(fetchedRates.size(), is(2));
		Iterator<Rate> fetchedRatesIterator = fetchedRates.iterator();
		verifyRate(fromCurrency1, toCurrency1, exchangeRate1, fetchedRatesIterator.next());
		verifyRate(fromCurrency2, toCurrency2, exchangeRate2, fetchedRatesIterator.next());
	}

	private void verifyRate(Currency fromCurrency, Currency toCurrency, BigDecimal exchangeRate, Rate rate) {
		assertThat(rate.fromCurrency, is(fromCurrency));
		assertThat(rate.toCurrency, is(toCurrency));
		assertThat(rate.rate, is(exchangeRate));
	}
}
