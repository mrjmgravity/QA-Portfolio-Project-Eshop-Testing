package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

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
        driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div[2]/div[2]/button[1]/p")).click();

    }

    @When("zadá meno {string}")
    public void zadaMeno(String meno) {
        driver.findElement(By.cssSelector("[data-qa='signup-name']")).sendKeys(meno);
    }

    @And("zadá neplatný e-mail {string}")
    public void zadaNeplatnyEMail(String email) {
        driver.findElement(By.cssSelector("[data-qa='signup-email']")).sendKeys(email);
    }

    @And("klikne na tlačidlo Signup")
    public void klikneNaTlacidloSignup() {
        driver.findElement(By.cssSelector("[data-qa='signup-button']")).click();
    }

    @Then("zobrazí sa chybová hláška Invalid email address")
    public void zobraziSaChybovaHlaskaInvalidEmailAddress() {
       WebElement emailField = driver.findElement(By.cssSelector("[data-qa='signup-email']"));
    JavascriptExecutor js = (JavascriptExecutor) driver;

    // 1. Overíme, že pole NIE JE validné
    Boolean isValid = (Boolean) js.executeScript("return arguments[0].checkValidity();", emailField);
    Assert.assertFalse("Pole e-mail prešlo validáciou, ale nemalo.", isValid);

    // 2. Získame systémovú hlášku pre invalidné pole
    String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", emailField);
    System.out.println("Systémová hláška: " + validationMessage);

    // 3. Overíme, že hláška nie je prázdna
    Assert.assertNotNull("Hláška je null.", validationMessage);
    Assert.assertFalse("Hláška je prázdna.", validationMessage.trim().isEmpty());

    // 4. Overíme, že obsahuje niečo očakávané (aspoň základ)
    Assert.assertTrue("Hláška neobsahuje '@', 'email' alebo 'fill out'.",
        validationMessage.toLowerCase().contains("@")
        || validationMessage.toLowerCase().contains("email")
        || validationMessage.toLowerCase().contains("fill out"));
    }
}
