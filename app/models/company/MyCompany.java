package models.company;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import play.db.jpa.Model;

@Entity
public class MyCompany extends Model {
	@OneToOne
	@JoinColumn(name = "company_id")
	public Company company;

	public MyCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "MyCompany [company=" + company + "]";
	}
}
