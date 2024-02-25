package org.example;

import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Testas3 {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\IdeaProjects\\chromedriver-win64\\chromedriver.exe");

        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 1. Atsidaryti https://demoqa.com
        driver.get("https://demoqa.com/");

        // 2. Uždarome Cookies sutikimo langą
        driver.findElement(By.xpath("//p[@class='fc-button-label' and text()='Consent']")).click();

        // 3. Pasirenkame "Elements" kortelę
        driver.findElement(By.xpath("//div[contains(@class, 'card mt-4 top-card') and descendant::h5[text()='Elements']]")).click();

        // 4. Pasirenkame meniu punktą "Web Tables"
        WebElement element = driver.findElement(By.xpath("//span[text()='Web Tables']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

        // 5. Pridėti pakankamai elementų, kad atsirastų antras puslapis puslapavime
        boolean nextButtonEnabled = true;

        while (nextButtonEnabled) {
            WebElement addButton = driver.findElement(By.id("addNewRecordButton"));
            addButton.click();

            driver.findElement(By.id("firstName")).sendKeys("Name");
            driver.findElement(By.id("lastName")).sendKeys("Lastname");
            driver.findElement(By.id("userEmail")).sendKeys("test@test.com");
            driver.findElement(By.id("age")).sendKeys("21");
            driver.findElement(By.id("salary")).sendKeys("10000");
            driver.findElement(By.id("department")).sendKeys("IDK");

            driver.findElement(By.id("submit")).click();

            WebElement nextButton = driver.findElement(By.xpath("//button[text()='Next'][1]"));
            nextButtonEnabled = !nextButton.isEnabled();
        }

        // 6. Pasirinkti 2 puslapį, paspaudus mygtuką "Next"
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//button[text()='Next']")));

        // 7. Ištrinti elementą antrame puslapyje
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@title='Delete'][1]")));

        // 8.
        String value = driver.findElement(By.xpath("//input[@aria-label='jump to page']")).getAttribute("value");
        Assert.isTrue(value.equals("1"), "");

        Thread.sleep(30000);
        driver.quit();
    }
}