package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;

public class Testas2 {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\IdeaProjects\\chromedriver-win64\\chromedriver.exe");

        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 1. Atsidaryti https://demoqa.com
        driver.get("https://demoqa.com/");

        // 2. Uždarome Cookies sutikimo langą
        driver.findElement(By.xpath("//p[@class='fc-button-label' and text()='Consent']")).click();

        // 3. Pasirenkame "Widgets" kortelę
        driver.findElement(By.xpath("//div[contains(@class, 'card mt-4 top-card') and descendant::h5[text()='Widgets']]\n")).click();

        // 4. Pasirenkame meniu punktą "Progress Bar"
        WebElement element = driver.findElement(By.xpath("//span[text()='Progress Bar']"));
        //Pasibandyti su explicit wait
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

        // 5. Spausti mygtuką "Start"
        driver.findElement(By.id("startStopButton")).click();

        // 6. Laukiame, kol progreso eilutė bus 100%
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(30000));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//*[@id='progressBar']/div"), "100%"));

        // 7. Paspaudžiame "Reset" mygtuką
        driver.findElement(By.id("resetButton")).click();

        // 8. Patikriname, ar progreso eilutė tuščia (0%)
        assert(Objects.equals(driver.findElement(By.xpath("//*[@id='progressBar']/div")).getText(), "0%"));

        Thread.sleep(30000);
        driver.quit();
    }
}