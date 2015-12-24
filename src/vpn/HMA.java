package vpn;

import araclar.Log;

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

    public static boolean bagliMi() {
        try {
            Log.yaz("VPN bağlantısı test ediliyor...", BILGI);
            String[] cmd = {"/bin/bash", "-c", "echo " + SUDO_SIFRESI + "| sudo -S  sh/hma-vpn.sh -s"};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = null;
            p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            String buff = null;
            while ((line = reader.readLine()) != null) {
                buff = buff + line;
                Log.yaz(line, CIKTI);
            }
            if (buff.contains("Connected")) {
                Log.yaz("VPN bağlantısı var", BILGI);
                return true;
            } else {
                Log.yaz("VPN bağlantısı yok", UYARI);
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


    public static void baglantiyiKes() {
        try {
            Log.yaz("VPN bağlantısı kesiliyor...", BILGI);
            String[] cmd = {"/bin/bash", "-c", "echo " + SUDO_SIFRESI + "| sudo -S  sh/hma-vpn.sh -x"};
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

    public static String sonIP() {
        try {
            Log.yaz("IP Adresi alınıyor...", BASARILI);
            String[] cmd = {"/bin/bash", "-c", "echo " + SUDO_SIFRESI + "| sudo -S  sh/hma-vpn.sh -s"};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process p = null;
            p = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = null;
            String buff = null;
            while ((line = reader.readLine()) != null) {
                buff = buff + line;
                Log.yaz(line, CIKTI);
            }
            if (buff.contains("Connected")) {
                String IPADDRESS_PATTERN =
                        "(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)";
                Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
                Matcher matcher = pattern.matcher(buff);
                if (matcher.find()) {
                    Log.yaz("IP Adresi alındı", BASARILI);
                    p.destroy();
                    reader.close();
                    return matcher.group();
                } else {
                    Log.yaz("IP Adresi alınamadı (sıfır döndü)", HATA);
                    p.destroy();
                    reader.close();
                    return "0.0.0.0";
                }
            } else {
                Log.yaz("IP Adresi alınamadı (sıfır döndü)", HATA);
                p.destroy();
                reader.close();
                return "0.0.0.0";
            }
        } catch (IOException e) {
            Log.yaz("IP Adresi alınamadı (sıfır döndü): " + e, HATA);
            return "0.0.0.0";
        }


    }


}
