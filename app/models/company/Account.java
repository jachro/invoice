package models.company;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;

@Entity
public class Account extends Model {
	public String number;

	@ManyToOne
	@JoinColumn(name="company_id")
	public Company company;

	public Account() {
	}
	public Account(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Account [number=" + number + "]";
	}
}
