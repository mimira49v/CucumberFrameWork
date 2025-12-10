package steps;

import Pages.*;

public class PageInitializers {

    public static LoginPage login;
    public static EmployeeSearchPage employeeSearchPage;
    public static AddEmployeePage addEmployeePage;
    public static DashboardPage dash;
    public static DeltaPage deltaPage;
    public static DropDownPage dropDownPage;
    public static SyntaxWaitPage syntaxWaitPage;
    public static IframePage iframePage;
    
    public static void intializePageObjects(){
        login = new LoginPage(); 
        employeeSearchPage = new EmployeeSearchPage();
        addEmployeePage = new AddEmployeePage();
        dash = new DashboardPage();
        deltaPage = new DeltaPage();
        dropDownPage = new DropDownPage();
        syntaxWaitPage = new SyntaxWaitPage();
        iframePage = new IframePage();
    }
}