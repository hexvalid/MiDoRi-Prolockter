package bot;

import araclar.Log;

import java.io.*;
import java.util.Random;

import static araclar.Log.Tur.BASARILI;
import static araclar.Log.Tur.HATA;

/**
 * Created by erkanmdr on 20.12.2015.
 */
public class Jenerator {


    public enum ListDosyalari {
        ad, soyad, tweet, bio, takip, star
    }


    public static String sifre() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 12) {
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString().toLowerCase();
        Log.yaz("Rastgele şifre üretildi", BASARILI);
        return saltStr;
    }

    public static String rastgele(ListDosyalari dosya) {
        try {
            Random rand = new Random();
            FileInputStream fs = new FileInputStream("list/" + dosya.toString() + ".txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            int satir = 0;
            while (br.readLine() != null) {
                satir++;
            }
            fs.getChannel().position(0);
            br = new BufferedReader(new InputStreamReader(fs));
            int n = rand.nextInt(satir) + 1;
            for (int i = 1; i < n; ++i)
                br.readLine();
            Log.yaz("Rastgele " + dosya.toString() + " üretildi (" + satir + "/" + n + ")", BASARILI);
            return br.readLine();
        } catch (FileNotFoundException e1) {
            Log.yaz("Rastgele " + dosya.toString() + " üretilemedi. Çünkü dosya yok: " + e1, HATA);
            return null;
        } catch (IOException e1) {
            Log.yaz("Rastgele " + dosya.toString() + " üretilemedi: " + e1, HATA);
            return null;
        }
    }


    public static File rastgeleAvatar() {
        File parent = new File("list/avatar");
        File[] files = parent.listFiles();
        Log.yaz("Rastgele avatar seçildi", BASARILI);
        return files[(int) (Math.random() * files.length)];
    }

    public static File rastgeleBanner() {
        File parent = new File("list/banner");
        File[] files = parent.listFiles();
        Log.yaz("Rastgele banner seçildi", BASARILI);
        return files[(int) (Math.random() * files.length)];
    }
}
