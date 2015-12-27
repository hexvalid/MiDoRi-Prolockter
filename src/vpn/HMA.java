package vpn;

import araclar.Log;
import araclar.Veritabani;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static araclar.Log.Tur.*;

/**
 * Created by erkanmdr on 19.12.2015.
 */
public class HMA {

    public static String SUDO_SIFRESI = "rkn42rdm";
    public static String SON_IP = "localhost";

    public static boolean bagliMi() {
        try {
            Log.yaz("VPN bağlantısı test ediliyor...", BILGI);
            String[] cmd = {"/bin/bash", "-c", "echo " + SUDO_SIFRESI + "| sudo -S  pgrep openvpn"};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = null;
            p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            String buff = null;
            line = reader.readLine();
            if (line != null) {
                Log.yaz("VPN bağlantısı var", BILGI);
                p.destroy();
                reader.close();
                return true;
            } else {
                Log.yaz("VPN bağlantısı yok", UYARI);
                p.destroy();
                reader.close();
                return false;
            }
        } catch (IOException e) {
            Log.yaz("VPN bağlantısı kontrol edilemedi: " + e, HATA);
            return false;
        }
    }

    public static boolean baglan() {
        try {
            //TODO: ŞİFRELER GLOBAL STRING'DE OLACAK!
            Log.yaz("VPN'e bağlanılıyor...", BILGI);
            String[] cmd = {"/bin/bash", "-c", "echo " + SUDO_SIFRESI + "| sudo -S  sh/hma-vpn.sh -c sertifika 'Germany' "};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = null;
            p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Log.yaz(line, CIKTI);
                if (line.contains("Connected")) {
                    Log.yaz("VPN'e bağlandı", BASARILI);
                    String IPADDRESS_PATTERN =
                            "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
                    Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        Log.yaz("IP Adresi alındı", BASARILI);
                        p.destroy();
                        reader.close();
                        SON_IP = matcher.group();
                    } else {
                        Log.yaz("IP Adresi alınamadı (sıfır döndü)", HATA);
                        p.destroy();
                        reader.close();
                        SON_IP = "0.0.0.0";
                    }
                    p.destroy();
                    reader.close();
                    return true;
                }
            }
        } catch (IOException e) {
            Log.yaz("VPN'e bağlanılamadı: " + e, HATA);
            return false;
        }
        return false;
    }

    public static void baglanVeTestEt() {
        boolean baglanti = baglan();
        while (true) {
            if (baglanti) {
                if (Veritabani.internetBaglantisiVarmi()) {
                    break;
                } else {
                    baglantiyiKes();
                    baglanti = baglan();
                }

            } else {
                baglantiyiKes();
                baglanti = baglan();
            }

        }


    }

    public static void baglantiyiKes() {
        try {
            Log.yaz("VPN bağlantısı kesiliyor...", BILGI);
            String[] cmd = {"/bin/bash", "-c", "echo " + SUDO_SIFRESI + "| sudo -S  killall openvpn"};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = null;
            p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Log.yaz(line, CIKTI);
            }
            Log.yaz("VPN bağlantısı kesildi", BASARILI);
            p.destroy();
            reader.close();
            Thread.sleep(1000);
        } catch (IOException | InterruptedException e) {
            Log.yaz("VPN bağlantısı kesilemedi: ", HATA);
        }
    }


}
