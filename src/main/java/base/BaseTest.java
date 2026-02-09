package base;
import data.LoginData;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import pages.HomePage;
import pages.LoginPage;

import java.io.File;
import java.time.Duration;

public abstract class BaseTest extends LoginData {

    public WebDriver driver;
    public WebDriverWait wait;
    public Select select;

    protected void performLogin() {
        HomePage homePage = new HomePage(driver, wait);
        LoginPage loginPage = new LoginPage(driver, wait);
        homePage.goToLoginPage();
        loginPage.fillEmail(loginEmail).fillPassword(loginPassword).clickLogin();
    }

    protected Select createSelect(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        return new Select(element);
    }

    protected void click(By locator){
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
        }
    }

    @BeforeSuite
    public void cleanAllureOnce() {
        cleanAllureResults();
    }

    @BeforeMethod(description = "Web tarayıcı açılır.")
    public void beforeTest() throws InterruptedException {
        //cleanAllureResults();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(1000);
    }

    @AfterMethod(description = "Web tarayıcı kapanır.")
    public void afterTests(){
        driver.quit();
    }

    void cleanAllureResults() {
        File allureResults = new File("allure-results");
        if (allureResults.exists() && allureResults.isDirectory()) {
            for (File file : allureResults.listFiles()) {
                file.delete();
            }
        }
    }
}
