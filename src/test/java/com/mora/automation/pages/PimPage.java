package com.mora.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PimPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public PimPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "input[placeholder='Type for hints...']")
    private WebElement employeeNameInput;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    @FindBy(css = ".oxd-table-row--with-border")
    private List<WebElement> resultRows;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[1]/div/div[2]/div/div[2]/input")
    private WebElement employeeIdInput;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[1]/div[2]/form/div[2]/button[1]")
    private WebElement resetButton;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[1]/button")
    private WebElement addButton;

    @FindBy(css = ".oxd-table-row--with-border .bi-pencil-fill")
    private WebElement editButton;

    @FindBy(xpath = "//label[normalize-space()='Employment Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text-input')]")
    private WebElement employmentStatusDropdown;

    @FindBy(xpath = "//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[3]/div/div[2]/div[1]/div/div[5]")
    private WebElement jobTitle;

    @FindBy(name = "firstname")
    private WebElement firstNameInput;

    @FindBy(name = "middlename")
    private WebElement middleNameInput;

    @FindBy(name = "lastname")
    private WebElement lastNameInput;

    @FindBy(xpath = "//label[normalize-space()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input")
    private WebElement employeeIdInputAdd;

    @FindBy(xpath = "//button[normalize-space()='Cancel']")
    private WebElement cancelButton;

    @FindBy(css = "button[type='submit']")
    private WebElement saveButton;


    public void searchByEmployeeName(String name) {
        wait.until(ExpectedConditions.visibilityOf(employeeNameInput));
        employeeNameInput.clear();
        employeeNameInput.sendKeys(name);
        employeeNameInput.sendKeys(Keys.ESCAPE);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", searchButton);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));

        Actions actions = new Actions(driver);
        actions.moveToElement(searchButton).click().perform();


        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span")));
    }

    public String getNoRecordsFoundText() {
        By noRecordsLocator = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span");
        WebElement noRecordsText = wait.until(ExpectedConditions.visibilityOfElementLocated(noRecordsLocator));
        return noRecordsText.getText();

    }

    public String getRecordsFoundText() {
        By recordsFoundLocator = By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div[2]/div/div[2]/div[2]/div/span");
        WebElement recordsFoundText = wait.until(ExpectedConditions.visibilityOfElementLocated(recordsFoundLocator));
        return recordsFoundText.getText();
    }
    public void searchByEmployeeId(String employeeId) {
        wait.until(ExpectedConditions.visibilityOf(employeeIdInput));
        employeeIdInput.clear();
        employeeIdInput.sendKeys(employeeId);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", searchButton);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));

        Actions actions = new Actions(driver);
        actions.moveToElement(searchButton).click().perform();
    }

    public void clickResetButton() {
        wait.until(ExpectedConditions.elementToBeClickable(resetButton));
        resetButton.click();
    }
    public void clickAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        addButton.click();
    }
    public String getEmployeeNameInputValue() {
        return employeeNameInput.getAttribute("value");
    }

    public String getEmployeeIdInputValue() {
        return employeeIdInput.getAttribute("value");
    }

    public void clickEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(editButton));
        editButton.click();
    }
    public void clickSearch(){
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }
    public void selectEmploymentStatus(String option) {
        wait.until(ExpectedConditions.elementToBeClickable(employmentStatusDropdown));
        employmentStatusDropdown.click();

        By optionLocator = By.xpath("//div[@role='listbox']//span[normalize-space()='" + option + "']");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }

    public String getSelectedEmploymentStatus() {
        return employmentStatusDropdown.getText();
    }
    public void selectInclude(String option) {
        By includeDropdownLocator = By.xpath("//label[normalize-space()='Include']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text-input')]");
        WebElement includeDropdown = wait.until(ExpectedConditions.elementToBeClickable(includeDropdownLocator));
        includeDropdown.click();

        By optionLocator = By.xpath("//div[@role='listbox']//span[normalize-space()='" + option + "']");
        wait.until(ExpectedConditions.elementToBeClickable(optionLocator)).click();
    }
    public String getSelectedInclude() {
        By includeDropdownLocator = By.xpath("//label[normalize-space()='Include']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text-input')]");
        WebElement includeDropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(includeDropdownLocator));
        return includeDropdown.getText();
    }
    public AddEmployeePage clickAdd() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton));
        addButton.click();
        return new AddEmployeePage(driver);
    }

    public void clickDeleteFirstResult() {
        By deleteIconLocator = By.cssSelector(".oxd-table-row--with-border .bi-trash");
        wait.until(ExpectedConditions.elementToBeClickable(deleteIconLocator));

        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(deleteIconLocator)).click().perform();
    }

    public void confirmDelete() {
        By yesButtonLocator = By.xpath("//*[@id=\"app\"]/div[3]/div/div/div/div[3]/button[2]");
        WebElement yesButton = wait.until(ExpectedConditions.elementToBeClickable(yesButtonLocator));

        Actions actions = new Actions(driver);
        actions.moveToElement(yesButton).click().perform();
    }

    public void cancelDelete() {
        By cancelButtonLocator = By.xpath("//*[@id=\"app\"]/div[3]/div/div/div/div[3]/button[1]");
        WebElement cancelButton = wait.until(ExpectedConditions.elementToBeClickable(cancelButtonLocator));

        Actions actions = new Actions(driver);
        actions.moveToElement(cancelButton).click().perform();
    }

    public void clickEditSecondResult() {
        By editIconLocator = By.cssSelector(".oxd-table-row--with-border .bi-pencil-fill");
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(editIconLocator));

        WebElement secondEditIcon = driver.findElements(editIconLocator).get(1); // index 1 = تاني عنصر
        Actions actions = new Actions(driver);
        actions.moveToElement(secondEditIcon).click().perform();
    }

}