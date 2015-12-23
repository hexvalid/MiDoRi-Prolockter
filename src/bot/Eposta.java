package bot;

import araclar.Log;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import static araclar.Log.Tur.BASARILI;
import static araclar.Log.Tur.BILGI;

/**
 * Created by erkanmdr on 30.11.2015.
 */
public class Eposta {

    static WebDriver driver;

    public static String yeniAc() {
        Log.yaz("Yeni E-posta hesabı hazırlanıyor...", BILGI);
        driver = new HtmlUnitDriver();
        driver.get("https://10minutemail.net/?lang=tr");
        String email = driver.findElement(By.id("fe_text")).getAttribute("value");
        Log.yaz("Yeni E-posta hesabı hazır", BASARILI);
        return email;
    }

    public static boolean dogrula() {
        Log.yaz("E-posta hesabının gelen kutusu yenileniyor...", BILGI);
        driver.get("https://10minutemail.net/?lang=tr");
        int i = 1;
        Log.yaz("Doğrulama postasının gelmesi bekleniyor...", BILGI);
        while (true) {
            try {
                String x = driver.findElement(By.xpath("//*[@id=\"maillist\"]/tbody/tr[3]/td[2]/a")).getText();
                Log.yaz("Doğrulama postası geldi", BASARILI);
                break;
            } catch (org.openqa.selenium.NoSuchElementException e) {
                i++;
                if (i >= 10) {
                    return false;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                Log.yaz("E-posta hesabının gelen kutusu tekrar yenileniyor...", BILGI);
                driver.get("https://10minutemail.net/?lang=tr");
            }
        }
        Log.yaz("Hesap doğrulanıyor...", BILGI);
        driver.findElement(By.xpath("//*[@id=\"maillist\"]/tbody/tr[3]/td[2]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"tabs-1\"]/p/a")).click();
        Log.yaz("Hesap doğrulandı", BASARILI);
        driver.quit();
        return true;
    }

    public String changeTurtoEng(String s) {
        s = s.replace("ş", "s");
        s = s.replace("Ş", "S");
        s = s.replace("ğ", "g");
        s = s.replace("Ğ", "G");
        s = s.replace("İ", "I");
        s = s.replace("ı", "i");
        s = s.replace("ç", "c");
        s = s.replace("Ç", "C");
        s = s.replace("ö", "o");
        s = s.replace("Ö", "O");
        s = s.replace("ü", "u");
        s = s.replace("Ü", "U");
        return s;
    }


}
