package steps;

import Pages.*;

public class PageInitializers {

    public static LoginPage login;
    public static EmployeeSearchPage employeeSearchPage;
    public static AddEmployeePage addEmployeePage;
    public static DashboardPage dash;
    public static DeltaPage deltaPage;

    public static void intializePageObjects(){
        login = new LoginPage(); // DEFAULT CONSTRUCTOR USING WHICH WE CREATE ANY OBJECT
        employeeSearchPage = new EmployeeSearchPage();
        addEmployeePage = new AddEmployeePage();
        dash = new DashboardPage();
        deltaPage = new DeltaPage();
    }
}