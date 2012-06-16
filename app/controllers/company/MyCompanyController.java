package controllers.company;

import java.util.List;

import models.Currency;
import models.company.Account;
import models.company.Company;
import models.company.MyCompany;
import play.mvc.Controller;

public class MyCompanyController extends Controller {

	/**
	 * This method fetches <em>My Company</em> data
	 * and renders the page.
	 */
	public static void loadMyCompany() {
		MyCompany myCompany = fetchFromDB();

		render("@myCompany", myCompany);
	}

	private static MyCompany fetchFromDB() {
		List<MyCompany> myCompanies = MyCompany.findAll();
		return (!myCompanies.isEmpty()) ? myCompanies.get(0) : null;
	}

	/**
	 * This method saves <em>My Company</em> data sent from the page
	 * and then renders the my company page.
	 * @param myCompany	mapped data from the page
	 */
	public static void saveMyCompany(MyCompany myCompany) {
		Company company = myCompany.company;
		company.joinAddressWithCompany();
		company.joinAccountsWithCompany();

		if (company.id != null) {
			company.merge();
		}
		company.save();

		if (myCompany.id != null) {
			myCompany.merge();
		}
		myCompany.save();

		render("@myCompany", myCompany);
	}

	/**
	 * This method adds a new account to the <em>My Company</em> object
	 * gathered from the page
	 * and then renders the my company page.
	 * @param myCompany	mapped data from the page
	 */
	// TODO: add validation
	public static void addAccount(MyCompany myCompany, String newAccountNumber, Currency newAccountCurrency) {
		Account newAccount = new Account(newAccountNumber, newAccountCurrency);

		Company company = myCompany.company;
		company.addAccount(newAccount);

		render("@myCompany", myCompany);
	}
}
