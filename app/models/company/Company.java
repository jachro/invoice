package models.company;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Company extends Model {
	public String name;
	
	@OneToOne(mappedBy="company", cascade = CascadeType.ALL)
	public Address address;
	
	public Company(String name, Address address) {
		this.name = name;
		this.address = address;
		this.address.company = this;
	}
	
	@Override
	public String toString() {
		return "Company [name=" + name + ", address=" + address + "]";
	}
}
