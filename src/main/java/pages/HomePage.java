package pages;

import base.BaseLibrary;
import base.BaseTest;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class HomePage extends BaseLibrary {

    private static final String SIGN_IN_LINK_SELECTOR = "a.nav-link#signin-link";

    public HomePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Home sayfası yüklenir.")
    public void goToLoginPage(){
        try{
            screenshot();
            WebElement signInLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(SIGN_IN_LINK_SELECTOR)));
            signInLink.click();
        } catch (Exception e) {
            Assert.fail("Home sayfası yüklenemedi" + e.getMessage());
        }
    }
}
