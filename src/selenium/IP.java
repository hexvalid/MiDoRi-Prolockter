package selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

/**
 * Created by erkanmdr on 18.12.2015.
 */
public class IP {

    public static void main(String[] args) {
        System.setProperty("socksProxyHost", "localhost"); // replace "localhost" with your proxy server
        System.setProperty("socksProxyPort", "2825"); // replace "9999" with your proxy port number

        WebDriver driver = new HtmlUnitDriver();
        driver.get("http://mxtoolbox.com/WhatIsMyIP/");
        String myIP = driver.findElement(By.xpath("//*[@id=\"ctl00_ContentPlaceHolder1_hlIP\"]")).getText();
        System.out.println(myIP);

    }
}
