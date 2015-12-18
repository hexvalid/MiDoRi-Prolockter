package selenium;

import com.gargoylesoftware.htmlunit.WebClient;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;
import java.util.Random;

/**
 * Created by erkanmdr on 18.12.2015.
 */
public class Test {


    public static void main(String[] args) {
        // Create a new instance of the html unit driver
        // Notice that the remainder of the code relies on the interface,
        // not the implementation.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--proxy-server=socks5://" + "localhost" + ":" + "2825");
        options.addArguments("-incognito");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://mobile.twitter.com/signup?type=email");

        System.out.println("Page title is: " + driver.getCurrentUrl());

        Random generator = new Random();
        int i = generator.nextInt(10545454) + 1;
        WebElement _kullaniciAdi = driver.findElement(By.xpath("//*[@id=\"oauth_signup_client_fullname\"]"));
        _kullaniciAdi.sendKeys("Hayko Elbet"+i);
        final WebElement errorElement = driver.findElement(By.xpath("//*[starts-with(@id,'A')]"));


        errorElement.sendKeys("barzani"+i+"@hotmail.com");
        errorElement.submit();
        System.out.println("Page title is: " + driver.getCurrentUrl());

    }
}

