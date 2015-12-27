package selenium;

import araclar.Log;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import vpn.HMA;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static araclar.Log.Tur.*;

/**
 * Created by erkanmdr on 27.12.2015.
 */


public class Tarayici {
    static Dimension ekran = new Dimension(415, 380);

    public static WebDriver Light(boolean resimler, boolean atmisaltiotuz) {
        Log.yaz("Light hazırlanıyor...", BILGI);
        File lightKonumu = new File("light/light-bin");
        FirefoxBinary lightbin = new FirefoxBinary(lightKonumu);
        FirefoxProfile profile = new FirefoxProfile(new File("light/prof/mdbk1ccg.default"));
        if (!resimler) {
            profile.setPreference("permissions.default.image", 2);
            profile.setPreference("permissions.default.stylesheet", 2);
        } else {
            profile.setPreference("permissions.default.image", 1);
        }
        if (atmisaltiotuz) {
            profile.setPreference("general.useragent.override",
                    "Opera/9.80 (J2ME/MIDP; Opera Mini/9.80 (J2ME/23.377; U; en) Presto/2.5.25 Version/10.54");
        } else {

        }


        profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false);
        profile.setPreference("browser.startup.page", 0);
        profile.setPreference("browser.cache.disk.capacity", 0);
        profile.setPreference("browser.cache.disk.enable", false);
        profile.setPreference("browser.cache.disk.smart_size.enabled", false);
        profile.setPreference("browser.cache.disk.smart_size.first_run", false);
        profile.setPreference("browser.cache.disk.smart_size_cached_value", 0);
        profile.setPreference("browser.cache.memory.enable", false);
        profile.setPreference("browser.shell.checkDefaultBrowser", false);
        profile.setPreference("browser.startup.homepage_override.mstone", "ignore");
        profile.setPreference("browser.preferences.advanced.selectedTabIndex", 0);
        profile.setPreference("browser.privatebrowsing.dont_prompt_on_enter", true);
        profile.setPreference("browser.privatebrowsing.autostart", true);
        profile.setPreference("browser.search.update", false);
        WebDriver driver = new FirefoxDriver(lightbin, profile);
        driver.manage().window().setSize(ekran);

        driver.manage().window().setPosition(new Point(0, 0));

        Log.yaz("Light hazır", BASARILI);

        return driver;

    }

    public static void hepsiniKapat() {
        try {
            Log.yaz("Tüm tarayıcılar kapatılıyor...", BILGI);
            String[] cmd = {"/bin/bash", "-c", "echo " + HMA.SUDO_SIFRESI + "| sudo -S  killall light-bin"};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = null;
            p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Log.yaz(line, CIKTI);
            }
            Log.yaz("Tüm tarayıcılar kapatıldı", BASARILI);
            p.destroy();
            reader.close();
            Thread.sleep(400);
        } catch (IOException | InterruptedException e) {
            Log.yaz("Tüm tarayıcılar kapatılamadı: ", HATA);
        }
    }
}
