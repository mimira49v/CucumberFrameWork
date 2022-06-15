package steps;

import Pages.AddEmployeePage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.CommonMethods;
import utils.Constants;
import utils.ExcelReader;
import utils.GlobalVariables;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import static utils.GlobalVariables.tableData;

public class AddEmployeeSteps extends CommonMethods {

    @When("user clicks on PIM option")
    public void user_clicks_on_pim_option() {
        //older version of writing the code
        // WebElement pimOPtion = driver.findElement(By.xpath("abc"));
        // pimOPtion.click();
        click(employeeSearchPage.pimOption);
    }
    @When("user clicks on add employee option")
    public void user_clicks_on_add_employee_option() {
        click(employeeSearchPage.addEmployeeOption);
    }

    @When("user enters firstname middlename and lastname")
    public void user_enters_firstname_middlename_and_lastname() {
        sendText(addEmployeePage.firstNameField, "dasir");
        sendText(addEmployeePage.middleNameField, "hamilia");
        sendText(addEmployeePage.lastNameField, "tolom");
    }
    @When("user clicks on save button")
    public void user_clicks_on_save_button() {
        click(addEmployeePage.saveButton);
    }
    @Then("employee added successfully")
    public void employee_added_successfully() {
        System.out.println("Employee added");
    }

    @When("user enters {string} {string} and {string}")
    public void user_enters_and(String firstNameValue, String middleNameValue, String lastNameValue) {
        sendText(addEmployeePage.firstNameField, firstNameValue);
        sendText(addEmployeePage.middleNameField, middleNameValue);
        sendText(addEmployeePage.lastNameField, lastNameValue);
    }

    @When("user provides {string} {string} and {string}")
    public void user_provides_and(String firstName, String middleName, String lastName) {
        sendText(addEmployeePage.firstNameField, firstName);
        sendText(addEmployeePage.middleNameField, middleName);
        sendText(addEmployeePage.lastNameField, lastName);
    }

    @When("user provides multiple employee data and verify they are added")
    public void user_provides_multiple_employee_data_and_verify_they_are_added(DataTable dataTable) throws InterruptedException {
        List<Map<String, String>> employeeNames =  dataTable.asMaps();
        for (Map<String, String> employee :employeeNames) {
            String firstNameValue = employee.get("firstName");
            String middleNameValue = employee.get("middleName");
            String lastNameValue = employee.get("lastName");

            sendText(addEmployeePage.firstNameField, firstNameValue);
            sendText(addEmployeePage.middleNameField, middleNameValue);
            sendText(addEmployeePage.lastNameField, lastNameValue);
            click(addEmployeePage.saveButton);

//          verification of adding an employee is HW
            String fullEmployeeName = employee.get("firstName")+" "+employee.get("middleName")+" "+ employee.get("lastName");
            if(addEmployeePage.emplVerify.getText().equals(fullEmployeeName)){
                System.out.println(fullEmployeeName+" "+" is added successfully!");
            } else {
                System.out.println("Was not added : "+fullEmployeeName);
            }
                    click(employeeSearchPage.addEmployeeOption);}
                        System.out.println("Employees have been succeffully!");
    }

    @When("user adds multiple employees from excel file using {string} sheet and verify the user added")
    public void user_adds_multiple_employees_from_excel_file_using_sheet_and_verify_the_user_added(String sheetName) throws InterruptedException {
        List<Map<String, String>> newEmployees = ExcelReader.excellIntoMap(Constants.TESTDATA_FILEPATH, sheetName);
        Iterator<Map<String, String>> itr = newEmployees.iterator();
//      It checks weather the next element exists or not
        while(itr.hasNext()){

//          returns the key and value for employees
            Map<String, String> mapNewEmp = itr.next();
            System.out.println(mapNewEmp.get("FirstName"));
            System.out.println(mapNewEmp.get("MiddleName"));
            System.out.println(mapNewEmp.get("LastName"));

//          filling all the fields from the data coming from excel file
            sendText(addEmployeePage.firstNameField, mapNewEmp.get("FirstName"));
            sendText(addEmployeePage.middleNameField, mapNewEmp.get("MiddleName"));
            sendText(addEmployeePage.lastNameField, mapNewEmp.get("LastName"));

//         it will fetch the employee id from attribute
            String empIDValue= addEmployeePage.empIDLocator.getAttribute("value");

//          to upload the photograph
            sendText(addEmployeePage.photograph, mapNewEmp.get("Photograph"));
                if(!addEmployeePage.checkBox.isSelected()){
                    click(addEmployeePage.checkBox);
            }

            sendText(addEmployeePage.createUsername, mapNewEmp.get("Username"));
            sendText(addEmployeePage.createPassword, mapNewEmp.get("Password"));
            sendText(addEmployeePage.confirmPassword, mapNewEmp.get("Password"));
            click(addEmployeePage.saveButton);

            Thread.sleep(5000);

            // to verify the emplyee, we will navigate to employee list option
            click(employeeSearchPage.empListOption);
            sendText(employeeSearchPage.idField, empIDValue);
            click(employeeSearchPage.searchButton);

//          it is returning the data from the row in results
            List<WebElement> rowData = driver.findElements(By.xpath("//table[@id='resultTable']/tbody/tr"));
            for (int i = 0; i<rowData.size(); i++){
                String rowText = rowData.get(i).getText();
                    System.out.println(rowText);
                    String expectedData = empIDValue +" "+ mapNewEmp.get("FirstName")+" "+
                            mapNewEmp.get("MiddleName")+" "+mapNewEmp.get("LastName");
                                Assert.assertTrue(dash.welcomeMessage.isDisplayed());
            }
            click(employeeSearchPage.addEmployeeOption);
            Thread.sleep(2000);
        }
    }

    @When("capture the employee id")
    public void capture_the_employee_id() {
        AddEmployeePage ad = new AddEmployeePage();
        GlobalVariables.emp_Id = ad.empIDLocator.getAttribute("value");
        GlobalVariables.firstName = ad.firstNameField.getAttribute("value");
        System.out.println("Emp_id :"+GlobalVariables.emp_Id);
        System.out.println("firstName :"+GlobalVariables.firstName);
    }

    @Then("verify the results from UI and Backend")
    public void verify_the_results_from_ui_and_backend() {
        String firtNameFromDb = tableData.get(0).get("emp_firstname");
        String dbEmpId = tableData.get(0).get("employee_id");
//      System.out.println("First Name from Database "+firtNameFromDb);
//      System.out.println("First Name from GUI "+GlobalVariables.firstName);

        Assert.assertEquals(firtNameFromDb, GlobalVariables.firstName);
        Assert.assertEquals(dbEmpId, GlobalVariables.emp_Id);
    }
}