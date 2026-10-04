package com.mora.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class LeavePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LeavePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "input[placeholder='yyyy-dd-mm']")
    private List<WebElement> dateInputs;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    public void search(String fromDate, String toDate) {
        wait.until(ExpectedConditions.visibilityOfAllElements(dateInputs));

        WebElement fromDateInput = dateInputs.get(0);
        WebElement toDateInput = dateInputs.get(1);

        fromDateInput.clear();
        fromDateInput.sendKeys(fromDate);

        toDateInput.clear();
        toDateInput.sendKeys(toDate);

        Actions actions = new Actions(driver);
        actions.moveToElement(searchButton).click().perform();
    }

    public String getDateRangeErrorMessage() {
        By errorLocator = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/span");
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator));
        return error.getText();
    }

    public String getNoRecordsText() {
        By noRecordsLocator = By.xpath("//span[text()='No Records Found']");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(noRecordsLocator)).getText();
    }
}