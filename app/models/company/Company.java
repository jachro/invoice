package models.company;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import org.apache.commons.lang.StringUtils;

import java.util.*;

@Entity
public class Company extends Model {
	public String name;
	public String nip;

	@OneToOne(mappedBy = "company", cascade = CascadeType.ALL)
	public Address address;
	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
	public List<Account> accounts = new ArrayList<Account>();

	public Company(String name, String nip, Address address) {
		this.name = name;
		this.nip = nip;
		this.address = address;
		this.address.company = this;
	}

	/**
	 * This method adds an account to {@code this} company
	 * @param account	an account to add
	 * @return			a reference to {@code this} company
	 */
	public Company addAccount(Account account) {
		this.accounts.add(account);
		account.company = this;
		return this;
	}

	/**
	 * This method adds {@code this} company reference to all {@code accounts}
	 */
	public void joinAccountsWithCompany() {
		if (this.accounts.isEmpty()) {
			return;
		}

		for (Account account : this.accounts) {
			account.company = this;
		}
	}

	/**
	 * This method adds {@code this} company reference to all {@code accounts}
	 */
	public void joinAddressWithCompany() {
		if (this.address == null) {
			return;
		}

		this.address.company = this;
	}

	@Override
	public String toString() {
		return "Company [id=" + id
				+ ", name=" + name
				+ ", nip=" + nip
				+ ", address=" + address
				+ ", accounts=" + accounts
				+ "]";
	}
}
