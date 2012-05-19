package models.company;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import models.Currency;

import play.db.jpa.Model;

@Entity
public class Account extends Model {
	public String number;
	@Enumerated(EnumType.STRING)
	public Currency currency;

	@ManyToOne
	@JoinColumn(name="company_id")
	public Company company;

	public Account(String number, Currency currency) {
		this.number = number;
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", number=" + number + ", currency=" + currency + "]";
	}
}
