package org.example;

import dev.failsafe.internal.util.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

//junit
public class Testas4 {
    private static final String baseUrl = "https://demowebshop.tricentis.com/";
    private static final String email = "test@example.com";
    private static final String password = "password123";
    private static ChromeDriver driver;

    //@Test
    public void testScenario1() {
        // Test scenario 1 implementation
        createUserAndPerformScenario();
    }

    // @Test
//    public void testScenario2() {
//        // Test scenario 2 implementation
//        createUserAndPerformScenario("data2.txt");
//    }

    private static void createUserAndPerformScenario() {
        // User creation steps
        driver.get(baseUrl);
        driver.findElement(By.xpath("//a[@href='/login']")).click();
        driver.findElement(By.xpath("//input[@type='button' and @class='button-1 register-button']")).click();

        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Vardas");
        driver.findElement(By.id("LastName")).sendKeys("Pavardė");
        driver.findElement(By.id("Email")).sendKeys("testas@example.com");
        driver.findElement(By.id("Password")).sendKeys("slaptazodis");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("slaptazodis");

        driver.findElement(By.id("register-button")).click();

        driver.findElement(By.xpath("//input[@type='button' and @class='button-1 register-continue-button']")).click();

        driver.findElement(By.xpath("//a[@href='/digital-downloads']")).click();


//        // Performing scenario steps
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Process each line of the file (e.g., adding items to the cart)
//            }
//            reader.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // Implement the rest of the scenario steps
    }

    @Test
    public void main() throws InterruptedException {
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\IdeaProjects\\chromedriver-win64\\chromedriver.exe");
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 1. Atsidaryti https://demoqa.com

        driver.get(baseUrl);
        driver.findElement(By.xpath("//a[@href='/login']")).click();
        driver.findElement(By.xpath("//input[@type='button' and @class='button-1 register-button']")).click();

        driver.findElement(By.id("gender-male")).click();
        driver.findElement(By.id("FirstName")).sendKeys("Vardas");
        driver.findElement(By.id("LastName")).sendKeys("Pavardė");
        driver.findElement(By.id("Email")).sendKeys("testas@example.com");
        driver.findElement(By.id("Password")).sendKeys("slaptazodis");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("slaptazodis");

        driver.findElement(By.id("register-button")).click();

        driver.findElement(By.xpath("//input[@type='button' and @class='button-1 register-continue-button']")).click();

        driver.findElement(By.xpath("//a[@href='/digital-downloads']")).click();

        driver.quit();
    }
}