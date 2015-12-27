package ana;

import araclar.Kontrol;
import araclar.Veritabani;
import bot.HesapAcBotu;
import org.openqa.selenium.WebDriver;
import selenium.Tarayici;
import selenium.WebLockter;
import twitter.Ayar;
import twitter4j.TwitterException;
import vpn.HMA;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Ana {
    public static String VERSIYON = "v1.99";

    public static void main(String[] args) throws IOException, TwitterException {
        HMA.SUDO_SIFRESI = "rkn42rdm";
        System.out.println("");
        System.out.println("______  MiDoRi  _            _    _            ");
        System.out.println("| ___ \\        | |          | |  | |           ");
        System.out.println("| |_/ / __ ___ | | ___   ___| | _| |_ ___ _ __ ");
        System.out.println("|  __/ '__/ _ \\| |/ _ \\ / __| |/ / __/ _ \\ '__|");
        System.out.println("| |  | | | (_) | | (_) | (__|   <| ||  __/ |   ");
        System.out.println("\\_|  |_|  \\___/|_|\\___/ \\___|_|\\_\\\\__\\___|_|   ");
        System.out.println("                                      " + VERSIYON + "\n\n");
        Veritabani.internetBaglantisiVarmi();
        boolean LINUX = Kontrol.LinuxMu();
        System.out.println(Kontrol.BagimlilikKontrolu());

        System.out.println("----------------------------------------------");
        System.out.println("ANA MENÜ");
        System.out.println("----------------------------------------------");
        System.out.println("Aşağıdan bir fonksiyon numarası seçin ve ENTER'a basınız.");
        System.out.println("1) Bi Tw1it'e RT ve/veya Fav gönder [STABİL]");
        System.out.println("2) Birine takipçi gönder veya geri al [STABİL]");
        System.out.println("3) Birine seri DM gönder [STABİL DEĞİL]");
        System.out.println("4) Veritabanına elle yeni kullanıcı ekle [BETA]");
        System.out.println("5) Veritabanındaki hesaplara rastgele tweet attır [STABİL DEĞİL]");
        System.out.println("6) Veritabanındaki hesapları kendi aralarında takip ettir [STABİL DEĞİL]");
        System.out.println("7) Veritabanını bilgileri");
        System.out.println("8) OTOMATİK SERİ HESAP AÇ [BETA]");
        System.out.println("9) Çık");


        //Birine Takipçi Gönder
        //
        //(Tüm Botlar) Twit At
        //(Tüm Botlar) Birbirlerini Rastgele Takip Et
        //(Tüm Botlar) Rastgele Twitler At
        //(Hatalı Botları Ayıkla) Rastgele Twitler At
        //OTOMATİK BOT HESABI AÇ (İŞLETİM SİSTEMİNİZ UYUMSUZ)

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
                //  System.out.println(kullanici.get(1) + kullanici.get(2));
                WebDriver driver = Tarayici.Light(false, true);
                WebLockter.girisYap(driver, kullanici.get(0), kullanici.get(6), kullanici.get(5));
                WebLockter.takipEt(driver, "daulkim_");
                driver.quit();
            }
            System.out.println("Görev tamamlandı. Programdan çıkılıyor...");
            System.exit(0);
        } else if (num == 2) {
            Ayar.ekle();
            System.out.println("Görev tamamlandı. Programdan çıkılıyor...");
            System.exit(0);
        } else if (num == 8) {
            HesapAcBotu.main(null);
        } else if (num == 9) {
            System.out.println("Programdan çıkılıyor...");
            System.exit(0);
        } else {
            System.out.println("Hatalı seçim!");
            System.out.println("Programdan çıkılıyor...");
            System.exit(0);
        }
    }
}
