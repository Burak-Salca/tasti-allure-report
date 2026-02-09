package tests;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CamGenToolPage;
import pages.CamQToolPage;
import pages.MainPage;

public class CamQToolTest extends BaseTest {

    private MainPage mainPage;
    private CamGenToolPage camGenToolPage;
    private CamQToolPage camQToolPage;


    @BeforeMethod
    @Override
    public void beforeTest() throws InterruptedException {
        super.beforeTest();
        performLogin();
        mainPage = new MainPage(driver, wait);
        camGenToolPage = new CamGenToolPage(driver,wait);
        camQToolPage = new CamQToolPage(driver,wait);
    }

    @Test(description = "CamQTool Testi")
    public void successfulCamQTool() throws InterruptedException{
        mainPage.clickCamGenTool();
        camGenToolPage.checkPage()
                .fillParameters(camGenGenerationMode, camGenModel, camGenCollection, camGenFromClass, camGenToClass, camGenPromt, camGenGenerativePerImage)
                .chooseSampleImages()
                .clickGenerate()
                .checkGenImages()
                .clickSendToCamQTool();
        camQToolPage.checkPage()
                .clickCheckQualityButton()
                .checkQualityImages();
    }
}
