package tests;
import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CamFiToolPage;
import pages.MainPage;

public class CamFiToolTests extends BaseTest {

    private MainPage mainPage;
    private CamFiToolPage camFiToolPage;

    @BeforeMethod
    @Override
    public void beforeTest() throws InterruptedException {
        super.beforeTest();
        performLogin();
        mainPage = new MainPage(driver, wait);
        camFiToolPage = new CamFiToolPage(driver, wait);
    }

    @Test(description = "CamFiTool Testi")
    public void SuccessfullCamFiTool() throws InterruptedException {
        mainPage.clickCamFiTool();
        camFiToolPage.fillParameters(collectionCategory,fromClass,toClass,faultType)
                .chooseUploadImages()
                .clickFaultInjectButton()
                .chooseFaultyImages();
    }
}
