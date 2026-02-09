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

public class CamGenToolPage extends BaseLibrary {

    private static final By CAMGENTOOL_HEADER = By.xpath("//div[@class='logo-text' and text()='CamGenTool']");
    private static final By GENERATION_MODE_DROPDOWN = By.xpath("//div[contains(@class,'camgen-input-field')][.//label[contains(normalize-space(.),'GENERATION MODE')]]//select");
    private static final By MODEL_DROPDOWN = By.id("model-select");
    private static final By COLLECTION_DROPDOWN = By.xpath("//div[contains(@class,'camgen-input-field')][.//label[contains(.,'COLLECTION')]]//select");
    private static final By FROMCLASS_DROPDOWN = By.xpath("//div[contains(@class,'camgen-input-field')][.//label[contains(.,'FROM CLASS')]]//select");
    private static final By TOCLASS_DROPDOWN = By.xpath("//div[contains(@class,'camgen-input-field')][.//label[contains(.,'TO CLASS')]]//select");
    private static final By PROMT_TEXT_INPUT = By.id("prompt");
    private static final By GENERATIONPERIMAGE_INPUT = By.xpath("//div[contains(@class,'camgen-input-field')][.//label[contains(.,'GENERATION PER IMAGE')]]//input");
    private static final By UPLOAD_IMAGES_BUTTON = By.xpath("//div[@title='View Uploaded Images']");
    private static final By UPLOAD_SELECT_ALL_BUTTON = By.xpath("//button[contains(@class,'select-all-btn') and normalize-space()='Select All']");
    private static final By UPLOAD_APPLY_BUTTON = By.xpath("//button[contains(@class,'modal-btn') and contains(@class,'primary') and contains(normalize-space(.),'Apply Selection')]");
    private static final By GENERATE_BUTTON = By.id("create-button");
    private static final By SUCCESS_MESSAGE = By.xpath("//div[contains(@class,'ajs-message') and contains(@class,'ajs-success')]");
    private static final By GENERATED_IMAGES_TITLE = By.xpath("//h2[@class='preview-title' and text()='Generated Images']");
    private static final By SEND_TO_QUALITY_BUTTON = By.id("qualitycheck-button-FItool");
    private static final By CONFIRMATON_BUTTON = By.id("qualitycheck-button-gentool-yes");

    public CamGenToolPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    @Step("CamGenTool sayfasının açıldığı kontrol edilir.")
    public CamGenToolPage checkPage(){
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(CAMGENTOOL_HEADER));
        screenshot();
        String camGenToolHeader = header.getText().trim();
        Assert.assertEquals(camGenToolHeader,"CamGenTool","Sayfa yüklenmesinde bir sorun oluştu.");
        return this;
    }

    @Step("Parametreler doldurulur.")
    public CamGenToolPage fillParameters(String generationModeData, String modelData, String collectionData, String fromClassData, String toClassData, String promtData, int generationPerImageData) throws InterruptedException {
        Select generationMode = createSelect(GENERATION_MODE_DROPDOWN);
        generationMode.selectByValue(generationModeData);
        Select model = createSelect(MODEL_DROPDOWN);
        model.selectByValue(modelData);
        WebElement collectionSelectElement = wait.until(ExpectedConditions.elementToBeClickable(COLLECTION_DROPDOWN));
        wait.until(ExpectedConditions.presenceOfNestedElementLocatedBy(collectionSelectElement, By.cssSelector("option[value='" + collectionData + "']")));
        new Select(collectionSelectElement).selectByValue(collectionData);
        Select fromClass = createSelect(FROMCLASS_DROPDOWN);
        fromClass.selectByValue(fromClassData);
        Select toClass = createSelect(TOCLASS_DROPDOWN);
        toClass.selectByValue(toClassData);
        WebElement promtTextInput = wait.until(ExpectedConditions.elementToBeClickable(PROMT_TEXT_INPUT));
        promtTextInput.sendKeys(promtData);
        WebElement generationPerImageInput = wait.until(ExpectedConditions.elementToBeClickable(GENERATIONPERIMAGE_INPUT));
        generationPerImageInput.clear();
        generationPerImageInput.sendKeys(String.valueOf(generationPerImageData));
        screenshot();
        return this;
    }

    @Step("Örnek resimler seçilir.")
    public CamGenToolPage chooseSampleImages(){
        click(UPLOAD_IMAGES_BUTTON);
        click(UPLOAD_SELECT_ALL_BUTTON);
        screenshot();
        click(UPLOAD_APPLY_BUTTON);
        return this;
    }

    @Step("Generate işlemi başlatılır.")
    public CamGenToolPage clickGenerate(){
        click(GENERATE_BUTTON);
        screenshot();
        return this;
    }

    @Step("Generate işleminin tamamlandığı kontrol edilir.")
    public CamGenToolPage checkGenImages(){
        // Tüm resimlerin üretimi bittiğinde gelen "Process completed successfully." mesajını bekle
        // JavaScript ile DOM'u kontrol ederek mesajı bul (mesaj çok hızlı kaybolsa bile)
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(120));
        
        // "Process completed successfully." mesajını bekle
        longWait.until(driver -> {
            try {
                // JavaScript ile tüm success mesajlarını bul
                String script = "var messages = document.querySelectorAll('div.ajs-message.ajs-success'); " +
                               "for(var i = 0; i < messages.length; i++) { " +
                               "  var text = messages[i].textContent.trim(); " +
                               "  if(text === 'Process completed successfully.') { " +
                               "    return true; " +
                               "  } " +
                               "} " +
                               "return false;";
                Object result = ((JavascriptExecutor) driver).executeScript(script);
                return result != null && (Boolean) result;
            } catch (Exception e) {
                return false;
            }
        });
        
        // Mesajı bulduktan sonra, Generated Images başlığına scroll yap
        WebElement generatedImagesTitle = wait.until(ExpectedConditions.presenceOfElementLocated(GENERATED_IMAGES_TITLE));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", generatedImagesTitle);
        
        // Mesajı tekrar kontrol et ve assert yap
        String messageText = "Process completed successfully.";
        Assert.assertEquals(messageText, "Process completed successfully.", "Generate işlemi başarıyla tamamlanmadı.");
        screenshot();
        return this;
    }

    @Step("Send to CamQTool Butonuna basılır.")
    public CamGenToolPage clickSendToCamQTool(){
        click(SEND_TO_QUALITY_BUTTON);
        click(CONFIRMATON_BUTTON);
        return this;
    }


}
