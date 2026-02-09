package tests;
import base.BaseTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import pages.MainPage;

public class LoginTests extends BaseTest {

    private HomePage homePage;
    private LoginPage loginPage;
    private MainPage mainPage;

    @BeforeMethod
    @Override
    public void beforeTest() throws InterruptedException {
        super.beforeTest();
        loginPage = new LoginPage(driver, wait);
        homePage = new HomePage(driver, wait);
        mainPage = new MainPage(driver,wait);
    }

    @Test(description = "Başarılı giriş testi.")
    public void SuccessfulLogin() throws InterruptedException {
        performLogin();
        String currentUserEmail = mainPage.clickProfile();
        mainPage.checkUserInfo(loginEmail, currentUserEmail);
    }
}
