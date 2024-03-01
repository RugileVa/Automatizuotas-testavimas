package org.example;

import dev.failsafe.internal.util.Assert;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Testas4 {
    //private static String email_register = "user-438654255595719931@test.com";
    private static final String baseUrl = "https://demowebshop.tricentis.com/";
    private static String email = "";
    private static final String pass = "Pass123$";
    private static ChromeDriver driver;
    @BeforeClass
    public static void beforeClassCreateUser() {
        System.out.println("Before all");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        driver = new ChromeDriver(options);
        //driver = new ChromeDriver();
        driver.manage().window().maximize();

        email =  String.format("user%d%d@test.com", new Random().nextInt(), new Random().nextInt());

        driver.get(baseUrl);
        driver.findElement(By.xpath("//a[@class='ico-login' and @href='/login']")).click();
        driver.findElement(By.xpath("//input[@type='button' and @class='button-1 register-button' and @value='Register']")).click();
        driver.findElement(By.id("gender-female")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Tester");
        driver.findElement(By.id("LastName")).sendKeys("Tester");
        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(pass);
        driver.findElement(By.id("ConfirmPassword")).sendKeys(pass);
        driver.findElement(By.id("register-button")).click();
        driver.findElement(By.xpath("//input[@type='button' and @class='button-1 register-continue-button' and @value='Continue']")).click();
        driver.quit();
    }

    @After
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Before
    public void beforeEach() {
      ChromeOptions options = new ChromeOptions();
      options.addArguments("--headless");
      //driver = new ChromeDriver();
      driver = new ChromeDriver(options);
      driver.manage().window().maximize();
      driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    private static void performScenario(String dataFile) {
        driver.get(baseUrl);
        driver.findElement(By.xpath("//a[@href='/login']")).click();

        WebElement emailElement = driver.findElement(By.id("Email"));
        emailElement.click();
        emailElement.sendKeys(email);

        WebElement passwordElement = driver.findElement(By.id("Password"));
        passwordElement.click();
        passwordElement.sendKeys(pass);

        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        driver.findElement(By.xpath("//a[@href='/digital-downloads']")).click();

        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(30000));
            for (String line : lines) {
                WebElement element = driver.findElement(By.xpath("//div[@class='item-box']//h2[@class='product-title']/a[text() = '" + line + "']/ancestor::div[@class='product-item']//input[@type='button'] "));
                wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.findElement(By.cssSelector("span.cart-label")).click();
        driver.findElement(By.id("termsofservice")).click();
        driver.findElement(By.id("checkout")).click();

        try {
            WebElement billingAddressSelect = driver.findElement(By.id("billing-address-select"));
            if (billingAddressSelect.isDisplayed()) {
                System.out.println("Billing address select exists...");
                billingAddressSelect.click();
                billingAddressSelect.sendKeys("n");
                billingAddressSelect.sendKeys(Keys.ENTER);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Billing address select does not exist...");
        }

        WebElement countryElement = driver.findElement(By.id("BillingNewAddress_CountryId"));
        countryElement.sendKeys(Keys.ENTER);
        countryElement.sendKeys("lit");
        countryElement.sendKeys(Keys.ENTER);

        driver.findElement(By.id("BillingNewAddress_City")).sendKeys("Vilnius");
        driver.findElement(By.id("BillingNewAddress_Address1")).sendKeys("Vilniaus g. 1");
        driver.findElement(By.id("BillingNewAddress_ZipPostalCode")).sendKeys("12345");
        driver.findElement(By.id("BillingNewAddress_PhoneNumber")).sendKeys("12345");

        driver.findElement(By.cssSelector("input[type='button'][title='Continue'][class='button-1 new-address-next-step-button']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='button'].button-1.payment-method-next-step-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.button-1.payment-info-next-step-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.button-1.confirm-order-next-step-button"))).click();

        Assert.isTrue(elementExists(driver, "//strong[text()='Your order has been successfully processed!']"), "Order did not go through");
    }

    public static boolean elementExists(WebDriver driver, String xpath) {
        try {
            driver.findElement(By.xpath(xpath));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }
    @Test
    public void testScenario1() {
        performScenario("data1.txt");
    }
    @Test
    public void testScenario2() {
        performScenario("data2.txt");
    }
}