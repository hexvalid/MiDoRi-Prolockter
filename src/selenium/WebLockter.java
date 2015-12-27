package selenium;

import araclar.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static araclar.Log.Tur.*;

/**
 * Created by erkanmdr on 27.12.2015.
 */
public class WebLockter {

    public static boolean girisYap(WebDriver driver, String kullaniciadi, String sifre, String eposta) {
        //TODO: Limit yazısını görürse tel. no. istiyor demektir. Gerekeni yapacak!
        //guest url'sine gidiyorsa ip engellenmiş demektir değiştirilecek ip
        Log.yaz("Giriş yapılıyor...", BILGI);
        driver.get("https://mobile.twitter.com/session/new");
        WebElement _kullaniciadi = driver.findElement(By.xpath("//*[@id=\"session[username_or_email]\"]"));
        _kullaniciadi.sendKeys(kullaniciadi);
        WebElement _sifre = driver.findElement(By.xpath("//*[@id=\"session[password]\"]"));
        _sifre.sendKeys(sifre);
        _sifre.submit();
        try {
            Log.yaz("İkinci doğrulama atlatılıyor...", BILGI);
            WebElement _ikincidogrulama = driver.findElement(By.xpath("//*[@id=\"challenge_response\"]"));
            _ikincidogrulama.sendKeys(eposta);
            _ikincidogrulama.submit();
        } catch (NoSuchElementException e) {
        }
        try {
            driver.findElement(By.xpath("//*[@id=\"main_content\"]/div/div/a"));
            Log.yaz("Giriş yapıldı", BASARILI);
            return true;
        } catch (NoSuchElementException e) {
            Log.yaz("Giriş yapılamadı!", HATA);
            if (driver.getCurrentUrl().contains("guest")) {
                Log.yaz("Bu IP'den giriş engellendi!", HATA);
            }
            return false;
        }
    }

    public static void tweetAt(WebDriver driver, String tweet) {
        Log.yaz("Tweet gönderiliyor...", BILGI);
        driver.get("https://mobile.twitter.com/compose/tweet");
        WebElement _tweetkutusu = driver.findElement(By.xpath("//*[@id=\"main_content\"]/div/div/form/table/tbody/tr[2]/td/div/textarea"));
        _tweetkutusu.sendKeys(tweet);
        _tweetkutusu.submit();
        WebElement _durum = driver.findElement(By.xpath("//*[@id=\"container\"]/div[2]/div"));
        if (_durum.getText().contains("gönderildi")) {
            Log.yaz("Tweet gönderildi", BASARILI);
        } else if (_durum.getText().contains("zaten")) {
            Log.yaz("Tweet gönderilemedi: Zaten bunu tweetlemişsin!", UYARI);
        } else {
            Log.yaz("Tweet gönderilemedi", HATA);
        }
    }

    public static void takipEt(WebDriver driver, String kullanici) {
        Log.yaz("Kullanıcı takip işi başlıyor...", BILGI);
        driver.get("https://mobile.twitter.com/" + kullanici);
        WebElement _takipetbutonu = driver.findElement(
                By.xpath("//*[@id=\"main_content\"]/div[1]/div/form[1]/span[2]/input"));
        _takipetbutonu.click();
        try {
            WebElement _takibibirakbutonu = driver.findElement(
                    By.xpath("//*[@id=\"main_content\"]/div/form/div/span/input"));
            Log.yaz("Zaten takip ediliyor", UYARI);
        } catch (NoSuchElementException e) {
            Log.yaz("Takip edildi", BASARILI);
        }
    }


}
