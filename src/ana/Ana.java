package ana;

import araclar.Veritabani;
import bot.HesapAcBotu;
import twitter.Ayar;
import twitter.Hesap;
import twitter.Is;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import vpn.HMA;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Ana {
    public static String VERSIYON = "v1.89";

    public static void main(String[] args) throws IOException, TwitterException {
        HMA.SUDO_SIFRESI = "rkn42rdm";
        System.out.println("MiDoRi-Prolockter " + VERSIYON + " hazır.");
        System.out.println("ANA MENÜ");
        System.out.println("Aşağıdan bir fonksiyon numarası seçin ve ENTER'a basınız.");
        System.out.println("1) RT'le ve Fav'la");
        System.out.println("2) Yeni hesap kaydet");
        System.out.println("3) Çık");
        System.out.println("4) Hesap Aç");
        //Birine Takipçi Gönder
        //Bi Twit'e RT ve/veya Fav Gönder
        //(Tüm Botlar) Twit At
        //(Tüm Botlar) Birbirlerini Rastgele Takip Et
        //(Tüm Botlar) Rastgele Twitler At
        //(Hatalı Botları Ayıkla) Rastgele Twitler At
        //Yeni API Ekle
        //OTOMATİK BOT HESABI AÇ (İŞLETİM SİSTEMİNİZ UYUMSUZ)
        //Toplu DM At

        System.out.print("SEÇİM: ");
        Scanner in = new Scanner(System.in);

        int num = in.nextInt();

        if (num == 1) {
            System.out.println("RT ve Fav yapılacak Tweet'in status kodunu giriniz.");
            System.out.print("STATUS KODU: ");
            Scanner in2 = new Scanner(System.in);
            String statuskodu = in2.next();
            Veritabani.sqlBaglan();
            List<String> liste = Veritabani.kullaniciAl(Veritabani.SQLMODEL.ID);
            Collections.shuffle(liste, new Random(System.nanoTime()));

            for (int i = 1; i < liste.size(); i++) {
                List<String> kullanici = Veritabani.kullaniciTokeniAl(liste.get(i));
                System.out.println(kullanici.get(1) + kullanici.get(2));
                Twitter baglihesap = Hesap.getir(kullanici.get(1), kullanici.get(2), kullanici.get(3), kullanici.get(4));
                Is.rtYap(baglihesap, statuskodu);
                Is.favYap(baglihesap, statuskodu);
            }
            System.out.println("Görev tamamlandı. Programdan çıkılıyor...");
            System.exit(0);
        } else if (num == 2) {
            Ayar.ekle();
            System.out.println("Görev tamamlandı. Programdan çıkılıyor...");
            System.exit(0);

        } else if (num == 3) {
            System.out.println("Programdan çıkılıyor...");
            System.exit(0);
        } else if (num == 4) {
            HesapAcBotu.main(null);
        } else {
            System.out.println("Hatalı seçim!");
            System.out.println("Programdan çıkılıyor...");
            System.exit(0);
        }
    }
}
