package bot;

import araclar.Log;
import araclar.Veritabani;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import twitter.Hesap;
import twitter.Is;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import vpn.HMA;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.logging.Level;

import static araclar.Log.Tur.*;

/**
 * Created by erkanmdr on 18.12.2015.
 */
public class HesapAcBotu {

    static String TAM_ISIM;
    static String EPOSTA;
    static String KULLANICI_ADI;
    static String SIFRE;
    static WebDriver driver;

    public static void main(String[] args) {
        Veritabani.sqlBaglan();
        //TODO: İnternet testi gerek
        while (true) {
            try {
                Log.yaz("Yeni hesap açma işi başladı ", BILGI);
                long baslamazamani = System.currentTimeMillis();
                if (!HMA.bagliMi()) {
                    Veritabani.sqlKapat();
                    HMA.baglan();
                    Veritabani.sqlBaglan();
                }

                Log.yaz("Loglar devre dışı bırakılıyor... ", BILGI);
                LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
                java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
                java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

                Log.yaz("Tarayıcı açılıyor...", BILGI);
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--log-level=1");
                chromeOptions.addArguments("whitelisted-ips");
                chromeOptions.addExtensions(new File("sh/b.crx"));
                driver = new ChromeDriver(chromeOptions);
                Log.yaz("Kayıt sayfası yükleniyor...", BILGI);
                driver.get("https://mobile.twitter.com/signup?type=email");
                Log.yaz("Form dolduruluyor... (İsim Soyisim ve E-posta)", BILGI);
                TAM_ISIM = Jenerator.rastgele(Jenerator.ListDosyalari.ad) + " "
                        + Jenerator.rastgele(Jenerator.ListDosyalari.soyad);
                WebElement _adsoyad = driver.findElement(By.xpath("//*[@id=\"oauth_signup_client_fullname\"]"));
                _adsoyad.sendKeys(TAM_ISIM);
                WebElement _eposta = driver.findElement(By.xpath("//*[starts-with(@id,'A')]"));
                EPOSTA = Eposta.yeniAc();
                _eposta.sendKeys(EPOSTA);
                _eposta.submit();
                if (driver.getCurrentUrl().contains("create_password")) {
                    Log.yaz("Form dolduruluyor... (Şifre)", BILGI);
                    SIFRE = Jenerator.sifre();
                    WebElement _sifre = driver.findElement(By.xpath("//*[@id=\"password\"]"));
                    _sifre.sendKeys(SIFRE);
                    _sifre.submit();
                    if (driver.getCurrentUrl().contains("add_phone")) {
                        Log.yaz("Telefon numarası pas geçiliyor...", BILGI);
                        WebElement _simdidegil = driver.findElement(By.xpath("//*[@id=\"main_content\"]/div[3]/form/input"));
                        _simdidegil.submit();
                        WebElement _kullaniciadi = driver.findElement(By.xpath("//*[@id=\"custom_name\"]"));
                        KULLANICI_ADI = _kullaniciadi.getAttribute("value");
                        Log.yaz("Kullanıcı adı kabul ediliyor... (" + KULLANICI_ADI + ")", BILGI);
                        _kullaniciadi.submit();
                        Log.yaz("Yeni hesap açıldı", BASARILI);
                        driver.get("https://apps.twitter.com/app/new");
                        WebElement _uygulamaadi = driver.findElement(By.xpath("//*[@id=\"edit-name\"]"));
                        WebElement _uygulamaaciklamasi = driver.findElement(By.xpath("//*[@id=\"edit-description\"]"));
                        WebElement _uygulamaurlsi = driver.findElement(By.xpath("//*[@id=\"edit-description\"]"));
                        WebElement _uygulamasozlesmesi = driver.findElement(By.xpath("//*[@id=\"edit-tos-agreement\"]"));
                        _uygulamaadi.submit();

                        WebElement _uygulamaanahtarlari = driver.findElement(By.xpath("//*[@id=\"gaz-content-body\"]/div[3]/div/div[5]/div[2]/span[2]/span/a"));
                        _uygulamaanahtarlari.submit();

                        WebElement _uygulamacunsomerkey = driver.findElement(By.xpath("//*[@id=\"gaz-content-body\"]/div[3]/div/div[5]/div[2]/span[2]/span/a"));
                        WebElement _uygulamacunsomersecret = driver.findElement(By.xpath("//*[@id=\"gaz-content-body\"]/div[3]/div/div[5]/div[2]/span[2]/span/a"));




                        Log.yaz("Token oluşturuluyor...", BILGI);
                        ConfigurationBuilder builder = new ConfigurationBuilder();
                        builder.setOAuthConsumerKey(Hesap.CUNSOMERKEY);
                        builder.setOAuthConsumerSecret(Hesap.CUNSOMERSECRET);
                        Configuration configuration = builder.build();
                        TwitterFactory factory = new TwitterFactory(configuration);
                        Twitter twitter = factory.getInstance();

                        RequestToken requestToken = null;
                        requestToken = twitter.getOAuthRequestToken();

                        AccessToken accessToken = null;
                        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                        Log.yaz("Token sayfası açılıyor...", BILGI);
                        driver.get(requestToken.getAuthorizationURL());
                        WebElement _uygulamayaizinver = driver.findElement(By.xpath("//*[@id=\"allow\"]"));
                        _uygulamayaizinver.submit();
                        Log.yaz("Uygulamaya izin verildi. PIN numarası alınıyor...", BILGI);
                        WebElement _pin = driver.findElement(By.xpath("//*[@id=\"oauth_pin\"]/p/kbd/code"));
                        accessToken = twitter.getOAuthAccessToken(requestToken, _pin.getText());
                        Log.yaz("PIN numarası alındı. Token alınıyor...", BILGI);
                        String TOKEN = accessToken.getToken();
                        String SECRETTOKEN = accessToken.getTokenSecret();
                        Log.yaz("Token alındı. Tarayıcı kapatılıyor...", BILGI);
                        driver.quit();
                        Log.yaz("Hesabın ayarları yapılıyor...", BILGI);
                        Twitter hesap = Hesap.getir(TOKEN, SECRETTOKEN);
                        Is.avatarGuncelle(hesap, Jenerator.rastgeleAvatar());
                        Is.bioGuncelle(hesap, Jenerator.rastgele(Jenerator.ListDosyalari.bio));

                        for(int i=1; i< new Random().nextInt(4) + 1; i++){
                            Is.tweetAt(hesap, Jenerator.rastgele(Jenerator.ListDosyalari.tweet));
                            Thread.sleep(1000);
                            //TODO: Aynı tweet'i atmamalı!
                        }
                        //TODO: DEĞİŞKENLER SAYI OLACAK--
                        for(int i=1; i< new Random().nextInt(5) + 1; i++){
                            Is.takipEt(hesap, Jenerator.rastgele(Jenerator.ListDosyalari.takip));
                            Thread.sleep(1000);
                        }

                        Log.yaz("Hesabın doğrulaması yapılıyor...", BILGI);

                        if (Eposta.dogrula()) {
                            Veritabani.hesapEkle(KULLANICI_ADI, SIFRE,
                                    TOKEN, SECRETTOKEN, Veritabani.DURUMMODEL.YUMURTA,
                                    EPOSTA, "RATER 2");
                        } else {
                            Log.yaz("Hesabın doğrulaması yapılamadı", HATA);
                            Eposta.driver.quit();
                            Veritabani.hesapEkle(KULLANICI_ADI, SIFRE,
                                    TOKEN, SECRETTOKEN, Veritabani.DURUMMODEL.ONAYSIZ,
                                    EPOSTA, "RATER 2");
                        }
                    }
                } else {
                    Log.yaz("Bu IP'den daha fazla hesap alınamıyor", UYARI);
                    Veritabani.sqlKapat();
                    driver.quit();
                    Eposta.driver.quit();
                    HMA.baglantiyiKes();
                }
                long bitiszamani = System.currentTimeMillis();
                int gecenzaman = (int) ((bitiszamani - baslamazamani) / 1000);
                Log.yaz("Yeni hesap açma işlemi " + gecenzaman + " saniyede tamamlandı!", UYARI);
            } catch (Exception e) {
                Log.yaz("Oh! Olamaz! Bi'şey yanlış gitti. Yani şu şey: " + e, HATA);
                Log.yaz("Beklenmeyen bir hata ile karşılaşıldığı için herşey devre dışı bırakılacak!", UYARI);
                Log.yaz("Tarayıcılar devre dışı bırakılıyor...", UYARI);
                Eposta.driver.quit();
                driver.quit();
                Veritabani.sqlKapat();
                HMA.baglantiyiKes();
            }
        }
    }
}

