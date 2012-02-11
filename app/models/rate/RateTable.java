package models.rate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class RateTable extends Model {
	public String name;
	@Temporal(TemporalType.DATE)
	public Date date;
	
	@OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
	List<Rate> rates = new ArrayList<Rate>();
	
	public RateTable(String name, Date date) {
		this.name = name;
		this.date = date;
	}
	
	public RateTable addRate(Rate rate) {
		this.rates.add(rate);
		rate.table = this;
		this.save();
		return this;
	}

	@Override
	public String toString() {
		return "RateTable [" +
				"name=" + name
				+ ", date=" + date
				+ ", rates=" + rates
				+ "]";
	}
}
