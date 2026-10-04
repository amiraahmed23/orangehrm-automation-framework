package com.mora.automation.tests;
import com.mora.automation.base.BaseTest;
import com.mora.automation.pages.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class PimTest extends BaseTest {
    @Test
    public void searchExistingEmployeeTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.searchByEmployeeName("ALi Taha Ali");

        String recordsFoundText = pimPage.getRecordsFoundText();
        Assert.assertTrue(recordsFoundText.contains("Record Found") || recordsFoundText.contains("Records Found"), "Expected 'Record Found' or 'Records Found' message for existing employee search");
    }
    @Test
    public void editWithEmptyFieldsTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        loginPage.waitForDashboard();

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.clickEditSecondResult();

        PersonalDetailsPage personalDetailsPage = new PersonalDetailsPage(driver);
        personalDetailsPage.editWithEmptyFirstName();

        Assert.assertEquals( personalDetailsPage.getFirstNameErrorMessage(), "Required", "Expected 'Required' error message when trying to save with empty first name");
    }
    @Test
    public void searchNonExistingEmployeeTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.searchByEmployeeName("ZZZ_NonExistentName_123");

        String noRecordsText = pimPage.getNoRecordsFoundText();
        Assert.assertEquals(noRecordsText, "No Records Found", "Expected 'No Records Found' message for non-existing employee search");
    }
    @Test
    public void searchByExistingEmployeeIdTest(){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.searchByEmployeeId("0295");

        String recordsFoundText = pimPage.getRecordsFoundText();
        Assert.assertTrue(recordsFoundText.contains("Record Found") || recordsFoundText.contains("Records Found"), "Expected 'Record Found' or 'Records Found' message for existing employee ID search");
    }
    @Test
    public void searchByNonExistingEmployeeIdTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.searchByEmployeeId("9999");

        Assert.assertEquals(pimPage.getNoRecordsFoundText(), "No Records Found", "Expected 'No Records Found' message for non-existing employee ID search");
    }
    @Test
    public void resetSearchTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.searchByEmployeeName("Automation");
        pimPage.clickResetButton();

        String employeeNameInputValue = pimPage.getEmployeeNameInputValue();
        String employeeIdInputValue = pimPage.getEmployeeIdInputValue();

        Assert.assertEquals(employeeNameInputValue, "", "Expected employee name input to be empty after reset");
        Assert.assertEquals(employeeIdInputValue, "", "Expected employee ID input to be empty after reset");
    }
    @Test
    public void addEmployeeTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.clickAddButton();

        Assert.assertEquals(driver.getCurrentUrl(), "https://opensource-demo.orangehrmlive.com/web/index.php/pim/addEmployee", "Expected to navigate to Add Employee page after clicking Add button");
    }
    @Test
    public void editEmployeeTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        PimPage pimPage = new DashboardPage(driver).goToPim();

        pimPage.clickEditButton();

        Assert.assertTrue(driver.getCurrentUrl().contains("viewPersonalDetails"), "Expected to navigate to Edit Employee page after clicking Edit button");
    }
    @Test
    public void searchWithEmptyFieldsTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.clickSearch();
        String recordsFoundText = pimPage.getRecordsFoundText();
        Assert.assertTrue(recordsFoundText.contains("Records Found") || recordsFoundText.contains("Records Found"), "Expected 'Record Found' or 'Records Found' message when searching with empty fields");
    }
    @Test
    public void searchByPartialNameTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.searchByEmployeeName("A");

        String recordsFoundText = pimPage.getRecordsFoundText();
        Assert.assertFalse(recordsFoundText.contains("No Records"), "Expected results for partial name 'A'");    }
    @Test
    public void filterByEmploymentStatusTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.selectEmploymentStatus("Full-Time Permanent");

        Assert.assertEquals(pimPage.getSelectedEmploymentStatus(), "Full-Time Permanent", "Expected selected employment status to be 'Full-Time Permanent'");

        pimPage.clickSearch();
        String recordsFoundText = pimPage.getRecordsFoundText();
        Assert.assertTrue(recordsFoundText.contains("Records Found") || recordsFoundText.contains("Records Found"), "Expected 'Record Found' or 'Records Found' message after filtering by employment status");
    }
    @Test
    public void filterByIncludeTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.selectInclude("Current and Past Employees");

        Assert.assertEquals(pimPage.getSelectedInclude(), "Current and Past Employees", "Expected selected include option to be 'Current and Past Employees'");


        pimPage.clickSearch();
        String recordsFoundText = pimPage.getRecordsFoundText();
        Assert.assertTrue(recordsFoundText.contains("Records Found") || recordsFoundText.contains("Records Found"), "Expected 'Record Found' or 'Records Found' message after filtering by include option");
    }
   @Test
    public void addEmployeeTest2(){
       LoginPage loginPage = new LoginPage(driver);
       loginPage.login("Admin", "admin123");
       loginPage.waitForDashboard();

       PimPage pimPage = new DashboardPage(driver).goToPim();
       AddEmployeePage addEmployeePage = pimPage.clickAdd();
       String uniqueLastName = "Test" + System.currentTimeMillis();
       addEmployeePage.AddEmployee("Mora", uniqueLastName);
       Assert.assertTrue(driver.getCurrentUrl().contains("viewPersonalDetails"), "Expected to navigate to the new employee's details page after saving");

   }
    @Test
    public void editSecondEmployeeFirstNameTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        loginPage.waitForDashboard();

        PimPage pimPage = new DashboardPage(driver).goToPim();
        pimPage.clickEditSecondResult();

        PersonalDetailsPage personalDetailsPage = new PersonalDetailsPage(driver);
        personalDetailsPage.updateFirstName("MoraEdited");
    }
    @Test
    public void deleteEmployeeTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        loginPage.waitForDashboard();

        PimPage pimPage = new DashboardPage(driver).goToPim();

        pimPage.clickDeleteFirstResult();
        pimPage.confirmDelete();
    }
    @Test
    public void cancelDeleteEmployeeTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        loginPage.waitForDashboard();

        PimPage pimPage = new DashboardPage(driver).goToPim();

        pimPage.clickDeleteFirstResult();
        pimPage.cancelDelete();

        String recordsFoundText = pimPage.getRecordsFoundText();
        Assert.assertTrue(recordsFoundText.contains("Records Found") || recordsFoundText.contains("Records Found"), "Expected 'Record Found' or 'Records Found' message after canceling delete");
    }
}
