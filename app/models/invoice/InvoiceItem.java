package models.invoice;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.db.jpa.Model;

@Entity
public class InvoiceItem extends Model {
	public BigDecimal amount;
	@Enumerated(EnumType.STRING)
	public Tax tax;
	
	@ManyToOne
	@JoinColumn(name="invoice_id")
	public Invoice invoice;
	
	public InvoiceItem(BigDecimal amount, Tax tax) {
		this.amount = amount;
		this.tax = tax;
	}

	@Override
	public String toString() {
		return "InvoiceItem [amount=" + amount + ", tax=" + tax + ", invoice=" + invoice.number + "]";
	}
}
