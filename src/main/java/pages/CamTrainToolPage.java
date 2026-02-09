package pages;

import base.BaseLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;

public class CamTrainToolPage extends BaseLibrary {

    private static final By CAMTRAINTOOL_HEADER = By.xpath("//button[contains(@class,'dashboard-logo') and .//div[normalize-space()='CamTrainTool']]\n");
    private static final By PROMT_TEXT_INPUT = By.xpath("//textarea[contains(@class,'input-field')]");
    private static final By UPLOAD_SAMPLE_IMAGES_BUTTON = By.id("selectfolder-camtrain");
    private static final By FILE_INPUT = By.xpath("//input[@type='file']");
    private static final By START_TRAIN_BUTTON = By.id("buttonTrainModel");
    private static final By SAVE_CONFIG_BUTTON = By.xpath("//button[normalize-space()='Save Train Configuration']\n");


    public CamTrainToolPage(WebDriver driver, WebDriverWait wait){
        this.driver = driver;
        this.wait = wait;
    }

    @Step("CamTrainTool sayfasının açıldığı kontrol edilir.")
    public CamTrainToolPage checkPage(){
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(CAMTRAINTOOL_HEADER));
        screenshot();
        String camTrainToolHeader = header.getText().trim();
        Assert.assertEquals(camTrainToolHeader,"CamTrainTool","Sayfa yüklemesinde bir sorun oluştu.");
        return this;
    }

    @Step("Parametreler girilir.")
    public CamTrainToolPage fillParameters(String promtData){
        try {
            WebElement promtTextInput = wait.until(ExpectedConditions.elementToBeClickable(PROMT_TEXT_INPUT));
            promtTextInput.sendKeys(promtData);
            screenshot();
        } catch (Exception e) {
            Assert.fail("Promt girilemedi: " + e.getMessage());
        }
        return this;
    }

    @Step("Eğitim yapılcak resimler yüklenir.")
    public CamTrainToolPage uploadSampleImages(String path, String[] imagesFileNames){
        click(UPLOAD_SAMPLE_IMAGES_BUTTON);
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(FILE_INPUT));
        String basePath = path;
        String[] fileNames = imagesFileNames;
        String[] extensions = {".jpg", ".png", ".jpeg", ".bmp", ".JPG", ".PNG", ".JPEG", ".BMP"};
        StringBuilder filePaths = new StringBuilder();
        int foundFileCount = 0;
        
        for (int i = 0; i < fileNames.length; i++) {
            String filePath = null;
            for (String ext : extensions) {
                String fullPath = basePath + fileNames[i] + ext;
                File file = new File(fullPath);
                if (file.exists() && file.isFile()) {
                    filePath = fullPath;
                    break;
                }
            }
            if (filePath != null) {
                if (foundFileCount > 0) {
                    filePaths.append("\n");
                }
                filePaths.append(filePath);
                foundFileCount++;
            } else {
            Assert.fail("Dosya bulunamadı: " + basePath + fileNames[i] + " (jpg, png, jpeg, bmp uzantıları kontrol edildi)");
            }
        }
        if (foundFileCount == 0) {
            Assert.fail("Hiçbir dosya bulunamadı. Klasör yolu: " + basePath);
        }
        fileInput.sendKeys(filePaths.toString());
        screenshot();
        return this;
    }

    @Step("Train işlemi başlatılır.")
    public CamTrainToolPage clickTrainStart(){
        try{
            click(START_TRAIN_BUTTON);
            screenshot();
        } catch (Exception e) {
            Assert.fail("Train işlemi başlatılamadı: " + e.getMessage());
        }
        return this;
    }

    @Step("Train işlemi sonuçlanır.")
    public CamTrainToolPage checkTrain(){
        try{
            WebDriverWait waitTrain = new WebDriverWait(driver, Duration.ofSeconds(120));
            waitTrain.until(ExpectedConditions.elementToBeClickable(SAVE_CONFIG_BUTTON));
            screenshot();
        } catch (Exception e) {
            Assert.fail("Train işlemi başarılı bir şekilde sonuçlanmadı: " + e.getMessage());
        }
        return this;
    }
}

