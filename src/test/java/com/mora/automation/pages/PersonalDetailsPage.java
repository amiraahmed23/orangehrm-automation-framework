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

public class PersonalDetailsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public PersonalDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "input.orangehrm-lastname")
    private WebElement lastNameInput;

    @FindBy(css = "button[type='submit']")
    private WebElement saveButton;


    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[1]/form/div[1]/div/div/div/div[2]/div[1]/div[2]/input")
    private WebElement firstNameInput;

    public void updateFirstName(String newFirstName) {
        wait.until(ExpectedConditions.visibilityOf(firstNameInput));
        firstNameInput.clear();
        firstNameInput.sendKeys(newFirstName);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", saveButton);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));

        Actions actions = new Actions(driver);
        actions.moveToElement(saveButton).click().perform();
    }

    public String getFirstNameErrorMessage() {
        By errorLocator = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div/div/div[2]/div[1]/form/div[1]/div/div/div/div[2]/div[1]/span");
        try {
            WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator));
            return error.getText();
        } catch (org.openqa.selenium.TimeoutException e) {
            By anyRequired = By.xpath("//span[contains(@class,'oxd-input-group__message') and text()='Required']");
            return wait.until(ExpectedConditions.visibilityOfElementLocated(anyRequired)).getText();
        }
    }

    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton));
        Actions actions = new Actions(driver);
        actions.moveToElement(saveButton).click().perform();
    }

   public void editWithEmptyFirstName() {
       wait.until(ExpectedConditions.visibilityOf(firstNameInput));

       JavascriptExecutor js = (JavascriptExecutor) driver;
       js.executeScript("arguments[0].value = '';", firstNameInput);
       js.executeScript("arguments[0].dispatchEvent(new Event('input', { bubbles: true }));", firstNameInput);

       clickSaveButton();
   }
}