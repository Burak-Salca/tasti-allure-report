package pages;

import base.BaseLibrary;
import base.BaseTest;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MainPage extends BaseLibrary {

    private static final String PROFILE_ICON_SELECTOR = "img.profile-icon";
    private static final String USER_EMAIL_SELECTOR = "div.user-info-item:nth-child(2) > span:nth-child(2)";
    private static final String CAMFITOOL_BUTTON_SELECTOR = "#CamFITool-button";
    private static final By CAMGENTOOL_BUTTON = By.id("CamGenTool-button");
    private static final By CAMTRAINTOOL_BUTTON = By.id("CamTrainTool-button");

    public MainPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Ana sayfa yüklenir. Profil ikonuna tıklanır.")
    public String clickProfile(){
        try{
            WebElement profileIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(PROFILE_ICON_SELECTOR)));
            profileIcon.click();
            WebElement currentUserEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(USER_EMAIL_SELECTOR)));
            String currentEmail = currentUserEmail.getText().trim();
            return currentEmail;
        } catch (Exception e) {
            Assert.fail("Main page yüklenemedi " + e.getMessage());
            return null;
        }
    }

    @Step("Kullanıcı bilgileri kontrol edilir.")
    public MainPage checkUserInfo(String loginEmail, String currentEmail) {
        try {
            screenshot();
            Assert.assertEquals(currentEmail, loginEmail, "Email bilgisi eşleşmiyor!");
        } catch (Exception e) {
            Assert.fail("Kullanıcı bilgisi yanlış/bulunamadı: " + e.getMessage());
        }
        return this;
    }

    @Step("CamFiTool sayfasına yönlendirilir.")
    public MainPage clickCamFiTool(){
        try{
            WebElement camFiToolButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(CAMFITOOL_BUTTON_SELECTOR)));
            camFiToolButton.click();
        } catch (Exception e) {
            Assert.fail("CamFiTool butonuna tıklanılamadı: "+ e.getMessage());
        }
        return this;
    }

    @Step("CamGenTool sayfasına yönlendirilir.")
    public MainPage clickCamGenTool(){
        try{
            // StaleElementReferenceException'dan kaçınmak için element'i her seferinde yeniden bul
            WebElement camGenToolButton = wait.until(ExpectedConditions.elementToBeClickable(CAMGENTOOL_BUTTON));
            camGenToolButton.click();
        } catch (Exception e) {
            // Eğer normal click başarısız olursa JavaScript click dene
            try {
                WebElement camGenToolButton = wait.until(ExpectedConditions.presenceOfElementLocated(CAMGENTOOL_BUTTON));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", camGenToolButton);
            } catch (Exception e2) {
                Assert.fail("CamGenTool butonuna tıklanılamadı: "+ e.getMessage());
            }
        }
        return this;
    }

    @Step("CamTrainTool sayfasına yönlendirilir.")
    public MainPage clickCamTrainTool(){
        try{
            click(CAMTRAINTOOL_BUTTON);
        } catch (Exception e) {
            Assert.fail("CamTrainTool butonuna tıklanılamadı: "+ e.getMessage());
        }
        return this;
    }
}
