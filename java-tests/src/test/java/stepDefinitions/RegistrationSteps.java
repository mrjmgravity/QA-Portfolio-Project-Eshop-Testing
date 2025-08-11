package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class RegistrationSteps {

    private WebDriver driver;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("používateľ otvorí registračnú stránku")
    public void pouzivatelOtvoriRegistracnuStranku() {
        driver.get("https://automationexercise.com/login");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Ak sa zobrazí cookies hláška, klikni na „Accept“
        try {
            WebElement cookiesBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button//p[contains(text(),'Súhlas')]")));
            cookiesBtn.click();
        } catch (Exception e) {
            System.out.println("Cookies hláška sa nezobrazila – pokračujem ďalej.");
        }

        // Klikni na tlačidlo Signup
        WebElement signupBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Signup')]")));
        signupBtn.click();

        // Počkaj na input pre e-mail
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-qa='signup-email']")));
        }

    @When("zadá meno {string}")
    public void zadaMeno(String meno) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-qa='signup-name']")))
                .sendKeys(meno);
    }

    @And("zadá neplatný e-mail {string}")
    public void zadaNeplatnyEMail(String email) {

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-qa='signup-email']")))
                .sendKeys(email);;

    }

    @And("klikne na tlačidlo Signup")
    public void klikneNaTlacidloSignup() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-qa='signup-button']")))
                .click();
    }

    @Then("zobrazí sa chybová hláška Invalid email address")
    public void zobraziSaChybovaHlaskaInvalidEmailAddress() {
        WebElement emailField = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-qa='signup-email']")));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        Boolean isValid = (Boolean) js.executeScript("return arguments[0].checkValidity();", emailField);
        Assert.assertFalse("Pole e-mail prešlo validáciou, ale nemalo.", isValid);

        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", emailField);
        System.out.println("Systémová hláška: " + validationMessage);

        // Over, že hláška nie je null ani prázdna
        Assert.assertNotNull("Hláška je null.", validationMessage);
        Assert.assertFalse("Hláška je prázdna.", validationMessage.trim().isEmpty());

        // Len informatívne – nebude to spôsobovať fail testu
        List<String> expectedKeywords = Arrays.asList("@", "email", "e-mail", "vyplňte", "zadajte", "neúplná", "symbol", "adresa", "pole"
);
        boolean containsExpected = expectedKeywords.stream()
                .anyMatch(keyword -> validationMessage.toLowerCase().contains(keyword));

        if (!containsExpected) {
            System.out.println("⚠️  Hláška neobsahuje známe kľúčové slová, ale test pokračuje.");
        }
    }
}