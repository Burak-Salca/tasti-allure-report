package pages;

import base.BaseLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class CamQToolPage extends BaseLibrary {

    private static final By CAMQTOOL_HEADER = By.xpath("//button[.//div[normalize-space()='CamQTool']]");
    private static final By CHECK_QUALITY_BUTTON = By.xpath("//button[contains(@class,'qcheck-quality-btn')]");
    private static final By ADD_TO_CLASS_BUTTON = By.xpath("//button[normalize-space()='Add to Class']\n");
    private static final By SUCCESS_MESSAGE = By.xpath("//div[contains(@class,'ajs-message') and contains(@class,'ajs-success')]");

    public CamQToolPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    @Step("CamQTool sayfasının açıldığı kontrol edilir.")
    public CamQToolPage checkPage() throws InterruptedException{
        wait.until(ExpectedConditions.visibilityOfElementLocated(CAMQTOOL_HEADER));
        Thread.sleep(1000);
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(CAMQTOOL_HEADER));
        screenshot();
        String camQToolHeader = header.getText().trim();
        Assert.assertEquals(camQToolHeader,"CamQTool","Sayfa yüklenmesinde bir sorun oluştu.");
        return this;
    }

    @Step("Check Quality butonuna basılır.")
    public CamQToolPage clickCheckQualityButton() throws InterruptedException {
        click(CHECK_QUALITY_BUTTON);
        Thread.sleep(3000);
        screenshot();
        return this;
    }

    @Step("Kaliteli/kalitesiz resimlerin ayrışması tamamlanır.")
    public CamQToolPage checkQualityImages() throws InterruptedException{
        wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CLASS_BUTTON));
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(SUCCESS_MESSAGE));
        String messageText = successMessage.getText().trim();
        Assert.assertEquals(messageText, "Process completed successfully.", "Kalite işlemi başarıyla tamamlanmadı.");
        screenshot();
        return this;
    }


}
