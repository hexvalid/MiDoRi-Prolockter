package selenium;

import araclar.Log;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;

import static araclar.Log.Tur.BASARILI;
import static araclar.Log.Tur.BILGI;

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
}
