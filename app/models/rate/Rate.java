package models.rate;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Rate extends Model {
	@Enumerated(EnumType.STRING)
	public Currency fromCurrency;
	@Enumerated(EnumType.STRING)
	public Currency toCurrency;
	public BigDecimal rate;
	
	@ManyToOne
	@JoinColumn(name="rate_table_id")
	public RateTable table;
	
	public Rate(Currency fromCurrency, Currency toCurrency, BigDecimal rate) {
		this.fromCurrency = fromCurrency;
		this.toCurrency = toCurrency;
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "Rate [fromCurrency=" + fromCurrency + ", toCurrency="
				+ toCurrency + ", rate=" + rate + "]";
	}
}
