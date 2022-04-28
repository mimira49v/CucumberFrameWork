package steps;

import Pages.EmployeeSearchPage;
import Pages.LoginPage;

public class PageInitializers {

    public static LoginPage login;
    public static EmployeeSearchPage employeeSearchPage;

    public static void initializePageObjects(){
        login = new LoginPage(); // this part will be called in CommonMethods!
        employeeSearchPage = new EmployeeSearchPage();
    }
}
