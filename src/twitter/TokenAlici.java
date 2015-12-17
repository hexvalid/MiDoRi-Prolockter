package twitter;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by erkanmdr on 17.12.2015.
 */
public class TokenAlici {
    public static String ENSON_TOKEN;
    public static String ENSON_SECRETTOKEN;

    public static Twitter al() throws Exception {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(Hesap.CUNSOMERKEY, Hesap.CUNSOMERSECRET);
        RequestToken requestToken = twitter.getOAuthRequestToken();
        AccessToken accessToken = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (null == accessToken) {
            System.out.println("Bu linkten uygulamaya giriş yapınız:");
            System.out.println(requestToken.getAuthorizationURL());
            System.out.print("PIN Kodunu buraya giriniz: ");
            String pin = br.readLine();
            try {
                if (pin.length() > 0) {
                    accessToken = twitter.getOAuthAccessToken(requestToken, pin);
                } else {
                    accessToken = twitter.getOAuthAccessToken();
                }
            } catch (TwitterException te) {
                if (401 == te.getStatusCode()) {
                    System.out.println("Token alımı başarısız");
                } else {
                    te.printStackTrace();
                }
            }
        }
        ENSON_TOKEN = accessToken.getToken();
        ENSON_SECRETTOKEN = accessToken.getTokenSecret();
        return Hesap.getir(ENSON_TOKEN, ENSON_SECRETTOKEN);
    }


}
