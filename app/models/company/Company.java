package models.company;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Company extends Model {
	public String name;
	
	@OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
	public Address address;
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	public List<Account> accounts = new ArrayList<Account>();
	
	public Company(String name, Address address) {
		this.name = name;
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
		return "Company [" +
				"name=" + name
				+ ", address=" + address
				+ ", accounts=" + accounts
				+ "]";
	}
}
