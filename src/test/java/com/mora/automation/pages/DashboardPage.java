package com.mora.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class DashboardPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[text()='PIM']")
    private WebElement pimMenuItem;

    public PimPage goToPim() {
        wait.until(ExpectedConditions.elementToBeClickable(pimMenuItem));
        pimMenuItem.click();
        return new PimPage(driver);
    }
    @FindBy(xpath = "//span[text()='Leave']")
    private WebElement leaveMenuItem;

    public LeavePage goToLeave() {
        wait.until(ExpectedConditions.elementToBeClickable(leaveMenuItem)).click();
        return new LeavePage(driver);
    }
}