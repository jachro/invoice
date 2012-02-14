package models.invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import models.company.Company;
import models.company.MyCompany;
import play.db.jpa.Model;

@Entity
public class Invoice extends Model {
	public String number;
	@Temporal(TemporalType.DATE)
	public Date date;
	
	@OneToOne
	@JoinColumn(name = "my_company_id")
	public MyCompany myCompany;
	@OneToOne
	@JoinColumn(name = "company_id")
	public Company company;
	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
	public List<InvoiceItem> items = new ArrayList<InvoiceItem>();
	
	public Invoice(String number, Date date, MyCompany myCompany, Company company) {
		this.number = number;
		this.date = date;
		this.myCompany = myCompany;
		this.company = company;
	}
	
	public Invoice addItem(InvoiceItem item) {
		item.invoice = this;
		this.items.add(item);
		return this.save();
	}

	@Override
	public String toString() {
		return "Invoice [" +
				"number=" + number
				+ ", date=" + date
				+ ", myCompany=" + myCompany
				+ ", company=" + company
				+ "]";
	}
}
