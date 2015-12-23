package bot;

import araclar.Log;
import araclar.Veritabani;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import twitter.Hesap;
import twitter.Is;
import twitter4j.Twitter;
import vpn.HMA;

import java.io.File;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import static araclar.Log.Tur.*;

/**
 * Created by erkanmdr on 18.12.2015.
 */
public class HesapAcBotu {

    //DEĞİŞKENLER!
    static int ZAMAN_ASIMI = 200;
    static int ZAMAN_ASIMI_TARAYICI = 25;

    //////////////
    static String TAM_ISIM;
    static String EPOSTA;
    static String KULLANICI_ADI;
    static String SIFRE;
    static WebDriver driver;

    //TODO: Bağımlılıklar: curl wget openvpn dialog sudo fping 	chromium-chromedriver
    public static void main(String[] args) {
        //TODO: /usr/bin/chromedriver kontrolü yapılacak!
        //TODO: İnternet testi gerek
        while (true) {
            try {
                Future<?> f = Executors.newSingleThreadExecutor().submit((Runnable) () -> {
                    Log.yaz("Yeni hesap açma işi başladı ", BILGI);


                    long baslamazamani = System.currentTimeMillis();
                    if (!HMA.bagliMi()) {
                       if (Veritabani.sqlBaglantisiVarmi())
                           Veritabani.sqlKapat();
                        HMA.baglan();
                        Veritabani.sqlBaglan();
                    }
                    if (!Veritabani.sqlBaglantisiVarmi())
                        Veritabani.sqlBaglan();
                    Log.yaz("Tarayıcı hazırlanıyor... ", BILGI);
                    LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
                    java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
                    java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

                    Log.yaz("Tarayıcı açılıyor...", BILGI);
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--log-level=3");
                    chromeOptions.addArguments("--disable-logging");

                    chromeOptions.addExtensions(new File("sh/b.crx"));
                    driver = new ChromeDriver(chromeOptions);
                    driver.manage().window().setSize(new Dimension(1, 1));
                    driver.manage().window().setPosition(new Point(0, 0));

                    WebDriverWait zamanasimi = new WebDriverWait(driver, ZAMAN_ASIMI_TARAYICI);

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
                            Log.yaz("Uygulama oluşturma sayfası açılıyor...", BILGI);
                            driver.get("https://apps.twitter.com/app/new");
                            WebElement _uygulamaadi = driver.findElement(By.xpath("//*[@id=\"edit-name\"]"));
                            Log.yaz("Uygulama oluşturma sayfası açıldı", BILGI);

                            _uygulamaadi.sendKeys(Jenerator.rastgele(Jenerator.ListDosyalari.star) + " " +
                                    new Random().nextInt(100000) + 1);

                            WebElement _uygulamaaciklamasi = driver.findElement(By.xpath("//*[@id=\"edit-description\"]"));
                            _uygulamaaciklamasi.sendKeys("For education.");

                            WebElement _uygulamaurlsi = driver.findElement(By.xpath("//*[@id=\"edit-url\"]"));
                            _uygulamaurlsi.sendKeys("http://facebook.com/" + KULLANICI_ADI.toLowerCase());

                            WebElement _uygulamasozlesmesi = driver.findElement(By.xpath("//*[@id=\"edit-tos-agreement\"]"));
                            _uygulamasozlesmesi.click();
                            _uygulamaadi.submit();

                            Log.yaz("Uygulama anahtarı bekleniyor...", BILGI);

                            zamanasimi.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"messages\"]/div")));

                            String anahtarURLsi = driver.getCurrentUrl() + "/keys";
                            driver.get(anahtarURLsi);


                            Log.yaz("Uygulama tokeni bekleniyor...", BILGI);
                            WebElement _uygulamacunsomerkey = zamanasimi.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"gaz-content-body\"]/div[3]/div/div[2]/div[1]/span[2]")));

                            String CUNSOMERKEY = _uygulamacunsomerkey.getText();

                            WebElement _uygulamacunsomersecret = driver.findElement(By.xpath("//*[@id=\"gaz-content-body\"]/div[3]/div/div[2]/div[2]/span[2]"));
                            String CUNSOMERSECRET = _uygulamacunsomersecret.getText();

                            Log.yaz("Token oluşturuluyor...", BILGI);
                            WebElement _uygulamatokenimiolustur = driver.findElement(By.xpath("//*[@id=\"edit-submit-owner-token\"]"));
                            _uygulamatokenimiolustur.submit();

                            WebElement _uygulamatoken = zamanasimi.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"gaz-content-body\"]/div[3]/div/div[4]/div[1]/span[2]")));
                            WebElement _uygulamatokensecret = driver.findElement(By.xpath("//*[@id=\"gaz-content-body\"]/div[3]/div/div[4]/div[2]/span[2]"));

                            String TOKEN = _uygulamatoken.getText();
                            String SECRETTOKEN = _uygulamatokensecret.getText();

                            Log.yaz("Token alındı. Tarayıcı kapatılıyor...", BILGI);
                            driver.quit();
                            Log.yaz("Hesabın ayarları yapılıyor...", BILGI);
                            Twitter hesap = Hesap.getir(TOKEN, SECRETTOKEN, CUNSOMERKEY, CUNSOMERSECRET);
                            Is.avatarGuncelle(hesap, Jenerator.rastgeleAvatar());
                            Is.bannerGuncelle(hesap, Jenerator.rastgeleBanner());

                            Is.bioGuncelle(hesap, Veritabani.rastgeleBio());

                            for (int i = 1; i < new Random().nextInt(4) + 1; i++) {
                                Is.tweetAt(hesap, Veritabani.rastgeleTweet());
                                //TODO: Aynı tweet'i atmamalı!
                            }
                            //TODO: DEĞİŞKENLER SAYI OLACAK--
                            for (int i = 1; i < new Random().nextInt(6) + 4; i++) {
                                Is.takipEt(hesap, Veritabani.rastgeleTakipEdilecekKullanici());
                            }

                            Log.yaz("Hesabın doğrulaması yapılıyor...", BILGI);

                            if (Eposta.dogrula()) {
                                Veritabani.hesapEkle(KULLANICI_ADI, SIFRE,
                                        TOKEN, SECRETTOKEN, CUNSOMERKEY, CUNSOMERSECRET,
                                        Veritabani.DURUMMODEL.REALISTIC, EPOSTA, "RATER 4 SERVER");
                            } else {
                                Log.yaz("Hesabın doğrulaması yapılamadı", HATA);
                                Eposta.driver.close();
                            }
                        }
                    } else {
                        Log.yaz("Bu IP'den daha fazla hesap alınamıyor", UYARI);
                        Log.yaz("Tarayıcılar devre dışı bırakılıyor...", UYARI);
                        if (!Eposta.driver.toString().contains("(null)")) {
                            Eposta.driver.close();
                            Log.yaz("E-posta tarayıcısı kapatıldı", UYARI);
                        }
                        if (!driver.toString().contains("(null)")) {
                            driver.close();
                            Log.yaz("Ana tarayıcı kapatıldı", UYARI);
                        }
                        if (Veritabani.sqlBaglantisiVarmi()) {
                            Veritabani.sqlKapat();
                        }
                        if (HMA.bagliMi()) {
                            HMA.baglantiyiKes();
                        }

                    }
                    long bitiszamani = System.currentTimeMillis();
                    int gecenzaman = (int) ((bitiszamani - baslamazamani) / 1000);
                    Log.yaz("Yeni hesap açma işlemi " + gecenzaman + " saniyede tamamlandı!", BILGI);
                    Log.yaz("Toplam hesap sayısı: " + Veritabani.hesapSayisi(), BILGI);
                });
                f.get(ZAMAN_ASIMI, TimeUnit.SECONDS);
            } catch (Exception e) {
                if (e.toString().contains("Timeout"))
                    Log.yaz("Hesap açma işlemi zaman aşımına uğradı! (" + ZAMAN_ASIMI + " saniye)", HATA);
                else
                    Log.yaz("Oh! Olamaz! Beklenmeyen bir hata ile karşılandı! Yani şu şey: " + e, HATA);
                Log.yaz("Kritik bir hata karşılaşıldığı için herşey devre dışı bırakılacak!", UYARI);
                Log.yaz("Tarayıcılar devre dışı bırakılıyor...", UYARI);
                if (!Eposta.driver.equals(null) && !Eposta.driver.toString().contains("null")) {
                    Eposta.driver.close();
                    Log.yaz("E-posta tarayıcısı kapatıldı", UYARI);
                }
                if (!driver.toString().contains("(null)")) {
                    driver.close();
                    Log.yaz("Ana tarayıcı kapatıldı", UYARI);
                }
                if (Veritabani.sqlBaglantisiVarmi()) {
                    Veritabani.sqlKapat();
                }
                if (HMA.bagliMi()) {
                    HMA.baglantiyiKes();
                }
            }
        }
    }
}

