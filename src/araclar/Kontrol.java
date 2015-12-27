package araclar;

import org.apache.commons.lang3.SystemUtils;

import java.io.File;

import static araclar.Log.Tur.*;

/**
 * Created by erkanmdr on 24.12.2015.
 */
public class Kontrol {

    public static boolean LinuxMu() {
        if (SystemUtils.IS_OS_LINUX) {
            Log.yaz("Linux makine tespit edildi!", BILGI);
            return true;
        } else {
            Log.yaz("Linux makinede çalışmıyorsun! Bazı fonksiyonlar devre dışı bırakılacak", UYARI);
            return false;
        }
    }


    public static boolean BagimlilikKontrolu() {
        Log.yaz("Bağımlılıklar kontrol ediliyor...", BILGI);
        if (!new File("/usr/bin/chromedriver").exists()) {
            Log.yaz("chromedriver bulanamadı! Bazı fonksiyonlar devre dışı bırakılacak", UYARI);
            return false;
        }

        else if (!new File("/usr/bin/fping").exists()) {
            Log.yaz("fping bulanamadı! Bazı fonksiyonlar devre dışı bırakılacak", UYARI);
            return false;
        }
        else if (!new File("/usr/bin/openvpn").exists()) {
            Log.yaz("openvpn bulanamadı! Bazı fonksiyonlar devre dışı bırakılacak", UYARI);
            return false;
        }
        else if (!new File("/usr/bin/sudo").exists()) {
            Log.yaz("sudo bulanamadı! Bazı fonksiyonlar devre dışı bırakılacak", UYARI);
            return false;
        }
        else if (!new File("/usr/bin/dialog").exists()) {
            Log.yaz("dialog bulanamadı! Bazı fonksiyonlar devre dışı bırakılacak", UYARI);
            return false;
        }
        else if (!new File("/usr/bin/dialog").exists()) {
            Log.yaz("dialog bulanamadı! Bazı fonksiyonlar devre dışı bırakılacak", UYARI);
            return false;
        }
        else if (!new File("/usr/bin/wget").exists()) {
            Log.yaz("wget bulanamadı! Bazı fonksiyonlar devre dışı bırakılacak", UYARI);
            return false;
        }
        else if (!new File("/usr/bin/curl").exists()) {
            Log.yaz("curl bulanamadı! Bazı fonksiyonlar devre dışı bırakılacak", UYARI);
            return false;
        }
        else {
            Log.yaz("Tüm bağımlılıklar mevcut!", BASARILI);
            return true;
        }
    }


}
