package com.mora.automation.tests;
import com.mora.automation.base.BaseTest;
import com.mora.automation.pages.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

public class LoginTest extends BaseTest {
    @Test
    public void validLoginTest(){
        LoginPage loginpage = new LoginPage(driver);
        loginpage.login("Admin", "admin123");
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"), "User was not redirected to dashboard after login");
    }
    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentialsData() {
        return new Object[][] {
                {"wronguser", "wrongpass"},
                {"wronguser", "admin123"},
                {"Admin", "wrongpass"},
                {"   Admin", "admin123"},
                { "' OR '1'='1", "' OR '1'='1" }
        };
    }
    @Test (dataProvider = "invalidCredentials")
    public void invalidLoginTest(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        String actualError = loginPage.getErrorMessage();
        Assert.assertEquals(actualError, "Invalid credentials",
                "Unexpected error message for username='" + username + "'");
    }
    @Test
    public void emptyUsernameAndPasswordTest(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickLoginWithoutFilling();
        List<WebElement> messages = loginPage.getRequiredFieldMessages();
        Assert.assertEquals(messages.size(), 2,
                "Expected 2 'Required' messages for empty username and password");

        for (WebElement message : messages) {
            Assert.assertEquals(message.getText(), "Required");
        }

    }
    @Test
    public void passwordFieldIsMaskedTest(){
        LoginPage loginPage = new LoginPage(driver);
        String type = loginPage.getPasswordFieldType();
        Assert.assertEquals(type, "password",
                "Password field is not masked (type attribute mismatch)");
    }
    @Test
    public void forgotPasswordNavigationTest(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.clickForgotPassword();
        Assert.assertTrue(driver.getCurrentUrl().contains("requestPasswordResetCode"),
                "Forgot password link did not navigate to the reset page");
    }
    @Test
    public void loginButtonStaysVisibleAfterFailedLoginTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("WrongUser", "WrongPass");

        Assert.assertTrue(loginPage.isLoginButtonDisplayed(),
                "Login button disappeared after failed login attempt");
    }
}
