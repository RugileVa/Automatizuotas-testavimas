package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Testas1 {
    public static void main(String[] args) throws InterruptedException {
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Dell\\IdeaProjects\\chromedriver-win64\\chromedriver.exe");

        FirefoxDriver driver = new FirefoxDriver();

        driver.manage().window().maximize();

        driver.get("https://demowebshop.tricentis.com/");

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        // Spausti 'Gift Cards' kairiajame meniu
        driver.findElement(By.xpath("//a[@href='/gift-cards']")).click();

       // Pasirinkti prekę, kurios kaina didesnė nei 99
        List<WebElement> giftItems = driver.findElements(By.xpath("//h2[@class='product-title']/a"));
        for (WebElement item : giftItems) {
            String linkText = item.getText();
            String priceText = linkText.replaceAll("[^0-9.]", ""); // Extract price from link text
            double price = Double.parseDouble(priceText);
            if (price > 99) {
                item.click();
                break;
            }
        }
        // Supildyti laukus 'Recipient's Name:' ir 'Your Name:'
        driver.findElement(By.id("giftcard_4_RecipientName")).sendKeys("Rec Name");

        driver.findElement(By.id("giftcard_4_SenderName")).sendKeys("Sen Name");

       // Įvesti '5000' į lauką 'Qty'
       driver.findElement(By.id("addtocart_4_EnteredQuantity")).clear();
       driver.findElement(By.id("addtocart_4_EnteredQuantity")).sendKeys("5000");

       // Spausti 'Add to cart' mygtuką
       driver.findElement(By.id("add-to-cart-button-4")).click();

       // Laukite, kol prekė pridedama į krepšelį
        Thread.sleep(3000);


        // Spausti 'Add to wish list' mygtuką
        driver.findElement(By.id("add-to-wishlist-button-4")).click();

       // driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

       // Spausti 'Jewelry' kairiajame meniu
       WebElement jewelryLink = driver.findElement(By.linkText("Jewelry"));
       jewelryLink.click();

        // Spausti 'Create Your Own Jewelry' nuorodą
        WebElement createYourOwnJewelryLink = driver.findElement(By.linkText("Create Your Own Jewelry"));
        createYourOwnJewelryLink.click();

        // Pasirinkti reikšmę 'Material' silver ir length
        WebElement materialDropdown = driver.findElement(By.id("product_attribute_71_9_15"));
        materialDropdown.sendKeys("Silver 1mm");

        WebElement length = driver.findElement(By.id("product_attribute_71_10_16"));
        length.sendKeys("80");

        WebElement starOption = driver.findElement(By.id("product_attribute_71_11_17_50"));
        starOption.click();

        driver.findElement(By.id("addtocart_71_EnteredQuantity")).clear();
        driver.findElement(By.id("addtocart_71_EnteredQuantity")).sendKeys("26");

        // Spausti 'Add to cart' mygtuką
        WebElement addToCartJewelryButton = driver.findElement(By.id("add-to-cart-button-71"));
        addToCartJewelryButton.click();

        // šito reikėjo, nes kažkaip kitaip nespėja į wishlist pridėt, gal dėstytojas kitaip padarys
        Thread.sleep(3000);
        // Laukite, kol prekė pridedama į krepšelį

        // Spausti 'Add to wish list' mygtuką
        WebElement addToWishlistJewelryButton = driver.findElement(By.xpath("//input[@value='Add to wishlist']"));
        addToWishlistJewelryButton.click();

        // Spausti nuorodą 'Wishlist' puslapio viršuje
        WebElement wishlistLink = driver.findElement(By.linkText("Wishlist"));
        wishlistLink.click();

        // Dabar galite paspausti varneles prekėms, kurias norite įdėti į krepšelį
        List<WebElement> addToCartElements = driver.findElements(By.xpath("//td[@class='add-to-cart']/input[@name='addtocart']"));

        for (WebElement element : addToCartElements) {
            element.click();
        }

        // add to cart
        WebElement addToCartButton = driver.findElement(By.xpath("//input[@name='addtocartbutton' and @value='Add to cart']"));
        addToCartButton.click();

        WebElement subtotalElement = driver.findElement(By.xpath("//span[@class='product-price']"));

    }
}