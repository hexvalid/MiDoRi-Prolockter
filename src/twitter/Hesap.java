package twitter;

import araclar.Log;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import static araclar.Log.Tur.BASARILI;
import static araclar.Log.Tur.BILGI;

/**
 * Created by erkanmdr on 17.12.2015.
 */
public class Hesap {
    //ESKİ
//    public static String CUNSOMERKEY = "RzMcnCQtffylPLlbeZ95qhlvn";
//    public static String CUNSOMERSECRET = "62Aoy5W1IgkXL7XXGxCno9XwT3YgsFS8cC8pWKiB3m8xgFIPer";

    public static Twitter getir(String accesstoken, String accesssecret,
                                String cunsomerkey, String cunsomersecret) {

        Log.yaz("Giriş yapılıyor...", BILGI);
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(cunsomerkey)
                .setOAuthConsumerSecret(cunsomersecret)
                .setOAuthAccessToken(accesstoken)
                .setOAuthAccessTokenSecret(accesssecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        Log.yaz("Giriş yapıldı", BASARILI);
        return twitter;
    }

}
