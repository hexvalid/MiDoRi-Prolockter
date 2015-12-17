package twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by erkanmdr on 17.12.2015.
 */
public class Hesap {
    public static String CUNSOMERKEY = "1XJhZzhZlbk2Qc41FBxD0NyrF";
    public static String CUNSOMERSECRET = "qX0WzbdOBNzVGOZFqDow26CyMThHRKakQpQfbBO8fFW0SZcCYX";

    public static Twitter getir(String accesstoken, String accesssecret) {
        System.out.println("Giriş yapılıyor...");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CUNSOMERKEY)
                .setOAuthConsumerSecret(CUNSOMERSECRET)
                .setOAuthAccessToken(accesstoken)
                .setOAuthAccessTokenSecret(accesssecret);
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();
        return twitter;
    }

}
