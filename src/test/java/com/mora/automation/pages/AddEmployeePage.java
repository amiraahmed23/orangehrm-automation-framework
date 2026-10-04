package com.mora.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class AddEmployeePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public AddEmployeePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(name = "firstName")
    private WebElement firstNameInput;

    @FindBy(name = "lastName")
    private WebElement lastNameInput;

    @FindBy(css = "button[type='submit']")
    private WebElement saveButton;

    @FindBy(xpath = "//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement employeeIdInput;

    public void AddEmployee(String firstName, String lastName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameInput));
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);

        employeeIdInput.clear();
        String uniqueId = String.valueOf(System.currentTimeMillis() % 100000);
        employeeIdInput.sendKeys(uniqueId);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", saveButton);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));

        Actions actions = new Actions(driver);
        actions.moveToElement(saveButton).click().perform();

        wait.until(ExpectedConditions.urlContains("viewPersonalDetails"));
    }
    public String getEmployeeUrl() {
        return driver.getCurrentUrl();
    }
}
