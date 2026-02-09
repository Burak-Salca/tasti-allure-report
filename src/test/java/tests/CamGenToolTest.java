package tests;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CamGenToolPage;
import pages.MainPage;

public class CamGenToolTest extends BaseTest {

    private MainPage mainPage;
    private CamGenToolPage camGenToolPage;

    @BeforeMethod
    @Override
    public void beforeTest() throws InterruptedException {
        super.beforeTest();
        performLogin();
        mainPage = new MainPage(driver, wait);
        camGenToolPage = new CamGenToolPage(driver,wait);

    }

    @Test(description = "CamGenTool Testi")
    public void SuccessfullCamGenTool() throws InterruptedException{
        mainPage.clickCamGenTool();
        camGenToolPage.checkPage()
                .fillParameters(camGenGenerationMode, camGenModel, camGenCollection, camGenFromClass, camGenToClass, camGenPromt, camGenGenerativePerImage)
                .chooseSampleImages()
                .clickGenerate()
                .checkGenImages()
                .clickSendToCamQTool();
    }
}
