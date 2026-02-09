package pages;

import base.BaseLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class CamFiToolPage extends BaseLibrary {

    private static final By COLLECTION_DROPDOWN = By.xpath("//label[text()='Collection Selection']/following-sibling::select");
    private static final By FROM_CLASS_DROPDOWN = By.xpath("//label[normalize-space()='From Class']/following-sibling::select");
    private static final By TO_CLASS_DROPDOWN = By.xpath("//label[normalize-space()='To Class']/following-sibling::select");
    private static final By FAULT_TYPE_DROPDOWN = By.id("faultTypeSelect");
    private static final By APPLY_FAULT_BUTTON = By.id("applyButtonFITool");
    private static final By UPLOAD_MODAL_BUTTON = By.xpath("//div[@title='View Uploaded Images']");
    private static final By SELECT_ALL_UPLOAD_BUTTON = By.cssSelector("button.select-all-btn");
    private static final By APPLY_BUTTON = By.cssSelector("button.modal-btn.primary");
    private static final By FAULTY_MODAL_BUTTON = By.cssSelector("div[title='View all generated images']");
    private static final By SELECT_ALL_FAULTY_BUTTON = By.xpath("//button[contains(@class,'select-all-btn') and normalize-space()='Select All']");
    private static final By ADD_TO_CLASS_BUTTON = By.xpath("//button[contains(@class,'download-button2') and normalize-space()='Add To Class']");
    private static final By FAULTY_MODAL =By.cssSelector("div.preview-image-list-modal");

    public CamFiToolPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    @Step("Parametreler girilir.")
    public CamFiToolPage fillParameters(String collection, String fromClass, String toClass, String faultType) {
        try {
            Select selectCollection = createSelect(COLLECTION_DROPDOWN);
            selectCollection.selectByValue(collection);
            Select selectFromClass = createSelect(FROM_CLASS_DROPDOWN);
            selectFromClass.selectByValue(fromClass);
            Select selectToClass = createSelect(TO_CLASS_DROPDOWN);
            selectToClass.selectByValue(toClass);
            Select selectFaultType = createSelect(FAULT_TYPE_DROPDOWN);
            selectFaultType.selectByValue(faultType);
            screenshot();
        } catch (Exception e) {
            Assert.fail("Parametreler girilemedi: " + e.getMessage());
        }
        return this;
    }

    @Step("Upload penceresinin açılması ve fault uygulanacak görsellerin seçilmesi.")
    public CamFiToolPage chooseUploadImages(){
        try{
            Thread.sleep(3000);
            // Daha uzun wait süresi ile element'i bekle (presenceOfElementLocated daha esnek)
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
            WebElement uploadModal = longWait.until(ExpectedConditions.presenceOfElementLocated(UPLOAD_MODAL_BUTTON));
            // Scroll yap
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", uploadModal);
            Thread.sleep(500);
            // Element'i tekrar bul ve click et
            uploadModal = wait.until(ExpectedConditions.elementToBeClickable(UPLOAD_MODAL_BUTTON));
            uploadModal.click();
            WebElement selectAllUploadButton = wait.until(ExpectedConditions.elementToBeClickable(SELECT_ALL_UPLOAD_BUTTON));
            selectAllUploadButton.click();
            screenshot();
            WebElement applyButton = wait.until(ExpectedConditions.elementToBeClickable(APPLY_BUTTON));
            applyButton.click();
        } catch (Exception e) {
            Assert.fail("Görseller seçilemedi: " + e.getMessage());
        }
        return this;
    }

    @Step("Inject Fault butonuna basılır.")
    public CamFiToolPage clickFaultInjectButton(){
        try {
            WebElement faultInjectButton = wait.until(ExpectedConditions.elementToBeClickable(APPLY_FAULT_BUTTON));
            faultInjectButton.click();
            screenshot();
        }
        catch (Exception e){
            Assert.fail("Butona basılamadı: " + e.getMessage());
        }
        return this;
    }

    @Step("Hatalı görsellerinin oluşması.")
    public CamFiToolPage chooseFaultyImages(){
        try {
            WebElement faultyModalButton = wait.until(ExpectedConditions.presenceOfElementLocated(FAULTY_MODAL_BUTTON));
            // Önce elementin olduğu yere scroll et - böylece görünür alana gelir
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', behavior: 'smooth'});", faultyModalButton);
            Thread.sleep(1000);
            // Overlay/disable durumlarında normal click bazen başarısız olur - JS click kullan
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", faultyModalButton);
            WebElement faultyModal = wait.until(ExpectedConditions.visibilityOfElementLocated(FAULTY_MODAL));
            screenshot();
            boolean isModalDisplayed = faultyModal.isDisplayed();
            Assert.assertTrue(isModalDisplayed, "Hatalı görseller oluşmamış.");
        } catch (Exception e) {
            Assert.fail("Hatalı görseller seçilemedi: " + e.getMessage());
        }
        return this;
    }

    @Step("Hatalı görsellerin kaydedilmesi.")
    public CamFiToolPage saveFaultImages(){
        try {
            WebElement addToClassButton = wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CLASS_BUTTON));
            addToClassButton.click();
            screenshot();
        } catch (Exception e) {
            Assert.fail("Hatalı görseller kaydedilemedi: " + e.getMessage());
        }
        return this;
    }
}
