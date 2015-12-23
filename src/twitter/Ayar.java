package twitter;

import twitter4j.Twitter;

import java.io.*;
import java.util.Properties;

/**
 * Created by erkanmdr on 17.12.2015.
 */
public class Ayar {

    public static void ekle() {
        try {
            Twitter hesap = TokenAlici.al();
            String ID = hesap.getScreenName();
            Properties props = new Properties();
            props.setProperty("id", ID);
            props.setProperty("token", TokenAlici.ENSON_TOKEN);
            props.setProperty("tokensecret", TokenAlici.ENSON_SECRETTOKEN);
            File f = new File("cfg/" + ID + ".properties");
            OutputStream out = new FileOutputStream(f);
            props.store(out, "MiDoRi-Prolockter");
            System.out.println(ID + ".properties yazıldı.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Twitter alveBaglan(String dosya) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("cfg/" + dosya);
            prop.load(input);
            System.out.println(prop.getProperty("id") + " kullanıcısının ayar dosyası okundu.");
         //   return Hesap.getir(prop.getProperty("token"), prop.getProperty("tokensecret"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}


