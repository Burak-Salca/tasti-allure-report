package tests;

import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CamTrainToolPage;
import pages.MainPage;

public class CamTrainToolTests extends BaseTest {

    private MainPage mainPage;
    private CamTrainToolPage camTrainToolPage;

    @BeforeMethod
    @Override
    public void beforeTest() throws InterruptedException{
        super.beforeTest();
        performLogin();
        mainPage = new MainPage(driver,wait);
        camTrainToolPage = new CamTrainToolPage(driver,wait);
    }

    @Test(description = "CamTrainTool Testi")
    public void successfulCamTrainTool(){
        mainPage.clickCamTrainTool();
        camTrainToolPage.checkPage()
                .fillParameters(camTrainPromt)
                .uploadSampleImages(camTrainFolderPath, camTrainFileNames)
                .clickTrainStart()
                .checkTrain();
    }

}
