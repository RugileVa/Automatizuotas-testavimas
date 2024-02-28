package org.example;

import dev.failsafe.internal.util.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.Random;

public class Testas4 {
    private static final String baseUrl = "https://demowebshop.tricentis.com/";
    private static String email = "";
    private static final String pass = "Pass123$";
    private static ChromeDriver driver;
    @BeforeAll
    public static void beforeAllCreateUser() {
        System.out.println("Before all");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");

        driver = new ChromeDriver();
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

    @AfterEach
    public void afterEach() {
        if (driver != null) {
            driver.quit();
        }
    }
    @BeforeEach
    public void beforeEach() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    private static void performScenario(String dataFile) {
        driver.get(baseUrl);
        driver.findElement(By.xpath("//a[@href='/login']")).click();

        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys(pass);

        driver.findElement(By.cssSelector("input[value='Log in']")).click();
        driver.findElement(By.xpath("//a[@href='/digital-downloads']")).click();

        try {
            List<String> lines = Files.readAllLines(Paths.get(dataFile));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(30000));
            for (String line : lines) {
                // System.out.println(line);
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
            // Attempt to find the element with id "billing-address-select"
            WebElement billingAddressSelect = driver.findElement(By.id("billing-address-select"));

            // If the element is found, perform actions on it
            if (billingAddressSelect.isDisplayed()) {
                System.out.println("Billing address select exists...");
                // Perform actions on the billing address select element
                billingAddressSelect.click();
                billingAddressSelect.sendKeys("n");
                billingAddressSelect.sendKeys(Keys.ENTER);
            }
        } catch (NoSuchElementException e) {
            // Handle the case where the element does not exist
            System.out.println("Billing address select does not exist...");
        }

        WebElement countryElement = driver.findElement(By.xpath("//*[@id='BillingNewAddress_CountryId']"));
        countryElement.click();
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

        //WebElement element = driver.findElement(By.xpath("/html/body/div[4]/div[1]/div[4]/div/div/div[2]/div/div[1]/strong"));
        // Assert that the element exists
        //Assert.assertTrue(element.isDisplayed());
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