package com.mora.automation.tests;

import com.mora.automation.base.BaseTest;
import com.mora.automation.pages.DashboardPage;
import com.mora.automation.pages.LeavePage;
import com.mora.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LeaveTest extends BaseTest {

    @Test
    public void validDateRangeSearchTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        loginPage.waitForDashboard();

        LeavePage leavePage = new DashboardPage(driver).goToLeave();
        leavePage.search("2026-01-01", "2026-31-12");

        Assert.assertFalse(driver.getPageSource().contains("should be after"),
                "Expected no date range error for a valid date range");
    }

    @Test
    public void fromDateAfterToDateTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("Admin", "admin123");
        loginPage.waitForDashboard();

        LeavePage leavePage = new DashboardPage(driver).goToLeave();
        leavePage.search("2026-31-12", "2025-01-01");

        String error = leavePage.getDateRangeErrorMessage();
        Assert.assertEquals(error, "Should be a valid date in yyyy-dd-mm format",
                "Expected a validation error when From Date is after To Date");
    }
}