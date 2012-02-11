package models.company;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Address extends Model {
	public String street;
	public String postCode;
	public String country;
	
	@OneToOne
	@JoinColumn(name = "company_id")
	public Company company;

	public Address(String street, String postCode, String country) {
		this.street = street;
		this.postCode = postCode;
		this.country = country;
	}

	@Override
	public String toString() {
		return "Address [street=" + street + ", postCode=" + postCode
				+ ", country=" + country + "]";
	}
	
}
