package models.company;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Company extends Model {
	public String name;
	public String nip;

	@OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
	public Address address;
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	public List<Account> accounts = new ArrayList<Account>();

	public Company() {
		this.address = new Address();
		this.address.company = this;
	}
	public Company(String name, String nip, Address address) {
		this.name = name;
		this.nip = nip;
		this.address = address;
		this.address.company = this;
	}

	public Company addAccount(Account account) {
		this.accounts.add(account);
		account.company = this;
		this.save();
		return this;
	}

	@Override
	public String toString() {
		return "Company ["
				+ "name=" + name
				+ ", nip=" + nip
				+ ", address=" + address
				+ ", accounts=" + accounts
				+ "]";
	}
}
