package twitter;

import araclar.Log;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.File;

import static araclar.Log.Tur.*;

/**
 * Created by erkanmdr on 17.12.2015.
 */
public class Is {

    public static void tweetAt(Twitter hesap, String tweet) {
        try {
            hesap.updateStatus(tweet);
            Log.yaz("Tweet atıldı", BASARILI);
        } catch (TwitterException e) {
            Log.yaz("Tweet atılamadı: " + e, HATA);
        }
    }

    public static void takipEt(Twitter hesap, String takipedilecekhesap) {
        try {
            hesap.createFriendship(takipedilecekhesap);
            Log.yaz("Kullanıcı takip edildi: @" + takipedilecekhesap, BASARILI);
        } catch (TwitterException e) {
            Log.yaz("Kullanıcı takip edilemedi: " + e, HATA);
        }
    }

    public static void rtYap(Twitter hesap, String statuskodu) {
        try {
            hesap.retweetStatus(Long.parseLong(String.valueOf(statuskodu)));
            Log.yaz("Tweet RT edildi", BASARILI);
        } catch (TwitterException e) {
            Log.yaz("Tweet RT edilemedi: " + e, HATA);
        }
    }

    public static void favYap(Twitter hesap, String statuskodu) {
        try {
            hesap.createFavorite(Long.parseLong(String.valueOf(statuskodu)));
            Log.yaz("Tweet Fav edildi", BASARILI);
        } catch (TwitterException e) {
            Log.yaz("Tweet Fav edilemedi: " + e, HATA);
        }
    }

    public static void avatarGuncelle(Twitter hesap, File dosya) {
        try {
            Log.yaz("Profil resmi yükleniyor...", BILGI);
            hesap.updateProfileImage(dosya);
            Log.yaz("Profil resmi güncellendi", BASARILI);
        } catch (TwitterException e) {
            Log.yaz("Profil resmi güncellenemedi: " + e, HATA);
        }
    }

    public static void isimGuncelle(Twitter hesap, String isim) {
        try {
            hesap.updateProfile(isim, null, null, null);
            Log.yaz("Profil ismi güncellendi", BASARILI);
        } catch (TwitterException e) {
            Log.yaz("Profil ismi güncellenemedi: " + e, HATA);
        }
    }

    public static void urlGuncelle(Twitter hesap, String url) {
        try {
            hesap.updateProfile(null, url, null, null);
            Log.yaz("Profil URL'si güncellendi", BASARILI);
        } catch (TwitterException e) {
            Log.yaz("Profil URL'si güncellenemedi: " + e, HATA);
        }
    }

    public static void konumGuncelle(Twitter hesap, String konum) {
        try {
            hesap.updateProfile(null, null, konum, null);
            Log.yaz("Profil konumu güncellendi", BASARILI);
        } catch (TwitterException e) {
            Log.yaz("Profil konumu güncellenemedi: " + e, HATA);
        }
    }

    public static void bioGuncelle(Twitter hesap, String bio) {
        try {
            hesap.updateProfile(null, null, null, bio);
            Log.yaz("Profil biografisi güncellendi", BASARILI);
        } catch (TwitterException e) {
            Log.yaz("Profil biografisi güncellenemedi: " + e, HATA);
        }
    }

}
