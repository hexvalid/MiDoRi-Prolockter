package bot;

import araclar.Log;
import araclar.Veritabani;
import twitter.APILockter;
import twitter.Hesap;
import twitter4j.*;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static araclar.Log.Tur.*;

/**
 * Created by erkanmdr on 22.12.2015.
 */
public class HirsizBotu {

    public static void main(String[] args) throws TwitterException, InterruptedException {

        while (true) {
            Log.yaz("Profil resmi ve kapak fotoğrafı çalıcı başladı", BILGI);
            Veritabani.sqlBaglan();
            Log.yaz("Rastgele API kodları alınıyor...", BILGI);
            List<String> liste = Veritabani.kullaniciAl(Veritabani.SQLMODEL.ID);
            Collections.shuffle(liste, new Random(System.nanoTime()));
            List<String> kullanici = Veritabani.kullaniciTokeniAl(liste.get(1));
            Log.yaz("Rastgele API kodları şu kullanıcıdan alındı: @" + kullanici.get(0), BASARILI);
            Twitter twitter = Hesap.getir(kullanici.get(1), kullanici.get(2), kullanici.get(3), kullanici.get(4));


            Trends trends = twitter.getPlaceTrends(23424969); //TÜRKİYE
            String tt = trends.getTrends()[0].getName();
            Log.yaz("En popüler TT alındı: " + tt, BASARILI);


            Query query = new Query(tt);
            QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {
                String kullaniciadi = status.getUser().getScreenName();
                if (!new File("list/avatar/" + kullaniciadi + ".jpeg").exists()) {
                    APILockter.indir(status.getUser().getOriginalProfileImageURLHttps(),
                            "list/avatar/" + kullaniciadi + ".jpeg", false);
                    Log.yaz("@" + kullaniciadi + " isimli kullanıcısının profil resmi alındı", BASARILI);
                    APILockter.indir(status.getUser().getProfileBannerURL(),
                            "list/banner/" + kullaniciadi + ".jpeg", false);
                    Log.yaz("@" + kullaniciadi + " isimli kullanıcısının banner resmi alındı", BASARILI);
                } else {
                    Log.yaz("@" + kullaniciadi + " isimli kullanıcının resimleri zaten alınmış", UYARI);
                }
            }
                Thread.sleep(15000);
        }
    }
}
