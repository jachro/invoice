package controllers.company;

import java.util.List;

import models.company.Company;
import models.company.MyCompany;
import play.mvc.Controller;

public class MyCompanyController extends Controller {

	public static void myCompany() {
		MyCompany myCompany = findMyCompany();

		render(myCompany);
	}

	public static void saveMyCompany(MyCompany myCompany) {
		Company company = myCompany.company;
		company.joinAddressWithCompany();
		company.joinAccountsWithCompany();
		company.save();

		myCompany.save();

		render("@myCompany", myCompany);
	}

	private static MyCompany findMyCompany() {
		List<MyCompany> myCompanies = MyCompany.findAll();
		return (!myCompanies.isEmpty()) ? myCompanies.get(0) : null;
	}
}
