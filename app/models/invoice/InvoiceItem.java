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
	public Integer number;
	public String serviceName;
	public String symbol;
	public Integer quantity;
	public BigDecimal net;
	public BigDecimal netTotal;
	@Enumerated(EnumType.STRING)
	public Tax tax;
	public BigDecimal taxAmount;
	public BigDecimal gross;

	@ManyToOne
	@JoinColumn(name="invoice_id")
	public Invoice invoice;

	public InvoiceItem(Integer number, String serviceName, String symbol,
			Integer quantity, BigDecimal net, BigDecimal netTotal, Tax tax,
			BigDecimal taxAmount, BigDecimal gross) {
		this.number = number;
		this.serviceName = serviceName;
		this.symbol = symbol;
		this.quantity = quantity;
		this.net = net;
		this.netTotal = netTotal;
		this.tax = tax;
		this.taxAmount = taxAmount;
		this.gross = gross;
	}

	@Override
	public String toString() {
		return "InvoiceItem [id=" + id + ", number=" + number + ", serviceName=" + serviceName
				+ ", symbol=" + symbol + ", quantity=" + quantity + ", net="
				+ net + ", netTotal=" + netTotal + ", tax=" + tax
				+ ", taxAmount=" + taxAmount + ", gross=" + gross
				+ ", invoice=" + invoice.number + "]";
	}
}
