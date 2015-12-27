package araclar;

import ana.Ana;
import vpn.HMA;

import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static araclar.Log.Tur.*;

/**
 * Created by erkanmdr on 20.12.2015.
 */
public class Veritabani {
    public static final String VERITABANI_URLSI = "jdbc:mysql://94.73.145.238:3306/midoriprolockter?" +
            "useUnicode=true&amp;characterEncoding=utf8";
    static String VERITABANI_KULLANICI_ADI = "mdr6C7100467E";
    static String VERITABANI_SIFRESI = "DNkw17V0";

    public enum SQLMODEL {
        ID, KULLANICI_ADI, SIFRE, TOKEN,
        SECRET_TOKEN, DURUM, ACILIS_TARIHI, ACILIS_IPSI,
        EMAIL_ADRESI, PROLOCKTER_VERSIYONU,
        CUNSOMER_KEY, CUNSOMER_SECRET, SERI, BIO, TWEET
    }

    public enum DURUMMODEL {
        REALISTIC, YUMURTA, ONAYSIZ, TEST, MANUEL
    }

    public static Statement SQL_STS = null;
    public static Connection SQL_BAGLANTISI = null;

    public static void main(String[] args) throws SQLException {
        sqlBaglan();


    }


    public static boolean sqlBaglan() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.yaz("Veritabanına bağlanılıyor...", BILGI);
            SQL_BAGLANTISI = DriverManager.getConnection
                    (VERITABANI_URLSI, VERITABANI_KULLANICI_ADI, VERITABANI_SIFRESI);
            SQL_STS = SQL_BAGLANTISI.createStatement();
            Log.yaz("Veritabanına bağlanıldı", BASARILI);
            return true;
        } catch (ClassNotFoundException e) {
            Log.yaz("Veritabanına bağlanılamadı. Class bulunamadı: " + e, HATA);
            return false;
        } catch (SQLException e) {
            Log.yaz("Veritabanına bağlanılamadı. SQL Hatası: " + e, HATA);
            return false;
        }
    }

    public static void sqlKapat() {

    }

    public static boolean sqlBaglantisiVarmi() {
        if (SQL_BAGLANTISI == null)
            return false;
        else
            return true;
    }

    public static void hesapEkle(String kullanici_adi, String sifre,
                                 String token, String secret_token,
                                 String cunsomer_key, String cunsomer_secret,
                                 DURUMMODEL durum, String email_adresi, String seri) {

        try {
            String sql = " insert into hesaplar (ID, KULLANICI_ADI, SIFRE," +
                    " TOKEN, SECRET_TOKEN, DURUM, ACILIS_TARIHI, ACILIS_IPSI," +
                    " EMAIL_ADRESI, PROLOCKTER_VERSIYONU," +
                    " CUNSOMER_KEY, CUNSOMER_SECRET, SERI)"
                    + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStmt = SQL_BAGLANTISI.prepareStatement(sql);
            preparedStmt.setNull(1, 0);
            preparedStmt.setString(2, kullanici_adi);
            preparedStmt.setString(3, sifre);
            preparedStmt.setString(4, token);
            preparedStmt.setString(5, secret_token);
            preparedStmt.setString(6, durum.toString());
            preparedStmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
            preparedStmt.setString(8, HMA.SON_IP);
            preparedStmt.setString(9, email_adresi);
            preparedStmt.setString(10, Ana.VERSIYON);
            preparedStmt.setString(11, cunsomer_key);
            preparedStmt.setString(12, cunsomer_secret);
            preparedStmt.setString(13, seri);
            preparedStmt.execute();
            Log.yaz("Veritabanına yeni hesap eklendi", BASARILI);
        } catch (SQLException e) {
            Log.yaz("Veritabanına yeni hesap eklenemedi: " + e, HATA);
        }
    }

    public static void hesapSil(String kullaniciadi) throws SQLException {
        String sql = "DELETE FROM hesaplar " +
                "WHERE '" + kullaniciadi + "' = KULLANICI_ADI";
        SQL_STS.executeUpdate(sql);
        Log.yaz("Veritabanından kullanıcı silindi", UYARI);
    }


    public static List<String> kullaniciAl(SQLMODEL model) {
        List<String> liste = new ArrayList<String>();
        try {
            String sql = "select * from hesaplar";
            ResultSet sonuc = SQL_STS.executeQuery(sql);
            while (sonuc.next()) {
                liste.add(sonuc.getString(model.toString()).toString());
            }
            Log.yaz("Veritabanından kullanıcı bilgileri alındı", BASARILI);
        } catch (SQLException e) {
            Log.yaz("Veritabanından kullanıcı bilgileri alınamadı: " + e, HATA);
        }
        return liste;
    }

    public static List<String> kullaniciTokeniAl(String id) {
        List<String> liste = new ArrayList<String>();
        try {
            String sql = "SELECT KULLANICI_ADI, TOKEN, SECRET_TOKEN," +
                    "CUNSOMER_KEY, CUNSOMER_SECRET, EMAIL_ADRESI, SIFRE FROM hesaplar WHERE id = " + id + ";";
            ResultSet sonuc = SQL_STS.executeQuery(sql);
            while (sonuc.next()) {
                liste.add(sonuc.getString("KULLANICI_ADI"));
                liste.add(sonuc.getString("TOKEN"));
                liste.add(sonuc.getString("SECRET_TOKEN"));
                liste.add(sonuc.getString("CUNSOMER_KEY"));
                liste.add(sonuc.getString("CUNSOMER_SECRET"));
                liste.add(sonuc.getString("EMAIL_ADRESI"));
                liste.add(sonuc.getString("SIFRE"));

            }
            Log.yaz("Veritabanından kullanıcı tokeni alındı", BASARILI);
        } catch (SQLException e) {
            Log.yaz("Veritabanından kullanıcı tokeni alınamadı: " + e, HATA);
        }
        return liste;
    }

    public static int hesapSayisi() {
        List<String> liste = new ArrayList<String>();
        int i = 0;
        try {
            String sql = "select * from hesaplar";
            ResultSet sonuc = SQL_STS.executeQuery(sql);
            while (sonuc.next()) {
                i++;
            }
            Log.yaz("Veritabanından kullanıcı bilgileri alındı", BASARILI);
        } catch (SQLException e) {
            Log.yaz("Veritabanından kullanıcı bilgileri alınamadı: " + e, HATA);
        }
        return i;
    }

    public static String rastgeleBio() {
        List<String> liste = new ArrayList<String>();
        try {
            String sql = "select * from rastgele_bio_listesi";
            ResultSet sonuc = SQL_STS.executeQuery(sql);
            while (sonuc.next()) {
                liste.add(sonuc.getString(SQLMODEL.BIO.toString()));
            }
            Collections.shuffle(liste, new Random(System.nanoTime()));
            Log.yaz("Veritabanından " + liste.size() + " bio arasından rastgele biri alındı", BASARILI);
        } catch (SQLException e) {
            Log.yaz("Veritabanından rastgele bio alınamadı: " + e, HATA);
        }
        return liste.get(0);
    }

    public static String rastgeleTweet() {
        List<String> liste = new ArrayList<String>();
        try {
            String sql = "select * from rastgele_tweet_listesi";
            ResultSet sonuc = SQL_STS.executeQuery(sql);
            while (sonuc.next()) {
                liste.add(sonuc.getString(SQLMODEL.TWEET.toString()));
            }
            Collections.shuffle(liste, new Random(System.nanoTime()));
            Log.yaz("Veritabanından " + liste.size() + " tweet arasından rastgele biri alındı", BASARILI);
        } catch (SQLException e) {
            Log.yaz("Veritabanından rastgele tweet alınamadı: " + e, HATA);
        }
        return liste.get(0);
    }

    public static String rastgeleTakipEdilecekKullanici() {
        List<String> liste = new ArrayList<String>();
        try {
            String sql = "select * from rastgele_takip_listesi";
            ResultSet sonuc = SQL_STS.executeQuery(sql);
            while (sonuc.next()) {
                liste.add(sonuc.getString(SQLMODEL.KULLANICI_ADI.toString()));
            }
            Collections.shuffle(liste, new Random(System.nanoTime()));
            Log.yaz("Veritabanından " + liste.size() + " takip edilecek kullanıcı arasından rastgele biri alındı", BASARILI);
        } catch (SQLException e) {
            Log.yaz("Veritabanından rastgele takip edilecek kullanıcı alınamadı: " + e, HATA);
        }
        return liste.get(0);
    }

    public static boolean internetBaglantisiVarmi() {
        Log.yaz("İnternet bağlantısı test ediliyor...", BILGI);
        try {
            final URL url = new URL("https://mobile.twitter.com/login");
            final URLConnection conn = url.openConnection();
            conn.connect();
            Log.yaz("İnternet bağlantısı var", BASARILI);
            return true;
        } catch (Exception e) {
            Log.yaz("İnternet bağlantısı bulunamadı!", HATA);
            return false;
        }
    }

}
