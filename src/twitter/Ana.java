package twitter;

import twitter4j.Twitter;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Ana {
    static String VERSIYON = "v1.0";

    public static void main(String[] args) throws IOException {
        System.out.println("MiDoRi-Prolockter " + VERSIYON + " hazır.");
        System.out.println("ANA MENÜ");
        System.out.println("Aşağıdan bir fonksiyon numarası seçin ve ENTER'a basınız.");
        System.out.println("1) RT'le ve Fav'la");
        System.out.println("2) Yeni hesap kaydet");
        System.out.println("3) Çık");
        System.out.print("SEÇİM: ");
        Scanner in = new Scanner(System.in);

        int num = in.nextInt();

        if (num == 1) {
            System.out.println("RT ve Fav yapılacak Tweet'in status kodunu giriniz.");
            System.out.print("STATUS KODU: ");
            Scanner in2 = new Scanner(System.in);
            String statuskodu = in2.next();
            File folder = new File("cfg/");
            File[] listOfFiles = folder.listFiles();
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    Twitter baglihesap = Ayar.alveBaglan(listOfFiles[i].getName());
                    Is.favRT(baglihesap, statuskodu);
                }
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
        } else {
            System.out.println("Hatalı seçim!");
            System.out.println("Programdan çıkılıyor...");
            System.exit(0);
        }
    }
}
