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

public class LoginPage extends BaseLibrary {

    private static final String EMAIL_ID = "email";
    private static final String PASSWORD_ID = "password";
    private static final String SIGNIN_BUTTON_ID = "signin-button";

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Sign up sayfası yüklenir. Email alanı doldurulur.")
    public LoginPage fillEmail(String email){
        try{
            WebElement emailSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(EMAIL_ID)));
            emailSpan.sendKeys(email);
            screenshot();
        } catch (Exception e) {
            Assert.fail("Giriş sayfası yüklenemedi " + e.getMessage());
        }
        return this;
    }

    @Step("Şifre alanı doldurulur.")
    public LoginPage fillPassword(String password){
        try{
            WebElement passwordSpan = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(PASSWORD_ID)));
            passwordSpan.sendKeys(password);
            screenshot();
        } catch (Exception e) {
            Assert.fail("Şifre girilemedi" + e.getMessage());
        }
        return this;
    }

    @Step("Sign In butonuna basılır.")
    public LoginPage clickLogin(){
        try{
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(SIGNIN_BUTTON_ID)));
            loginButton.click();
            screenshot();
        } catch (Exception e) {
            Assert.fail("Login butonuna tıklanılamadı" + e.getMessage());
        }
        return this;
    }

}
