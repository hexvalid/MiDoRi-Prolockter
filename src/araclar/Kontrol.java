package araclar;

import org.apache.commons.lang3.SystemUtils;

import static araclar.Log.Tur.BILGI;
import static araclar.Log.Tur.UYARI;

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

}
