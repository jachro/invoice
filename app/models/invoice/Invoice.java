package models.invoice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import models.Currency;
import models.company.Account;
import models.company.Company;
import models.company.MyCompany;
import models.rate.Rate;
import play.db.jpa.Model;

@Entity
public class Invoice extends Model {
	public String number;
	@Enumerated(EnumType.STRING)
	public PaymentType paymentType;
	@Temporal(TemporalType.DATE)
	public Date invoiceDate;
	@Enumerated(EnumType.STRING)
	public Currency currency;
	public String invoiceOrder;
	@Temporal(TemporalType.DATE)
	public Date sellingDate;
	public Integer duePeriod;
	@Temporal(TemporalType.DATE)
	public Date dueDate;
	public String transport;
	
	@OneToOne
	@JoinColumn(name = "my_company_id")
	public MyCompany myCompany;
	@OneToOne
	@JoinColumn(name = "company_id")
	public Company company;
	@OneToOne
	@JoinColumn(name = "rate_id")
	public Rate rate;
	@OneToOne
	@JoinColumn(name = "account_id")
	public Account account;
	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
	public List<InvoiceItem> items = new ArrayList<InvoiceItem>();
	
	public Invoice(String number, PaymentType paymentType, Account account,
			Currency currency, String order, Date invoiceDate, 
			Date sellingDate, Integer duePeriod, Date dueDate,
			String transport, MyCompany myCompany, Company company) {
		this.number = number;
		this.paymentType = paymentType;
		this.invoiceDate = invoiceDate;
		this.currency = currency;
		this.invoiceOrder = order;
		this.sellingDate = sellingDate;
		this.duePeriod = duePeriod;
		this.dueDate = dueDate;
		this.transport = transport;
		this.account = account;
		this.myCompany = myCompany;
		this.company = company;
	}

	public Invoice addItem(InvoiceItem item) {
		item.invoice = this;
		this.items.add(item);
		return this.save();
	}
	
	public Invoice setRate(Rate rate) {
		this.rate = rate;
		return this.save();
	}

	@Override
	public String toString() {
		return "Invoice [number=" + number + ", invoiceDate=" + invoiceDate
				+ ", currency=" + currency + ", order=" + invoiceOrder
				+ ", sellingDate=" + sellingDate + ", duePeriod=" + duePeriod
				+ ", dueDate=" + dueDate + ", transport=" + transport
				+ ", myCompany=" + myCompany + ", company=" + company
				+ ", rate=" + rate + ", account=" + account + ", items="
				+ items + "]";
	}

}
