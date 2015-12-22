package araclar;

import ana.Ana;
import bot.Jenerator;
import twitter.Hesap;
import vpn.HMA;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static araclar.Log.Tur.*;

/**
 * Created by erkanmdr on 20.12.2015.
 */
public class Veritabani {
    public static final String VERITABANI_URLSI = "jdbc:mysql://94.73.145.238:3306/midoriprolockter";
    static String VERITABANI_KULLANICI_ADI = "mdr6C7100467E";
    static String VERITABANI_SIFRESI = "DNkw17V0";

    public enum SQLMODEL {
        ID, KULLANICI_ADI, SIFRE, TOKEN,
        SECRET_TOKEN, DURUM, ACILIS_TARIHI, ACILIS_IPSI,
        EMAIL_ADRESI, PROLOCKTER_VERSIYONU,
        CUNSOMER_KEY, CUNSOMER_SECRET, SERI
    }

    public enum DURUMMODEL {
        REALISTIC, YUMURTA, ONAYSIZ, TEST, MANUEL
    }

    public static Statement SQL_STS = null;
    public static Connection SQL_BAGLANTISI = null;

    public static void main(String[] args) throws SQLException {
        System.out.println(Jenerator.rastgeleAvatar());
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
        try {
            SQL_STS.close();
            SQL_BAGLANTISI.close();
            Log.yaz("Veritabanı kapatıldı", BILGI);
        } catch (SQLException e) {
            Log.yaz("Veritabanı kapatılamadı: " + e, HATA);
        }
    }

    public static void hesapEkle(String kullanici_adi, String sifre,
                                 String token, String secret_token, DURUMMODEL durum,
                                 String email_adresi, String seri) {

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
            preparedStmt.setString(8, HMA.sonIP());
            preparedStmt.setString(9, email_adresi);
            preparedStmt.setString(10, Ana.VERSIYON);
            preparedStmt.setString(11, Hesap.CUNSOMERKEY);
            preparedStmt.setString(12, Hesap.CUNSOMERSECRET);
            preparedStmt.setString(13, seri);
            preparedStmt.execute();
            Log.yaz("Veritabanına yeni hesap eklendi", BASARILI);
        } catch (SQLException e) {
            Log.yaz("Veritabanına yeni hesap eklenemedi: " + e, HATA);
        }
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
            String sql = "SELECT KULLANICI_ADI, TOKEN, SECRET_TOKEN FROM hesaplar WHERE id = " + id + ";";
            ResultSet sonuc = SQL_STS.executeQuery(sql);
            while (sonuc.next()) {
                liste.add(sonuc.getString("KULLANICI_ADI"));
                liste.add(sonuc.getString("TOKEN"));
                liste.add(sonuc.getString("SECRET_TOKEN"));
            }
            Log.yaz("Veritabanından kullanıcı tokeni alındı", BASARILI);
        } catch (SQLException e) {
            Log.yaz("Veritabanından kullanıcı tokeni alınamadı: " + e, HATA);
        }
        return liste;
    }
}
